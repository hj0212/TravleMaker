<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${param.proc==login}">
			<c:choose>
				<c:when test="${loginResult==true}">
					<script>
						alert("로그인 성공");
						location.href = "index.jsp";
					</script>
				</c:when>
				<c:otherwise>
					<script>
						alert("로그인 실패");
						location.href = "newlogin.jsp";
					</script>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${param.proc==join}">
			<c:choose>
				<c:when test="${joinResult==1}">
					<script>
						alert("가입을 축하합니다! 로그인해주세요");
						location.href = "newlogin.jsp";
					</script>
				</c:when>
				<c:when test="${joinResult==-1}">
					<script>
						alert("이미 사용중인 아이디입니다.");
						location.href = "newlogin.jsp";
					</script>
				</c:when>
				<c:otherwise>
					<script>
						alert("다시 시도해 주세요.");
						location.href = "newlogin.jsp";
					</script>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>

</body>
</html>