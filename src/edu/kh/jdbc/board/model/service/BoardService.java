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






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
