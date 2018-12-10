package com.sldt.rpt.bip.uicserv.entity;

public class FdmBaseTabl implements java.io.Serializable{

	private String tablcd;//列表代码
	private String tablna;//数据库表名
	private String tablds;//列表中文描述
	private String grupid;//列表列表所属分组编号
	private String status;//列表状态 1:可用  0:不可用
	private String ismgtb;//是否列表管理表 1:是 0:否 (管理表不参与界面维护)
	public String getTablcd() {
		return tablcd;
	}
	public void setTablcd(String tablcd) {
		this.tablcd = tablcd;
	}
	public String getTablna() {
		return tablna;
	}
	public void setTablna(String tablna) {
		this.tablna = tablna;
	}
	public String getTablds() {
		return tablds;
	}
	public void setTablds(String tablds) {
		this.tablds = tablds;
	}
	public String getGrupid() {
		return grupid;
	}
	public void setGrupid(String grupid) {
		this.grupid = grupid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsmgtb() {
		return ismgtb;
	}
	public void setIsmgtb(String ismgtb) {
		this.ismgtb = ismgtb;
	}
	public FdmBaseTabl(String tablcd, String tablna, String tablds,
			String grupid, String status, String ismgtb) {
		super();
		this.tablcd = tablcd;
		this.tablna = tablna;
		this.tablds = tablds;
		this.grupid = grupid;
		this.status = status;
		this.ismgtb = ismgtb;
	}
	public FdmBaseTabl() {
		super();
	}
}
