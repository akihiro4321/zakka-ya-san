/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mapper;

import dto.DefaultResultDto;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author okuya
 */
@Mapper
public interface DBAccessMapper {
    
    public void create();

    public void edit();

    public void remove();

    public DefaultResultDto find(
            @Param("id") Object id,
            @Param("columns") String columns,
            @Param("tableName") String tableName);

    public List<DefaultResultDto> findAll(
            @Param("columns") String columns,
            @Param("tableName") String tableName);

    public List<DefaultResultDto> findRange(int[] range);

    public int count(String tableName);
}
