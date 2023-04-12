package com.kkwo.JAM.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.config.Config;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

@WebServlet("/member/doLogout")
public class MemberDoLogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		session.removeAttribute("loginedMemberId");
		session.removeAttribute("loginedMemberLoginId");

		response.getWriter()
				.append(String.format("<script>alert('로그아웃되었습니다');location.replace('../home/main');</script>"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}