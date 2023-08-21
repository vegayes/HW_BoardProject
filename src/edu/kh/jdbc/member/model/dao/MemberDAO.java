package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.model.vo.Member;

public class MemberDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public MemberDAO() {
		
		try {
			 prop = new Properties();
			 prop.loadFromXML(new FileInputStream("member-sql.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/** 1) 회원정보 조회 DAO
	 * @param loginMember 
	 * @param con 
	 * @return
	 */
	public Member selectMem(Connection con, Member loginMember) throws Exception {
		
		Member mem = null;
		
		try {
			
			String sql = prop.getProperty("selectMem");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, loginMember.getMemberId());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String memberId = rs.getString("MEMBER_ID");
				String memberNm = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDt = rs.getString("ENROLL_DT");
				
				mem = new Member();
				
				mem.setMemberId(memberId);
				mem.setMemberNM(memberNm);
				mem.setMemberGender(memberGender);
				mem.setEnrollDt(enrollDt);
			}
			
		}finally {
			
			close(rs);
			close(pstmt);
			
		}

		return mem;
	}



	/** 3) 회원정보 수정
	 * @param con
	 * @param loginMember
	 * @param reName
	 * @param reGender
	 * @return
	 * @throws Exception
	 */
	public int updateMem(Connection con, Member loginMember, String reName, String reGender) throws Exception {
		
		int result = 0 ;
		
		try {
			String sql = prop.getProperty("updateMem");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, reName);
			pstmt.setString(2, reGender);
			pstmt.setString(3, loginMember.getMemberId());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			
			close(pstmt);
		}

		return result;
	}



	public List<Member> selectMembers(Connection con) throws Exception{
		
		List<Member> members = new ArrayList<Member>();

		try {
			String sql = prop.getProperty("selectMembers");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String memId = rs.getString("MEMBER_ID");
				String memNm = rs.getString("MEMBER_NM");
				String memGender = rs.getString("MEMBER_GENDER");
				
				Member mem = new Member();
				
				mem.setMemberId(memId);
				mem.setMemberNM(memNm);
				mem.setMemberGender(memGender);
				
				members.add(mem);
			}
			
			
			
		}finally {
			close(rs);
			close(stmt);
		}

		return members;
	}



	public int updatePw(Connection con, Member loginMember, String pw) throws Exception{
		
		int result = 0;
		try {
			String sql = prop.getProperty("updatePw");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,loginMember.getMemberId());
			pstmt.setString(2,pw);
			
			result =pstmt.executeUpdate();
			
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return result;
	}

	public int updatePw2(Connection con, String memberId, String updatePw)throws Exception {
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updatePw2");
			
			pstmt = con.prepareStatement(sql);
			

			pstmt.setString(1, updatePw);
			pstmt.setString(2, memberId);
			
			
			result=pstmt.executeUpdate();
			
		}finally {
		
			close(pstmt);
			
		}
		
		return result;
	}



	public int secession(Connection con, String memberId) throws Exception{
		
		int result = 0;
		
		String check = "Y";
		
		try {
			
			String sql= prop.getProperty("secession");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, check);
			pstmt.setString(2,memberId);
			
			result = pstmt.executeUpdate();
		}finally {
			
			close(pstmt);
		}
	
		return result;
	}



/*	public Map<String, String> selectMembers(Connection con) throws Exception {

		Map<String, String> members = new LinkedHashMap<String, String>();
		
		int count = 0;
		try {
			String sql = prop.getProperty("selectMembers");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String memId = rs.getString("MEMBER_ID");
				String memNm = rs.getString("MEMBER_NM");
				String memGender = rs.getString("MEMBER_GENDER");
				
				members.put("MEMBER_ID", memId);
				members.put("MEMBER_Name", memNm);
				members.put("MEMBER_Gender", memGender);
				System.out.println(count);
				
				count++;
				
			}
			
			
			
		}finally {
			close(rs);
			close(stmt);
		}

		return members;
	}
	*/
	
	
	
	

}
