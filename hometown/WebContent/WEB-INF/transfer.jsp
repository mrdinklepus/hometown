<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Account"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<% 
  Person person = (Person)request.getAttribute("reqObject");
  Collection<Account> accountList = (Collection)request.getAttribute("accountCollection");
  String fromAccountId = request.getAttribute("from") != null ? request.getAttribute("from").toString() : "";
  String toAccountId = request.getAttribute("to") != null ? request.getAttribute("to").toString() : "";
  String amt = request.getAttribute("amt") != null ? request.getAttribute("amt").toString() : "";
%>
<%@ include file="navigation.html" %>
<div id="displayMsg">
<%  
  Object err = request.getAttribute("error");
  Object suc = request.getAttribute("success");
  if (suc != null)
  { 
%>
	<div class="success">
	  <p><%=suc.toString()%></p>
	  <input type="button" value="Back to Transfer" onclick="showTransfer();" />
	</div>
<%
  }
  else
  {
    if (err != null)
    {
%>
	<div class="error">
	  <p><%=err.toString()%></p>
	</div>
  <%}%>
</div>
<header> 
	<h3>Transfer Funds</h3>
  <p>Select accounts and enter an amount to transfer</p>
</header>
	
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
				<li>
				  <select name="from">
	        <%for (Iterator<Account> it = accountList.iterator(); it.hasNext();)
	          {
					    Account aAccount = it.next();
					    String temp = aAccount.getAccountid() + "~~fromAccount=" + aAccount.getAccountNo();%>
				    <option value="<%=temp%>" <%if (fromAccountId.equals(Integer.toString(aAccount.getAccountid()))) {%>SELECTED<%} %>><%=aAccount.getAccountType()%><%=aAccount.getAccountNo()%> &nbsp;($<%=aAccount.getBalance()%> )</option>			
	        <%}%>
				  </select>
				</li>
				<li>
				  <select name="to">
				  <%for (Iterator<Account> it = accountList.iterator(); it.hasNext();)
				    {
					    Account aAccount = it.next();
					    String temp = aAccount.getAccountid() + "~~toAccount=" + aAccount.getAccountNo();%>
				    <option value="<%=temp%>" <%if (toAccountId.equals(Integer.toString(aAccount.getAccountid()))) {%>SELECTED<%} %>><%=aAccount.getAccountType()%><%=aAccount.getAccountNo()%> &nbsp;($<%=aAccount.getBalance()%> )</option>
				  <%}%>
				  </select>
				</li>
				<li>$ <input id="foc" name="amt" type="number" step="0.01" required <%if(!amt.equals("")) {%>value="<%=amt%>"<%}%>/></li>
			</ul>
			<input name="cmd" type="hidden" value="confirmTransfer"/>
		</div>
	  <br />
		<div class="submit">
			<input type="submit" value="Make Transfer"/>
		</div>
	</form>	
</div>
<%}%>