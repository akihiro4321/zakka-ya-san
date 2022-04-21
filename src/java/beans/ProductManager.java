package beans;

import entity.AppKind;
import entity.Product;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import mapper.ProductMapper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import query.ProductSelectQuery;
import query.ProductSelectQuery.OrderByField;
import query.ProductSelectQuery.SortOrder;
import util.Pagenation;

@Stateless
public class ProductManager {

    static final Logger log = Logger.getLogger(ProductManager.class.getName());

    @Inject
    private SqlSession sqlSession;

    public List<Product> getFromDbMybatis(int priceItem, AppKind kindItem, Pagenation productPage) {

        ProductSelectQuery query = new ProductSelectQuery();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        if (priceItem == 1) {
            query.addOrderCondition(OrderByField.ID, SortOrder.ASC);
        } else if (priceItem == 2) {
            query.addOrderCondition(OrderByField.PRICE, SortOrder.ASC);
        } else {
            query.addOrderCondition(OrderByField.PRICE, SortOrder.DESC);
        }

        if (kindItem != AppKind.NONE) {
            query.setAppKind(kindItem);
        }
        List<Product> resultList
                = mapper.selectProduct(query, new RowBounds(productPage.firstResult(), productPage.maxResult()));

        return resultList;
    }

    public void counterClearMybatis(AppKind kindItem, Pagenation productPage) {
        ProductSelectQuery query = new ProductSelectQuery();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);
        if (kindItem != AppKind.NONE) {
            query.setAppKind(kindItem);
        }
        long countResult = mapper.selectCount(query);
        productPage.setup((int) countResult, 5);
    }

//    public List<Product> getFromDb(int priceItem, AppKind kindItem, Pagenation productPage) {
//        TypedQuery<Product> query = null;
//        if (kindItem == AppKind.NONE) {
//            if (priceItem == 1) {
//                query = em.createNamedQuery(Product.Qall, Product.class);
//            } else if (priceItem == 2) {
//                query = em.createNamedQuery(Product.QallASC, Product.class);
//            } else {
//                query = em.createNamedQuery(Product.QallDESC, Product.class);
//            }
//        } else {
//            if (priceItem == 1) {
//                query = em.createNamedQuery(Product.QKind, Product.class);
//            } else if (priceItem == 2) {
//                query = em.createNamedQuery(Product.QkindASC, Product.class);
//            } else {
//                query = em.createNamedQuery(Product.QkindDESC, Product.class);
//            }
//            query.setParameter("valueOfKind", kindItem);
//        }
//        query.setFirstResult(productPage.firstResult());
//        query.setMaxResults(productPage.maxResult());
//        return query.getResultList();
//    }
//    public void counterClear(AppKind kindItem, Pagenation productPage) {
//        TypedQuery<Long> count_query = null;
//        if (kindItem == AppKind.NONE) {
//            count_query = em.createNamedQuery(Product.Count_Qall, Long.class);
//        } else {
//            count_query = em.createNamedQuery(Product.Count_QKind, Long.class);
//            count_query.setParameter("valueOfKind", kindItem);
//        }
//        long countResult = count_query.getSingleResult();
//        productPage.setup((int) countResult, 5);
//    }
}
