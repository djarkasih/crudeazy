/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import id.djarkasih.crudeazy.error.RestifierException;
import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.model.Envelope;
import id.djarkasih.crudeazy.repository.SpecificationBuilder;
import id.djarkasih.crudeazy.service.CrudService;
import id.djarkasih.crudeazy.repository.SearchCriteria;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahmad
 */
@RestController
@RequestMapping(value="/config")
public class DatabaseManager {
    
    private final static Set<String> EDITABLE_KEYS = Set.of("name","driverName","url","username","password");
    private static final String DATABASE_PATH = "/databases";

    @Autowired
    private Logger logger;
    
    private final GenericCrudController<Database,Long> crudController;
    
    private final SpecificationBuilder<Database> specbld = new SpecificationBuilder();

    @Autowired
    public DatabaseManager(CrudService<Database,Long> dbService) {
        logger.info("There are " + dbService.count() + " databases");
        this.crudController = new GenericCrudController(Database.class,dbService,EDITABLE_KEYS);
    }
    
    @GetMapping(value=DATABASE_PATH + "/size")
    public ResponseEntity<Envelope> countDatabase() {
                
        return new ResponseEntity(crudController.count(),HttpStatus.OK);
        
    }
    
    @PostMapping(value=DATABASE_PATH)
    public ResponseEntity<Envelope> createDatabase(@RequestBody Map<String,Object> inp) throws RestifierException {
                
        return new ResponseEntity(crudController.Create(inp),HttpStatus.CREATED);
        
    }
    
    @GetMapping(value=DATABASE_PATH + "/{dbName}")
    public ResponseEntity<Envelope> findDatabase(@PathVariable("dbName") String dbName) throws RestifierException {
        
        Specification spec = specbld.with(new SearchCriteria(
            "name",
            "=",
            dbName
        ));
        
        return new ResponseEntity(crudController.findRecord(spec),HttpStatus.OK);
        
    }
    
    @GetMapping(value=DATABASE_PATH)
    public ResponseEntity<Envelope> getAll() {
        
        return new ResponseEntity(crudController.getAll(),HttpStatus.OK);
    
    }
    
    @PutMapping(value=DATABASE_PATH + "/{dbName}")
    public ResponseEntity<Envelope> updateDatabase(
           @RequestBody Map<String,Object> inp,
           @PathVariable("dbName") String dbName) throws RestifierException {
        
        Specification spec = specbld.with(new SearchCriteria(
            "name",
            "=",
            dbName
        ));
        
        return new ResponseEntity(crudController.updateRecord(spec, inp),HttpStatus.OK);

    }
    
    @DeleteMapping(value=DATABASE_PATH + "/{dbName}")
    public ResponseEntity<Envelope> deleteDatabase(
           @PathVariable("dbName") String dbName) throws RestifierException {
        
        Specification spec = specbld.with(new SearchCriteria(
            "name",
            "=",
            dbName
        ));
                
        return new ResponseEntity(crudController.deleteRecord(spec),HttpStatus.OK);
        
    }

}
