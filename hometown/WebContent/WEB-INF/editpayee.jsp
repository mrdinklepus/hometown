<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person, entityBeans.Address, entityBeans.Phone, entityBeans.PayeeAccount, entityBeans.Payee"%>
<%@page import="java.util.Iterator"%>
<% Payee payee = (Payee)request.getAttribute("reqObject");%>
<% Phone ph = (Phone)request.getAttribute("phone");%>
<% PayeeAccount payeeAccount = (PayeeAccount)request.getAttribute("payeeAccount");%>
<%@include file="navigation.html" %>
<header> 
  <h3>Edit <%=payee.getCompany()%></h3>
  <p>Update Information for this payee</p>
</header>
<%@include file="subnavBillPay.html" %>
<br/>
<!-- <input type="button" class="right" style="margin-bottom:35px;" value="Back to Manage Payees" onclick="showManagePayees();"/> -->
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

<div id="billpay">	
	<div class="spacer"></div>
	<div class="addnew"">
		<!-- <div class="title">Information for <%=payee.getCompany()%></div><br/>-->
		<div class="form">					
			<form action="javascript:postForm()">
				Company Name:&nbsp;&nbsp; <input type="text" name="coname" value="<%=payee.getCompany()%>" maxlength="35"/> <br />
				Street Address:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="street" required value="<%=payee.getAddress().getStreet()%>" maxlength="40"/> <br />
				City:&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="city" required value="<%=payee.getAddress().getCity()%>" maxlength="20"/>
				State: <input name="state" value="<%=payee.getAddress().getState()%>" type="text" size="4" maxlength="2" required/><br>
				Zip:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="zip" required value="<%=payee.getAddress().getZipcode()%>"size="5" maxlength="5"/> <br />
				Phone:&nbsp; <input id="test" type="tel" name="phone" placeholder="(optional)" <%if (ph != null){%>value="<%=ph.getPhoneNumber()%>"<%}%> maxlength="10"/><br />
				<!-- Fax:&nbsp; <input id="test" type="text" name="phone" value="<%if (ph != null){%><%=ph.getPhoneNumber()%><%}%>" maxlength="10"/> &nbsp; (optional)<br /> -->
				Account Number:&nbsp; <input type="text" name="accnum" required value="<%=payeeAccount.getPayeeAccountKey().getPayeeAccountNo()%>" maxlength="30"/> <br />	
				<input type="hidden" name="cmd" value="editPayee" />
				<input type="hidden" name="payeeid" value="<%=payee.getPayeeid()%>" />
				<!-- <input type="hidden" name="addressid" value="<%=payee.getAddress().getAddressid()%>" /> 	 -->
				<!-- <input type="hidden" name="phoneid" value="<%if (ph != null){%><%=ph.getPhonetype()%><%}else{%>0<%}%>" /> 	 -->
				<input type="hidden" name="origaccnum" value="<%=payeeAccount.getPayeeAccountKey().getPayeeAccountNo()%>" />
				<div class="submitbutton"><input type="submit" value="UPDATE"/></div>
			</form>		
		</div>
	</div>
</div>