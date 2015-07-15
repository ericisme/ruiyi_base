package cn.unis.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


public class StringUtils2 {
	/**
	 * ex:1111.jpeg ===> .jpeg
	 *
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		String suffix = "";
		if (fileName != null && fileName.length() > 0) {
			int length = fileName.length();
			int pointIndex = fileName.lastIndexOf(".");
			if (pointIndex != -1) {
				suffix = fileName.substring(pointIndex, length);
			}
		}
		return suffix;
	}

	/**
	 * 解析特定格式窜（Long）
	 * @param tString 需要解析的串
	 * @param split 分隔符
	 * @param clazz 类型
	 * @param breakIfOneaError 遇到一个解析出错是否返回空List
	 * @return 数据List
	 */
	public static <T> List<T>  getTFromString(String tString,String split,Class<T> clazz,boolean breakIfOneaError){
		List<T> tList  = new ArrayList<T>();
		if(tString == null || tString.equals("")){
			return tList;
		}
		String[] tStrs = tString.split(split);
		if(tStrs != null && tStrs.length > 0){
			for(String str : tStrs){
				try {
					Constructor<T> constructor = clazz.getDeclaredConstructor(String.class);
					tList.add((T)constructor.newInstance(str));
				} catch (Exception e) {
					if(breakIfOneaError){
						tList  = new ArrayList<T>();
						break;
					}
				}
			}
		}
		return tList;

	}

	public static void main(String[] args){
		List<Long> longList = getTFromString("11,12，13，，，14,15", ",|，", Long.class,false);
		System.out.println(longList.size());
	}
}
