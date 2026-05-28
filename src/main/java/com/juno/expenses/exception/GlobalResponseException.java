package com.juno.expenses.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalResponseException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(
            ResponseStatusException e,
            HttpServletRequest request
    ) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                e.getStatusCode().value(),
                e.getStatusCode().toString(),
                e.getReason(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(e.getStatusCode())
                .body(error);
    }
}
