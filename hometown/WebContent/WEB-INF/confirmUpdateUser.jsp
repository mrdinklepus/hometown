<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entityBeans.Person"%>
<%@include file="navigation.html" %>
<%
Person person = (Person)request.getAttribute("reqObject");
String username = "";
if (person != null) {
  username = person.getUsername();
}
%>
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
  <div class="title">Update your online account login info!</div>
  <div class="form">          
    <form name="signup" action="postForm()">
      <div class=formline><label for="un">Username:</label><div class="textfield"><input title="Username cannot contain special characters" type="text" id="un" name="un" maxlength="15" required pattern="[a-zA-Z0-9]+" value="<%=username%>"/></div></div>
      <div class=formline><label for="pw">New Password:</label><div class="textfield"><input title="Password must contain at least 6 characters (including 1 UPPER, 1 lower, & 1 number)" type="password" id="pw" name="pw" maxlength="12" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" onchange=
         "this.setCustomValidity(this.validity.patternMismatch ? this.title : '');
          if(this.checkValidity()) form.pw2.pattern = this.value;"/></div></div>
      <div class=formline><label for="pw2">Re-enter Password:</label><div class="textfield"><input title="Passwords must match" type="password" id="pw2" name="pw2" maxlength="12" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" onchange=
         "this.setCustomValidity(this.validity.patternMismatch ? this.title : '');"/></div></div>
      <input name="cmd" type="hidden" value="updateUP"/>
      <div class="submitbutton"><input type="submit" value="Submit"/></div>
    </form> 
  </div>
</div>