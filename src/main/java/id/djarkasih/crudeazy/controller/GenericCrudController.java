/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.djarkasih.crudeazy.error.RestifierError;
import id.djarkasih.crudeazy.error.RestifierException;
import id.djarkasih.crudeazy.model.DataEnvelope;
import id.djarkasih.crudeazy.model.Envelope;
import id.djarkasih.crudeazy.model.ItemEnvelope;
import id.djarkasih.crudeazy.model.DataPayload;
import id.djarkasih.crudeazy.service.CrudService;
import id.djarkasih.crudeazy.util.MapUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ahmad
 */
public class GenericCrudController<T,ID> {
    
    //private static final Logger logger = LoggerFactory.getLogger(GenericCrudController.class);

    private final Class<T> klass;
    private final Set<String> mandatoryFields;
    private final CrudService<T,ID> crudService;
   
    private final ObjectMapper mapper = new ObjectMapper();

    public GenericCrudController(Class<T> klass, CrudService<T, ID> crudService, Set<String> mandatoryFields) {
        this.klass = klass;
        this.crudService = crudService;
        this.mandatoryFields = mandatoryFields;
    }
    
    public Envelope count() {
        
        long count = crudService.count();
        
        ItemEnvelope env = new ItemEnvelope(true, HttpStatus.OK.value(), "There are " + count + " record(s).");
        env.setPayload(count);
        
        return env;

    }
    
    public Envelope Create(Map<String,Object> rec) throws RestifierException {
        
        Set<String> missingFields = MapUtils.getMissingFields(rec, this.mandatoryFields);
        if (missingFields.size() > 0) {
            throw new RestifierException(RestifierError.DATA_INCOMPLETE,"Missing field(s) : " + missingFields.toString() + ".");
        }
        
        T inpObj = mapper.convertValue(rec,this.klass);
        T outObj = this.crudService.save(inpObj);

        ItemEnvelope env = new ItemEnvelope(true, HttpStatus.CREATED.value(),this.klass.getSimpleName() + " entity created.");
        env.setPayload(outObj);
        
        return env;
    }
    
    public Envelope findRecord(Specification spec) throws RestifierException {
        
        ItemEnvelope env = null;
        
        T obj = crudService.findMatch(spec);
        if (obj == null) {
            throw new RestifierException(RestifierError.DATA_NOT_FOUND, "Could not found " + klass.getSimpleName() + " entity.");
        } else {
            env = new ItemEnvelope(true, HttpStatus.OK.value(),klass.getSimpleName() + " entity found.");
            env.setPayload(obj);
        }
        
        return env;
        
    } 
    
    public Envelope getAll() {
        
        List<T> objs = crudService.readAll();
        if (objs == null) {
            objs = new ArrayList();
        }
        
        DataPayload payload = new DataPayload();
        List<Map<String,Object>> data = mapper.convertValue(objs, List.class);
        payload.setData(data);
                
        DataEnvelope env = new DataEnvelope(true, HttpStatus.OK.value());
        env.setPayload(payload);
        
        return env;
        
    }
    
    public Envelope updateRecord(Specification spec, Map<String,Object> obj) throws RestifierException {
        
        T inObj = crudService.findMatch(spec);
        if (inObj == null) {
            throw new RestifierException(RestifierError.DATA_NOT_FOUND,"Could not found " + klass.getSimpleName() + " entity.");
        }
        
        Set<String> changedKeys = MapUtils.getPresentFields(obj, this.mandatoryFields);
        if (changedKeys.isEmpty()) {
            throw new RestifierException(RestifierError.DATA_INCOMPLETE,"Missing at least one of these following fields : " + this.mandatoryFields.toString());
        }
        
        Map<String,Object> changedValues = MapUtils.copy(obj, changedKeys);
        
        T outObj = null;
        try {
            BeanUtils.populate(inObj, changedValues);
            outObj = crudService.save(inObj);
        } catch (IllegalAccessException | InvocationTargetException ex) {
        }

        ItemEnvelope env = new ItemEnvelope(true, HttpStatus.OK.value(), klass.getSimpleName() + " - " + changedKeys.toString() + " updated.");
        env.setPayload(outObj);
        
        return env;
    }

    
    public Envelope deleteRecord(Specification spec) throws RestifierException {
        
        ItemEnvelope env = null;
                
        if (!crudService.delete(spec)) {
            throw new RestifierException(RestifierError.DATA_NOT_FOUND,"Could not found " + klass.getSimpleName() + " entity.");            
        }
        
        T  outdb = crudService.findMatch(spec);
        if (outdb == null) {
            env = new ItemEnvelope(false, HttpStatus.OK.value(),klass.getSimpleName() + " entity removed.");
        } else {
            throw new RestifierException(RestifierError.DATABASE_ERROR,"Could not remove " + klass.getSimpleName() + " entity.");
        }
        
        return env;
    }
}
