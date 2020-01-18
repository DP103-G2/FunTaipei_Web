package dp103.gp2.server.group.Good;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GoodServlet extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	GoodDao goodDao = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		System.out.println("input" + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if(goodDao == null) {
			goodDao = new GoodDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		
		if(action.equals("getAll")) {
			List<Good> goods = goodDao.getAll();
			writeText(response, gson.toJson(goods));
		}else if(action.equals("goodInsert") || action.equals("goodUpdate")) {
			String goodJson = jsonObject.get("good").getAsString();
			System.out.println("goodJson = " + goodJson);
			Good good = gson.fromJson(goodJson, Good.class);
			
			int count = 0;
			if(action.equals("goodInsert")){
				count = goodDao.insert(good);
			}else if(action.equals("goodUpdate")) {
				count = goodDao.update(good);
			}
			writeText(response, String.valueOf(count));
		}else if(action.equals("goodDelete")) {
			int mb_no = jsonObject.get("MB_NO").getAsInt();
			int count = goodDao.delete(mb_no);
			writeText(response, String.valueOf(count));
		}else if(action.equals("findById")) {
			int mb_no = jsonObject.get("mb_no").getAsInt();
			Good good = goodDao.findById(mb_no);
			writeText(response, gson.toJson(good));
		}else {
			writeText(response, "");
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(goodDao == null) {
			goodDao = new GoodDaoMySql();
		}
		Gson gson = new Gson();
		List<Good> good = goodDao.getAll();
		writeText(response, gson.toJson(good));
	}
	private void writeText(HttpServletResponse response,String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output" + outText);
	}
}
