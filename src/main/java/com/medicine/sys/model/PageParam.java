package com.medicine.sys.model;

public class PageParam {

	private int pageNum = 1;   //当前页码
	private int maxPage;   //最大页码
	private int pageSize;  //每页行数
	private String ext1;   //附加参数1
	private String ext2;   //附加参数2
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	@Override
	public String toString() {
		return "PageParam [pageNum=" + pageNum + ", maxPage=" + maxPage + ", pageSize=" + pageSize + ", ext1=" + ext1
				+ ", ext2=" + ext2 + "]";
	}
	
}
