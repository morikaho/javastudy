package raisetech.studentManagement.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleValidationException(ConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Validation error: " + e.getMessage());
  }
}
