package com.juno.expenses.exception;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int StatusCode,
        String Error,
        String Message,
        String Path,
        LocalDateTime Timestamp
) {
}
