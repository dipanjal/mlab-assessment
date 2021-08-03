package com.mlab.assessment.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class TransactionRequest {
    @NotBlank(message = "Request ID can not be empty")
    private String requestId;
    @NotBlank(message = "Requester can not be empty")
    private String requester;
    @NotBlank(message = "Transaction Type can not be empty")
    private String transactionType;
    @NotBlank(message = "Source Account Number can not be empty")
    private String sourceAccountNumber;
    @NotBlank(message = "Amount can not be empty")
    private String amount;
    @NotBlank(message = "Destination Account Number can not be empty")
    private String destinationAccountNumber;
    @NotBlank(message = "Transaction Note can not be empty")
    private String note;
}
