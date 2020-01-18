package dp103.gp2.server.group.Place;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import dp103.gp2.server.group.Common.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@WebServlet("/PlaceServlet")
public class PlaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
    PlaceDao placeDao = null;
    
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (placeDao == null) {
			placeDao = new PlaceDaoMySQL();
		}
		
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getRestaurant")) {
			List<Place> places3 = placeDao.getRestaurant();
			writeText(response, gson.toJson(places3));
		}
		
		else if (action.equals("getHotel")) {
			List<Place> places2 = placeDao.getHotel();
			writeText(response, gson.toJson(places2));
		}
		else if (action.equals("getPlace")) {
			List<Place> places = placeDao.getPlace();
			writeText(response, gson.toJson(places));
		}
		else if (action.equals("getAll")) {
			List<Place> places = placeDao.getAll();
			writeText(response, gson.toJson(places));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = placeDao.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("placeInsert") || action.equals("placeUpdate")) {
			String placeJson = jsonObject.get("place").getAsString();
			System.out.println("placeJson = " + placeJson);
			Place place = gson.fromJson(placeJson, Place.class);
			byte[] image = null;
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);
				}
			}
			int count = 0;
			if (action.equals("placeInsert")) {
				count = placeDao.insert(place, image);
			} else if (action.equals("placeUpdate")) {
				count = placeDao.update(place, image);
			}
			writeText(response, String.valueOf(count));
		} else if (action.equals("placeDelete")) {
			int placeId = jsonObject.get("placeId").getAsInt();
			int count = placeDao.delete(placeId);
			writeText(response, String.valueOf(count));
		} else if (action.equals("findById")) {
			int id = jsonObject.get("id").getAsInt();
			Place place = placeDao.findById(id);
			writeText(response, gson.toJson(place));
		} else {
			writeText(response, "");
		}
	}
		
		private void writeText(HttpServletResponse response, String outText) throws IOException {
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.print(outText);
			// 將輸出資料列印出來除錯用
			System.out.println("output: " + outText);
		}
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			if (placeDao == null) {
				placeDao = new PlaceDaoMySQL();
			}
			List<Place> places = placeDao.getAll();
			writeText(response, new Gson().toJson(places));
		}
}
