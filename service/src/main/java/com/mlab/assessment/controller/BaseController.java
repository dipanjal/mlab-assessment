package com.mlab.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public class BaseController {

    private HttpServletRequest request;
    private MessageSource messageSource;

    @Autowired
    public void inject(HttpServletRequest request, MessageSource messageSource){
        this.request = request;
        this.messageSource = messageSource;
    }

    protected String getLocalMessage(String key, Object... objects){
        return messageSource.getMessage(key, objects, request.getLocale());
    }

    protected String getJoinedErrorMessage(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .map(this::getLocalMessage)
                .collect(Collectors.joining(", "));
    }
}
