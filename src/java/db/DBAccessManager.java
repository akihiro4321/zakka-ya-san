/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import dto.DefaultResultDto;
import dto.TableDto;
import entity.Product;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.ejb.Stateless;
import javax.inject.Inject;
import mapper.DBAccessMapper;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author okuya
 */
public class DBAccessManager {
    
    @Inject
    private SchemaAccessManager schemaManager;

    @Inject
    private SqlSession sqlSession;

    public DBAccessManager() {}
   

//    public void create(T entity) {
//    }
//
//    public void edit(T entity) {
//    }
//
//    public void remove(T entity) {
//    }

    public DefaultResultDto find(Object id, String className) {
        DBAccessMapper mapper = sqlSession.getMapper(DBAccessMapper.class);
        TableDto tableDto = schemaManager.getTableInfo(className);
        return mapper.find(id, tableDto.getColumn(), tableDto.getTable());
    }

    public List<DefaultResultDto> findAll(String className) {
        DBAccessMapper mapper = sqlSession.getMapper(DBAccessMapper.class);
        TableDto tableDto = schemaManager.getTableInfo(className);
        List<DefaultResultDto> result = mapper.findAll(tableDto.getColumn(), tableDto.getTable());
        return result;
    }

//    public List<T> findRange(int[] range) {
//    }

    public int count(String className) {
        DBAccessMapper mapper = sqlSession.getMapper(DBAccessMapper.class);
        TableDto tableDto = schemaManager.getTableInfo(className);
        long count = mapper.count(tableDto.getTable());
        return (int) count;
    }
}
