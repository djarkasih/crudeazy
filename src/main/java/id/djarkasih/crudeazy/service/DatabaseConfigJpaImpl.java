/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.model.Database;
import id.djarkasih.crudeazy.model.DatabaseManager;
import id.djarkasih.crudeazy.repository.DatabaseManagerRepository;
import id.djarkasih.crudeazy.util.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahmad
 */
@Service("dbCfgJpaImpl")
public class DatabaseConfigJpaImpl implements DatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfigJpaImpl.class);

    @Autowired
    private DatabaseManagerRepository repo;
    
    private DatabaseManager manager() {
        DatabaseManager dbmgr = repo.findByName(Constants.CRUD_EAZY);
        if (dbmgr == null) {
            dbmgr = new DatabaseManager(Constants.CRUD_EAZY);
        }
        return dbmgr;
    }

    @Override
    public Database save(Database inp) {

        Database out = null;
        
        try {
            DatabaseManager mgr = this.manager();
            mgr.addDatabase(inp);
            repo.save(mgr);
            out = mgr.getDatabases().get(inp.getName());            
        } catch (Exception ex) {
            
        }
        
        return out;

    }

    @Override
    public Database find(String name) {
        
        Database out = null;
        
        try {
            DatabaseManager mgr = this.manager();
            out = mgr.getDatabases().get(name);            
        } catch (Exception ex) {
            
        }
        
        return out;

    }

    @Override
    public List<Database> readAll() {
        
        List<Database> databases = null;
        
        try {
            DatabaseManager mgr = this.manager();
            Map<String, Database> dbm = mgr.getDatabases();        
            
            databases = new ArrayList();
            dbm.values().parallelStream().forEach(databases::add);
        } catch (Exception ex) {
        }

        return databases;
        
    }

    @Override
    public void Delete(Database inp) {
        
        try {
            
            DatabaseManager mgr = this.manager();
            mgr.removeDatabase(inp);
            repo.save(mgr);
            
        } catch (Exception ex) {
            
        }

    }
    
}
