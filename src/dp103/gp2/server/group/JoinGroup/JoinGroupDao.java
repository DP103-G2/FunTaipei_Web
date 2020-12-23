package dp103.gp2.server.group.JoinGroup;

import java.util.List;


public interface JoinGroupDao {
	int insert(JoinGroup jg);
	int update(JoinGroup jg);
	int delete(int mb, int gp);
	JoinGroup findById(int id);
	JoinGroup findByMb (int mb, int gp);
	List<JoinGroup> getAll();
	List<JoinGroup> findMember (int id);
	byte[] getImage(int id);
}