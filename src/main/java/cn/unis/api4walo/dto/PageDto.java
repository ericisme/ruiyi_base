package cn.unis.api4walo.dto;

import java.util.List;


/**
 * 通用分页Dto
 * @author Administrator
 *
 * @param <T> 
 */
public class PageDto<T> {

	//当前页数
	private Integer pageNum;
	
	//每页记录数
	private Integer rowsPerPage;
	
	//总页数
	private Integer totalPageCount;
	
	//总记录数
	private Long totalRowsCount;
	
	//总记内容,list
	private List<T> content;

	/**
	 * 构造方法
	 * @param pageNum	当前页数
	 * @param rowsPerPage	每页记录数
	 * @param totalPageCount	总页数
	 * @param totalRowsCount	总记录数
	 * @param content	总记内容,list
	 */
	public PageDto(Integer pageNum, Integer rowsPerPage, Integer totalPageCount, Long totalRowsCount, List<T> content){
		this.pageNum = pageNum;
		this.rowsPerPage = rowsPerPage;
		this.totalPageCount = totalPageCount;
		this.totalRowsCount = totalRowsCount;
		this.content = content;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public Long getTotalRowsCount() {
		return totalRowsCount;
	}

	public void setTotalRowsCount(Long totalRowsCount) {
		this.totalRowsCount = totalRowsCount;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	
}
