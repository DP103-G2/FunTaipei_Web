package dp103.gp2.server.group.Administrator;

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
@WebServlet("/AdministratorServlet")

public class AdministratorServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	AdministratorDao administratorDao = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if(administratorDao == null) {
			administratorDao = new AdministratorMySql();
		}
		String action = jsonObject.get("action").getAsString();
		
		if(action.equals("getAll")) {
			List<Administrator> administrators = administratorDao.getAll();
			writeText(response, gson.toJson(administrators));
		}else if(action.equals("administratorInsert") || action.equals("administratorUpdate")) {
			String administratorJson = jsonObject.get("administrator").getAsString();
			System.out.println("administratorJson" + administratorJson);
			Administrator administrator = gson.fromJson(administratorJson, Administrator.class);
			int count = 0;
			if(action.equals("administratorInsert")) {
				count = administratorDao.insert(administrator);
			}else if(action.equals("administratorUpdate")) {
				count = administratorDao.update(administrator);
			}
			writeText(response, String.valueOf(count));
		}else if(action.equals("administratorDelete")) {
			int AD_ID = jsonObject.get("AD_ID").getAsInt();
			int count = administratorDao.delete(AD_ID);
			writeText(response, String.valueOf(count));
		}else if(action.equals("findById")) {
			int id = jsonObject.get("AD_ID").getAsInt();
			Administrator administrator = administratorDao.findById(id);
			writeText(response, gson.toJson(administrator));
		}else {
			writeText(response, "");
		}
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		if(administratorDao == null) {
			administratorDao = new AdministratorMySql();
			}
		Gson gson = new Gson();
		List<Administrator> administrator = administratorDao.getAll();
		writeText(response,gson.toJson(administrator));
		
	}
	
	private void writeText(HttpServletResponse response,String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
