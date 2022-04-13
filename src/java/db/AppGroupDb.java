
package db;

import entity.*;
import javax.ejb.Stateless;
import util.TryCatchDb;

@Stateless
public class AppGroupDb extends TryCatchDb{

	public AppGroupDb() {
		super(AppGroup.class);
	}

}
