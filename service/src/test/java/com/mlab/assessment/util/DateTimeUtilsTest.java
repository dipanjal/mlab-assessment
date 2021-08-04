package com.mlab.assessment.util;

import com.mlab.assessment.utils.DateTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
public class DateTimeUtilsTest {

    @Test
    public void apiToDbDateTest(){
        String apiDate = "12-05-2019";
        String expected = "2019-05-12";
        String result = DateTimeUtils.toDBDateFormat(apiDate);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void dbToAPIDateTest(){
        String expected = "12-05-2019";
        String dbDate = "2019-05-12";
        String result = DateTimeUtils.toAPIDateFormat(dbDate);
        Assertions.assertEquals(expected, result);
    }
}
