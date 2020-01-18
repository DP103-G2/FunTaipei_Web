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
	public int insert(TravelCollection travelCollection) {
		int count = 0;
		String sql = "INSERT INTO Travel_Collection (MB_NO, TRAVEL_ID) VALUES (? + ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travelCollection.getMb_no());
			ps.setInt(2, travelCollection.getTravel_id());
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
	public int delete(int mb_no, int travel_no) {
		int count = 0;
		String sql = "DELETE FROM Travel_Collection WHERE MB_NO = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, mb_no);
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
	
	
	
//
//	@Override
//	public TravelCollection findById(int mb_no, int travel_id) {
//		String sql = "SELECT MB_NO,TRAVEL_ID FROM Travel_Collection WHERE TRAVEL_ID = ?;";
//		Connection connection = null;
//		PreparedStatement ps = null;
//		TravelCollection travelCollection = null;
//		try {
//			connection = DriverManager.getConnection(URL,USER,PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setInt(1, travel_id);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()) {
//				int MB_NO = rs.getInt(1);
//				TravelCollection travelCollections = new TravelCollection(MB_NO, travel_id);
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(ps != null) {
//					ps.close();
//				}
//				if(connection != null) {
//					connection.close();
//				}
//			}catch(SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return travelCollection;
//	}
				//GetALL
	@Override
	public List<TravelCollection> getAll() {
		String sql = "";
		Connection connection = null;
		PreparedStatement ps = null;
		List<TravelCollection> travelCollectionList = new ArrayList<TravelCollection>();
		sql = "SELECT Travel_Collection.MB_NO, Travel_Collection.TRAVEL_ID ,MB_EMAIL, MB_NAME, Join_Group.GP_ID, GP_NAME, GP_DATESTART,GP_DATEEND, GP_EVENTDATE " + 
				"FROM FunTaipei.Travel_Collection " + 
				"JOIN FunTaipei.MEMBER " + 
				"on Travel_Collection.MB_NO = FunTaipei.MEMBER.MB_NO " + 
				"JOIN Join_Group " + 
				"on FunTaipei.MEMBER .MB_NO = Join_Group.MB_NO " + 
				"JOIN GROUP_ALL " + 
				"ON Join_Group.GP_ID = Group_All.GP_ID; ";
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int mb_no = rs.getInt(1);
				int travel_id = rs.getInt(2);
				String mb_email = rs.getString(3);
				String mb_name = rs.getString(4);
				int gp_id = rs.getInt(5);
				String gp_name = rs.getString(6);
				Timestamp gp_datestart = rs.getTimestamp(7);
				Timestamp gp_dateend = rs.getTimestamp(8);
				Timestamp gp_eventdate = rs.getTimestamp(9);
				TravelCollection travelCollection = new TravelCollection(mb_no ,travel_id, mb_email, mb_name, gp_id, gp_name, gp_datestart, gp_dateend, gp_eventdate);
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
	//01-05新增 要抓Member部分資料
//		@Override
//		public List<Member> getByMemberId(int id) {
//			String sql = "";
//			Connection connection = null;
//			PreparedStatement ps = null;
//			TravelCollection travelCollection = null;
//			List<TravelCollection> travelCollectionList = new ArrayList<TravelCollection>();
//			sql = "SELECT m.MB_NO,MB_EMAIL,MB_NAME,JOIN_GROUP.GP_ID,GP_NAME,GP_DATESTART,GP_DATEEND,GP_EVENTDATE FROM FunTaipei.Member m JOIN JOIN_GROUP  ON m.MB_NO = JOIN_GROUP.MB_NO JOIN Group_All on Join_Group.GP_ID = Group_All.GP_ID;";
//			byte[] image = null;
//			try {
//				connection = DriverManager.getConnection(URL, USER, PASSWORD);
//				ps = connection.prepareStatement(sql);
//				ps.setInt(1,id);
//				ResultSet rs = ps.executeQuery();
//				while(rs.next()) {
//					String mb_email = rs.getString(1);
//					String mb_name = rs.getString(2);
//					int gp_id = rs.getInt(3);
//					String gp_name = rs.getString(4);
//					Timestamp gp_datestart = rs.getTimestamp(5);
//					Timestamp gp_dateend = rs.getTimestamp(6);
//					Timestamp gp_eventdate = rs.getTimestamp(7);
//					TravelCollection travelCollection = new TravelCollection(id, mb_email, mb_name, gp_id, gp_name, gp_datestart, gp_dateend, gp_eventdate);
//					travelCollectionList.add(travelCollection);
//				}
//			}catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					if (ps != null) {
//						ps.close();
//					}
//					if (connection != null) {
//						connection.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			return travelCollectionList;
//		}
}
