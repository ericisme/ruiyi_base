package cn.ruiyi.base.filter;

import java.util.Date;

public interface IRequestLogService {

	/**
	 * 写入日志
	 * @param sessionId
	 * @param ip
	 * @param path
	 * @param dateTime
	 */
	public void log(String sessionId, String ip, String path, Date dateTime);
	
}
