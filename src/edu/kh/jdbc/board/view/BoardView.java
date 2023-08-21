package edu.kh.jdbc.board.view;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.common.Session;

public class BoardView {
	
	private Scanner sc = new Scanner(System.in);
	
	private BoardService service = new BoardService();
	
	private CommentView comView = new CommentView();
	
	
	public int boardMenu() {
		
		int boardNum = 0;
		
		do {
			try {
				System.out.println("\n============ 게시판 기능 메뉴 ============\n ");
				System.out.println(" 1. 게시글 목록 조회");
				System.out.println(" 2. 게시글 상세 조회(+ 댓글 기능)");
				System.out.println(" 3. 게시글 작성");
				System.out.println(" 4. 게시글 검색");
				System.out.println(" 5. 게시글 삭제");
				System.out.println(" 9. 이전(로그인) 메뉴로 돌아가기 ");
				System.out.println(" 0. 프로그램 종료 ");
				
				System.out.print("\n 메뉴 선택 > ");
				boardNum = sc.nextInt();
				sc.nextLine();
				
				switch(boardNum) {
				case 1: selectBoard(); break;
				case 2: detailSelect(); break;
				case 3: writeBoard(); break;
				case 4: searchBoard(); break;
				case 5: deleteBoard(); break;
				case 9: System.out.println("↩ 이전 메뉴로 돌아가기"); break;
				case 0 : System.out.println("\n** 프로그램이 종료 되었습니다.** \n");
				     	System.exit(0); // JVM 강제 종료 구문
					break;			
				default : System.out.println("\n***** 메뉴에 있는 숫자를 입력해주세요! *****"); break;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}while(boardNum != 9);
		
		return boardNum;
	}









	/** 1) 게시물 목록 조회
	 * @throws Exception
	 */
	private void selectBoard() throws Exception {

		System.out.println("\n============ 게시물 목록 조회 ============\n ");

		List<Board> boardList = service.selectBoard();
		
		System.out.println("No|        제목        |      작성일       | 조회 수"); // 작성자?
		
		for(Board temp : boardList) {
			
			System.out.print(temp.getBoardNo() +"   \t" + temp.getBoardTitle() +" \t"  + temp.getCreateDt() +"\t"  /*+ temp.getBoardNo() */+ temp.getReadCount());
			System.out.println();
		}	
		
	}


	/** 2) 게시글 상세 조회
	 * @throws Exception
	 */
	private void detailSelect() throws Exception {

		selectBoard();

		System.out.println("\n============ 게시글 상세 조회 ============\n ");
		
		System.out.print("게시물 번호 >> ");
		int index = sc.nextInt();
			
		Board content = service.detailContent(index);		
		printOne(content);
	
		if(content != null) {
		
				comView.CommentStart(index);
		}
		
	}

	
	
	
	
	/** 3) 게시글 작성
	 * @throws Exception
	 */
	private void writeBoard() throws Exception {
		
		System.out.println("\n============ 게시글 작성 ============\n ");

		
		System.out.print("제목 :");
		String title = sc.nextLine();
		
		System.out.print("내용 :");
		String content = sc.nextLine();
	
		int write = service.writeBoard(Session.loginMember, title, content);
		
		if(write > 0 ) {
			
			System.out.println("게시글 작성이 완료되었습니다.");
		}else {
			System.out.println("게시글 작성 중 오류가 발생하였습니다.");
		}
		
	}
	
	
	
	
	/** 4) 게시글 검색
	 * @throws Exception
	 */
	private void searchBoard() throws Exception {  // 제목, 내용

		List<Board> search;
		System.out.println("\n============ 게시글 찾기 ============\n ");

		System.out.println("1. 제목으로 찾기");
		System.out.println("2. 작성자로 찾기");
		System.out.println("3. 내용으로 찾기");
		
		System.out.print(">> 선택 :");
	
		int check = sc.nextInt();

		sc.nextLine();
		
		if( check == 1) {
			System.out.print("\n 제목 :");
			String title = sc.nextLine();
			
			search = service.searchBoard(check, title);
			
		}else if ( check == 2) {
			System.out.print("작성자 :");
			String writer = sc.nextLine();

			search = service.searchBoard(check, writer);
				
		}else {
			System.out.print("내용 :");
			String content = sc.nextLine();

			search = service.searchBoard(check, content);		
		}
		
		printAll(search);
		
	}




	private void deleteBoard() throws Exception{

		System.out.println("\n============ 게시글 삭제 ============\n ");

		System.out.print("삭제 할 게시글의 번호 :");
		int titleNo = sc.nextInt();
		
		Board checkNumber = service.checkNumber(titleNo);
		
		if(checkNumber.getMemberNo() == Session.loginMember.getMemberNo()) {
			System.out.println(checkNumber.getMemberNo());
			
			System.out.print("비밀번호를 입력해주세요 :");
			String pw = sc.next();
			
			sc.nextLine();
			
			
			if(Session.loginMember.getMemberPw().equals(pw)) {
				
				System.out.print("삭제를 진행하시겠습니까? (Y/N) >");
				char check = sc.next().toUpperCase().charAt(0);
				
				if(check == 'Y') {
					
					int deleteBoard = service.deleteBoard(titleNo, check);
					
					if(deleteBoard > 0) {
						System.out.println("삭제를 완료하였습니다.");
					}else{
						System.out.println("삭제 진행 중 오류가 발생하였습니다.");
					}
					
				}else {
					System.out.println("삭제 진행을 취소하겠습니다.");
				}
			}
		}else {
			System.out.println("게시글 삭제에 대한 권한이 존재하지 않습니다.\n다시 확인해주세요");
		}
		
		

	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//  보조 기능
	public void printOne( Board board) {
		
		if(board == null) {
			System.out.println("조회된 게시글의 정보가 없습니다.");
			
		} else {
			System.out.println("No |      제목       |             내용              |        작성일        |   조회수" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			System.out.printf(" %2d| %15s | %20s | %20s | %d\n",
					board.getBoardNo(), board.getBoardTitle(), board.getBoardContent(), board.getCreateDt(),board.getReadCount());
		}
		
	}
	
	public void printAll(List<Board> boardList) {
		
		if(boardList.isEmpty()) {
			System.out.println("조회된 게시글의 정보가 없습니다.");
			
		} else {
			System.out.println("No |      제목       |             내용              |        작성일        |   조회수" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			for(Board temp : boardList) {
				System.out.printf(" %2d| %15s | %20s | %20s | %d\n",
						temp.getBoardNo(), temp.getBoardTitle(), temp.getBoardContent(), temp.getCreateDt(),temp.getReadCount());
			}
		}
		
	}
	
	

	
	
	
	

}
