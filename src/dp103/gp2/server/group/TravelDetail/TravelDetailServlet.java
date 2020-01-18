package dp103.gp2.server.group.TravelDetail;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/TravelDetailServlet")

public class TravelDetailServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	TravelDetailDao travelDetailDao = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
			
			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			if(travelDetailDao == null) {
				travelDetailDao = new TravelDetailMySql();
			}
			String action = jsonObject.get("action").getAsString();
			
			if(action.equals("getAll")) {
				List<TravelDetail> travelDetails = travelDetailDao.getAll();
				writeText(response, gson.toJson(travelDetails));
				//01-02新增
			}else if(action.equals("findByTravelId")){
				int id = jsonObject.get("id").getAsInt();
				List<TravelDetail> travelDetails = travelDetailDao.findByTravelId(id);
				writeText(response, gson.toJson(travelDetails));
			}else if(action.equals("travelDetailInsert") || action.equals("travelDetailUpdate")) {
				String travelDetailJson = jsonObject.get("travelDetail").getAsString();
				System.out.println("travelDetailJson = " + travelDetailJson );
				TravelDetail travelDetail = gson.fromJson(travelDetailJson, TravelDetail.class);
				
				int count = 0;
				if(action.equals("travelDetailInsert")) {
					count = travelDetailDao.insert(travelDetail);
				}else if(action.equals("travelDetailUpdate")) {
					count = travelDetailDao.update(travelDetail);
				}
				writeText(response, String.valueOf(count));
			}else if(action.equals("travelDetailDelete")) {
				int travel_id = jsonObject.get("travel_id").getAsInt();
				int count = travelDetailDao.delete(travel_id);
				writeText(response, String.valueOf(count));
			}else if(action.equals("findById")){
				int travel_id = jsonObject.get("travel_id").getAsInt();
				TravelDetail travelDetail = travelDetailDao.findById(travel_id);
			}else {
				writeText(response,"");
			}
			}
		}
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		if(travelDetailDao == null) {
			travelDetailDao = new TravelDetailMySql();
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<TravelDetail> travelDetails = travelDetailDao.getAll();
		writeText(response, gson.toJson(travelDetails));
	}
	
	private void writeText(HttpServletResponse response,String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println(outText);
		System.out.println("output" + outText);
	}

}
