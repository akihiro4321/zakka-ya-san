/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
    public SqlSession session() {
        return ssf.openSession();
    }
    
    public void closeSession(@Disposes SqlSession sqlSession) {
        sqlSession.close();
    }
}
