/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

import javax.validation.ConstraintViolation;

/**
 *
 * @author ahmad
 */
public class FieldViolation {
    
    public static FieldViolation of(ConstraintViolation<?> violation) {
    
        String field = violation.getPropertyPath().toString();
        int pos = field.lastIndexOf(".");
        if ((pos >=0) && (pos <= field.length())) {
            field = field.substring(pos+1);
        }
        
        return new FieldViolation(
            field,
            violation.getMessage()
        );      
        
    }
    
    private final String field;
    private final String message;

    public FieldViolation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
    
}
