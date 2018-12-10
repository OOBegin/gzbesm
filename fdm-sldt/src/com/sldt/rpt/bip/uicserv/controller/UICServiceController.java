package com.sldt.rpt.bip.uicserv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sldt.framework.common.ControllerHelper;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;
import com.sldt.rpt.bip.uicserv.service.UICService;
import com.sldt.rpt.bip.uicserv.util.UICServiceUtil;

/***
 * 外部接口调用控制类
 * @author guihuaiyu
 *
 */
@Controller
@RequestMapping("/drillRpt.do")
public class UICServiceController {
	private static final Logger logger = LoggerFactory.getLogger(UICServiceController.class );
    
	@Autowired
	UICService uicServ ;
	
	private static Map<String, String> cookieMap = new HashMap<String, String>(); 
	/**
	 * 分页查询所有下钻结果
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=queryDrillRpt")
	@ResponseBody
	public PageVo List(HttpServletRequest request, HttpServletResponse response){
		PageVo pv = null;
		PageResults page = ControllerHelper.buildPage(request);
		Map<String, Object> paraMap = page.getParameters();
		String exeMethod = "";
		try {
			String tablNa = request.getSession().getAttribute("tablNa").toString();
			String tabLds = request.getSession().getAttribute("tabLds").toString();
			List fildList = (List)(request.getSession().getAttribute("fildList"));
			
			if(tablNa != null && !tablNa.trim().equals("")){
				page.setParameter("tablNa", tablNa);
			}
			if(tabLds != null && !tabLds.trim().equals("")){
				page.setParameter("tabLds", tabLds);
			}
			if(fildList != null && !fildList.isEmpty() && fildList.size() > 0){
				page.setParameter("fildList", fildList);
			}
			
			//判断request参数是否为空
			if(null != paraMap && paraMap.size() > 0 && paraMap.containsKey("method")
					&& null != paraMap.get("method") && !paraMap.get("method").equals("")){
				exeMethod = paraMap.get("method").toString();
				//查询明细下钻
				if(exeMethod.toUpperCase().equals("QUERYDRILLRPT")){
					pv = uicServ.queryDrillRpt(page);
			    }else{
			    	pv.setTotal(UICServiceUtil.PAGE_TOTAL_ZERO);
			    	pv.setRows(UICServiceUtil.PAGE_ROW_NULL);
			    	pv.setPage(UICServiceUtil.PAGE_IDX_ZERO);
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pv;
	}
	
	/**
	 * 下载所有下钻结果
	 * 数据量大，分多个文件保存，压缩后导出
	 * 考虑到时效性，服务器性能
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=downloadDrillRpt")
	public void DownLoad(HttpServletRequest request, HttpServletResponse response){
		String cookieKey = "";
		if(request.getParameter("cookieKey") != null && !request.getParameter("cookieKey").equals("")){
			cookieKey = request.getParameter("cookieKey").toString();
			//设置cookieMap,导出按钮禁用
			cookieMap.put(cookieKey, UUID.randomUUID().toString());
		}
		PageResults page = ControllerHelper.buildPage(request);
		
		String tablNa = request.getSession().getAttribute("tablNa").toString();
		String tabLds = request.getSession().getAttribute("tabLds").toString();
		List fildList = (List)(request.getSession().getAttribute("fildList"));
		
		if(tablNa != null && !tablNa.trim().equals("")){
			page.setParameter("tablNa", tablNa);
		}
		if(tabLds != null && !tabLds.trim().equals("")){
			page.setParameter("tabLds", tabLds);
		}
		if(fildList != null && !fildList.isEmpty() && fildList.size() > 0){
			page.setParameter("fildList", fildList);
		}
		
		//excek文件生成目录
		String excelFilePath = UICServiceUtil.getUICServExcelDir().replace(".", File.separator);
		String agent = (String)request.getHeader("USER-AGENT"); 
		OutputStream output = null;
		FileInputStream in = null;
		try {
			//生成Excel数据文件
			String fileName = uicServ.createExcel(page);
			File file = new File(excelFilePath + fileName);
			
			//中文文件名称兼容浏览器
			if(agent != null && agent.indexOf("Firefox") != -1) {
			    // firefox      
			    fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}else{
			    // others
			    fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");

			output = response.getOutputStream();
			in = new FileInputStream(file);
			byte[] byt = new byte[4096];
			int length;
			while((length = in.read(byt)) != -1){
				output.write(byt, 0, length);
			}
			output.flush();
			in.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				output.flush();
				in.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//清除cookieMap,导出按钮启用
			if(cookieMap != null && !cookieMap.isEmpty() && cookieMap.size() > 0 && cookieMap.containsKey(cookieKey)) {
				cookieMap.remove(cookieKey);
			}
		}
	}
	
	/**
	 * 通过tid查询数据表配置的列表信息
	 */
	@RequestMapping(params="method=queryFildsInfo")
	public void queryFildsInfo(HttpServletRequest request, HttpServletResponse response){
		PageResults page = ControllerHelper.buildPage(request);
		//通过tid参数查询列表信息
		String fildsInfo = uicServ.queryFdmBaseFildsInfoByTablcd(page);
		
		//设置表中文名称
		if(page.getParameter("tabLds") != null && !page.getParameter("tabLds").equals("")){
			request.getSession().setAttribute("tabLds", page.getParameter("tabLds"));
		}
		//设置表名称
		if(page.getParameter("tablNa") != null && !page.getParameter("tablNa").equals("")){
			request.getSession().setAttribute("tablNa", page.getParameter("tablNa"));
		}
		//设置表列字段
		if(page.getParameter("fildList") != null ){
			request.getSession().setAttribute("fildList", page.getParameter("fildList"));
		}
        
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			//以json形式动态输出jqgrid中的colNames、colModes
			out.print(fildsInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params="method=getCookie", method=RequestMethod.POST)
	@ResponseBody
	public String getCookie(String cookieKey, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		String key = "success";
		//判断cookieMap中是否有cookieKey,若无则表示文件已经生成完毕
		if(cookieMap == null || cookieMap.isEmpty() || cookieMap.size() < 1 || !cookieMap.containsKey(cookieKey) || cookieMap.get(cookieKey) == null || cookieMap.get(cookieKey).equals("")){
			flag = true;
		}
		map.put(key, flag);
		return JSON.toJSONString(map);
	}
}
