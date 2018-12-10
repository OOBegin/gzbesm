package com.sldt.rpt.bip.uicserv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sldt.framework.common.PageResults;
import com.sldt.framework.orm.hibernate.dao.BaseDao;
import com.sldt.framework.utils.EntityList;
import com.sldt.rpt.bip.uicserv.dao.IUICServiceDao;

@Component("uicServiceDao")
public class UICServiceDaoImpl implements IUICServiceDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> queryByNamedSql(PageResults page, String namedSql) {
		// TODO Auto-generated method stub
		Map<String, Object> paraMap = page.getParameters();
		String sql = baseDao.getSQL(namedSql, paraMap);
        
		Object[] paramObj = baseDao.getParam(namedSql, paraMap);
		List<Map<String, Object>> mapList = baseDao.queryPageMap(sql,paramObj,page);
		return mapList;
	}

	@Override
	public List queryByNamedSqlForPageEntity(Map<String, Object> paraMap, String namedSql, EntityList list) {
		// TODO Auto-generated method stub
		String sql = baseDao.getSQL(namedSql, paraMap);
		
		Object[] paramObj = baseDao.getParam(namedSql, paraMap);
		List<Map<String, Object>> entityList = baseDao.queryListForEntity(sql, paramObj, list);
		return entityList;
	}

	@Override
	public List<Map<String, Object>> queryByParaInfoWithPage(String sql, Object[] paramObj, PageResults page) {
        List<Map<String, Object>> mapList = baseDao.queryPageMap(sql, paramObj, page);
        return mapList;
	}
}
