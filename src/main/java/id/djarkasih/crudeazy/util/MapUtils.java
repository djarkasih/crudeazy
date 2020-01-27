/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ahmad
 */
public class MapUtils {
    private static final Logger logger = LoggerFactory.getLogger(MapUtils.class);
    
    public static Set<String> getMissingFields(Map<String, Object> inp, Set<String> keys) {
        
        Set<String> out = new HashSet();
            
        keys.stream().filter((key) -> (! inp.containsKey(key))).forEachOrdered((key) -> {
            out.add(key);
        });
        
        return out;
        
    }
    
    public static Set<String> getPresentFields(Map<String, Object> inp, Set<String> keys) {
        
        Set<String> out = new HashSet();
        
        keys.stream().filter((key) -> (inp.containsKey(key))).forEachOrdered((key) -> {
            out.add(key);
        });
        
        return out;
        
    }

    public static Map<String, Object> copy (Map<String, Object> inp, Set<String> keys) {
        
        Map<String, Object> out = new HashMap();
        
        keys.forEach((key) -> {
            out.put(key, inp.get(key));
        });
        
        return out;
        
    }
    
}
