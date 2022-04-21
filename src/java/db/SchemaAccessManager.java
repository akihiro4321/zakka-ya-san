/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import dto.TableDto;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.enterprise.context.Dependent;
import util.FileUtil;

/**
 *
 * @author okuya
 */
@Dependent
public class SchemaAccessManager {
    
    private static Map<String,Map<String,?>> cacheMap = new ConcurrentHashMap<>();
    
    private <T> Map<String,T> getCacheMap(String cacheName) {
        return (Map<String,T>) cacheMap.computeIfAbsent(cacheName, key -> new ConcurrentHashMap<String,T>());
    }
    
    public TableDto getTableInfo(String className) {
        TableDto tableDto = null;
        Map<String, TableDto> cache = getCacheMap("TableInfo");
        
        if(cache.containsKey(className)) {
            tableDto = cache.get(className);
        } else {
            tableDto = FileUtil.getTableInfoFromXML(className);
        }
        
        return tableDto;
    }
}
