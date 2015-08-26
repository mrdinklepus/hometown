<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% String payeeid = request.getAttribute("payeeid") != null ? request.getAttribute("payeeid").toString() : "";%>
<% String co = request.getAttribute("coname") != null ? request.getAttribute("coname").toString() : "";%>
<%@include file="navigation.html" %>
<div id="subnav">
	<ul>
		<li id="addPayee" onclick="showManagePayees();" style="background-color:#BED8F1">Manage Payees</li>
		<li id="schedulePayment" onclick="showSchedule();">Schedule Payment</li>
	</ul>
</div>
<br/>
<div id="displayMsg">
	<div class="success">
		<p style="color:black;font-weight:bold;">Are you sure you want to remove "<%=co%>" from your payees list?</p>
		<input type="button" value="Cancel" onclick="showManagePayees();" />
		<input type="button" value="Continue" onclick="removePayee(<%=payeeid%>);" />
	</div>
</div>