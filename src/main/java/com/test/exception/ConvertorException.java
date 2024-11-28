package com.test.exception;

import org.springframework.util.StringUtils;


public class ConvertorException extends EndUserException {

    public ConvertorException(Class<?> clazz, String formattedParam) {
        super(ConvertorException.generateMessage(clazz.getSimpleName(), formattedParam));
    }

    private static String generateMessage(String entity, String formattedParam) {
        return String.format("%s was not found for parameter(s) %s", StringUtils.capitalize(entity), formattedParam);
    }

    public ConvertorException(String msg) {
        super(msg);
    }

}
