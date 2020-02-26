/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.configs;

import id.djarkasih.crudeazy.controller.RestifierInterceptor;
import id.djarkasih.crudeazy.repository.CollectionRepository;
import id.djarkasih.crudeazy.repository.DatabaseRepository;
import id.djarkasih.crudeazy.service.RestifierProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author ahmad
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private RestifierProvider restifierProvider;
    
    @Autowired
    private DatabaseRepository dbRepo;
    
    @Autowired
    private CollectionRepository collRepo;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new RestifierInterceptor(restifierProvider,dbRepo,collRepo));

    }
    
}
