package dp103.gp2.server.group.TravelCollection;

import java.sql.Timestamp;
import java.util.Date;

public class TravelCollection {
	//這些給Collection首頁用
	private int mb_no;
	private int travel_id;
	private String mb_email;
	private String mb_name;
	private String travel_name;
	//這些給Detail用
	private int pc_id;
	private String pc_name;
	//首頁用
	public TravelCollection(int mb_no, int travel_id, String mb_email, String mb_name, String travel_name) {
		super();
		this.mb_no = mb_no;
		this.travel_id = travel_id;
		this.mb_email = mb_email;
		this.mb_name = mb_name;
		this.travel_name = travel_name;
	}
	//Detail使用
	public TravelCollection(int travel_id, int pc_id, String pc_name) {
		this.travel_id = travel_id;
		this.pc_id = pc_id;
		this.pc_name = pc_name;
	}
	//忘了
	public TravelCollection(int mb_no, int travel_id) {
		this.mb_no = mb_no;
		this.travel_id = travel_id;
	}
	
	//Get and Set區塊
	
	public int getMb_no() {
		return mb_no;
	}
	public int getPc_id() {
		return pc_id;
	}
	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}
	public String getPc_name() {
		return pc_name;
	}
	public void setPc_name(String pc_name) {
		this.pc_name = pc_name;
	}
	public void setMb_no(int mb_no) {
		this.mb_no = mb_no;
	}
	public int getTravel_id() {
		return travel_id;
	}
	public void setTravel_id(int travel_id) {
		this.travel_id = travel_id;
	}
	public String getMb_email() {
		return mb_email;
	}
	public void setMb_email(String mb_email) {
		this.mb_email = mb_email;
	}
	public String getMb_name() {
		return mb_name;
	}
	public void setMb_name(String mb_name) {
		this.mb_name = mb_name;
	}
	public String getTravel_name() {
		return travel_name;
	}
	public void setTravel_name(String travel_name) {
		this.travel_name = travel_name;
	}
	

	
	
	
}