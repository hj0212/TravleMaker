package dto;

public class ReportFreeDTO {
	
	private int reportfree_seq;
	private int free_seq;
	private String free_title;
	private String free_writer;
	private String report_user;
	private String report_date;
	private String report_link;
	private int report_count;
	private String free_writedate;
	private int free_viewcount;
	
	public ReportFreeDTO(){}

	public ReportFreeDTO(int reportfree_seq, int free_seq, String free_title, String free_writer, String report_user,
			String report_date, String report_link, int report_count, String free_writedate, int free_viewcount) {
		super();
		this.reportfree_seq = reportfree_seq;
		this.free_seq = free_seq;
		this.free_title = free_title;
		this.free_writer = free_writer;
		this.report_user = report_user;
		this.report_date = report_date;
		this.report_link = report_link;
		this.report_count = report_count;
		this.free_writedate = free_writedate;
		this.free_viewcount = free_viewcount;
	}

	public int getReportfree_seq() {
		return reportfree_seq;
	}
	public void setReportfree_seq(int reportfree_seq) {
		this.reportfree_seq = reportfree_seq;
	}
	public int getFree_seq() {
		return free_seq;
	}
	public void setFree_seq(int free_seq) {
		this.free_seq = free_seq;
	}
	public String getFree_writer() {
		return free_writer;
	}
	public void setFree_writer(String free_writer) {
		this.free_writer = free_writer;
	}
	public String getReport_user() {
		return report_user;
	}
	public void setReport_user(String report_user) {
		this.report_user = report_user;
	}
	public String getFree_title() {
		return free_title;
	}
	public void setFree_title(String free_title) {
		this.free_title = free_title;
	}

	public String getReport_date() {
		return report_date;
	}

	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}

	public String getReport_link() {
		return report_link;
	}

	public void setReport_link(String report_link) {
		this.report_link = report_link;
	}

	public int getReport_count() {
		return report_count;
	}

	public void setReport_count(int report_count) {
		this.report_count = report_count;
	}

	public String getFree_writedate() {
		return free_writedate;
	}

	public void setFree_writedate(String free_writedate) {
		this.free_writedate = free_writedate;
	}

	public int getFree_viewcount() {
		return free_viewcount;
	}

	public void setFree_viewcount(int free_viewcount) {
		this.free_viewcount = free_viewcount;
	}



	
}
