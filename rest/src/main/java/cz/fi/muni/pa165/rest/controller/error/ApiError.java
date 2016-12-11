package cz.fi.muni.pa165.rest.controller.error;

import java.util.List;

/**
 * Represents a possible representation of errors to be used
 * with @ControllerAdvice global exception handler
 *
 * @author brossi
 */
public class ApiError {

    private List<String> errors;

    public ApiError() {
    }

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
