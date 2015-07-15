package cn.unis.api4walo.dto;

import java.math.BigDecimal;


/**
 * 日期 与 次数 实体
 * 下载量报表统计用
 * @author Administrator
 *
 */
public class DateAndTimesDto {

	//日期
	private String date;
	//次数
	private BigDecimal times;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getTimes() {
		return times;
	}
	public void setTimes(BigDecimal times) {
		this.times = times;
	}


	
}
