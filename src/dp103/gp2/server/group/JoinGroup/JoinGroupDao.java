package dp103.gp2.server.group.JoinGroup;

import java.util.List;


public interface JoinGroupDao {
	int insert(JoinGroup jg);
	int update(JoinGroup jg);
	JoinGroup findById(int id);
	List<JoinGroup> getAll();
}