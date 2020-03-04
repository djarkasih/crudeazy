/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.configs;

import id.djarkasih.crudeazy.util.DataSourceBuilder;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author ahmad
 */
@Configuration
public class DataSourceConfig {
    
    private final String DEFAULT_H2_JDBC_URL = "jdbc:h2:mem:crudeazy";

    private final String DEFAULT_JDBC_URL = "jdbc:h2:mem:crudeazy";
    private final String DEFAULT_JDBC_USER = "crudeadm";
    private final String DEFAULT_JDBC_PASSWORD = "crud4dmK3y";

    private final String JDBC_URL_VAR = "JDBC_URL";
    private final String JDBC_USER_VAR = "JDBC_USER";
    private final String JDBC_PASSWORD_VAR = "JDBC_PASSWORD";

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        
        DataSource ds = null;
        
        String jdbcUrl = env.getProperty(JDBC_URL_VAR);
        String jdbcUser = env.getProperty(JDBC_USER_VAR);
        String jdbcPassword = env.getProperty(JDBC_PASSWORD_VAR);
        
        if ((jdbcUrl != null) && (jdbcUser != null) && (jdbcPassword != null)) {
            ds = DataSourceBuilder.build(jdbcUrl, jdbcUser, jdbcPassword);
        } else if ((jdbcUrl == null) && (jdbcUser != null) && (jdbcPassword != null)) {
            ds = DataSourceBuilder.build(DEFAULT_H2_JDBC_URL, jdbcUser, jdbcPassword);
        } else {
            ds = DataSourceBuilder.build(DEFAULT_JDBC_URL, DEFAULT_JDBC_USER, DEFAULT_JDBC_PASSWORD);
        }
        
        return ds;
        
    }

}
