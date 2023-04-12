<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세보기</title>
</head>
<body>
	<h1>게시물 상세페이지</h1>

	<div>
		번호:
		<%=articleRow.get("id")%>,
	</div>
	<div>
		날짜:
		<%=articleRow.get("regDate")%>,
	</div>
	<div>
		작성자 id:
		<%=articleRow.get("memberId")%>,
	</div>
	<div>
		제목:
		<%=articleRow.get("title")%>,
	</div>
	<div>
		내용:
		<%=articleRow.get("body")%></div>
	<div><a style="color:green;" href="list">리스트로 돌아가기</a></div>
	<a href="modify?id=${param.id}">수정</a>
	<a href="doDelete?id=${param.id}">삭제</a>
</body>
</html>