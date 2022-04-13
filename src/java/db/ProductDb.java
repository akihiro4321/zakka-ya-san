
package db;

import entity.*;
import javax.ejb.Stateless;
import util.TryCatchDb;

@Stateless
public class ProductDb extends TryCatchDb{

	public ProductDb() {
		super(Product.class);
	}

}
