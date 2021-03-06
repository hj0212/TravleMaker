package dao;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DBUtils.DBConnection;
import dto.PlanDTO;
import dto.ReviewCommentDTO;
import dto.ReviewDTO;
import dto.ReviewPhotoMainDTO;

public class ReviewDAO {
	private MemberDAO mdao = new MemberDAO();
	private ReviewPhotoDAO rdao = new ReviewPhotoDAO();
	//-------------------후기 글 전부 가져오기
	public List<ReviewDTO> getAllReview() throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from reviewboard";
		PreparedStatement pstmt = con.prepareStatement(sql);		
		ResultSet rs = pstmt.executeQuery();
		List<ReviewDTO> result = new ArrayList<>();

		while(rs.next()) {
			int seq = rs.getInt("review_writer");

			ReviewDTO tmp = new ReviewDTO();
			tmp.setReview_seq(rs.getInt(1));
			tmp.setReview_title(rs.getString(2));
			tmp.setReview_contents(rs.getString(3));
			tmp.setReview_writerN(mdao.getUserNickname(rs.getInt(4)));
			tmp.setReview_writedate(rs.getString(5));
			tmp.setReview_viewcount(rs.getInt(6));
			result.add(tmp);
		}
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}

	private String clobToString(Clob clob) throws Exception{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(clob.getCharacterStream());
		String ts = "";
		while((ts=br.readLine()) != null) {
			sb.append(ts);
		}

		br.close();
		return sb.toString();
	}

	public int insertReview(String title, String contents, int writer, String[] array) throws Exception{
		Connection conn = DBConnection.getConnection();
		String sql = "INSERT INTO reviewboard_c values(reviewboard_seq.nextval,?,?,?,sysdate,0)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		StringReader sr = new StringReader(contents);
		pstmt.setCharacterStream(2, sr, contents.length());
		pstmt.setInt(3, writer);

		int result = pstmt.executeUpdate();
		pstmt.close();

		if(array.length > 0) {
			sql = "UPDATE REVIEW_PHOTOS SET ARTICLE_NO = reviewboard_seq.currval WHERE SYSTEM_FILE_NAME = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			for(int i = 0; i < array.length; i++) {
				pstmt2.setString(1, array[i]);
				pstmt2.addBatch();
				pstmt2.clearParameters();
			}
			pstmt2.executeBatch();
			pstmt2.close();
		}

		conn.commit();
		conn.close();

		return result;
	}
	
	public int recentArticle(int writer) throws Exception {
		int result = 0;
		Connection conn = DBConnection.getConnection();
		String sql = "select review_seq from (select review_seq from reviewboard_c where review_writer = ? ORDER BY review_seq DESC) where rownum = 1";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, writer);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			result = rs.getInt(1);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return result;
	}
	
	public int updateReview(String title, String contents, int writer, String[] array, int seq) throws Exception {
		Connection conn = DBConnection.getConnection();
		String sql = "update reviewboard_c set review_title = ?, review_contents = ? where review_writer = ? and review_seq = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		StringReader sr = new StringReader(contents);
		pstmt.setCharacterStream(2, sr, contents.length());
		pstmt.setInt(3, writer);
		pstmt.setInt(4, seq);

		int result = pstmt.executeUpdate();
		pstmt.close();

		if(array.length > 0) {
			sql = "UPDATE REVIEW_PHOTOS SET ARTICLE_NO = ? WHERE SYSTEM_FILE_NAME = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			for(int i = 0; i < array.length; i++) {
				pstmt2.setInt(1, seq);
				pstmt2.setString(2, array[i]);
				pstmt2.addBatch();
				pstmt2.clearParameters();
			}
			pstmt2.executeBatch();
			pstmt2.close();
		}

		conn.commit();
		conn.close();

		return result;
	}

	//-----------------------네비에 정한 개수만큼 기록 가져오기
	public ArrayList<ReviewDTO> getSomeReview(int startNum, int endNum, String searchTerm) throws Exception {
		Connection con = DBConnection.getConnection();

		String sql;
		PreparedStatement pstat = null;

		if(searchTerm == null || searchTerm.equals("") || searchTerm.equals("null")) {
			sql = "select * from (select review_seq, review_title, review_contents, review_writer, to_char(review_writedate, 'YYYY/MM/DD') review_writedate, review_viewcount, row_number() over(order by review_seq desc) as num from reviewboard_c) where num between ? and ?";
			pstat = con.prepareStatement(sql);
			pstat.setInt(1, startNum);
			pstat.setInt(2, endNum);
		} else {
			sql = "select * from (select review_seq, review_title, review_contents, review_writer, to_char(review_writedate, 'YYYY/MM/DD') review_writedate, review_viewcount, row_number() over(order by review_seq desc) as num from reviewboard_c where review_title like ?) where num between ? and ?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, "%"+searchTerm+"%");
			pstat.setInt(2, startNum);
			pstat.setInt(3, endNum);
		}
		ResultSet rs = pstat.executeQuery();

		ArrayList<ReviewDTO> reviewResult = new ArrayList<>();

		while(rs.next()) {
			ReviewDTO tmp = new ReviewDTO();
			tmp.setReview_seq(rs.getInt(1));
			tmp.setReview_title(rs.getString(2));
			tmp.setReview_contents(rs.getString(3));
			tmp.setReview_writerN(mdao.getUserNickname(rs.getInt(4)));
			tmp.setReview_writedate(rs.getString(5));
			tmp.setReview_viewcount(rs.getInt(6));
			tmp.setReview_thumbnail(getThumbnail(tmp.getReview_seq()));
			reviewResult.add(tmp);
		}

		con.close();
		pstat.close();
		rs.close();	
		return reviewResult;
	}

	public List<ReviewPhotoMainDTO> getNewReview() throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from (select rownum, rp.review_photo_seq, rp.article_no, rp.original_file_name, rp.system_file_name, r.review_title, r.review_writer, r.review_writedate from review_photos rp, reviewboard_c r where rp.article_no = r.review_seq)where rownum between 1 and 4 order by review_photo_seq desc";
		PreparedStatement pstmt = con.prepareStatement(sql);		
		ResultSet rs = pstmt.executeQuery();
		List<ReviewPhotoMainDTO> result = new ArrayList<>();

		while(rs.next()) {		
			String nick = mdao.getUserNickname(rs.getInt(7));
			ReviewPhotoMainDTO tmp = new ReviewPhotoMainDTO();
			tmp.setRownum(rs.getInt(1));
			tmp.setReview_photo_seq(rs.getInt(2));
			tmp.setArticle_no(rs.getInt(3));
			tmp.setOriginal_file_name(rs.getString(4));
			tmp.setSystem_file_name(rs.getString(5));
			tmp.setReview_title(rs.getString(6));
			tmp.setReview_writer(nick);
			tmp.setReview_writedate(rs.getString(8));
			result.add(tmp);
		}
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}



	//-------------------페이지 네비	
	public String getPageNavi(int currentPage, String searchTerm) throws Exception {
		Connection con = DBConnection.getConnection();		
		String sql;
		PreparedStatement pstat;

		if(searchTerm == null || searchTerm.equals("") || searchTerm.equals("null")) {
			sql = "select count(*) totalCount from reviewboard_c";
			pstat = con.prepareStatement(sql);
		} else {
			sql = "select count(*) totalCount from reviewboard_c where review_title like ?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, "%"+searchTerm+"%");
		}

		ResultSet rs= pstat.executeQuery();
		rs.next();

		int recordTotalCount = rs.getInt("totalCount"); 
		//System.out.println(recordTotalCount);
		int recordCountPerPage = 12;  
		int naviCountPerPage = 10;  
		int pageTotalCount = 0;  

		if(recordTotalCount % recordCountPerPage > 0 ) { 
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		} else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}

		//------------------------------------------------------------------------------------------	
		//int currentPage = 1;		
		if(currentPage < 1) {	
			currentPage = 1;
		} else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}		
		//------------------------------------------------------------------------------------------		
		int startNavi = (currentPage - 1) / naviCountPerPage * naviCountPerPage + 1;  
		int endNavi = startNavi + (naviCountPerPage - 1);  

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) {
			needPrev = false;
		} 

		if(endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();

		if(needPrev) {
			sb.append("<li class='page-item'><a class='page-link' href='reviewboard.bo?currentPage="+(startNavi-1)+"&search="+searchTerm+"' aria-label='Previous'><span aria-hidden=\"true\">&laquo;</span><span class=\"sr-only\">Previous</span></a></li>");
		}

		for(int i = startNavi; i <= endNavi; i++) {
			if(currentPage == i) {
				sb.append("<li class='page-item'><a class='page-link' href='reviewboard.bo?currentPage="+i+"&search="+searchTerm+"'>"+i+"</a></li>");
			}else {
				sb.append("<li class='page-item'><a class='page-link' href='reviewboard.bo?currentPage="+i+"&search="+searchTerm+"'> "+i+"</a></li>");
			}
		}

		if(needNext) {
			sb.append("<li class='page-item'><a class='page-link' href='reviewboard.bo?currentPage="+(startNavi-1)+"&search="+searchTerm+"' aria-label='Next'><span aria-hidden='true'>&raquo;</span><span class='sr-only'>Next</span></a></li>");
		}

		con.close();
		pstat.close();
		rs.close();

		return sb.toString();
	}	

	public int getArticleViewCount (int review_seq) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select review_viewcount from reviewboard_c where review_seq = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, review_seq);
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()) {
			result = rs.getInt("review_viewcount");
		}
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}


	public ReviewDTO getReviewArticle(int review_seq) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select review_title, review_contents, review_writer, review_writedate, review_viewcount from reviewboard_c where review_seq = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, review_seq);
		ResultSet rs = pstmt.executeQuery();
		ReviewDTO rdto = new ReviewDTO();
		if(rs.next()) {
			rdto.setReview_title(rs.getString("review_title"));
			rdto.setReview_contents(clobToString(rs.getClob("review_contents")));
			rdto.setReview_writer(rs.getInt("review_writer"));
			rdto.setReview_writerN(mdao.getUserNickname(rs.getInt("review_writer")));
			rdto.setReview_writedate(rs.getString("review_writedate"));
			rdto.setReview_viewcount(rs.getInt("review_viewcount"));
		}

		rs.close();
		pstmt.close();
		con.close();

		return rdto;
	}

	public int insertReviewComment(String comment_text, int user, int review_seq) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "insert into review_comment values(review_comment_seq.nextval,?,?,sysdate,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, comment_text);
		pstmt.setInt(2, user);
		pstmt.setInt(3, review_seq);

		int result = pstmt.executeUpdate();
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}

	public List<ReviewCommentDTO> getReviewComment(int review_seq) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from review_comment where review_seq=? order by COMMENT_SEQ ASC";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, review_seq);
		ResultSet rs = pstmt.executeQuery();
		List<ReviewCommentDTO> result = new ArrayList<>();
		while(rs.next()) {
			ReviewCommentDTO rdto = new ReviewCommentDTO();
			rdto.setReview_seq(rs.getInt("review_seq"));
			rdto.setComment_seq(rs.getInt("comment_seq"));
			rdto.setComment_writer_seq(rs.getInt("comment_writer"));
			rdto.setComment_writer(mdao.getUserNickname(rs.getInt("comment_writer")));
			rdto.setComment_text(rs.getString("comment_text"));
			rdto.setComment_time(rs.getString("comment_time"));
			result.add(rdto);
		}
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}

	public int getReviewArticleCount(int review_seq) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "update reviewboard set review_viewcount=nvl(review_viewcount,0)+1 where review_seq=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, review_seq);
		int result = pstat.executeUpdate();
		con.commit();
		pstat.close();
		con.close();
		return result;
	}

	public int deleteReview(int review_seq, int writer) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "delete from reviewboard_c where review_seq=? and review_writer = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, review_seq);
		pstmt.setInt(2, writer);

		int result = pstmt.executeUpdate();
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}

	/*public List<ReviewDTO> getAllMyReview (int review_writer) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from reviewboard where review_writer=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, review_writer);
		ResultSet rs = pstmt.executeQuery();
		List<ReviewDTO> result = new ArrayList<>();
		while(rs.next()) {
			ReviewDTO rdto = new ReviewDTO();
			rdto.setReview_seq(rs.getInt("review_seq"));
			rdto.setReview_title(rs.getString("review_title"));
			rdto.setReview_contents(rs.getString("review_contents"));
			rdto.setReview_writer(rs.getInt("review_writer"));
			rdto.setReview_writerN(mdao.getUserNickname(rs.getInt("review_writer")));
			rdto.setReview_writedate(rs.getString("review_writedate"));
			rdto.setReview_viewcount(rs.getInt("review_viewcount"));
			result.add(rdto);
		}
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}*/

	public List<ReviewDTO> getMyReview(int seq, int startNum, int endNum) throws Exception {
		Connection con = DBConnection.getConnection();

		String sql;
		PreparedStatement pstat = null;

		sql = "select * from (select review_seq, review_title, review_contents, review_writer, to_char(review_writedate, 'YYYY/MM/DD') review_writedate, review_viewcount, row_number() over(order by review_seq desc) as num from reviewboard_c where review_writer=?) where num between ? and ?";
		pstat = con.prepareStatement(sql);
		pstat.setInt(1,seq);
		pstat.setInt(2, startNum);
		pstat.setInt(3, endNum);
		ResultSet rs = pstat.executeQuery();

		List<ReviewDTO> myReviewResult = new ArrayList<>();

		while(rs.next()) {
			ReviewDTO rdto = new ReviewDTO();
			rdto.setReview_seq(rs.getInt(1));
			rdto.setReview_title(rs.getString(2));
			rdto.setReview_contents(rs.getString(3));
			rdto.setReview_writerN(mdao.getUserNickname(rs.getInt(4)));
			rdto.setReview_writedate(rs.getString(5));
			rdto.setReview_viewcount(rs.getInt(6));
			myReviewResult.add(rdto);
		}

		con.close();
		pstat.close();
		rs.close();	
		return myReviewResult;
	}

	public String getMyReviewPageNavi( int seq,int currentPage) throws Exception {
		Connection con = DBConnection.getConnection();		
		String sql;
		PreparedStatement pstat;
		ResultSet rs;

		sql = "select count(*) totalCount from reviewboard_c where review_writer=?";		
		pstat = con.prepareStatement(sql);
		pstat.setInt(1, seq);

		rs = pstat.executeQuery();
		rs.next();

		int recordTotalCount = rs.getInt("totalCount"); 
		//System.out.println(recordTotalCount);
		int recordCountPerPage = 12;  
		int naviCountPerPage = 10;  
		int pageTotalCount = 0;  

		if(recordTotalCount % recordCountPerPage > 0 ) { 
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		} else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}

		//------------------------------------------------------------------------------------------	
		//int currentPage = 1;		
		if(currentPage < 1) {	
			currentPage = 1;
		} else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}		
		//------------------------------------------------------------------------------------------		
		int startNavi = (currentPage - 1) / naviCountPerPage * naviCountPerPage + 1;  
		int endNavi = startNavi + (naviCountPerPage - 1);  

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) {
			needPrev = false;
		} 

		if(endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();

		if(needPrev) {
			sb.append("<li class='page-item'><a class='page-link' href='mypage.do?currentPage="+(startNavi-1)+"' aria-label='Previous'><span aria-hidden=\"true\">&laquo;</span><span class=\"sr-only\">Previous</span></a></li>");
		}

		for(int i = startNavi; i <= endNavi; i++) {
			if(currentPage == i) {
				sb.append("<li class='page-item'><a class='page-link' href='mypage.do?currentPage="+i+"'>"+i+"</a></li>");
			} else {
				sb.append("<li class='page-item'><a class='page-link' href='mypage.do?currentPage="+i+"'> "+i+"</a></li>");
			}
		}

		if(needNext) {
			sb.append("<li class='page-item'><a class='page-link' href='mypage.do?currentPage="+(startNavi-1)+"' aria-label='Next'><span aria-hidden='true'>&raquo;</span><span class='sr-only'>Next</span></a></li>");
		}

		con.close();
		pstat.close();
		rs.close();

		return sb.toString();
	}

	public int deleteReviewComment (int comment_seq, int comment_writer_seq) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "delete from review_comment where comment_seq=? and comment_writer=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setInt(1, comment_seq);
		pstat.setInt(2, comment_writer_seq);
		int result = pstat.executeUpdate();
		con.commit();
		pstat.close();
		con.close();
		return result;
	}

	public int reViewCount(int review_seq) throws Exception {
		Connection conn = DBConnection.getConnection();
		String sql = "UPDATE reviewboard_c set review_viewcount = review_viewcount + 1 where review_seq = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, review_seq);

		int result = pstmt.executeUpdate();

		conn.commit();
		pstmt.close();
		conn.close();
		return result;
	}

	public int writerCheck(int seq) throws Exception {
		Connection conn = DBConnection.getConnection();
		String sql = "select review_writer from reviewboard_c where review_seq = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		ResultSet rs = pstmt.executeQuery();
		int writer = 0;

		if(rs.next()) {
			writer = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return writer;
	}

	public String getThumbnail(int seq) throws Exception {
		Connection conn = DBConnection.getConnection();
		String sql = "select system_file_name from (select rp.* from review_photos rp, reviewboard_c rc where rp.article_no = rc.review_seq and rc.review_seq = ?) where rownum = 1";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		String fname = "";

		ResultSet rs = pstmt.executeQuery();

		if(rs.next()) {
			fname = rs.getString(1);
		}else {
			fname = "back1.png";
		}

		rs.close();
		pstmt.close();
		conn.close();
		return fname;
	}
}
