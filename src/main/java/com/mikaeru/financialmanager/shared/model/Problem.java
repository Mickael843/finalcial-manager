package com.mikaeru.financialmanager.shared.model;

import java.time.OffsetDateTime;
import java.util.List;

public record Problem(
        String title,
        Integer status,
        List<Field> fields,
        OffsetDateTime dateTime
) {
    public record Field(String name, String message) { }
}
