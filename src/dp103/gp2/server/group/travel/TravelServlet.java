package dp103.gp2.server.group.travel;

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

@SuppressWarnings("serial")
@WebServlet("/TravelServlet")

public class TravelServlet extends HttpServlet{
		private final static String CONTENT_TYPE = "text/html; charset=utf-8";
		TravelDao travelDao = null;
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			request.setCharacterEncoding("utf-8");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			BufferedReader br = request.getReader();
			StringBuilder jsonIn = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null) {
				jsonIn.append(line);
			}
			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			if(travelDao == null) {
				travelDao = new TravelDaoMySql();
			}
			String action = jsonObject.get("action").getAsString();
			if(action.equals("getAll")) {
				List<Travel> travels = travelDao.getAll();
				writeText(response,gson.toJson(travels));
			}else if(action.equals("getImage")) {
				OutputStream os = response.getOutputStream();
				int travel_id = jsonObject.get("id").getAsInt();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				byte[] image = travelDao.getImage(travel_id);
				if(image != null) {
					image = ImageUtil.shrink(image, imageSize);
					response.setContentType("image/jpeg");
					response.setContentLength(image.length);
					os.write(image);
				}
			}else if(action.equals("travelInsert") || action.equals("travelUpdate")) {
				String travelJson = jsonObject.get("travel").getAsString();
				System.out.println("travelJson = " + travelJson);
				Travel travel = gson.fromJson(travelJson, Travel.class);
				byte[] image = null;
				
				if(jsonObject.get("imageBase64") != null) {
					String imageBase64 = jsonObject.get("imageBase64").getAsString();
					if(imageBase64 != null && !imageBase64.isEmpty()) {
						image = Base64.getMimeDecoder().decode(imageBase64);
					}
				}
				int count = 0;
				if(action.equals("travelInsert")) {
					count = travelDao.insert(travel, image);
				}else if(action.equals("travelUpdate")) {
					count = travelDao.update(travel, image);
				}
				writeText(response,String.valueOf(count));
			}else if(action.equals("travelDelete")) {
				int travel_id = jsonObject.get("id").getAsInt();
				int count = travelDao.delete(travel_id);
				writeText(response,String.valueOf(count));
			}else if(action.equals("findById")) {
				int id = jsonObject.get("id").getAsInt();
				Travel travel = travelDao.findById(id);
				writeText(response,gson.toJson(travel));
			}else {
				writeText(response,"");
			}
		}
			public void writeText(HttpServletResponse response, String outText) throws IOException{
				response.setContentType(CONTENT_TYPE);
				PrintWriter out = response.getWriter();
				out.print(outText);
				
				System.out.println("output" + outText);
			}
			public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
				if(travelDao == null) {
					travelDao = new TravelDaoMySql();
				}
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				List<Travel> travel = travelDao.getAll();
				writeText(response,gson.toJson(travel));
				}
			}
