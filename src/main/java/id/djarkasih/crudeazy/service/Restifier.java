/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.error.RestifierException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author ahmad
 */
public interface Restifier {
    
    public static final String FIELD_KEY = "_field";
    public static final String ORDER_BY_KEY = "_orderBy";
    public static final String PAGE_NO_KEY = "_pageNo";
    public static final String PAGE_SIZE_KEY = "_pageSize";
    public static final String IS_DISTINCT_KEY = "_isDistinct";
    
    public static final String RESTIFIER_KEY = "__restifierKey";
    public static final String COLLECTION_NAME_KEY = "__collectionNameKey";
    
    public static final Set<String> RESERVED_WORDS = Set.of(FIELD_KEY,ORDER_BY_KEY,PAGE_NO_KEY,PAGE_SIZE_KEY,IS_DISTINCT_KEY);
    
    public long count(String tableName, MultiValueMap<String,String> params) throws RestifierException;
    public boolean create(String tableName, Map<String,String> rec) throws RestifierException;
    public List<Map<String,Object>> findAll(String tableName, MultiValueMap<String,String> params) throws RestifierException;
    public int update(String tableName, Map<String, String> rec, MultiValueMap<String, String> filters) throws RestifierException;
    public int delete(String tableName, MultiValueMap<String, String> filters) throws RestifierException;
    
}
