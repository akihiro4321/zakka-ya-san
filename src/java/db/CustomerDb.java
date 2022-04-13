
package db;

import util.TryCatchDb;
import entity.*;
import javax.ejb.Stateless;

@Stateless
public class CustomerDb extends TryCatchDb{
	
	public CustomerDb() {
		super(Customer.class);
	}
}
