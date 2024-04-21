package com.yuanit.common.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;


/**
 * 手动校验参数工具类
 *
 * @author qinrongjun
 * @description
 */
public class BeanValidatorUtils {

    /**
     * 参数校验
     *
     * @param validator
     * @param object
     * @param groups
     * @throws ConstraintViolationException
     */
    public static void validateWithException(Validator validator, Object object, Class<?>... groups) throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
