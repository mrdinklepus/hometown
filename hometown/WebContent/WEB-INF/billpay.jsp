<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Personpayee, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%
Person person = (Person)request.getAttribute("reqObject");
List list1 = (List)request.getAttribute("errlist1");
List list2 = (LinkedList)request.getAttribute("alist");
%>
<%@ include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="subnav">
	<ul>
		<li id="addPayee" onclick="showManagePayees();">Manage Payees</li>
		<li id="schedulePayment" onclick="showSchedule();">Schedule Payment</li>
	</ul>
</div>
<div id="emess">
<%	
	String a = request.getAttribute("error").toString();
	String cd = request.getAttribute("success").toString();
	if (!a.equals(""))
	{ 
%>
<div class="transferSuccessful">
	<p><%=a%></p>
</div>
<%
		cd = "no";
	}
	if (cd.equals("suc")){ 
%>
<div class="transferSuccessful">
	<p>&nbsp;Your payments have been made.  You may make other payments.</p>
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
	Iterator i = person.getPersonpayeeCollection().iterator();
	while(i.hasNext()){
		Personpayee aPersonPayee = (Personpayee)i.next();
		Payee payee = aPersonPayee.getPayeeid();
%>		
		<div class="payment">
			<ul>
				<%if (list1.contains(payee.getCompany())){ %>
				<li class="payee" style="color:red;text-decoration:underline;"><input type="hidden" name="payeeId" value="<%=payee.getPayeeid()%>" />*<%=payee.getCompany()%></li>
				<%}else{ %>
				<li class="payee"><input type="hidden" name="payeeId" value="<%=payee.getPayeeid()%>" /><%=payee.getCompany()%></li>
				<%} %>
				<li><input type="text" style="margin-left:10px;" name="payeeAmt" /></li>
				<li style="padding-right:5px;">
					<select name="accountid">
						<% for (Iterator b = list2.iterator(); b.hasNext();){ 
								Account aAccount = (Account)b.next();
								if (!aAccount.getAccounttype().equals("U")){ //|| aAccount.getAccounttype().equals("S") || aAccount.getAccounttype().equals("R")){ 
						%>						
								<option value="<%=aAccount.getAccountid() %>"><%=aAccount.getType()%><%=aAccount.getAccountno()%> &nbsp;($<%=aAccount.getBalance()%> )</option>
						<%}} %>
					</select>
				</li>
			</ul>
			<div style="float:right; padding-top:10px; padding-right:73px;">Description:&nbsp;<input name="bDescription" type="text" maxlength="33"/></div>			
		</div>		
	<%} %>
	</form>
	<input type="button" class="button" value="Submit Payments" style="float:left;" onclick="payBills();"/>
</div>
