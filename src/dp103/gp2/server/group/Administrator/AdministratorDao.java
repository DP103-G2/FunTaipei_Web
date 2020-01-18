package dp103.gp2.server.group.Administrator;

import java.util.List;

public interface AdministratorDao {
	
	int insert(Administrator administrator);
	
	int update(Administrator administrator);
	
	int delete(int AD_ID);
	
	Administrator findById(int AD_ID);
	
	List<Administrator> getAll();
}
