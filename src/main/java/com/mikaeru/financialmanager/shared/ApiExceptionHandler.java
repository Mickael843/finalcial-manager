package com.mikaeru.financialmanager.shared;

import com.mikaeru.financialmanager.shared.exceptions.DuplicatedDataException;
import com.mikaeru.financialmanager.shared.model.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DuplicatedDataException.class})
    protected ResponseEntity<Object> handleDuplicatedDataException(DuplicatedDataException exception, WebRequest request) {
        Problem problem = new Problem(exception.getMessage(), BAD_REQUEST.value(), emptyList(), OffsetDateTime.now());
        return handleExceptionInternal(exception, problem, new HttpHeaders(), BAD_REQUEST, request);
    }
}
