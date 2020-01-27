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
public class ObjectEnvelope extends Envelope{
    
    private Object payload;

    public ObjectEnvelope() {
        super();
    }

    public ObjectEnvelope(boolean success, int code, String message) {
        super(success, code, message);
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
    
}
