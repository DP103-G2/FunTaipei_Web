package dp103.gp2.server.group.Member;

import java.util.List;

import com.google.gson.JsonElement;

public interface MemberDao{
	//呈現資料列表
	List<Member> getAll();
	Member findById(int MB_NO);
	//增刪改查
	int insert(Member member, byte[] image);
	int update(Member member, byte[] image);
	//用String account 去找圖片id(以byte[]回傳
	byte[] getImage(int MB_NO);
	//判斷登入 布林
	boolean login(String Mb_email, String Mb_password);
	//登入後 用帳號取id
	int getUserIdByEmail(String Mb_gmail);
	
	//Member searchUser(JsonElement mb_mail);
}
