<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSA</title>
<meta id="extViewportMeta" name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="stylesheet" href="css/jquery.mobile-1.1.1.min.css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.1.1.min.js"></script>
<script type="text/javascript" src="js/slideinmenu.js"></script>
</head>
<body>


	<div id="menu">
		<ul data-role="listview" id="menuList">
			<li><a href="/home" class="contentLink">Home</a></li>
			<li><a href="/conventions" class="contentLink">Conventions</a></li>
			<li><a href="/debates" class="contentLink">Debates</a></li>
			<li><a href="/links" class="contentLink">Links</a></li>
		</ul>
	</div>
	
	<div data-role="page">

		<div data-role="header" id="header">
			<a href="#" class="showMenu" data-role="none"> <img
				src="/images/navigation.png" id="navButton">
			</a>
			<h1>SoCal JSA</h1>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			console.log($("#header").height());
			$("#navButton").css("max-height", $("#header").height())
			$("#navButton").css("max-width", $("#header").height());
		});
	</script>

</body>

</html>