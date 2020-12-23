package dp103.gp2.server.group.TravelDetail;
import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;
import static dp103.gp2.server.group.Common.Common.PASSWORD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dp103.gp2.server.group.group.Group;

public class TravelDetailMySql implements TravelDetailDao{
	
	public TravelDetailMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(TravelDetail travelDetail) {
		int count = 0;
		String sql = "INSERT INTO TRAVEL_DETAIL(Travel_id, PC_ID) VALUES(? ,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travelDetail.getTravel_id());
			ps.setInt(2, travelDetail.getPc_id());	
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
	public int update(TravelDetail traveldetail) {
		
		return 0;
	}

	@Override
	public TravelDetail findById(int travel_id) {
		String sql = "SELECT TRAVEL_ID, TRAVEL_DETAIL.PC_ID,PC_NAME FROM FunTaipei.Travel_Detail JOIN PLACE ON TRAVEL_DETAIL.PC_ID = PLACE.PC_ID WHERE TRAVEL_ID = ?;";	
				
		Connection connection = null;
		PreparedStatement ps = null;
		TravelDetail travelDetail = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1 , travel_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int pc_id = rs.getInt(1);
				String pc_name = rs.getString(2);
				Timestamp travel_time = rs.getTimestamp(3);
				travelDetail = new TravelDetail(travel_id, pc_id, pc_name);
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
		return travelDetail;
	}

	@Override
	public List<TravelDetail> getAll() {
		List<TravelDetail> travelDetails = new ArrayList<TravelDetail>();
		String sql = "SELECT TRAVEL_ID,PLACE.PC_ID,PC_NAME, Travel_TIME FROM TRAVEL_DETAIL D JOIN Place on D.PC_ID = PLACE.PC_ID;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int travel_id = rs.getInt(1);
				int pc_id = rs.getInt(2);
				String pc_name = rs.getString(3);
				Timestamp travel_time = rs.getTimestamp(4);
				TravelDetail travelDetail = new TravelDetail(travel_id, pc_id, pc_name);
				travelDetails.add(travelDetail);
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
		return travelDetails;
	}

	@Override
	public int delete(int travel_id, int pc_id) {
		int count = 0;
		String sql ="DELETE FROM Travel_Detail WHERE TRAVEL_ID = ? AND PC_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travel_id);
			ps.setInt(2, pc_id);
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
	//01-02新增
	@Override
	public List<TravelDetail> findByTravelId(int id) {
		String sql = "SELECT PLACE.PC_ID, PC_NAME "
					+"FROM TRAVEL_DETAIL JOIN PLACE "
					+" ON TRAVEL_DETAIL.PC_ID = PLACE.PC_ID "
					+"WHERE TRAVEL_ID = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		TravelDetail travelDetail = null;
		List<TravelDetail> travelDetailList = new ArrayList<TravelDetail>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int pc_id = rs.getInt(1);
				String pc_name = rs.getString(2);
				TravelDetail traveldetail = new TravelDetail(id, pc_id, pc_name);
				travelDetailList.add(traveldetail);
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
		return travelDetailList;
	}
}
