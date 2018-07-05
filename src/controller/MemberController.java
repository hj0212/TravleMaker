package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import beans.SendMail;
import dao.AdminDAO;
import dao.MemberDAO;
import dao.ReviewDAO;
import dto.MemberDTO;
import dto.ReviewDTO;


@WebServlet("*.do")
public class MemberController extends HttpServlet { 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String requestURI= request.getRequestURI();
			String contextPath = request.getContextPath();
			String command = requestURI.substring(contextPath.length());
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("utf8");

			ReviewDAO rdao = new ReviewDAO();
			MemberDAO mdao = new MemberDAO();
			AdminDAO adao = new AdminDAO();

			boolean isForward = true;
			String dst = null;

			if(command.equals("/login.do")) {
				MemberDTO dto = new MemberDTO();
				dto.setUserid(request.getParameter("id"));
				dto.setPassword(request.getParameter("pw"));
				MemberDTO user = mdao.loginMember(dto);
				user.setPart("home");
				boolean result = false;
				if(user.getSeq() > 0) {
					result = true;
				}
				request.setAttribute("proc", "login");
				request.setAttribute("loginResult", result);
				request.getSession().setAttribute("part", "home");
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("loginId", dto.getUserid());

				String nickname = mdao.getUserNickname(user.getSeq());
				request.getSession().setAttribute("nickname", nickname);
				if(user.getBlock().equals("y")) {
					isForward = true;
					dst="errorBlock.jsp";
				}
				else if(user.getBlock().equals("n") || user.getBlock().equals("x")){
					isForward = true;
					dst="userResult.jsp";
				}


			} else if(command.equals("/join.do")) {
				MemberDTO dto = new MemberDTO();
				dto.setUserid(request.getParameter("id"));
				dto.setPassword(request.getParameter("pw"));
				dto.setNickname(request.getParameter("nickname"));
				dto.setEmail(request.getParameter("email"));
				int result=mdao.addMember(dto);	
				request.setAttribute("proc", "join");
				request.setAttribute("joinResult", result);	
				isForward = true;
				dst="userResult.jsp";

			} else if(command.equals("/navlogin.do")) {				
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				MemberDTO dto = new MemberDTO();
				dto.setNaver_id(id);
				dto.setNaver_nickname(name);
				dto.setNaver_email(email);

				MemberDTO user = mdao.loginMember(dto);
				user.setPart("naver");

				String nickname=mdao.getUserNickname(user.getSeq());
				request.getSession().setAttribute("nickname", nickname);

				request.getSession().setAttribute("part", "naver");
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("loginId", user.getUserid());
				
				if(user.getBlock().equals("y")) {
					isForward = true;
					dst="errorBlock.jsp";
				}else {
					isForward = true;
					dst="main.jsp";
				}	

			}else if(command.equals("/kakaologin.do")) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				MemberDTO dto = new MemberDTO();
				dto.setKakao_id(id);
				dto.setKakao_nickname(name);
				dto.setKakao_email(email);

				MemberDTO user = mdao.addKakaoMember(dto);
				user.setPart("kakao");

				request.getSession().setAttribute("part", "kakao");
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("loginId", user.getUserid());

