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

    public RestifierException(RestifierError error) {
        super(error.toString());
    }

    public RestifierException(RestifierError error, String message) {
        super("[" + error.toString() + "] " + message);
    }
    
}
