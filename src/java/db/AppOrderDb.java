
package db;

import entity.*;
import javax.ejb.Stateless;
import util.TryCatchDb;

@Stateless
public class AppOrderDb extends TryCatchDb{
	
	public	AppOrderDb(){
		super(AppOrder.class);
	}
}
