package com.example.diplomavalidation.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity invalidField(HttpMessageNotReadableException e, HttpServletRequest httpServletRequest) {
        if (e.getMessage().contains("Cannot construct instance of")) {
            return invalidNestedEntity((MismatchedInputException) e.getCause(), httpServletRequest);
        } else if (e.getMessage().contains("JSON parse error")) {
            try {
                return invalidField((JsonMappingException) e.getCause(), httpServletRequest);
            } catch (Exception exception) {
            }
        }
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Invalid Body",
                System.currentTimeMillis(), httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(err);
    }

    private ResponseEntity<StandardError> invalidField(JsonMappingException e, HttpServletRequest httpServletRequest) {
        String invalidProperty = e.getPath().get(0).getFieldName();
        String valueProvided = ((InvalidFormatException) e).getValue().toString();
        String targetType = ((InvalidFormatException) e).getTargetType().getSimpleName();
        String errorMessage = "Value of " + invalidProperty + ": " + valueProvided + " cannot be cast to " + targetType;
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), errorMessage,
                System.currentTimeMillis(), httpServletRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    private ResponseEntity invalidNestedEntity(MismatchedInputException e, HttpServletRequest httpServletRequest) {
        ParseNestedObjectException err = new ParseNestedObjectException(HttpStatus.BAD_REQUEST.value(), "Invalid Body", new ArrayList(),
                System.currentTimeMillis(), httpServletRequest.getRequestURI());
        String errorField = e.getPath().get(0).getFieldName();
        List<Field> fields = null;
        try {
            fields = Arrays.asList(Class.forName(e.getTargetType().getName()).getDeclaredFields());
        } catch (ClassNotFoundException classNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
        List<ParamRequirements> paramRequirements = new ArrayList<>();
        outerloop:
        for (Field field : fields) {
            for (Annotation ann : field.getDeclaredAnnotations()) {
                Class annotationType = ann.annotationType();
                if (Arrays.asList(NotBlank.class, NotNull.class, Min.class, Max.class).contains(annotationType)) {
                    paramRequirements.add(new ParamRequirements(errorField + "." + field.getName()
                            , field.getType().getSimpleName(), true));
                    continue outerloop;
                }
            }
            paramRequirements.add(new ParamRequirements(errorField + "." + field.getName()
                    , field.getType().getSimpleName(), false));
        }
        ;
        err.setMessage("Invalid value for field " + errorField);
        err.setParams(paramRequirements);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e,
                                                    HttpServletRequest httpServletRequest) {
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
                "Validation error", System.currentTimeMillis(), httpServletRequest.getRequestURI());
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
