/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.model.domain.Database;
import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author ahmad
 */
@Validated
public interface DatabaseConfig {
    
    public Database save(@Valid Database inp);
    public Database find(String name);
    public List<Database> readAll();
    public void Delete(@Valid Database inp);
    
}
