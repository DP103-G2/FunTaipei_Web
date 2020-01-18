package dp103.gp2.server.group.JoinGroup;
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




public class JoinGroupDaoSql implements JoinGroupDao {

		public JoinGroupDaoSql() {
			super();
			try {
				Class.forName(CLASS_NAME);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int insert(JoinGroup jg) {
			int count = 0;
			String sql = "insert into FunTaipei.Join_Group" + "(GP_ID, MB_NO, CHECK_IN)" + "values(?, ?, ?);"
					+ "update FunTaipei.Group_ALL set GP_ENROLLMENT = ? where GP_ID = ?;";
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = connection.prepareStatement(sql);
				ps.setInt(1, jg.getGP_ID());
				ps.setInt(2, jg.getMB_NO());
				ps.setInt(3, jg.getCHECK_IN());
				ps.setInt(4, jg.getGP_ENROLLMENT());
				ps.setInt(5, jg.getGP_ENROLLMENT());
				ps.setInt(6, jg.getGP_ID());
				count = ps.executeUpdate();
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
			return count;
		}

		@Override
		public int update(JoinGroup jg) {
			int count = 0;
			String sql = "update FunTaipei.Join_Group set CHECK_IN = ? where MB_NO = ?;";
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = connection.prepareStatement(sql);
				ps.setInt(1, jg.getCHECK_IN());
				ps.setInt(2, jg.getMB_NO());
				count = ps.executeUpdate();
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
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
			return count;
		}

		@Override
		public JoinGroup findById(int id) {
			String sql = "select MB_NO, CHECK_IN from FunTaipei.Join_Group where GP_ID = ?;";
			Connection conn = null;
			PreparedStatement ps = null;
			JoinGroup jg = null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					int mB_NO = jg.getMB_NO();
					int cHECK_IN = jg.getCHECK_IN();
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
				} catch (SQLException e ) {
					e.printStackTrace();
				}
			}
			return jg;
		}

		@Override
		public List<JoinGroup> getAll() {
			String sql = "select GP_ID, MB_NO, CHECK_IN from FunTaipei.Join_Group;";
			Connection connection = null;
			PreparedStatement ps = null;
			List<JoinGroup> jgList = new ArrayList<JoinGroup>();
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					int gP_ID = rs.getInt(1);
					int mB_NO = rs.getInt(2);
					int cHECK_IN = rs.getInt(3);
					JoinGroup jg = new JoinGroup(gP_ID, mB_NO, cHECK_IN);
					jgList.add(jg);
				}
				return jgList;
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
			return jgList;
		}

	}



