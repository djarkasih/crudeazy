/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.repository.SqlRestifier;
import id.djarkasih.crudeazy.util.DataSourceBuilder;
import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.repository.DatabaseRepository;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahmad
 */
@Service
public class RestifierProvider {
    
    @Autowired
    private DatabaseRepository repo;
    
    @Value("${crudeazy.multiEditEnabled}")
    private boolean multiEditEnabled;
    @Value("${crudeazy.pageSize}")
    private int defaultPageSize;
    
    private final Map<String,JdbcTemplate> jdbcTemplates = new HashMap();
    
    private JdbcTemplate getJdbcTemplate(String dbName) {
        
        JdbcTemplate template = null;
        
        if (! jdbcTemplates.containsKey(dbName)) {
            
            Database db = repo.findByName(dbName);
            DataSource ds = DataSourceBuilder.build(db);
            template = new JdbcTemplate(ds);
            jdbcTemplates.put(dbName, template);
            
        }
        
        if (jdbcTemplates.containsKey(dbName)) {
            
            template = jdbcTemplates.get(dbName);
            
        }
        
        return  template;
        
    }
    
    public Restifier getRestifierImplementation(String dbName) {
        
        JdbcTemplate template = this.getJdbcTemplate(dbName);
        return new SqlRestifier(template,defaultPageSize,multiEditEnabled);
        
    }
    
}
