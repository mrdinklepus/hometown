<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="emess" style="padding-bottom:15px;">
<%	String err = request.getAttribute("error").toString();
	if (err.equals("suc")){ 
%>
	<div class="transferSuccessful">
		<p>Thank You.  Your Username and Password have been changed.</p>
		<input type="button" value="OK" onclick="showViewAccounts();" />
	</div>
	<%}	else{
		if (err.startsWith("*")){ 
	%>
	<div class="transferSuccessful">
		<p>&nbsp;<%=err%></p>
	</div>
	<%} %>
</div>
<div id="billpay">	
	<div class="spacer"></div>
	<div class="addnew" style="margin-left:200px;">
		<div class="title">Update your online account login info!</div><br/>
		<div class="payeeInfo">					
			<form action="javascript:postForm()">
				New Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="un" maxlength="15""/>&nbsp;(optional) <br/><br/>
				New Password:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="password" name="pw1" maxlength="10""/> <br/><br/>
				Reenter Password:&nbsp;&nbsp; <input type="password" name="pw2" maxlength="10"/> <br/><br/>
				<input name="cmd" type="hidden" value="updateUP"/>
				<input type="button" value="Back to Accounts" onclick="showViewAccounts();"/>
				<input type="button" value="Submit" onclick="postForm()" />
			</form>		
		</div>
	</div>
</div>
<%}%>