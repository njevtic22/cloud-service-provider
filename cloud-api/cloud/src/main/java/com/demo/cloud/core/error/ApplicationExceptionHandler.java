package com.demo.cloud.core.error;

import com.demo.cloud.core.error.exceptions.BlankStringException;
import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.core.error.exceptions.InvalidPasswordException;
import com.demo.cloud.core.error.exceptions.UniquePropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info(ex.getMessage(), ex);

        ArrayList<FieldErrorMessage> details = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String messages = fieldError.getDefaultMessage() == null ? "Message is: null" : fieldError.getDefaultMessage();
            List<String> messagesList = Arrays.asList(messages.split("\\|"));

            details.add(new FieldErrorMessage(fieldError.getField(), fieldError.getRejectedValue(), messagesList));
        }

        ApiFieldError errorBody = new ApiFieldError("Invalid field(s).", details);
        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler({
            UniquePropertyException.class,
            InvalidPasswordException.class,
            BlankStringException.class
    })
    public ResponseEntity<ApiError> handleBadRequest(RuntimeException ex) {
        logger.info(ex.getMessage(), ex);

        ApiError errorBody = new ApiError(ex.getMessage());
        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleForbidden(RuntimeException ex) {
        logger.info(ex.getMessage(), ex);

        ApiError errorBody = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex) {
        logger.info(ex.getMessage(), ex);

        ApiError errorBody = new ApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternalServer(Exception ex) {
        logger.error(ex.getMessage(), ex);

        ApiError errorBody = new ApiError(ex.getMessage());
        return ResponseEntity.internalServerError().body(errorBody);
    }
}
