package com.mlab.assessment.statics;

import lombok.experimental.UtilityClass;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@UtilityClass
public class TransactionType {
    public static final String TRANSFER = "TRANSFER";
    public static final String REVERSE = "REVERSE";

    public static boolean isValid(String value){
        return value.equalsIgnoreCase(TRANSFER)
                || value.equalsIgnoreCase(REVERSE);
    }

    public static boolean isNotValid(String value){
        return !isValid(value);
    }

    public static boolean isTransfer(String value){
        return value.equalsIgnoreCase(TRANSFER);
    }

    public static boolean isReverse(String value){
        return value.equalsIgnoreCase(REVERSE);
    }
}
