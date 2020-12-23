package dp103.gp2.server.group.travel;

import java.util.List;

public interface TravelDao {
	
	int insert(Travel travel,byte[] image);
	
	int update(Travel travel,byte[] image);
	
	int delete(int travel_id);
	
	Travel findById(int travel_id);
	
	Travel findByGroup(int id);
	
	List<Travel> findBymemId(int mb_no);
	
	List<Travel> getAll();
	
	byte[] getImage(int travel_id);

}
