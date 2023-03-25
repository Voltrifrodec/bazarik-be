package sk.umb.dvestodola.bazarik.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(LibraryApplicationException.class)
    public ResponseEntity<Map<String, String>> handleException(LibraryApplicationException libraryApplicationException) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", libraryApplicationException.getMessage());
        return ResponseEntity.status(400)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException validationException) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : validationException.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(400)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }
}
