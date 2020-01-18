package dp103.gp2.server.group.Administrator;

public class Administrator {
	
	private int AD_ID;
	private String AD_NO;
	private String AD_PASSWORD;
	public Administrator(int aD_ID, String aD_NO, String aD_PASSWORD) {
		super();
		AD_ID = aD_ID;
		AD_NO = aD_NO;
		AD_PASSWORD = aD_PASSWORD;
	}
	public int getAD_ID() {
		return AD_ID;
	}
	public void setAD_ID(int aD_ID) {
		AD_ID = aD_ID;
	}
	public String getAD_NO() {
		return AD_NO;
	}
	public void setAD_NO(String aD_NO) {
		AD_NO = aD_NO;
	}
	public String getAD_PASSWORD() {
		return AD_PASSWORD;
	}
	public void setAD_PASSWORD(String aD_PASSWORD) {
		AD_PASSWORD = aD_PASSWORD;
	}
	
	

}
