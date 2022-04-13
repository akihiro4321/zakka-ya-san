package beans;
/* 顧客の購入履歴を得る */
import entity.AppOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class OrderManager {
	@PersistenceContext
	EntityManager	em;
	
	public  List<AppOrder> getHistory(String id){
		TypedQuery<AppOrder> query = em.createNamedQuery(AppOrder.AppOrder_History, AppOrder.class);
		query.setParameter("customerId", id);
		return	query.getResultList();
	}
}
