package dp103.gp2.server.group.FavoritePlace;
import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dp103.gp2.server.group.Member.Member;
import dp103.gp2.server.group.TravelCollection.TravelCollection;

import static dp103.gp2.server.group.Common.Common.PASSWORD;


public class FavoritePlaceDaoMySql implements FavoritePlaceDao{
	

	public FavoritePlaceDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//增加
		@Override
		public int insert(FavoritePlace favoritePlace) {
			int count = 0;
			String sql = "INSERT INTO Favorite_Place (MB_NO, PC_ID) VALUES (? , ?);";
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				connection = DriverManager.getConnection(URL,USER,PASSWORD);
				ps = connection.prepareStatement(sql);
				ps.setInt(1, favoritePlace.getMb_no());
				ps.setInt(2, favoritePlace.getPc_id());
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
		public int update(FavoritePlace favoritePlace) {
			int count = 0;
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				connection = DriverManager.getConnection(URL,USER,PASSWORD);
				ps.setInt(1, favoritePlace.getMb_no());
				ps.setInt(2, favoritePlace.getPc_id());
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
		public int delete(int pc_id, int mb_no) {
			int count = 0;
			String sql = "DELETE FROM FunTaipei.Favorite_Place WHERE PC_ID = ? AND MB_NO = ?;";
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				connection = DriverManager.getConnection(URL,USER,PASSWORD);
				ps = connection.prepareStatement(sql);
				ps.setInt(1, pc_id);
				ps.setInt(2, mb_no);
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
//		@Override
//		public FavoritePlace findById(int mb_no, int pc_id) {
//			String sql = "SELECT MB_NO,PC_ID FROM Favorite_Place WHERE PC_ID = ?;";
//			Connection connection = null;
//			PreparedStatement ps = null;
//			TravelCollection travelCollection = null;
//			try {
//				connection = DriverManager.getConnection(URL,USER,PASSWORD);
//				ps = connection.prepareStatement(sql);
//				ps.setInt(1, travel_id);
//				ResultSet rs = ps.executeQuery();
//				if(rs.next()) {
//					int MB_NO = rs.getInt(1);
//					TravelCollection travelCollections = new TravelCollection(MB_NO, travel_id);
//				}
//			}catch(SQLException e) {
//				e.printStackTrace();
//			}finally {
//				try {
//					if(ps != null) {
//						ps.close();
//					}
//					if(connection != null) {
//						connection.close();
//					}
//				}catch(SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			return travelCollection;
//		}
					//GetALL
		
		
		@Override
		public List<FavoritePlace> getAll() {
			String sql = "";
			Connection connection = null;
			PreparedStatement ps = null;
			List<FavoritePlace> favoritePlaceList = new ArrayList<FavoritePlace>();
			sql = "SELECT Favorite_Place.MB_NO, Favorite_Place.PC_ID , Place.PC_NAME, PC_ADDRESS , LAT, LNG , PC_PHONE " +
					"FROM FunTaipei.Favorite_Place " + 
					"JOIN FunTaipei.MEMBER " + 
					"on Favorite_Place.MB_NO = FunTaipei.MEMBER.MB_NO " + 
					"JOIN PLACE " +
					"ON Favorite_Place.PC_ID = Place.PC_ID  ;"  ;
				
			try {
				connection = DriverManager.getConnection(URL,USER,PASSWORD);
				ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					int mb_no = rs.getInt(1);
					int pc_id = rs.getInt(2);
					String pc_name = rs.getString(3);
					String pc_address = rs.getString(4);
					double lat = rs.getDouble(5);
					double lng = rs.getDouble(6);
					String pc_Phone = rs.getString(7);
					
					FavoritePlace favoritePlace = new FavoritePlace(mb_no ,pc_id, pc_name,pc_address, lat, lng, pc_Phone);
					favoritePlaceList.add(favoritePlace);
				}
			return favoritePlaceList;
		}catch(SQLException e) {
			e.printStackTrace();
			}finally {
				try {
					if(ps != null) {
						ps.close();
					}
					if(connection !=null) {
						ps.close();
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return favoritePlaceList;
		}
		
	
		public List<FavoritePlace> findById(int mb_no) {
			String sql = "SELECT Favorite_Place.MB_NO, Favorite_Place.PC_ID , Place.PC_NAME, PC_ADDRESS , LAT, LNG , PC_PHONE FROM FunTaipei.Favorite_Place JOIN FunTaipei.MEMBER on Favorite_Place.MB_NO = FunTaipei.MEMBER.MB_NO JOIN PLACE ON Favorite_Place.PC_ID = Place.PC_ID  WHERE Favorite_Place.MB_NO = ?;"  ;
			
			Connection conn = null;
			PreparedStatement ps = null;
			List<FavoritePlace> favoritePlaces = new ArrayList<FavoritePlace>();
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, mb_no);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					int mB_no = rs.getInt(1);
					int pc_id = rs.getInt(2);
					String pc_name = rs.getString(3);
					String pc_address = rs.getString(4);
					double lat = rs.getDouble(5);
					double lng = rs.getDouble(6);
					String pc_Phone = rs.getString(7);
					FavoritePlace favoritePlace = new FavoritePlace(mB_no ,pc_id, pc_name,pc_address, lat, lng, pc_Phone);
					favoritePlaces.add(favoritePlace);
					
					
				}
				return favoritePlaces;
			} catch (SQLException e) {
				e.printStackTrace();
				
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return favoritePlaces;
		}
		
	}

	

