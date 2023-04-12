<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<script type="text/javascript">
		function joinForm__submit(form) {
			var joinForm__submitDone = false;
			if(joinForm__submitDone){
				alert('처리중입니다');
				return;
			}
			var loginId = form.loginId.value.trim();
			var loginPw = form.loginPw.value.trim();
			
			if(loginId.length == 0){
				alert('아이디를 입력해주세요');
				return;
			}
			if(loginPw.length == 0){
				alert('비밀번호를 입력해주세요');
				return;
			}
			joinForm__submitDone = true;
			form.submit();
		}
	</script>

	<div>
		<a href="../home/main">메인으로</a>
	</div>
	<h1>로그인</h1>
	<form action="doLogin" method="post"
		onsubmit="joinForm__submit(this); return false;">
		<div>
			<lable for="loginId">아이디: </lable>
			<input name="loginId" type="text" placeholder="loginId" autofocus autocomplete="off"/>
		</div>
		<div>
			<lable for=""loginPw"">비밀번호: </lable>
			<input name="loginPw" type="password" placeholder="loginPw" />
		</div>
		<input type="submit" value="로그인"/>
	</form>
</body>
</html>