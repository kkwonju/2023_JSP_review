package com.kkwo.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.kkwo.JAM.dao.MemberDao;

public class MemberService {
	private MemberDao memberDao;
	private Connection conn;

	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
		this.conn = conn;
	}

	public int getMemberCntByLoginId(String loginId) {
		int cnt = memberDao.getMemberCntByLoginId(loginId);
		return cnt;
	}
	
	public Map<String, Object> getMemberRowByLoginId(String loginId) {
		Map<String, Object> memberRow = memberDao.getMemberRowByLoginId(loginId);
		return memberRow;
	}

	public void joinMember(String loginId, String loginPw, String name) {
		memberDao.joinMember(loginId, loginPw, name);

	}
}
