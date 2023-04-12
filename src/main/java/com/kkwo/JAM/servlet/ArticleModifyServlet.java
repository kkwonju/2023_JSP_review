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

@WebServlet("/article/modify")
public class ArticleModifyServlet extends HttpServlet {

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
			int loginedMemberId = (int) session.getAttribute("loginedMemberId");
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = SecSql.from("SELECT COUNT(*)"); 
			sql.append(" FROM article AS a");
			sql.append("INNER JOIN `member` AS m");
			sql.append("ON a.memberId = m.id");
			sql.append(" WHERE a.memberId = ?", loginedMemberId);
			sql.append(" AND a.id = ?", id);
			
			int cnt = DBUtil.selectRowIntValue(conn, sql);
			if(cnt == 0) {
				response.getWriter()
				.append(String.format("<script>alert('수정 권한이 없습니다');history.back();</script>"));
				return;
			}
			
			sql = SecSql.from("SELECT * FROM article");
			sql.append("WHERE id = ?", id);

			Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
			
			request.setAttribute("articleRow", articleRow);
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
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