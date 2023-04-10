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

import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");

		// DB 연결
		String url = "jdbc:mysql://127.0.0.1:3306/JSPAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다");
			System.out.println("프로그램을 종료합니다");
			return;
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			
			int page = 1;
			int maxArticle = 5;
			if(request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));				
			}
			
			int articleFrom = (page-1)*maxArticle;
			
			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC");
//			if(page != 0) {
//				sql.append("LIMIT ?, ?", articleFrom, maxArticle);
//			}
			
			List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
			int totalPage = (int) Math.ceil((double) articleRows.size()/maxArticle);
			
			response.getWriter().append(articleRows.toString());
			// 여기까지 직접 처리
			
			// 여기는 위탁 처리
			request.setAttribute("articleRows", articleRows); // set => jsp에서 get
			request.setAttribute("maxArticle", maxArticle);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("articleFrom", articleFrom);
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