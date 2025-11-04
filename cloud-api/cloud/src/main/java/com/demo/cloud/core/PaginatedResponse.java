package com.demo.cloud.core;

import java.util.List;

public record PaginatedResponse<T>(long totalElements, int totalPages, List<T> data) {
}
