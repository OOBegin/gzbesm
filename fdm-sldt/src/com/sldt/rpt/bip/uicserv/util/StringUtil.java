package com.sldt.rpt.bip.uicserv.util;

import java.util.Collection;
import java.util.Map;

public class StringUtil {
	
	/**
	 * 判断字符串是否为空或者null
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str){
		boolean vali = false;
		
		if (null == str){
			vali = true;
		}
		
		if(null == str || "".equals(str)){
			vali = true;
		}
		
		if(null == str || str.length() <= 0){
			vali = true;
		}
		
		if(null == str || str.isEmpty()){
			vali = true;
		}
		return vali;
	}
	
	/**
	 * 判断对象是否为空或者null
	 * @param obj
	 * @return
	 */
	public static boolean isObjNullOrEmpty(Object obj){
		if (obj == null)
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();
        
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isObjNullOrEmpty(object[i])) {  
                    empty = false;
                    break;  
                }  
            }  
            return empty;  
        }
        return false;
	}
}