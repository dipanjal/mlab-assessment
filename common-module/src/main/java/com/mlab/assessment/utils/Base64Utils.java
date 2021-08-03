package com.mlab.assessment.utils;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@UtilityClass
public class Base64Utils {
    public static String decodeToString(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString), StandardCharsets.UTF_8);
    }
}
