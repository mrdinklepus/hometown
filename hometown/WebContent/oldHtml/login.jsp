<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body onload="javascript:uname.focus();">
<div id="loginbar">
	<form name="login">
		username:<input type="text" name="uname" width="30px" tabindex=1/> <br/>
		password:<input type="password" name="pass" width="30px" tabindex=2/> <br/>
		<input type="hidden" name="cmd" value="login"/>
		<input type="button" class="right" value="Login" onclick="postForm()"/> <br/><br/>
	</form>
	<div id="error"></div>
	<!--Don't have an account?  <br/>Click here:<input type="button" class="right" value="Sign Up" onclick="showSignup();"/>-->	
</div>
<div id="main">
Tzril wisi elit consequat exerci wisi ut suscipit accumsan vel feugait at, duis, vel et, molestie nisl minim nonummy eum blandit. Esse augue luptatum dolor in wisi et lorem in, odio illum ea. Ad molestie sit zzril iusto feugait duis vulputate vulputate, et minim amet velit praesent dolore autem qui dolore elit dolore blandit consequat ex molestie nonummy. 
Accumsan nostrud, in tation esse, volutpat, ut ullamcorper nulla vulputate eu delenit eum duis tincidunt tincidunt nostrud suscipit vulputate nisl, illum at minim, ut consequat. Qui at vel hendrerit iriure, magna illum, suscipit zzril, consequat ea nulla, accumsan nostrud ea. 		
</div>	 

</body>
</html>