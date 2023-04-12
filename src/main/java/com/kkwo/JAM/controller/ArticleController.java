package com.kkwo.JAM.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.service.ArticleService;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

public class ArticleController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private ArticleService articleService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
		this.articleService = new ArticleService(conn);
	}

	public void showList() throws ServletException, IOException {
		int page = 1;

		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int totalPage = articleService.getTotalPage(page);
		List<Map<String, Object>> articleRows = articleService.getForPrintArticleRows(page);

		request.setAttribute("articleRows", articleRows);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

	public void showWriteForm() throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
			return;
		}

		request.setAttribute("loginedMemberId", session.getAttribute("loginedMemberId"));
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}

	public void doWrite() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
			return;
		}

		String title = (String) request.getParameter("title");
		String body = (String) request.getParameter("body");
		int memberId = (int) Integer.parseInt(request.getParameter("memberId"));

		int id = articleService.getArticleId(title, body, memberId);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 생성되었습니다');location.replace('list');</script>", id));
	}

	public void showDetail() throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Map<String, Object> articleRow = articleService.getForPrintArticleRow(id); 

		request.setAttribute("articleRow", articleRow);
		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
	}

	public void showModifyForm() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
			return;
		}

		int memberId = (int) session.getAttribute("loginedMemberId");
		int id = Integer.parseInt(request.getParameter("id"));

		Map<String, Object> articleRow = articleService.getArticleRowById(id);

		if (!articleRow.get("memberId").equals(memberId)) {
			response.getWriter().append(String.format("<script>alert('수정 권한이 없습니다');history.back();</script>"));
			return;
		}
		
		request.setAttribute("articleRow", articleRow);
		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}

	public void doModify() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
			return;
		}

		String title = (String) request.getParameter("title");
		String body = (String) request.getParameter("body");

		int id = Integer.parseInt(request.getParameter("id"));
		int memberId = (int) session.getAttribute("loginedMemberId");

		Map<String, Object> articleRow = articleService.getArticleRowById(id);

		if (!articleRow.get("memberId").equals(memberId)) {
			response.getWriter().append(String.format("<script>alert('수정 권한이 없습니다');history.back();</script>"));
			return;
		}

		articleService.modifyArticle(title, body, id);

		response.getWriter().append(
				String.format("<script>alert('%d번 글이 수정되었습니다');location.replace('detail?id=%d');</script>", id, id));
	}

	public void doDelete() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
			return;
		}

		int memberId = (int) session.getAttribute("loginedMemberId");
		int id = Integer.parseInt(request.getParameter("id"));

		Map<String, Object> articleRow = articleService.getArticleRowById(id);

		if (!articleRow.get("memberId").equals(memberId)) {
			response.getWriter().append(String.format("<script>alert('삭제 권한이 없습니다');history.back();</script>"));
			return;
		}

		articleService.deleteArticle(id);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 삭제되었습니다');location.replace('list');</script>", id));
	}

}
