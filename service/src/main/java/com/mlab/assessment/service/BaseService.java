package com.mlab.assessment.service;

import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.props.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public abstract class BaseService {

    protected final String RECORD_NOT_FOUND_MSG_KEY="api.response.NOT_FOUND.message";

    @Autowired
    protected LocaleMessageHelper messageHelper;
    @Autowired
    protected AppProperties props;

    protected Supplier<RecordNotFoundException> supplyRecordNotFoundException(String messageKey){
        return this.supplyException(
                new RecordNotFoundException(
                        messageHelper.getLocalMessage(messageKey)
                )
        );
    }

    private  <E extends RuntimeException> Supplier<E> supplyException(E exception){
        return () -> exception;
    }
}
