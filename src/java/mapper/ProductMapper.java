/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mapper;

import entity.AppKind;
import entity.Product;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import query.ProductSelectQuery;

/**
 *
 * @author okuya
 */
@Mapper
public interface ProductMapper {
    
    List<Product> selectProduct(ProductSelectQuery query, RowBounds rowbound);
    
    long selectCount(ProductSelectQuery query);
}
