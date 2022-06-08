package com.mikaeru.financialmanager.shared.exceptions;

public class DuplicatedDataException extends DomainException {
    public DuplicatedDataException(Error invalidDuplicatedData) {
        super(invalidDuplicatedData);
    }
}
