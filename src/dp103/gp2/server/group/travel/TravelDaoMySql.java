package dp103.gp2.server.group.travel;
import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;
import static dp103.gp2.server.group.Common.Common.PASSWORD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelDaoMySql implements TravelDao{
	
	public TravelDaoMySql(){
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//0221會員管理行程用新增可以直接新增到TravelList
	@Override
	public int insert(Travel travel, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO TRAVEL " + "(TRAVEL_NAME ,IMAGE, TRAVEL_STATUS, MB_NO) " + "VALUE(?, ?, '1', '33');";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, travel.getTravel_name());
			ps.setBytes(2, image);
			
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
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return count;
	}
	//更新
	@Override
	public int update(Travel travel, byte[] image) {
		int count = 0;
		String sql = "";
		if(image != null) {
			sql = "UPDATE Travel SET Travel_NAME = ?, IMAGE = ? WHERE TRAVEL_ID = ?;";
		}else {
			sql = "UPDATE Travel SET Travel_NAME = ? WHERE TRAVEL_ID = ?;";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, travel.getTravel_name());
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
	public int delete(int travel_id) {
		int count = 0;
		String sql = "DELETE FROM Travel WHERE TRAVEL_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travel_id);
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
	public Travel findById(int travel_id) {
		String sql = "SELECT TRAVEL_NAME FROM FunTaipei.TRAVEL WHERE TRAVEL_ID = ? ;";
		Connection connection = null;
		PreparedStatement ps = null;
		Travel travel = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, travel_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String travel_name = rs.getString(1);
				travel = new Travel(travel_id , travel_name);
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
		return travel;
	}
	@Override
	public List<Travel> getAll() {
		String sql = "SELECT travel_id, travel_name, travel_status FROM Travel WHERE travel_status = 1 ORDER BY travel_id DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Travel> travelList = new ArrayList<Travel>();
		
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int travel_id = rs.getInt(1);
				String travel_name = rs.getString(2);
				int travel_status = rs.getInt(3);
				Travel travel = new Travel(travel_id, travel_name, travel_status);
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
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return travelList;
	}

	@Override
	public byte[] getImage(int travel_id) {
		String sql = "SELECT IMAGE FROM Travel WHERE Travel_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
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
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	@Override
	public List<Travel> findBymemId(int memId) {
		String sql = "SELECT TRAVEL_ID, TRAVEL_NAME, IMAGE " + 
					 "FROM Travel " + 
					 "WHERE MB_NO = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Travel> travelList = new ArrayList<Travel>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, memId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int travel_id = rs.getInt(1);
				String travel_name = rs.getString(2);
				Travel travel = new Travel(memId,travel_id, travel_name);
				travelList.add(travel);
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
		return travelList;
	}
	@Override
	public Travel findByGroup(int id) {
		String sql = "SELECT travel_id, travel_name, travel_status, mb_no FROM FunTaipei.TRAVEL WHERE TRAVEL.TRAVEL_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Travel travel = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int travel_id = rs.getInt(1);
				String travel_name = rs.getString(2);
				int travel_status = rs.getInt(3);
				int mb_no = rs.getInt(4);
				travel = new Travel(travel_id, travel_name, travel_status, mb_no);
				
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
		
		return travel;
	}
	

	
}
