package cn.unis.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

/**
 * 处理注解标记的工具类
 * @author fanzz
 *
 */
public class PropertyAnnotationProcessUtil {

	/**
	 * 获取标注在实体get方法上的Column数组
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Integer> getPropertyLength(Class<T>... clazz) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		for(int k=0;k<clazz.length;k++){
			Method[] methods = clazz[k].getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String name = methods[i].getName();
				if(name.startsWith("get")){
					Column column = methods[i].getAnnotation(Column.class);
					if(column != null){
						String newName = (name.charAt(3) + "").toLowerCase() + name.substring(4,name.length());
						map.put(newName, column.length());
						column.length();
					}
				}
			}
		}
		return map;
	}

}
