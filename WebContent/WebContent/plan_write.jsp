<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=h6OAt0uXG7GgMxCgzJWa&submodules=geocoder"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.1.0/css/all.css">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0/css/tempusdominus-bootstrap-4.min.css" />
<script src="source/lib/lightswitch05-table-to-json/jquery.tabletojson.min.js"></script>
<style>
#title-board {
	font-size: 15px;
}

.container {
	margin-top: 120px;
}

.input-group {
	margin-bottom: 10px;
}

#plan-board {
	margin-top: 20px;
	width: 70%;
	float: left
}

#plan-div {
	margin-top: 20px;
}

#end-bt:hover {
	background-color: #e9e9e9;
}

#plus-plan:hover {
	background-color: #e9e9e9;
}

.col-10 {
	padding: 0px;
	width: 150px;
	float: left;
	margin-left: 24px;
}

.cococo {
	box-sizing: border-box;
	width: 10px;
	margin-left: 24px;
	float: left;
	text-align: center;
	vertical-align: middle;
}

#schedule-plan {
	padding: 0px;
	text-align: center;
	vertical-align: middle;
	margin-bottom: 10px;
}

.active {
	background-color: #eee;
}

#schedule-plan div {
	display: inline;
}

/* #schedulearea {
	height: 300px;
	overflow: auto;
} */

#extraarea {
	height: 38px;;
}


#totalbudget {
	width: 150px;
	float: left;
}

#delete-table {
	float: right;
}
#title-board {
	background: white;
}
</style>

</head>

