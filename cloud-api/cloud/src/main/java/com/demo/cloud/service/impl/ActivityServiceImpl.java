package com.demo.cloud.service.impl;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.ModelConstraintException;
import com.demo.cloud.model.Activity;
import com.demo.cloud.model.Category;
import com.demo.cloud.model.DriveType;
import com.demo.cloud.model.VirtualMachine;
import com.demo.cloud.repository.ActivityRepository;
import com.demo.cloud.repository.specification.EntitySpecification;
import com.demo.cloud.service.ActivityService;
import com.demo.cloud.service.DriveService;
import com.demo.cloud.service.VirtualMachineService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository repository;
    private final VirtualMachineService service;
    private final DriveService driveService;
    private final EntitySpecification<Activity> spec;

    public ActivityServiceImpl(ActivityRepository repository, VirtualMachineService service, DriveService driveService, EntitySpecification<Activity> spec) {
        this.repository = repository;
        this.service = service;
        this.driveService = driveService;
        this.spec = spec;
    }

    @Override
    @Transactional
    public Activity add(Long machineId) {
        VirtualMachine found = service.getById(machineId);

        if (found.isActive()) {
            throw new ModelConstraintException("Chosen machine is already active");
        }

        Activity toAdd = new Activity(
                LocalDateTime.now(),
                null,
                found,
                0
        );
        Activity saved = repository.save(toAdd);
        found.turnOn();
        return saved;
    }

    @Override
    public Page<Activity> getAll(Pageable pageable, Map<String, String> filter) {
        return repository.findAll(spec.get(filter), pageable);
    }

    @Override
    public Activity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity", id));
    }

    @Override
    @Transactional
    public Activity end(Long machineId) {
        VirtualMachine found = service.getById(machineId);

        if (!found.isActive()) {
            throw new ModelConstraintException("Chosen machine is not active");
        }

        Activity ongoing = repository.findByMachineIdAndTurnedOffNull(found.getId())
                .orElseThrow(() -> new EntityNotFoundException("No activity found for machine with id '" + found.getId() + "' and no turned off date"));

        // TODO: add tmpProfit
        LocalDateTime turnedOff = LocalDateTime.now();
        long elapsedSeconds = elapsedSeconds(ongoing.getTurnedOn(), turnedOff);
        float profit = calcProfit(found, found.getCategory(), elapsedSeconds);

        Activity toEnd = new Activity(
                ongoing.getId(),
                ongoing.getTurnedOn(),
                turnedOff,
                profit,
                found
        );
        Activity ended = repository.save(toEnd);
        found.turnOff();
        return ended;
    }

    private long elapsedSeconds(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toSeconds();
    }

    private float calcProfit(VirtualMachine vm, Category cat, long elapsedSeconds) {
        int cpu = cat.getCpu();
        int ram = cat.getRam();
        int gpu = cat.getGpu();

        int cpuCost = 25_000_000;
        int ramCost = 15_000_000;
        int gpuCost = 1_000_000;

        float machineProfit = cpuCost * cpu + ramCost * ram + gpuCost * gpu;

        float hddProfit = driveService.calcProfit(vm.getId(), DriveType.HDD);
        float ssdProfit = driveService.calcProfit(vm.getId(), DriveType.SSD);
        float driveProfit = hddProfit + ssdProfit;

        long secondsInMonth = 30 * 24 * 60 * 60;
        float profitPerSecond = (machineProfit + driveProfit) / secondsInMonth;
        float totalProfit = profitPerSecond * elapsedSeconds;
        return round(totalProfit, 2);
    }

    // or Math.round(f, 10 ^ decimal) / 10 ^ decimal
    private float round(float f, int decimal) {
        BigDecimal tmp = new BigDecimal(f);
        tmp = tmp.setScale(decimal, RoundingMode.HALF_UP);
        return tmp.floatValue();
    }
}
