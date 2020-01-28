/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.model.domain.Collection;
import java.util.List;

/**
 *
 * @author ahmad
 */
public interface CollectionConfig {
    
    public Collection save(Collection inp);
    public Collection find(String name);
    public List<Collection> readAll();
    public int Delete(Collection inp);
    
}
