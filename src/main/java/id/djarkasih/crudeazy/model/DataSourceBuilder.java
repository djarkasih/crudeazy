/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

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

}
