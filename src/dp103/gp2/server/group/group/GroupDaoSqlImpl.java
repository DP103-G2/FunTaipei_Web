package dp103.gp2.server.group.group;


import static dp103.gp2.server.group.Common.Common.CLASS_NAME;
import static dp103.gp2.server.group.Common.Common.PASSWORD;
import static dp103.gp2.server.group.Common.Common.URL;
import static dp103.gp2.server.group.Common.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GroupDaoSqlImpl implements GroupDao {

	public GroupDaoSqlImpl() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int insert(Group group, byte[] image) {
		int count = 0;

		String sql = "INSERT INTO FunTaipei.Group_ALL"
				+ "(GP_ID, TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, GP_STATUS, GP_NOTES, GP_IMAGE)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		String sql2 = "INSERT INTO FunTaipei.Join_Group (GP_ID, MB_NO, CHECK_IN, MASTER) VALUES(last_insert_id(),?,?,?);";

		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps2 = connection.prepareStatement(sql2);
			ps.setInt(1, group.getGP_ID());
			ps.setInt(2, group.getTRAVEL_ID());
			ps.setString(3, group.getGP_NAME());
			ps.setInt(4, group.getGP_ENROLLMENT());
			ps.setInt(5, group.getGP_UPPER());
			ps.setInt(6, group.getGP_LOWER());
			ps.setTimestamp(7, new Timestamp(group.getGP_DATESTAR().getTime()));
			ps.setTimestamp(8, new Timestamp(group.getGP_DATEEND().getTime() + 86399000));
			ps.setTimestamp(9, new Timestamp(group.getGP_EVENTDATE().getTime()+ 86399000));
			ps.setInt(10, group.getGP_STATUS());
			ps.setString(11, group.getGP_NOTES());
			ps.setBytes(12, image);
			ps2.setInt(1, group.getMB_NO());
			ps2.setInt(2, group.getCHECK_IN());
			ps2.setInt(3, group.getMASTER());
			count = ps.executeUpdate();
			count += ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (ps2 != null) {
					ps2.close();
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
	public int update(Group group, byte[] image) {
		int count = 0;
		String sql = "";

		if (image != null) {
			sql = "update FunTaipei.Group_ALL set TRAVEL_ID = ?, GP_NAME = ?, GP_ENROLLMENT = ?, GP_UPPER = ?, GP_LOWER = ?, GP_DATESTART = ?, GP_DATEEND = ?, GP_EVENTDATE = ?, GP_STATUS = ?, GP_NOTES = ?, GP_IMAGE = ?" 
					+ "WHERE GP_ID = ?;";
		} else {
			sql = "update FunTaipei.Group_ALL set TRAVEL_ID = ?, GP_NAME = ?, GP_ENROLLMENT = ?, GP_UPPER = ?, GP_LOWER = ?, GP_DATESTART = ?, GP_DATEEND = ?, GP_EVENTDATE = ?, GP_STATUS = ?, GP_NOTES = ?" 
					+ "WHERE GP_ID = ?;";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, group.getTRAVEL_ID());
			ps.setString(2, group.getGP_NAME());
			ps.setInt(3, group.getGP_ENROLLMENT());
			ps.setInt(4, group.getGP_UPPER());
			ps.setInt(5, group.getGP_LOWER());
			ps.setTimestamp(6, new Timestamp(group.getGP_DATESTAR().getTime()));
			ps.setTimestamp(7, new Timestamp(group.getGP_DATEEND().getTime()));
			ps.setTimestamp(8, new Timestamp(group.getGP_EVENTDATE().getTime()));
			ps.setInt(9, group.getGP_STATUS());
			ps.setString(10, group.getGP_NOTES());
			
			
			if (image != null) {
				ps.setBytes(11, image);
				ps.setInt(12, group.getGP_ID());
			} else {
				ps.setInt(11, group.getGP_ID());
			}
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
	public Group findById(int id) {
		String sql = "SELECT TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, GP_STATUS, GP_NOTES from FunTaipei.Group_ALL where GP_ID = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Group group = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int tRAVEL_ID = rs.getInt(1);
				String gP_NAME = rs.getString(2);
				int gP_ENROLLMENT = rs.getInt(3);
				int gP_UPPER = rs.getInt(4);
				int gP_LOWER = rs.getInt(5);
				Timestamp gP_DATESTART = rs.getTimestamp(6);
				Timestamp gP_DATEEND = rs.getTimestamp(7);
				Timestamp gP_EVENTDATE = rs.getTimestamp(8);
				int gP_STATUS = rs.getInt(9);
				String gP_NOTES = rs.getString(10);
//				int mB_NO = rs.getInt(11);
				group = new Group(id, tRAVEL_ID, gP_NAME, gP_ENROLLMENT, gP_UPPER, gP_LOWER, gP_DATESTART, gP_DATEEND,
						gP_EVENTDATE, gP_STATUS, gP_NOTES);
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
		return group;
	}
	//12-31新增
	@Override
	public List<Group> findByTravelId(int id) {
		String sql = "SELECT GP_ID,GP_NAME,GP_DATESTART,GP_DATEEND,GP_EVENTDATE FROM GROUP_ALL WHERE GP_ID = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Group> groupList = new ArrayList<Group>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int gP_ID = rs.getInt(1);
				String gP_NAME = rs.getString(2);
				Timestamp gP_DATESTART = rs.getTimestamp(3);
				Timestamp gP_DATEEND = rs.getTimestamp(4);
				Timestamp gP_EVENTDATE = rs.getTimestamp(5);
				Group group = new Group(gP_ID, id, gP_NAME, gP_DATESTART, gP_DATEEND, gP_EVENTDATE);
				groupList.add(group);
			}
		}catch (SQLException e) {
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
			return groupList;
		}

	@Override
	public List<Group> getAll() {
		String sql = "SELECT GP_ID, TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, "
				+ "GP_STATUS, GP_NOTES from FunTaipei.Group_ALL where GP_DATEEND >= now() and GP_STATUS = 1 and "
				+ "GP_UPPER > GP_ENROLLMENT and GP_DATESTART < now() ORDER BY GP_EVENTDATE ASC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Group> groupList = new ArrayList<Group>();
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int gP_ID = rs.getInt(1);
				int tRAVEL_ID = rs.getInt(2);
				String gP_NAME = rs.getString(3);
				int gP_ENROLLMENT = rs.getInt(4);
				int gP_UPPER = rs.getInt(5);
				int gP_LOWER = rs.getInt(6);
				Timestamp gP_DATESTART = rs.getTimestamp(7);
				Timestamp gP_DATEEND = rs.getTimestamp(8);
				Timestamp gP_EVENTDATE = rs.getTimestamp(9);
				int gP_STATUS = rs.getInt(10);
				String gP_NOTES = rs.getString(11);
//				int mB_NO = rs.getInt(12);
				Group group = new Group(gP_ID, tRAVEL_ID, gP_NAME, gP_ENROLLMENT, gP_UPPER, gP_LOWER, gP_DATESTART,
						gP_DATEEND, gP_EVENTDATE, gP_STATUS, gP_NOTES);
				groupList.add(group);
			}
			return groupList;
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
		return groupList;
	}

	@Override
	public byte[] getImage(int id) {
		String sql = "select GP_IMAGE from FunTaipei.Group_ALL where GP_ID = ?;";
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

	@Override
	public List<Group> findByMbNo(int id) {
		String sql = "select ga.GP_ID,TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, GP_STATUS, GP_NOTES, "
				+ "MB_NO, CHECK_IN, MASTER from FunTaipei.Join_Group jg Join FunTaipei.Group_ALL ga on jg.GP_ID = ga.GP_ID "
				+ "where GP_EVENTDATE >= now() and jg.MASTER = 0 and jg.MB_NO = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		List<Group> groups = new ArrayList<Group>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int gP_ID = rs.getInt(1);
				int tRAVEL_ID = rs.getInt(2);
				String gP_NAME = rs.getString(3);
				int gP_ENROLLMENT = rs.getInt(4);
				int gP_UPPER = rs.getInt(5);
				int gP_LOWER = rs.getInt(6);
				Timestamp gP_DATESTART = rs.getTimestamp(7);
				Timestamp gP_DATEEND = rs.getTimestamp(8);
				Timestamp gP_EVENTDATE = rs.getTimestamp(9);
				int gP_STATUS = rs.getInt(10);
				String gP_NOTES = rs.getString(11);
				int mB_NO = rs.getInt(12);
				int cHECK_IN = rs.getInt(13);
				int mASTER = rs.getInt(14);
				Group group = new Group(gP_ID, tRAVEL_ID, gP_NAME, gP_ENROLLMENT, gP_UPPER, gP_LOWER, gP_DATESTART,
						gP_DATEEND, gP_EVENTDATE, gP_STATUS, gP_NOTES, mB_NO, cHECK_IN, mASTER);
				groups.add(group);
			}
			return groups;
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
		return groups;
	}

	@Override
	public List<Group> findEndGroup(int id) {
		String sql = "select ga.GP_ID,TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, GP_STATUS, GP_NOTES, "
				+ "MB_NO, CHECK_IN, MASTER from FunTaipei.Join_Group jg Join FunTaipei.Group_ALL ga on jg.GP_ID = ga.GP_ID "
				+ "where GP_EVENTDATE <= now() and jg.MB_NO = ? and MASTER = 0;";
		Connection conn = null;
		PreparedStatement ps = null;
		List<Group> groups = new ArrayList<Group>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int gP_ID = rs.getInt(1);
				int tRAVEL_ID = rs.getInt(2);
				String gP_NAME = rs.getString(3);
				int gP_ENROLLMENT = rs.getInt(4);
				int gP_UPPER = rs.getInt(5);
				int gP_LOWER = rs.getInt(6);
				Timestamp gP_DATESTART = rs.getTimestamp(7);
				Timestamp gP_DATEEND = rs.getTimestamp(8);
				Timestamp gP_EVENTDATE = rs.getTimestamp(9);
				int gP_STATUS = rs.getInt(10);
				String gP_NOTES = rs.getString(11);
				int mB_NO = rs.getInt(12);
				int cHECK_IN = rs.getInt(13);
				int mASTER = rs.getInt(14);
				Group group = new Group(gP_ID, tRAVEL_ID, gP_NAME, gP_ENROLLMENT, gP_UPPER, gP_LOWER, gP_DATESTART,
						gP_DATEEND, gP_EVENTDATE, gP_STATUS, gP_NOTES, mB_NO, cHECK_IN, mASTER);
				groups.add(group);
			}
			return groups;
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
		return groups;
	}

	@Override
	public List<Group> findMaster(int id) {
		String sql = "select ga.GP_ID,TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, GP_STATUS, GP_NOTES, "
				+ "MB_NO, CHECK_IN, MASTER from FunTaipei.Join_Group jg Join FunTaipei.Group_ALL ga on jg.GP_ID = ga.GP_ID "
				+ "where ga.GP_EVENTDATE >= now() and jg.MASTER = 1 and jg.MB_NO = ? ORDER BY GP_EVENTDATE ASC;";
		Connection conn = null;
		PreparedStatement ps = null;
		List<Group> groups = new ArrayList<Group>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int gP_ID = rs.getInt(1);
				int tRAVEL_ID = rs.getInt(2);
				String gP_NAME = rs.getString(3);
				int gP_ENROLLMENT = rs.getInt(4);
				int gP_UPPER = rs.getInt(5);
				int gP_LOWER = rs.getInt(6);
				Timestamp gP_DATESTART = rs.getTimestamp(7);
				Timestamp gP_DATEEND = rs.getTimestamp(8);
				Timestamp gP_EVENTDATE = rs.getTimestamp(9);
				int gP_STATUS = rs.getInt(10);
				String gP_NOTES = rs.getString(11);
				int mB_NO = rs.getInt(12);
				int cHECK_IN = rs.getInt(13);
				int mASTER = rs.getInt(14);
				Group group = new Group(gP_ID, tRAVEL_ID, gP_NAME, gP_ENROLLMENT, gP_UPPER, gP_LOWER, gP_DATESTART,
						gP_DATEEND, gP_EVENTDATE, gP_STATUS, gP_NOTES, mB_NO, cHECK_IN, mASTER);
				groups.add(group);
			}
			return groups;
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
		return groups;
	}

	@Override
	public List<Group> findFinish(int id) {
		String sql = "select ga.GP_ID,TRAVEL_ID, GP_NAME, GP_ENROLLMENT, GP_UPPER, GP_LOWER, GP_DATESTART, GP_DATEEND, GP_EVENTDATE, GP_STATUS, GP_NOTES, "
				+ "MB_NO, CHECK_IN, MASTER from FunTaipei.Join_Group jg Join FunTaipei.Group_ALL ga on jg.GP_ID = ga.GP_ID "
				+ "where ga.GP_EVENTDATE < now() and jg.MASTER = 1 and ga.GP_EVENTDATE < now() and jg.MB_NO = ? ORDER BY GP_EVENTDATE ASC;";
		Connection conn = null;
		PreparedStatement ps = null;
		List<Group> groups = new ArrayList<Group>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int gP_ID = rs.getInt(1);
				int tRAVEL_ID = rs.getInt(2);
				String gP_NAME = rs.getString(3);
				int gP_ENROLLMENT = rs.getInt(4);
				int gP_UPPER = rs.getInt(5);
				int gP_LOWER = rs.getInt(6);
				Timestamp gP_DATESTART = rs.getTimestamp(7);
				Timestamp gP_DATEEND = rs.getTimestamp(8);
				Timestamp gP_EVENTDATE = rs.getTimestamp(9);
				int gP_STATUS = rs.getInt(10);
				String gP_NOTES = rs.getString(11);
				int mB_NO = rs.getInt(12);
				int cHECK_IN = rs.getInt(13);
				int mASTER = rs.getInt(14);
				Group group = new Group(gP_ID, tRAVEL_ID, gP_NAME, gP_ENROLLMENT, gP_UPPER, gP_LOWER, gP_DATESTART,
						gP_DATEEND, gP_EVENTDATE, gP_STATUS, gP_NOTES, mB_NO, cHECK_IN, mASTER);
				groups.add(group);
			}
			return groups;
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
		return groups;
	}

	@Override
	public int delete(int id) {
		int count = 0;
		String sql = "delete from FunTaipei.Join_Group where FunTaipei.Join_Group.GP_ID = ?;";
		String sql2 = "DELETE from FunTaipei.GROUP_ALL where GP_ID = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
			ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, id);
			count += ps2.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try {
				if (ps2 != null) {
					ps2.close();
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
