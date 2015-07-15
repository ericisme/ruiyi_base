package cn.ruiyi.base.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RuntimeListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(RuntimeListener.class
			.getName());

	public void contextInitialized(ServletContextEvent arg0) {
		//log.info("contextInitialized");
		System.out.println("注册监测到jvm的关闭");
		/*
		 * 注册JVM钩子，在JVM关闭之前做一些收尾的工作，当然也能阻止TOMCAT的关闭；必须放在contextInitialized中注册。
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("监测到jvm的关闭");
				//int n = 0;
				//while (n < 10) {
				//	log.info(Thread.currentThread() + "," + n++);
				//	try {
				//		TimeUnit.SECONDS.sleep(1);
				//	} catch (InterruptedException e) {
				//		e.printStackTrace();
				//	}
				//}
			}
		}));
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("监测到jvm的关闭2");
		log.info("contextDestroyed ....");
	}

}
