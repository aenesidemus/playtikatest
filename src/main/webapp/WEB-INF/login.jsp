<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <script src="js/update.js"></script>-->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script language="javascript">
	$(document).ready(function() {
		$("<p>Test</p>").appendTo(".inner");
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KISS chat</title>
</head>
<body>
  <div class="inner"></div>
	<form action="NicController" method="post">
		Enter login : <input type="text" name="login"> <BR> <input
			type="submit" value="Login" />
	</form>
</body>
</html>
