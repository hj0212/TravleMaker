<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 부트 스트랩 코드-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
	integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
	crossorigin="anonymous"></script>
        <style>
        	.container {
        		margin-top : 15%;
        	}
        	
        	body {
				background: #1d76a6;
				background: -webkit-linear-gradient(90deg, #f9fafc 10%, #84dcf9 90%);
				background: -moz-linear-gradient(90deg, #f9fafc 10%, #84dcf9 90%);
				background: -ms-linear-gradient(90deg, #f9fafc 10%, #84dcf9 90%);
				background: -o-linear-gradient(90deg, #f9fafc 10%, #84dcf9 90%);
				background: linear-gradient(90deg, #f9fafc 10%, #84dcf9 90%);
				height: 1200px;
			}
        </style>
    </head>
    <body>
        <c:choose>
		<c:when test="${sessionScope.loginId !=null}">
			<%@include file="../include/mainNavi_login.jsp"%>
		</c:when>
		<c:otherwise>
			<%@include file="../include/mainNavi.jsp"%>
		</c:otherwise>
		</c:choose>
		
    	<div class="container text-center">
   			<h1>부적절한 접근!</h1>
   			<p>부적절한 경로로 접근하셨습니다</p>
   			<button type="button" class="btn btn-light" id="back">메인으로가기</button>
    	</div>
	</body>
	<script>
		$("#back").click(function(){
			location.href = "../main.jsp";
		})
	</script>
    </html>