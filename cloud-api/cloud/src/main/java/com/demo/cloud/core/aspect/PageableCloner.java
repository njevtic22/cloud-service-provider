package com.demo.cloud.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Aspect
@Component
public class PageableCloner {

    @Pointcut("execution(* com.demo.cloud.controller.*.*(@com.demo.cloud.core.aspect.annotation.MachinePageable (*), ..))")
    public void machinePageableMethods() {}

    @Around("machinePageableMethods()")
    public Object cloneMachinePageable(ProceedingJoinPoint joinPoint) throws Throwable {
//        Unknown index of pageable -> find it
//        Pageable original = null;
//        int i;
//        for (i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            if (arg instanceof Pageable) {
//                original = (Pageable) arg;
//                break;
//            }
//        }

//        Known index of pageable
        Object[] args = joinPoint.getArgs();
        Pageable original = (Pageable) args[0];
        args[0] = clone(original, this::machineClone);

        return joinPoint.proceed(args);
    }

    private Object clone(Pageable original, Function<Sort.Order, Sort.Order> orderCloning) {
        Sort clonedSort = clone(original.getSort(), orderCloning);
        return PageRequest.of(original.getPageNumber(), original.getPageSize(), clonedSort);
    }

    private Sort clone(Sort original, Function<Sort.Order, Sort.Order> orderCloning) {
        List<Sort.Order> clonedOrders = original.stream().map(orderCloning).toList();
        return Sort.by(clonedOrders);
    }

    private Sort.Order machineClone(Sort.Order orig) {
        String property = orig.getProperty();
        return switch (property) {
            case "cpu", "ram", "gpu" -> new Sort.Order(orig.getDirection(), "category." + property);
            default -> orig;
        };
    }
}
