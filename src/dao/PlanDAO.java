package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DBUtils.DBConnection;
import dto.BudgetDTO;
import dto.FreeboardDTO;
import dto.PlanDTO;
import dto.ScheduleDTO;

public class PlanDAO {
	
	private MemberDAO mdao = new MemberDAO();
	public String getPlantitle(int plan_seq) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select plan_title from plan where plan_seq = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, plan_seq);
		ResultSet rs = pstmt.executeQuery();
		String result = "";
		if(rs.next()) {
			result = rs.getString(1);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}
	
	public int getScheduleseq() throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select max(schedule_seq) from schedule";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()) {
			result = rs.getInt(1);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		return result;
	}
	
	public int addBudget(BudgetDTO dto) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "insert into budget VALUES (budget_seq.nextval, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, dto.getSchedule_seq());
		pstmt.setString(2, dto.getBudget_plan());
		pstmt.setInt(3, dto.getBudget_amount());
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		con.commit();
		con.close();

		return result;
	}

	public int addSchedule(ScheduleDTO dto) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "insert into schedule VALUES (?,?,schedule_seq.nextval, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, dto.getPlan_seq());
		pstmt.setInt(2, dto.getDay_seq());
		pstmt.setString(3, dto.getSchedule_starttime());
		pstmt.setString(4, dto.getSchedule_endtime());
		pstmt.setString(5, dto.getLocation_id());
		pstmt.setString(6, dto.getSchedule_plan());
		pstmt.setString(7, dto.getSchedule_ref());
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		con.commit();
		con.close();

		return result;
	}
	
	public int updateSchedule(ScheduleDTO dto) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "update schedule set schedule_starttime=?, schedule_endtime=?, location_id=?,schedule_plan=?,schedule_ref=? where schedule_seq=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dto.getSchedule_starttime());
		pstmt.setString(2, dto.getSchedule_endtime());
		pstmt.setString(3, dto.getLocation_id());
		pstmt.setString(4, dto.getSchedule_plan());
		pstmt.setString(5, dto.getSchedule_ref());
		pstmt.setInt(6, dto.getSchedule_seq());
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		con.commit();
		con.close();

		return result;
	}

	public List<ScheduleDTO> selectSchedule(int plan, int day) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select * from schedule where plan_seq = ? and day_seq = ? order by 4";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, plan);
		pstmt.setInt(2, day);
		
		ResultSet rs = pstmt.executeQuery();

		List<ScheduleDTO> result = new ArrayList<>();
		while(rs.next()) {
			ScheduleDTO tmp = new ScheduleDTO();
			tmp.setPlan_seq(rs.getInt(1));
			tmp.setDay_seq(rs.getInt(2));
			tmp.setSchedule_seq(rs.getInt(3));
			tmp.setSchedule_starttime(rs.getString(4));
			tmp.setSchedule_endtime(rs.getString(5));
			tmp.setLocation_id(rs.getString(6));
			tmp.setSchedule_plan(rs.getString(7));
			tmp.setSchedule_ref(rs.getString(8));
			result.add(tmp);
		}

		rs.close();
		pstmt.close();
		con.close();
		return result;
	}
	
	public List<BudgetDTO> selectBudget(int plan, int day) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select * from budget where plan_seq = ? and day_seq = ? order by 2";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, plan);
		pstmt.setInt(2, day);
		
		ResultSet rs = pstmt.executeQuery();

		List<BudgetDTO> result = new ArrayList<>();
		while(rs.next()) {
			BudgetDTO tmp = new BudgetDTO();
			tmp.setPlan_seq(plan);
			tmp.setDay_seq(day);
			tmp.setBudget_seq(rs.getInt(3));
			tmp.setSchedule_seq(rs.getInt(4));
			tmp.setBudget_plan(rs.getString(5));
			tmp.setBudget_amount(rs.getInt(6));
			System.out.println(tmp.getBudget_amount());
			result.add(tmp);
		}

		rs.close();
		pstmt.close();
		con.close();
		return result;
	}
	
	public int startPlanInsertData(PlanDTO dto)throws Exception{
		Connection con = DBConnection.getConnection();
		String sql ="insert into plan values(plan_seq.nextval,?,?,?,?,0,0,0,0)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, dto.getPlan_writer());
		pstmt.setString(2,dto.getPlan_startdate());
		pstmt.setString(3, dto.getPlan_enddate());
		pstmt.setString(4, dto.getPlan_title());
		int result = pstmt.executeUpdate();
		
		con.commit();
		con.close();
		pstmt.close();
		return result;
	}
	
	public int getSchedule_seq() throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "select max(schedule_Seq) from schedule";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int seq = 0;
		if(rs.next()) {
			seq = rs.getInt(1);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		return seq;
	}
	
	public List<PlanDTO> getSomePlan(int startNum, int endNum, String searchTerm)throws Exception{
		Connection con = DBConnection.getConnection();
		String sql;
		PreparedStatement pstat = null;
		
		if(searchTerm == null || searchTerm.equals("null")) {
		sql = "select * from (select plan_seq, plan_writer, plan_title, plan_good, plan_viewcount, row_number() over(order by plan_seq desc) as num from plan) where num between ? and ?";
		pstat = con.prepareStatement(sql);
		pstat.setInt(1, startNum);
		pstat.setInt(2, endNum);
		} else {
			sql = "select * from (select plan_seq, plan_writer, plan_title, plan_good, plan_viewcount, row_number() over(order by plan_seq desc) as num from plan where plan_title like ?) where num between ? and ?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, "%"+searchTerm+"%");
			pstat.setInt(2, startNum);
			pstat.setInt(3, endNum);
		}
		ResultSet rs = pstat.executeQuery();
		ArrayList<PlanDTO> result = new ArrayList<>();

		while(rs.next()) {
			PlanDTO tmp = new PlanDTO();
			tmp.setPlan_seq(rs.getInt(1));
			tmp.setPlan_writerN(mdao.getUserNickname(rs.getInt(2)));
			tmp.setPlan_title(rs.getString(3));
			tmp.setPlan_good(rs.getInt(4));
			tmp.setPlan_viewcount(rs.getInt(5));
			
			result.add(tmp);
		}
		
		con.close();
		pstat.close();
		rs.close();
		return result;
	}
