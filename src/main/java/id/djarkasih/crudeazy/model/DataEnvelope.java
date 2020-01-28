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
public class DataEnvelope extends Envelope {
    
    private MultiplePayload payload;

    public DataEnvelope() {
        super();
    }

    public DataEnvelope(boolean success, int code, String message) {
        super(success, code, message);
    }

    public MultiplePayload getPayload() {
        return payload;
    }

    public void setPayload(MultiplePayload payload) {
        this.payload = payload;
    }
    
}
