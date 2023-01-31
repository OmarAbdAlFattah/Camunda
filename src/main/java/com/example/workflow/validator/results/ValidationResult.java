package com.example.workflow.validator.results;

import com.example.workflow.validator.enums.ValidationMessageType;
import com.example.workflow.validator.enums.ValidationStatus;

public interface ValidationResult {
    ValidationMessageType getType();

    String getCode();

    String getCategory();

    String getMessage();

    ValidationStatus getStatus();
}
