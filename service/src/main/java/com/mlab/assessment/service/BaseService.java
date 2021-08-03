package com.mlab.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public class BaseService {

    private HttpServletRequest request;
    private MessageSource messageSource;

    @Autowired
    public void inject(HttpServletRequest request, MessageSource messageSource){
        this.request = request;
        this.messageSource = messageSource;
    }


    protected Locale getLocale(){
        return request.getLocale();
    }

    protected String getLocalMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, getLocale());
    }


}
