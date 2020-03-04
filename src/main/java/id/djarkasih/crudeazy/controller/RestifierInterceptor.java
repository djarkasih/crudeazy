/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.controller;

import id.djarkasih.crudeazy.error.RestifierError;
import id.djarkasih.crudeazy.error.RestifierException;
import id.djarkasih.crudeazy.model.domain.Collection;
import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.repository.CollectionRepository;
import id.djarkasih.crudeazy.repository.DatabaseRepository;
import id.djarkasih.crudeazy.service.Restifier;
import static id.djarkasih.crudeazy.service.Restifier.COLLECTION_NAME_KEY;
import static id.djarkasih.crudeazy.service.Restifier.RESTIFIER_KEY;
import id.djarkasih.crudeazy.service.RestifierProvider;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author ahmad
 */
public class RestifierInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(RestifierInterceptor.class);
    
    private static final Set<String> modules = Set.of("/config");
    
    private final RestifierProvider restifierProvider;
    
    private final DatabaseRepository dbRepo;
    
    private final CollectionRepository collRepo;

    public RestifierInterceptor(RestifierProvider restifierProvider, DatabaseRepository dbRepo, CollectionRepository collRepo) {
        this.restifierProvider = restifierProvider;
        this.dbRepo = dbRepo;
        this.collRepo = collRepo;
    }
    
    private boolean isInsideMainService(String uri) {
        
        String firstPath = null;
        
        String[] paths = uri.split("/");
        
        if (paths.length > 2)
            firstPath = "/" + paths[1];

        return (firstPath != null) && (! modules.contains(firstPath));

    }
    
    private void addRestifierImplementor(HttpServletRequest req) throws RestifierException {
        
        String[] inputs = req.getRequestURI().split("/");
        if (inputs.length > 2) {
            
            String dbName = inputs[1];
            
            logger.info("dbName = " + dbName);
            Database db = dbRepo.findByName(dbName);  
            if (db == null) {
                throw new RestifierException(RestifierError.DATABASE_NOT_FOUND);
            }
           
            String alias = inputs[2];
            if (!collRepo.existsByDatabaseIdAndAlias(db.getDatabaseId(),alias)) {
                throw new RestifierException(RestifierError.COLLECTION_NOT_FOUND);
            }
            
            Restifier restifier = restifierProvider.getRestifierImplementation(dbName);
            Collection coll = collRepo.findByAlias(alias);
            
            req.setAttribute(RESTIFIER_KEY, restifier);
            req.setAttribute(COLLECTION_NAME_KEY, coll.getName());
        }
        
    }
    
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        
        if (isInsideMainService(req.getRequestURI())) {
            
            this.addRestifierImplementor(req);

        }
        
        return true;
    
    }
    
}
