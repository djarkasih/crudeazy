/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ahmad
 */
public class DataPayload {

    private List<Map<String, Object>> data;

    public DataPayload() {
        this.data = new ArrayList();
    }

    public long getSize() {
        return data.size();
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
    
}
