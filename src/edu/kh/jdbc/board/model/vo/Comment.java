package edu.kh.jdbc.board.model.vo;

public class Comment {
	
	
	private int commentNo ; // 댓글 번호
	private String commentContent; // 댓글 내용
	private String createDt; // 댓글 작성일  //  DATE (굳이.. 안써도 될 거 같기두.?)
	private String deleteFl; // 삭제 여부 //  char 굳이?
	private int memberNo; // 회원번호 
	private int boardNo; // 게시글 번호
	
	private String memberName; // 추가 회원이름
	
	// 기본 생성자
	public Comment() {}



	// 매개변수 생성자
	public Comment(int commentNo, String commentContent, String createDt, String deleteFl, int memberNo, int boardNo) {
		super();
		this.commentNo = commentNo;
		this.commentContent = commentContent;
		this.createDt = createDt;
		this.deleteFl = deleteFl;
		this.memberNo = memberNo;
		this.boardNo = boardNo;
	}

	
	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getDeleteFl() {
		return deleteFl;
	}

	public void setDeleteFl(String deleteFl) {
		this.deleteFl = deleteFl;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memNum) {
		this.memberNo = memNum;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", commentContent=" + commentContent + ", createDt=" + createDt
				+ ", deleteFl=" + deleteFl + ", memberNo=" + memberNo + ", boardNo=" + boardNo + "]";
	}
	
	
	

}
