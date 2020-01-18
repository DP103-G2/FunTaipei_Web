package dp103.gp2.server.group.JoinGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/JoinGroupServlet")
public class JoinGroupServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charest=utf8";
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
		System.out.print("input: " + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (jgDao == null) {
			jgDao = new JoinGroupDaoSql();
		}
		
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			List<JoinGroup> jgs = jgDao.getAll();
			writeText(response, gson.toJson(jgs));
		} else if (action.equals("jgInsert") || action.equals("jgUpdate")) {
			String jgJson = jsonObject.get("jgs").getAsString();
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
