package com.kkwo.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

public class MemberDao {
	private Connection conn;

	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	public void joinMember(String loginId, String loginPw, String name) {
		SecSql sql = SecSql.from("INSERT INTO `member` ");
		sql.append("SET regDate = NOW(),");
		sql.append(" loginId = ?,", loginId);
		sql.append(" loginPw = ?,", loginPw);
		sql.append(" `name` = ?;", name);

		DBUtil.insert(conn, sql);		
	}

	public Map<String, Object> getMemberRowByLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT * FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRow(conn, sql);
	}

	public int getMemberCntByLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT COUNT(*) FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowIntValue(conn, sql);
	}
}
