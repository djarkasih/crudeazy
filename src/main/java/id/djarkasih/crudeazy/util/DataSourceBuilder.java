/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.djarkasih.crudeazy.model.domain.Database;
import javax.sql.DataSource;

/**
 *
 * @author ahmad
 */
public class DataSourceBuilder {
    
    public static DataSource build(Database db) {
        
        HikariConfig cfg = new HikariConfig();
        cfg.setDriverClassName(db.getDriverName());
        cfg.setJdbcUrl(db.getUrl());
        cfg.setUsername(db.getUsername());
        cfg.setPassword(db.getPassword());
                
        return new HikariDataSource(cfg);

    }
    
    public static DataSource build(String jdbcUrl, String jdbcUser, String jdbcPassword) {
        
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(jdbcUser);
        cfg.setPassword(jdbcPassword);
        
        return new HikariDataSource(cfg);
        
    }

}
