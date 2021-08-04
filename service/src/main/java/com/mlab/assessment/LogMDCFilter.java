package com.mlab.assessment;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
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
public class LogMDCFilter extends OncePerRequestFilter {

    @Value("${application.request-id-header}")
    private String headerName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader(headerName);
        MDC.put(headerName, requestId);
        filterChain.doFilter(request, response);
    }
}
