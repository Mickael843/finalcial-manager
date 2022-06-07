package com.mikaeru.financialmanager.shared;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({DuplicatedDataException.class})
//    protected ResponseEntity<Object> handleDuplicatedDataException(DuplicatedDataException exception, WebRequest request) {
//        List<Problem.Field> problemFields = new ArrayList<>();
//
//        for (int i = 0; i < exception.getFields().size(); i++) {
//            Problem.Field field = new Problem.Field(exception.getFields().get(i), exception.getMessage());
//            problemFields.add(field);
//        }
//
//        Problem problem = new Problem(exception.getMessage(), BAD_REQUEST.value(), problemFields, OffsetDateTime.now());
//
//        return handleExceptionInternal(exception, problem, new HttpHeaders(), BAD_REQUEST, request);
//    }
}
