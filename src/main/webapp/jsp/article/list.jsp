<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
int maxArticle = (int) request.getAttribute("maxArticle");
int totalPage = (int) request.getAttribute("totalPage");
int articleFrom = (int) request.getAttribute("articleFrom");
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

	<table style="border-collapser: collapse; border-color: green"
		border=3px>
		<tr>
			<th>번호</th>
			<th>작성날짜</th>
			<th>제목</th>
			<th>삭제</th>
		</tr>
		<%
		for (int i = articleFrom; i <= articleFrom+maxArticle; i++) {
		%>
		<tr style="text-align: center;">
			<td><%=articleRows.get(i).get("id")%></td>
			<td><%=articleRows.get(i).get("regDate")%></td>
			<td><a href="detail?id=<%=articleRows.get(i).get("id")%>"><%=articleRows.get(i).get("title")%></a></td>
			<td><a href="doDelete?id=<%=articleRows.get(i).get("id")%>">del</a></td>
		</tr>
		<%
		}
		%>		
	</table>
	<div>
	<%for(int i = 1; i <= totalPage; i++){ %>
		<a href="?page=<%=i%>"><%=i %></a>
	<%} %>
	</div>
</body>
</html>