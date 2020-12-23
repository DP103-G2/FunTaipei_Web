package dp103.gp2.server.group.group;


import java.util.List;

public interface GroupDao {
	int insert(Group group, byte[] image);
	int update(Group group, byte[] image);
	int delete(int id);
	Group findById(int id);
	
	List<Group> findByTravelId(int id);
	List<Group> getAll();
	List<Group> findByMbNo(int id);
	List<Group> findEndGroup(int id);
	List<Group> findMaster (int id);
	List<Group> findFinish (int id);
	byte[] getImage(int id);
}
