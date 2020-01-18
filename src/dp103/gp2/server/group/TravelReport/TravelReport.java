package dp103.gp2.server.group.TravelReport;

import java.util.Date;

public class TravelReport {
	private int travel_report_id;
	private int travel_id;
	private int mb_id;
	private String travel_report_cause;
	private Date travel_datetime;
	public TravelReport(int travel_report_id, int travel_id, int mb_id, String travel_report_cause,
			Date travel_datetime) {
		super();
		this.travel_report_id = travel_report_id;
		this.travel_id = travel_id;
		this.mb_id = mb_id;
		this.travel_report_cause = travel_report_cause;
		this.travel_datetime = travel_datetime;
	}
	public int getTravel_report_id() {
		return travel_report_id;
	}
	public void setTravel_report_id(int travel_report_id) {
		this.travel_report_id = travel_report_id;
	}
	public int getTravel_id() {
		return travel_id;
	}
	public void setTravel_id(int travel_id) {
		this.travel_id = travel_id;
	}
	public int getMb_id() {
		return mb_id;
	}
	public void setMb_id(int mb_id) {
		this.mb_id = mb_id;
	}
	public String getTravel_report_cause() {
		return travel_report_cause;
	}
	public void setTravel_report_cause(String travel_report_cause) {
		this.travel_report_cause = travel_report_cause;
	}
	public Date getTravel_datetime() {
		return travel_datetime;
	}
	public void setTravel_datetime(Date travel_datetime) {
		this.travel_datetime = travel_datetime;
	}
	
	
}
