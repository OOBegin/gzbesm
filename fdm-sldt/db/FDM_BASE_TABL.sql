prompt PL/SQL Developer import file
prompt Created on 2018年12月4日 by guihuaiyu
set feedback off
set define off
prompt Creating FDM_BASE_TABL...
create table FDM_BASE_TABL
(
  tablcd VARCHAR2(64) not null,
  tablna VARCHAR2(64),
  tablds VARCHAR2(128),
  grupid VARCHAR2(32),
  status VARCHAR2(1),
  ismgtb VARCHAR2(1)
)
;
comment on table FDM_BASE_TABL
  is '自定义列表';
comment on column FDM_BASE_TABL.tablcd
  is '列表代码';
comment on column FDM_BASE_TABL.tablna
  is '数据库表名';
comment on column FDM_BASE_TABL.tablds
  is '列表中文描述';
comment on column FDM_BASE_TABL.grupid
  is '列表列表所属分组编号';
comment on column FDM_BASE_TABL.status
  is '列表状态,M:可补录, L:锁定';
comment on column FDM_BASE_TABL.ismgtb
  is '是否列表管理表(管理表不参与界面维护),1:是,0:否';

prompt Loading FDM_BASE_TABL...
insert into FDM_BASE_TABL (tablcd, tablna, tablds, grupid, status, ismgtb)
values ('DRILLDETAIL', 'INX_KPI_VAL_TEST', '账务追溯下钻明细', '001', 'M', '0');
commit;
prompt 1 records loaded
set feedback on
set define on
prompt Done.
