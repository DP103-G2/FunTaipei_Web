package dp103.gp2.server.group.Member;

import java.util.List;

public interface MemberDao{
	List<Member> getAll();
	Member findById(int MB_NO);
	int insert(Member member, byte[] image);
	int update(Member member, byte[] image);
	byte[] getImage(int MB_NO);
	boolean login(String Mb_email, String Mb_password);
}
