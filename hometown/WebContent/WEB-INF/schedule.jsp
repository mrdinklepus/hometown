<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Personpayee, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="entityBeans.Payments"%>

<%	Person person = (Person)request.getAttribute("reqObject");%>
<%List list1 = (LinkedList)request.getAttribute("alist");%>
<%List list2 = (LinkedList)request.getAttribute("dlist");%>

<%@include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="subnav">
	<ul>
		<li id="addPayee" onclick="showManagePayees();">Manage Payees</li>
		<li id="schedulePayment" onclick="showSchedule();" style="background-color:#BED8F1">Schedule Payment</li>
	</ul>
</div>
<div id="emess" style="padding-bottom:15px;">
<%	String err = request.getAttribute("error").toString();
	String remsuc = request.getAttribute("remsuc").toString();
	if (err.equals("suc")){ 
%>
	<div class="transferSuccessful">
		<p>Thank You.  Your payment has been scheduled.</p>
		<input type="button" value="OK" onclick="showSchedule();" />
	</div>
	<%}	else{
		if (err.startsWith("*")){ 
	%>
	<div class="transferSuccessful">
		<p>&nbsp;<%=err%></p>
	</div>
	<%} if (err.equals("remsuc")){%>
	<div class="transferSuccessful">
		<p>Thank You.  Your payment to <%=remsuc%> has been removed.</p>
	</div>
	<%}%>
</div>
<div id="billpay">
	<div class="schedulepayments" style="position:relative; float:left; width: 350px; height:auto; ">
	<form action="javascript:postForm()">
		<div class="title">Schedule Payment</div>
		<div class="currentPayee">
			Select Payee:
			<select name="payeeid" style="margin-left:29px; width:150px;">
				<%for (Iterator i = person.getPersonpayeeCollection().iterator(); i.hasNext();){ 
					Personpayee aPersonPayee = (Personpayee)i.next();
					Payee payee = aPersonPayee.getPayeeid();
				%>
					<option value="<%=payee.getPayeeid()%>" ><%=payee.getCompany()%></option>
				<%} %>
			</select>
		</div>
		<div class="currentPayee">			
			Payment Account:
			<select name="accountid">
				<% for (Iterator b = list1.iterator(); b.hasNext();){ 
						Account aAccount = (Account)b.next();
					if (!aAccount.getAccounttype().equals("U")){ //|| aAccount.getAccounttype().equals("S") || aAccount.getAccounttype().equals("R")){ 
				%>						
					<option value="<%=aAccount.getAccountid() %>"><%=aAccount.getType()%><%=aAccount.getAccountno()%> ($<%=aAccount.getBalance()%> )</option>
				<%}} %>
			</select>
		</div>
		<div class="currentPayee"> Amount:	<input type="text" name="amt" style="margin-left:59px;"/> </div>
		<div class="currentPayee"> Date to Transfer:<input type="text" name="date" style="margin-left:12px;" maxlength="11"/><br/>(ex. 22-Jan-2008) </div>
		<input type="hidden" name="cmd" value="schedulePayment"/>
		<input style="position:relative; left:190px; margin-top:20px;" type="submit" value="schedule"/>
	</form>	
	</div>
	<div id="scheduledpayments" style="margin-top:25px; margin-bottom:20px;">
		<div class="header" style="text-align:center">Scheduled Payments</div>
		<div class="scheduled">
			<table width="100%" border="0">
				<tr>
					<th>Payee</th>
					<th>Amount</th>
					<th>Date</th>
					<th></th>
				</tr>
			<%
			if (list2.size() == 0){
			%>
				<tr><td colspan="4" style="text-align:center;">No Payments Scheduled</td></tr>
			<%}				
				for (Iterator c = list2.iterator(); c.hasNext();){
					Payments payment = (Payments)c.next();
					Payee payee = payment.getPayeeid();				
			%>
				<tr>
					<td><%=payee.getCompany() %></td>
					<td>$<%=payment.getAmount() %></td>
					<td><%=payment.getPaydate().split("\\s")[0] %></td>
					<td><input type="button" value="cancel" onclick="removePayment(<%=payment.getPaymentid() %>);" /></td>
				</tr>
			<%}%>
			</table>
		</div>
	</div>
</div>
<%}%>