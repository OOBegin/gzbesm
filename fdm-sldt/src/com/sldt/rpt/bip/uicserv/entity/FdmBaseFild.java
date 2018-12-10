package com.sldt.rpt.bip.uicserv.entity;

public class FdmBaseFild implements java.io.Serializable{
	
	private String tablcd;//表代码
	private String tabcol;//字段映射代码
	private String colmna;//字段名称
    private String colmds;//字段中文名
    private String isclpk;//是否主键  1:是 0:否
    private String datatp;//数据类型DATE  STRING
    private String edtwgt;//编辑控件DATE  TEXT  SELECT
    private String valdtp;//验证类型REQUIRED
    private String datafm;//数据格式
    private String issrch;//是否显示在查询 1:是 0:否
    private String islist;//是否显示在数据列表 1:是 0:否
    private String isedit;//是否显示在编辑 1:是 0:否
    private String autogn;//自动生成值函数
    private String schcdn;//查询模式
    private String clwdth;//列表宽度
    private String shwodr;//显示序号
    private String odrmod;//排序模式
    private String valexp;//特殊正则表达式
    private String canedt;//是否可编辑
    private String collen;//字段长度
    
	public String getTablcd() {
		return tablcd;
	}
	public void setTablcd(String tablcd) {
		this.tablcd = tablcd;
	}
	public String getTabcol() {
		return tabcol;
	}
	public void setTabcol(String tabcol) {
		this.tabcol = tabcol;
	}
	public String getColmna() {
		return colmna;
	}
	public void setColmna(String colmna) {
		this.colmna = colmna;
	}
	public String getColmds() {
		return colmds;
	}
	public void setColmds(String colmds) {
		this.colmds = colmds;
	}
	public String getIsclpk() {
		return isclpk;
	}
	public void setIsclpk(String isclpk) {
		this.isclpk = isclpk;
	}
	public String getDatatp() {
		return datatp;
	}
	public void setDatatp(String datatp) {
		this.datatp = datatp;
	}
	public String getEdtwgt() {
		return edtwgt;
	}
	public void setEdtwgt(String edtwgt) {
		this.edtwgt = edtwgt;
	}
	public String getValdtp() {
		return valdtp;
	}
	public void setValdtp(String valdtp) {
		this.valdtp = valdtp;
	}
	public String getDatafm() {
		return datafm;
	}
	public void setDatafm(String datafm) {
		this.datafm = datafm;
	}
	public String getIssrch() {
		return issrch;
	}
	public void setIssrch(String issrch) {
		this.issrch = issrch;
	}
	public String getIslist() {
		return islist;
	}
	public void setIslist(String islist) {
		this.islist = islist;
	}
	public String getIsedit() {
		return isedit;
	}
	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}
	public String getAutogn() {
		return autogn;
	}
	public void setAutogn(String autogn) {
		this.autogn = autogn;
	}
	public String getSchcdn() {
		return schcdn;
	}
	public void setSchcdn(String schcdn) {
		this.schcdn = schcdn;
	}
	public String getClwdth() {
		return clwdth;
	}
	public void setClwdth(String clwdth) {
		this.clwdth = clwdth;
	}
	public String getShwodr() {
		return shwodr;
	}
	public void setShwodr(String shwodr) {
		this.shwodr = shwodr;
	}
	public String getOdrmod() {
		return odrmod;
	}
	public void setOdrmod(String odrmod) {
		this.odrmod = odrmod;
	}
	public String getValexp() {
		return valexp;
	}
	public void setValexp(String valexp) {
		this.valexp = valexp;
	}
	public String getCanedt() {
		return canedt;
	}
	public void setCanedt(String canedt) {
		this.canedt = canedt;
	}
	public String getCollen() {
		return collen;
	}
	public void setCollen(String collen) {
		this.collen = collen;
	}
	public FdmBaseFild(String tablcd, String tabcol, String colmna,
			String colmds, String isclpk, String datatp, String edtwgt,
			String valdtp, String datafm, String issrch, String islist,
			String isedit, String autogn, String schcdn, String clwdth,
			String shwodr, String odrmod, String valexp, String canedt, String collen) {
		super();
		this.tablcd = tablcd;
		this.tabcol = tabcol;
		this.colmna = colmna;
		this.colmds = colmds;
		this.isclpk = isclpk;
		this.datatp = datatp;
		this.edtwgt = edtwgt;
		this.valdtp = valdtp;
		this.datafm = datafm;
		this.issrch = issrch;
		this.islist = islist;
		this.isedit = isedit;
		this.autogn = autogn;
		this.schcdn = schcdn;
		this.clwdth = clwdth;
		this.shwodr = shwodr;
		this.odrmod = odrmod;
		this.valexp = valexp;
		this.canedt = canedt;
		this.collen = collen;
	}
	public FdmBaseFild() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
