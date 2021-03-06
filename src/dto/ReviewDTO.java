package dto;

public class ReviewDTO {
	private int review_seq;
	private String review_title;
	private String review_contents;
	private int review_writer;
	private String review_writerN;
	private String review_writedate;
	private int review_viewcount;
	private String review_thumbnail;
	
	public ReviewDTO() {}


	public ReviewDTO(int review_seq, String review_title, String review_contents, int review_writer,
			String review_writerN, String review_writedate, int review_viewcount) {
		super();
		this.review_seq = review_seq;
		this.review_title = review_title;
		this.review_contents = review_contents;
		this.review_writer = review_writer;
		this.review_writerN = review_writerN;
		this.review_writedate = review_writedate;
		this.review_viewcount = review_viewcount;
	}


	public int getReview_seq() {
		return review_seq;
	}


	public void setReview_seq(int review_seq) {
		this.review_seq = review_seq;
	}


	public String getReview_title() {
		return review_title;
	}


	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}


	public String getReview_contents() {
		return review_contents;
	}


	public void setReview_contents(String review_contents) {
		this.review_contents = review_contents;
	}


	public int getReview_writer() {
		return review_writer;
	}


	public void setReview_writer(int review_writer) {
		this.review_writer = review_writer;
	}


	public String getReview_writerN() {
		return review_writerN;
	}


	public void setReview_writerN(String review_writerN) {
		this.review_writerN = review_writerN;
	}


	public String getReview_writedate() {
		return review_writedate;
	}


	public void setReview_writedate(String review_writedate) {
		this.review_writedate = review_writedate;
	}


	public int getReview_viewcount() {
		return review_viewcount;
	}


	public void setReview_viewcount(int review_viewcount) {
		this.review_viewcount = review_viewcount;
	}

	public String getReview_thumbnail() {
		return review_thumbnail;
	}

	public void setReview_thumbnail(String review_thumbnail) {
		this.review_thumbnail = review_thumbnail;
	}
}
