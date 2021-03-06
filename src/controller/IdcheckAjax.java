package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;

/**
 * Servlet implementation class IdcheckAjax
 */
@WebServlet("*.Ajax")
public class IdcheckAjax extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
		String requestURI= request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf8");
		
		MemberDAO mdao = new MemberDAO();
		
		if(command.equals("/idcheck.Ajax")) {				
			String userid = request.getParameter("userid");
			boolean idcheck = mdao.check(userid);
			PrintWriter pw = response.getWriter();
			
			if(userid.equals("")) {
				System.out.println(userid);
				pw.println("id");
				return;
			}
			
			if(userid.length()>45) {
				pw.println("<font color=red>id(최대48글자입니다)</font>");
				return;
			}
			if(userid.length()<8) {
				pw.println("<font color=red>id(최소8글자이상입니다)</font>");
				return;
			}
		
			if(idcheck) {
				pw.println("<font color=red>id(사용중인 아이디입니다)</font>");
				
			}else{
				pw.println("<font color=blue>id(사용가능한아이디입니다)</font>");
					
			}
			
			
			
			
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
