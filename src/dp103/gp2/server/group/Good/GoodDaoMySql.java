package dp103.gp2.server.group.Good;
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

public class GoodDaoMySql implements GoodDao{
	
	public GoodDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Good good) {
		int count = 0;
		String sql = "INSERT INTO Good (PC_ID,MB_NO) VALUES (?,?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, good.getPc_id());
			ps.setInt(2, good.getMb_no());
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
	public int update(Good good) {
		int count = 0;
		String sql = "";
		sql = "UPDATE GOOD SET PC_ID = ?, MB_NO = ?;";
		Connection  connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, good.getPc_id());
			ps.setInt(2, good.getMb_no());
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
	public int delete(int mb_no) {
		int count = 0;
		String sql = "DELETE FROM Good WHERE MB_NO = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, mb_no);
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
	public List<Good> getAll() {
		String sql = "SELECT PC_ID,MB_NO FROM Good ORDER BY PC_ID DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Good> goodList = new ArrayList<Good>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int pc_id = rs.getInt(1);
				int mb_no = rs.getInt(2);
				Good good = new Good(pc_id,mb_no);
			}
		return goodList;
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
		return goodList;
	}

	@Override
	public Good findById(int mb_no) {
		String sql = "SELECT mb_no FROM Good WHERE PC_ID =?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Good good = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, mb_no);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int pc_id = rs.getInt(1);
				Good goods = new Good(pc_id, mb_no);
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
		return good;
	}

}
