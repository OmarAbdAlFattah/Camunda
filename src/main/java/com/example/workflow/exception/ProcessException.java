package com.example.workflow.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessException extends Exception{

    private static final long serialVersionUID = 1L;

    private String category;
    private String code;
    private String message;
    private HttpStatus httpCode;

    public ProcessException(String category, String code, String message) {
        this.category = category;
        this.code = code;
        this.message = message;
        this.httpCode =HttpStatus.BAD_REQUEST ;
    }

}
