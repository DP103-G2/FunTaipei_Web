package dp103.gp2.server.group.TravelCollection;

import java.util.List;

import dp103.gp2.server.group.Member.Member;

public interface TravelCollectionDao {
	
	int insert(TravelCollection travelCollection);
	
	int update(TravelCollection travelCollection);
	
	int delete(int mb_no ,int  travel_id);
	
	List<TravelCollection> getAll();
}
