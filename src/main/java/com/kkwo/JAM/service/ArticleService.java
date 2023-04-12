package com.kkwo.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kkwo.JAM.dao.ArticleDao;

public class ArticleService {
	private ArticleDao articleDao;
	private Connection conn;
	
	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
		this.conn = conn;
	}

	public int getItemsInAPage() {
		return 10;
	}

	public int getTotalPage(int page) {
		int itemsInAPage = getItemsInAPage();
		int totalCnt = articleDao.getTotalCnt(); 
		int totalPage = (int) Math.ceil((double) totalCnt / itemsInAPage);
		return totalPage;
	}

	public List<Map<String, Object>> getForPrintArticleRows(int page) {
		int itemsInAPage = getItemsInAPage();
		int limitFrom = (page - 1) * itemsInAPage;
		List<Map<String, Object>> articleRows = articleDao.getForPrintArticleRows(limitFrom ,itemsInAPage);
		return articleRows;
	}
}
