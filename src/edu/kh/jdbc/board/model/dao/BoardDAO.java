package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.member.model.vo.Member;

import static edu.kh.jdbc.common.JDBCTemplate.*;


public class BoardDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public BoardDAO() {
		
		try {
			 prop = new Properties();
			 prop.loadFromXML(new FileInputStream("board-sql.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/** 1) 게시글 조회 DAO
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<Board> selectBoard(Connection con) throws Exception{
		
		List<Board> boards= new ArrayList<Board>();
		
		try{
			
			String sql = prop.getProperty("selectBoard");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);

			
			while(rs.next()) {
				
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String memberName = rs.getString("MEMBER_NM");
				String createDt = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
				int commentCount = rs.getInt("COMMENT_COUNT");
						
				Board board = new Board();
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setCreateDt(createDt);
				board.setReadCount(readCount);
				board.setMemberName(memberName);
				board.setCommentCount(commentCount);
				
				
				boards.add(board);
			}

		}finally {
			close(rs);
			close(stmt);
		}
		
		return boards;
	}

	/** 2) 게시글 상세조회 DAO 
	 * @param con
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public Board detailContent(Connection con, int index) throws Exception {
		
		Board content = null;
		
		try {
			
			String sql = prop.getProperty("detailContent");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, index);
		
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String createDt = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
						
				content = new Board();
				
				content.setBoardNo(boardNo);
				content.setBoardTitle(boardTitle);
				content.setCreateDt(createDt);
				content.setReadCount(readCount);

			}

		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		return content;
	}
	
	/** 2) 게시글 조회 -> 조회수  
	 * @param con
	 * @param index
	 * @param no
	 * @return
	 * @throws Exception
	 */
	public int updateReadCount(Connection con, int index, int no)throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updateReadCount");
			
			pstmt =con.prepareStatement(sql);
						
			
			pstmt.setInt(1, index);
			
			result = pstmt.executeUpdate();

			
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 2) 게시글 조회  [ 선생님 풀이 ]
	 * @param con
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public Board selectBoard(Connection con, int index) throws Exception{

		Board board = null;
		
		try {
			String sql = prop.getProperty("selectBoard2");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, index);
			
			rs = pstmt.executeQuery();

			
			while(rs.next()) {
				
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String memberName = rs.getString("MEMBER_NM");
				String createDt = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
				int commentCount = rs.getInt("COMMENT_COUNT");
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setCreateDt(createDt);
				board.setReadCount(readCount);
				board.setMemberName(memberName);
				board.setCommentCount(commentCount);
			
				}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return board;
	}
	
	

	/** 3) 게시글 작성 DAO
	 * @param con
	 * @param loginMember
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public int writeBoard(Connection con, Member loginMember, String title, String content) throws Exception{
		
		int writeResult = 0;
		
		try {
			
			String sql = prop.getProperty("writeBoard");
			
			pstmt =con.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, loginMember.getMemberNo());
			
			writeResult = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		return writeResult;
	}


	/** 4) 게시글 검색 DAO
	 * @param con
	 * @param check
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public List<Board> searchBoard(Connection con, int check, String title) throws Exception{

		List<Board> search = new ArrayList<Board>();
		String sql;
		try {
			
			if(check == 1) {
				sql = prop.getProperty("searchBoard");
			}else if (check == 2) {
				sql = prop.getProperty("searchWriter");
			}else {
				sql = prop.getProperty("searchContent");
			}
			
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, "%"+title+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String boardContent = rs.getString("BOARD_CONTENT");
				String createDt = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
				
				Board write = new Board();
				
				write.setBoardNo(boardNo);
				write.setBoardTitle(boardTitle);
				write.setBoardContent(boardContent);
				write.setCreateDt(createDt);
				write.setReadCount(readCount);
				
				search.add(write);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}

		return search;
	}


	public Board checkNumber(Connection con, int titleNo) throws Exception {
		
		Board checkNum = null;
		
		try {
			
			String sql = prop.getProperty("checkNumber");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, titleNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int checkMem = rs.getInt(titleNo);
				
				checkNum = new Board();
				
				checkNum.setMemberNo(checkMem);
				
			}
			
			
			
		}finally {
			
			close(rs);
			close(pstmt);
		}
		
		return checkNum;
	}


	public int deleteBoard(Connection con, int titleNo, char check) throws Exception{

		int result = 0;
		String checks= String.valueOf(check);
		
		try {
			
			String sql = prop.getProperty("deleteBoard");
			
			pstmt =con.prepareStatement(sql);
						
			
			pstmt.setString(1, checks);
			pstmt.setInt(2, titleNo);
			
			result = pstmt.executeUpdate();

			
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	public int updateBoard(Connection con, String boardTitle, String boardContent, int boardNo) throws Exception{

		int result = 0;
		try {
			
			String sql = prop.getProperty("updateBoard");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardNo);
			
			result = pstmt.executeUpdate();

			
		}finally {
			
			close(pstmt);
		}
		return result;
	}


	/** 
	 * @param con
	 * @param boardNo
	 * @return
	 */
	public int delBoard(Connection con, int boardNo) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("delBoard");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	public int nextBoardNo(Connection con) throws Exception{
		
		int boardNo = 0;
		try {
			String sql = prop.getProperty("nextBoardNo");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				boardNo = rs.getInt(1); 
				
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		
		return boardNo;
	}


	public int insertBoard(Connection con, String boardTitle, String boardContent, int memberNo, int boardNo) throws Exception {
		
		int result = 0;
		
	
		try {
			String sql = prop.getProperty("insertBoard");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.setInt(4, memberNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}

		
		
		
		
		return result;
	}



	
	
	
	
	// sql 합쳐 쓸 수 있음.
	/*
	 * String sql = prop.getProperty("searchBoard1")
	 * 				+ prop.getProperty("searchBoard2_"+condition)
	 * 				+ prop.getProperty("searchBoard3")
	 * 
	 * 
	 * 
	 */
	
	

}
