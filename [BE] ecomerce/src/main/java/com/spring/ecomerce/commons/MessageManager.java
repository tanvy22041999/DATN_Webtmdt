package com.spring.ecomerce.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageManager {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String var1, @Nullable Object[] var2){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(var1, var2, locale);
    }
}
