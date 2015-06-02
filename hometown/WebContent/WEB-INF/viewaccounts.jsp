<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="entityBeans.AccountType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.AccountType, entityBeans.Account"%>
<%@page import="java.util.Iterator"%>
<a id="PageTop" name="PageTop"></a>
<script type="text/javascript">window.location.hash='PageTop';</script>

<% Person person = (Person)request.getAttribute("reqObject"); %>
<%@ include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<%if (request.getParameter("cmd").equals("login")){ %>
<div class="welcome"> Welcome&nbsp;<%=person.getFullname()%>!</div>
<%}%>

<div id="header"> 
	<ul>
		<li class="large">View Account Information</li>
		<li class="small">Click on an account to view more details</li>
	</ul>	
</div>

<div id="accounts">
<div id="test">
<div class="title" onclick="toggle('checking');">Checking <span id="checkingtotal"></span></div>
<div id="checking" class="accountType">			
	<% for (Iterator<Account> it = person.getAccounts().iterator(); it.hasNext();)
	   {
				Account aAccount = (Account)it.next();
				if (aAccount.getAccountType() == AccountType.CHECKING) { %>
	<div class="caccount">
		<ul>
			<li class="accountName" onclick="showAccountDetails(<%=aAccount.getAccountid()%>);"><%=aAccount.getAccountNo()%></li>
			<li>$<%=aAccount.getBalance()%></li>
		</ul>
	</div>
	<%}}%>	
</div>
</div>

 
<div id="test2">
<div class="title" onclick="toggle('savings');">Savings<span id="savingstotal"></span></div>
<div id="savings" class="accountType">	
	<% for(Iterator<Account> it = person.getAccounts().iterator(); it.hasNext();) {
				Account aAccount = (Account)it.next();
				if (aAccount.getAccountType() == AccountType.SAVINGS) { %>
	<div class="saccount">
		<ul>
			<li class="accountName" onclick="showAccountDetails(<%=aAccount.getAccountid()%>);"><%=aAccount.getAccountNo()%></li>
			<li>$<%=aAccount.getBalance()%></li>
		</ul>
	</div>
	<%}}%>	
</div>
</div>

<div id="test3">
<div class="title" onclick="toggle('credit');">Credit Cards <span id="credittotal"></span></div>
<div id="credit" class="accountType">	
	<% for(Iterator<Account> it = person.getAccounts().iterator(); it.hasNext();) {
				Account aAccount = (Account)it.next();
				if (aAccount.getAccountType() == AccountType.CREDIT) { %>
	<div class="saccount">
		<ul>
			<li class="accountName" onclick="showAccountDetails(<%=aAccount.getAccountid()%>);"><%=aAccount.getAccountNo()%></li>
			<li>$<%=aAccount.getBalance()%></li>
		</ul>
	</div>
	<%}}%>	
</div>
</div>

<div id="test4">
<div class="title" onclick="toggle('cashdeposit');">Security <span id="cashdeposittotal"></span></div>
<div id="cashdeposit" class="accountType">	
	<% for(Iterator<Account> it = person.getAccounts().iterator(); it.hasNext();) {
				Account aAccount = (Account)it.next();
				if (aAccount.getAccountType() == AccountType.SECURITY) { %>
	<div class="saccount">
		<ul>
			<li class="accountName" onclick="showAccountDetails(<%=aAccount.getAccountid()%>);"><%=aAccount.getAccountNo()%></li>
			<li>$<%=aAccount.getBalance()%></li>
		</ul>
	</div>
	<%}}%>	
</div>
</div>
</div>