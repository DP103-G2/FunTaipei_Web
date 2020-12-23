package dp103.gp2.server.group.Administrator;

import java.util.List;

public interface AdministratorDao {
	
	int insert(Administrator administrator);
	
	boolean login(String AD_NO, String AD_PASSWORD);
	
	List<Administrator> getAll();
}
