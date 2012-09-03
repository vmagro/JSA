<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.jsa.socal.mobile.Debate"%>
<%@ page import="org.jsa.socal.mobile.Debate.Comment"%>
<%@ page import="com.google.appengine.api.users.User"%>
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
		User user = (User) request.getAttribute("user");
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
			<a href="#new-comment"
				data-role="button" data-rel="dialog">Leave a comment</a> <br>
			<ul data-role="listview" id="comments">
				<%
					ArrayList<Comment> comments = debate.getComments();
					for(Comment c : comments){
				%>
				<li cid=<%=c.getId() %>" class="comment">
					<h5><%=c.getAuthor() %></h5>
					<p><%=c.getDateString() %></p> <br>
					<p><%=c.getText() %></p>
					<%if(request.getAttribute("admin") != null && (Boolean) request.getAttribute("admin")){%>
						<a class="deleteComment" cid=<%=c.getId() %> data-role="button">Delete</a>
					<%}%>
				</li>
				<%} %>
			</ul>
		</div>
		
		<script type="text/javascript">
			$(".deleteComment").click(function(){
				var cid = $(this).attr("cid");
				$.post(  
			            "/debate",  
			            {action: "delete-comment", id: <%=debate.getId()%>, comment: cid},
			            function(responseText){  
			                $("li[cid="+cid+"]").remove();
			            }
			        );  
			});
		</script>
	</div>
	
	<!-- new comment form -->
	<div data-role="page" data-url="new-comment">
		<div data-role="header">
			<h1>New Comment</h1>
		</div>
		
		<div data-role="content">
			<p>
				Your name (<%=user.getNickname()%>) will be recorded along with your
				comment. Please keep your comments appropriate. Thank you for your
				participation.
			</p>
			<p>Note: your comment will appear after a page refresh</p>

			<div>
				<textarea name="text" cols="40" rows="20"></textarea>
				<div id="submitcomment" data-role="button">Submit</div>
			</div>
			
			<script>
				$(document).ready(function(){
					$("#submitcomment").unbind("click");
					$("#submitcomment").click(function(){
						console.log("clicked");
						var ftext = $("textarea[name='text']").val();
						var fauthor = "<%=user.getNickname()%>";
						$.post(  
					    	"/debate",
					        {id: <%=debate.getId()%>, action : "add-comment", text: ftext, author: fauthor},
					        function(responseText){  
					        	$('.ui-dialog').dialog('close');
					        }
					    );
					    return false;
					})
				});
			</script>
		</div>
	</div>

</body>

</html>