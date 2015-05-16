<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Address, entityBeans.Phone, entityBeans.Personpayee, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<% //Person person = (Person)request.getAttribute("reqObject");%>
<% Payee payee = (Payee)request.getAttribute("reqObject");%>
<% Address add = (Address)request.getAttribute("address");%>
<% Phone ph = (Phone)request.getAttribute("phone");%>
<% Personpayee pp = (Personpayee)request.getAttribute("pp");%>
<%@include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<br/>
<input type="button" class="right" style="margin-bottom:35px;" value="Back to Manage Payees" onclick="showManagePayees();"/>
<div id="emess">
<%	String err = request.getAttribute("error").toString();
	if (err.equals("suc")){ 
%>
<div class="transferSuccessful">
	<p>Payee information has been successfully updated.</p>
<input type="button" value="Back to Payee" onclick="editPayee(<%=payee.getPayeeid()%>);" />
</div>
<%}	else{
	if (err.startsWith("*")){ 
%>
<div class="transferSuccessful">
	<p>&nbsp;<%=err%></p>
</div>
<%}%>
</div>
<div id="billpay">	
	<div class="spacer"></div>
	<div class="addnew" style="margin-left:200px;">
		<div class="title">Information for <%=payee.getCompany()%></div><br/>
		<div class="payeeInfo">					
			<form action="javascript:postForm()">
				Company Name:&nbsp;&nbsp; <input type="text" name="coname" value="<%=payee.getCompany()%>" maxlength="33"/> <br />
				Street Address:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="street" value="<%=add.getStreet1()%>" maxlength="28"/> <br />
				City:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="city" value="<%=add.getCity()%>" maxlength="20"/>
				State: <input name="state" value="<%=add.getState()%>" type="text" size="4" onKeyDown="limitText(this.form.state,2);" 
					onKeyUp="limitText(this.form.state,2);" maxlength="2"> &nbsp;(ex. ID)<br>				
				Zip:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="zip" value="<%=add.getZipcode()%>"size="5" maxlength="5"/> <br />
				Phone:&nbsp; <input id="test" type="text" name="phone" value="<%if (ph != null){%><%=ph.getPhone()%><%}%>" maxlength="10"/> &nbsp; (optional)<br />
				Account Number:&nbsp; <input type="text" name="accnum" value="<%=pp.getPayeeaccountno()%>" maxlength="30"/> <br />	
				<input type="hidden" name="cmd" value="editPayee" />
				<input type="hidden" name="payeeid" value="<%=payee.getPayeeid()%>" />
				<input type="hidden" name="addressid" value="<%=add.getAddressid()%>" /> 	
				<input type="hidden" name="phoneid" value="<%if (ph != null){%><%=ph.getPhoneid()%><%}else{%>0<%}%>" /> 	
				<input type="hidden" name="ppid" value="<%=pp.getPersonpayeeid()%>" /> 	 	
				<input style="position:relative; margin-top:20px; left:220px;" type="submit" value="UPDATE"/>
			</form>		
		</div>
	</div>
</div>
<%} %>	