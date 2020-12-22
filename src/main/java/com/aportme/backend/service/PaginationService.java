package com.aportme.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationService {

    public static <T> Page<T> mapToPageImpl(List<T> content, Pageable pageable, long totalSize) {
        return new PageImpl<>(content, pageable, totalSize);
    }
}
