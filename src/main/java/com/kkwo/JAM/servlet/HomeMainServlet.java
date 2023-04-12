package com.kkwo.JAM.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/home/main")
public class HomeMainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isLogined = false;
		String loginedMemberLoginId = null;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null && session.getAttribute("loginedMemberLoginId") != null) {
			isLogined = true;
			loginedMemberLoginId = (String) session.getAttribute("loginedMemberLoginId");
		}
		
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberLoginId", loginedMemberLoginId);
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	}
}