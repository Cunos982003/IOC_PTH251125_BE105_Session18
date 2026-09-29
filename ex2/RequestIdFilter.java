package com.example.loggingdemo.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. Sinh UUID duy nhất cho mỗi request
            String requestId = UUID.randomUUID().toString();

            // 2. Put requestId vào MDC Context
            MDC.put(REQUEST_ID_KEY, requestId);

            // 3. Cho phép request tiếp tục đi qua Filter Chain
            filterChain.doFilter(request, response);
        } finally {
            // 4. Xóa requestId khỏi MDC để dọn dẹp Thread context
            MDC.remove(REQUEST_ID_KEY);
        }
    }
}