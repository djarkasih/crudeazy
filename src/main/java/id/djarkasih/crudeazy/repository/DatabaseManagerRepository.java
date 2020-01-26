/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.repository;

import id.djarkasih.crudeazy.model.DatabaseManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ahmad
 */
public interface DatabaseManagerRepository extends CrudRepository<DatabaseManager,Long> {
    
    @Query(value ="select count(*) from collection", nativeQuery=true)
    long numberOfCollection();
    
    @Query(value ="select count(*) from database", nativeQuery=true)
    long numberOfDatabase();
    
    DatabaseManager findByName(String name);
    
}
