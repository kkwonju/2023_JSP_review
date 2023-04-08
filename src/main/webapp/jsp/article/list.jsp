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
	<div>
		<a href="../home/main">메인 페이지</a>
	</div>

	<h1>게시물 리스트</h1>
	<ul>
		<%
		for (Map<String, Object> articleRow : articleRows) {
		%>
		<li><a href="detail?id=<%=articleRow.get("id")%>">
				<%=articleRow.get("id")%>번,
				<%=articleRow.get("regDate")%>,
				<%=articleRow.get("title")%>,
				<%=articleRow.get("body")%></a></li>
		<%
		}
		%>
	</ul>
</body>
</html>