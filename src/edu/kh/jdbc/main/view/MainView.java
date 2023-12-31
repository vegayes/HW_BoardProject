package edu.kh.jdbc.main.view;

import java.util.Scanner;

import edu.kh.jdbc.board.view.BoardView;
import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.view.MemberView;


// 1) main --> 로그인 VS 로그인 X ( 로그인 여부는 Session에서 저장됨 )   

public class MainView {
	
	private Scanner sc = new Scanner(System.in);
	private MainService service = new MainService();
	
	private MemberView memView = new MemberView();
	private BoardView boardView = new BoardView();
	
	
	public void displayBoard() {
		
		int menuNum = 0;
		
		do {
			try {
				
				if(Session.loginMember == null) {  // 로그인 X
					
					System.out.println("\n============회원제 게시판 프로그램 ============\n ");
					System.out.println(" 1. 로그인");
					System.out.println(" 2. 회원가입");
					System.out.println(" 0. 프로그램 종료");
					
					System.out.print("\n 메뉴 선택 > ");
					menuNum = sc.nextInt();
					sc.nextLine(); // 입력 버퍼 개행 문자 제거
					
					switch(menuNum) {
					case 1 : login(); break;			
					case 2 : signUp(); break;			
					case 0 : System.out.println("\n** 프로그램이 종료 되었습니다.** \n"); break;			
					default : System.out.println("\n***** 메뉴에 있는 숫자를 입력해주세요! *****"); break;
					}
					
					
				}else { // 로그인 O
				
					System.out.println("\n============ 로그인 메뉴 ============\n ");
					System.out.println(" 1. 회원 기능");
					System.out.println(" 2. 게시판 기능");
					System.out.println(" 3. 로그아웃");
					System.out.println(" 0. 프로그램 종료");
					
					System.out.print("\n 메뉴 선택 > ");
					menuNum = sc.nextInt();
					sc.nextLine();
					
					switch(menuNum) {
					case 1: memView.memberMenu(); break;
					case 2: boardView.boardMenu(); break;				
					case 3: 
						System.out.println("\n [로그아웃 되었습니다.]");
						Session.loginMember = null; 
						// 참조하고 있던 로그인 회원 객체를 없앰.
						break;
					case 0 : System.out.println("\n** 프로그램이 종료 되었습니다.** \n"); break;			
					default : System.out.println("\n***** 메뉴에 있는 숫자를 입력해주세요! *****"); break;
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}while(menuNum != 0);
	}
	
	



	// 주 기능 메서드
	/**
	 * 1. 로그인
	 */
	public void login() {
		
		// Q1) 애초에 아무도 회원가입이 안되어있는 경우는 ?? 
		
		System.out.println("===== 로그인 =====\n");
		System.out.print("ID : ");
		String logId = sc.next();
		
		System.out.print("PW : ");
		String logPw = sc.next();
		
		
		// 로그인 중 예외 발생을 띄워주고 싶기 때문에 메인으로 던지지 않았음.
		try {
			
			// 로그인 서비스 호출 후 결과 반환 받기 
			// -> 반환 받은 결과는 Session.loginMember에 저장
			Session.loginMember  = service.login(logId, logPw);
			
			if(Session.loginMember != null) { 
				
				System.out.println("\n"+ Session.loginMember.getMemberId()+"님 로그인에 성공하셨습니다.\n");
			}else {
				System.out.println("\n!!*****회원정보가 일치하지 않습니다!\n다시 로그인 해주세요*****!!\n");
				
				// Q2) 하나의 ID에서 5번 이상 틀리면 당분간 로그인 하지 못하게.. 하는방법은?? 
			}
			
		}catch(Exception e) {
			System.out.println("\n***** 로그인 중 예외 발생 *****\n");
			e.printStackTrace();
		}	
	}
	
	
	
	/**
	 * 2. 회원가입
	 * 
	 * 		1) 중복ID 거르기
	 * 		2) 비밀번호 확인
	 * 
	 */
	private void signUp() throws Exception {
		
		System.out.println("===== 회원가입 =====\n");
		
		String signId = null;
		String signPw = null;	
		
		try {
				while(true) {
					
					System.out.print("ID : ");
					signId = sc.next();
					
					// 아이디 중복 확인 서비스 호출
					// -> 중복인 경우 1, 아니면 0 반환
					int checkId = service.checkId(signId);
					
					if(checkId == 0) {
						System.out.println("\n=== 사용 가능한 아이디 입니다===\n");
						break;
					}else {
						System.out.println("중복된 ID가 존재합니다.\n다른 ID로 진행해주세요\n");
					}
					
					
				}
				
				while(true) {
					System.out.print("PW : ");
					signPw = sc.next();
					
					System.out.print("check PW : ");
					String signPw2 = sc.next();
					
					if(signPw.equals(signPw2)) {
						System.out.print("Name :");
						String signName = sc.next();
						
						System.out.print("Gender(F/M) :");
						String signGender = sc.next().toUpperCase();
							
						int newMem = service.signUp(signId, signPw, signName, signGender);
						
						if(newMem > 0) {
							System.out.println("회원가입에 성공하셨습니다.");
							return;
							
						}else {
							System.out.println("회원가입에 실패하셨습니다.");
						}
					}else {
						System.out.println("비밀번호가 일치하지 않습니다.\n");
				}
			}
		
		}catch(Exception e) {
			System.out.println("\n*****회원 가입 중 예외 발생******\n");
			e.printStackTrace();
			
		}
	}
}
	
	/* 선생님 풀이
			// 아이디, 비밀번호, 이름, 성별(M/F)을 저장할 변수 선언
		String memberId = null;
		
		String memberPw = null;
		String pwConfirm = null; // 비밀번호 확인용 변수
		
		String memberName = null;
		
		String memberGender = null;
		
		try {
			
			// 아이디 입력
			while(true) {
				
				System.out.print("아이디 입력 : ");
				memberId = sc.next();
				
				// 아이디 중복 확인 서비스 호출
				// -> 중복인 경우 1, 아니면 0 반환
				int result = service.idDuplicationCheck(memberId);
				
				if(result == 0) {
					System.out.println("\n=== 사용 가능한 아이디 입니다===\n");
					break;
				}else {
					System.out.println("\n=== 이미 사용중인 아이디 입니다. ===\n");
				}
				
			}
			
			// 비밀번호, 비밀번호 확인 입력을 받아서 둘이 같을 때 까지 무한반복
			while(true) {
				System.out.print("비밀번호 입력 : ");
				memberPw = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				pwConfirm = sc.next();
				
				
				if(memberPw.equals(pwConfirm)) {
					System.out.println("\n=====비밀번호 일치=======\n");
					break;
				}else {
					System.out.println("\n*****비밀번호가 일치하지 않습니다*****\n");
				}
				
				
			}
			
			
			// 이름 입력
			System.out.print("이름 : ");
			memberName = sc.next();
			
			// 성별 입력
			// M또는 F가 입력될 때까지 무한 반복
			while(true) {
				System.out.print("성별 : ");
				memberGender = sc.next().toUpperCase();
				
				if(memberGender.equals("M") || memberGender.equals("F")) {
					break;
				} else {
					System.out.println("\n*** M또는 F만 입력해주세요 ***\n");
				}
			}
			
			// Member 객체 생성하여 입력받은값 세팅
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			member.setMemberName(memberName);
			member.setMemberGender(memberGender);
			
			// 회원가입 서비스 호출
			int result = service.signUp(member);

			if(result > 0) {
				System.out.println("\n====회원가입 성공====\n");
			} else {
				System.out.println("\n====회원가입 실패====\n");
			}
			
			
		} catch(Exception e) {
			System.out.println("\n*****회원 가입 중 예외 발생******\n");
			e.printStackTrace();
		}
	 */

