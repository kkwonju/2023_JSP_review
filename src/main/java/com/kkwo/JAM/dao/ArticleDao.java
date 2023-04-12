package com.kkwo.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

public class ArticleDao {
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int getTotalCnt() {
		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article");

		int cnt = DBUtil.selectRowIntValue(conn, sql);
		return cnt;
	}
	
	public int getArticleId(String title, String body, int memberId) {
		SecSql sql = SecSql.from("INSERT INTO article ");
		sql.append("SET regDate = NOW(),");
		sql.append(" title = ?,", title);
		sql.append(" `body` = ?,", body);
		sql.append(" memberId = ?", memberId);
		
		int articleId = DBUtil.insert(conn, sql);
		return articleId;
	}
	
	public Map<String, Object> getArticleRowById(int id) {
		SecSql sql = SecSql.from("SELECT * ");
		sql.append("FROM article ");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		return articleRow;
	}
	
	public Map<String, Object> getForPrintArticleRow(int id) {
		SecSql sql = SecSql.from("SELECT A.*, M.name AS 'extra__writer'");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.id = ?;", id);
		
		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		return articleRow;
	}

	public List<Map<String, Object>> getForPrintArticleRows(int limitFrom, int itemsInAPage) {
		SecSql sql = SecSql.from("SELECT A.*, M.name AS 'extra__writer'");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("ORDER BY A.id DESC");
		sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);
		
		List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
		return articleRows;
	}

	public void modifyArticle(String title, String body, int id) {
		SecSql sql = SecSql.from("UPDATE article ");
		sql.append("SET regDate = NOW(),");
		sql.append(" title = ?,", title);
		sql.append(" `body` = ?", body);
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(conn, sql);		
	}

	public void deleteArticle(int id) {
		SecSql sql = SecSql.from("DELETE ");
		sql.append("FROM article");
		sql.append("WHERE id =?", id);

		DBUtil.delete(conn, sql);		
	}
}
