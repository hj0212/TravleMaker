<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> --%>

<header>
<style>
	.logo {
	background: transparent url('source/img/travellogo2.png') center center no-repeat;
	width: 195px;
	background-size: contain;
	text-indent: 100%;
	white-space: nowrap;
	overflow: hidden;
}
</style>

	<nav
		class="navbar fixed-top navbar-right navbar-expand-lg navbar-light shadow-sm"
		style="background-color: white; height:100px" id="naviId">
		<a class="navbar-brand" href="main.jsp" style="font-size: 30px"><h1 class="logo">Logo</h1></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavDropdown">
			<ul class="navbar-nav ml-auto ">
				<li class="nav-item active"><a class="nav-link" href="planboard.plan">Plans
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="selectSchedule.plan?plan=1&day=1&create=f">Schedules</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="reviewboard.bo">Review</a></li>
				<li class="nav-item"><a class="nav-link" href="freeboard.bo">Board</a></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownMenuLink" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"> ${sessionScope.user.userid}<span
						class="caret"></span></a>
					<ul class="dropdown-menu mt-1 mr-5"
						aria-labelledby="navbarDropdownMenuLink">
						<li><a class="dropdown-item" href="mypage.do">My Page</a></li>
						<li><a class="dropdown-item" href="traveldiary.html">My
								Plans</a></li>
						<li><a class="dropdown-item" href="logout.do">Log Out</a></li>
					</ul></li>
			</ul>
		</div>
	</nav>

</header>
