package com.mlab.assessment.service.email;

import com.mlab.assessment.annotation.EnableLogging;
import com.mlab.assessment.model.dto.EmailResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@EnableLogging
@Slf4j
public class EmailServiceImpl implements EmailService{

    @Override
    public CompletableFuture<Boolean> sendEmail(EmailResponseDTO dto) {
        return CompletableFuture.supplyAsync(() -> {
            log.info(dto.getBody());
            log.info("Email Sent Successfully to: {}", dto.getEmail());
            return true;
        });
    }
}
