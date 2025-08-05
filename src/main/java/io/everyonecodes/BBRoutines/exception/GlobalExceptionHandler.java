package io.everyonecodes.BBRoutines.exception;

import io.everyonecodes.BBRoutines.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

// @RestControllerAdvice combines @ControllerAdvice and @ResponseBody.
// It tells Spring to apply this advice to all classes annotated with @RestController.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // This method will be called whenever an IllegalArgumentException is thrown from any controller.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Log the exception for debugging purposes.
        logger.warn("Bad request received: {}", ex.getMessage());

        // Create our custom error DTO.
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), LocalDateTime.now());

        // Return a ResponseEntity with the DTO as the body and a 404 NOT_FOUND status.
        // We choose 404 because our service throws this exception for "not found" scenarios.
        // For other validation errors, 400 BAD_REQUEST might be more appropriate.
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    // This handler will now specifically catch your business rule violations.
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleIllegalStateException(IllegalStateException ex) {
        logger.warn("Business rule conflict: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), LocalDateTime.now());
        // 409 is the correct status for a state conflict.
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }
    // ----------------------------

    // This is a "catch-all" handler for any other unexpected exceptions.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        // CRITICAL: For unexpected errors, log the full stack trace to help with debugging.
        logger.error("An unexpected error occurred", ex);

        // Create a generic, user-friendly error message.
        // Never expose internal exception details (like a NullPointerException) to the client.
        ErrorDto errorDto = new ErrorDto("An internal server error occurred.", LocalDateTime.now());

        // Return a ResponseEntity with a 500 INTERNAL_SERVER_ERROR status.
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}