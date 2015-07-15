package cn.unis.transit.chargeRecord;

import java.util.List;

/**
 * 分页记录 的基础bean
 * @author Administrator
 *
 * @param <R>
 */
public class Page<R> {
	
	/**
	 * 记录总数
	 */
	private Integer total_count;
	/**
	 * 记录集合
	 */
	private List<R> records;

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public List<R> getRecords() {
		return records;
	}

	public void setRecords(List<R> records) {
		this.records = records;
	}
	
	
}
