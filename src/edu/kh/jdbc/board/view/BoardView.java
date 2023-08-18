package edu.kh.jdbc.board.view;

import java.util.Scanner;

import edu.kh.jdbc.common.Session;

public class BoardView {
	
	private Scanner sc = new Scanner(System.in);
	
	public int boardMenu() {
		
		int boardNum = 0;
		
		do {
			try {
				System.out.println("\n============ 게시판 기능 메뉴 ============\n ");
				System.out.println(" 1. 게시글 목록 조회");
				System.out.println(" 2. 게시글 상세 조회(+ 댓글 기능)");
				System.out.println(" 3. 게시글 작성");
				System.out.println(" 4. 게시글 검색");
				System.out.println(" 9. 이전(로그인) 메뉴로 돌아가기 ");
				System.out.println(" 0. 프로그램 종료 ");
				
				System.out.print("\n 메뉴 선택 > ");
				boardNum = sc.nextInt();
				sc.nextLine();
				
				switch(boardNum) {
				case 1:  break;
				case 2:  break;
				case 3:  break;
				case 4:  break;
				case 9:  break;
				case 0 :  break;			
				default : System.out.println("\n***** 메뉴에 있는 숫자를 입력해주세요! *****"); break;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}while(boardNum != 0 && boardNum != 9);
		
		return boardNum;
	}
	
	
	
	
	
	
	
	
	
	
	

}
