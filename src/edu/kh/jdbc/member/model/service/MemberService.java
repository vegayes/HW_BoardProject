package edu.kh.jdbc.member.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.model.vo.Member;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();
	

	/** 1) 회원 정보 조회 서비스 XXXXXX [사용 X]
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

/* 2) 회원 목록 조회 서비스 [Map 이용] [사용 X]
 * 
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


	/** 4-1) 비밀번호 변경 확인 서비스
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



	/** 4-2) 비밀번호 변경 서비스
	 * @param loginMember
	 * @param updatePw
	 * @return
	 * @throws Exception
	 */
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


	/** 숫자 6자리 보안코드 생성 서비스
	 * @return
	 */
	public String createSecurityCode() {
		// 서비스의 있는 모든 것이 DAO로 가는 것 아님.
		// 기능들을 모아두는 곳
		
		StringBuffer code = new StringBuffer();  // 새로운 자료형 :: 문자열을 추가/변경할 때 주로 사용하는 자료형
		
		// StringBuffer 자료형 append 메서드를 문자열을 추가할 수 있음.
		
		
		/* 클린코드 X
		String str = "안녕";
		str += "하세요";
		*/
		/* 클린코드 O
		code.append("안녕");
		code.append("하세요");
		*/
		
		Random ran = new Random(); // 난수 생성 객체 (random 객체)
		
		for(int i = 0; i < 6; i ++) {
			
			// 6자리 append해서 넣을 것.
			
			int x = ran.nextInt(10); // 0 이상 10미만 정수 만드는 방법
			
			code.append(x);
		}
		
		return code.toString();  // 그냥 반환 X , toString이용해서 반환해야 함!
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
