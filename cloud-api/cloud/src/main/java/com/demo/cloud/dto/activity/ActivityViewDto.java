package com.demo.cloud.dto.activity;

public record ActivityViewDto(
        Long id,
        long turnedOn,
        Long turnedOff,
        float profit
) {
}
