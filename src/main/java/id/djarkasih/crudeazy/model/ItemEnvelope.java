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
public class ItemEnvelope extends Envelope{
    
    private Object payload;

    public ItemEnvelope() {
        super();
    }

    public ItemEnvelope(boolean success, int code, String message) {
        super(success, code);
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
    
}
