package com.mlab.assessment;

import com.mlab.assessment.props.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class LogMDCFilter extends OncePerRequestFilter {

    private final AppProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader(properties.getRequestIdHeader());
        MDC.put(properties.getRequestIdHeader(), requestId);
        filterChain.doFilter(request, response);
    }
}
