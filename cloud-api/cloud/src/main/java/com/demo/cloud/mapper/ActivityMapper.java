package com.demo.cloud.mapper;

import com.demo.cloud.dto.activity.ActivityViewDto;
import com.demo.cloud.model.Activity;
import org.springframework.stereotype.Component;

import static com.demo.cloud.util.DateTimeUtil.toEpochMilli;

@Component
public class ActivityMapper extends PageMapper<Activity, ActivityViewDto> {
    @Override
    public ActivityViewDto toDto(Activity activity) {
        Long turnedOff = activity.getTurnedOff() == null ? null : toEpochMilli(activity.getTurnedOff());
        return new ActivityViewDto(
                activity.getId(),
                toEpochMilli(activity.getTurnedOn()),
                turnedOff,
                activity.getProfit()
        );
    }
}
