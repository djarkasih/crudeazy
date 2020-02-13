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
public class SearchCriteria {
    
    private final String field;
    private final String operation;
    private final Object value;

    public SearchCriteria(String field, String operation, Object value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

}
