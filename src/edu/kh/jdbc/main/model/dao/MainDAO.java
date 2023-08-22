package edu.kh.jdbc.main.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.vo.Member;

public class MainDAO {
	
	// JDBC 객체 참조 변수
	private Statement stmt; // SQL 수행, 결과 반환
	private PreparedStatement pstmt; // placeholder를 포함한 SQL 세팅/ 수행
	private ResultSet rs; // SELECT 수행 결과 저장
	
	private Properties prop;
	// - Map<String, String> 형태
	// - XML 파일 입/출력 메서드를 제공
	
	
	public MainDAO() {
		// DAO 객체가 생성될 때 XML 파일을 읽어와 Properties 객체에 저장
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-sql.xml"));
			
			// ************************
			
		}catch(Exception e) {
			// InputStream 때문에 IO예외 발생
			e.printStackTrace();
		}
	}
	

	/** 1. 로그인 
	 * @param con 
	 * @param logId
	 * @param logPw
	 * @return
	 */
	public Member login(Connection con, String logId, String logPw) throws Exception {

		// 1. 결과 저장용 변수 선언
		Member mem = null;
		
		try {
			
			String sql = prop.getProperty("login");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, logId);
			pstmt.setString(2, logPw);
			
			rs = pstmt.executeQuery(); // SELECT 수행 후 결과 반환 받기
			
			
			while(rs.next()) {
				
//				String id = rs.getString("MEMBER_ID");
				// 입력받은 아이디 == 조회한 아이디
				// -> DB에서 얻어올 필요가 없음.
				
//				String pw = rs.getString("MEMBER_PW");
				
				
				int memberNo = rs.getInt("MEMBER_NO");				
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DT");
				
				
				mem = new Member();
				
				mem.setMemberNo(memberNo);
				mem.setMemberId(logId);
				mem.setMemberPw(logPw);
				mem.setMemberNM(memberName);
				mem.setMemberGender(memberGender);
				mem.setEnrollDt(enrollDate);
				
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		return mem;
	}


	
	/** 2-1). 회원가입 아이디 중복 확인 DAO
	 * @param con
	 * @param signId
	 * @return
	 * @throws Exception
	 */
	public int checkId(Connection con, String signId) throws Exception {

		int result = 0 ;
		
		try {
			
			String sql = prop.getProperty("checkId");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, signId);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(rs);
			close(stmt);
		}

		return result;
	}
	
	/**  선생님 :: 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return
	 */
/*	public int idDuplicationCheck(Connection conn, String memberId) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("idDuplicationCheck");
			
			pstmt = conn.prepareStatement(sql);    
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery(); // + Count로 받음.
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
*/	
	
	/** 2-2) 회원가입 정보 삽입 DAO
	 * @param con
	 * @param signId
	 * @param signPw
	 * @param signName
	 * @param signGender
	 * @return
	 * @throws Exception
	 */
	public int signUp(Connection con, String signId, String signPw, String signName, String signGender) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, signId);
			pstmt.setString(2, signPw);
			pstmt.setString(3, signName);
			pstmt.setString(4, signGender);
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
			
		}
		return result;
	}



	/** 선생님 :: 회원 가입 SQL 수행( INSERT )
	 * @param conn
	 * @param member
	 * @return
	 */
/*	public int signUp(Connection conn, Member member) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}
	
*/	
	
	
	
	

}
