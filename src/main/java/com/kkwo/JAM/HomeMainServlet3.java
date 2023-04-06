package com.kkwo.JAM;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/printDan")
public class HomeMainServlet3 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); // 아래 컨텐츠의 타입을 다음으로 하겠다
		
		String inputedDan = request.getParameter("dan"); 
		String inputedLimit = request.getParameter("limit"); 
		
		if(inputedDan == null) {
			inputedDan = "1";
		}
		if(inputedLimit == null) {
			inputedLimit = "1";
		}
				
		int dan = Integer.parseInt(inputedDan);
		int limit = Integer.parseInt(inputedLimit);
		response.getWriter().append(String.format("==%d단==<br>", dan));
		
		for(int i = 1; i <= limit; i++) {
			response.getWriter().append(String.format("%d + %d = %d<br>",dan, i , dan*i));
		}
	}
}