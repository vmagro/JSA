<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.jsa.socal.mobile.AgendaTopic"%>
<%@ page import="org.jsa.socal.mobile.AgendaTopic.Comment"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=request.getAttribute("title") %></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" href="/images/iphone_icon.png" />
<link rel="apple-touch-startup-image" href="/images/iphone_splash.png" />
<link rel="stylesheet" href="/css/jquery.mobile-1.1.1.min.css" />
<script type="text/javascript" src="/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="/js/jquery.mobile-1.1.1.min.js"></script>
<link rel="stylesheet"
	href="css/redwhiteandblue/red-white-and-blue.min.css" />

<script type="text/javascript" src="/js/android.js"></script>


<script type="text/javascript">
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '434281589955303', // App ID
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true  // parse XFBML
    });

    // Additional initialization code here
  };

  // Load the SDK Asynchronously
  (function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/en_US/all.js";
     ref.parentNode.insertBefore(js, ref);
   }(document));
</script>


</head>
<body>

	<%
		AgendaTopic topic = (AgendaTopic) request.getAttribute("topic");
	%>

	<div data-role="page">

		<div data-role="header" id="header">
			<h1><%=topic.getBlock()%></h1>
			<div data-role="navbar">
				<ul data-role="navbar" id="menuList">
					<li><a href="/" class="contentLink">Home</a></li>
					<li><a href="/conventions" class="contentLink">Conferences</a></li>
					<li><a href="/links" class="contentLink">Links</a></li>
				</ul>
			</div>
		</div>

		<div data-role="content">
			<h2>Details</h2>
			<h3><%=topic.getStartTime() %>-<%=topic.getEndTime() %></h3>
			<h4><%=topic.getLocation() %></h4>
			<p><%=topic.getText()%></p>
			
			<h3>Comments</h3>
			<a href="#new-comment" data-role="button" data-rel="dialog">Leave
				a comment</a> <br>
			<ul data-role="listview" id="comments">
				<%
					ArrayList<Comment> comments = topic.getComments();
					for(Comment c : comments){
				%>
				<li cid=<%=c.getId() %> " class="comment">
					<h5><%=c.getAuthor() %></h5>
					<p><%=c.getDateString() %></p> <br>
					<p style="white-space: normal"><%=c.getText() %></p> <%if(request.getAttribute("admin") != null && (Boolean) request.getAttribute("admin")){%>
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
			            "/agenda",  
			            {action: "delete-comment", id: <%=topic.getId()%>, comment: cid},
			            function(responseText){  
			                $("li[cid="+cid+"]").remove();
			            }
			        );  
			});
		</script>

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

	<!-- new comment form -->
	<div data-role="page" data-url="new-comment">
		<div data-role="header">
			<h1>New Comment</h1>
		</div>

		<div data-role="content">

			<script>
				var name = "";
				FB.login(function(response) {
				   if (response.authResponse) {
				   	FB.api('/me', function(response) {
				  		name = response.name;
						console.log('Good to see you, ' + response.name + '.');
						$("#name").text(response.name);
				       	$("#mustlogin").hide();
				       	$("#mustlogin").remove();
				       	$("#commentform").show();
				     });
				   } else {
				     console.log('User cancelled login or did not fully authorize.');
				   }
				   });
			</script>

			<p id="mustlogin">You must login with Facebook to submit a
				comment</p>


			<div id="commentform" style="display: none">
				<p>
					Your name (<span id="name"></span>) will be recorded along with
					your comment. Please keep your comments appropriate. Thank you for
					your participation.
				</p>

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
						console.log(name);
						var fauthor = name;
						$.post(  
					    	"/agenda",
					        {id: <%=topic.getId()%>, action : "add-comment", text: ftext, author: fauthor},
					        function(responseText){  
					        	$('.ui-dialog').dialog('close');
					        	window.location.href="<%=request.getAttribute("reqUri")%>";
					        }
					    );
					    return false;
					})
				});
			</script>
			</div>
		</div>
	</div>

</body>

</html>