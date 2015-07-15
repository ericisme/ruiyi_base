package cn.ruiyi.base.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ruiyi.base.util.PathUtil;
import cn.unis.utils.ThreadSafeData;


@Component("requestLogFilter")
public class RequestLogFilter implements Filter {

	@Autowired
	private RequestLogServiceJDBCAdaptor requestLogServiceJDBCAdaptor;
	private RequestLogServiceFileAdaptor requestLogServiceFileAdaptor = new RequestLogServiceFileAdaptor();


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if("true".equals(PathUtil.getConfigResource("REQUEST_LOG_OPEN"))){

		    HttpServletRequest req = (HttpServletRequest) request;
		    String sessionId = req.getSession().getId();
		    String ip = req.getRemoteAddr();
		    String path = req.getRequestURI()+(req.getQueryString()==null?"":("?"+req.getQueryString()));
		    if(PathUtil.getConfigResource("RequestLogFilter_Times_Limit").equals("true")){
		    	   Integer obj = ThreadSafeData.visitTimes.get(ip);
				    if(obj == null ){
				    	 ThreadSafeData.visitTimes.put(ip, 0);
				    }else{
				    	Integer tmp = obj + 1;
				    	ThreadSafeData.visitTimes.put(ip, tmp);
				    	Integer times = 10000;
				    	try {
							times = Integer.parseInt(PathUtil.getConfigResource("RequestLogFilter_Times"));
						} catch (Exception e) {
							Logger.getLogger(RequestLogServiceJDBCAdaptor.class.getName()).warning("some error with RequestLogFilter_Times in the config.properties!");;
						}

				    	if(tmp > times){
				    		response.setContentType("text/plain;charset=UTF-8");
				    		response.getWriter().write("您刷新的太快了吧,请稍等60秒~");
				    		return;
				    	}
				    }
		    }
		    List<String> keyList = PathUtil.getKeys(1);
		    for(String str : keyList){
		    	if(str.startsWith("RequestLogFilter_StartsWith")){
		    		String value = PathUtil.getConfigResource(str);
		    		if(StringUtils.isNotBlank(value) && path.startsWith(value)){
		    			chain.doFilter(request, response);
				    	return;
		    		}
		    	}else if(str.startsWith("RequestLogFilter_Equals")){
		    		String value = PathUtil.getConfigResource(str);
		    		if(StringUtils.isNotBlank(value) && path.equals(value)){
		    			chain.doFilter(request, response);
				    	return;
		    		}
		    	}
		    }

//		    if(path.startsWith("/static") || path.startsWith("/upload") || path.startsWith("/favicon.ico")){
//		    	chain.doFilter(request, response);
//		    	return;
//		    }

		    if("file".equals(PathUtil.getConfigResource("REQUEST_LOG_IMPL"))){
		    	requestLogServiceFileAdaptor.log(sessionId, ip, path, new Date());
		    }
		    if("jdbc".equals(PathUtil.getConfigResource("REQUEST_LOG_IMPL"))){
		    	requestLogServiceJDBCAdaptor.log(sessionId, ip, path, new Date());
		    }

		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
