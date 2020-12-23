package dp103.gp2.server.group.TravelCollection;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import dp103.gp2.server.group.Common.ImageUtil;
import dp103.gp2.server.group.travel.Travel;
import dp103.gp2.server.group.travel.TravelDao;

@SuppressWarnings("serial")
@WebServlet("/TravelCollectionServlet")

public class TravelCollectionServlet extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	TravelCollectionDao travelCollectionDao = null;
	TravelDao travelDao = null;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input" + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if(travelCollectionDao == null) {
			travelCollectionDao = new TravelCollectionDaoMySql();
		}
		String action =  jsonObject.get("action").getAsString();
		
		if(action.equals("getAll")) {
			int memId = jsonObject.get("memId").getAsInt();
			List<TravelCollection> travelCollections = travelCollectionDao.getAll(memId);
			writeText(response,gson.toJson(travelCollections));
			//0211新增getImage方法
		}else if(action.equals("getImage")){
			OutputStream os = response.getOutputStream();
			int travel_id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = travelCollectionDao.getImage(travel_id);
			if(image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		//0122新增
		}else if(action.equals("getTravelInfo")){
			int memId = jsonObject.get("memId").getAsInt();
			List<Travel> travels = travelCollectionDao.getTravelInfo(memId);
			writeText(response,  gson.toJson(travels));
		}else if(action.equals("insert")){
			
			
		} else if(action.equals("getMemberInfo")){
			List<TravelCollection> travelCollections = travelCollectionDao.getMemberInfo();
			writeText(response,gson.toJson(travelCollections));
		//增加
		}else if(action.equals("travelCollectionInsert")) {
			int count = 0;
			int memId = jsonObject.get("memId").getAsInt();
			int travelId = jsonObject.get("travelId").getAsInt();
			count = travelCollectionDao.insert(memId, travelId);
			writeText(response, String.valueOf(count));
		//刪除
		}else if(action.equals("travelCollectionDelete")) {
			int memId = jsonObject.get("mb_no").getAsInt();
			int travel_id = jsonObject.get("travel_id").getAsInt();
			int count = travelCollectionDao.delete(memId, travel_id);
			List<Travel> travelCollections = travelCollectionDao.getTravelInfo(memId);
			writeText(response,String.valueOf(count));
		}else {
			writeText(response,"");
		}	
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		if(travelCollectionDao == null) {
			travelCollectionDao = new TravelCollectionDaoMySql();
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<TravelCollection> travelCollection = travelCollectionDao.getAll(18);
		writeText(response,gson.toJson(travelCollection));
	}
	
	private void writeText(HttpServletResponse response,String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output" + outText);
	}
}
