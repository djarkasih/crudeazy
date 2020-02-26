/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.repository;

import id.djarkasih.crudeazy.model.domain.Collection;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ahmad
 */
public interface CollectionRepository extends CrudRepository<Collection,Long>, JpaSpecificationExecutor<Collection> {
    
    public boolean existsByDatabaseIdAndName(Long databaseId, String name);
    
}
