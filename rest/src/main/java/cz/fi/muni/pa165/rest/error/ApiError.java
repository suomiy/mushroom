package cz.fi.muni.pa165.rest.error;

import java.util.List;

/**
 * Represents a possible representation of errors to be used
 * with @ControllerAdvice global exception handler
 *
 * @author Filip Krepinsky (410022)
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
