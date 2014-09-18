<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script language="javascript">
	function readCookie(name) {
	    var nameEQ = name + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0;i < ca.length;i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1,c.length);
	        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	    }
	    return null;
	}
	$(document).ready(function() {
		$("#subm").click(function() {
			text = $("#message").val();
			text = jQuery.trim(text);
			text = encodeURIComponent(text);

			$("#message").val('');
			$("#message").focus();
			if (text.length > 0){
			$.ajax({
				type : "POST",
				url : "Message",
				data : "message=" + text,
				success : function() {
                $('<p>').text(readCookie("login") + " : " + text).appendTo(".messages");
				},
			});}
		});
		setInterval(updatem, 1000);
		setInterval(updateu, 30000);
	});
		function updatem() {
  $.get('Message', function(responseJson) {
	            $.each(responseJson, function(key, value) {
	                $('<p>').text(key + " : " + value).appendTo(".messages");
	            });
	        });
	        }
		function updateu() {
			  $.get('Users', function(responseJson) {
				            $.each(responseJson, function(index, value) {
				                $('<p>').text(value).appendTo(".users");
				            });
				        });
				        }	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>KISS chat</title>
</head>
<body style="overflow-y:hidden">
	<div>
 		Welcome ${user.login}.
		<a href="Logout">Logout</a>
	</div>
	<div style="width: 20%; overflow-y:auto; float:left; height: 80vh" class="users">
    <c:forEach items="${users}" var="user">
            <p>${user.login}</p>
    </c:forEach>
	</div>
	<div style="width: 80%; overflow-y:auto; float:right; height: 80vh" class="messages">
    <c:forEach items="${messages}" var="message">
            <p>${message.message}</p>
    </c:forEach>
	</div>
	<div style="clear: both; height: 20vh">
		<textarea id="message" rows="2" cols="100"></textarea>
		<button id="subm">Send</button>
	</div>
</body>
</html>