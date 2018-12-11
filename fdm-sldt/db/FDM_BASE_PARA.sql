prompt PL/SQL Developer import file
prompt Created on 2018年12月10日 by guihuaiyu
set feedback off
set define off
prompt Creating FDM_BASE_PARA...
create table FDM_BASE_PARA
(
  paratp VARCHAR2(255),
  paracd VARCHAR2(32),
  parana VARCHAR2(1024),
  parasq NUMBER
)
;
comment on column FDM_BASE_PARA.paratp
  is '参数类型';
comment on column FDM_BASE_PARA.paracd
  is '参数代码';
comment on column FDM_BASE_PARA.parana
  is '参数值';
comment on column FDM_BASE_PARA.parasq
  is '参数排序';

prompt Loading FDM_BASE_PARA...
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', '%', '币种', 1);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'AUD', '澳大利亚', 2);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'CAD', '加拿大', 3);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'CNY', '人民币', 4);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'EUR', '欧元', 5);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'GBP', '英国', 6);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'JPY', '日元', 7);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'USD', '美元', 8);
insert into FDM_BASE_PARA (paratp, paracd, parana, parasq)
values ('crcycd', 'HKD', '港币', 9);
commit;
prompt 9 records loaded
set feedback on
set define on
prompt Done.
