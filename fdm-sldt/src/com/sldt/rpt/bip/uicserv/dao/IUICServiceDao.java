package com.sldt.rpt.bip.uicserv.dao;

import java.util.List;
import java.util.Map;

import com.sldt.framework.common.PageResults;
import com.sldt.framework.utils.EntityList;

public interface IUICServiceDao {

	/**
	 * ͨ��namedSql(mybatis����)������map������ʵ�������tablEntity��ѯ�����
	 * @param parameters
	 * @param namedSql
	 * @param tablEntity
	 * @return ���н����
	 */
	List queryByNamedSqlForPageEntity(Map<String, Object> parameters, String namedSql,
			EntityList tablEntity);

	/**
	 * ͨ��namedSql(mybatis����)��ѯ�����
	 * @param page
	 * @param namedSql
	 * @return ��ҳ�����
	 */
	List queryByNamedSql(PageResults page, String namedSql);

	/**
	 * ͨ����ѯSQL�����������ѯ�����
	 * @param sql      ��ѯSQL
	 * @param paraMap  ��������
	 * @param page     page��Ϣ(������ҳ��ҳ�롢ҳsize����Ϣ)
	 * @return  ��ҳ�����
	 */
	List<Map<String, Object>> queryByParaInfoWithPage(String sql,
			Object[] paraMap, PageResults page);

}
