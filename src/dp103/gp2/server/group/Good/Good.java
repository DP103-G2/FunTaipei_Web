package dp103.gp2.server.group.Good;

public class Good {
	private int pc_id;
	private int mb_no;
	public Good(int pc_id, int mb_no) {
		super();
		this.pc_id = pc_id;
		this.mb_no = mb_no;
	}
	public int getPc_id() {
		return pc_id;
	}
	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}
	public int getMb_no() {
		return mb_no;
	}
	public void setMb_no(int mb_no) {
		this.mb_no = mb_no;
	}
	
}
