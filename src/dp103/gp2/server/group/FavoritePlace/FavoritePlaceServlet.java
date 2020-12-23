package dp103.gp2.server.group.FavoritePlace;

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

import dp103.gp2.server.group.Member.Member;
import dp103.gp2.server.group.TravelCollection.TravelCollection;
import dp103.gp2.server.group.TravelCollection.TravelCollectionDaoMySql;

@SuppressWarnings("serial")
@WebServlet("/FavoritePlaceServlet")

public class FavoritePlaceServlet extends HttpServlet {
	
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	FavoritePlaceDao favoritePlaceDao = null;
   
   

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
			System.out.println("input" + jsonIn);
			
			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			if(favoritePlaceDao == null) {
				favoritePlaceDao = new FavoritePlaceDaoMySql();
			}
			String action =  jsonObject.get("action").getAsString();
			
			if(action.equals("getAll")) {
				List<FavoritePlace> favoritePlaces = favoritePlaceDao.getAll();
				writeText(response,gson.toJson(favoritePlaces));
			}else if(action.equals("favoritePlaceInsert")|| action.equals("favoritePlaceUpdate")) {
				String favoritePlaceJson = jsonObject.get("favoritePlace").getAsString();
				System.out.println("favoritePlaceJson = " + favoritePlaceJson);
				FavoritePlace favoritePlace = gson.fromJson(favoritePlaceJson, FavoritePlace.class);
				int count = 0;
				if(action.equals("favoritePlaceInsert")) {
					count = favoritePlaceDao.insert(favoritePlace);
			}else if(action.equals("favoritePlaceUpdate")){
				count = favoritePlaceDao.update(favoritePlace);
			}
				writeText(response,String.valueOf(count));
			}else if(action.equals("favoritePlaceDelete")) {
				int pc_id = jsonObject.get("Pc_id").getAsInt();
				int mb_no = jsonObject.get("mbNo").getAsInt();
				int count = favoritePlaceDao.delete(pc_id, mb_no);
				writeText(response, String.valueOf(count));
			}else if (action.equals("findById")) {
				int mb_no = jsonObject.get("mbNo").getAsInt();
				List<FavoritePlace> favoritePlace =  favoritePlaceDao.findById(mb_no);
				writeText(response, gson.toJson(favoritePlace));
			}else {
				writeText(response, "zz");
			
			}
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(favoritePlaceDao == null) {
			favoritePlaceDao = new FavoritePlaceDaoMySql();
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<FavoritePlace> favoritePlaces = favoritePlaceDao.getAll();
		writeText(response,gson.toJson(favoritePlaces));
	}

	private void writeText(HttpServletResponse response,String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output" + outText);
	}
}
