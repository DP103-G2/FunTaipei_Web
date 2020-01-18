package dp103.gp2.server.group.TravelCollection;

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
@WebServlet("/TravelCollectionServlet")

public class TravelCollectionServlet extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	TravelCollectionDao travelCollectionDao = null;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
			System.out.println("input" + jsonIn);
			
			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			if(travelCollectionDao == null) {
				travelCollectionDao = new TravelCollectionDaoMySql();
			}
			String action =  jsonObject.get("action").getAsString();
			
			if(action.equals("getAll")) {
				List<TravelCollection> travelCollections = travelCollectionDao.getAll();
				writeText(response,gson.toJson(travelCollections));
			}else if(action.equals("travelCollectionInsert")|| action.equals("travelCollectionUpdate")) {
				String travelCollectionJson = jsonObject.get("travelCollection").getAsString();
				System.out.println("travelCollection = " + travelCollectionJson);
				TravelCollection travelCollection = gson.fromJson(travelCollectionJson, TravelCollection.class);
				int count = 0;
				if(action.equals("travelCollectionInsert")) {
					count = travelCollectionDao.insert(travelCollection);
			}else if(action.equals("travelCollectionUpdate")){
				count = travelCollectionDao.update(travelCollection);
			}
				writeText(response,String.valueOf(count));
			}else if(action.equals("travelCollectionDelete")) {
				int mb_no = jsonObject.get("mb_no").getAsInt();
				int travel_id = jsonObject.get("travel_id").getAsInt();
				int count = travelCollectionDao.delete(mb_no, travel_id);
				writeText(response,String.valueOf(count));
//			}else if(action.equals("findById")) {
//				int travel_id = jsonObject.get("travel_id").getAsInt();
//				int mb_no = jsonObject.get("mb_no").getAsInt();
//				TravelCollection travelCollection = travelCollectionDao.findById(travel_id, mb_no);
//				writeText(response, gson.toJson(travelCollection));
			}else {
				writeText(response,"");
			}	
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		if(travelCollectionDao == null) {
			travelCollectionDao = new TravelCollectionDaoMySql();
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<TravelCollection> travelCollection = travelCollectionDao.getAll();
		writeText(response,gson.toJson(travelCollection));
	}
	
	private void writeText(HttpServletResponse response,String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output" + outText);
	}
}
