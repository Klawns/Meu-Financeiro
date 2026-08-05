package klaus.financeirosimples.common.vo;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String message, String details) {
}
