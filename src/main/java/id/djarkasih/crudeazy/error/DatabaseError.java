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
public class DatabaseError extends Exception {

    public DatabaseError(String message) {
        super(message);
    }

}
