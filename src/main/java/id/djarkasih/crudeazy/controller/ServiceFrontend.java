/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import id.djarkasih.crudeazy.error.RestifierException;
import id.djarkasih.crudeazy.model.DataEnvelope;
import id.djarkasih.crudeazy.model.Envelope;
import id.djarkasih.crudeazy.model.PagedPayload;
import id.djarkasih.crudeazy.service.Restifier;
import static id.djarkasih.crudeazy.service.Restifier.COLLECTION_NAME_KEY;
import static id.djarkasih.crudeazy.service.Restifier.RESTIFIER_KEY;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahmad
 */
@RestController
public class ServiceFrontend {
    
    @Value("${crudeazy.pageSize}")
    private int defaultPageSize;
    
    private ResponseEntity<Envelope> buildSimpleEntity(boolean success, HttpStatus status, Object payload) {
                
        DataEnvelope env = new DataEnvelope(success, status.value());
        env.setPayload(payload);
        
        return new ResponseEntity(env,status);
        
    }
    
    @GetMapping(value="/{dbName}/{aliasName}/count")
    public ResponseEntity<Envelope> count(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @RequestAttribute(COLLECTION_NAME_KEY) String collName,
        @RequestParam MultiValueMap<String,String> params) throws RestifierException {
        
        long count = restifier.count(collName, params);
        
        return buildSimpleEntity(
            true,
            HttpStatus.OK,
            Map.of(
                "count", count,
                "info", "There are " + count + " matched rows in " + collName + ".")
        );
        
    }
    
    @PostMapping(value="/{dbName}/{aliasName}")
    public ResponseEntity<Envelope> create(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @RequestAttribute(COLLECTION_NAME_KEY) String collName,
        @RequestBody Map<String,String> inp) throws RestifierException {
        
        boolean succeed = restifier.create(collName, inp);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String,Object> payload = new HashMap();
        
        if (succeed) {

            status = HttpStatus.CREATED;
            payload.put("info", collName + " entity created.");
            
        } else {
            
            payload.put("info", "Failed to create " + collName + " entity.");
            
        } 
        
        return buildSimpleEntity(succeed,status,payload);
        
    }
    
    @GetMapping(value="/{dbName}/{aliasName}")
    public ResponseEntity<Envelope> findAll ( 
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @RequestAttribute(COLLECTION_NAME_KEY) String collName,
        @RequestParam(name=Restifier.PAGE_NO_KEY,defaultValue="1") int pageNo,
        @RequestParam(name=Restifier.PAGE_SIZE_KEY,defaultValue="-1") int pageSize,
        @RequestParam MultiValueMap<String,String> params) throws RestifierException {
        
        if (pageSize == -1) pageSize = this.defaultPageSize;
        
        List<Map<String,Object>> data = restifier.findAll(collName, params); 
        
        PagedPayload payload = new PagedPayload(pageNo,pageSize);
        payload.setData(data);

        return buildSimpleEntity(data != null,data != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR,payload);
        
    }
    
    @PutMapping(value="/{dbName}/{aliasName}")
    public ResponseEntity<Envelope> update(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @RequestAttribute(COLLECTION_NAME_KEY) String collName,
        @RequestParam MultiValueMap<String,String> params,
        @RequestBody Map<String,String> inp) throws RestifierException {
        
        int affectedRows = restifier.update(collName, inp, params);
        
        return buildSimpleEntity(
            affectedRows > 0,
            affectedRows > 0 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR,
            Map.of(
                "affectedRows", affectedRows,
                "info", "There are " + affectedRows + " rows in " + collName + " updated.")
        );
        
    }
    
    @DeleteMapping(value="/{dbName}/{aliasName}")
    public ResponseEntity<Envelope> delete(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @RequestAttribute(COLLECTION_NAME_KEY) String collName,
        @RequestParam MultiValueMap<String,String> params) throws RestifierException {
        
        int affectedRows = restifier.delete(collName, params);
        
        return buildSimpleEntity(
            affectedRows > 0,
            affectedRows > 0 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR,
            Map.of(
                "affectedRows", affectedRows,
                "info", "There are " + affectedRows + " rows in " + collName + " removed.")
        );
        
    }

}
