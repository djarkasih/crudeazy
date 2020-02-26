/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.configs;

import id.djarkasih.crudeazy.model.domain.Collection;
import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.repository.CollectionRepository;
import id.djarkasih.crudeazy.repository.DatabaseRepository;
import id.djarkasih.crudeazy.service.CrudService;
import id.djarkasih.crudeazy.service.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ahmad
 */
@Configuration
public class JpaServicesConfiguration {
        
    @Autowired
    private DatabaseRepository dbRepo;
    
    @Autowired
    private CollectionRepository collRepo;


    @Bean
    public CrudService<Database, Long> dbService() {
        CrudService<Database, Long> service = new GenericCrudService<>(dbRepo);
        return service;
    }
    
    @Bean
    public CrudService<Collection, Long> collService() {
        CrudService<Collection, Long> service = new GenericCrudService<>(collRepo);
        return service;
    }

}
