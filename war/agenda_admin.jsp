<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="org.jsa.socal.mobile.Convention"%>
<%@ page import="org.jsa.socal.mobile.AgendaTopic"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SoCal JSA Admin</title>
<link type="text/css" href="/css/smoothness/jquery-ui-1.8.23.custom.css"
	rel="Stylesheet" />
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.9.2.custom.min.js"></script>

</head>
<body>

	<%
		ArrayList<AgendaTopic> agenda = (ArrayList<AgendaTopic>) request.getAttribute("agenda");
		Convention convention = (Convention) request.getAttribute("convention");
	%>

	<h1>Agenda Admin for <%=convention.getTitle() %></h1>
	<h4><%=convention.getDateString() %></h4>
	<h4><%=convention.getLocation() %></h4>

	<button href="#bottom">Go to End</button>
	<br>
	<br>

	<%
		for(AgendaTopic t : agenda){
	%>
	<form action="/admin/agenda" method="POST">
		<input type="hidden" name="convention" value="<%=convention.getId() %>" />
		<input type="hidden" name="action" value="update-agenda" />
		<input type="hidden" name="id" value="<%=t.getId()%>" />
		<table>
			<tr>
				<td>Title/Block</td>
				<td><input type="text" name="title" value="<%=t.getBlock()%>"/></td>
			</tr>
			<tr>
				<td>Text</td>
				<td>
				<textarea name="text" cols="40" rows="10"><%=t.getText()%></textarea>
				</td>
			</tr>
			<tr>
				<td>Long Text</td>
				<td>
				<textarea name="longtext" cols="40" rows="10"><%=t.getText()%></textarea>
				</td>
			</tr>
			
			<tr>
				<td>Start</td>
				<td><input name="start" type="text" value="<%=t.getStartTime()%>"/></td>
			</tr>
			<tr>
				<td>End</td>
				<td><input name="end" type="text" value="<%=t.getEndTime()%>"/></td>
			</tr>
			<tr>
				<td>Location</td>
				<td><input name="location" type="text" value="<%=t.getLocation()%>"/></td>
			</tr>
			<tr>
				<td>Order</td>
				<td><input name="order" type="text" value="<%=t.getOrder()%>"/></td>
			</tr>
		</table>
		<input type="submit" />
	</form>
	<br>
	
	<button id="deleteButton<%=t.getId()%>">Delete</button>
	<script type="text/javascript">
		$("#deleteButton<%=t.getId()%>").click(function(){
			$.post("/admin/agenda?id=<%=t.getId() %>&action=delete", function(){
				location.reload();
			})
		})
	</script>

	<%
		}
	%>

	<br>
	<h3>New topic</h3>
	<form action="/admin/agenda" method="POST">
		<input type="hidden" name="convention" value="<%=convention.getId() %>" />
		<input type="hidden" name="action" value="update-agenda" />
		<input type="hidden" name="id" value="-1" />
		<table>
			<tr>
				<td>Title/Block</td>
				<td><input type="text" name="title" /></td>
			</tr>
			<tr>
				<td>Text</td>
				<td><textarea name="text" cols="40" rows="10"></textarea></td>
			</tr>
			<tr>
				<td>Long Text</td>
				<td><textarea name="longtext" cols="40" rows="10"></textarea></td>
			</tr>
			
			<tr>
				<td>Start</td>
				<td><input name="start" type="text" /></td>
			</tr>
			<tr>
				<td>End</td>
				<td><input name="end" type="text" /></td>
			</tr>
			<tr>
				<td>Location</td>
				<td><input name="location" type="text" /></td>
			</tr>
			<tr>
				<td>Order</td>
				<td><input name="order" type="text" id="ordernew"/></td>
			</tr>
		</table>
		<input type="submit" />
	</form>

	<script type="text/javascript">
	$("#ordernew").attr("value", $("form").length);
		$("button[href='#bottom']").click(function() {
			$("html, body").animate({
				scrollTop : $(document).height()
			}, "slow");
			return false;
		});
	</script>

</body>
</html>