<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.AccountType, entityBeans.Account"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>

<%Person person = (Person)request.getAttribute("reqObject"); %>
<%Collection<Account> accountList = (Collection)request.getAttribute("alist"); %>

<%@include file="navigation.html" %>
<header> 
  <h3>Order Checks</h3>
  <p>Select Check Design (Checks come in packs of 100)</p>
</header>

<div id="displayMsg">
<%
	if (request.getAttribute("success") != null)
	{
%>
	<div class="success">
		<p><%=request.getAttribute("success")%></p>
		<input type="button" value="Back to Checks" onclick="showOrderCheck();" />
	</div>
<%}
	else
	{%>
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
			<%for (Iterator<Account> b = accountList.iterator(); b.hasNext();)
			  {
					Account aAccount = (Account)b.next();
					if (aAccount.getAccountType() != AccountType.SECURITY)
					{
			%>						
				<option value="<%=aAccount.getAccountid()%>"><%=aAccount.getAccountType()%><%=aAccount.getAccountNo()%> &nbsp;($<%=aAccount.getBalance()%> )</option>
			<%} } %>
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