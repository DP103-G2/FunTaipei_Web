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
	
//	
//	private String userName = "";
//	private String password = "";
//
//	public Administrator(String userName, String password) {
//		super();
//		this.userName = userName;
//		this.password = password;
//	}
//
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null || !(obj instanceof Administrator)) {
//			return false;
//		} else {
//			Administrator adminstrator = (Administrator) obj;
//			return this.userName.equals(adminstrator.userName);
//		}
//	}
//	
//	@Override
//	public int hashCode() {
//		return userName.hashCode();
//	}
//
//	public String getName() {
//		return userName;
//	}
//
//	public void setName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

//	

}
