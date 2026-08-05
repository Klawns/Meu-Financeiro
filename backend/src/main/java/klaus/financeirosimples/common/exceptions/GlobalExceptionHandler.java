package klaus.financeirosimples.common.exceptions;

import klaus.financeirosimples.auth.application.exceptions.InvalidCredentialsException;
import klaus.financeirosimples.auth.infra.jwt.JwtGenerationException;
import klaus.financeirosimples.common.vo.ErrorResponse;
import klaus.financeirosimples.transactions.application.exceptions.TransactionNotFoundException;
import klaus.financeirosimples.transactions.application.exceptions.UnsupportedCurrencyException;
import klaus.financeirosimples.user.application.exceptions.UserAlreadyExists;
import klaus.financeirosimples.user.application.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Unexpected error", e);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred. Please try again later."
        );
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e) {
        log.warn("Domain exception: {}", e.getMessage());

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                "The request could not be processed."
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                "Please verify your email and password and try again."
        );
    }

    @ExceptionHandler(JwtGenerationException.class)
    public ResponseEntity<ErrorResponse> handleJwtGenerationException(JwtGenerationException e) {
        log.error("Error generating JWT", e);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "JWT Generation Error",
                "An error occurred while generating the authentication token."
        );
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException e) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                "Transaction not found."
        );
    }

    @ExceptionHandler(UnsupportedCurrencyException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedCurrencyException(UnsupportedCurrencyException e) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                "Unsupported currency."
        );
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExists e) {
        return buildResponse(
                HttpStatus.CONFLICT,
                e.getMessage(),
                "User already exists."
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                "User not found."
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            String details) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                message,
                details
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}