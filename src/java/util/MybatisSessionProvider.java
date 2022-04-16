/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 *
 * @author okuya
 */
@Stateless
public class MybatisSessionProvider {
    
    @Inject
    private SqlSessionFactory ssf;
    
    private SqlSessionFactory create() {
        String resource = "conf/mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource);) {
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            return builder.build(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    @ApplicationScoped
    @Produces
    public SqlSessionFactory sessionFactory() {
        return this.create();
    }
    
    @RequestScoped
    @Produces
    public SqlSession appleSession() {
        return ssf.openSession();
    }
    
    public void closeAppleSession(@Disposes SqlSession sqlSession) {
        sqlSession.close();
    }
}
