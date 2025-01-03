package com.prometheusutil.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prometheusutil.global.aop.ExceptionHandlerLog;
import com.prometheusutil.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    ApiResponse<Object> bindException(BindException e, HttpServletRequest request) {
        log.info("[{} {}] request - {}", request.getMethod(), request.getRequestURI(),
            getRequestData(request));
        ApiResponse<Object> response = ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorResponse.builder()
                .errorCode(1001)
                .errorMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build()
        );
        log.info("[{} {}] response - {}", request.getMethod(), request.getRequestURI(),
            response);
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<Object> MethodArgumentNotValidException(MethodArgumentNotValidException e,
        HttpServletRequest request) {
        log.info("[{} {}] request - {}", request.getMethod(), request.getRequestURI(),
            getRequestData(request));
        ApiResponse<Object> response = ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorResponse.builder()
                .errorCode(1001)
                .errorMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build()
        );
        log.info("[{} {}] response - {}", request.getMethod(), request.getRequestURI(),
            response);
        return response;
    }

    @ExceptionHandlerLog
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    ApiResponse<Object> notFoundException(CustomNotFoundException e) {
        return ApiResponse.of(
            HttpStatus.NOT_FOUND,
            ErrorResponse.builder()
                .errorCode(e.getErrorCode().getCode())
                .errorMessage(e.getErrorCode().getMessage())
                .build()
        );
    }

    @ExceptionHandlerLog
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ApiResponse<Object> notFoundException(Exception e) {
        return ApiResponse.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorResponse.builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build()
        );
    }

    private String getRequestData(HttpServletRequest request) {
        ObjectNode requestInfo = new ObjectMapper().createObjectNode();
        ObjectNode requestParam = new ObjectMapper().createObjectNode();
        ObjectNode requestBody = new ObjectMapper().createObjectNode();

        // request parameter
        request.getParameterMap().forEach((key, value) -> {
            requestParam.put(key, String.join(",", value));
        });

        // request body
        try {
            ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
            String responseBody = new String(cachingRequest.getContentAsByteArray(),
                StandardCharsets.UTF_8);
        } catch (Exception ignored) {
        }

        requestInfo.put("param", requestParam);
        requestInfo.put("body", requestBody);
        return requestInfo.toString();
    }
}
