package com.project.gym.common;

import java.util.List;

public record PageResult<T>(List<T> content, int total, int page, int size) {
    public long  totalPages() {
        return (size <= 0) ? 0 : (long) Math.ceil((double) total / (double) size);
    }
    public boolean hasPrev() {
        return page > 1;
    }
    public boolean hasNext() {
        return page < totalPages();
    }
}
