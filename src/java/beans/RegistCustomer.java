/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package beans;

import db.CustomerDb;
import entity.AppGroup;
import entity.Customer;
import entity.GroupKey;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import net.tkxtools.MailSender;
import util.SHA256Encoder;

/**
 *
 * @author okuya
 */
@Named(value = "registCustomer")
@Stateless
public class RegistCustomer extends SuperBb implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @EJB
    CustomerDb db;
    
    @Inject
    transient Logger log;
    
    @EJB
    MailSender sender;

    private String username;
    private String password;
    private String mail;
    private String group = "user";
    
    // 顧客情報の登録
    public void registInfo() {
        String cid = randomUsingStreams(6);
        GroupKey key = new GroupKey(this.group, cid);
        AppGroup group = new AppGroup(key, null);
        // パスワードは暗号化してからセットする
        Customer customer = new Customer(cid, getEncodedPw(password), username, mail, group);
        group.setCustomer(customer);
        db.add(customer);
        System.out.println("メール送信");
        sender.send(mail, "新規登録通知", "ご登録ありがとうございます。");
        System.out.println("メール送信完了");
    }
    
    // パスワードの暗号化
    private String getEncodedPw(String pw) {
        SHA256Encoder encoder = new SHA256Encoder();
        return encoder.encodePassword(pw);
    }
    
    // random生成
    private String randomUsingStreams(int length) {
    final int start = '0';
    final int end = 'z';
    final Random random = new Random();
    final String generated = random.ints(start, end + 1)
      .filter(i -> Character.isLetter(i) || Character.isDigit(i))
      .limit(length)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
    
    return generated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
    
}
