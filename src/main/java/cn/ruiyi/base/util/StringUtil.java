package cn.ruiyi.base.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;


/**
 * 字符串实用方法类。
 * 
 * @author 马必强
 *
 */
public class StringUtil
{
	
	public static String null2blank(String s){
		if(s==null || "null".equals(s)){
			return "";
		} else {
			return s;
		}
	}
	public static Integer null2Zero(Integer i){
		if(i==null){
			return 0;
		} else {
			return i;
		}
	}
	
	//获取字符串的长度,中文占一个3位,英文、数字及符号占1个位，. 
	public static int length(String str){ 
	   int strlen=0; 	   
	   if(str == null){   
		   str = ""; 
	   }
	   char[] a = str.toCharArray(); 
	   for(int i = 0;i <a.length;i++){ 
	       if(a[i] < 255){ 
	               strlen++;
	       } 
	       else{ 
	           strlen++; 
	           strlen++;
	           strlen++;
	       } 
	   } 
	   return strlen; 
	} 
	
	//重新组合字符串,中文占一个位,英文、数字及符号占0.5个位，也就是2个英文占一个位. 
	public static String substring(String str, double length){ 
		   String textStr = "";
		   if(str == null)   
			   str= ""; 
		   int i;
		   char[] a = str.toCharArray(); 
		   for(i = 0; i < length; i++){
			   if(a[i] < 255){
				   length = length + 0.5;
			   }
		   }
		   for(i = 0; i < length;i++){ 
		            textStr = textStr + a[i];
		   } 
		   return textStr; 
		} 

	/**获取字符串, 
	*   str为标题字串 
	*   len为要截取的长度, 
	*   addstr为截取后字串尾追加的标识字串*/ 
	public static String subTitle(String str, int len, String addstr){ 
	  if(str == null)   
		  return ""; 
	  if(addstr == null)  
		  addstr= ""; 
	  if(len < 0)   
		  return   null; 
	  if(StringUtil.length(str) > len){ 
	      str = StringUtil.substring(str, len-1) + addstr; 
	  } 
	  return str; 
	} 
	
	
	
	//判断字符串trim后非空 ,非空为true
	public   static   boolean   stringTrim(String   str){ 
		if (str == null) return false;
		str = str.trim();
		if("".equals(str)){
			return false;
		}
		return true; 
	}	
	//如果只有英文和数字,返回true 
	public   static   boolean   isEngString(String   str){ 
		if(str==null) return true;
        str   =   str.trim(); 
        String   arg   =   "^[A-Za-z0-9]+$"; 
        return   str.matches(arg); 
	}
	//如果只有数字,返回true 
	public   static   boolean   isNumString(String   str){     
		if(str==null) return true;
        str   =   str.trim(); 
        String   arg   =   "^[0-9]*[1-9][0-9]*$"; 
        return   str.matches(arg); 
	}
	
	/**
	 * 防止subString报错
	 * @param s
	 * @param beginIndex
	 * @param endIndex
	 * @param returnNull
	 * @return
	 */
	public static String subString(String s, int beginIndex, int endIndex, boolean returnNull){
		String result = "";
		try{
			if(endIndex == 0){
				result = s.substring(beginIndex);
			}else{
				result = s.substring(beginIndex, endIndex);
			}
		}catch(Exception e){
			if(returnNull){
				result = ""; 
			}else{
				result = s;
			}
		}
		
		return result;
	}
	
