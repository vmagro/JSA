<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="org.jsa.socal.mobile.Convention"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SoCal JSA Admin</title>
<link type="text/css" href="/css/smoothness/jquery-ui-1.9.2.custom.css"
	rel="Stylesheet" />
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.9.2.custom.min.js"></script>
</head>
<body>

	<%
		ArrayList<Convention> conventions = (ArrayList<Convention>) request.getAttribute("conventions");
	%>

	<h1>Convention Admin</h1>

	<button href="#bottom">Go to End</button>
	<br>
	<br>

	<%
		for(Convention c : conventions){
	%>
	<form action="/admin/conventions" method="POST" id="form<%=c.getId()%>">
		<input type="hidden" name="action" value="update-convention" />
		<input type="hidden" name="id" value="<%=c.getId()%>" />
		<table>
			<tr>
				<td>Title</td>
				<td><input type="text" name="title" value="<%=c.getTitle()%>" /></td>
			</tr>
			<tr>
				<td>Location</td>
				<td><input type="text" name="location"
					value="<%=c.getLocation()%>" /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input name="date" class="date"
					value="<%=c.getDateString()%>" /></td>
			</tr>
		</table>
		<input type="submit"/>
		
	</form>
	
	<a href="/admin/agenda?convention=<%=c.getId()%>" data-role="button">Agenda Admin</a>
		
	<button id="deleteButton<%=c.getId()%>">Delete</button>
	<script type="text/javascript">
		$("#form<%=c.getId()%>").ajaxForm
		$("#deleteButton<%=c.getId()%>").click(function(){
			$.post("/admin/conventions?id=<%=c.getId() %>&action=delete", function(){
				location.reload();
			})
		})
	</script>
	<br>
	<br>

	<%
		}
	%>

	<br>
	<h3>New convention</h3>
	<form action="/admin/conventions" method="POST">
		<input type="hidden" name="action" value="update-convention" />
		<input type="hidden" name="id" value="-1" />
		<table>
			<tr>
				<td>Title</td>
				<td><input type="text" name="title" /></td>
			</tr>
			<tr>
				<td>Location</td>
				<td><input type="text" name="location" /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input name="date" class="date" /></td>
			</tr>
		</table>
		<input type="submit" />
	</form>

	<script type="text/javascript">
		$(".date").datepicker();
		$("button[href='#bottom']").click(function() {
			$("html, body").animate({
				scrollTop : $(document).height()
			}, "slow");
			return false;
		});
	</script>

</body>
</html>