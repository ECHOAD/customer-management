package interfaces.dto;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
        String message,
        int status,
        LocalDateTime timestamp
) {
}
