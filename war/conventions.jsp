<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.jsa.socal.mobile.Convention"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSA</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="/images/iphone_icon.png"/>
<link rel="apple-touch-startup-image" href="/images/iphone_splash.png" />
<link rel="stylesheet" href="/css/jquery.mobile-1.1.1.min.css" />
<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.mobile-1.1.1.min.js"></script>
<link rel="stylesheet"
	href="css/redwhiteandblue/red-white-and-blue.min.css" />

</head>
<body>

	<%
		ArrayList<Convention> conventions = (ArrayList<Convention>) request
			.getAttribute("conventions");
	%>

	<div data-role="page">
	
		<div data-role="header" id="header">
			<h1>Conferences</h1>
			<div data-role="navbar">
				<ul data-role="navbar" id="menuList">
					<li><a href="/" class="contentLink">Home</a></li>
					<li><a href="/conventions" class="contentLink">Conferences</a></li>
					<li><a href="/links" class="contentLink">Links</a></li>
				</ul>
			</div>
		</div>

		<div data-role="content">
			<ul data-role="listview">
				<%
					for (Convention c : conventions) {
				%>
				<li><a href="/convention?id=<%=c.getId()%>">
						<h2><%=c.getTitle()%></h2>
						<h6><%=c.getLocation()%></h6>
						<p><%=c.getDateString()%></p>
				</a></li>
				<%
					}
				%>
			</ul>
		</div>
		
		<script type="text/javascript">
			window.addEventListener("load", function() {
				// Set a timeout...
				setTimeout(function() {
					// Hide the address bar!
					window.scrollTo(0, 1);
				}, 0);
			});
		</script>
		
	</div>

</body>

</html>