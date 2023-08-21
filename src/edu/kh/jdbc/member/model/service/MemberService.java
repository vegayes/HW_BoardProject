package edu.kh.jdbc.member.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.model.vo.Member;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();
	

	/** 1) 회원 정보 조회 서비스
	 * @param loginMember 
	 * @return
	 */
	public Member selectMem(Member loginMember) throws Exception{
		
		Connection con = getConnection();
		
		Member mem = dao.selectMem(con, loginMember);
		
		close(con);
		
		
		return mem;
	}

	
	/** 2) 회원 목록 조회 서비스
	 * @return
	 */
	public List<Member> selectMembers() throws Exception{
		
		Connection con = getConnection();
		
		List<Member> membersMap = dao.selectMembers(con);
		
		close(con);

		return membersMap;
	}

/*
	public Map<String, String> selectMembers() throws Exception{

		Connection con = getConnection();
		
		Map<String, String> membersMap = dao.selectMembers(con);
		
		close(con);

		return membersMap;
	}
*/
	
	
	
	
	/** 3) 나의 정보 수정 서비스
	 * @param loginMember
	 * @param reGender 
	 * @param reName 
	 * @return
	 */
	public int updateMem(Member loginMember, String reName, String reGender) throws Exception{

		Connection con  = getConnection();
		
		int updateMem = dao.updateMem(con, loginMember, reName, reGender);
		
		if(updateMem > 0 ) commit(con);
		else 			   rollback(con);
		
		
		close(con);
		
		return updateMem;
	}


	/** 4) 비밀번호 변경 서비스
	 * @param loginMember
	 * @param updatePw
	 * @return
	 * @throws Exception
	 */
	public int updatePw(Member loginMember, String pw) throws Exception{
		
		Connection con = getConnection();
		
		int checkNum = dao.updatePw(con,loginMember, pw);
		
		if(checkNum != 0) commit(con);
		else		 rollback(con);
		
		
		
		close(con);
		
		return checkNum;
	}



	public int updatePw2(Member loginMember, String updatePw) throws Exception {
		Connection con = getConnection();
		
		int result = dao.updatePw2(con, loginMember.getMemberId(), updatePw);
		
		if(result != 0 ) commit(con);
		else 			 rollback(con);
		
		
		close(con);	
		
		return result;
	}


	public int secession(Member loginMember) throws Exception {

		Connection con = getConnection();
		
		int result = dao.secession(con, loginMember.getMemberId());
		
		if(result != 0 ) commit(con);
		else 			 rollback(con);

		close(con);	
		
		return result;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
