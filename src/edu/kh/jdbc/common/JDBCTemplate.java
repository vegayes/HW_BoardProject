package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	/* DB 연결 (Connection 생성) , 
	 * 자동 커밋 off, 
	 * 트랜잭션 제어, 
	 * JDBC 객체 자원 반환(close)
	 * 
	 * 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	 * 
	 * * 모든 필드, 메서드가 static *
	 * -> 별도 객체 생성 X
	 * -> 어디서든지 클래스명.필드명 / 클래스명.메서드명  호출 가능 
	 */
	
	// static.. 
	private static Connection con = null;

	/** 
	 * DB  연결 정보를 담고 있는 Connection 객체 생성 및 반환 메서드
	 * @return
	 */
	public static Connection getConnection() {
		
		try {
			// 현재 커넥션 객체가 없을 경우  -> 1) 새 커넥션 객체 생성
			if(con == null || con.isClosed()) {
				// con.isClosed() : 커넥션이 close 상태면 true 반환 
				
				Properties prop = new Properties();
				// Map<String, String> 형태의 객체, XML 입출력 특화
				
				
				// driver.xml 파일 읽어오기 
				
				prop.loadFromXML(new FileInputStream("driver.xml"));
				// -> XML 파일에 작성된 내용이 Properties 객체에 모두 저장됨.
				
				
				// XML에서 읽어온 값을 모두 변수에 저장 
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String pw = prop.getProperty("pw");
				
				// 커넥션 생성
				Class.forName(driver); // Oracle JDBC Driver 객체 메모리 로드
				
				// DriverManager를 이용해 Connection 객체 생성
				con = DriverManager.getConnection(url,user,pw);
				
				
				
				// 2) 자동 커밋 비활성화
				con.setAutoCommit(false);
				
			}
			
			
			
			
			
		}catch(Exception e) {
			System.out.println("[Connection 생성 중 예외 발생]");
			e.printStackTrace();
		}
		
		
		return con;
		
	}
	
	
	
	/** 
	 * 커넥션 객체 자원 반환 메서드
	 */
	public static void close(Connection con) {
		try {
			
			// 전달 받은 con이 
			// 참조하는 Connection 객체가 있는 상태고 
			// 그 Connection 객체가 close 상태가 아니라면
			// 그때 Close 해라.
			if(con!= null && !con.isClosed()) con.close();	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** statement(부모), PrearedStatement(자식) 객체 자원 반환 메서드
	 * 						다형성 적용됨.
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** ResultSet 객체 자원 반환 메서드.
	 * @param rs
	 */
	public static void close(ResultSet rs) {
	
		try {
			
			if(rs != null && !rs.isClosed()) rs.close();
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}	
	}
	

	// 왜 닫는 기능 다 넣지 않고 따로 만드나? --> 메서드에는 하나의 기능만 넣자!
	
	/** 트랜잭션 Commit 메서드
	 * @param con
	 */
	public static void commit(Connection con) {
		
		try {
		
			if( con != null && !con.isClosed()) con.commit();
			// con안에 무언가가 있고 , 닫히지 않았을 때, 
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	/** 트랜잭션 Rollback 메서드 
	 * @param con
	 */
	public static void rollback(Connection con) {
		try {
			
			if( con != null && !con.isClosed()) con.rollback();
			// con안에 무언가가 있고 , 닫히지 않았을 때, 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
