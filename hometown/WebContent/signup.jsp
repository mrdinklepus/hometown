<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
<link type="text/css" rel="stylesheet" media="screen" href="css/hometown.css"/>
<link type="text/css" rel="stylesheet" media="screen" href="css/navigation.css"/>
<script type="text/javascript" src="javascript/ajax.js"></script>
<script type="text/javascript" src="javascript/htjs.js"></script>
<script type="text/javascript" src="javascript/divslide.js"></script>
</head>

<body>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<input type="button" class="right" style="margin-bottom:15px; margin-top:15px;" value="Back to Homepage" onclick="showLogin();"/><br/>
<div id="billpay">		
	<div class="addnew" style="margin-left:200px;">
		<div class="title">Sign up for an online Account!</div><br/>
		<div class="payeeInfo">					
			<form>
				First Name:&nbsp;<input type="text" name="fn" maxlength="15" style="align:right"/> <br/><br/>
				Last Name:&nbsp;<input type="text" name="ln" maxlength="15"/> <br/><br/>
				Phone Number:&nbsp;<input type="text" name="phone" maxlength="10""/> <br/><br/>
				Create a Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="un" maxlength="15""/> <br/><br/>
				Create a Password:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="password" name="pw" maxlength="10""/> <br/><br/>
				Reenter Password:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="password" name="pw2" maxlength="10"/> <br/><br/>
				<input name="cmd" type="hidden" value="signup"/>
				<input type="button" value="Submit" onclick="postForm()"/>
			</form>	
		</div>
	</div>
</div>
</body>
</html>