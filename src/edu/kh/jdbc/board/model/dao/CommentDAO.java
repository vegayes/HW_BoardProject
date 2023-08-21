package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.member.model.vo.Member;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class CommentDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs ;
	
	private Properties prop;
	
	
	public CommentDAO() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-sql.xml"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/** 댓글 추가 DAO
	 * @param con
	 * @param comment
	 * @param loginMember
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public int addComment(Connection con, String comment, Member loginMember, int index)throws Exception{
		
		int result = 0;
		
		try {
			System.out.println(comment);
			System.out.println(index);

			
			
			String sql = prop.getProperty("addComment");
			System.out.println(sql);
			
			System.out.println(loginMember.getMemberNo());
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, comment );
			pstmt.setInt(2, loginMember.getMemberNo());
			pstmt.setInt(3, index);
			
			result = pstmt.executeUpdate();
			
			
			
		}finally {
			close(pstmt);
		}

		return result;
	}


	/** 댓글 출력 DAO
	 * @param con
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public List<Comment> showComment(Connection con, int index) throws Exception{

		List<Comment> showCommentList = new ArrayList<Comment>();
		
		try {
			
			String sql = prop.getProperty("showComment");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, index);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {

				int commentNum = rs.getInt("COMMENT_NO");
				String content = rs.getString("COMMENT_CONTENT");
				String memName = rs.getString("MEMBER_NM");
				
				
				Comment com = new Comment();
				

				com.setCommentNo(commentNum);
				com.setCommentContent(content);
				com.setMemberName(memName);
				
				showCommentList.add(com);
				
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}

		return showCommentList;
	}


	/** 수정/삭제 확인 DAO
	 * @param con
	 * @param index
	 * @param updateNum
	 * @return
	 * @throws Exception
	 */
	public Comment checkNo(Connection con, int index, int updateNum) throws Exception {
		
		Comment checkNo = null;
		
		try {
			
			String sql = prop.getProperty("checkNo");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, index);
			pstmt.setInt(2, updateNum);
			
			rs = pstmt.executeQuery();
		
			
			while(rs.next()) {
				
				int memNum = rs.getInt("MEMBER_NO");
				
				checkNo = new Comment();
				
				checkNo.setMemberNo(memNum);

			}

		}finally {
			close(rs);
			close(pstmt);
		}

		return checkNo;
	}


	/** 댓글 수정 DAO
	 * @param con
	 * @param checkNo
	 * @param update
	 * @return
	 * @throws Exception
	 */
	public int updateComment(Connection con, int checkNo, String update) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updateComment");
			System.out.println(sql);
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, update);
			pstmt.setInt(2, checkNo);
			
			result =pstmt.executeUpdate();

		}finally {
			
			close(pstmt);
		}

		return result;
	}


	/** 댓글 삭제 DAO
	 * @param con
	 * @param index
	 * @param deleteNum
	 * @return
	 */
	public int deleteComment(Connection con, int index, int deleteNum) throws Exception {

		int deleteComment = 0 ;
		
		try {
			
			String sql = prop.getProperty("deleteComment");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, deleteNum);
			pstmt.setInt(2, index);

			deleteComment = pstmt.executeUpdate();
			
			
		}finally {
			
			close(pstmt);
		}

		return deleteComment;
	}



	
	
	
	
	
	
	
	
	
	
	
	
	

}
