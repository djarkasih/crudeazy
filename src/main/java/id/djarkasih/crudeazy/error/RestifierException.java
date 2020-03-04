/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.error;

/**
 *
 * @author ahmad
 */
public class RestifierException extends Exception {
    
    private final RestifierError errorType;

    public RestifierException(RestifierError errorType) {
        super(errorType.toString());
        this.errorType = errorType;
    }

    public RestifierException(RestifierError errorType, String message) {
        super("[" + errorType.toString() + "] " + message);
        this.errorType = errorType;
    }

    public RestifierException(RestifierError errorType, Throwable cause) {
        super(errorType.toString(),cause);
        this.errorType = errorType;
    }

    public RestifierError getErrorType() {
        return errorType;
    }
    
}
