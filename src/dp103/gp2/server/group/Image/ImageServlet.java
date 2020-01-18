package dp103.gp2.server.group.Image;

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
import com.google.gson.JsonObject;

import dp103.gp2.server.group.Common.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/ImageServlet")

public class ImageServlet extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	ImageDao imageDao = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if(imageDao == null) {
			imageDao = new ImageDaoMySql();
		}
		String action = jsonObject.get("action").getAsString();
		if(action.equals("getAll")) {
			List<Image> images = imageDao.getAll();
			writeText(response, gson.toJson(images));
		}else if(action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int ig_no = jsonObject.get("ig_no").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = imageDao.getImage(ig_no);
			if(image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		}else if(action.equals("imageInsert") || action.equals("imageUpdate")) {
			String imageJson = jsonObject.get("image").getAsString();
			System.out.println("imageJson" + imageJson);
			Image image = gson.fromJson(imageJson, Image.class);
			byte[] images = null;
			
			if(jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if(imageBase64 != null && !imageBase64.isEmpty()) {
					images = Base64.getMimeDecoder().decode(imageBase64);
				}
			}
			int count = 0;
			if(action.equals("imageInsert")) {
				count = imageDao.insert(image, images);
			}else if(action.equals("imageUpdate")) {
				count = imageDao.update(image, images);
			}
			writeText(response, String.valueOf(count));
			}else if(action.equals("imageDelete")) {
				int ig_no = jsonObject.get("ig_no").getAsInt();
				int count = imageDao.delete(ig_no);
				writeText(response, String.valueOf(count));
			}else if(action.equals("findById")) {
				int id = jsonObject.get("ig_no").getAsInt();
				Image image = imageDao.findByID(id);
				writeText(response, gson.toJson(image));
			}else {
				writeText(response, "");
			}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(imageDao == null) {
			imageDao = new ImageDaoMySql();
		}
		Gson gson = new Gson();
		List<Image> image = imageDao.getAll();
		writeText(response,gson.toJson(image));
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output" + outText);
	}
}
