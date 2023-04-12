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
}
