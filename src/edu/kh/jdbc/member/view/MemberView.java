package edu.kh.jdbc.member.view;

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
				System.out.println("\n============ 회원 기능 메뉴 ============\n ");
				System.out.println(" 1. 나의 정보 조회");
				System.out.println(" 2. 회원 목록 조회(아이디, 이름, 성별)");
				System.out.println(" 3. 나의 정보 수정(이름, 성별)");
				System.out.println(" 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
				System.out.println(" 5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
				System.out.println(" 9. 이전(로그인) 메뉴로 돌아가기 ");
				System.out.println(" 0. 프로그램 종료 ");
				
				
				System.out.print("\n 메뉴 선택 >");
				memNum = sc.nextInt();
				
				switch(memNum) {
				case 1: selectMem(); break;
				case 2: selectMembers(); break;
				case 3: updateMem(); break;
				case 4: updatePw();break;
				case 5: break;
				case 9: System.out.println("↩ 이전 메뉴로 돌아가기");break;
				case 0 :  break;		
				default: System.out.println("\n***** 메뉴에 있는 숫자를 입력해주세요! *****"); break;

				}
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
			
		
		}while(memNum != 0  && memNum != 9);

	}
	

	


	// 주 기능
	/**
	 * 1) 본인 회원 정보 조회 
	 */
	private void selectMem()throws Exception{
		
		System.out.println("\n===== 나의 정보 조회 =====\n");

		Member mem = service.selectMem(Session.loginMember);

		printOne(mem);
		
	}
	


	/** 2) 회원 목록 조회
	 * @throws Exception
	 */
	private void selectMembers() throws Exception {
		
		System.out.println("\n===== 회원 목록 조회 =====\n");
		
		// Map<String, String> memMap = service.selectMembers();
		
		List<Member> memList = service.selectMembers();
		
		System.out.println("아이디  |     이름      |     성별 ");
		
		for(Member key : memList) {
			
			System.out.print(key.getMemberId() +"\t|") ;
			System.out.print("\t"+ key.getMemberNM() +"\t|") ;
			System.out.print("\t"+key.getMemberGender() +"\t\n") ;
			
			
		}	
		
		/*
		System.out.println("아이디  |    이름    |  성별 ");
		
		for(String key : memMap.keySet()) {
			
			System.out.print( memMap.get(key) +" \t ") ;
			
		}
		*/
		
	}

	
	
	
	/**
	 *  3) 나의 정보 수정
	 */
	private void updateMem() throws Exception {
		
		System.out.println("\n===== 나의 정보 수정 =====\n");
		
		System.out.print("수정할 이름 :");
		String reName = sc.next();
		
		System.out.print("수정할 성별 (M/F):");
		String reGender = sc.next().toUpperCase();
		
		sc.nextLine();

		int updateMem = service.updateMem(Session.loginMember, reName, reGender);
		
		if(updateMem > 0) {
			System.out.println("수정이 완료 되었습니다.");
			// Q4) 수정된 값 표시
		}else {
			System.out.println("수정이 진행되지 않았습니다.");
		}
	}


	
	
	/**  4) 비밀번호 변경
	 * @throws Exception
	 */
	private void updatePw() throws Exception{
		
		System.out.println("\n===== 비밀번호 변경 =====\n");
	
		System.out.print("현재 비밀번호를 입력해주세요 >> ");
		String pw = sc.next();
		
		int checkNum = service.updatePw(Session.loginMember,pw);
	
		if(checkNum == 0) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}else {
			System.out.print("새 비밀번호를 입력해주세요 >>");
			String updatePw = sc.next();			
			
			System.out.print("새 비밀번호를 확인을 하기 위해 입력해주세요 >>");
			String updatePw2 = sc.next();
			
			
			
			
			
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	// 보조 기능
	/** 회원 한명 출력
	 * @param member
	 */
	public void printOne(Member member) {

		if(member == null) {
			System.out.println("\n조회된 회원 정보가 없습니다.\n");
			
		} else {
			System.out.println("   아이디    |   이름   | 성별 |        가입일      " );
			System.out.println("---------------------------------------------------------");
			
			System.out.printf(" %10s  | %7s | %3s | %20s \n",
					member.getMemberId(), member.getMemberNM(), member.getMemberGender(), member.getEnrollDt());
		}
		
		
	}
	

	
	
	
	

}
