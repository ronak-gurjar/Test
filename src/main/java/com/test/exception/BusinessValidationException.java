package com.test.exception;

import java.io.Serial;
import java.util.List;

public class BusinessValidationException extends EndUserException {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> errors;

    private String[] params;

    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(String message, String... params) {
        super(message);
        this.params = params;
    }

    public BusinessValidationException(Exception e) {
        super(e);
    }


    /**
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    public Object[] getParams() {
        return params;
    }

}
