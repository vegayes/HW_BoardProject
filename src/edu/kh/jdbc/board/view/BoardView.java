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
//				System.out.println(" 5. 게시글 삭제");
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
//				case 5: deleteBoard(); break;
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
		
		System.out.println("No|        제목        |        작성일        |     작성자    |    조회 수    |   댓글 수 "); 
		
		for(Board temp : boardList) {
			System.out.print(temp.getBoardNo() +"   \t  " + temp.getBoardTitle() +"  \t  "  + temp.getCreateDt() +"\t    "  
			+ temp.getMemberName() +" \t\t" + temp.getReadCount()+"  \t\t"+temp.getCommentCount());
			System.out.println();
		}			
	}


	/** 2) 게시글 상세 조회  [ 댓글 기능 추가된 것 ]

	 *        
	 * @throws Exception
	 */
	private void detailSelect() {

		try {
		selectBoard();

		System.out.println("\n============ 게시글 상세 조회 ============\n ");
		
		System.out.print("게시물 번호 >> ");
		int index = sc.nextInt();
		
		
		int updateReadCount = service.updateReadCount(index, Session.loginMember.getMemberNo());
		Board content = service.detailContent(index);		
		
		
		
		// 선생님 풀이
//		Board content = service.selectBoard(index, Session.loginMember.getMemberNo()); 
		printOne(content);
	
		if(content != null) {
		
				comView.CommentStart(index);
		}else {
			
			System.out.println("해당 게시글이 존재하지 않습니다.\n");
			
		}
		}catch(Exception e) {
			System.out.println("\n****** 게시글 상세 조회 중 예외 발생 ******\n");
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 * 2) 선생님 풀이. 
	 * 	 1. 게시글 번호 입력
	 *     1) 번호가 일치하는 게시글이 있으면 조회
	 *         -> 조회수 증가(단, 자신이 작성한 게시글일 경우 조회수 증가 X) 
	 *         ->  +++ 자신이 작성한 게시글이 경우 수정/삭제 기능 노출
	 *     2) 번호가 일치하는 게시글이 없으면
	 *     	   -> 해당 게시글이 존재하지 않다고 출력
	 */
	private void answerSelect() throws Exception{
		
		try {
			
			selectBoard();

			System.out.println("\n============ 게시글 상세 조회 ============\n ");
			
			System.out.print("게시물 번호 >> ");
			int index = sc.nextInt();
			
			Board content = service.selectBoard(index, Session.loginMember.getMemberNo()); 
			
			
			if(content == null) {
				System.out.println("\n 해당 게시글이 존재하지 않습니다.\n");
				return;
			}
			
			System.out.println("--------------------------------------------------------");
			System.out.printf("글번호 : %d \n제목 : %s\n", content.getBoardNo(), content.getBoardTitle());
			System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n", 
					content.getMemberName(), content.getCreateDt(), content.getReadCount());
			System.out.println("--------------------------------------------------------\n");
			System.out.println(content.getBoardContent());
			System.out.println("\n--------------------------------------------------------");
			
			
			//로그인한 회원이 작성한 게시글일 경우
			// 게시글 수정/삭제 기능 노출
			
			if(Session.loginMember.getMemberNo() == content.getMemberNo()) {
				
				while(true) {
					System.out.println("1) 수정");
					System.out.println("2) 삭제");
					System.out.println("0) 게시판 메뉴로 돌아가기");
					
					System.out.print("선택 >> ");
					
					index = sc.nextInt();
					sc.nextLine();
					
					// 기능 수행 후 게시판 메뉴로 돌아가기
					switch(index) {
					case 1 : updateBoard(content.getBoardNo()); return;
							// 게시글 번호를 매개변수로 전달
					
					case 2 : deleteBoard(content.getBoardNo()); return;
							// 게시글 번호를 매개변수로 전달
					
					case 0 : return;
					default : System.out.println("\n*** 잘못 입력 하셨습니다 ***\n");
					}
				}
				
			}
			
			
		}catch(Exception e) {	
			System.out.println("\n게시글 상세 조회 중 예외 발생\n");
			e.printStackTrace();
		}		
	}
	




	/** 게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int boardNo){

		System.out.println("\n============ 게시글 수정 ==============\n");
		
		System.out.println("수정할 제목 : ");
		String boardTitle = sc.nextLine();
				
		StringBuffer sb = new StringBuffer();
		
		System.out.println("<!wq 입력 시 종료>");
		// vi : cmd의 editor  ==> Linux에서 씀. 
		// !wq 특정 단어가 입력될때까지 무한히 입력
		
		while(true) {
			
			String str = sc.nextLine();
			// 안녕 
			//  하세요
			
			if(str.equals("!wq")) break;
			
			sb.append(str);
			sb.append("\n");
			// 안녕
			//  하세요 
			
			
		}
		
		try {
			// 게시글 수정 서비스 호출
			
			int result = service.updateBoard(boardTitle, sb.toString(), boardNo);
			
			if(result > 0 ) {
				System.out.println("\n=========== 게시글이 수정 되었습니다. ============\n");
			}else {
				System.out.println("\n=========== 수정 실패 =============\n");
			}
			
			
			
			
		}catch(Exception e) {
			System.out.println("\n*************게시글 수정 중 예외 발생 ******************\n");
			e.printStackTrace();
		}
	}

	
	
	private void deleteBoard(int boardNo) {


		System.out.println("\n============ 게시글 삭제 ==============\n");
		
		while(true) {
			
			System.out.println("정말 삭제 하시겠습니까? (Y/N) :");
			
			char check = sc.next().toUpperCase().charAt(0);
			
			if(check == 'N'){
				System.out.println("삭제 취소");
				return;
			}
			
			if(check != 'Y') {
				
				System.out.println("잘못 입력하셨습니다.");
				continue;
				
			}
			
			System.out.println("삭제 성공");
			break; // check가 Y인 경우
		}
		
		try {
			// 게시글 삭제 서비스 호출
			
			int result = service.delBoard(boardNo);
			
			if(result > 0) {
				System.out.println("\n====삭제되었습니다.====\n");
			}else {
				System.out.println("\n====삭제 실패====\n");
			}

		}catch(Exception e) {
			System.out.println("\n***게시글 삭제 중 예외 발생***\n");
			e.printStackTrace();
		}
		
		
		
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
	
	
	
	private void writeBoard2() throws Exception {
		
		System.out.println("\n============ 게시글 삭제 ==============\n");
		
		System.out.println("수정할 제목 : ");
		String boardTitle = sc.nextLine();
				
		StringBuffer sb = new StringBuffer();
		
		System.out.println("<!wq 입력 시 종료>");
		// vi : cmd의 editor  ==> Linux에서 씀. 
		// !wq 특정 단어가 입력될때까지 무한히 입력
		
		while(true) {
			
			String str = sc.nextLine();
			// 안녕 
			//  하세요
			
			if(str.equals("!wq")) break;
			
			sb.append(str);
			sb.append("\n");
			// 안녕
			//  하세요 
	
		}
		
		try {
			// 게시글 등록 서비스 호출
			
			int result = service.inserBoard(boardTitle, sb.toString(), Session.loginMember.getMemberNo());
			
			if(result > 0 ) {
				System.out.println("\n=========== 게시글을 등록하였습니다.============\n");
				
				Board board = service.selectBoard(result, Session.loginMember.getMemberNo());
				
				
				System.out.println("--------------------------------------------------------");
				System.out.printf("글번호 : %d \n제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
				System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n", 
						board.getMemberName(), board.getCreateDt(), board.getReadCount());
				System.out.println("--------------------------------------------------------\n");
				System.out.println(board.getBoardContent());
				System.out.println("\n--------------------------------------------------------");
				
				
				
			}else {
				System.out.println("\n=========== 등록 실패 =============\n");
			}
			
			
			
			
		}catch(Exception e) {
			System.out.println("\n*************게시글 등록 중 예외 발생 ******************\n");
			e.printStackTrace();
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
			System.out.print("\n제목 :");
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


	
	
	//  보조 기능
	public void printOne( Board board) {
		
		if(board == null) {
			System.out.println("조회된 게시글의 정보가 없습니다.");
			
		} else {
			System.out.println("No|        제목        |        작성일        |     작성자    |    조회 수    |   댓글 수 "); 
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			
			System.out.printf(" %2d| %15s | %20s | %20s | %d\n",
					board.getBoardNo(), board.getBoardTitle(), board.getBoardContent(), board.getCreateDt(),
					board.getMemberName(), board.getReadCount(), board.getCommentCount());
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
						temp.getBoardNo() +"   \t  " + temp.getBoardTitle() +"  \t  "  + temp.getCreateDt() +"\t    "  
								+ temp.getMemberName() +" \t\t" + temp.getReadCount()+"  \t\t"+temp.getCommentCount());
			}
		}
		
	}
	
	

	
	
	
	

}
