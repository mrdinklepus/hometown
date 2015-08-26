<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.math.BigDecimal" %>
<% String toNum = request.getAttribute("toNum").toString();%>
<% String fromNum = request.getAttribute("fromNum").toString();%>
<% BigDecimal amt = (BigDecimal)request.getAttribute("amt");%>
<% String to = request.getAttribute("to").toString();%>
<% String from = request.getAttribute("from").toString();%>
<% String toType = request.getAttribute("toType").toString();%>
<% String fromType = request.getAttribute("fromType").toString();%>
<%@include file="navigation.html" %>
<br/>
<div id="displayMsg">
	<div>
	<form action="javascript:postForm()">
		<p style="color:black;font-weight:bold;">Transferring $<%=amt%> from <%=fromType%> ( <%=fromNum%> ) to <%=toType%> ( <%=toNum%> ).</p>
		Add description:&nbsp;&nbsp; <i>for</i>&nbsp;<input type="text" name="tDescription" maxlength="35"/> <br><br>
		<input name="to" type="hidden" value="<%=to%>"/>
		<input name="from" type="hidden" value="<%=from%>"/>
		<input name="amt" type="hidden" value="<%=amt%>"/>
		<input name="cmd" type="hidden" value="transfer"/>
	
	  <input type="button" value="Cancel" onclick="showTransfer();" />
	  <input type="submit" value="Continue"/>
	</form>
	</div>
</div>