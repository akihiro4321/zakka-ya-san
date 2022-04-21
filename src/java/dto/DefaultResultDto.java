/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author okuya
 */
@Data
public class DefaultResultDto {
private Long id;
private String kind;
private String name;
private byte[] pic_L;
private byte[] pic_S;
private Integer price;
private String text;
private Long apporder_id;
private Long orderline_id;
private Integer quantity;
private Long product_id;
private String form_mail;
private String form_msg;
private String form_name;
private Date orderdate;
private Integer totalPrice;
private String customer_Id;// ZAKKA_CUSTOMERのIDカラム名を変更
private String groupId;
private String cid;
private String mail;
private String passwd;
}
