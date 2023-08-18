package edu.kh.jdbc.common;

import edu.kh.jdbc.member.model.vo.Member;

public class Session {

	// 로그인 : 기록된 회원정보 (DB)를 가지고 오는 것
	//         -> 로그아웃 할 때까지 프로그램에서 회원 정보가 유지
	
	public static Member loginMember = null;
	
	// loginMember == null -> 로그아웃 상태
	// loginMember != null -> 로그인 상태 
	// 로그인의 유무가 필요할때마다 static에 저장된 값을 가져와서 사용함.
	
	
	
	
	
	
	
	
	
}
