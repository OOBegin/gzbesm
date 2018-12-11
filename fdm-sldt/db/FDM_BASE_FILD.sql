prompt PL/SQL Developer import file
prompt Created on 2018年12月4日 by guihuaiyu
set feedback off
set define off
prompt Creating FDM_BASE_FILD...
create table FDM_BASE_FILD
(
  tablcd       VARCHAR2(64) not null,
  tabcol       VARCHAR2(64) not null,
  colmna       VARCHAR2(64),
  colmds       VARCHAR2(128),
  isclpk       VARCHAR2(1),
  datatp       VARCHAR2(64),
  edtwgt       VARCHAR2(64),
  valdtp       VARCHAR2(128),
  datafm       VARCHAR2(64),
  issrch       VARCHAR2(1),
  islist       VARCHAR2(1),
  isedit       VARCHAR2(1),
  autogn       VARCHAR2(128),
  schcdn       VARCHAR2(32),
  clwdth       NUMBER,
  shwodr       NUMBER,
  odrmod       VARCHAR2(32),
  valexp       VARCHAR2(512),
  canedt       VARCHAR2(1),
  collen       NUMBER,
  islinked     VARCHAR2(4),
  linkedtab    VARCHAR2(50),
  linkedcol    VARCHAR2(50),
  linkedtrgcol VARCHAR2(200),
  linkedsrccol VARCHAR2(200)
)
;
comment on table FDM_BASE_FILD
  is '自定义列表列';
comment on column FDM_BASE_FILD.tablcd
  is '列表代码';
comment on column FDM_BASE_FILD.tabcol
  is '列表数据项代码';
comment on column FDM_BASE_FILD.colmna
  is '数据库字段名';
comment on column FDM_BASE_FILD.colmds
  is '中文描述';
comment on column FDM_BASE_FILD.isclpk
  is '是否主键,1:是,0:否';
comment on column FDM_BASE_FILD.datatp
  is '数据类型';
comment on column FDM_BASE_FILD.edtwgt
  is '编辑控件';
comment on column FDM_BASE_FILD.valdtp
  is '验证类型';
comment on column FDM_BASE_FILD.datafm
  is '数据格式';
comment on column FDM_BASE_FILD.issrch
  is '是否显示在查询,1:是,0:否';
comment on column FDM_BASE_FILD.islist
  is '是否显示在列表,1:是,0:否';
comment on column FDM_BASE_FILD.isedit
  is '是否显示在编辑,1:是,0:否';
comment on column FDM_BASE_FILD.autogn
  is '自动生成值函数';
comment on column FDM_BASE_FILD.schcdn
  is '查询条件';
comment on column FDM_BASE_FILD.clwdth
  is '在列表中宽度';
comment on column FDM_BASE_FILD.shwodr
  is '显示的序号';
comment on column FDM_BASE_FILD.odrmod
  is '排序模式';
comment on column FDM_BASE_FILD.valexp
  is '特殊验证正则表达式';
comment on column FDM_BASE_FILD.canedt
  is '是否可以编辑';
comment on column FDM_BASE_FILD.collen
  is '字段长度';
comment on column FDM_BASE_FILD.islinked
  is '是否是自联取 ( 1: 是 0: 否 )';
comment on column FDM_BASE_FILD.linkedtab
  is '关联表';
comment on column FDM_BASE_FILD.linkedcol
  is '关联字段';
comment on column FDM_BASE_FILD.linkedtrgcol
  is '自动关联取目标字段';
comment on column FDM_BASE_FILD.linkedsrccol
  is '自动关联取源表字段';

prompt Loading FDM_BASE_FILD...
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'ELDT', 'ETL_DATE', '数据日期', '0', 'DATE', 'DATE', 'REQUIRED', '%Y-%m-%d', '1', '1', '1', null, '=', 80, 1, 'desc', 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXNM', 'INDEX_NUM', '指标编号', '0', 'STRING', 'TEXT', 'REQUIRED', null, '1', '1', '1', null, '=', 80, 2, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'CYCD', 'CRCY_CD', '币种', '0', 'STRING', 'SELECT', 'REQUIRED', null, '1', '1', '1', null, '=', 80, 3, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXFQ', 'INDEX_FREQ', '指标频度', '0', 'STRING', 'SELECT', 'REQUIRED', null, null, '1', '1', null, '=', 80, 4, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'BRCH', 'BRCH_NO', '机构编号', '0', 'STRING', 'TEXT', 'REQUIRED', null, '1', '1', '1', null, '=', 80, 5, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXVL', 'INDEX_VAL', '指标值', '0', 'STRING', 'TEXT', 'REQUIRED', '#.##', null, '1', '1', null, '=', 80, 6, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXDV', 'INDEX_LST_D_VAL', '上日指标值', '0', 'STRING', 'TEXT', 'REQUIRED', '#.##', null, '1', '1', null, '=', 80, 7, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXMV', 'INDEX_LST_M_VAL', '上月末指标值', '0', 'STRING', 'TEXT', 'REQUIRED', '#.##', null, '1', '1', null, '=', 80, 8, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXQV', 'INDEX_LST_Q_VAL', '上季末指标值', '0', 'STRING', 'TEXT', 'REQUIRED', '#.##', null, '1', '1', null, '=', 80, 9, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXYV', 'INDEX_LST_Y_VAL', '上年末指标值', '0', 'STRING', 'TEXT', 'REQUIRED', '#.##', null, '1', '1', null, '=', 80, 10, null, 'REQUIRED', '1', null, null, null, null, null, null);
insert into FDM_BASE_FILD (tablcd, tabcol, colmna, colmds, isclpk, datatp, edtwgt, valdtp, datafm, issrch, islist, isedit, autogn, schcdn, clwdth, shwodr, odrmod, valexp, canedt, collen, islinked, linkedtab, linkedcol, linkedtrgcol, linkedsrccol)
values ('DRILLDETAIL', 'IXPV', 'INDEX_LST_P_VAL', '上期末指标值', '0', 'STRING', 'TEXT', 'REQUIRED', '#.##', null, '1', '1', null, '=', 80, 11, null, 'REQUIRED', '1', null, null, null, null, null, null);
commit;
prompt 11 records loaded
set feedback on
set define on
prompt Done.
