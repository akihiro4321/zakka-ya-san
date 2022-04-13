
package db;

import entity.*;
import javax.ejb.Stateless;
import util.TryCatchDb;

@Stateless
public class OrderLineDb extends TryCatchDb{
	
	public	OrderLineDb(){
		super(OrderLine.class);
	}
}
