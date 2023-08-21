package edu.kh.jdbc.board.model.service;

import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.vo.Member;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

public class CommentService {
	
	private CommentDAO dao = new CommentDAO();


	/** 댓글 추가
	 * @param comment
	 * @param loginMember
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public int addComment(String comment, Member loginMember, int index) throws Exception {
		
		Connection con = getConnection();
		
		int result = dao.addComment(con, comment, loginMember, index);
		
		if(result >0 ) commit(con);
		else 			rollback(con);
	
		close(con);
		
		return result;

	}


	/** 댓글 출력
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public List<Comment> showComment(int index) throws Exception{

		Connection con = getConnection();
		
		List<Comment> showCommentList = dao.showComment(con, index);
		
		close(con);
			
		return showCommentList;
	}



	/** 댓글 수정/삭제 가능확인
	 * @param index
	 * @param updateNum
	 * @return
	 * @throws Exception
	 */
	public Comment checkNo(int index, int updateNum) throws Exception {
		
		Connection con = getConnection();
		
		Comment checkNo = dao.checkNo(con,index, updateNum);
		
		close(con);

		return checkNo;
	}


	/** 댓글 수정
	 * @param checkNo
	 * @param update
	 * @return
	 * @throws Exception
	 */
	public int updateComment(int checkNo, String update) throws Exception {

		Connection con = getConnection();

		int update2 = dao.updateComment(con, checkNo, update);
		
		if(update2 > 0) commit(con);
		else		 rollback(con);
		
		close(con);
	
		return update2;
	}


	/** 댓글 삭제 서비스
	 * @param index
	 * @param deleteNum
	 * @return
	 */
	public int deleteComment(int index, int deleteNum) throws Exception{
		
		Connection con = getConnection();
		
		int deleteComment = dao.deleteComment(con,index,deleteNum);
		
		if(deleteComment >0 ) commit(con);
		else					rollback(con);
		
		return deleteComment;
	}





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
