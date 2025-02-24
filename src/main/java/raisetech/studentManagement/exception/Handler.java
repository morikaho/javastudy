package raisetech.studentManagement.exception;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class Handler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(
      ConstraintViolationException e, WebRequest request) {
    Map<String, Object> errorResponse = new LinkedHashMap<>();
    String errorMessages = e.getConstraintViolations().stream()
        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
        .collect(Collectors.joining(", "));

    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", 400);
    errorResponse.put("error", "Bad Request");
    errorResponse.put("message", errorMessages);
    errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(TestException.class)
  public ResponseEntity<Map<String, Object>> handleTestException(TestException ex,
      WebRequest request) {
    Map<String, Object> errorResponse = new LinkedHashMap<>();

    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", 400);
    errorResponse.put("error", "Bad Request");
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}
