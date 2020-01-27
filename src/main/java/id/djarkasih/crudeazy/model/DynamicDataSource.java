/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.djarkasih.crudeazy.model.Database;

/**
 *
 * @author ahmad
 */
public class DynamicDataSource {
    
    private final String name;
    
    private final HikariDataSource ds;

    public DynamicDataSource(String name, Database db) {
        
        HikariConfig cfg = new HikariConfig();
        cfg.setDriverClassName(db.getDriverName());
        cfg.setJdbcUrl(db.getUrl());
        cfg.setUsername(db.getUsername());
        cfg.setPassword(db.getPassword());
                
        this.name = name;
        this.ds = new HikariDataSource(cfg);
    }

    public String getName() {
        return name;
    }

    public HikariDataSource getDs() {
        return ds;
    }

}
