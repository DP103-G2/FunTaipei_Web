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
			String sql = "insert into FunTaipei.Join_Group" + "(GP_ID, MB_NO, CHECK_IN,MASTER)" + "values(?, ?, ?, ?);";
					//+ "update FunTaipei.Group_ALL set GP_ENROLLMENT = ? where GP_ID = ?;";
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = connection.prepareStatement(sql);
				ps.setInt(1, jg.getGP_ID());
				ps.setInt(2, jg.getMB_NO());
				ps.setInt(3, jg.getCHECK_IN());
				ps.setInt(4, jg.getMASTER());
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
			String sql = "select MB_NO, CHECK_IN, MASTER from FunTaipei.Join_Group where GP_ID = ?;";
			Connection conn = null;
			PreparedStatement ps = null;
			List<JoinGroup> jgList = new ArrayList<JoinGroup>();
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					int mB_NO = rs.getInt(1);
					int cHECK_IN = rs.getInt(2);
					int gP_ID = rs.getInt(3);
					int mASTER = rs.getInt(4);
					JoinGroup jg = new JoinGroup(gP_ID, mB_NO, cHECK_IN, mASTER);
					jgList.add(jg);
					
				}
				return (JoinGroup) jgList;
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
			return (JoinGroup) jgList;
		}

//		@Override
//		public List<JoinGroup> getAll() {
//			// TODO Auto-generated method stub
//			return null;
//		}

		@Override
		public List<JoinGroup> getAll() {
			String sql = "select * from FunTaipei.Join_Group;";
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
					int mASTER = rs.getInt(4);
					
					JoinGroup jg = new JoinGroup(gP_ID, mB_NO, cHECK_IN, mASTER);
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
		
		@Override
		public byte[] getImage(int id) {
			String sql = "select MB_IMAGE from FunTaipei.Member where MB_NO = ?;";
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
		
		public List<JoinGroup> findMember (int id) {
			String sql = "SELECT jg.MB_NO,MB_NAME,CHECK_IN from FunTaipei.Join_Group jg Join FunTaipei.Member mb on jg.MB_NO = mb.MB_NO where MASTER = 0 and jg.GP_ID = ?;";
			Connection conn = null;
			PreparedStatement ps = null;
			List<JoinGroup> joinGroups = new ArrayList<JoinGroup>();
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					int mB_NO = rs.getInt(1);
					String mB_NAME = rs.getString(2);
					int cHECK_IN = rs.getInt(3);
					JoinGroup joinGroup = new JoinGroup (mB_NO, mB_NAME, cHECK_IN);
					joinGroups.add(joinGroup);
				}
				return joinGroups;
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
			
			return joinGroups;
		}

		@Override
		public JoinGroup findByMb(int mb, int gp) {
			String sql = "SELECT jg.GP_ID, jg.MB_NO, jg.CHECK_IN, jg.MASTER from FunTaipei.Join_Group jg where jg.MB_NO = ? and jg.GP_ID = ?;";
			Connection conn = null;
			PreparedStatement ps = null;
			JoinGroup joinGroup = null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, mb);
				ps.setInt(2, gp);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					int gP_ID = rs.getInt(1);
					int mB_NO = rs.getInt(2);
					int cHECK_IN = rs.getInt(3);
					int mASTER = rs.getInt(4);
					joinGroup = new JoinGroup (gP_ID, mB_NO, cHECK_IN, mASTER);
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
			return joinGroup;
		}

		@Override
		public int delete(int mb, int gp) {
			int count = 0;
			String sql = "DELETE from FunTaipei.Join_Group where MB_NO = ? and GP_ID = ?;";
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, mb);
				ps.setInt(2, gp);
				count = ps.executeUpdate();
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
			return count;
		}

		
		

	}



