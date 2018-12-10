package com.sldt.rpt.bip.uicserv.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;
import com.sldt.framework.utils.EntityList;
import com.sldt.rpt.bip.uicserv.dao.IUICServiceDao;
import com.sldt.rpt.bip.uicserv.entity.FdmBaseFild;
import com.sldt.rpt.bip.uicserv.entity.FdmBaseTabl;
import com.sldt.rpt.bip.uicserv.service.UICService;
import com.sldt.rpt.bip.uicserv.util.UICServiceUtil;

@Service("uicService")
public class UICServiceImpl implements UICService {

	@Resource(name="uicServiceDao")
	private IUICServiceDao dao;
	
	@Override
	public List drillRptByParams(PageResults page) {
		return dao.queryByNamedSql(page, "queryAllRptDrill");
	}

	/**
	 * 读取数据生成Excel
	 */
	@Override
	public String createExcel(PageResults page) {
		//Excel文件生成目录
		String excelFilePath = UICServiceUtil.getUICServExcelDir().replace(".", UICServiceUtil.FILE_SEPARATOR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStmp = sdf.format(new Date());
		//限制查询结果为Excel最大条数
		page.setPageSize(UICServiceUtil.EXCEL_MAX_ROW);
		page.setParameter("timeStmp", timeStmp);
		List<String> fileList = new ArrayList<String>();
		
		//获取表中文名称
		String tabLds = page.getParameter("tabLds").toString();
		//获取表名称
		String tablNa = page.getParameter("tablNa").toString();
		//获取字段列表
		List<FdmBaseFild> fildList = (List<FdmBaseFild>) page.getParameter("fildList");
		
		//参数querySql为查询的sql语句    paramObj为参数数组Object[]   showMap为展示字段的列表List<FdmBaseFild>
		Map<String, Object> paraMap = UICServiceUtil.convertParaToParaSql(page, tablNa, fildList);
		
		//设置表中文名称为文件名称
		String fileInxNa = tabLds + "_" + timeStmp;
		
		//判断零时存放文件目录是否存在,不存在则创建
	    if(!new File(excelFilePath.toString()).exists()){
	    	new File(excelFilePath.toString()).mkdirs();
	    }
	    
		int pageNums = 0;
		//初始化遍历计数器
		int i = 1;
		//遍历结果生成Excel文件
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		do{
			//设置查询数据的页码为第i页
			page.setCurrPage(i);
			//设置查询数据的起始行
			page.setStartIndex((i - 1) * page.getPageSize());
			//查询第i页数据，最大行数为650000
//			res = dao.queryByNamedSql(page, "queryAllRptDrill");
			res = dao.queryByParaInfoWithPage(paraMap.get("querySql").toString(), (Object[])paraMap.get("paramObj"), page);
			//获取生成文件数量
			pageNums = (page.getTotalRecs() % page.getPageSize() == 0) ? page.getTotalRecs() / page.getPageSize() : page.getTotalRecs() / page.getPageSize() + 1;
			//将当页数据生成Excel文件
			if(pageNums <= 1) {
			    this.dataToExcel(res, excelFilePath + fileInxNa + UICServiceUtil.FILE_EXCEL_SUFFIX, (List<FdmBaseFild>)paraMap.get("showList"));
			}else {
			    this.dataToExcel(res, excelFilePath + fileInxNa + "_" + i + UICServiceUtil.FILE_EXCEL_SUFFIX, (List<FdmBaseFild>)paraMap.get("showList"));
			    fileList.add(excelFilePath + fileInxNa + "_" + i + UICServiceUtil.FILE_EXCEL_SUFFIX);
			}
			i ++;
			//清除结果集
			res.clear();
		}while( i <= pageNums);
		/**
		 *  1.若数据行数<=65000,则返回单个excel,不生成ZIP压缩文件
		 *  2.若数据行数>65000,则需要生成两个及以上的Excel文件,则返回压缩文件名称
		 * */
		if (pageNums <= 1){
			return fileInxNa + UICServiceUtil.FILE_EXCEL_SUFFIX;
		}else{
			this.batchZipExcel(fileList, excelFilePath + fileInxNa + UICServiceUtil.FILE_ZIP_SUFFIX);
			//清除记录集
			fileList.clear();
			return fileInxNa + UICServiceUtil.FILE_ZIP_SUFFIX;
		}
	}

	/**
	 * 生成第i页的数据文件
	 * @param res
	 * @param fildList 
	 * @param page
	 */
	public void dataToExcel(List<Map<String, Object>> res, String tmpfileName, List<FdmBaseFild> showList){
		FileOutputStream fos = null ;
		//判断fildList是否为空
		if(showList != null && !showList.isEmpty() && showList.size() > 0){
			try {
				String[] assetHeadTemp = new String[showList.size()];
				String[] assetNameTemp = new String[showList.size()];
				//遍历获取展示列表字段
				for(int i = 0; i < showList.size(); i++){
					assetHeadTemp[i] = showList.get(i).getColmds();
					assetNameTemp[i] = showList.get(i).getTabcol();
				}
				
				tmpfileName = new String(tmpfileName.getBytes("GBK"));
				fos = new FileOutputStream(new File(tmpfileName));
				SXSSFWorkbook wb = new SXSSFWorkbook();
//				String[] assetHeadTemp = {"数据日期", "指标编号", "币种","指标频度","机构编号", "指标值", "指标上日值", "指标上月末值", "指标上季末值", "指标上年末值", "指标上期末值"};
//				String[] assetNameTemp = {"ELDT","IXNM","CYCD" ,"IXFQ","BRCH","IXVL","IXDV","IXMV","IXQV","IXYV","IXPV"};
				
				//绘制表头
				Sheet sheet = null;
				CellStyle columnHeadStyle = wb.createCellStyle();
				columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
				columnHeadStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
				columnHeadStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
				columnHeadStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
				columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				columnHeadStyle.setWrapText(true);
				Font f = wb.createFont();// 字体
				f.setFontHeightInPoints((short) 9);// 字号
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//BOLDWEIGHT_BOLD);// 加粗
				columnHeadStyle.setFont(f);
				
				Row row;
				Cell cell;
				sheet = wb.createSheet("sheet");
				row = sheet.createRow(0);
				sheet.createFreezePane(0, 1, 0, 1);
				for(int i=0;i< assetHeadTemp.length;i++){
					cell = row.createCell(i);
					cell.setCellStyle(columnHeadStyle);
					cell.setCellValue(assetHeadTemp[i]);
					sheet.setColumnWidth(i, (int)7000);
				}
				
				//写入数据
				if(res != null && res.size() > 0){
					int rowIndex = 1;
					for(Map<String, Object> map : res){
						row = sheet.createRow(rowIndex ++);
						int index = 0;
	//					Map<String, Object> obj = null;
						for(int i =0; i < assetNameTemp.length; i ++){
							cell = row.createCell(index ++);
	//						obj = res.get(rowIndex);
							cell.setCellValue((map.get(assetNameTemp[i]) == null 
									|| map.get(assetNameTemp[i]).equals("")) ? "" 
											: map.get(assetNameTemp[i]).toString());
						}
					}
				}
				//清除结果集
				res.clear();
	            wb.write(fos);
	            fos.flush();
	            fos.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//清除结果集
				res.clear();
			}
		}
	}
	
	/**
	 * Excel批量打包
	 */
	@Override
	public void batchZipExcel(List<String> tmpFileList, String zipFileName) {
		//获取临时文件
		File srcFile[] = new File[tmpFileList.size()];
		for (int i = 0; i < tmpFileList.size(); i ++){
			srcFile[i] = new File(tmpFileList.get(i));
		}
		
		//压缩文件
		byte[] byt = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            
			for(int i = 0; i < srcFile.length; i ++){
				FileInputStream in = new FileInputStream(srcFile[i]);
				out.putNextEntry(new ZipEntry(srcFile[i].getName()));
				int length ;
				while((length = in.read(byt)) > 0){
					out.write(byt, 0 ,length);
				}
				out.setEncoding("GBK");
				out.closeEntry();
				in.close();
			}
			out.close();
			
			//删除临时文件
			for(int i = 0; i < srcFile.length; i ++){
				if(srcFile[i].exists() && srcFile[i].isFile()){
					srcFile[i].delete();
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  通过tid查询表列信息,返回到界面的jqgrid中动态拼接
	 *  colNames: fildNames,
	 *	colModel: fildCols
	 *  @return [{fildNames,fildCols}]
	 *  @author guihy
	 */
	public String queryFdmBaseFildsInfoByTablcd(PageResults page){
		StringBuilder fildsInfo = new StringBuilder("");
		Map<String, Object> paraMap = page.getParameters();
		String tablcd = paraMap.get("tid").toString();
		List<FdmBaseTabl> tables = new ArrayList();
		List<FdmBaseFild> fildlist = new ArrayList();
		if(null != tablcd && !tablcd.equals("")){
			//设置tablcd参数
			page.setParameter("tablcd", tablcd);
		    
		    EntityList tablEntity = new EntityList();
		    tablEntity.put(FdmBaseTabl.class);
		    //查询tablcd的表信息
		    tables = dao.queryByNamedSqlForPageEntity(page.getParameters(), "queryFdmBaseTablByTablcd", tablEntity);
		    
		    tablEntity.removeAll();
		    tablEntity.put(FdmBaseFild.class);
		    //查询tablcd的列信息
		    fildlist = dao.queryByNamedSqlForPageEntity(page.getParameters(), "queryFdmBaseFildByTablcd", tablEntity);
		    String tablNa = "";
		    String tabLds = "";
		    if(null != tables && tables.size() > 0){
		    	//获取第一个表
		    	FdmBaseTabl table = tables.get(0);
		    	//获取表英文名称
		    	tablNa = table.getTablna();
		    	if(null != tablNa && !tablNa.trim().equals("")){
		    		page.setParameter("tablNa", tablNa);
		    	}
		    	//获取表中文名
		    	tabLds = table.getTablds();
		    	if(null != tabLds && !tabLds.trim().equals("")){
		    		page.setParameter("tabLds", tabLds);
		    	}
		    	//获取表字段英文名
		    	if(null != fildlist && fildlist.size() > 0){
		    		StringBuilder fildNames = new StringBuilder("[");
		    		StringBuilder fildCols = new StringBuilder("[");
		    		fildsInfo.append("{");
		    		for(FdmBaseFild fild : fildlist){
		    			fildNames.append("\"").append(fild.getColmds()).append("\",");
		    			fildCols.append("{\"name\":\"").append(fild.getTabcol()).append("\", \"index\":\"").append(fild.getTabcol()).append("\", \"sortable\":false, \"width\":\""+fild.getClwdth()+"%\", \"align\":\"left\"},");
		    		}
		    		fildNames.deleteCharAt(fildNames.toString().lastIndexOf(","));
		    		fildCols.deleteCharAt(fildCols.toString().lastIndexOf(","));
		    		fildCols.append("]");
		    		fildNames.append("]");
		    		fildsInfo.append("\"fildNames\":").append(fildNames.toString()).append(",").append("\"fildCols\":").append(fildCols.toString()).append("");
		    		fildsInfo.append("}");
		    		page.setParameter("fildList", fildlist);
		    	}
		    }
		}
		return fildsInfo.toString();
	}

	@Override
	public PageVo queryDrillRpt(PageResults page){
//		List res = dao.queryByNamedSql(page, "queryAllRptDrill");
		//获取表名称
		String tablNa = page.getParameter("tablNa").toString();
		//获取字段列表
		List<FdmBaseFild> fildList = (List<FdmBaseFild>) page.getParameter("fildList");
				
		//参数querySql为查询的sql语句    paramObj为参数数组Object[]   showMap为展示字段的列表List<FdmBaseFild>
		Map<String, Object> paraMap = UICServiceUtil.convertParaToParaSql(page, tablNa, fildList);
		List res = dao.queryByParaInfoWithPage(paraMap.get("querySql").toString(), (Object[])paraMap.get("paramObj"), page);
		PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(), page.getTotalRecs());
		pv.setRows(page.getQueryResult());
		return pv;
	}
	
}
