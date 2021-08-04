package com.mlab.assessment.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
@ConfigurationProperties("application")
@Getter
@Setter
public class AppProperties {
    private String requestIdHeader;
    private int maxIssueBook;
}
