/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package query;

import entity.AppKind;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author okuya
 */
@Data
public class ProductSelectQuery {
    /**
     * 並び替え対象フィールド
     */
    public enum OrderByField {
        /**
         * ID
         */
        ID("id"),
        /**
         * 商品価格
         */
        PRICE("price");

        private final String value;

        OrderByField(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * 並び替え順序(asc / desc)
     */
    public enum SortOrder {
        ASC("asc"), DESC("desc");

        private final String value;
        SortOrder(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
    
    @AllArgsConstructor
    private static class OrderCondition {
        private final OrderByField field;
        private final SortOrder sortOrder;
    }
    
    // where句用フィールド
    private AppKind appKind;
    
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private List<OrderCondition> orderConditions = new ArrayList<>();
    
    /**
     * orderBy文字列を返します
     * getter形式のため、xmlからは${orderBy}でアクセス可能
     * @return orderBy文字列
     */
    public String getOrderBy() {
        StringJoiner sj = new StringJoiner(",");
        for (OrderCondition oc: orderConditions) {
            sj.add(oc.field.getValue() + " " + oc.sortOrder.getValue());
        }

        return sj.toString();
    }
    
    public void addOrderCondition(OrderByField field, SortOrder sortOrder) {
        orderConditions.add(new OrderCondition(field, sortOrder));
    }
}
