package com.demo.cloud.mapper;

import com.demo.cloud.core.PaginatedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public abstract class PageMapper<T, DTO> {
    public PaginatedResponse<DTO> toDto(Page<T> model) {
        List<DTO> dto =model.getContent()
                .stream()
                .map(this::toDto)
                .toList();

        return new PaginatedResponse<>(
                dto,
                model.getTotalElements(),
                model.getTotalPages()
        );
    }

    public abstract DTO toDto(T model);
}