				String nickname=mdao.getUserNickname(user.getSeq());
				request.getSession().setAttribute("nickname", nickname);
				if(user.getBlock().equals("y")) {
					isForward = true;
					dst="errorBlock.jsp";
				}else {
					isForward = true;
					dst="main.jsp";
				}
					

			}else if(command.equals("/admin.do")) {
				String part = (String)request.getSession().getAttribute("part");
				String id = (String)request.getSession().getAttribute("loginId");
				MemberDTO mdto = new MemberDTO();
				mdto = mdao.getProfileInfo(part, id);
				if(part.equals("home")) {
					request.setAttribute("part", mdto.getPart());
					request.setAttribute("nickname", mdto.getNickname());
					request.setAttribute("email", mdto.getEmail());
				}else if(part.equals("naver")) {
					request.setAttribute("part", mdto.getPart());
					request.setAttribute("nickname", mdto.getNaver_nickname());
					request.setAttribute("email", mdto.getNaver_email());
				}else if(part.equals("kakao")) {
					request.setAttribute("part", mdto.getPart());
					request.setAttribute("nickname", mdto.getKakao_nickname());
					request.setAttribute("email", mdto.getKakao_email());
				}

				isForward = true;
				dst="admin.jsp";
			}else if(command.equals("/mypage.do")) {
				String part = (String)request.getSession().getAttribute("part");
				String id = (String)request.getSession().getAttribute("loginId");			
				MemberDTO user = (MemberDTO)request.getSession().getAttribute("user");
				
			
				
				MemberDTO mdto = mdao.newMemberInfo(user.getSeq(), part);
				System.out.println("seq :"+user.getSeq());
				
				System.out.println("mdto :"+mdto.getPhoto_system_file_name());
				
				/*mdto = mdao.getProfileInfo(part, id);*/
				
				/*String file_name = ((MemberDTO)request.getSession().getAttribute("user")).getPhoto_system_file_name();*/
				 request.setAttribute("file_name", mdto.getPhoto_system_file_name());
				 
				request.setAttribute("uploadPath", request.getAttribute("uploadPath"));
				if(part.equals("home")) {
					request.setAttribute("nickname", mdto.getNickname());
					request.setAttribute("email", mdto.getEmail());
					request.setAttribute("file_name", mdto.getPhoto_system_file_name());
				}else if(part.equals("naver")) {
					request.setAttribute("nickname", mdto.getNaver_nickname());
					request.setAttribute("email", mdto.getNaver_email());
					request.setAttribute("file_name", mdto.getPhoto_system_file_name());
				}else if(part.equals("kakao")) {
					request.setAttribute("nickname", mdto.getKakao_nickname());
					request.setAttribute("email", mdto.getKakao_email());
					request.setAttribute("file_name", mdto.getPhoto_system_file_name());
				}
				
			
				
				/*List<ReviewDTO> MyReviewResult = rdao.getMyReview(user.getSeq());
		        request.setAttribute("MyReviewResult", MyReviewResult);*/

				int currentPage = 0;
				String currentPageString = request.getParameter("currentPage");

				if(currentPageString == null) {
					currentPage = 1;
				} else {
					currentPage = Integer.parseInt(currentPageString);
				}

				String searchTerm = request.getParameter("search");
				List<ReviewDTO> MyReviewResult = rdao.getMyReview(user.getSeq(), currentPage*12-11, currentPage*12, searchTerm);
				request.setAttribute("MyReviewResult", MyReviewResult);

				String MyReviewPageNavi = rdao.getMyReviewPageNavi(user.getSeq(), currentPage, searchTerm);
				request.setAttribute("MyReviewPageNavi", MyReviewPageNavi);

				isForward = true;
				dst="mypage.jsp";
			}else if(command.equals("/logout.do")) {
				request.getSession().invalidate();

				isForward = true;
				dst="main.jsp";	


				//////////////비밀번호 찾기 기능 ->입력받은 이메일 확인
			}else if(command.equals("/checkEmail.do")){
				String id=request.getParameter("id");				
				String email = request.getParameter("email");
				int result =mdao.getEmail(id, email);
				request.setAttribute("checkEmailResult",result);
				if(result==10) {
					request.setAttribute("inputId",id);
				}else if(result==11) {			
					request.setAttribute("inputId",id);
					request.setAttribute("inputEmail", email);			
				}
				isForward = true;
				dst="findPwresult.jsp";
			}
			//////////////임시비밀번호 전송 기능	
			else if(command.equals("/sendtmpPw.do")) {
				String id=request.getParameter("id");
				String email=request.getParameter("email");
				SendMail smail = new SendMail();
				String pw =smail.maketmpPw();
				System.out.println(pw);
				int changeResult = mdao.changePw(id, pw);
				if(changeResult==1) {
					smail.send(id, email, pw);			
					request.setAttribute("mailResult", true);		
				}else {
					request.setAttribute("mailResult", false);
				}

				isForward = true;
				dst = "sendtmpPwResult.jsp";
			}else if(command.equals("/homeMemInfo.do")) {
				String part = (String)request.getSession().getAttribute("part");
				String id = (String)request.getSession().getAttribute("loginId");
				if(part.equals("home")) {
					MemberDTO mdto = new MemberDTO();
					mdto = mdao.getHomeMemberInfo(id, part);
					request.setAttribute("id", mdto.getUserid());
					request.setAttribute("pw", mdto.getPassword());
					request.setAttribute("nickname", mdto.getNickname());
					request.setAttribute("email", mdto.getEmail());
				}
				isForward = true;
				dst="editInfo.jsp";
			}else if(command.equals("/updateEmail.do")) {
				String part = (String)request.getSession().getAttribute("part");
				String id = (String)request.getSession().getAttribute("loginId");
				String email = request.getParameter("email");
				int result = mdao.updateEmail(id, part, email);
				request.setAttribute("result", result);

				isForward = true;
				dst="updateEmailView.jsp";
			}else if(command.equals("/toUpdateEmail.do")) {
				isForward=true;
				dst="updateEmail.jsp";
			}else if(command.equals("/toPwCheck.do")) {
				isForward=true;
				dst="pwCheck.jsp";

			}else if(command.equals("/pwCheck.do")) {
				String id = (String)request.getSession().getAttribute("loginId");
				String pw = request.getParameter("pw");
				boolean result = mdao.isHomeMemberPW(id, pw);
				request.setAttribute("result", result);		
				isForward = true;
				dst="pwCheckView.jsp";
			}else if(command.equals("/modiHomeMemInfo.do")) {
				String id = (String)request.getSession().getAttribute("loginId");
				String email = request.getParameter("email");
				String nickname = request.getParameter("nickname");
				String pw = request.getParameter("pw");
				int result = mdao.updateHomeMemberInfo(id, pw, email, nickname);
				request.setAttribute("result", result);

				isForward = true;
				dst = "editInfoView.jsp";
			}
			/*else if(command.equals("/toPwTrueCheck.do")) {
				isForward=true;
				dst="pwTrueCheck.jsp";
			}else if(command.equals("/pwTrueCheck.do")) {
				String id = (String)request.getSession().getAttribute("loginId");
				String pw = request.getParameter("pw");
				boolean result = mdao.isHomeMemberPW(id, pw);
				request.setAttribute("result", result);		
				isForward = true;
				dst="pwTrueCheckView.jsp";
			}else if(command.equals("/toModiPw.do")) {			
				isForward=true;
				dst="modiPw.jsp";
			}else if(command.equals("/modiPw.do")) {
				String id = (String)request.getSession().getAttribute("loginId");
				String pw = request.getParameter("pw");
				String repw = request.getParameter("repw");
				if(pw.equals(repw)) {
					int result = mdao.updatePw(id, repw);
					request.setAttribute("result", result);
					isForward = true;
					dst="modiPwView.jsp";
				}else {
					isForward = false;
				}
			}else if(command.equals("/toLogin.do")) {
				isForward=true;
				dst="newlogin.jsp";
*/

			else if(command.equals("/editInfoNK.do")){
				MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
				String part = user.getPart();
				
				if(part.equals("naver")) {
					request.setAttribute("id", user.getNaver_id());
					request.setAttribute("nickname", user.getNaver_nickname());
					request.setAttribute("email", user.getNaver_email());
				}else if(part.equals("kakao")) {
					request.setAttribute("id", user.getKakao_id());
					request.setAttribute("nickname", user.getKakao_nickname());
					request.setAttribute("email", user.getKakao_email());
				}
				isForward=true;
				dst="editInfoNK.jsp";

			}else if(command.equals("/modiNKMemInfo.do")) {
				MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
				String part = user.getPart();
				int seq = user.getSeq();
				String id = request.getParameter("id");
				String nickname = request.getParameter("nickname");
				String email = request.getParameter("email");
				int result  = mdao.updateNKMemberInfo(id, nickname, email, seq, part);
				request.setAttribute("result", result);
				
				isForward= true;
				dst = "editInfoNKView.jsp";
			}else if(command.equals("/toEditInfoNK.do")) {
				
				isForward=true;
			dst="toEditInfoNK.jsp";
				//////////////비밀번호 찾기 기능 ->입력받은 이메일 확인
			}else if(command.equals("/checkEmail.do")){
				String id=request.getParameter("id");
				String email = request.getParameter("email");
				int result =mdao.getEmail(id, email);
				request.setAttribute("checkEmailResult",result);
				if(result==10) {
					request.setAttribute("inputId",id);
					if(result==11) {
						request.setAttribute("inputEmail", email);			
					}			
				}
				isForward = true;
				dst="findPwresult.jsp";
			}
			//////////////임시비밀번호 전송 기능	
			else if(command.equals("/sendtmpPw.do")) {
				String id=request.getParameter("id");
				String email=request.getParameter("email");
				SendMail smail = new SendMail();
				String pw =smail.maketmpPw();
				int changeResult = mdao.changePw(id, pw);
				if(changeResult==1) {
					smail.send(id, email, pw);			
					request.setAttribute("mailResult", true);		
				}else {
					request.setAttribute("mailResult", false);
				}

				isForward = true;
				dst = "sendtmpPwResult";
			}else if(command.equals("/profileImg.do")) {
				// 이미지를 업로드할 경로
				String uploadPath = request.getServletContext().getRealPath("file");
				int size = 10 * 1024 * 1024;	// 업로드 사이즈 10M 이하,
				System.out.println(uploadPath);
				// 경로가 없을 경우 결로 생성
				File f = new File(uploadPath);
				if(!f.exists()) {
					f.mkdir();
				}
				
				// 원래 파일명, 시스템에 저장되는 파일명
				String ofileName ="";
				String sfileName ="";
				
				try {
					// 파일 업로드 및 업로드 후 파일명을 가져옴
					MultipartRequest mr = new MultipartRequest(request, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
					Enumeration<String> files = mr.getFileNames();
					String file = files.nextElement();
					ofileName = mr.getOriginalFileName(file);
					sfileName = mr.getFilesystemName(file);
				
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				uploadPath = contextPath +"/file/"+ sfileName;
				
		/*		// 생성된 경로를 JSON 형식으로 보내주기 위한 설정
				JSONObject jobj = new JSONObject();
				jobj.put("url", uploadPath);
				
				response.setContentType("application/json");
				response.getWriter().print(jobj.toJSONString());*/
				
				MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
				int user_seq = user.getSeq();
				System.out.println("user_seq :"+user_seq);
				int result = mdao.updateProfileImg(user_seq, sfileName);
				request.setAttribute("result", result);
				String file_name =user.getPhoto_system_file_name();
				String part = user.getPart();
				user = mdao.newMemberInfo(user_seq, part);
				request.setAttribute("file_name",file_name);
				request.setAttribute("user_seq", user_seq);
				
				System.out.println("file_name :"+user.getPhoto_system_file_name());
				System.out.println("fileUpload결과 : "+result);
				System.out.println(uploadPath);
			
				request.setAttribute("uploadPath", uploadPath);
		
				
			isForward=true;
			dst = "mypage.do";
			}
			
			//-----------------------admin.jsp > 모든 회원 리스트보기
			else if(command.equals("/showMembers.do")) {
				List<MemberDTO> mlist = new ArrayList<>(); 
				mlist=adao.getAllMembers();
				request.setAttribute("memberList", mlist);
				
				isForward = true;
				dst = "admin/admin.jsp";
			}
			
			//-----------------------admin.jsp > 회원계정 차단
			else if(command.equals("/blockMember.do")) {
				int seq = Integer.parseInt(request.getParameter("sequence"));
				String isBlocked = adao.checkBlock(seq);
				
				int result = adao.changeBlock(seq,isBlocked);
				request.setAttribute("blockResult", result);
				isForward = true;
				dst = "admin/admin.jsp";
			}
			
			



				
				
				
				
				
				
				
			

			if(isForward) {
				RequestDispatcher rd = request.getRequestDispatcher(dst);
				rd.forward(request, response);
			} else {
				response.sendRedirect(dst);
			}
		}catch(Exception e) {e.printStackTrace();}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
