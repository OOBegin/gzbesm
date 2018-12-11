<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=IE8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/page/drillRpt/js/commonLinkScript.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryForm/jquery.form.js"></script>
<title>追溯报表明细</title>
<%
    String querString = request.getQueryString();
    String uri = request.getRequestURI().substring(request.getContextPath().length());
%>
<script type="text/javascript">
    var queryString = "<%=querString%>";
    var context = "<%=request.getContextPath()%>";
</script>
</head>

<body class="row-fluid">
   <!-- <div class="ui-layout-west">
		<div id="roleTree" class="ztree"></div>
	</div> -->
	<div id="ui-layout-center" class="ui-layout-center" style="overflow: hidden;border: 0px;">
		<div id="managerContent1">
			<div>
				<form id="alertFormId" name="alertForm" class="form-inline">
				<!-- <input type="text" id="pRoleId" name="pRoleId" value="0000" class="input-small span4" style="display:none;"> -->
		            <div class="control-group controls-row">
					  <!-- <label class="span2" for="roleName">角色名称：</label> --> 
					  <!-- <input oninput="doSearch();" type="text" id="searchRoleNm"  class="input-small span4" placeholder="请输入角色名称..."> -->
					  <button id="downBtn" class="button button-info ipt_button" type="button" onclick="doExport()"><i class="fa fa-download icolor"></i> 导 出</button>
					</div>
				</form>
			</div>
			<table id="grid-table"></table>
			<div id="grid-pager"></div>
		</div>
	</div>
</body>
    <script type="text/javascript" src="<%=request.getContextPath()%>/page/drillRpt/js/redirect.js"></script>
</html>