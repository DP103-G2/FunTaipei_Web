package dp103.gp2.server.group.TravelReport;

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
@WebServlet("/TravelReportServlet")

public class TravelReportServlet extends HttpServlet{
		private final static String CONTENT_TYPE = "text/html; charset=utf-8";
		TravelReportDao travelReportDao = null;
		
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
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
		if(travelReportDao == null) {
			travelReportDao = new TravelReportMySql();
		}
		String action = jsonObject.get("action").getAsString();
		
		if(action.equals("getAll")) {
			List<TravelReport> travelReports = travelReportDao.getAll();
			writeText(response,gson.toJson(travelReports));
		}else if(action.equals("travelReportInsert") || action.equals("travelReportUpdate")) {
			String travelReportJson = jsonObject.get("travelReport").getAsString();
			System.out.println("travelReportJson = " + travelReportJson);
			TravelReport travelReport = gson.fromJson(travelReportJson, TravelReport.class);
		
		int count = 0;
		if(action.equals("travelReportInsert")) {
			count = travelReportDao.insert(travelReport);
		}else if(action.equals("travelReportUpdate")) {
			count = travelReportDao.update(travelReport);
				}
			writeText(response,String.valueOf(count));
		}else if(action.equals("travelReportDelete")) {
			int travel_report_id = jsonObject.get("travel_report_id").getAsInt();
			int count = travelReportDao.delete(travel_report_id);
			writeText(response, String.valueOf(count));
			}else if(action.equals("findById")) {
				int travel_report_id = jsonObject.get("travel_report_id").getAsInt();
				TravelReport travelReport = travelReportDao.findById(travel_report_id);
				writeText(response,gson.toJson(travelReport));
			}else {
				writeText(response,"");
			}
		}
		
		
		
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			if(travelReportDao == null) {
				travelReportDao = new TravelReportMySql();
			}
			Gson gson = new Gson();
			List<TravelReport> travelReport = travelReportDao.getAll();
			writeText(response,gson.toJson(travelReport));
		}
		
		private void writeText(HttpServletResponse response, String outText) throws IOException{
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.print(outText);
			System.out.println("output" + outText);
		}
}
