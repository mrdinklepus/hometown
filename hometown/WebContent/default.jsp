<!-- <?xml version="1.0" encoding="ISO-8859-1" ?> -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html> <!-- xmlns="http://www.w3.org/1999/xhtml"> -->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Home Town Bank</title>
	<link type="text/css" rel="stylesheet" media="screen" href="css/hometown.css"/>
	<link type="text/css" rel="stylesheet" media="screen" href="css/navigation.css"/>
	<link type="text/css" rel="stylesheet" media="screen" href="css/billpay.css"/>
	<script type="text/javascript" src="javascript/ajax.js"></script>
	<script type="text/javascript" src="javascript/htjs.js"></script>
	<script type="text/javascript" src="javascript/divslide.js"></script>
</head>

<body onload="init();">
	<div id="big" style="padding:10px;">
		<div id="wrapper" style="margin-left:auto; margin-right:auto;">
			<div id="title">
			  <div style="float:right; width:220px;">
			    <div id="updateUP" onclick="showUpdateUP();">Update User/Pass</div>
			    <div id="logout" onclick="logout();">Logout</div>
			  </div>
			</div>
			<%@ include file="waiting.html" %>
			<div id="content" style="height:auto;">
				<%@ include file="login.html" %>
			</div>
			<div id="footer" style="clear:both">
				Home Town Bank <br/>
				Copyright &copy; <script type="text/javascript">document.write(new Date().getFullYear());</script> <br/>
				<a href="#">Privacy Policy</a>	
			</div>
		</div>
	</div>
</body>
</html>