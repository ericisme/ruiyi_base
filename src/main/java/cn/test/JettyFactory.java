/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.test;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import com.google.common.collect.Lists;

/**
 * 鍒涘缓Jetty Server鐨勫伐鍘傜被.
 * 
 * @author calvin
 */
public class JettyFactory {

	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
	private static final String WINDOWS_WEBDEFAULT_PATH = "jetty/webdefault-windows.xml";

	/**
	 * 鍒涘缓鐢ㄤ簬寮�彂杩愯璋冭瘯鐨凧etty Server, 浠rc/main/webapp涓篧eb搴旂敤鐩綍.
	 */
	public static Server createServerInSource(int port, String contextPath) {
		Server server = new Server();
		//璁剧疆鍦↗VM閫�嚭鏃跺叧闂璊etty鐨勯挬瀛愩�
		server.setStopAtShutdown(true);

		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		//瑙ｅ喅Windows涓嬮噸澶嶅惎鍔↗etty灞呯劧涓嶆姤鍛婄鍙ｅ啿绐佺殑闂.
		connector.setReuseAddress(false);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, contextPath);
		//淇敼webdefault.xml锛岃В鍐砏indows涓婮etty Lock浣忛潤鎬佹枃浠剁殑闂.
		webContext.setDefaultsDescriptor(WINDOWS_WEBDEFAULT_PATH);
		server.setHandler(webContext);

		return server;
	}

	/**
	 * 璁剧疆闄stl-*.jar澶栧叾浠栧惈tld鏂囦欢鐨刯ar鍖呯殑鍚嶇О.
	 * jar鍚嶇О涓嶉渶瑕佺増鏈彿锛屽sitemesh, shiro-web
	 */
	public static void setTldJarNames(Server server, String... jarNames) {
		WebAppContext context = (WebAppContext) server.getHandler();
		List<String> jarNameExprssions = Lists.newArrayList(".*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
		for (String jarName : jarNames) {
			jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
		}

		context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				StringUtils.join(jarNameExprssions, '|'));

	}

	/**
	 * 蹇�閲嶆柊鍚姩application锛岄噸杞絫arget/classes涓巘arget/test-classes.
	 */
	public static void reloadContext(Server server) throws Exception {
		WebAppContext context = (WebAppContext) server.getHandler();

		System.out.println("Application reloading");
		context.stop();

		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		classLoader.addClassPath("target/classes");
		classLoader.addClassPath("target/test-classes");
		context.setClassLoader(classLoader);

		context.start();

		System.out.println("Application reloaded");
	}
}