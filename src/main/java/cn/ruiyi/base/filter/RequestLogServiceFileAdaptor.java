package cn.ruiyi.base.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ruiyi.base.util.LogsReaderWriter;
import cn.ruiyi.base.util.PathUtil;

public class RequestLogServiceFileAdaptor implements IRequestLogService {

	@Override
	public void log(String sessionId, String ip, String path, Date dateTime) {
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");   		
		String logs = "{\"dateTime\" : \"" + dateToString(new Date()) + 
						"\",  \"sessionId\":\"" + sessionId + 
						"\",  \"ip\":\"" + ip + 
						"\",  \"path\":\""+path+"\"},  "
						+ "\n";
		String cpath = System.getProperty("user.dir")+"/logs";
		if("#projectPath".equals(PathUtil.getConfigResource("REQUEST_LOG_FILE_PATH"))){
			cpath = System.getProperty("user.dir")+"/logs/";
		}else{
			cpath = PathUtil.getConfigResource("REQUEST_LOG_FILE_PATH");
		}
		LogsReaderWriter.writeIntoFile(logs, cpath+"request_log/request_log_"+formatDate.format(new Date())+".txt", true);
	}
	
    private String dateToString(Date date) {        
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");            	       
    	return formatDate.format(date);
    }
    
}
