package com.kkwo.JAM;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home/printDan")
public class HomePrintDanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Hi"); 응답하는게 아니라 요청을 한 번 더 할 예정
		
		request.getRequestDispatcher("/jsp/home/printDan.jsp").forward(request, response);
		// "대상" : 대상에 요청 , 일을 미룸
		// forward : 내가 썼던 자원을 넘겨줌
	}
}