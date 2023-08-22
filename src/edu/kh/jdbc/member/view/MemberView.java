package edu.kh.jdbc.member.view;

import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.model.vo.Member;

public class MemberView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MemberService service = new MemberService();
	
	
	/*
	 * ---- 1) 본인 정보 조회
	   ---- 2) 정보 수정
	   ---- 3) 회원 탈퇴

	 */
	
	public void memberMenu() {
		
		int memNum = 0;
		
		do {
			try {
				System.out.println("\n=============== 회원 기능 메뉴 ===============\n ");
				System.out.println(" 1. 나의 정보 조회");
				System.out.println(" 2. 회원 목록 조회(아이디, 이름, 성별)");
				System.out.println(" 3. 나의 정보 수정(이름, 성별)");
				System.out.println(" 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
				System.out.println(" 5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
				System.out.println(" 9. 이전(로그인) 메뉴로 돌아가기. ");
				System.out.println(" 0. 프로그램 종료 ");
				
				
				System.out.print("\n 메뉴 선택 >");
				memNum = sc.nextInt();
				
				switch(memNum) {
				case 1: selectMem(); break;
				case 2: selectMembers(); break;
				case 3: updateMem(); break;
				case 4: updatePw();break;
				case 5: //  secession()
				// 회원 기능을 빠져나감.
				
				  if(unRegisterMember()){
				  		return;
				  }
				  
				  /*
				  return 값 ;  메서드 종료 시, 값을 가지고 반환함.
				  return; 메서드 종료
				 */	
				;break;
				case 9: System.out.println("↩ 이전 메뉴로 돌아가기.");break;
				case 0 : System.out.println("\n***** 프로그램이 종료 되었습니다. ***** \n");
		     		System.exit(0); // JVM 강제 종료 구문
		     		break;				
				default: System.out.println("\n***** 메뉴에 있는 숫자를 입력해주세요! *****"); break;

				}
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
			
		
		}while(memNum != 9);

	}
	


	// 주 기능
	/**
	 * 1) 본인 회원 정보 조회 
	 */
	private void selectMem()throws Exception{
		
		// Session에 있는 정보만 사용해도 괜찮음.
		/*
		System.out.println("\n===== 나의 정보 조회 =====\n");

		Member mem = service.selectMem(Session.loginMember);

		printOne(mem);
		*/
		
		System.out.println("회원 번호 : " +  Session.loginMember.getMemberNo());
		System.out.println("아이디 : " +  Session.loginMember.getMemberId());
		System.out.println("회원 이름 : " +  Session.loginMember.getMemberNM());
		
		if(Session.loginMember.getMemberGender().equals('M')) {
			System.out.println("성별 : 남");
		}else {
			System.out.println("성별 : 여");
		}
		
		System.out.println("가입일 : " + Session.loginMember.getEnrollDt());
	}
	


	/** 2) 회원 목록 조회
	 */
	private void selectMembers() {
		
		System.out.println("\n=============== 회원 목록 조회 ===============\n");
		
		// Map<String, String> memMap = service.selectMembers();
		try{
			List<Member> memList = service.selectMembers();
			
			if(memList.isEmpty()) {
				
				System.out.println("\n***** 조회 결과가 없습니다. *****\n");
			}else {
			
			
			System.out.println(" 아이디  |     이름     |     성별 ");
			
			for(Member key : memList) {
				
				System.out.printf(" %s\t |    %s\t|\t%s\t \n", key.getMemberId(), key.getMemberNM()
						, key.getMemberGender());
				}
			}
			
		}catch(Exception e) {
			System.out.println("\n***** 회원 목록 조회 중 예외 발생 *****\n");
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 *  3) 나의 정보 수정
	 */
	private void updateMem() throws Exception {
		
		System.out.println("\n=============== 나의 정보 수정 ===============\n");
		
		System.out.print("수정할 이름 :");
		String reName = sc.next();
		
		String reGender = null;
		
		while(true) {
			System.out.print("수정할 성별 (M/F):");
			reGender = sc.next().toUpperCase();
		
			if(reGender.equals("M") || reGender.equals("F")) {
				break; // 정확하게 입력하면, 반복문 빠져나가기.
			}
			
			System.out.println("\n*****성별은 M 또는 F로 입력해주세요!*****\n");
		}	
			sc.nextLine();
		try {
			int updateMem = service.updateMem(Session.loginMember, reName, reGender);
			
			if(updateMem > 0) {
				
				//DB와  JAVA 프로그램 데이터 동기화 필요! 
				Session.loginMember.setMemberNM(reName);
				Session.loginMember.setMemberGender(reGender);
				
				System.out.println("\n*수정이 완료 되었습니다.*\n");
				System.out.printf("수정된 회원 이름 : %s\n", Session.loginMember.getMemberNM());
				System.out.printf("수정된 회원 성별 : %s\n", Session.loginMember.getMemberGender());
				
			}else {
				System.out.println("수정 실패");
			}
		}catch(Exception e) {
			System.out.println("\n***** 내정보 수정 중 예외발생 *****\n");
			e.printStackTrace();
		}
	}

	
	
	/**  4) 비밀번호 변경
	 * @throws Exception
	 */
	private void updatePw() throws Exception{
		
		String updatePw = null;
		
		System.out.println("\n=============== 비밀번호 변경 ===============\n");
	
		System.out.print("현재 비밀번호를 입력해주세요 >> ");
		String pw = sc.next();
		
		int checkNum = service.updatePw(Session.loginMember, pw);

		if(checkNum == 0) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}else {
			
			while(true) {
				System.out.print("새 비밀번호를 입력해주세요 >>");
				updatePw = sc.next();			
				
				if(updatePw.equals(pw)) {
					System.out.println("\n*****! 현재 비밀번호와 같습니다.!*****\n 다른 비밀번호로 입력해주세요!\n");
					continue; // 현재 비밀번호와 일치하면 다시 입력해서 동일하지 않는 비번으로 변경 진행
				}
				
				System.out.print("\n새 비밀번호를 확인을 하기 위해 입력해주세요 >>");
				String updatePw2 = sc.next();
				
				if(updatePw.equals(updatePw2)) {
					break; // 재입력한 비번이 같을때까지 계속 진행
				}
				System.out.println("\n***** 비밀번호가 일치하지 않습니다. *****\n");
			}
			
			int result = service.updatePw2(Session.loginMember, updatePw);
			
			if(result > 0 ) {
				System.out.println("\n*비밀번호 수정이 완료되었습니다.*\n");
			}else {
				System.out.println("***** 비밀번호 수정을 실패하였습니다. *****");
			}
		}
	}

	
	
	


	/**  5) 회원 탈퇴
	 * @return
	 * @throws Exception
	 */
	private void secession() throws Exception{

		// 비밀번호 랜덤 
		
		int secStr = ((int)(Math.random()*100000)+1);
		
		System.out.println("\n=============== 회원 탈퇴 ===============\n");
		
		System.out.print("현재 비밀번호 :");
		String currentPw = sc.next();

		System.out.printf("보안문자 입력 [%d] : ",secStr);
		int inSecStr = sc.nextInt();
		
		if(Session.loginMember.getMemberPw().equals(currentPw) && secStr ==inSecStr) {

			
			System.out.print("탈퇴를 진행하시겠습니까? (Y/N)>> ");
			char check = sc.next().toUpperCase().charAt(0);
			
			if(check == 'Y') {
				
				int secession = service.secession(Session.loginMember);
				
				if(secession >0) {
					System.out.println("\n*탈퇴를 진행하였습니다.*\n");
				}else {
					System.out.println("\n***** 탈퇴에 실패하여습니다. *****\n");
				}
				
				
			}else {
				System.out.println("탈퇴를 취소하였습니다. "
						+ Session.loginMember.getMemberId()+"님, 환영합니다.");
			}
			
		}
		
	

	}



	private boolean	unRegisterMember() {
		
		System.out.println("\n=============== 회원 탈퇴 ===============\n");
		
		System.out.print("현재 비밀번호 :");
		String memberPw = sc.next();
		
		String code = service.createSecurityCode();  // 6자리 랜덤문자 반환 받음.
		System.out.printf("보안문자 입력 [%s] :", code);
		String inSecStr = sc.next();
		
		/*
		if(Session.loginMember.getMemberPw().equals(memberPw) && code.equals(inSecStr)) {

			
			System.out.print("탈퇴를 진행하시겠습니까? (Y/N)>> ");
			char check = sc.next().toUpperCase().charAt(0);
			
			if(check == 'Y') {
				
				int secession = service.secession(Session.loginMember);
				
				if(secession >0) {
					System.out.println("탈퇴를 진행하였습니다.");
				}else {
					System.out.println("탈퇴에 실패하여습니다.");
				}
				
				
			}else {
				System.out.println("탈퇴를 취소하였습니다. "
						+ Session.loginMember.getMemberId()+"님, 환영합니다.");
			}
			
		}
		*/
		
		
		// 선생님 풀이
		
		if(!code.equals(inSecStr)){
			
			System.out.println("\n***** 보안 문자가 일치하지 않습니다. *****\n");
			
			return false;
		}
		
		while(true) {

			System.out.print("탈퇴를 진행하시겠습니까? (Y/N)>> ");
			char check = sc.next().toUpperCase().charAt(0);
			
			if(check == 'N') {
				System.out.println("탈퇴를 취소하였습니다. "
						+ Session.loginMember.getMemberId()+"님, 환영합니다.");
				
				return false; // 메서드 종료
			}
			
			if(check == 'Y') {
			
				break; // 반복문 종료
			}
				
		
			// 'Y' 'N'이 아닌 경우
			System.out.println("\n*****잘못 입력하셨습니다.*****\n");			
		}
		
		
		try {
			int secession = service.secession(Session.loginMember);
			
			if(secession >0) {
				System.out.println("탈퇴되었습니다.");
				
				// 로그아웃 (동기화되어야 함!)
				// return 되면서  -> 로그인 된 화면 -> loginMember가 로그아웃되었기 때문에  메인으로 돌아감.
				Session.loginMember = null;
				
				return true;
			}else {
				System.out.println("***** 탈퇴에 실패하였습니다. *****");
			}
			
			
		}catch(Exception e) {
			
			System.out.println("\n=============== 회원 탈퇴 중 예외 발생 ===============\n");
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
	
	// 보조 기능
	/** 회원 한명 출력
	 * @param member
	 */
	public void printOne(Member member) {

		if(member == null) {
			System.out.println("\n***** 조회된 회원 정보가 없습니다. *****\n");
			
		} else {
			System.out.println("   아이디    |   이름   | 성별 |        가입일      " );
			System.out.println("---------------------------------------------------------");
			
			System.out.printf(" %10s  | %7s | %3s | %20s \n",
					member.getMemberId(), member.getMemberNM(), member.getMemberGender(), member.getEnrollDt());
		}
		
		
	}
	

	
	
	
	

}
