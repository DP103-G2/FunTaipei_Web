package dp103.gp2.server.group.Administrator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;
import static dp103.gp2.server.group.Common.Common.PASSWORD;
public class AdministratorMySql implements AdministratorDao {
	
	public AdministratorMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Administrator administrator) {
		int count = 0;
		String sql ="INSERT INTO Administrator (AD_NO,AD_PASSWORD) VALUES(? , ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, administrator.getAD_ID());
			ps.setString(2, administrator.getAD_NO());
			ps.setString(3, administrator.getAD_PASSWORD());
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
	public int update(Administrator administrator) {
		int count = 0;
		String sql = "";
		sql = "UPDATE Administrator SET AD_NO = ?,AD_PASSWORD = ? WHERE AD_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, administrator.getAD_NO());
			ps.setString(2, administrator.getAD_PASSWORD());
			count = ps.executeUpdate();
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
		return count;
	}

	@Override
	public int delete(int AD_ID) {
		int count = 0;
		String sql = "DELETE FROM Administrator WHERE AD_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, AD_ID);
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
	public Administrator findById(int AD_ID) {
		String sql = "SELECT AD_NO,AD_PASSWORD FROM Administrator WHERE AD_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Administrator administrator = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, AD_ID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String AD_NO = rs.getString(1);
				String AD_PASSWORD = rs.getString(2);
				Administrator administrators = new Administrator(AD_ID, AD_NO, AD_PASSWORD);
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
		return administrator;
	}

	@Override
	public List<Administrator> getAll() {
		String sql = "SELECT AD_ID,AD_NO,AD_PASSWORD FROM FunTaipei.Administrator ORDER BY AD_ID DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Administrator> administratorsList = new ArrayList<Administrator>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int AD_ID = rs.getInt(1);
				String AD_NO = rs.getString(2);
				String AD_PASSWORD = rs.getString(3);
				Administrator administrator = new Administrator(AD_ID, AD_NO, AD_PASSWORD);
				administratorsList.add(administrator);
			}
			return administratorsList;
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
		return administratorsList;
		
	}
	
	

}
