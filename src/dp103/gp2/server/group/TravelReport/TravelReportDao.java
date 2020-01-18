package dp103.gp2.server.group.TravelReport;

import java.util.List;

public interface TravelReportDao {
	
	int insert(TravelReport travelReport);
	
	int update(TravelReport travelReport);
	
	int delete(int travel_report_id );
	
	TravelReport findById(int travel_report_id);
	
	List<TravelReport> getAll();
}
