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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DuplicatedDataException.class})
    protected ResponseEntity<Object> handleDuplicatedDataException(DuplicatedDataException exception, WebRequest request) {
        List<Problem.Field> problemFields = new ArrayList<>();

        for (int i = 0; i < exception.getFields().size(); i++) {
            Problem.Field field = new Problem.Field(exception.getFields().get(i), exception.getMessage());
            problemFields.add(field);
        }

        Problem problem = new Problem(exception.getMessage(), BAD_REQUEST.value(), problemFields, OffsetDateTime.now());

        return handleExceptionInternal(exception, problem, new HttpHeaders(), BAD_REQUEST, request);
    }
}