	/**
	 * 把对象转换成String
	 */
	public final static String toString(Object obj)
	{
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 调式方法
	 */
	public final static void debug(Object obj)
	{
		System.out.println("[DEBUG]:" + toString(obj));
	}

	/**
	 * 调式方法
	 */
	public final static void debug(Object[] obj)
	{
		for (Object tmp : obj) debug(tmp);
	}

	/**
	 * 过滤将要写入到XML文件中的字符串，即过滤掉<![CDATA[和]]>标签
	 */
	public static String toXMLFilter(Object obj)
	{
		if (trim(obj).equals("")) return " ";
		return trim(obj).replaceAll("<!\\[CDATA\\[", "&lt;!CDATA").replaceAll(
				"\\]\\]>", "]] >");
	}

	/**
	 * 返回一个对象的字符串，多数是处理字符串的
	 */
	public static String trim(Object obj)
	{
		return obj == null ? "" : String.valueOf(obj).trim();
	}
	
	/**
	 * 对一字符串数组进行去空格操作
	 */
	public final static String[] trim(String[] aStr)
	{
		if (aStr == null) return null;
		for (int i = 0; i < aStr.length; i ++) {
			aStr[i] = trim(aStr[i]);
		}
		return aStr;
	}
	
	/**
	 * 过滤设置到SQL语句中的字符串
	 */
	public final static String toDBFilter(String aStr)
	{
		return trim(aStr).replaceAll("\\\'", "''");
	}
	
	/**
	 * 数字字符串的整型转换
	 * @param str 数字字符串
	 * @param defaultVal 默认值
	 * @return 转换后的结果
	 */
	public final static int parseInt(String str, int defaultVal)
	{
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return defaultVal;
		}
	}
	
