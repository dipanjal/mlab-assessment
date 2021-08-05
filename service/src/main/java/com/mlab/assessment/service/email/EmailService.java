package com.mlab.assessment.service.email;

import com.mlab.assessment.model.dto.EmailDTO;

import java.util.concurrent.CompletableFuture;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface EmailService {
    CompletableFuture<Boolean> sendEmail(EmailDTO dto);
}