<body>
	<c:choose>
		<c:when test="${sessionScope.user.seq !=null}">
			<%@include file="include/mainNavi_login.jsp"%>
		</c:when>
		<c:otherwise>
			<%@include file="include/mainNavi.jsp"%>
		</c:otherwise>
	</c:choose>
	<div class="container">
		<div class="input-group input-group-lg">
		
			<div class="input-group-prepend">
				<span class="input-group-text" id="inputGroup-sizing-lg">여행</span>
			</div>
			<input type="text" class="form-control" aria-label="Large"
				aria-describedby="inputGroup-sizing-sm" id="title-board"
				name="plantitle" value="${plan_title}" readonly>
			
		</div>
		<div id="schedulearea">
			<table class="table table-bordered" id="schedule-plan">
				<thead>
					<tr>
						<th scope="col">시간</th>
						<th scope="col">장소</th>
						<th scope="col">일정</th>
						<th scope="col">예산</th>
						<th scope="col">참조</th>
						<th scope="col" style="width: 20px;">삭제</th>
					</tr>
				</thead>
				
				<tbody id="schedule-tbody"
					style="table-layout: fixed; word-break: break-all;">
					<c:if test="${create == 'f'}">
					<c:forEach var="item" items="${scheduleList}">
					<tr class="clickable-row">
						<th scope="row" style="height: 50px;">${item.schedule_starttime}~${item.schedule_endtime}</th>
						<td name="place">${item.schedule_place}</td>
						<td name="schedule">${item.schedule_plan}</td>
						
						<c:if test="${!empty budgetList}">
						<c:set var="loop_flag" value="false" />
						<c:forEach var="bitem" items="${budgetList}" varStatus="index">
							<c:if test="${not loop_flag }">
								<c:if test="${item.schedule_seq == bitem.schedule_seq}">
									<td name="money">${bitem.budget_amount}</td>
									<c:set var="loop_flag" value="true" />
								</c:if>
								<c:if test="${index.last && item.schedule_seq != bitem.schedule_seq}">
									<td name="money"></td>
									<c:set var="loop_flag" value="true" />
								</c:if>
							</c:if>
						</c:forEach>
						</c:if>
						<c:if test="${empty budgetList}">
						<td name="money"></td>
						</c:if>
						<td name="reference">${item.schedule_ref}</td>
						<td><button style="float: left; border: none;" type="button"
								class="btn btn-outline-danger">
								<i class="far fa-times-circle"></i>
							</button>
						<input type="hidden" class="schedule_seq" value="${item.schedule_seq}">	
						</td>
					</tr>
					</c:forEach>
					</c:if>
					<tr class="clickable-row active new">
						<th scope="row" style="height: 50px;"></th>
						<td name="place"></td>
						<td name="schedule"></td>
						<td name="money"></td>
						<td name="reference"></td>
						<td><button style="float: left; border: none;" type="button"
								class="btn btn-outline-danger">
								<i class="far fa-times-circle"></i>
							</button><input type="hidden" class="schedule_seq" value="0"></td>

					</tr>
				</tbody>
			</table>
			
			<div id="extraarea">
				<input type="text" id="totalbudget" class="form-control" value="${totalBudget}원" readonly>
				<button type="button" class="btn btn-outline-danger"
					id="delete-table">삭제</button>
			</div>
			<script></script>
		</div>

		<div id="plan-board">
			<form action="addSchedule.plan" method="post" id="scheduleform">
			<input type="hidden" name="plan" value="${param.plan}">
			<input type="hidden" name="day" value="${param.day}">
			<input type="hidden" name="schedule_seq" value="0">
			<table class="table table-bordered" id="schedule-boarder">
				<thead>
				</thead>
				<tbody>
					<tr>
						<th scope="row"
							style="background-color: #e9e9e9; text-align: center; vertical-align: middle;">시간</th>
						<td style="width: 70%; text-align: center;">

							<div class="col-10">
								<input class="form-control" type="time"
									id="start-time" name="starttime"/>
							</div>
							<div class="cococo">~</div>
							<div class="col-10">
								<input class="form-control" type="time"
									id="end-time" name="endtime"/>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"
							style="background-color: #e9e9e9; text-align: center; vertical-align: middle">장소</th>
						<td>
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Search"
									readonly id="place" name="place">
								<div class="input-btn">
									<button class="btn btn-default" type="button"
										style="height: 100%; border: 1px">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"
							style="background-color: #e9e9e9; text-align: center; vertical-align: middle">일정</th>
						<td><input class="form-control" id="schedule" name="schedule"></td>

					</tr>
					<tr>
						<th scope="row"
							style="background-color: #e9e9e9; text-align: center; vertical-align: middle">예산
							<!-- <button type='button' class='btn btn-outline-primary btn-sm'
								style='float: right' id="moneyaddbtn">
								<i class='fa fa-plus'></i>
							</button> -->
						</th>
						<td><input type="text" class="form-control" id="money" name="money"></td>
			
					</tr>
					<tr>
						<th scope="row"
							style="background-color: #e9e9e9; text-align: center; vertical-align: middle">참조</th>
						<td><input type="text" class="form-control" id="reference" name="reference"></td>
					</tr>
				</tbody>
			</table>
			<div style="text-align: right">
				<button type="button" class="btn btn-outline-primary"
					id="success-primary">등록</button>
			</div>
			</form>
		</div>
		<!-- <div style="width: 40%; float: right" id="plan-div">
			<div style="text-align: right">
				<button class="btn btn-inline-primary btn-lg" id="endbtn">여행계획
					완료</button>

			</div>
		</div> -->
	</div>
