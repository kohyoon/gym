package com.project.gym.common;

import java.util.List;

public record PageResult<T>(List<T> content, int total, int page, int size) {
    public int totalPages() {
        return (int) Math.ceil((double) total / Math.max(1, size));
    }
    public boolean hasPrev() {
        return page > 1;
    }
    public boolean hasNext() {
        return page < totalPages();
    }
}
