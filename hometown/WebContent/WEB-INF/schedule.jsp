<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.PayeeAccount, entityBeans.AccountType, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entityBeans.Payments"%>

<%Person person = (Person)request.getAttribute("reqObject");%>
<%Collection<Account> accountList = (Collection)request.getAttribute("accountList");%>
<%Collection<Payments> paymentList = (Collection)request.getAttribute("paymentList");%>

<%@include file="navigation.html" %>
<header> 
  <h3>Schedule Payments</h3>
  <p>Schedule a future payment, or cancel a pending payment</p>
</header>
<%@include file="subnavBillPay.html" %>

<div id="displayMsg">
<%Object err = request.getAttribute("error");
	Object suc = request.getAttribute("success");
	if (err != null)
  {
%>
  <div class="error">
    <p><%=err.toString()%></p>
  </div>
<%
  }
	
	if (suc != null)
	{
%>
  <div class="success">
    <p><%=suc.toString()%></p>
  </div>
<%
	}
%>
</div>

<div id="billpay">
	<div class="schedulepayments" style="position:relative; float:left; width: 350px; height:auto; ">
	<form action="javascript:postForm()">
		<div class="title">Schedule Payment</div>
		<div class="currentPayee">
			Select Payee:
			<select name="payeeid" style="margin-left:29px; width:150px;">
				<%for (Iterator<PayeeAccount> it = person.getPayeeAccounts().iterator(); it.hasNext();)
				  {
					  PayeeAccount aPersonPayee = (PayeeAccount)it.next();
					  Payee payee = aPersonPayee.getPayeeAccountKey().getPayeeid();
				%>
					<option value="<%=payee.getPayeeid()%>" ><%=payee.getCompany()%></option>
				<%} %>
			</select>
		</div>
		<div class="currentPayee">			
			Payment Account:
			<select name="accountid">
				<% for (Iterator<Account> it = accountList.iterator(); it.hasNext();){ 
						Account aAccount = it.next();
					if (aAccount.getAccountType() != AccountType.SECURITY) { 
				%>						
					<option value="<%=aAccount.getAccountid() %>"><%=aAccount.getType()%><%=aAccount.getAccountNo()%> ($<%=aAccount.getBalance()%> )</option>
				<%}} %>
			</select>
		</div>
		<div class="currentPayee"> Amount:	<input type="number" step="0.01" required name="amt" style="margin-left:59px;"/> </div>
		<div class="currentPayee"> Date to Transfer:<input type="date" name="date" style="margin-left:12px;" maxlength="11"/><br/>(ex. 22-Jan-2008) </div>
		<input type="hidden" name="cmd" value="schedulePayment"/>
		<input style="position:relative; left:190px; margin-top:20px;" type="submit" value="schedule"/>
	</form>	
	</div>
	<div id="scheduledpayments" style="margin-top:25px; margin-bottom:20px;">
		<div class="header" style="text-align:center">Scheduled Payments</div>
		<div class="scheduled">
			<table>
				<tr>
					<th>Payee</th>
					<th>Amount</th>
					<th>Date</th>
					<th></th>
				</tr>
			<%
			if (paymentList.isEmpty()){
			%>
				<tr><td colspan="4" style="text-align:center;">No Payments Scheduled</td></tr>
			<%}
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
				for (Iterator<Payments> it = paymentList.iterator(); it.hasNext();)
				{
					Payments payment = (Payments)it.next();
					Payee payee = payment.getPayeeid();				
			%>
				<tr>
					<td><%=payee.getCompany() %></td>
					<td>$<%=payment.getAmount() %></td>
					<td><%=df.format(payment.getPaydate())%></td>
					<td><input type="button" value="cancel" onclick="removePayment(<%=payment.getPaymentid() %>);" /></td>
				</tr>
			<%}%>
			</table>
		</div>
	</div>
</div>