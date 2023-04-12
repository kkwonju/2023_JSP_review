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

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {

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

			boolean isLogined = false;
			int loginedMemberId = -1;
			Map<String, Object> memberRow = null;

			HttpSession session = request.getSession();
			if (session.getAttribute("loginedMemberId") != null) {
				isLogined = true;
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
				
				SecSql sql = SecSql.from("SELECT *");
				sql.append("FROM `member`");
				sql.append("WHERE id = ?", loginedMemberId);
				
				memberRow = DBUtil.selectRow(conn, sql);
			}
			
			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("memberRow", memberRow);
			
			int page = 1;
			if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			int itemsInAPage = 10;
			int limitFrom = (page - 1) * itemsInAPage;

			SecSql sql = SecSql.from("SELECT COUNT(*)");
			sql.append("FROM article");

			int totalCnt = DBUtil.selectRowIntValue(conn, sql);
			int totalPage = (int) Math.ceil((double) totalCnt / itemsInAPage);

			sql = SecSql.from("SELECT A.*, M.name AS 'extra__writer'");
			sql.append("FROM article AS A");
			sql.append("INNER JOIN `member` AS M");
			sql.append("ON A.memberId = M.id");
			sql.append("ORDER BY A.id DESC");
			sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

			List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

			response.getWriter().append(articleRows.toString());

			request.setAttribute("articleRows", articleRows);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);

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
}