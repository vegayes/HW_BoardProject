package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import java.sql.Connection;

import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.model.vo.Member;

public class MainService {

	private MainDAO dao = new MainDAO();
	
	

	/** 1. 로그인 
	 * @param logId
	 * @param logPw
	 * @return
	 * @throws Exception
	 */
	public Member login(String logId, String logPw) throws Exception {
		
		Connection con = getConnection();
		
		Member mem = dao.login(con, logId, logPw);
		
		close(con);
		
		return mem;
	}


	/** 2-1). 회원가입 ID 중복 확인 서비스
	 * @param signId
	 * @return
	 */
	public int checkId(String signId) throws Exception {

		Connection con = getConnection();
		
		int checkId = dao.checkId(con,signId);
		
		close(con);
		
		return checkId;
	}
	
	

	/** 2-2). 회원가입 정보 삽입 서비스
	 * @param signId
	 * @param signPw
	 * @param signName
	 * @param signGender
	 * @return
	 * @throws Exception
	 */
	public int signUp(String signId, String signPw, String signName, String signGender) throws Exception {
		
		Connection con = getConnection();
		
		int result = dao.signUp(con,signId, signPw, signName, signGender);
		
		if(result > 0) commit(con);
		else 		   rollback(con);
	
		close(con);
		
		return result;
	}







	
	
	
	
	

}
