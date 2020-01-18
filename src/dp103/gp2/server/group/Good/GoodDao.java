package dp103.gp2.server.group.Good;

import java.util.List;

public interface GoodDao {
	
	int insert(Good good);
	
	int update(Good good);
	
	int delete(int pc_id);
	
	Good findById(int pc_id);
	
	List<Good> getAll();

}
