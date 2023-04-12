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

@WebServlet("/article/doModify")
public class ArticleDoModifyServlet extends HttpServlet {

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

			HttpSession session = request.getSession();

			if (session.getAttribute("loginedMemberId") == null) {
				response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
				return;
			}

			String title = (String) request.getParameter("title");
			String body = (String) request.getParameter("body");
			
			int id = Integer.parseInt(request.getParameter("id"));
			int memberId = (int) session.getAttribute("loginedMemberId");

			SecSql sql = SecSql.from("SELECT * ");
			sql.append("FROM article ");
			sql.append("WHERE id = ?", id);

			Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
			
			if (!articleRow.get("memberId").equals(memberId)) {
				response.getWriter().append(String.format("<script>alert('수정 권한이 없습니다');history.back();</script>"));
				return;
			}

			sql = SecSql.from("UPDATE article ");
			sql.append("SET regDate = NOW(),");
			sql.append(" title = ?,", title);
			sql.append(" `body` = ?", body);
			sql.append("WHERE id = ?", id);

			DBUtil.update(conn, sql);

			response.getWriter().append(String
					.format("<script>alert('%d번 글이 수정되었습니다');location.replace('detail?id=%d');</script>", id, id));

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