package cn.unis.utils;


/**
 * 分页参数封装类.
 */
public class MyPageRequest {
	/**
	 * 当前页码
	 */
	protected int pageNo = 1;
	/**
	 * 每一页显示的数量
	 */
	protected int pageSize = 5;
	/**
	 * 总数量
	 */
	protected long totalCount = 0;
	
	/**
	 * 步长
	 */
	protected int step = 2;

	/**
	 * 存在上一页
	 */
	protected boolean prePage;
	/**
	 * 存在下一页
	 */
	protected boolean nextPage;

	public MyPageRequest() {
		
	}

	public MyPageRequest(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * 获得当前页的页号, 序号从1开始, 默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号, 序号从1开始, 低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为5.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量, 低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}



	public boolean isPrePage() {
		return prePage;
	}

	public void setPrePage(boolean prePage) {
		this.prePage = prePage;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
	
	private void setPreAndNextPage(){
		int totalPages = this.getTotalPages();
		if(totalPages > (getStep() * 2 + 1)){
			if(pageNo < (getStep() + 2)){
				this.setPrePage(Boolean.FALSE);
			}else{
				this.setPrePage(Boolean.TRUE);
			}
			if(pageNo > (totalPages - getStep() -1) || totalPages == 0){
				this.setNextPage(Boolean.FALSE);
			}else{
				this.setNextPage(Boolean.TRUE);
			}
		}else{
			this.setPrePage(Boolean.FALSE);
			this.setNextPage(Boolean.FALSE);
		}
		
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		
		this.setPreAndNextPage();
	}

	/**
	 * 计算总页数
	 * @return
	 */
	public int getTotalPages() {
		return (int) Math.ceil(totalCount / (float)pageSize) ;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
}
