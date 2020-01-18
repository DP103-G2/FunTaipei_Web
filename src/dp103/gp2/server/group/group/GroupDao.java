package dp103.gp2.server.group.group;


import java.util.List;

public interface GroupDao {
	int insert(Group group, byte[] image);
	int update(Group group, byte[] image);
	Group findById(int id);
	List<Group> findByTravelId(int id);
	List<Group> getAll();
	byte[] getImage(int id);
}
