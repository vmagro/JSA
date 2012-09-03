<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Comment</title>
</head>
<body>

	<div data-role="page">
		<div data-role="header">
			<h1>New Comment</h1>
		</div>

		<%
			User u = (User) request.getAttribute("user");
		%>

		<div data-role="content">
			<p>
				Your name (<%=u.getNickname()%>) will be recorded along with your
				comment. Please keep your comments appropriate. Thank you for your
				participation.
			</p>

			<form action="/debate" method="POST">
				<input type="hidden" name="action" value="add-comment">
				<input type="hidden" name="id" value="<%=request.getParameter("id") %>">
				<textarea name="text" cols="40" rows="20"></textarea>
				<input type="submit" value="Submit">
			</form>
		</div>
	</div>

</body>
</html>