<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
//	String fn = request.getAttribute("fn").toString();
//	String ln = request.getAttribute("ln").toString();
//	String ph = request.getAttribute("ph").toString();
//	String un = request.getAttribute("fn").toString();
//	String pw = request.getParameter("fn").toString();
%>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="emess">
<% 	
	String valid = request.getAttribute("validate").toString();
	if (valid.equals("true")){ 
%>
	<div class="transferSuccessful">
		<p>Thank You.  Your Online User Account has been created.</p>
		<input type="button" value="Login now" onclick="showLogin();" />
	</div>
<%}else{ %>
		<div class="transferSuccessful">
		<p><%=request.getAttribute("error").toString()%></p>
	</div>
</div>
<input type="button" class="right" style="margin-bottom:15px; margin-top:15px;" value="Back to Homepage" onclick="showLogin();"/><br/>
<div id="billpay">		
	<div class="addnew" style="margin-left:200px;">
		<div class="title">Sign up for an online Account!</div><br/>
		<div class="payeeInfo">					
			<form action="javascript:postForm()">
				First Name:&nbsp;<input type="text" name="fn" maxlength="15" style="align:right" value="<%=request.getAttribute("fn").toString()%>"/> <br/><br/>
				Last Name:&nbsp;<input type="text" name="ln" maxlength="15" value="<%=request.getAttribute("ln").toString()%>"/> <br/><br/>
				Phone Number:&nbsp;<input type="text" name="phone" maxlength="10"" value="<%=request.getAttribute("phone").toString()%>"/> <br/><br/>
				Create a Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="un" maxlength="15"" value="<%=request.getAttribute("un").toString()%>"/> <br/><br/>
				Create a Password:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="password" name="pw" maxlength="10"/> <br/><br/>
				Reenter Password:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="password" name="pw2" maxlength="10"/> <br/><br/>
				<input name="cmd" type="hidden" value="signup"/>
				<input type="submit" value="Submit"/>	
			</form>	
		</div>
	</div>
</div>
<%} %>
