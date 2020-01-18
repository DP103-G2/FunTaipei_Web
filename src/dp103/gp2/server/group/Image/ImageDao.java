package dp103.gp2.server.group.Image;

import java.util.List;

public interface ImageDao {
	
	int insert(Image image, byte[] images);
	
	int update(Image image, byte[] images);
	
	int delete(int ig_no);
	
	Image findByID(int ig_no);
	
	List<Image> getAll();
	
	byte[] getImage(int ig_id);

}
