package com.sldt.rpt.bip.uicserv.service;

import java.util.List;

import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;

public interface UICService {

	List drillRptByParams(PageResults page);

	String createExcel(PageResults page);

	void batchZipExcel(List<String> fileList, String zipFileName);

	PageVo queryDrillRpt(PageResults page);

	String queryFdmBaseFildsInfoByTablcd(PageResults page);


}
