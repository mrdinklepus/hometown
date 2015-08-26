<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page import="entityBeans.AccountType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entityBeans.Person, entityBeans.AccountType, entityBeans.Account"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="navigation.html" %>

<% Person person = (Person)request.getAttribute("reqObject"); %>

<%if (request.getParameter("cmd").equals("login")){ %>
<div id="displayMsg">
  <div class="success"> Welcome&nbsp;<%=person.getFullname()%>!</div>
</div>
<%}%>

<header> 
  <h3>View Account Information</h3>
  <p>Click on an account to view more details</p>
</header>

<div id="accounts">
<div id="test">
	<div class="title" onclick="toggle('checking');">Checking <span id="checkingtotal"></span></div>
	<div id="checking" class="accountType">			
		<% for (Iterator<Account> it = person.getAccounts().iterator(); it.hasNext();)
		   {
					Account aAccount = (Account)it.next();
					if (aAccount.getAccountType() == AccountType.CHECKING) { 
					  System.out.println("Account Balance: " + aAccount.getBalance()); %>
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