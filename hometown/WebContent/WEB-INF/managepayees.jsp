<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="entityBeans.Person, entityBeans.PayeeAccount, entityBeans.Payee, viewCO.PayeeAccountComparator"%>
<%@ page import="java.util.Iterator, java.util.Collections, java.util.List, java.util.ArrayList"%>
<% Person person = (Person)request.getAttribute("reqObject");%>

<%@ include file="navigation.html" %>
<header> 
  <h3>Manage Payees</h3>
  <p>Edit an existing payee, or add a new payee</p>
</header>
<%@ include file="subnavBillPay.html" %>
<br/>

<div id="billpay">
	<div class="current">
		<div class="title">Current Payees &nbsp;&nbsp;&nbsp;&nbsp;(Hover over Payee name for more info)</div>
<%
      List<PayeeAccount> payeeList = new ArrayList<PayeeAccount>(person.getPayeeAccounts());
		  Collections.sort(payeeList, new PayeeAccountComparator());
			for (PayeeAccount aPersonPayee : payeeList)
			{ 
				Payee payee = aPersonPayee.getPayeeAccountKey().getPayeeid();
				String remove = payee.getPayeeid() + "&payeeCompany=" + payee.getCompany() + "&payeeAccount=" + aPersonPayee.getPayeeAccountKey().getPayeeAccountNo();
				String tt = payee.getAddress().getStreet() + " " + payee.getAddress().getCity() + ", " + payee.getAddress().getState() + " " + payee.getAddress().getZipcode();
%>		
			<div id="<%=payee.getCompany()%>" class="currentPayee">				
				<div class="payeeName"><a title="<%=tt%>"><%=payee.getCompany()%></a></div>
				<div class="buttons">
					<span class="edit" onclick="editPayee(<%=payee.getPayeeid()%>)">EDIT</span>
					<span class="remove" onclick="confirmRemovePayee('<%=remove%>')">REMOVE</span>
				</div>
			</div>
		<%} %>			
	</div>
	<div class="spacer"></div>
	<div id="displayMsg">
	<%Object err = request.getAttribute("error");
	  Object suc = request.getAttribute("success");
	  if (err != null)
	  {
	%>
	  <div class="error">
	    <p><%=err.toString()%></p>
	  </div>
	<%
	  }
	  
	  if (suc != null)
	  {
	%>
	  <div class="success">
	    <p><%=suc.toString()%></p>
	  </div>
	<% } %>
	</div>
	<div class="addnew">
		<div class="title">Create New Payee</div>
		<div class="form">					
			<form action="javascript:postForm()">
				Company Name:&nbsp;&nbsp; <input type="text" name="coname" maxlength="35" required/><br>
				Street Address:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="street" maxlength="40" required/><br>
				City:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="city" maxlength="20" required/>
				State: <input name="state" type="text" size="4" maxlength="2" required/><br>
				Zip:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="zip" size="5" maxlength="5" required/><br>
				Phone:&nbsp; <input type="tel" name="phone" maxlength="10" placeholder="(optional)"/><br>
				Account Number:&nbsp; <input type="text" name="accnum" maxlength="30" required/><br>
				<input type="hidden" name="cmd" value="addPayee" /> 	
				<div class="submitbutton"><input type="submit" value="Add"/></div>
			</form>		
		</div>
	</div>
</div>