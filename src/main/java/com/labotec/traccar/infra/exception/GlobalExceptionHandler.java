package com.labotec.traccar.infra.exception;

import com.labotec.traccar.infra.common.ApiError;
import com.labotec.traccar.infra.web.controller.rest.traccar.exception.Unauthorised;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Leer la propiedad desde application.properties
    @Value("${app.debug.enabled:false}")
    private boolean debugEnabled;

    private static final String NO_DEBUG_MESSAGE = "Details not available";

    // Maneja la excepción personalizada EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                debugMessage,
                Map.of("error", ex.getMessage())  // Error único
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // Maneja los errores de validación (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        // Crear un mapa de errores donde la clave es el campo y el valor es el mensaje de error
        Map<String, String> subErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,        // El nombre del campo que produjo el error
                        FieldError::getDefaultMessage // El mensaje de error asociado a ese campo
                ));

        // Mensaje de depuración opcional
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        // Crear un ApiError con los subErrores de validación
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                debugMessage,
                subErrors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // Maneja HttpRequestMethodNotSupportedException (método no permitido)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        // Mensaje de depuración opcional
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        ApiError apiError = new ApiError(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Method not allowed",
                debugMessage,
                Map.of("ERROR" , ex.getMessage()) // No hay subErrores en este caso
        );

        return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Maneja cualquier otra excepción general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        // Mensaje de depuración opcional
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                debugMessage,
                Map.of("ERROR", ex.getMessage())
        );
        System.out.println(ex.getMessage());
        System.out.println(ex.getLocalizedMessage());
        System.out.println(ex.getCause().toString());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        // Extraer mensaje de depuración si está habilitado
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        // Mensaje amigable para el usuario
        String userMessage = "Invalid JSON format or invalid data provided.";

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                userMessage,
                debugMessage,
                Map.of("error", "Malformed JSON or invalid value provided.") // Suberror detallado
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Extraer el mensaje relevante de la excepción
        String rootCauseMessage = getRootCause(ex).getMessage();

        // Verificar si es una violación de unicidad y proporcionar un mensaje claro
        String userMessage = "A record with the same value already exists.";
        if (rootCauseMessage.contains("Duplicate entry")) {
            userMessage = "Duplicate entry detected: " + extractDuplicateEntryInfo(rootCauseMessage);
        }

        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT,
                userMessage,
                rootCauseMessage,
                Map.of("error", rootCauseMessage) // Proporciona el error completo si es necesario para depuración
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(Unauthorised.class)
    public ResponseEntity<ApiError> handleNoAuthorized(Unauthorised ex, WebRequest request){
        // Mensaje de depuración opcional
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        // Mensaje amigable para el usuario
        String userMessage = "The requested no authorized";

        // Crear un ApiError con los detalles del error
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                userMessage,
                debugMessage,
                Map.of("error", "Resource bad request: " + request.getDescription(false)) // Información adicional si es necesario
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        // Mensaje de depuración opcional
        String debugMessage = debugEnabled ? ex.getLocalizedMessage() : null;

        // Mensaje amigable para el usuario
        String userMessage = "The requested resource could not be found.";

        // Crear un ApiError con los detalles del error
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                userMessage,
                debugMessage,
                Map.of("error", "Resource not found: " + request.getDescription(false)) // Información adicional si es necesario
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // Método auxiliar para extraer la causa raíz de la excepción
    private Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause != rootCause.getCause()) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    // Método auxiliar para extraer información relevante del mensaje de error duplicado
    private String extractDuplicateEntryInfo(String message) {
        // Extraer la información de la entrada duplicada del mensaje de error
        int start = message.indexOf("Duplicate entry") + 16;
        int end = message.indexOf("for key");
        return message.substring(start, end).trim();
    }
}
