<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="org.jsa.socal.mobile.Convention"%>
<%@ page import="org.jsa.socal.mobile.Debate"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SoCal JSA Admin</title>
<link type="text/css" href="/css/smoothness/jquery-ui-1.8.23.custom.css"
	rel="Stylesheet" />
<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.23.custom.min.js"></script>
</head>
<body>

	<%
		ArrayList<Debate> debates = (ArrayList<Debate>) request.getAttribute("debates");
	%>

	<h1>Convention Admin</h1>

	<button href="#bottom">Go to End</button>
	<br>
	<br>

	<%
		for(Debate d : debates){
	%>
	<form action="/admin/debates" method="POST">
		<input type="hidden" name="action" value="update-debate" />
		<input type="hidden" name="id" value="<%=d.getId()%>" />
		<table>

			<tr>
				<td>Title</td>
				<td><input type="text" name="title" value="<%=d.getTitle()%>" /></td>
			</tr>
			<tr>
				<td>Resolution</td>
				<td><input type="text" name="location"
					value="<%=d.getResolution()%>" /></td>
			</tr>
		</table>
		<input type="submit" />
	</form>

	<br>

	<%
		}
	%>

	<br>
	<h3>New debate</h3>
	<form action="/admin/debates" method="POST">
		<input type="hidden" name="action" value="update-debate" />
		<input type="hidden" name="id" value="-1" />
		<table>
			
			<tr>
				<td>Title</td>
				<td><input type="text" name="title" /></td>
			</tr>
			<br>
			<tr>
				<td>Location</td>
				<td><input type="text" name="location" /></td>
			</tr>
			<br>
			<tr>
				<td>Date</td>
				<td><input name="date" class="date" /></td>
			</tr>
		</table>
		<input type="submit" />
	</form>

	<script type="text/javascript">
		$("button[href='#bottom']").click(function() {
			$("html, body").animate({
				scrollTop : $(document).height()
			}, "slow");
			return false;
		});
	</script>

</body>
</html>