	/**
	 * 数字字符串的长整型转换
	 * @param str 数字字符串
	 * @param defaultVal 默认值
	 * @return 转换后的结果
	 */
	public final static long parseLong(String str, long defaultVal)
	{
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException ex) {
			return defaultVal;
		}
	}
	
	/**
	 * 数字字符串的整型转换
	 * @param str 数字字符串
	 * @param defaultVal 默认值
	 * @return 转换后的结果
	 */
	public final static int[] parseInt(String[] str, int defaultVal)
	{
		if (str == null || str.length < 1) return new int[0];
		int[] result = new int[str.length];
		for (int i = 0; i < str.length; i ++) {
			result[i] = parseInt(str[i], defaultVal);
		}
		return result;
	}
	
	/**
	 * 将指定的值从源数组中进行排除，并返回一个新数组
	 * @param src
	 * @param target
	 * @return
	 */
	public final static int[] exclude(int[] src, int[] target)
	{
		if (target == null || target.length < 1) return src;
		StringBuilder tmp = new StringBuilder();
		for (int tt : src) {
			if (!include(target, tt)) tmp.append(tt + ",");
		}
		if (tmp.length() > 1 && tmp.charAt(tmp.length() - 1) == ',') {
			tmp.deleteCharAt(tmp.length() - 1);
		}
		if (tmp.toString().trim().length() < 1) return new int[0];
		String[] array = tmp.toString().split(",");
		return parseInt(array, 0);
	}
	
	/**
	 * 将指定的target数组从src源数组中进行排除.
	 * @param src
	 * @param target
	 * @return
	 */
	public final static String[] exclude(String[] src, String[] target)
	{
		if (target == null || target.length < 1) return src;
		StringBuilder tmp = new StringBuilder();
		for (String str : src) {
			if (!include(target, str)) tmp.append(str + ",");
		}
		if (tmp.length() > 1 && tmp.charAt(tmp.length() - 1) == ',') {
			tmp.deleteCharAt(tmp.length() - 1);
		}
		if (tmp.toString().trim().length() < 1) return new String[0];
		return tmp.toString().split(",");
	}
	
	/**
	 * 将指定的数组字符串使用指定的符号进行连接.
	 */
	public final static String join(Object[] src, String spliter)
	{
		if (src == null || src.length < 1) return "";
		StringBuffer tmp = new StringBuffer();
		//String mySpliter = trim(spliter).intern() == "" ? "," : spliter;
		String mySpliter = trim(spliter);
		for (int i = 0; i < src.length; i ++) {
			tmp.append(src[i]);
			if (i < src.length - 1) tmp.append(mySpliter);
		}
		return tmp.toString();
	}
	
	/**
	 * 将指定的数组字符串使用指定的符号进行连接.
	 */
	public final static String join(int[] src, String spliter)
	{
		if (src == null || src.length < 1) return "";
		StringBuffer tmp = new StringBuffer();
		String mySpliter = trim(spliter).intern() == "" ? "," : spliter;
		for (int i = 0; i < src.length; i ++) {
			tmp.append(src[i] + mySpliter);
		}
		return tmp.deleteCharAt(tmp.length() - 1).toString();
	}
	
	/**
	 * 将指定的字符串数组使用指定的字符串进行保围，比如一字符串数组如下：
	 * ["hello", "world"],使用的包围字符串为"'",那么返回的结果就应该是：
	 * ["'hello'","'world'"].
	 * @param src
	 * @param str
	 * @return
	 */
	public final static String[] arround(String[] src, String str)
	{
		if (src == null || src.length < 1) return src;
		String[] result = new String[src.length];
		for (int i = 0; i < src.length; i ++) {
			result[i] = str + src[i] + str;
		}
		return result;
	}
	
	/**
	 * 判断指定的字符串是否是空指针或空串
	 * @param src
	 * @return
	 */
	public final static boolean isNullAndBlank(String src)
	{
		return trim(src).intern() == "";
	}
	
	/**
	 * 将指定的字符串转换成符合JavaBean规范的方法名称！
	 * 此方法将只转换第一个字母为大写字母，比如有一字符串是
	 * helloWorld,那么转换后就是：setHelloWorld.另外如果给出
	 * 的字符串为空（null或""），那么将直接返回空字符串！
	 * @param name
	 * @return
	 */
	public final static String toMethodName(String name)
	{
		String tmp = trim(name).intern();
		if (tmp == "") return "";
		if (tmp.length() < 2) {
			return "set" + name.toUpperCase();
		} else {
			return "set" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		}
	}
	
	/**
	 * 利用反射将指定对象的属性打印成字符串形式！
	 * @param obj
	 * @return
	 */
	public final static String reflectObj(Object obj)
	{
		if (obj == null) return "";
		return ReflectionToStringBuilder.reflectionToString(obj);
	}
	
	/**
	 * 将map中的键和值进行对应并返回成字符串.
	 * @param map
	 * @return
	 */
	public final static String mapToString(Map map)
	{
		if (map == null) return null;
		StringBuilder buf = new StringBuilder("[");
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			buf.append(String.valueOf(key) + ":" + String.valueOf(map.get(key)) 
					+ ",");
		}
		if (buf.charAt(buf.length() - 1) == ',') {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.append(']').toString();
	}
	
	/**
	 * 检查给定的数组长度是否一致，若全部一致则返回true，否则返回false
	 * @param array
	 * @return
	 */
	public final static boolean sameLength(String[]... array)
	{
		if (array.length <= 1) return true;
		for (int i = 0; i < array.length; i ++) {
			String[] str1 = array[i];
			for (int j = i + 1; j < array.length; j ++) {
				String[] str2 = array[j];
				if (str1 == null && str2 == null) continue;
				if (str1 == null && str2 != null) return false;
				if (str1 != null && str2 == null) return false;
				if (str1.length != str2.length) return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查指定的数组中是否包含了指定的数字.
	 * @param source
	 * @param test
	 * @return
	 */
	public final static boolean include(int[] source, int test)
	{
		if (source == null || source.length < 1) return false;
		for (int tmp : source) {
			if (tmp == test) return true;
		}
		return false;
	}
	
	/**
	 * 检查指定的字符串数组中是否包含了指定的字符串.
	 * @param source
	 * @param test
	 * @return
	 */
	public final static boolean include(String[] source, String test)
	{
		if (source == null || source.length < 1) return false;
		for (String tmp : source) {
			if (tmp == null && test == null) return true;
			if (tmp != null && tmp.equals(test)) return true;
		}
		return false;
	}

	/**
	 * 检查指定的字符串数组中是否包含了指定的字符串，不区分大小写.
	 * @param source
	 * @param test
	 * @return
	 */
	public final static boolean includeIgnoreCase(String[] source, String test)
	{
		if (source == null || source.length < 1) return false;
		for (String tmp : source) {
			if (tmp == null && test == null) return true;
			if (tmp != null && tmp.equalsIgnoreCase(test)) return true;
		}
		return false;
	}
	
	/**
	 * 将指定字符串的首字母变成大写.
	 * @param str
	 * @return
	 */
	public final static String capitalize(String str)
	{
		if (str == null || str.length() < 1) return str;
		if (str.length() == 1) return str.toUpperCase();
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * 将指定字符串的首字母变成小写.
	 * @param str
	 * @return
	 */
	public final static String unCapitalize(String str)
	{
		if (str == null || str.length() < 1) return str;
		if (str.length() == 1) return str.toLowerCase();
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	/**
	 * 获取当前日期形成的路径字符串.
	 * @return
	 */
	public final static String getDailyDirectory()
	{
		return DateUtil.getFormateDate("yyyy/MM/dd/");
	}
	
	/**
	 * 反转字符串.
	 * @param str
	 * @return
	 */
	public final static String reverse(String str)
	{
		if (trim(str).equals("")) return str;
		return new StringBuilder(str).reverse().toString();
	}
	/**
	 * 字符转日期
	 */
	public static Date strToDate(String date) {
        Date d = null;
        if ((date != null) && (!"".equals(date))) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
            	d = new Date(df.parse(date).getTime());
            } catch (Exception e) {  
            }
        }        
        return d;
    }
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args)
	{
		StringUtil.debug("toString : " + toString(34567));
		StringUtil.debug("trim : " + trim("   hello world!  "));
		StringUtil.debug("parseInt : " + parseInt("fff", 89));
		StringUtil.debug("join : " + join(new String[]{"hello","world","beijing"}, "="));
		StringUtil.debug("toMethodName:" + toMethodName(null));
		StringUtil.debug("toMethodName:" + toMethodName(""));
		StringUtil.debug("toMethodName:" + toMethodName("e"));
		StringUtil.debug("toMethodName:" + toMethodName("3"));
		StringUtil.debug("toMethodName:" + toMethodName("g3"));
		StringUtil.debug("toMethodName:" + toMethodName("4r"));
		StringUtil.debug("toMethodName:" + toMethodName("gr4fd"));
		int[] a = new int[]{1,2,3}, b = new int[]{2,1,5};
		StringUtil.debug("exclude:" + join(exclude(a, b), ","));
	}
	
	/**
	 * 
	 * unicode 转换成 中文
	 * 
	 * @param theString
	 * @return
	 */

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	
	/**
	 * 
	 * @param 把html中的标签去掉，只留文字内容
	 * @return
	 */
	public static String html2Text(String inputString) {
	    String htmlStr = inputString; //含html标签的字符串
	    String textStr = "";
	    java.util.regex.Pattern p_script;
	    java.util.regex.Matcher m_script;
	    java.util.regex.Pattern p_style;
	    java.util.regex.Matcher m_style;
	    java.util.regex.Pattern p_html;
	    java.util.regex.Matcher m_html;
	    java.util.regex.Pattern p_other;
	    java.util.regex.Matcher m_other;
	    try {
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	                                                                                                // }
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	                                                                                            // }
	        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
	        
	        String regEx_other = "&[a-z]+;";//一些特殊字符处理，主要是&开头;结尾

	        p_script = java.util.regex.Pattern.compile(regEx_script,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); //过滤script标签

	        p_style = java.util.regex.Pattern.compile(regEx_style,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); //过滤style标签

	        p_html = java.util.regex.Pattern.compile(regEx_html,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); //过滤html标签
	        
	        p_other = java.util.regex.Pattern.compile(regEx_other,java.util.regex.Pattern.CASE_INSENSITIVE);
	        m_other = p_other.matcher(htmlStr);
	        htmlStr = m_other.replaceAll(""); //过滤特殊字符标签

	        textStr = htmlStr;
	        
	        try{
	            byte[] bytes = textStr.getBytes("GBK");
	            for(int i = 0;i < bytes.length;i++) {
	                if (bytes[i] > 0 && bytes[i] < 32) 
	                    bytes[i] = 32;
	            }
	            textStr = new String(bytes,"GBK");
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	        }

	    } catch (Exception e) {
	        System.err.println("Html2Text: " + e.getMessage());
	    }	    
	    //textStr = textStr.replaceAll(" ","").replaceAll("　","").replaceAll("，",",").replaceAll("。",".");
	    return textStr;//返回文本字符串
	   }
	
	
	
}
