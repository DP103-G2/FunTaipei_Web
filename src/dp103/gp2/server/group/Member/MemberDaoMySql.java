package dp103.gp2.server.group.Member;

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
import java.util.Date;
import java.util.List;

public class MemberDaoMySql implements MemberDao {

	public MemberDaoMySql() {
		super();
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean login(String Mb_email, String Mb_passwrod) {
		boolean isValid = false;
		String sql = "SELECT MB_PASSWORD from `Member` WHERE MB_EMAIL = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, Mb_email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				isValid = rs.getString(1).equals(Mb_passwrod);
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

		return isValid;

	}

	@Override
	public int insert(Member member, byte[] image) {
		int m = getUserIdByEmail(member.getMb_email());
		if(m != 0) {
			return -1;
		}
		int count = 0;
		String sql = "INSERT INTO `Member`(Mb_no, Mb_email, Mb_password, Mb_name, Mb_gender, Mb_birthday, Mb_status, Mb_image) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, member.getMb_no());
			ps.setString(2, member.getMb_email());
			ps.setString(3, member.getMb_password());
			ps.setString(4, member.getMb_name());
			ps.setString(5, member.getMb_gender());
//			ps.setString(6, member.getMb_birthday());
			ps.setTimestamp(6, new Timestamp(member.getMb_birthday().getTime()));
			ps.setInt(7, member.getMb_Status());
			ps.setBytes(8, image);
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
	public int update(Member member, byte[] image) {
		int count = 0;
		String sql = "";
		if (image != null) {
			sql = "UPDATE `Member` SET mb_email = ?, mb_name = ?,mb_gender = ?,mb_birthday = ?, mb_image = ? WHERE mb_no = ?;";
		} else {
			sql = "UPDATE `Member` SET mb_email = ?, mb_name = ?,mb_gender = ?,mb_birthday = ? WHERE mb_no = ?;";
		}
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, member.getMb_email());
			// ps.setString(2, member.getMb_password());
			ps.setString(2, member.getMb_name());
			ps.setString(3, member.getMb_gender());
			// ps.setDate(5, member.getMb_birthday());
			ps.setTimestamp(4, new Timestamp(member.getMb_birthday().getTime()));
			if (image != null) {
				ps.setBytes(5, image);
				ps.setInt(6, member.getMb_no());
			} else {
				ps.setInt(5, member.getMb_no());
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
//	@Override
//	public int delete(int MB_NO) {
//		int count = 0;
//		String sql = "";
//		Connection connection = null;
//		PreparedStatement ps = null;
//		try {
//			connection = DriverManager.getConnection(URL,User,Password);
//			ps = connection.prepareCall(sql);
//			ps.setInt(1,MB_NO);
//			count = ps.executeUpdate();
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
//		return count;
//	}

	@Override
	public Member findById(int mb_no) {
		String sql = "SELECT mb_email,mb_password,mb_name,mb_gender,mb_birthday,mb_status FROM `Member` Where mb_no = ?;";
		Connection conn = null;
		PreparedStatement ps = null;
		Member member = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mb_no);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String mb_email = rs.getString(1);
				String mb_password = rs.getString(2);
				String mb_name = rs.getString(3);
				String mb_gender = rs.getString(4);
				Date mb_birthday = rs.getTimestamp(5);
				int mb_status = rs.getInt(6);
				member = new Member(mb_no, mb_email, mb_password, mb_name, mb_gender, mb_birthday, mb_status);
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
		return member;
	}

	@Override
	public List<Member> getAll() {
		String sql = "SELECT mb_no, mb_email,mb_password,mb_image,mb_name,mb_gender,mb_birthday,mb_status FROM `Member`;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Member> memberList = new ArrayList<Member>();

		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int mb_no = rs.getInt(1);
				String mb_email = rs.getString(2);
				String mb_password = rs.getString(3);
				byte[] image = rs.getBytes(4);
				String mb_name = rs.getString(5);
				String mb_gender = rs.getString(6);
				Date mb_birthday = rs.getDate(7);
				int mb_status = rs.getInt(8);
				Member member = new Member(mb_no, mb_email, mb_password, mb_name, mb_gender, mb_birthday, mb_status);
				memberList.add(member);
			}
			return memberList;
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
		return memberList;

	}

	@Override
	public byte[] getImage(int mb_no) {
		String sql = "SELECT mb_image FROM `Member` WHERE MB_NO = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, mb_no);
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
	public int getUserIdByEmail(String eb_email) {
		Connection connection = null;
		PreparedStatement ps = null;
		int mb_no = 0;
		String sql = "SELECT mb_no FROM `Member` WHERE mb_email = ?;";
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, eb_email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				mb_no = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mb_no;

	}


}