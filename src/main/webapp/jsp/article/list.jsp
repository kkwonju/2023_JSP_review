<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
int totalPage = (int) request.getAttribute("totalPage");
int cPage = (int) request.getAttribute("page");
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
	<div>
		<a href="write">글쓰기</a>
	</div>

	<h1>게시물 리스트</h1>

	<table style="border-collapser: collapse; border-color: green"
		border=3px>
		<tr>
			<th>번호</th>
			<th>작성날짜</th>
			<th>제목</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
		<%
		for (Map<String, Object> articleRow : articleRows) {
		%>
		<tr style="text-align: center;">
			<td><%=articleRow.get("id")%></td>
			<td><%=articleRow.get("regDate")%></td>
			<td><a href="detail?id=<%=articleRow.get("id")%>"><%=articleRow.get("title")%></a></td>
			<td><a href="modify?id=<%=articleRow.get("id")%>">수정</a></td>
			<td><a href="doDelete?id=<%=articleRow.get("id")%>">삭제</a></td>
		</tr>
		<%
		}
		%>
	</table>

	<style>
.page {
	background-color: gold;
}

.page>a {
	color: black;
}

.page>a.red {
	color: red;
}
</style>

	<div class="page">
		<%
		int limitPage = 5;
		int start = cPage - limitPage;
		int end = cPage + limitPage;

		if (start < 1) {
			start = 1;
		}

		if (end > totalPage) {
			end = totalPage;
		}

		if (cPage > 1) {
		%>
		<a href="list?page=1"> << </a>
		<%
		}
		for (int i = start; i <= end; i++) {
		%>
		<a class="<%=cPage == i ? "red" : ""%>" href="list?page=<%=i%>"><%=i%></a>
		<%
		}
		if (cPage < totalPage) {
		%>
		<a href="list?page=<%=totalPage%>"> >> </a>
		<%
		}
		%>
	</div>
</body>
</html>