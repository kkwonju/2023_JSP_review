package com.kkwo.JAM.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.management.loading.PrivateClassLoader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.config.Config;
import com.kkwo.JAM.controller.ArticleController;
import com.kkwo.JAM.controller.MainController;
import com.kkwo.JAM.controller.MemberController;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
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
			request.setAttribute("memberRow", memberRow);
			request.setAttribute("loginedMemberId", loginedMemberId);
			
			String requestUri = request.getRequestURI();
			String[] requestUriBits = requestUri.split("/");
			
			if(requestUriBits.length < 5) {
				response.getWriter().append("");
				return;
			}
			
			String controllerName = requestUriBits[3];
			String actionMethodName = requestUriBits[4];
			
			if(controllerName.equals("home")) {
				MainController mainController = new MainController(request, response, conn);
				
				if(actionMethodName.equals("main")) {
					mainController.main();
				}
			}
			
			if(controllerName.equals("article")) {
				ArticleController articleController = new ArticleController(request, response, conn);
				
				if(actionMethodName.equals("list")) {
					articleController.showList();
				}
				if(actionMethodName.equals("write")) {
					articleController.showWriteForm();
				}
				if(actionMethodName.equals("doWrite")) {
					articleController.doWrite();
				}
				if(actionMethodName.equals("detail")) {
					articleController.showDetail();
				}
				if(actionMethodName.equals("modify")) {
					articleController.showModifyForm();
				}
				if(actionMethodName.equals("doModify")) {
					articleController.doModify();
				}
				if(actionMethodName.equals("doDelete")) {
					articleController.doDelete();
				}
				
			}
			
			if(controllerName.equals("member")) {
				MemberController memberController = new MemberController(request, response, conn);
				
				if(actionMethodName.equals("join")) {
					memberController.showJoinForm();
				}
				if(actionMethodName.equals("doJoin")) {
					memberController.doJoin();
				}
				if(actionMethodName.equals("login")) {
					memberController.showLoginForm();
				}
				if(actionMethodName.equals("doLogin")) {
					memberController.doLogin();
				}
				if(actionMethodName.equals("doLogout")) {
					memberController.doLogout();
				}
			}
			
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