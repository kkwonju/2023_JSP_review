<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>
	<h1>게시물 리스트 v1</h1>
	<ul>
		<%for(int i = 0; i < articleRows.size(); i++){%>
		<li style="list-style: none;"><%=articleRows.get(i).get("id")%>번,
			<%=articleRows.get(i).get("regDate")%>, <%=articleRows.get(i).get("title")%>,
			<%=articleRows.get(i).get("body")%><br></li>
		<%} %>
	</ul>

	<h1>게시물 리스트 v2</h1>
	<ul>
		<%
		for (Map<String, Object> articleRow : articleRows){
		%>
		<li style="list-style: none;"><%=articleRow.get("id")%>번,
			<%=articleRow.get("regDate")%>, <%=articleRow.get("title")%>,
			<%=articleRow.get("body")%><br></li>
		<%} %>
	</ul>
</body>
</html>