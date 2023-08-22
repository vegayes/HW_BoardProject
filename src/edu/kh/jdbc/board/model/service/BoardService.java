package edu.kh.jdbc.board.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.member.model.vo.Member;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {
	
	private BoardDAO  dao = new BoardDAO();
	

	/** 1) 게시글 조회 서비스
	 * @return
	 * @throws Exception
	 */
	public List<Board> selectBoard() throws Exception {
		
		Connection con = getConnection();
		
		List<Board> boardList = dao.selectBoard(con);
		
		close(con);
		
		return boardList;
	}


	/** 3) 게시글 작성 서비스
	 * @param loginMember
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public int writeBoard(Member loginMember, String title, String content) throws Exception {

		Connection con = getConnection();
		
		int write = dao.writeBoard(con, loginMember, title, content);
		
		if(write > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		
		return write;
	}


	/** 4)게시글 검색 서비스
	 * @param check
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public List<Board> searchBoard(int check, String title) throws Exception {

		Connection con = getConnection();
		
		List<Board> search = dao.searchBoard(con, check, title);

		close(con);
		
		return search;
	}


	public Board detailContent(int index) throws Exception {
		
		Connection con = getConnection();
		
		Board content = dao.detailContent(con, index);

		close(con);

		return content;
	}


	public Board checkNumber(int titleNo) throws Exception {

		Connection con = getConnection();
		
		Board checkNum = dao.checkNumber(con, titleNo);
		
		close(con);
		
		return checkNum;
	}


	public int deleteBoard(int titleNo, char check) throws Exception {
		
		Connection con = getConnection();
		
		int delete = dao.deleteBoard(con, titleNo, check);
		
		if(delete > 0 ) commit(con);
		else			rollback(con);
		
		close(con);
		
		return delete;
	}


	/** 조회수 증가 
	 * @param index
	 * @param No
	 * @return
	 * @throws Exception
	 */
	public int updateReadCount(int index, int No) throws Exception {

		Connection con = getConnection();
		
		int result = dao.updateReadCount(con,index, No);
		
		if(result > 0 ) commit(con);
		else			rollback(con);
		
		close(con);
		
		return result;
	}


	// 선생님 풀이
	/** 2) 선생님 풀이 
	 * @param index
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Board selectBoard(int index, int memberNo) throws Exception {

		Connection con = getConnection();
		
		Board content = dao.selectBoard(con, index);
		
		if(content != null) {
			if(content.getMemberNo() != memberNo) {
				 // 조회한 게시글 작성한 회원번호랑 로그인한 회원번호가 다를 때, 조회수 증가
				
				int result = dao.updateReadCount(con, index, memberNo);
				
				if(result > 0) {
					commit(con);
					// !동기화! 
					// 조회된 board의 조회수가 0 
					// DB에서 조회수가 1이지만, 미리 조회해둔 결과의 read_count를 1증가 해줘야 함. 
					content.setReadCount(content.getReadCount()+1);
				}
				else{
					rollback(con);
				}
			}
		}
		
		close(con);
		
		return content;
	}


	/**  게시글 수정 서비스
	 * @param boardTitle
	 * @param string
	 * @param boardNo
	 * @return
	 */
	public int updateBoard(String boardTitle, String boardContent, int boardNo) throws Exception{

		Connection con = getConnection();
		
		int result = dao.updateBoard(con,boardTitle,boardContent,boardNo);
		
		
		
		
		if(result > 0) commit(con);
		else		   rollback(con);
		
		close(con);
		
		return result;
	}


	public int delBoard(int boardNo) throws Exception {

		Connection con = getConnection();
		
		int result = dao.delBoard(con,boardNo);
		
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		
		return result;
	}




	public int inserBoard(String boardTitle, String boardContent, int memberNo) throws Exception {
		
		Connection con = getConnection();
		
		// 다음 게시글 번호 생성 ->
		int boardNo = dao.nextBoardNo(con);
		
		int result = dao.insertBoard(con, boardTitle, boardContent, memberNo, boardNo);
		
		if(result >0 ) {
			commit(con);
			result = boardNo; // result가 성공한 후에 boardNo를 넘겨주고 싶음.
		}
		else{
			rollback(con);
		}
		
	
		close(con);
		
		return result;  // 삽입 성공 시, 다음 게시글 번호 
						//      실패 시, 0 
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
