<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.PayeeAccount, entityBeans.AccountType, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
Person person = (Person)request.getAttribute("reqObject");
List<String> billpayErrorList = (List)request.getAttribute("billpayErrorList");
if (billpayErrorList == null)
{
  billpayErrorList = new ArrayList<String>();
}
List<Account> billpayAccounts = (List)request.getAttribute("billpayAccounts");
%>
<%@ include file="navigation.html" %>
<header> 
  <h3>Pay Bills</h3>
  <p>Enter one or more payments to send to Payees</p>
</header>
<%@ include file="subnavBillPay.html" %>

<div id="displayMsg">
<%	
	Object err = request.getAttribute("error");
	Object suc = request.getAttribute("success");
	if (err != null)
	{ 
%>
<div class="error">
	<p><%=err.toString()%></p>
</div>
<%
	}
	else if (suc != null)
	{ 
%>
<div class="success">
	<p><%=suc.toString()%></p>
</div>
<%}%>
</div>
<input type="button" class="button" value="Submit Payments" style="float:right;margin-right:40px;" onclick="payBills();"/><br/>
<div id="billpay">
	<div class="titleBar">
		<ul>
			<li>Payee</li>
			<li>&nbsp;&nbsp;Amount</li>
			<li>Account</li>
		</ul>
	</div>
	<form>
<% 
	Iterator<PayeeAccount> i = person.getPayeeAccounts().iterator();
	while (i.hasNext())
	{
		PayeeAccount aPersonPayee = i.next();
		Payee payee = aPersonPayee.getPayeeAccountKey().getPayeeid();
%>		
		<div class="payment">
			<ul>
				<%if (billpayErrorList.contains(payee.getCompany())) { %>
				<li class="payee" style="color:red;text-decoration:underline;"><input type="hidden" name="payeeId" value="<%=payee.getPayeeid()%>" />*<%=payee.getCompany()%></li>
				<%} else { %>
				<li class="payee"><input type="hidden" name="payeeId" value="<%=payee.getPayeeid()%>" /><%=payee.getCompany()%></li>
				<%} %>
				<li><input type="number" step="0.01" style="margin-left:10px;" name="payeeAmt" /></li>
				<li style="padding-right:5px;">
					<select name="accountid">
						<%for (Iterator<Account> it = billpayAccounts.iterator(); it.hasNext();)
						  { 
								Account aAccount = it.next();
								if (aAccount.getAccountType() != AccountType.SECURITY)
								{
						%>						
								<option value="<%=aAccount.getAccountid() %>"><%=aAccount.getType()%><%=aAccount.getAccountNo()%> &nbsp;($<%=aAccount.getBalance()%> )</option>
						<%} }%>
					</select>
				</li>
			</ul>
			<div style="float:right; padding-top:10px; padding-right:73px;">Description:&nbsp;<input name="bDescription" type="text" maxlength="33"/></div>			
		</div>		
	<%} %>
	</form>
	<input type="button" class="button" value="Submit Payments" style="float:left;" onclick="payBills();"/>
</div>
