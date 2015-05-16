<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Banktransaction, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ListIterator"%>
<%@page import="java.util.*"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@ include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<% List list = (List)request.getAttribute("list"); %>
<% Account aAccount = (Account)request.getAttribute("reqObject"); %>
<br />
<input type="button" class="right" value="Back to Accounts" onclick="showViewAccounts()"/>

<div id="header"> 
	<ul>
		<li class="large">Account Details for Account <%=aAccount.getAccountno()%></li>
	</ul>	
</div>
<div id="details">
		<div class="header">
			<ul>
				<li>Date</li>
				<li style="width:40%">Description</li>
				<li style="text-align:right">Amount</li>
				<li style="text-align:right;">Balance&nbsp;</li>
			</ul>
		</div>
	 
	<% int count = 0;//Iterator it = list.iterator();%>
	<% Collections.reverse(list); %>
	<% for(ListIterator it = list.listIterator(); it.hasNext();) {
				
				if (count >=20){
					break;
				}
				Banktransaction bt = (Banktransaction)it.next();
				String date = bt.getTimemade();
				Account a = bt.getToaccountid();
				Account b = bt.getFromaccountid();
				BigDecimal a1 = bt.getAmount();
				BigDecimal a2 = bt.getTobalance();
				if (a2 == null){
					a2 = new BigDecimal(0.00);
				}
				BigDecimal a3 = bt.getBalance();
				if (a3 == null){
					a3 = new BigDecimal(0.00);
				}
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				
				String[] result = date.split("\\s");
				System.out.println("TransId is " + bt.getTransactionid());
	%>
		
		<ul style="border-top:1px solid black; border-bottom:1px solid black; height:45px;">
			<li><%=result[0]%></li>
			<% if (aAccount.equals(a)){ %>
			<li style="width:40%">Transfer In From <%=bt.getFromaccountid().getType()%><%=bt.getFromaccountid().getAccountno()%><%if (bt.getUserdescription() != null){ %> &nbsp;<i>for <%=bt.getUserdescription()%></i><%} %></li>
			<%} else if (bt.getDescription().equals("Transfer") && aAccount.equals(b)){ %>
			<li style="width:40%">Transfer To <%=bt.getToaccountid().getType()%><%=bt.getToaccountid().getAccountno()%><%if (bt.getUserdescription() != null){ %> &nbsp;<i>for <%=bt.getUserdescription()%></i><%} %></li>
			<%} else{ %>
			<li style="width:40%"><%=bt.getDescription()%><%if(bt.getUserdescription() != null){%>&nbsp;<i>for <%=bt.getUserdescription() %></i><%} %></li>
			<%} %>
			<li style="text-align:right"><%=nf.format(a1)%></li>
			<% if (aAccount.equals(a)){ %>
			<li style="text-align:right"><%=nf.format(a2)%></li>
			<%}else { %>
			<li style="text-align:right"><%=nf.format(a3)%></li>
			<%} %>
		</ul>
		
	<%count++;}%>
	
</div>