<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Personpayee, entityBeans.Account, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@page import="entityBeans.Payments"%>

<%Person person = (Person)request.getAttribute("reqObject"); %>
<%List list1 = (LinkedList)request.getAttribute("alist"); %>

<%@include file="navigation.html" %>
<div id="waiting">
	<p><b>Loading...</b></p><br/>
	<img src="images/Progressbar.gif">
</div>
<div id="header">
	<ul>
		<li class="large">Order Checks</li>
		<li class="small">Select Check Design (Checks come in packs of 100)</li>
	</ul>
</div>

<div id="emess">
<%	String cd = request.getAttribute("success").toString();
	if (cd.equals("yes")){ 
%>
<div class="transferSuccessful">
	<p>&nbsp;Thank You for using Home town Webbank.  Your checks have been ordered!</p>
	<input type="button" value="Back to Checks" onclick="showOrderCheck();" />
</div>
<%}else{%>
</div>
<div id="checkOrd">
<form>
	<div id="selectCheck">
		<div class="checkBox">
			<div><input type="radio" name="cid" value="ck1" Checked/>Price: $9.99</div>
			<div class="check"></div>
		</div>
		
		<div class="checkBox">
			<div><input type="radio" name="cid" value="ck2"/>Price: $9.99</div>
			<div class="check"></div>
		</div>
		
		<div class="checkBox">
			<div><input type="radio" name="cid" value="ck3"/>Price: $9.99</div>
			<div class="check"></div>
		</div>
	</div>
	<br><br/>	
	
	<div id="selectCheck">
		<div class="checkBox">
			<div><input type="radio" name="cid" value="ck4"/>Price: $9.99</div>
			<div class="check"></div>
		</div>
		
		<div class="checkBox">
			<div><input type="radio" name="cid" value="ck5"/>Price: $9.99</div>
			<div class="check"></div>
		</div>
		
		<div class="checkBox">
			<div><input type="radio" name="cid" value="ck6"/>Price: $9.99</div>
			<div class="check"></div>
		</div>
	</div>
	<br/><br/>
	<div id="selectAccount">
		<div>Select Account for Checks 
			<select name="aid">
			<% for (Iterator b = list1.iterator(); b.hasNext();){ 
					Account aAccount = (Account)b.next();
				if (aAccount.getAccounttype().equals("C") || aAccount.getAccounttype().equals("S") || aAccount.getAccounttype().equals("R")){ 
			%>						
				<option value="<%=aAccount.getAccountid()%>"><%=aAccount.getType()%><%=aAccount.getAccountno()%> &nbsp;($<%=aAccount.getBalance()%> )</option>
			<%}} %>
			</select>
		</div>
		<br/>
		<div>
			<input type="hidden" name="amt" value="9.99"/>
			<input type="hidden" name="cmd" value="orderChecks"/>
			<input type="button" value="Submit Order" onclick="postForm();"/>
		</div>
	</div>
</form>
</div>
<%}%>