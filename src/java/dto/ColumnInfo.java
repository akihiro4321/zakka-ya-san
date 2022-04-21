/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import lombok.Data;

/**
 *
 * @author okuya
 */
@Data
public class ColumnInfo {
    private String columnName;
    private String jdbcType;
}
