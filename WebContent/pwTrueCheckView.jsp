<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
if(${result}>0){
	/* window.open("modiPw.do","_blank","width=500, height=300, scrollbars=no");	  */
	opener.location.href="toModiPw.do";
	 	close();
	 
	 /* location.href="homeMemInfo.do"; */
}else{
	alert("비밀번호가 일치하지 않습니다 \n\t 관리자에게 문의해주세요");
	opener.location.href="mypage.do";
	 close(); 
/* location.href="mypage.do"; */
}
</script>
</body>
</html>