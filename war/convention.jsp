<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.jsa.socal.mobile.Debate"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=request.getAttribute("title") %></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="stylesheet" href="css/jquery.mobile-1.1.1.min.css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.1.1.min.js"></script>
</head>
<body>

	<%
		ArrayList<Debate> debates = (ArrayList<Debate>) request
				.getAttribute("debates");
	%>

	<div data-role="page">

		<div data-role="header" id="header">
			<h1>Conventions</h1>
			<div data-role="navbar">
				<ul data-role="navbar" id="menuList">
					<li><a href="/home" class="contentLink">Home</a></li>
					<li><a href="/conventions" class="contentLink">Conventions</a></li>
					<li><a href="/debates" class="contentLink">Debates</a></li>
					<li><a href="/links" class="contentLink">Links</a></li>
				</ul>
			</div>
		</div>

		<div data-role="content">
			<ul data-role="listview">
				<%
					for (Debate d : debates) {
				%>
				<li><a href="/convention?id=<%=d.getId() %>">
						<h2><%=d.getTitle()%></h2>
						<p><%=d.getResolution()%></p>
				</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</div>

</body>

</html>