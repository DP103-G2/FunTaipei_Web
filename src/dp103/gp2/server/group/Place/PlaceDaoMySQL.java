package dp103.gp2.server.group.Place;

import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.PASSWORD;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlaceDaoMySQL implements PlaceDao {
	public PlaceDaoMySQL() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		}
	
	@Override
	public int insert(Place place, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO FunTaipei.Place" + "(PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, place.getPC_NAME());
			ps.setString(2, place.getPC_PHONE());
			ps.setString(3, place.getPC_ADDRESS());
			ps.setDouble(4, place.getLAT());
			ps.setDouble(5, place.getLNG());
			ps.setInt(6, place.getVIEW_ALL());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	@Override
	public int update(Place place, byte[] image) {
		int count = 0;
		String sql = "UPDATE FunTaipei.Place SET PC_NAME = ?, PC_PHONE = ?, PC_ADDRESS = ?, LAT = ?, LNG = ?, VIEW_ALL = ?,PC_STATUS = ?  WHERE id = ?;";
		
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps = connection.prepareStatement(sql);
			ps.setString(1, place.getPC_NAME());
			ps.setString(2, place.getPC_PHONE());
			ps.setString(3, place.getPC_ADDRESS());
			ps.setDouble(4, place.getLAT());
			ps.setDouble(5, place.getLNG());
			ps.setInt(6, place.getVIEW_ALL());
			
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	@Override
	public int delete(int id) {
		int count = 0;
		String sql = "DELETE FROM FunTaipei.Place WHERE id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public Place findById(int id) {
		
		String sql = "SELECT PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS  FROM FunTaipei.Place WHERE id = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Place place = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String pc_Name = rs.getString(2);
				String pc_Phone = rs.getString(3);
				String pc_Address = rs.getString(4);
				double lat = rs.getDouble(5);
				double lng = rs.getDouble(6);
				int view_All = rs.getInt(7);
				int pc_Status = rs.getInt(8);
				place = new Place(id, pc_Name, pc_Phone, pc_Address, lat, lng, view_All, pc_Status);
			}
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
		return place;
	}
	
	//(PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS) 
	@Override
	public List<Place> getAll() {
		String sql = "SELECT PC_id, PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS, Image FROM  FunTaipei.Place;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Place> placeList = new ArrayList<Place>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String pc_Name = rs.getString(2);
				String pc_Phone = rs.getString(3);
				String pc_Address = rs.getString(4);
				double lat = rs.getDouble(5);
				double lng = rs.getDouble(6);
				int view_All = rs.getInt(7);
				int pc_Status = rs.getInt(8);
				
				
				Place place = new Place(id, pc_Name, pc_Phone, pc_Address, lat, lng, view_All, pc_Status);
				placeList.add(place);
			}
			return placeList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return placeList;
	}
	
	@Override
	public List<Place> getPlace() {
		String sql = "SELECT PC_id, PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS, Image FROM  FunTaipei.Place WHERE VIEW_ALL = 1;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Place> placeList = new ArrayList<Place>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String pc_Name = rs.getString(2);
				String pc_Phone = rs.getString(3);
				String pc_Address = rs.getString(4);
				double lat = rs.getDouble(5);
				double lng = rs.getDouble(6);
				int view_All = rs.getInt(7);
				int pc_Status = rs.getInt(8);
				
				
				Place place = new Place(id, pc_Name, pc_Phone, pc_Address, lat, lng, view_All, pc_Status);
				placeList.add(place);
			}
			return placeList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return placeList;
	}
	
	@Override
	public List<Place> getHotel() {
		String sql = "SELECT PC_id, PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS, Image FROM  FunTaipei.Place WHERE VIEW_ALL = 2;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Place> placeList = new ArrayList<Place>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String pc_Name = rs.getString(2);
				String pc_Phone = rs.getString(3);
				String pc_Address = rs.getString(4);
				double lat = rs.getDouble(5);
				double lng = rs.getDouble(6);
				int view_All = rs.getInt(7);
				int pc_Status = rs.getInt(8);
				
				
				Place place = new Place(id, pc_Name, pc_Phone, pc_Address, lat, lng, view_All, pc_Status);
				placeList.add(place);
			}
			return placeList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return placeList;
	}
	
	@Override
	public List<Place> getRestaurant() {
		String sql = "SELECT PC_id, PC_NAME, PC_PHONE, PC_ADDRESS, LAT, LNG, VIEW_ALL, PC_STATUS, Image FROM  FunTaipei.Place WHERE VIEW_ALL = 3;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Place> placeList = new ArrayList<Place>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String pc_Name = rs.getString(2);
				String pc_Phone = rs.getString(3);
				String pc_Address = rs.getString(4);
				double lat = rs.getDouble(5);
				double lng = rs.getDouble(6);
				int view_All = rs.getInt(7);
				int pc_Status = rs.getInt(8);
				
				
				Place place = new Place(id, pc_Name, pc_Phone, pc_Address, lat, lng, view_All, pc_Status);
				placeList.add(place);
			}
			return placeList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return placeList;
	}

	@Override
	public byte[] getImage(int id) {
		
		String sql =  "SELECT image FROM place WHERE pc_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
}
