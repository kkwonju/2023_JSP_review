<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<h1>글쓰기</h1>
	<form action="doWrite" method="post">
		<div>
			제목
		</div>
			<input name="title" type="text" placeholder="제목을 입력하세요" />
		<div>
			내용
		</div>
			<textarea name="body" id="body" placeholder="내용을 입력하세요"></textarea>
		<div>
			<button type="submit">작성</button>
			<button type="reset">초기화</button>
		</div>
	</form>
</body>
</html>