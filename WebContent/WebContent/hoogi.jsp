<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/all.css"
	integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt"
	crossorigin="anonymous">



<style>
</style>
</head>
<body>
<head>

<meta charset="utf-8">

<title>일정 확인</title>

<style type="text/css">
* {
	padding: 0;
	margin: 0;
	text-align: center;
}


.container {
	width: 960px;
}

.wrapper {
	position: relative;
	margin: 10px 0 10px;
	height: 300px;
}

.left_half {
	position: absolute;
	top: 0;
	right: 50%;
	left: 0%;
	bottom: 0;
	overflow: visible;
}

.right_half {
	position: absolute;
	top: 0;
	right: 0%;
	left: 50%;
	bottom: 0;
	overflow: visible;
}

.form-control {
	position: absoulte;
	float: left;
}

.pagination {
	position: absoulte;
	float: left;
}
</style>

</head>


<body>

	<div class="container text-center">
		<div class="form-group">
			<center>
				<input type="text" class="form-control" id="title" readonly
					style="width: 600px;">
			</center>
			<button class="btn btn-default">수정</button>
			<button class="btn btn-default">후기보기</button>
			<button class="btn btn-default">목록</button>

			<div class="wrapper">
				<div class="left_half">
					<div style="border: 1px solid gray; width: 450px; height: 300px;"></div>
				</div>
				<div class="right_half">
					<div style="border: 1px solid gray; width: 450px; height: 300px;"></div>
				</div>
			</div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col">1</th>
						<th scope="col">2</th>
						<th scope="col">3</th>
						<th scope="col">4</th>
						<th scope="col">5</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td scope="row">""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
					</tr>
					<tr>
						<td scope="row">""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
					</tr>
					<tr>
						<td scope="row">""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
					</tr>
					<tr>
						<td scope="row">""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
					</tr>
					<tr>
						<td scope="row">""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
						<td>""</td>
					</tr>
				</tbody>
			</table>
			<nav aria-label="Page navigation">
			<ul class="pagination justify-content-center">
				<li class="page-item disabled"><a class="page-link" href="#"
					tabindex="-1">Previous</a></li>
				<li class="page-item"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">Next</a></li>
			</ul>
			</nav>
			<button type="button" class="btn btn-outline-danger" >
				<i class="fas fa-heart"></i> 30
			</button>
			<button type="button" class="btn btn-outline-primary" >
				<i class="far fa-frown"></i> 30
			</button>
			<button class="btn btn-outline-success">스크랩</button>
			
			<button class="btn btn-default" style="float: right;">신고</button>
			<br>
			<div class="input-group mb-3">
				<input type="text" class="form-control" placeholder="reply"
					aria-label="reply" aria-describedby="basic-addon2">
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="button"
						id="commentbtn" name="commentbtn">댓글작성</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>