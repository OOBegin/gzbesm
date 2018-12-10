package com.sldt.rpt.bip.uicserv.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sldt.framework.common.PageResults;
import com.sldt.rpt.bip.app.utils.AppPropertiesUtil;
import com.sldt.rpt.bip.uicserv.entity.FdmBaseFild;

public class UICServiceUtil {
	
	//Excel最大条数
	public final static int EXCEL_MAX_ROW = 65000;
	//分页总行数
	public final static int PAGE_TOTAL_ZERO = 0;
	//分页数据为空
	public final static List PAGE_ROW_NULL = null;
	//分页编号为0
	public final static int PAGE_IDX_ZERO = 0;
	//系统路径分隔符
	public final static String FILE_SEPARATOR = String.valueOf(File.separatorChar);
	//zip压缩文件名称后缀
	public final static String FILE_ZIP_SUFFIX = ".zip";
	//Excel文件名称后缀
	public final static String FILE_EXCEL_SUFFIX = ".xls";
	
	//将字段转换为相应格式的对象  DATE日期   DCIMAL数字
	public static String convertDataByValue(Object value, String dataTp, String dataFm){
		String obj = value.toString();
		if(StringUtil.isNullOrEmpty(value.toString())){
			if(!StringUtil.isNullOrEmpty(dataTp)){
				if(dataTp.toUpperCase().equals("DATE")){
					return null;
				}
				if(dataTp.toUpperCase().equals("DECIMAL")){
					return "0.00";
				}
				return "";
			}else{
				return null;
			}
		}
		
		if(!StringUtil.isNullOrEmpty(dataTp)){
			if(dataTp.toUpperCase().equals("DATE")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(!StringUtil.isNullOrEmpty(dataFm)){
					sdf.applyPattern(dataFm);
				}
				obj = sdf.format(value);
			}else if(dataTp.toUpperCase().equals("DECIMAL")){
				DecimalFormat df = new DecimalFormat("#.##");
				if(!StringUtil.isNullOrEmpty(dataFm)){
					df.applyPattern(dataFm);
				}
				obj = df.format(value);
			}
		}
		return obj;
	}
	
	//Excel导出目录
	public static String getUICServExcelDir() {
		return AppPropertiesUtil.getValue("uicserv.excel.dir");
	}
	
	//获取所有的参数列表   字典值
	public static Map<String, List<Object>> queryAllParamToHashMap(){
		Map<String, List<Object>> paraMap = new HashMap<String, List<Object>>();
		
		return paraMap;
	}
	
