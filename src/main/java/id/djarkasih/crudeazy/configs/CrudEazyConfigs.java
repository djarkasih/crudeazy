/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ahmad
 */
@Configuration
public class CrudEazyConfigs {
    
    private final Logger logger = LoggerFactory.getLogger(CrudEazyConfigs.class);
    
    @Bean
    public Logger getLogger() {
        return logger;
    }

}
