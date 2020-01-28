/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.djarkasih.crudeazy.error.DataIncomplete;
import id.djarkasih.crudeazy.error.DataNotFound;
import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.service.DatabaseConfig;
import id.djarkasih.crudeazy.model.Envelope;
import id.djarkasih.crudeazy.model.DataEnvelope;
import id.djarkasih.crudeazy.util.MapUtils;
import id.djarkasih.crudeazy.model.MultiplePayload;
import id.djarkasih.crudeazy.model.ItemEnvelope;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class DatabaseEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseEndpoint.class);
    
    private final static Set<String> POST_KEYS = Set.of("name","driverName","url","username","password");
    private final static Set<String> PATCH_KEYS = Set.of("driverName","url","username","password");
    
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    @Qualifier("dbCfgJpaImpl")
    private DatabaseConfig dbcfg;
    
    @PostMapping(value="/databases")
    public ResponseEntity<Envelope> createDatabase(@RequestBody Map<String,Object> inp) throws DataIncomplete {
        
        Set<String> missingFields = MapUtils.getMissingFields(inp, POST_KEYS);
        if (missingFields.size() > 0) {
            throw new DataIncomplete("Missing field(s) : " + missingFields.toString() + ".");
        }
        
        Database indb = mapper.convertValue(inp, Database.class);
        
        Database outdb = dbcfg.save(indb);
        
        ItemEnvelope env = new ItemEnvelope(true, HttpStatus.CREATED.value(),"Database configuration created.");
        env.setPayload(outdb);
        
        return new ResponseEntity(env,HttpStatus.CREATED);
        
    }
    
    @GetMapping(value="/databases/{dbName}")
    public ResponseEntity<Envelope> findDatabase(@PathVariable("dbName") String dbName) throws DataNotFound {
        
        ItemEnvelope env = null;
        
        Database db = dbcfg.find(dbName);
        if (db == null) {
            throw new DataNotFound("Could not found " + dbName + " configuration database.");
        } else {
            env = new ItemEnvelope(true, HttpStatus.OK.value(),"Database configuration found.");
        }
        
        env.setPayload(db);
        
        return new ResponseEntity(env,HttpStatus.valueOf(env.getCode()));
    }
    
    @GetMapping(value="/databases")
    public ResponseEntity<Envelope> getAll() {

        List<Database> dbs = dbcfg.readAll();
        if (dbs == null) {
            
        }
        
        MultiplePayload payload = new MultiplePayload();
        List<Map<String,Object>> data = mapper.convertValue(dbs, List.class);
        payload.setData(data);
                
        DataEnvelope env = new DataEnvelope(true, HttpStatus.OK.value(),payload.getSize() + " database configuration(s) found.");
        env.setPayload(payload);
        
        return new ResponseEntity(env,HttpStatus.OK);
    }
    
    @PutMapping(value="/databases/{dbName}")
    public ResponseEntity<Envelope> updateDatabase(
           @RequestBody Map<String,Object> inp,
           @PathVariable("dbName") String dbName) throws DataNotFound, DataIncomplete {
        
        Database indb = dbcfg.find(dbName);
        if (indb == null) {
            throw new DataNotFound("Could not found " + dbName + " configuration database.");
        }
        
        Set<String> changedKeys = MapUtils.getPresentFields(inp, PATCH_KEYS);
        if (changedKeys.isEmpty()) {
            throw new DataIncomplete("Missing at least one of these following fields : " + PATCH_KEYS.toString());
        }
        
        Map<String,Object> changedValues = MapUtils.copy(inp, changedKeys);
        
        Database outdb = null;
        try {
            BeanUtils.populate(indb, changedValues);
            outdb = dbcfg.save(indb);
        } catch (IllegalAccessException | InvocationTargetException ex) {
        }

        ItemEnvelope env = new ItemEnvelope(true, HttpStatus.OK.value(),"Database configuration updated.");
        env.setPayload(outdb);
        
        return new ResponseEntity(env,HttpStatus.OK);
    }
    
    @DeleteMapping(value="/databases/{dbName}")
    public ResponseEntity<Envelope> deleteDatabase(
           @PathVariable("dbName") String dbName) throws DataNotFound {
        
        ItemEnvelope env = null;
        
        Database indb = dbcfg.find(dbName);
        if (indb == null) {
            throw new DataNotFound("Could not found " + dbName + " configuration database.");
        }
        
        dbcfg.Delete(indb);
        
        Database outdb = dbcfg.find(dbName);
        if (outdb == null) {
            env = new ItemEnvelope(false, HttpStatus.OK.value(),"Database(" +  dbName + ")configuration removed.");
        } else {
            env = new ItemEnvelope(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),"Could not remove Database(" +  dbName + ")configuration.");
        }

        env.setPayload(indb);
        
        return new ResponseEntity(env,HttpStatus.valueOf(env.getCode()));
    }

}
