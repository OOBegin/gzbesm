package com.sldt.rpt.bip.uicserv.dao;

import java.util.List;
import java.util.Map;

import com.sldt.framework.common.PageResults;
import com.sldt.framework.utils.EntityList;

public interface IUICServiceDao {

	/**
	 * 通过namedSql(mybatis配置)、参数map、返回实体的类型tablEntity查询结果集
	 * @param parameters
	 * @param namedSql
	 * @param tablEntity
	 * @return 所有结果集
	 */
	List queryByNamedSqlForPageEntity(Map<String, Object> parameters, String namedSql,
			EntityList tablEntity);

	/**
	 * 通过namedSql(mybatis配置)查询结果集
	 * @param page
	 * @param namedSql
	 * @return 分页结果集
	 */
	List queryByNamedSql(PageResults page, String namedSql);

	/**
	 * 通过查询SQL、参数数组查询结果集
	 * @param sql      查询SQL
	 * @param paraMap  参数数组
	 * @param page     page信息(包含分页、页码、页size等信息)
	 * @return  分页结果集
	 */
	List<Map<String, Object>> queryByParaInfoWithPage(String sql,
			Object[] paraMap, PageResults page);

}
