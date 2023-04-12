<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
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
			var loginPwConfirm = form.loginPwConfirm.value.trim();
			var name = form.name.value.trim();
			
			if(loginId.length == 0){
				alert('아이디를 입력해주세요');
				return;
			}
			if(loginPw.length == 0){
				alert('비밀번호를 입력해주세요');
				return;
			}
			if(loginPwConfirm.length == 0){
				alert('비밀번호 확인을 입력해주세요');
				return;
			}
			if(loginPw != loginPwConfirm){
				alert('비밀번호가 일치하지 않습니다');
				form.loginPw.focus();
				return;
			}
			if(name.length == 0){
				alert('이름을 입력해주세요');
				return;
			}
			joinForm__submitDone = true;
			form.submit();
		}
	</script>

	<div>
		<a href="../home/main">메인으로</a>
	</div>
	<form action="doJoin" method="post"
		onsubmit="joinForm__submit(this); return false;">
		<div>
			<lable for="loginId">아이디: </lable>
			<input name="loginId" type="text" placeholder="loginId" autofocus autocomplete="off"/>
		</div>
		<div>
			<lable for=""loginPw"">비밀번호: </lable>
			<input name="loginPw" type="password" placeholder="loginPw" />
		</div>
		<div>
			<lable for="loginPwConfirm">비밀번호 확인: </lable>
			<input name="loginPwConfirm" type="password" placeholder="loginPwConfirm" />
		</div>
		<div>
			<lable for="name">이름: </lable>
			<input name="name" type="text" placeholder="name" autocomplete="off"/>
		</div>
		<input type="submit" value="완료"/>
	</form>
</body>
</html>