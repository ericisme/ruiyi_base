package cn.unis.utils;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import cn.unis.entity.Log;

public class ThreadSafeData {

	/**
	 * 线程安全map
	 */
	public static ConcurrentHashMap<String,Object> data  = new ConcurrentHashMap<String, Object>();
	public static ConcurrentHashMap<String,Integer> visitTimes = new ConcurrentHashMap<String, Integer>();
	public static Vector<Log> logList = new Vector<Log>();


}
