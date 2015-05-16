<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Personpayee, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%Person person = (Person)request.getAttribute("reqObject"); %>
<%List list1 = (LinkedList)request.getAttribute("alist"); 
  String toac = request.getAttribute("to").toString();
  String fromac = request.getAttribute("from").toString();
  String amnt = request.getAttribute("amt").toString();
%>
<%@ include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="emess">
<%	String err = request.getAttribute("error").toString();
	if (err.equals("suc")){ 
%>
<div class="transferSuccessful">
	<p>Thank You.  Your Transfer has been made.  You may make another transfer.</p>
	<input type="button" value="Back to Transfer" onclick="showTransfer();" />
</div>
<%}	else{
	if (err.startsWith("*")){ 
%>
<div class="transferSuccessful">
	<p>&nbsp;<%=err%></p>
</div>
<%}%>
</div>
<div id="header"> 
	<ul>
		<li class="large">Transfer Funds</li>
		<li class="small">Select accounts and enter an amount to transfer</li>
	</ul>	
</div>
	
<div id="transFunds">
	<div class="titlebar">
		<ul>
			<li>Transfer From Account:</li>
			<li>Transfer To Account:</li>
			<li>&nbsp;&nbsp;Enter amount to transfer:</li>
		</ul>
	</div>

<form action="javascript:postForm()">	
	<div class="accounts">
		<ul>
			<li><select name="from">
			<%for(Iterator it = list1.iterator(); it.hasNext();) {
				Account aAccount = (Account)it.next();
				String temp = aAccount.getAccountid() + "~~fromAccount=" + aAccount.getAccountno();
			%>
			<option value="<%=temp%>" <%if(fromac.equals(Integer.toString(aAccount.getAccountid()))){%>SELECTED<%} %>><%=aAccount.getType()%><%=aAccount.getAccountno()%> &nbsp;($<%=aAccount.getBalance()%> )</option>			
			<%} %>
			</select></li>
			
			<li><select name="to">
			<%for(Iterator it = list1.iterator(); it.hasNext();) {
				Account aAccount = (Account)it.next();
				String temp = aAccount.getAccountid() + "~~toAccount=" + aAccount.getAccountno();
			%>
			<option value="<%=temp%>" <%if(toac.equals(Integer.toString(aAccount.getAccountid()))){%>SELECTED<%} %>><%=aAccount.getType()%><%=aAccount.getAccountno()%> &nbsp;($<%=aAccount.getBalance()%> )</option>
			<%} %>
			</select></li>
			
			<li>$ <input id="foc" name="amt" type="text" <%if(!amnt.equals("")){%>value="<%=amnt%>"<%} %>/></li>
			<input name="cmd" type="hidden" value="confirmTransfer"/>
		</ul>	
	</div>
<br />
	<div class="submit" style="padding-top:5px;">
		<input type="submit" value="Make Transfer"/>
	</div>
</form>	
</div>
<%}%>