	/**
	 * 通过参数返回SQL以及相关参数
	 * @param page
	 * @param tablNa    表名称
	 * @param tabLds    表英文名称
	 * @param fildList    表字段列表
	 * @return    返回查询表SQL、参数数组、显示的字段列表
	 */
    public static Map<String, Object> convertParaToParaSql(PageResults page, String tablNa, List<FdmBaseFild> fildList){
    	Map<String, Object> resMap = new HashMap<String, Object>();
    	
    	//展示的字段列表,用于在toExcel中直接获取
    	List<FdmBaseFild> showList = new ArrayList<FdmBaseFild>();
        //获取参数列表
    	Map<String, Object> paraMap = page.getParameters();
    	//保存page参数中再fildList中的字段信息
    	StringBuilder searchSql = new StringBuilder(" WHERE 1 = 1 ");
    	StringBuilder querySql = new StringBuilder("SELECT ");
    	StringBuilder orderSql = new StringBuilder(" ORDER BY ");
    	//创建SQL参数数组
    	List<Object> paramList = new ArrayList<Object>();
    	Object[] paramObj = {};
        
    	if(fildList != null && fildList.size() > 0 && !StringUtil.isNullOrEmpty(tablNa)) {
	    	for(FdmBaseFild fild : fildList) {
	    		//判断字段是否允许展示    1:是  其他为否   默认为1
	    		if(!StringUtil.isNullOrEmpty(fild.getIslist()) && fild.getIslist().trim().equals("1")) {
    				if(!StringUtil.isNullOrEmpty(fild.getDatatp()) && fild.getDatatp().equalsIgnoreCase("DATE")) {
    					//若当前字段配置为日期类型,则查询条件必须转换成日期类型    日期类型必须转换成yyyy-MM-dd类型
    					querySql.append(" TO_CHAR(T.").append(fild.getColmna()).append(",'"+fild.getDatafm()+"')").append(" AS ").append(fild.getTabcol()).append(",\n");
    				}else if (!StringUtil.isNullOrEmpty(fild.getDatatp()) && fild.getDatatp().equalsIgnoreCase("DECIMAL")) {
    					//若当前字段配置为数值类型,则查询SQL中必须转成数值类型的字符串    数值类型必须转换成#,###.##类型
    					querySql.append(" TO_CHAR(T.").append(fild.getColmna()).append(",'"+fild.getDatafm()+"')").append(" AS ").append(fild.getTabcol()).append(",\n");
    				}else{
	    			    querySql.append(" T.").append(fild.getColmna()).append(" AS ").append(fild.getTabcol()).append(",\n");
    				}
	    			showList.add(fild);
	    		}
	    		
	    		//判断字段是否允许搜索
	    		if(!StringUtil.isNullOrEmpty(fild.getIssrch()) && fild.getIssrch().trim().equals("1")) {
	    			//判断page参数中是否有该字段查询条件    参数名称大小写不敏感  但是必须全为大写或者全为小写
	    			if(paraMap.containsKey(fild.getTabcol().toUpperCase()) || paraMap.containsKey(fild.getTabcol().toLowerCase())) {
	    				//若当前字段配置为日期类型,则查询条件必须转换成日期类型    日期类型必须转换成yyyy-MM-dd类型
	    				if(!StringUtil.isNullOrEmpty(fild.getDatatp()) && fild.getDatatp().equalsIgnoreCase("DATE")) {
	    					searchSql.append("\n   AND T.").append(fild.getColmna()).append(" = TO_DATE(?,'YYYY-MM-DD')");
	    				}
	    				/*//若当前字段配置为数值类型,则查询条件必须转换成数值类型
	    				else if(fild.getDatatp() != null && !fild.getDatatp().equals("") && fild.getDatatp().equalsIgnoreCase("DECIMAL")) {
	    					
	    				}*/
	    				else {
	    				    searchSql.append("\n   AND T.").append(fild.getColmna()).append(" = ?");
	    				}
	    				paramList.add(StringUtil.isObjNullOrEmpty(paraMap.get(fild.getTabcol().toUpperCase())) ? paraMap.get(fild.getTabcol().toLowerCase()) : paraMap.get(fild.getTabcol().toUpperCase()));
	    			}
	    		}
	    		
	    		//判断字段排序的模式    desc:逆序   asc:为顺序
	    		if(!StringUtil.isNullOrEmpty(fild.getOdrmod())) {
	    			//判断是不是排序的第一个字段
	    			orderSql.append("T.").append(fild.getColmna()).append(" ").append(fild.getOdrmod()).append(", ");
	    		}
	    	}
	    	querySql.deleteCharAt(querySql.lastIndexOf(","));
	    	if(orderSql.toString().equals(" ORDER BY ")) {
	    		orderSql.append("1");
	    	}else if(orderSql.indexOf(",") > -1) {
	    		orderSql.deleteCharAt(orderSql.lastIndexOf(","));
	    	}
	    	querySql.append("  FROM ").append(tablNa).append(" T");
	    	querySql.append("\n").append(searchSql.toString()).append("\n").append(orderSql.toString());
	    	//参数列表转换成数组
	    	paramObj = paramList.toArray();
    	}else{
    		//若fildList为空,则返回 SELECT NULL FROM DUAL T WHERE 1 = 1 ORDER BY 1
    		querySql.append(" NULL FROM DUAL T ");
    	}
    	resMap.put("querySql", querySql);
    	resMap.put("paramObj", paramObj);
    	resMap.put("showList", showList);
    	return resMap;
    }
    
    /*public static void main(String[] args){
    	Date date = new Date(Calendar.getInstance().getTimeInMillis());
    	Float dem = new Float("-1230.986");
    	System.out.println(convertDataByValue(date, "DATE", "yyyy-MM-dd"));
    	System.out.println(convertDataByValue(dem, "DECIMAL", "#.##"));
    }*/
}

