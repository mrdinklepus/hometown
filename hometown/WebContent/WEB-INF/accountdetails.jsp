<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.BankTransaction, entityBeans.Account"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@ include file="navigation.html" %>
<%
  int MAX_TRANSACTIONS_DISPLAYED = 20;
  TreeSet<BankTransaction> list = (TreeSet)request.getAttribute("list");
  Account aAccount = (Account)request.getAttribute("reqObject");
%>
<br>
<div><input type="button" class="left" value="Back to Accounts" onclick="showViewAccounts()"/></div>
<header> 
	<h1>Details for Account <%=aAccount.getAccountNo()%></h1>
</header>
<div id="details">
	<div class="header">
		<ul>
			<li>Date</li>
			<li style="width:40%">Description</li>
			<li style="text-align:right">Amount</li>
			<li style="text-align:right;">Balance&nbsp;</li>
		</ul>
	</div>
	 
	<% int count = 0;
	   Iterator<BankTransaction> it = list.descendingIterator();
	   while (it.hasNext())
	   {
				if (count >= MAX_TRANSACTIONS_DISPLAYED)
				{
					break;
				}
				
				BankTransaction transaction = it.next();
				String date = transaction.getTimemade();
				Account toAccount = transaction.getToAccount();
				Account fromAccount = transaction.getFromAccount();
				BigDecimal amt = transaction.getAmount();
				BigDecimal toAccountBalance = transaction.getTobalance();
				
				if (toAccountBalance == null)
				{
				  toAccountBalance = new BigDecimal(0.00);
				}
				
				BigDecimal fromAccountBalance = transaction.getBalance();
				if (fromAccountBalance == null)
				{
				  fromAccountBalance = new BigDecimal(0.00);
				}
				
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				
				String[] result = date.split("\\s");
	%>
		
	<ul style="border-top:1px solid black; border-bottom:1px solid black; height:45px;">
		<li><%=result[0]%></li>
		<% if (aAccount.equals(toAccount)) { %>
		<li style="width:40%">Transfer In From <%=transaction.getFromAccount().getType()%><%=transaction.getFromAccount().getAccountNo()%><%if (transaction.getUserdescription() != null) { %> &nbsp;<i>for <%=transaction.getUserdescription()%></i><%} %></li>
		<%} else if (transaction.getDescription().equals("Transfer") && aAccount.equals(fromAccount)) { %>
		<li style="width:40%">Transfer To <%=transaction.getToAccount().getType()%><%=transaction.getToAccount().getAccountNo()%><%if (transaction.getUserdescription() != null) { %> &nbsp;<i>for <%=transaction.getUserdescription()%></i><%} %></li>
		<%} else { %>
		<li style="width:40%"><%=transaction.getDescription()%><%if(transaction.getUserdescription() != null){%>&nbsp;<i>for <%=transaction.getUserdescription() %></i><%} %></li>
		<%} %>
		<li style="text-align:right"><%=nf.format(amt)%></li>
		<% if (aAccount.equals(toAccount)) { %>
		<li style="text-align:right"><%=nf.format(toAccountBalance)%></li>
		<%} else { %>
		<li style="text-align:right"><%=nf.format(fromAccountBalance)%></li>
		<%} %>
	</ul>
		
	<%count++;}%>
	
</div>