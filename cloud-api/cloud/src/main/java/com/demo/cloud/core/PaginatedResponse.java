package com.demo.cloud.core;

import java.util.List;

public record PaginatedResponse<T>(List<T> data, long totalElements, int totalPages) {
}
