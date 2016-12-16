package cz.fi.muni.pa165.rest.error;

import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Filip Krepinsky (410022) on 12/10/16
 */

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiError handleException(JpaSystemException x) {
        ApiError apiError = new ApiError();
        List<String> errors = new ArrayList<>();
        for (Throwable cause = x.getCause(); cause != null; cause = cause.getCause()) {
            if (cause instanceof ConstraintViolationException) {
                String errorCode = ((ConstraintViolationException) cause).getSQLState();

                ConstraintViolation violation = ConstraintViolation.fromErrorCode(errorCode);
                if (violation != null) {
                    errors.add(violation.getMessage());
                }
            }
        }

        apiError.setErrors(errors);
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiError handleException(javax.validation.ConstraintViolationException x) {
        ApiError apiError = new ApiError();
        List<String> errors = x.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s %s", violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.toList());

        apiError.setErrors(errors);
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleException(HttpMessageConversionException x) { // otherwise it would get classified as RuntimeException
        throw x;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleException(MethodArgumentTypeMismatchException x) { // otherwise it would get classified as RuntimeException
        throw x;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void handleException(HunterAuthenticationException x) {
        throw x;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ApiError handleException(RuntimeException x) {
        String error = "Internal Error occurred";
        ApiError apiError = new ApiError();
        List<String> errors = new ArrayList<>();
        if (x instanceof InvalidDataAccessApiUsageException) {
            error = String.format("%s: %s", error, x.getMessage());
        }

        errors.add(error);

        apiError.setErrors(errors);
        return apiError;
    }
}
