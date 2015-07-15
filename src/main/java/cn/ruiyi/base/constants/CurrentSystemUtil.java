/**
 * <pre>
 * Title: 		CurrentSystemUtil.java
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.constants.CurrentSystemUtil
 * Author:		Administrator
 * Create:	 	2013-1-31 下午05:12:43
 * Copyright: 	Copyright (c) 2013
 * Company:		
 * <pre>
 */
package cn.ruiyi.base.constants;

import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.util.StringUtil;

/**
 * 获取当前系统工具类
 * 
 * @author 郭世江
 * @version 1.0, 2013-1-31
 */
public class CurrentSystemUtil
{
	/**
	 * 枚举可使用系统
	 * 系统代号规则：非法: -1; 公用为: 0;其他系统系统分别以1为基数自增。
	 * 
	 */
	private enum CurrentSystem {
		
		PUBLIC("public",0), SYSTEM_BASE("system_base", 1), UNIS_COMMERCE("unis_commerce", 2);
		
		// 成员变量
		private String name;
		private int index;
		
		// 构造方法
		private CurrentSystem(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
	    // 普通方法  
		private static int getIndex(String name) { 
			int retInt = -1;
	        for (CurrentSystem c : CurrentSystem.values()) {  
	            if (name.equalsIgnoreCase(c.getName())) {  
	            	retInt = c.getIndex();  
	            	break;
	            }  
	        }  
	        return retInt;  
	    }  
		
	    // get set 方法  
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    public int getIndex() {  
	        return index;  
	    }  
	    public void setIndex(int index) {  
	        this.index = index;  
	    }  

	}
	
	//获取当前系统
	public static int getCurrentSystem(){
		String CURRENT_SYSTEM = StringUtil.trim(PathUtil.getConfigResource("CURRENT_SYSTEM"));
		return CurrentSystem.getIndex(CURRENT_SYSTEM);
	}
	
	public static void main(String[] args)
	{
		CurrentSystemUtil.getCurrentSystem();
	}
}
