package com.example.workflow.process;

import lombok.Data;

@Data
public class ContextObject<T> {
    private final T value;
}