package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DBUtils.DBConnection;
import dto.MemberDTO;

public class MemberDAO {

	public MemberDTO loginMember(MemberDTO dto) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where userid = ? and password = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dto.getUserid());
		pstmt.setString(2, dto.getPassword());
		ResultSet rs = pstmt.executeQuery();
		MemberDTO tmp = new MemberDTO();
		if(rs.next()) {
			tmp.setSeq(rs.getInt(1));
			tmp.setUserid(rs.getString(2));
			tmp.setPassword(rs.getString(3));
			tmp.setEmail(rs.getString(4));
			tmp.setNickname(rs.getString(5));
			tmp.setBlock(rs.getString(15));
			tmp.setPhoto_system_file_name(rs.getString(16));
		}
		
		con.close();
		pstmt.close();
		rs.close();

		return tmp;
	}

	public int addMember(MemberDTO dto) throws Exception {
		if(!check(dto.getUserid())) {
			Connection con = DBConnection.getConnection();
			String sql = "insert into users (seq, userid, password, nickname, email, modify_date,create_date) VALUES (users_seq.nextval, ?, ?, ?, ?,sysdate,sysdate)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getUserid());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getNickname());
			pstmt.setString(4, dto.getEmail());
			int result = pstmt.executeUpdate();

			con.commit();
			con.close();
			pstmt.close();

			return result;
		}
		System.out.println("여기");
		return -1;
	}

	public MemberDTO addNaverMember(MemberDTO dto) throws Exception {
		if(!navercheck(dto.getNaver_id())) {
			Connection con = DBConnection.getConnection();
			String sql = "insert into users (seq, naver_id, naver_nickname, naver_email,modify_date,create_date, part) VALUES (users_seq.nextval, ?, ?, ?,sysdate,sysdate, 'naver')";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getNaver_id());
			pstmt.setString(2, dto.getNaver_nickname());
			pstmt.setString(3, dto.getNaver_email());

			int result = pstmt.executeUpdate();		
			con.commit();
			pstmt.close();
			con.close();
		} 

		Connection con = DBConnection.getConnection();
		String sql = "select * from users where naver_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dto.getNaver_id());
		ResultSet rs = pstmt.executeQuery();
		MemberDTO tmp = new MemberDTO();
		if(rs.next()) {
			tmp.setSeq(rs.getInt(1));
			tmp.setNaver_id(rs.getString(6));
			tmp.setNaver_nickname(rs.getString(7));
			tmp.setNaver_email(rs.getString(8));
			tmp.setBlock(rs.getString("block"));
			tmp.setPhoto_system_file_name(rs.getString(16));
		}

		con.close();
		pstmt.close();
		rs.close();

		return tmp;
	}

	public MemberDTO addKakaoMember(MemberDTO dto) throws Exception {
		if(!kakaocheck(dto.getKakao_id())) {
			Connection con = DBConnection.getConnection();
			String sql = "insert into users (seq, kakao_id, kakao_nickname, kakao_email, modify_date, create_date,part,photo_system_file_name) VALUES (users_seq.nextval, ?, ?, ?, sysdate, sysdate, 'kakao',?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getKakao_id());
			pstmt.setString(2, dto.getKakao_nickname());
			pstmt.setString(3, dto.getKakao_email());
			pstmt.setString(4, dto.getPhoto_system_file_name());

			int result = pstmt.executeUpdate();		
			con.commit();
			pstmt.close();
			con.close();
		}

		Connection con = DBConnection.getConnection();
		String sql = "select * from users where kakao_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dto.getKakao_id());
		ResultSet rs = pstmt.executeQuery();
		MemberDTO tmp = new MemberDTO();
		if(rs.next()) {
			tmp.setSeq(rs.getInt(1));
			tmp.setNaver_id(rs.getString(9));
			tmp.setNaver_nickname(rs.getString(10));
			tmp.setNaver_email(rs.getString(11));
			tmp.setBlock(rs.getString("block"));
			tmp.setPhoto_system_file_name(rs.getString(16));
		}

		con.close();
		pstmt.close();
		rs.close();

		return tmp;
	}

	public boolean check(String id) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = true;
		} 

		rs.close();
		con.close();
		pstmt.close();

		return result;
	}

	private boolean navercheck(String id) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where naver_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		boolean result;
		if(rs.next()) {
			result = true;
		} else {
			result = false;
		}

		rs.close();
		con.close();
		pstmt.close();

		return result;
	}

	private boolean kakaocheck(String id) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where kakao_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		boolean result;
		if(rs.next()) {
			result = true;
		} else {
			result = false;
		}

		rs.close();
		con.close();
		pstmt.close();

		return result;
	}

	public MemberDTO getProfileInfo(String part, String id)throws Exception{
		Connection con = DBConnection.getConnection();
		MemberDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		if(part.equals("home")) {
			String sql = "select nickname, email, part from users where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);		
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
				dto.setPart(rs.getString("part"));
			}
		}else if (part.equals("naver")) {
			String sql = "select naver_nickname, naver_email from users where naver_id =?";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmt.setString(1, id);
			if(rs.next()) {
				dto.setNaver_nickname(rs.getString("naver_nickname"));
				dto.setNaver_email(rs.getString("naver_email"));
			}
		}
		else if (part.equals("kakao")) {
			String sql = "select kakao_nickname, kakao_email form users where kakao id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setKakao_nickname(rs.getString("kakao_nickname"));
				dto.setKakao_email(rs.getString("kakao_email"));
			}
		}
		pstmt.close();
		rs.close();
		con.close();
		return dto;	
	}

	public int getEmail(String id, String email)throws Exception{
		Connection con = DBConnection.getConnection();
		int result =0;
		ResultSet rs = null;
		/*if(this.check(id)==false) {
	      return result; //아이디가 없음
	    }else */if(this.check(id)) {
	    	String sql = "select * from users where userid=? and email=?";
	    	PreparedStatement pstmt=con.prepareStatement(sql);
	    	pstmt.setString(1, id);
	    	pstmt.setString(2, email);
	    	rs = pstmt.executeQuery();      

	    	if(rs.next()) {
	    		result=11;  //아이디 있고 이메일 확인도 됨    
	    	}else {  
	    		result=10; // 아이디 있고 이메일 불일치
	    	}
	    	pstmt.close();
	    	rs.close();
	    	con.close();    
	    }
	    return result;  
	}


	public String getUserNickname(int seq)throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select part from users where seq=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, seq);
		ResultSet rs = pstmt.executeQuery();
		String nickname = "";
		if(rs.next()) {
			String part = rs.getString(1);
			
			if(part.equals("home")) {
				sql = "select nickname from users where seq=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, seq);      
				rs = pstmt.executeQuery();
				if(rs.next())
				nickname = rs.getString(1);
			}else if (part.equals("naver")) {
				sql = "select naver_nickname from users where seq =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();
				if(rs.next())
				nickname = rs.getString(1);
			}
			else if (part.equals("kakao")) {
				sql = "select kakao_nickname from users where seq = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();
				if(rs.next())
				nickname = rs.getString(1);
			}
		}
		
		pstmt.close();
		rs.close();
		con.close();
		return nickname;   
	}
	
	public static String getUserNickname(String sequence)throws Exception{
		int seq = Integer.parseInt(sequence);
		Connection con = DBConnection.getConnection();
		String sql = "select part from users where seq=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, seq);
		ResultSet rs = pstmt.executeQuery();
		String nickname = "";
		if(rs.next()) {
			String part = rs.getString(1);
			
			if(part.equals("home")) {
				sql = "select nickname from users where seq=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, seq);      
				rs = pstmt.executeQuery();
				if(rs.next())
				nickname = rs.getString(1);
			}else if (part.equals("naver")) {
				sql = "select naver_nickname from users where seq =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();
				if(rs.next())
				nickname = rs.getString(1);
			}
			else if (part.equals("kakao")) {
				sql = "select kakao_nickname from users where seq = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();
				if(rs.next())
				nickname = rs.getString(1);
			}
		}
		
		pstmt.close();
		rs.close();
		con.close();
		return nickname;   
	}

	public int changePw(String id, String pw)throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "update users set password =? where userid=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, id);
		int result = pstmt.executeUpdate();

		con.commit();
		pstmt.close();
		con.close();
		return result;
	}

	public boolean isHomeMemberPW (String id, String pw) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where userid=? and password= ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		ResultSet rs = pstmt.executeQuery();
		boolean result = rs.next();
		
		rs.close();
		pstmt.close();
		con.close();
		
		return result;
	}

	public MemberDTO getHomeMemberInfo(String id, String part) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where USERID=?";
		PreparedStatement pstmt= con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs= pstmt.executeQuery();
		MemberDTO result = null;
		if(part.equals("home")) {
			MemberDTO dto = new MemberDTO();
			if(rs.next()) {
				dto.setUserid(rs.getString("userid"));
				dto.setPassword(rs.getString("password"));
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
			}
			result = dto;
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return result;
	}


	public int updateHomeMemberInfo(String id, String pw, String email, String nickname) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "update users set password=?, nickname=?, email=?, modify_date= to_char(sysdate,'YYYY/MM/DD') where userid=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, nickname);
		pstmt.setString(3, email);
		pstmt.setString(4, id);
		int result = pstmt.executeUpdate();
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}
	
	public int updateNKMemberInfo(String id, String nickname, String email, int seq, String part) throws Exception{
		Connection con = DBConnection.getConnection();
		PreparedStatement pstat = null;
		int result =0;
		if(part.equals("naver")) {
			String sql = "update users set naver_id=?, naver_nickname=?, naver_email=?, modify_date= to_char(sysdate,'YYYY/MM/DD') where seq=?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setString(2, nickname);
			pstat.setString(3, email);
			pstat.setInt(4, seq);
			result = pstat.executeUpdate();
		}else if (part.equals("kakao")) {
			String sql = "update users set kakao_id=?, kakao_nickname=?, kakao_email=?, modify_date= to_char(sysdate,'YYYY/MM/DD') where seq=?";			
			pstat = con.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setString(2, nickname);
			pstat.setString(3, email);
			pstat.setInt(4, seq);
			result = pstat.executeUpdate();

		}
		con.commit();
		pstat.close();
		con.close();
		return result;
	}

	public int updateEmail (String id, String part, String email) throws Exception{
		Connection con = DBConnection.getConnection();
		PreparedStatement pstmt=null;
		if(part.equals("home")) {
			String sql = "update users set email=? where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
		}else if(part.equals("naver")) {
			String sql = "upadate users set naver_email=? where naver_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
		}else if(part.equals("kakao")) {
			String sql = "update users set kakao_email=? where kakao_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
		}
		int result = pstmt.executeUpdate();
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}

	public int updatePw (String id, String pw) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "update users set password =? where userid = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, id);
		int result = pstmt.executeUpdate();
		con.commit();
		pstmt.close();
		con.close();
		return result;
	}
	
	
		

	public int updateProfileImg (int user_seq, String file_name) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "update users set photo_system_file_name =? where seq=?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setString(1, file_name);
		pstat.setInt(2, user_seq);
		int result = pstat.executeUpdate();
		con.commit();
		pstat.close();
		con.close();
		return result;
	}
	
	public MemberDTO newMemberInfo (int seq , String part) throws Exception{
		Connection con = DBConnection.getConnection();
		String sql = "select * from users where part =?";
		PreparedStatement pstat = con.prepareStatement(sql);
		pstat.setString(1, part);
		ResultSet rs = pstat.executeQuery();
		MemberDTO dto = new MemberDTO();
		if(rs.next()) {
			
			if(part.equals("home")) {
				String sql1 = "select userid, nickname, email, part, photo_system_file_name from users where seq=?";
				pstat = con.prepareStatement(sql1);
				pstat.setInt(1, seq);		
				rs = pstat.executeQuery();
				if(rs.next()) {
					dto = new MemberDTO();
					dto.setUserid(rs.getString("userid"));
					dto.setNickname(rs.getString("nickname"));
					dto.setEmail(rs.getString("email"));
					dto.setPart(rs.getString("part"));
					dto.setPhoto_system_file_name(rs.getString("photo_system_file_name"));
				}
			}else if (part.equals("naver")) {
				String sql1 = "select naver_id, naver_nickname, naver_email, photo_system_file_name from users where seq =?";
				pstat = con.prepareStatement(sql1);
				pstat.setInt(1, seq);
				rs = pstat.executeQuery();
				if(rs.next()) {
					dto.setNaver_id(rs.getString("naver_id"));
					dto.setNaver_nickname(rs.getString("naver_nickname"));
					dto.setNaver_email(rs.getString("naver_email"));
					dto.setPhoto_system_file_name(rs.getString("photo_system_file_name"));
				}
			}
			else if (part.equals("kakao")) {
				String sql1 = "select kakao_id, kakao_nickname, kakao_email, photo_system_file_name from users where seq = ?";
				pstat = con.prepareStatement(sql1);
				pstat.setInt(1, seq);
				rs = pstat.executeQuery();
				if(rs.next()) {
					dto.setKakao_id(rs.getString("kakao_id"));
					dto.setKakao_nickname(rs.getString("kakao_nickname"));
					dto.setKakao_email(rs.getString("kakao_email"));
					dto.setPhoto_system_file_name(rs.getString("photo_system_file_name"));
				}
			}
		}
		pstat.close();
		rs.close();
		con.close();
		return dto;	
		
	}
	
}

