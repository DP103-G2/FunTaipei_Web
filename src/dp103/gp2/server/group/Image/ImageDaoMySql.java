package dp103.gp2.server.group.Image;
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

public class ImageDaoMySql implements ImageDao{
	
	public ImageDaoMySql() {
		super();
		try {
		 Class.forName(CLASS_NAME);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Image image, byte[] images) {
		int count = 0;
		String sql = "Insert INTO IMAGE(IG) VALUES(?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps.setInt(1, image.getIG_NO());
			ps.setInt(2, image.getPC_ID());
			ps.setBytes(3, image.getImage());
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
	public int update(Image image, byte[] images) {
		
		return 0;
	}

	@Override
	public int delete(int ig_no) {
		int count = 0;
		String sql = "DELETE FROM Image WHERE IG_NO = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps.setInt(1, ig_no);
			count = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(connection != null)
					connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
		return count;
	}

	@Override
	public Image findByID(int ig_no) {
		String sql = "SELECT IG_NO,PC_ID,IG FROM IMAGE WHERE IG_NO = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		Image image = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, ig_no);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int pc_id = rs.getInt(1);
				byte[] ig = rs.getBytes(2);
				Image images = new Image(ig_no, pc_id ,ig);
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
	public List<Image> getAll() {
		String sql = "SELECT IG_NO,PC_ID,IG FROM IMAGE ORDER BY IG_NO DESC;";
		Connection connection = null;
		PreparedStatement ps = null;
		List<Image> imageList = new ArrayList<Image>();
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int ig_no = rs.getInt(1);
				int pc_id = rs.getInt(2);
				Image image = new Image(ig_no,pc_id,null);
				imageList.add(image);
			}
			return imageList;
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
		return imageList;
	}

	@Override
	public byte[] getImage(int ig_no) {
		String sql = "SELECT IG FROM IMAGE WHERE IG_NO = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, ig_no);
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
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	
	
}
