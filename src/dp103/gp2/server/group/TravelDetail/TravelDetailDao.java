package dp103.gp2.server.group.TravelDetail;

import java.util.List;

import dp103.gp2.server.group.group.Group;

public interface TravelDetailDao {
	
	int insert(TravelDetail traveldetail);
	
	int update(TravelDetail traveldetail);
	
	List<TravelDetail> findByTravelId(int id);
	
	TravelDetail findById(int travel_id);
	
	List<TravelDetail> getAll();

	int delete(int travel_id);


	
}
