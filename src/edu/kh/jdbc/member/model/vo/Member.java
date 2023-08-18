package edu.kh.jdbc.member.model.vo;

public class Member {
	
	private int memberNo ; //회원번호
	private String memberId; // 회원 아이디
	private String memberPw; // 회원 비밀번호
	private String memberNM; // 회원 이름
	private String memberGender; // 성별 
	private String enrollDt;     // 가입일 
	private String unregisterFl; // 탈퇴여부 
	
	// 기본 생성자
	public Member() {}

	// 매개변수 생성자 --> 나중에 필요할 때 만듦.
	public Member(int memberNo, String memberId, String memberPw, String memberNM, String memberGender, String enrollDt,
			String unregisterFl) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberNM = memberNM;
		this.memberGender = memberGender;
		this.enrollDt = enrollDt;
		this.unregisterFl = unregisterFl;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberNM() {
		return memberNM;
	}

	public void setMemberNM(String memberNM) {
		this.memberNM = memberNM;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getEnrollDt() {
		return enrollDt;
	}

	public void setEnrollDt(String enrollDt) {
		this.enrollDt = enrollDt;
	}

	public String getUnregisterFl() {
		return unregisterFl;
	}

	public void setUnregisterFl(String unregisterFl) {
		this.unregisterFl = unregisterFl;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPw=" + memberPw + ", memberNM="
				+ memberNM + ", memberGender=" + memberGender + ", enrollDt=" + enrollDt + ", unregisterFl="
				+ unregisterFl + "]";
	}
}
	
	