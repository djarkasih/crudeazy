/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.util;

/**
 *
 * @author ahmad
 */
public class JsonEnvelope extends Envelope {
    
    private MultiplePayload payload;

    public JsonEnvelope() {
        super();
    }

    public JsonEnvelope(boolean success, int code, String message) {
        super(success, code, message);
    }

    public MultiplePayload getPayload() {
        return payload;
    }

    public void setPayload(MultiplePayload payload) {
        this.payload = payload;
    }
    
}
