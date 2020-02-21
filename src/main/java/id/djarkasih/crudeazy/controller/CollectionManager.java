/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import id.djarkasih.crudeazy.error.DataIncomplete;
import id.djarkasih.crudeazy.error.DataNotFound;
import id.djarkasih.crudeazy.error.DatabaseError;
import id.djarkasih.crudeazy.model.Envelope;
import id.djarkasih.crudeazy.model.domain.Collection;
import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.repository.SearchCriteria;
import id.djarkasih.crudeazy.repository.SpecificationBuilder;
import id.djarkasih.crudeazy.service.CrudService;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CollectionManager {
    
    private final static Set<String> EDITABLE_KEYS = Set.of("databaseId","name");    
    private static final String COLLECTIONS_PATH = "/collections";

    private static final Logger logger = LoggerFactory.getLogger(CollectionManager.class);

    private final CrudService<Database,Long> dbService;
    
    private final GenericCrudController crudController;
    
    private final SpecificationBuilder<Collection> specbld = new SpecificationBuilder();

    @Autowired
    public CollectionManager(CrudService<Database, Long> dbService, CrudService<Collection, Long> collService) {
        this.dbService = dbService;
        this.crudController = new GenericCrudController(Collection.class,collService,EDITABLE_KEYS);
    }
    
    @GetMapping(value=COLLECTIONS_PATH + "/size")
    public ResponseEntity<Envelope> countCollections() {
                
        return new ResponseEntity(crudController.count(),HttpStatus.OK);
        
    }
    
    public void checkDatabase(Map<String,Object> rec) throws DataNotFound, NumberFormatException {
        
        Long dbId = -1l;
        
        try {            
            dbId = Long.valueOf(rec.get("databaseId").toString());
        } catch (NumberFormatException ex) {
            throw ex;
        }
        
        Database db = dbService.find(dbId);
        if (db == null) {
            throw new DataNotFound("Database (id=" + dbId + ") not found.");
        }

    }

    @PostMapping(value=COLLECTIONS_PATH)
    public ResponseEntity<Envelope> createCollection(@RequestBody Map<String,Object> inp) throws DataIncomplete, DataNotFound, NumberFormatException {
        
        checkDatabase(inp);

        return new ResponseEntity(crudController.Create(inp),HttpStatus.CREATED);
        
    }

    @GetMapping(value=COLLECTIONS_PATH + "/{collName}")
    public ResponseEntity<Envelope> findCollection(@PathVariable("collName") String collName) throws DataNotFound {
        
        Specification spec = specbld.with(new SearchCriteria(
            "name",
            "=",
            collName
        ));
        
        return new ResponseEntity(crudController.findRecord(spec),HttpStatus.OK);
        
    }

    @GetMapping(value=COLLECTIONS_PATH)
    public ResponseEntity<Envelope> getAll() {
        
        return new ResponseEntity(crudController.getAll(),HttpStatus.OK);
    
    }
    
    @PutMapping(value=COLLECTIONS_PATH + "/{collName}")
    public ResponseEntity<Envelope> updateCollection(
           @RequestBody Map<String,Object> inp,
           @PathVariable("collName") String collName) throws DataNotFound, DataIncomplete, NumberFormatException {
                
        checkDatabase(inp);
        
        Specification spec = specbld.with(new SearchCriteria(
            "name",
            "=",
            collName
        ));
        
        return new ResponseEntity(crudController.updateRecord(spec, inp),HttpStatus.OK);
    }
    
    @DeleteMapping(value=COLLECTIONS_PATH + "/{collName}")
    public ResponseEntity<Envelope> deleteCollection(
           @PathVariable("collName") String collName) throws DataNotFound, DatabaseError {
        
        Specification spec = specbld.with(new SearchCriteria(
            "name",
            "=",
            collName
        ));
                
        return new ResponseEntity(crudController.deleteRecord(spec),HttpStatus.OK);

    }

}
