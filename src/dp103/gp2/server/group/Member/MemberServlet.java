package dp103.gp2.server.group.Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dp103.gp2.server.group.Common.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/MemberServlet")

public class MemberServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset = utf-8";
	MemberDao memberDao = null;
	private JsonElement mb_mail;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//Gson gson = new Gson;
		Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn.toString());
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (memberDao == null) {
			memberDao = new MemberDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getAll")) {
			List<Member> members = memberDao.getAll();
			writeText(response, gson.toJson(members));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = memberDao.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("memberInsert") || action.equals("memberUpdate")) {
			String memberJson = jsonObject.get("member").getAsString();
			System.out.println("memberJson = " + memberJson);
			Member member = gson.fromJson(memberJson, Member.class);
			byte[] image = null;
			//檢查是否上傳照片
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);
				}
			}
			int count = 0;
			if (action.equals("memberInsert")) {
				count = memberDao.insert(member, image);
			} else if (action.equals("memberUpdate")) {
				count = memberDao.update(member, image);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("login")) {//判斷登入
			String email = jsonObject.get("email").getAsString();
			String password = jsonObject.get("password").getAsString();
			boolean isValid = memberDao.login(email, password);
			writeText(response, String.valueOf(isValid));
		} 
		
		else if (action.equals("getUserIdByEmail")) {
			int mb_no = 0;
			String email = jsonObject.get("email").getAsString();
			mb_no = memberDao.getUserIdByEmail(email);
			writeText(response, String.valueOf(mb_no));
		}
		//登入：回傳資料
		else if (action.equals("findById")) {
			int mb_no = jsonObject.get("id").getAsInt();
			Member member = memberDao.findById(mb_no);
			writeText(response, gson.toJson(member));
			
//		}else if (action.equals("searchUser")) {
//			String email = jsonObject.get("email").getAsString();
//			Member member = memberDao.searchUser(mb_mail);
//			writeText(response, gson.toJson(member));
		}else {
			writeText(response, "");
		}
		

	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (memberDao == null) {
			memberDao = new MemberDaoMySql();
		}
		List<Member> members = memberDao.getAll();
		writeText(response, new Gson().toJson(members));
	}
}