<script>
$(document).ready(function() {
	$("#endbtn").click(function() {
		/*$("#schedule-plan tr:last").remove();
		$("#schedule-plan thead tr th:last").remove();
		$("#schedule-plan tbody tr td:last").remove();
        
		var table = $("#schedule-plan").tableToJSON();
		var table_json = JSON.stringify(table);
		alert(table_json);
		
		 $.ajax({
			url:"saveschedules.plan",
			dataType:"json",
			type:"POST",
			data:table_json,
			contentType:"application/x-www-form-urlencoded",
			success: function() {
			},
			error:function() {
        	}
		}) */
	});
	
	
	/* budgetcount = 1;
	$("#moneyaddbtn").click(function() {
		budgetcount++;
	 	$("#schedule-boarder>tbody>tr>td[name='budget']").append("<div class='input-group mb-1'><input type='text' class='form-control' placeholder='예) 입장료' id='ex"+budgetcount+"'><input type='text' class='form-control' placeholder='10000' id='money"+budgetcount+"'>"
				+"<div class='input-group-prepend'><span class='input-group-text'>원</span></div></div><button style='float: left; border: none' type='button' class='btn btn-outline-danger'><i class='far fa-times-circle'></i></button>");
	});

	$("#moneyxbtn").click(function() {
		
	}); */
	
    $("#plan-table").on('click', "button[type='button']", function(event) {
        var index = $(event.currentTarget).closest("tr").index();
        var info = $("#plan-tbody")[0];
        info.deleteRow(index, datecount--);

        $("#plan-table tr:last td:last-child").append("<button type='button' class='btn btn-outline-danger btn-sm'style='float:right'><i class='fa fa-times'></i></button>");

    });

    $("#schedule-plan td:last-child").hide();
    $("#schedule-plan th:last-child").hide();

    $("#delete-table").click(function() {
        if ($("#delete-table").text() == "삭제") {
            $("#delete-table").text("완료");
            $("#delete-table").attr("class", "btn btn-outline-primary");
            $("#schedule-plan td:last-child").show();
            $("#schedule-plan th:last-child").show();
        } else {
            $("#delete-table").text("삭제");
            $("#delete-table").attr("class", "btn btn-outline-danger");
            $("#schedule-plan td:last-child").hide();
            $("#schedule-plan th:last-child").hide();
        }
    });

    $("#schedule-plan").on('click', "button[type='button']", function(event) {
        var index = $(event.currentTarget).closest("tr").index();
        var info = $("#schedule-tbody")[0];
        info.deleteRow(index);
    });

    var schedulecount = 1;
    timeArray = new Array();
    $("#success-primary").click(function() {
    	console.log("active:" + $("#schedule-plan>tbody>.active>th").html());
        con = "";
        if($("#schedule-plan>tbody>.active>th").html() != "") {
        	con = "일정을 수정하시겠습니까?";
        } else {
        	con = "일정을 추가하시겠습니까?";
        }
        
        starttime = $("#start-time").val();
        endtime = $("#end-time").val();
        place = "이레빌딩"; /*  $("#place").val("이레빌딩");*/
        schedule = $("#schedule").val();
        money = $("#money").val();
        reference = $("#reference").val();
        
            if (starttime == "" || endtime == "") {
            	alert("시간을정해주세요");
            } else if (place == "") {
                alert("장소를정해주세요");
            } else if (schedule == "") {
                alert("일정을적어주세요");
            } else if (confirm(con)) {
            	$("#scheduleform").submit();
            	var contents = '';
                contents += '<tr class="clickable-row new active"><th style="height:50px;"></th><td name="place"></td><td name="schedule"></td>';
                contents += '<td name="money"></td>';
                contents += '<td name="reference"></td>';
                contents += '<td><button style="float:left;border:none"type="button"class="btn btn-outline-danger"><i class="far fa-times-circle"></i></button><input type="hidden" name="schedule_seq" value="0"></td>';
                contents += '</tr>';
                
            	if($("#schedule-plan>tbody>.active>th").val() == "") {	// 빈칸 = 마지막줄
            		console.log("빈칸");
            		$("#schedule-plan>tbody>tr").removeClass('new');
                	$("#schedule-plan>tbody>tr").removeClass('active');
                	$("#schedule-plan>tbody:last").append(contents);
                    $("#schedule-plan td:last-child").hide();
                   
                } else { // 빈칸x = 마지막줄x
                	var cursor = "#schedule-plan>tbody>.active";
                	$("#schedule-plan>tbody>tr").removeClass('active');
                	$("#schedule-plan>tbody>.new").addClass('active');
                }

                $("#start-time").val("");
                $("#end-time").val("");
                $("#place").val("");
                $("#schedule").val("");
                $("#money").val("");
                $("#reference").val("");
            }
    });
    
    $("#schedule-plan").on('click', '.clickable-row', function(event) {
    	  $(this).addClass('active').siblings().removeClass('active');
    	  var seq = $(".active .schedule_seq").val();
    	  console.log("선택:" + seq);
    	  $("#plan-board input[name='schedule_seq']").val(seq);
    	  console.log("seq셋팅: " + $("#plan-board input[name='schedule_seq']").val());
    	  var timestr = $("#schedule-plan>tbody>.active>th").html().split("~");
    	  $("#start-time").val(timestr[0]);
          $("#end-time").val(timestr[1]);
          $("#place").val($("#schedule-plan>tbody>.active>td[name='place']").html());
          $("#schedule").val($("#schedule-plan>tbody>.active>td[name='schedule']").html());
          $("#money").val($("#schedule-plan>tbody>.active>td[name='money']").html());
          $("#reference").val($("#schedule-plan>tbody>.active>td[name='reference']").html());
    });

});
</script>
</body>
</html>