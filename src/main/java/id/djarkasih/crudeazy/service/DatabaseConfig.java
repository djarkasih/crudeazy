/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.model.Database;
import java.util.List;

/**
 *
 * @author ahmad
 */
public interface DatabaseConfig {
    
    public Database save(Database inp);
    public Database find(String name);
    public List<Database> readAll();
    public void Delete(Database inp);
    
}
