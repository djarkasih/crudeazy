/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

/**
 *
 * @author ahmad
 */
public class ErrorEnvelope extends Envelope {
    
    private ErrorPayload exceptions;

    public ErrorEnvelope(int code, String message) {
        super(false, code, message);
    }

    public ErrorPayload getExceptions() {
        return exceptions;
    }

    public void setExceptions(ErrorPayload exceptions) {
        this.exceptions = exceptions;
    }

}
