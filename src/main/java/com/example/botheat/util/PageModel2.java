package com.example.botheat.util;

import java.util.List;

/**
 * 封装分页逻辑
 * @author Administrator
 *
 */
public class PageModel2<T> {

	//结果集
	private List<T> list;

	//每页多少条数据
	private int pageSize;

	//第几页
	private int pageNo;

	//当前页首行
	private String firstRow;

	//当前页尾行
	private String lastRow;

	/**
	 * 首页
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 上一页
	 * @return
	 */
	public int getPreviousPageNo() {
		if (this.pageNo <= 1) {
			return 1;
		}
		return this.pageNo - 1;
	}

	/**
	 * 下一页
	 * @return
	 */
	public int getNextPageNo() {
		return this.pageNo + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getLastRow() {
		return lastRow;
	}

	public void setLastRow(String lastRow) {
		this.lastRow = lastRow;
	}

	public String getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(String firstRow) {
		this.firstRow = firstRow;
	}

}
