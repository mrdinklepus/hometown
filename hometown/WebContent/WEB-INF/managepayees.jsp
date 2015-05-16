<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Personpayee, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<% Person person = (Person)request.getAttribute("reqObject");%>
<%@include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="subnav">
	<ul>
		<li id="addPayee" onclick="showManagePayees();" style="background-color:#BED8F1">Manage Payees</li>
		<li id="schedulePayment" onclick="showSchedule();">Schedule Payment</li>
	</ul>
</div>
<br/>
<div id="billpay">
	<div class="current">
		<div class="title"><u>Current Payees</u> &nbsp;&nbsp;&nbsp;&nbsp;(Hover over Payee name for more info)</div>
<%
			for (Iterator i = person.getPersonpayeeCollection().iterator(); i.hasNext();){ 
			Personpayee aPersonPayee = (Personpayee)i.next();
			Payee payee = aPersonPayee.getPayeeid();
			String remove = payee.getPayeeid() + "&payeeCompany=" + payee.getCompany();
			String tt = payee.getAddressid().getStreet1() + "&nbsp;&nbsp;  " + payee.getAddressid().getCity() + ", " + payee.getAddressid().getState() + " " + payee.getAddressid().getZipcode();
%>		
			<div id="<%=payee.getCompany()%>" class="currentPayee">				
				<div class="payeeName"><a title="<%=tt%>"><%=payee.getCompany()%></a></div>
				<div class="buttons">
					<span class="edit" onclick="editPayee(<%=payee.getPayeeid()%>)">EDIT</span>
					<span class="remove" onclick="confirmRemovePayee('<%=remove%>')">REMOVE</span>
				</div>
			</div>
		<%} %>			
	</div>
	<div class="spacer"></div>	
	<div class="addnew">
		<div id="emess">
<%	
		String err = request.getAttribute("error").toString();
		if (err.compareTo("suc") == 0){ 
%>
		<div class="transferSuccessful">
			<p>Thank You.  Your Payee has been added.</p>
			<input type="button" value="OK" onclick="showManagePayees();" />
		</div>
<%	
		}else{
		if (err.startsWith("*")){
%>	
		<div class="transferSuccessful">
			<p><%=err%></p>
		</div>
	<%	} %>
		</div>	
		<div class="title"><u>New Payee Information</u></div>
		<div class="payeeInfo">					
			<form action="javascript:postForm()">
				Company Name:&nbsp;&nbsp; <input type="text" name="coname" maxlength="33"/> <br />
				Street Address:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="street" maxlength="28"/> <br />
				City:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="city" maxlength="20"/>
				State: <input name="state" type="text" size="4" onKeyDown="limitText(this.form.state,2);" 
					onKeyUp="limitText(this.form.state,2);" maxlength="2"> &nbsp;(ex. ID)<br>				
				Zip:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="zip" size="5" maxlength="5"/> <br />
				Phone:&nbsp; <input type="text" name="phone" maxlength="10"/> &nbsp; (optional)<br />
				Account Number:&nbsp; <input type="text" name="accnum" maxlength="30"/> <br />	
				<input type="hidden" name="cmd" value="addPayee" /> 	
				<input style="position:relative; left:220px; margin-top:10px; margin-bottom:10px;" type="submit" value="add"/>
			</form>		
		</div>
	</div>
</div>
<%} %>	