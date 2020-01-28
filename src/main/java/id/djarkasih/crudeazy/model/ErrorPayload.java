/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

/**
 *
 * @author ahmad
 */
public class ErrorPayload {
    private final String path;
    private final String method;
    private final String exceptionName;
    @JsonInclude(Include.NON_NULL)
    private final String exceptionMessage;
    @JsonInclude(Include.NON_NULL)
    private String cause = null;
    @JsonInclude(Include.NON_NULL)
    private List<FieldViolation> violations;

    public ErrorPayload(String path, String method, String exceptionName) {
        this.path = path;
        this.method = method;
        this.exceptionName = exceptionName;
        this.exceptionMessage = null;
    }

    public ErrorPayload(String path, String method, String exceptionName, String exceptionMessage) {
        this.path = path;
        this.method = method;
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getCause() {
        return cause;
    }

    public List<FieldViolation> getViolations() {
        return violations;
    }

    public void setViolations(List<FieldViolation> violations) {
        this.violations = violations;
    }

}
