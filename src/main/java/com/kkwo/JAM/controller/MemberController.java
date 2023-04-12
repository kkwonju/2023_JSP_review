package com.kkwo.JAM.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.service.ArticleService;
import com.kkwo.JAM.service.MemberService;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

public class MemberController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private MemberService memberService;

	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
		this.memberService = new MemberService(conn);
	}

	public void showJoinForm() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
	}

	public void doJoin() throws ServletException, IOException {
		String loginId = (String) request.getParameter("loginId");
		String loginPw = (String) request.getParameter("loginPw");
		String name = (String) request.getParameter("name");

		int cnt = memberService.getMemberCntByLoginId(loginId);
		if (cnt != 0) {
			response.getWriter()
					.append(String.format("<script>alert('이미 사용중인 아이디입니다');location.replace('join');</script>"));
			return;
		}

		memberService.joinMember(loginId, loginPw, name);

		response.getWriter().append(
				String.format("<script>alert('%s님 환영합니다');location.replace('../home/main');</script>", loginId));
	}

	public void showLoginForm() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);
	}

	public void doLogin() throws ServletException, IOException {
		String loginId = (String) request.getParameter("loginId");
		String loginPw = (String) request.getParameter("loginPw");

		Map<String, Object> memberRow = memberService.getMemberRowByLoginId(loginId);

		if (memberRow.isEmpty()) {
			response.getWriter()
					.append(String.format("<script>alert('일치하는 회원이 없습니다');location.replace('login');</script>"));
			return;
		}

		if (!memberRow.get("loginPw").equals(loginPw)) {
			response.getWriter()
					.append(String.format("<script>alert('비밀번호가 틀립니다');location.replace('login');</script>"));
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginedMemberId", memberRow.get("id"));
		session.setAttribute("loginedMemberLoginId", memberRow.get("loginId"));
		response.getWriter().append(
				String.format("<script>alert('%s님 환영합니다');location.replace('../home/main');</script>", loginId));
	}

	public void doLogout() throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		session.removeAttribute("loginedMemberId");
		session.removeAttribute("loginedMemberLoginId");

		response.getWriter()
				.append(String.format("<script>alert('로그아웃되었습니다');location.replace('../home/main');</script>"));
	}

}
