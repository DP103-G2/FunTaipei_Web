package dp103.gp2.server.group.TravelCollection;
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
import java.util.List;

import dp103.gp2.server.group.Member.Member;
import dp103.gp2.server.group.travel.Travel;

public class TravelCollectionDaoMySql implements TravelCollectionDao{
	

	
	public TravelCollectionDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//增加
	@Override
	public int insert(int memId, int travelId) {
		int count = 0;
		String sql = "INSERT INTO Travel_Collection (MB_NO, TRAVEL_ID) VALUES (?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, memId);
			ps.setInt(2, travelId);
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
	public int update(TravelCollection travelCollection) {
		int count = 0;
		String sql = "";
		sql = "UPDATE Travel_Collration SET MB_NO = ?,TRAVEL_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps.setInt(1, travelCollection.getMb_no());
			ps.setInt(2, travelCollection.getTravel_id());
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
	//刪除
	@Override
	public int delete(int mb_no, int travel_id) {
		int count = 0;
		String sql = "DELETE FROM Travel_Collection WHERE MB_NO = ? AND TRAVEL_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, mb_no);
			ps.setInt(2, travel_id);
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
	
	
	//2020-01-22更新SQL語法
	@Override
	public List<TravelCollection> getAll(int memId) {
		String sql = "";
		Connection connection = null;
		PreparedStatement ps = null;
		List<TravelCollection> travelCollectionList = new ArrayList<TravelCollection>();
		sql = "SELECT Travel_Collection.MB_NO, Travel_Collection.TRAVEL_ID ,MB_EMAIL, MB_NAME , Travel_Name, Image " + 
				"FROM FunTaipei.Travel_Collection " + 
				"JOIN FunTaipei.MEMBER " + 
				"on Travel_Collection.MB_NO = FunTaipei.MEMBER.MB_NO " + 
				"JOIN FunTaipei.Travel " + 
				"on Travel_Collection.TRAVEL_ID = Travel.TRAVEL_ID " + 
				"WHERE Travel_Collection.MB_NO = ?; ";
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, memId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int mb_no = rs.getInt(1);
				int travel_id = rs.getInt(2);
				String mb_email = rs.getString(3);
				String mb_name = rs.getString(4);
				String travel_name = rs.getString(5);
				TravelCollection travelCollection = new TravelCollection(mb_no ,travel_id, mb_email, mb_name, travel_name);
				travelCollectionList.add(travelCollection);
			}
		return travelCollectionList;
	}catch(SQLException e) {
		e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return travelCollectionList;
	}
	@Override
	public List<TravelCollection> getMemberInfo() {
		String sql = "SELECT TRAVEL_COLLECTION.MB_NO,MB_NAME, MB_EMAIL, MB_IMAGE "+
					 "FROM TRAVEL_COLLECTION " +
					 "JOIN FunTaipei.Member " +
					 "on TRAVEL_COLLECTION.MB_NO = MEMBER.MB_NO "+
					 "WHERE TRAVEL_COLLECTION.MB_NO = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		List<TravelCollection> travelCollectionList = new ArrayList<TravelCollection>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int mb_no = rs.getInt(1);
				String mb_name = rs.getString(2);
				String mb_email = rs.getString(3);
			}
			return travelCollectionList;
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
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
		return travelCollectionList;
	}
	//取得Travel圖片文字0201
	public List<Travel> getTravelInfo(int memId){
		String sql = "SELECT Travel_Collection.Travel_id, Travel_Name, IMAGE " +
					 "from Travel_Collection " +
					 "join Travel " +
					 "on Travel.Travel_id = Travel_Collection.Travel_id " +
					 "WHERE Travel_Collection.MB_NO = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Travel> travelList = new ArrayList<Travel>();
		try {
			connection = DriverManager.getConnection(URL,USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, memId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int travel_id = rs.getInt(1);
				String travel_name = rs.getString(2);
				
				Travel travel = new Travel(travel_id, travel_name);
				travelList.add(travel);
			}
			return travelList;
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
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return travelList;
	}
	
	//抓Travel圖片方法
	@Override
	public byte[] getImage(int travel_id) {
		String sql = "SELECT Image " + 
					 "FROM FunTaipei.Travel_Collection " +
					 "JOIN Travel " +
					 "ON Travel_Collection.Travel_id = TRAVEL.Travel_id";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(sql);
			ps.setInt(1, travel_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				image = rs.getBytes(1);
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
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return image;
	}

	
}
