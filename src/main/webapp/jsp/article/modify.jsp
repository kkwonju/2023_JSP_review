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
<title>게시물 작성 페이지</title>
</head>
<body>
	<div>
		<a href="list">목록으로 돌아가기</a>
	</div>
	<form action="doModify" method="post">
		<input name="id" type="hidden" value="${param.id}" />
		<div>
			번호:
			<%=articleRow.get("id")%>,
		</div>
		<div>
			날짜:
			<%=articleRow.get("regDate")%>,
		</div>
		<div>
			제목: <input name="title" type="text" value="<%=articleRow.get("title")%>" />
		</div>
		<div>
			내용:
			<textarea name="body"><%=articleRow.get("body")%></textarea></div>
		<button type="submit">수정</button>
		<button type="reset">초기화</button>
	</form>
</body>
</html>