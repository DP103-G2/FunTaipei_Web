package dp103.gp2.server.group.group;


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
import com.google.gson.JsonObject;


import dp103.gp2.server.group.Common.ImageUtil;



@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	GroupDao groupDao = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
		//Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (groupDao == null) {
			groupDao = new GroupDaoSqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Group> groups = groupDao.getAll();
			writeText(response, gson.toJson(groups));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = groupDao.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("groupInsert") || action.equals("groupUpdate")) {
			String groupJson = jsonObject.get("group").getAsString();
			System.out.println("groupJson = " + groupJson);
			Group group = gson.fromJson(groupJson, Group.class);
			
			byte[] image = null;
			// 檢查是否上傳圖片
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);// 轉乘byte[]
				}
			}
			
			int count = 0;
			if (action.equals("groupInsert")) {
				count = groupDao.insert(group, image);
			} else if (action.equals("groupUpdate")) {
				count = groupDao.update(group, image);
			}
			writeText(response, String.valueOf(count));
		}  else if (action.equals("findById")) {
			int id = jsonObject.get("id").getAsInt();
			Group group = groupDao.findById(id);
			writeText(response, gson.toJson(group));
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if(groupDao == null) {
			groupDao = new GroupDaoSqlImpl();
		}
		List<Group> groups = groupDao.getAll();
		writeText(response, new Gson().toJson(groups));
		writeText(response, new GsonBuilder().setDateFormat("yyyy/MM/dd").create().toJson(groups));

	}

}
