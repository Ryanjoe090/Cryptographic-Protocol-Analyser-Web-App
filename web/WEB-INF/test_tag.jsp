<%@ page session="false" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags" %>

<cms:include property="template" element="head" />

<h1>A simple form</h1>

<%	
String name = request.getParameter("name");
if (name != null) {
%>
<h2>Your name is: <%= name %></h2>
<% } %>

<form name="test" method="get" action="example-jsp-template.jsp">
<p>Enter your name: <input name="name" size="20" value="">&nbsp;&nbsp;<input type="submit" value="OK"></p>
</form>	

<cms:include property="template" element="foot" />