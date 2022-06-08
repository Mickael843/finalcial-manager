package com.mikaeru.financialmanager.shared.exceptions;

import java.util.List;

public class DomainException extends RuntimeException {

    private final int code;
    private final List<String> fields;

    public DomainException(Error error) {
        super(error.message);
        code = error.code;
        fields = error.fields;
    }

    public enum Error {
        INVALID_DUPLICATED_DATA("Invalid duplicated data!", 1000);

        private final int code;
        private final String message;
        private List<String> fields;

        Error(String message, int code) {
            this.message = message;
            this.code = code;
        }

        void setFields(List<String> fields) {
            this.fields = fields;
        }

        public String getMessage() {
            return message;
        }
    }

    public int getCode() {
        return code;
    }

    public List<String> getFields() {
        return fields;
    }
}
