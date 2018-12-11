--------------------------------------------------
-- Export file for user PORTAL                  --
-- Created by guihuaiyu on 2018/12/11, 18:38:24 --
--------------------------------------------------

set define off
spool INX_KPI_VAL_TEST_1.log

prompt
prompt Creating table INX_KPI_VAL_TEST
prompt ===============================
prompt
create table INX_KPI_VAL_TEST
(
  etl_date        DATE,
  index_num       VARCHAR2(10),
  crcy_cd         VARCHAR2(10),
  index_freq      VARCHAR2(10),
  brch_no         VARCHAR2(60),
  index_val       NUMBER(30,4),
  index_lst_d_val NUMBER(30,4),
  index_lst_m_val NUMBER(30,4),
  index_lst_q_val NUMBER(30,4),
  index_lst_y_val NUMBER(30,4),
  index_lst_p_val NUMBER(30,4)
)
;
create index IDX_DRILL_RPT_1 on INX_KPI_VAL_TEST (ETL_DATE, INDEX_NUM, CRCY_CD);
create index IDX_DRILL_RPT_2 on INX_KPI_VAL_TEST (ETL_DATE, INDEX_NUM, CRCY_CD, BRCH_NO);


spool off
