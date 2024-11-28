package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageServiceImpl implements com.test.service.MessageService {


    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String id) {
        Locale locale = Locale.US;
        try {
            return messageSource.getMessage(id, null, locale);
        } catch (Exception e) {
            return id;
        }
    }

    @Override
    public String getMessage(String id, Object[] arg) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, arg, locale);
    }
}
