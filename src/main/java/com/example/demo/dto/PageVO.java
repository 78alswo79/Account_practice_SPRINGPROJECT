package com.example.demo.dto;

public class PageVO {
	private int currentPage;
	private int perPage;
	private int totalPages;
	private int startIndex;
	private int endIndex;
	private int totalListCnt;
	
	public PageVO(int currentPage, int perPage, int totalListCnt/* , int totalPages, int startIndex, int endIndex */) {
		super();
		this.currentPage = currentPage;
		this.perPage = perPage;
		this.totalListCnt = totalListCnt;

		setTotalPages(this.totalListCnt, this.perPage);
		setStartIndex(this.currentPage, this.perPage);
		setEndIndex(getStartIndex(), this.perPage);
	}
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public int getTotalList() {
		return totalListCnt;
	}
	public void setTotalList(int totalListCnt) {
		this.totalListCnt = totalListCnt;
	}
	
	
	
	@Override
	public String toString() {
		return "PageVO [currentPage=" + currentPage + ", perPage=" + perPage + ", totalPages=" + totalPages
				+ ", startIndex=" + startIndex + ", endIndex=" + endIndex + ", totalListCnt=" + totalListCnt + "]";
	}


	/** 
	 * 총 페이징넘버를 처리할 메소드입니다.
	 * @author leemj
	 * @param totalListCnt
	 * @param perPage
	 * @return void
	 * */
	private void setTotalPages(int totalListCnt, int perPage) {
		if (totalListCnt % perPage == 0) {
			setTotalPages(totalListCnt / perPage);
		} else if (totalListCnt % perPage != 0) {
			// java에서 int/int형을 계산하면, 결과도 정수로 나오고 소수점이하도 버려지기 때문에 하나를 double로 선언한다. 공부하기 좋은
			setTotalPages((int)Math.ceil((double)totalListCnt / perPage));
		}
	}
	
	/**
	 * 시작 인덱스를 가공한다.
	 * (currentPage - 1) * perPage
	 * 
	 * @author leemj
	 * @param currentPage
	 * @param perPage
	 * @return void
	 * */
	private void setStartIndex(int currentPage, int perPage) {
		setStartIndex((currentPage - 1) * perPage); 
	}
	
	/**
	 * 끝 인덱스를 가공한다.
	 * 스타트인덱스 + perPage - 1
	 * 
	 * @author leemj
	 * @param startIndex
	 * @param perPage
	 * @return void
	 * */
	private void setEndIndex(int startIndex, int prePage) {
		setEndIndex(startIndex + prePage);
	}
	
	
}
