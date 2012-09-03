<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.jsa.socal.mobile.Debate"%>
<%@ page import="org.jsa.socal.mobile.Debate.Comment"%>
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
<link rel="stylesheet" href="/css/jquery.mobile-1.1.1.min.css" />
<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.mobile-1.1.1.min.js"></script>
</head>
<body>

	<%
		Debate debate = (Debate) request.getAttribute("debate");
	%>

	<div data-role="page">

		<div data-role="header" id="header">
			<h1><%=debate.getTitle() %></h1>
			<div data-role="navbar">
				<ul data-role="navbar" id="menuList">
					<li><a href="/" class="contentLink">Home</a></li>
					<li><a href="/conventions" class="contentLink">Conventions</a></li>
					<li><a href="/links" class="contentLink">Links</a></li>
				</ul>
			</div>
		</div>

		<div data-role="content">
			<h3>Resolution</h3>
			<p><%=debate.getResolution() %></p>
			<h5>Comments</h5>
			<a href="/debate?action=new-comment&id=<%=debate.getId() %>"
				data-role="button" data-rel="dialog">Leave a comment</a> <br>
			<ul data-role="listview">
				<%
					ArrayList<Comment> comments = debate.getComments();
					for(Comment c : comments){
				%>
				<li>
					<h5><%=c.getAuthor() %></h5>
					<p><%=c.getDateString() %></p> <br>
					<p><%=c.getText() %></p>
					<%if((Boolean) request.getAttribute("admin")){%>
						<a href="/debate?action=delete-comment&comment=<%=c.getId() %>&id=<%=debate.getI %>" data-role="button" >Delete</a>
					<%}%>
				</li>
				<%} %>
			</ul>
		</div>
	</div>

</body>

</html>