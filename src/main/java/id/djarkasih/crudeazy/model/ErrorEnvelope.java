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
    
    private Object error;

    public ErrorEnvelope(int code) {
        super(false, code);
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
