package edu.kh.jdbc.board.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.common.Session;

public class CommentView {
	
	private Scanner sc = new Scanner(System.in);

	private CommentService service = new CommentService();
	
	
	public void CommentStart(int index) throws Exception {
		
		int menuNum = 0;
		
		// 우선 댓글 하나 보여줌
	
		showComment(index);
		
		
		do {
			try {
				
				System.out.println("\n1. 댓글 추가");
				System.out.println("2. 댓글 수정");
				System.out.println("3. 댓글 삭제");
				System.out.println("9. 이전 화면으로 돌아가기");
				System.out.println("0. 종료하기\n");
				
				
				System.out.print("댓글 기능 선택 >");
				menuNum = sc.nextInt();

				switch(menuNum) {
				case 1 : addComment(index); break;
				case 2 : updateComment(index);break;			
				case 3 : deleteComment(index); break;
				case 9 :  System.out.println("↩ 이전 메뉴로 돌아가기\n"); break;
				case 0 :  System.out.println("\n** 프로그램이 종료 되었습니다.** \n");
		     			   System.exit(0); // JVM 강제 종료 구문
				default: break;
				}

		
			}catch(Exception e) {
				e.printStackTrace();
			}
		}while(menuNum != 9);	
		
	}
	
	




	// 주 기능
	/** + 댓글 내용 출력
	 * @param index
	 * @throws Exception
	 */
	private void showComment(int index) throws Exception {

		List<Comment> showCommentList = service.showComment(index);
		
		if (showCommentList == null) {
			System.out.println("댓글을 조회하는 도중 오류가 발생하였습니다.");
		}else {
			System.out.println("\n====================================== 댓글 ======================================\n");
			System.out.println(" No |    \t댓글 내용\t     |     작성자");
			
			
			for(Comment temp : showCommentList) {
				System.out.print("  "+temp.getCommentNo());
				System.out.print("       "+temp.getCommentContent() );
				System.out.print("\t\t\t" +temp.getMemberName());
				System.out.println();
				
			}
			
		}	
	}


	/**
	 * 1) 댓글 추가
	 * @param index 
	 * @throws Exception 
	 */
	private void addComment(int index) throws Exception {

		System.out.println("\n============ 댓글 추가 ============\n ");
		sc.nextLine();
		
		System.out.print("내용 : ");
		String comment = sc.nextLine();

		
		int addCom = service.addComment(comment, Session.loginMember, index);
		
		if(addCom > 0) {
			System.out.println("\n댓글이 저장되었습니다.\n");
			showComment(index);
			
		}else {
			System.out.println("\n댓글 작성 중 오류가 발생하엿습니다.\n");
		}	
		
	}
	
	
	/** 2) 댓글 수정
	 * @param index
	 */
	private void updateComment(int index)throws Exception {

		System.out.println("\n============ 댓글 수정 ============\n ");
		sc.nextLine();
		
		System.out.print("수정할 번호 : ");
		int updateNum = sc.nextInt();
		
		Comment checkNo = service.checkNo(index ,updateNum);
		
		if(checkNo.getMemberNo() == Session.loginMember.getMemberNo()) {
			// Q5) 수정하면 Number도 바뀌어야 하나? 
			sc.nextLine();
			System.out.print("수정할 내용 :");
			String update= sc.next();
			
			int updateComment = service.updateComment(updateNum, update);
			
			if(updateComment > 0 ) {
				System.out.println("수정을 완료했습니다.\n");
				showComment(index);
			}else {
				System.out.println("수정을 실패했습니다.\n");
			}
			
		}else {
			
			System.out.println("수정할 권한이 존재하지 않습니다.\n다시 확인해주세요!");
		}
		
	}
	
	
	/** 3) 댓글 삭제
	 * @param index
	 * @throws Exception 
	 */
	private void deleteComment(int index) throws Exception {
		
		System.out.println("\n============ 댓글 삭제 ============\n ");
		sc.nextLine();
		
		System.out.print("삭제할 댓글 번호 : ");
		int deleteNum = sc.nextInt();
		
//		Comment checkComment = service.checkComment(index ,deleteNum);
		Comment checkComment = service.checkNo(index ,deleteNum);
		
		if(checkComment.getMemberNo() == Session.loginMember.getMemberNo()) {
			System.out.print("정말 삭제 하시겠습니까? (Y/N) >> ");
			char deleteCheck = sc.next().toUpperCase().charAt(0);
			
			if(deleteCheck == 'Y') {
					
				int deleteComment = service.deleteComment(index, deleteNum);
				
				if(deleteComment > 0 ) {
					System.out.println("삭제 성공\n");
					
					showComment(index);
				}else {
					System.out.println("삭제 실패\n");
				}
		
				
			}else {
				System.out.println("삭제 진행이 취소 되었습니다.\n");
			}
		}else {
			System.out.println("삭제 권한이 존재하지 않습니다.\n다시 확인해주세요.");
		}
	}



}
