package dp103.gp2.server.group.JoinGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dp103.gp2.server.group.Common.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/JoinGroupServlet")
public class JoinGroupServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	JoinGroupDao jgDao = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (jgDao == null) {
			jgDao = new JoinGroupDaoSql();
		}
		
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			List<JoinGroup> jgs = jgDao.getAll();
			writeText(response, gson.toJson(jgs));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = jgDao.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
			
		}
		
			else if (action.equals("jgInsert") || action.equals("jgUpdate")) {
			String jgJson = jsonObject.get("jg").getAsString();
			System.out.println("jgJson = " + jgJson);
			JoinGroup jg = gson.fromJson(jgJson, JoinGroup.class);
			int count = 0;
			if (action.equals("jgInsert")) {
				count = jgDao.insert(jg);
			} else if (action.equals("jgUpdate")) {
				count = jgDao.update(jg);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("findByID")) {
			int id = jsonObject.get("id").getAsInt();
			JoinGroup jg = jgDao.findById(id);
			writeText(response, gson.toJson(jg));
		}  else if(action.equals("findMember")) {
			int id = jsonObject.get("id").getAsInt();
			List<JoinGroup> joinGroups = jgDao.findMember(id);
			writeText(response, gson.toJson(joinGroups));
		} else if (action.equals("findByMb")) {
			int mb = jsonObject.get("mb").getAsInt();
			int gp = jsonObject.get("gp").getAsInt();
			JoinGroup jg = jgDao.findByMb(mb, gp);
			writeText(response, gson.toJson(jg));
			
		} else if (action.equals("delete")) {
			int mb = jsonObject.get("mb").getAsInt();
			int gp = jsonObject.get("gp").getAsInt();
			int count = jgDao.delete(mb, gp);
			writeText(response, String.valueOf(count));
			
			
		} else {
			writeText(response, "");
		}
		
	}
       
    
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		
		System.out.println("output: " + outText);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (jgDao == null) {
			jgDao = new JoinGroupDaoSql();
		}
		List<JoinGroup> jgs = jgDao.getAll();
		writeText(response, new Gson().toJson(jgs));
	}
	

}
