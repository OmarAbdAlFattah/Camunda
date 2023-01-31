package com.example.workflow.validator.results;

import com.example.workflow.validator.enums.ValidationMessageType;
import com.example.workflow.validator.enums.ValidationStatus;
import lombok.NonNull;

public class ValidationResultImpl implements ValidationResult{
    @NonNull
    private final ValidationMessageType type;

    private final String code;

    private final String category;

    private final String message;

    public ValidationResultImpl(@NonNull ValidationMessageType type, String code, String category, String message) {
        this.type = type;
        this.code = code;
        this.category = category;
        this.message = message;
    }

    @Override
    public ValidationMessageType getType() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public ValidationStatus getStatus() {
        return null;
    }
}
