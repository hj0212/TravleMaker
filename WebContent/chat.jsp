<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>

<script>
//채팅 추가 .. 모든 페이지에 있어야 함.
(function(t,a,l,k,j,s){
s=a.createElement('script');s.async=1;s.src="https://cdn.talkjs.com/talk.js";a.head.appendChild(s)
;k=t.Promise;t.Talk={ready:{then:function(f){if(k)return new k(function(r,e){l.push([f,r,e])});l
.push([f])},catch:function(){return k&&new k()},c:l}};})(window,document,[]);
</script>


</head>
<body>

</body>
</html>
=======
<link rel="stylesheet" href="css/livechat.css" type="text/css">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
	window.onload = function() {

		$("#livechathistory").scrollTop($("#livechathistory")[0].scrollHeight);

		if ("WebSocket" in window) {
			var ws = new WebSocket(" ws://192.168.20.53:8080/WEB_06_18/websocket");

			var str;

			$("#livemsg").keydown(function(event) {
				if (event.which === 13) {
					var id = "";
					var msg = $("#livemsg").val();

					ws.send(JSON.stringify({
						id : id,
						msg : msg
					}));
					$("#livemsg").val("");

				}
			});

			ws.onopen = function() {

				//document.getElementById("contents").innerHTML += str+"�떂�씠 �엯�옣�븯�뀲�뒿�땲�떎"+"<br>";
			};
			ws.onmessage = function(msg) {
				var obj = JSON.parse(msg.data);
				console.log(obj.id + ":" + obj.msg);
				$("#livechathistory")
						.append(
								"<hr class=livehr><div class=chat-message clearfix><img src=https://gravatar.com/avatar/2c0ad52fc5943b78d6abe069cc08f320?s=32 alt='' width='32' height='32'><div class=chat-message-content clearfix><h5 class=live5>"
										+ obj.id
										+ "</h5><p class=livep>"
										+ obj.msg + "</p></div></div>");
			};
			ws.onclose = function() {

			};

		} else {
			alert("釉뚮씪�슦���媛� �쎒�냼耳볦쓣 吏��썝�븯吏� �븡�뒿�땲�떎");
		}

	}
</script>


<div id="live-chat">

	<header class="clearfix">

		<a href="#" class="chat-close">x</a>

		<h4 class="live4">�삎�꽠</h4>

		<span class="chat-message-counter">!</span>

	</header>

	<div class="chat" id="chatchat">

		<div class="chat-history" id="livechathistory"></div>
		<!-- end chat-history -->

		<p class="chat-feedback">Messages</p>



		<fieldset>

			<input type="text" placeholder="Type your message���" class="liveinput"
				id="livemsg"> <input type="hidden" class="liveinput">

		</fieldset>



	</div>
	<!-- end chat -->

</div>
<!-- end live-chat -->
<script>
	(function() {
		$('#live-chat header').on('click', function() {

			$('.chat').slideToggle(300, 'swing');
			$('.chat-message-counter').fadeToggle(300, 'swing');

		});
		$('.chat-close').on('click', function(e) {

			e.preventDefault();
			$('#live-chat').fadeOut(300);

		});

	})();
</script>
>>>>>>> KakaoAPI
