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

import com.kkwo.JAM.config.Config;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다");
			System.out.println("프로그램을 종료합니다");
			return;
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());

			String loginId = (String) request.getParameter("loginId");
			String loginPw = (String) request.getParameter("loginPw");
			String name = (String) request.getParameter("name");
			
			SecSql sql = SecSql.from("SELECT COUNT(*) FROM `member`");
			sql.append("WHERE loginId = ?", loginId);
			
			int cnt = DBUtil.selectRowIntValue(conn, sql);
			if(cnt != 0) {
				response.getWriter()
				.append(String.format("<script>alert('이미 사용중인 아이디입니다');location.replace('join');</script>"));
				return;
			}
			
			sql = SecSql.from("INSERT INTO `member` ");
			sql.append("SET regDate = NOW(),");
			sql.append(" loginId = ?,", loginId);
			sql.append(" loginPw = ?,", loginPw);
			sql.append(" `name` = ?;", name);

			DBUtil.insert(conn, sql);

			response.getWriter()
					.append(String.format("<script>alert('%s님 환영합니다');location.replace('../home/main');</script>", loginId));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}