/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.List;
import java.util.StringJoiner;
import lombok.Data;

/**
 *
 * @author okuya
 */
@Data
public class TableDto {
    private String table;
    private List<ColumnInfo> columnInfoList;
    
    public String getColumn(){
        StringJoiner sj = new StringJoiner(",");
        for(ColumnInfo columnInfo : columnInfoList) {
            sj.add(columnInfo.getColumnName());
        }
        return sj.toString();
    }
}
