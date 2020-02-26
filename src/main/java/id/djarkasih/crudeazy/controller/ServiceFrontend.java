/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import id.djarkasih.crudeazy.service.Restifier;
import static id.djarkasih.crudeazy.service.Restifier.RESTIFIER_KEY;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private static final Logger logger = LoggerFactory.getLogger(ServiceFrontend.class);
    
    @GetMapping(value="/{dbName}/{collName}/count")
    public long count(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @PathVariable("collName") String collName,
        @RequestParam MultiValueMap<String,String> params) {
                
        return restifier.count(collName,params);
        
    }
    
    @PostMapping(value="/{dbName}/{collName}")
    public boolean create(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @PathVariable("collName") String collName,
        @RequestBody Map<String,String> inp) {
        
        return restifier.create(collName, inp);
        
    }
    
    @GetMapping(value="/{dbName}/{collName}")
    public List<Map<String,Object>> findAll(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @PathVariable("collName") String collName,
        @RequestParam(name=Restifier.PAGE_NO_KEY,defaultValue="1") int pageNo,
        @RequestParam(name=Restifier.PAGE_SIZE_KEY,defaultValue="5") int pageSize,
        @RequestParam MultiValueMap<String,String> params) {

        return restifier.findAll(collName,params);
        
    }
    
    @PutMapping(value="/{dbName}/{collName}")
    public int update(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @PathVariable("collName") String collName,
        @RequestParam MultiValueMap<String,String> params,
        @RequestBody Map<String,String> inp) {
        
        return restifier.update(collName, inp, params);
        
    }
    
    @DeleteMapping(value="/{dbName}/{collName}")
    public int delete(
        @RequestAttribute(RESTIFIER_KEY) Restifier restifier,
        @PathVariable("collName") String collName,
        @RequestParam MultiValueMap<String,String> params) {
        
        return restifier.delete(collName, params);
        
    }

}
