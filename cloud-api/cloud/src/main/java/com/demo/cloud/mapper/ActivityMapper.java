package com.demo.cloud.mapper;

import com.demo.cloud.dto.activity.ActivityViewDto;
import com.demo.cloud.model.Activity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ActivityMapper extends PageMapper<Activity, ActivityViewDto> {
    @Override
    public ActivityViewDto toDto(Activity activity) {
        long turnedOff = activity.getTurnedOff() == null ? -1 : activity.getTurnedOff().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new ActivityViewDto(
                activity.getId(),
                activity.getTurnedOn().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                turnedOff,
                activity.getProfit()
        );
    }
}
