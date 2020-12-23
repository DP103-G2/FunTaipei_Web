package dp103.gp2.server.group.TravelCollection;

import java.util.List;

import dp103.gp2.server.group.Member.Member;
import dp103.gp2.server.group.travel.Travel;
public interface TravelCollectionDao {
	
	public int insert(int memId, int travelId);
	
	int update(TravelCollection travelCollection);
	
	int delete(int mb_no ,int  travel_id);
	
	List<TravelCollection> getAll(int memId);
	
	List<Travel> getTravelInfo(int memId);
	
	//0122新增
	List<TravelCollection> getMemberInfo();
	
	byte[] getImage(int travel_id);
}
