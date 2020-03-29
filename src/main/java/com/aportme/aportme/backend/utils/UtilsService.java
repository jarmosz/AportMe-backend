package com.aportme.aportme.backend.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Objects;

@Service
public class UtilsService {
    public static void copyNonNullProperties(Object source, Object target) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final String[] emptyNames = Arrays.stream(src.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(x -> Objects.isNull(src.getPropertyValue(x)))
                .distinct()
                .toArray(String[]::new);

        BeanUtils.copyProperties(source, target, emptyNames);
    }
}
