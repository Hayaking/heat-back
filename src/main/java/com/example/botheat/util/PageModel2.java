package com.example.botheat.util;

import java.util.List;

/**
 * ��װ��ҳ�߼�
 * @author Administrator
 *
 */
public class PageModel2<T> {

	//�����
	private List<T> list;

	//ÿҳ����������
	private int pageSize;

	//�ڼ�ҳ
	private int pageNo;

	//��ǰҳ����
	private String firstRow;

	//��ǰҳβ��
	private String lastRow;

	/**
	 * ��ҳ
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * ��һҳ
	 * @return
	 */
	public int getPreviousPageNo() {
		if (this.pageNo <= 1) {
			return 1;
		}
		return this.pageNo - 1;
	}

	/**
	 * ��һҳ
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
