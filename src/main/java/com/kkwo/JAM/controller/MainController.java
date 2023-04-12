package com.kkwo.JAM.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	
	public MainController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
	}

	public void main() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
	}
	
	
}