//--------------------------페이지 네비게이터	
	public String getPageNavi(int currentPage, String searchTerm) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql;
		PreparedStatement pstat;
		ResultSet rs;
		
		if(searchTerm == null || searchTerm.equals("null")) {
			sql = "select count(*) totalCount from plan";
			pstat = con.prepareStatement(sql);
		} else {
			sql = "select count(*) totalCount from plan where plan_title like ?";
			pstat = con.prepareStatement(sql);
			pstat.setString(1, "%"+searchTerm+"%");
		}
		
		rs = pstat.executeQuery();
		rs.next();
		
		int recordTotalCount = rs.getInt("totalCount"); // �쟾泥� 湲�(�젅肄붾뱶)�쓽 媛쒖닔瑜� ���옣�븯�뒗 蹂��닔
		int recordCountPerPage = 12;  // �븳 �럹�씠吏��뿉 寃뚯떆湲��씠 紐뉕컻 蹂댁씪嫄댁�
		int naviCountPerPage = 10;  // �븳 �럹�씠吏��뿉�꽌 �꽕鍮꾧쾶�씠�꽣媛� 紐뉕컻�뵫 蹂댁씪嫄댁�
		int pageTotalCount = 0;  // �쟾泥닿� 紐뉙럹�씠吏�濡� 援ъ꽦�맆寃껋씤吏�
		
		if(recordTotalCount % recordCountPerPage > 0 ) { 
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		} else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}
		
		//------------------------------------------------------------------------------------------
	
		if(currentPage < 1) {	
			currentPage = 1;
		} else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
			
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
			sb.append("<li class='page-item'><a class='page-link' href='planboard.plan?currentPage="+(startNavi-1)+"&search="+searchTerm+"' aria-label='Previous'><span aria-hidden=\"true\">&laquo;</span><span class=\"sr-only\">Previous</span></a></li>");
		}
		
		for(int i = startNavi; i <= endNavi; i++) {
			if(currentPage == i) {
				sb.append("<li class='page-item'><a class='page-link' href='planboard.plan?currentPage="+i+"&search="+searchTerm+"'>"+i+"</a></li>");
			} else {
				sb.append("<li class='page-item'><a class='page-link' href='planboard.plan?currentPage="+i+"&search="+searchTerm+"'> "+i+"</a></li>");
			}
		}
		
		if(needNext) {
			sb.append("<li class='page-item'><a class='page-link' href='planboard.plan?currentPage="+(startNavi-1)+"&search="+searchTerm+"' aria-label='Next'><span aria-hidden='true'>&raquo;</span><span class='sr-only'>Next</span></a></li>");
		}
		
		con.close();
		pstat.close();
		rs.close();
		
		return sb.toString();
	}	
	
	
	
}
