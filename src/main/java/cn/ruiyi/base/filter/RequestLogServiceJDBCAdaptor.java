package cn.ruiyi.base.filter;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.unis.entity.Log;
import cn.unis.utils.ThreadSafeData;

@Component
public class RequestLogServiceJDBCAdaptor implements IRequestLogService {

	@PersistenceContext
	private EntityManager em;

	private static int insertNumbersPerTime = 5;

//	@Transactional(readOnly = false)
//	@Override
//	public void log(String sessionId, String ip, String path, Date dateTime) {
//		Log log = new Log();
//		log.setDateTime(dateTime);
//		log.setPath(path);
//		log.setIp(ip);
//		log.setSessionId(sessionId);
//		synchronized (SingleData.getInstance()) {
//			SingleData.getInstance().addToLogList(log);
//			int size = SingleData.getInstance().getLogList().size();
//			if (size >= insertNumbersPerTime) {
//				save();
//			}
//		}
//
//	}
	@Transactional(readOnly = false)
	@Override
	public synchronized void log(String sessionId, String ip, String path, Date dateTime) {
		Log log = new Log();
		log.setDateTime(dateTime);
		log.setPath(path);
		log.setIp(ip);
		log.setSessionId(sessionId);
			ThreadSafeData.logList.add(log);
			int size = ThreadSafeData.logList.size();
			if (size >= insertNumbersPerTime) {
				save();
			}

	}

	@Transactional(readOnly=false)
	public synchronized void save() {
		String executeSql = getSql(insertNumbersPerTime);
		Query query = em.createNativeQuery(executeSql);
		int i = 1;
		for (Log tmpLog : ThreadSafeData.logList) {
			query.setParameter(i++, tmpLog.getSessionId());
			query.setParameter(i++, tmpLog.getIp());
			query.setParameter(i++, tmpLog.getPath());
			query.setParameter(i++, tmpLog.getDateTime());
		}
		int executeResult = query.executeUpdate();
		if (executeResult >= insertNumbersPerTime)
			ThreadSafeData.logList.clear();
	}

	private String getSql(int size){
		StringBuffer executeSql = new StringBuffer("insert into backend_log(id,session_id,ip,path,date_time) values");
		StringBuffer tmp = new StringBuffer("");
		for (int i = 0; i < size; i++) {
			tmp.append(((i != (size - 1)) ? "(null,?,?,?,?)," : "(null,?,?,?,?)"));
		}
		executeSql.append(tmp);
		return executeSql.toString();
	}
}
