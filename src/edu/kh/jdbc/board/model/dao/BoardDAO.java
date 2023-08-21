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
				String createDt = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
						
				Board board = new Board();
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setCreateDt(createDt);
				board.setReadCount(readCount);
				
				boards.add(board);
			}

		}finally {
			close(rs);
			close(stmt);
		}
		
		return boards;
	}

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



	
	
	
	
	
	
	
	

}
