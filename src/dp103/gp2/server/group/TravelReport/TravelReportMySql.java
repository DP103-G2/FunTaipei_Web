package dp103.gp2.server.group.TravelReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;
import static dp103.gp2.server.group.Common.Common.PASSWORD;
public class TravelReportMySql implements TravelReportDao {
	
	public TravelReportMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(TravelReport travelReport) {
		int count = 0;
		String sql = "INSERT INTO Travel_Report (Travel_Report_CAUSE) value(?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, travelReport.getTravel_report_cause());
			count = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public int update(TravelReport travelReport){
		int count = 0;
		String sql = "UPDATE Travel_Report SET TRAVEL_REPORT_CAUSE = '?' WHERE TRAVEL_REPORT_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, travelReport.getTravel_report_cause());
			count = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public int delete(int travel_report_id) {
		int count = 0;
		String sql = "DELETE FROM TRAVEL_REPORT WHERE TRAVEL_REPORT_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travel_report_id);
			count = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
		try {
			if(ps != null) {
				ps.close();
			}
			if(connection != null) {
				connection.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public TravelReport findById(int travel_report_id) {
		String sql = "SELECT TRAVEL_ID,MB_ID,TRAVEL_REPORT_CAUSE,TRAVEL_DATETIME FROM TRAVEL_REPORT WHERE TRAVEL_REPORT_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		TravelReport travelReport = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travel_report_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int TRAVEL_ID = rs.getInt(1);
				int MB_ID = rs.getInt(2);
				String TRAVEL_REPORT_CAUSE = rs.getString(3);
				Date TRAVEL_DATETIME = rs.getDate(4);
				TravelReport travelReports = new TravelReport(travel_report_id, TRAVEL_ID, MB_ID, TRAVEL_REPORT_CAUSE, TRAVEL_DATETIME);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
			if(ps != null) {
				ps.close();
			}
			if(connection != null) {
				connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return travelReport;
	}

	@Override
	public List<TravelReport> getAll() {
		String sql = "SELECT TRAVEL_REPORT_ID,TRAVEL_ID,MB_ID,TRAVEL_REPORT_CAUSE,TRAVEL_DATETIME FROM TRAVEL_REPORT ORDER BY TRAVEL_DATETIME DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<TravelReport> TravelReportList = new ArrayList<TravelReport>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int travel_report_id = rs.getInt(1);
				int travel_id = rs.getInt(2);
				int mb_no = rs.getInt(3);
				String travel_report_cause = rs.getString(4);
				Date travel_datetime = rs.getDate(5);
				TravelReport travelReport = new TravelReport(travel_report_id ,travel_id,mb_no,travel_report_cause,travel_datetime);
				TravelReportList.add(travelReport);	
		}
		return TravelReportList;
	}catch(SQLException e) {
		e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return TravelReportList;
	}
}
