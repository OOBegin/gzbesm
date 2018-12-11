$(document).ready(function() {
	var fildNames = "";
	var fildCols = "";
	var str = queryString.replace(/queryDrillRpt/g,"queryFildsInfo");
	//ajax请求动态的表头和表字段
	$.ajax({
		type: "POST",
        url:  context+"/drillRpt.do?"+str,
        dataType: "json",
        async:false,
        success: function (msg){
        	fildNames = msg.fildNames;
        	fildCols = msg.fildCols;
        	queryDataDetail(fildNames,fildCols);
        }
	});
     
});

function queryDataDetail(fildNames, fildCols){
	//布局设置
	$("body").layout({
		applyDemoStyles:true,
		tips:{
			Open:'展开',
			Close:'隐藏',
			Resize:	'调整'
		}
	});
	
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var gridOffsetTop = document.getElementById("grid-table").offsetTop;
	var windowHeight = top.document.documentElement.clientHeight;
	var girdHeigth = windowHeight-gridOffsetTop-280;
	girdHeigth = girdHeigth>500?girdHeigth:500;
	jQuery(grid_selector).jqGrid({
		//data: grid_data,
/*		page : 1,
		datatype: "json",
		url: context + '/drillRpt.do?method=queryDrillRpt'+'&'+queryString,*/
		datatype: "local",
		regional : 'cn',
		height: girdHeigth,
		mtype: "POST",
		autowidth: true,
		colNames: fildNames,
		colModel: fildCols,
		viewrecords : true,
		rowNum:20,
		rowList:[10,20,30],
		pager : pager_selector,
		altRows: true,
//		toolbar: [ true, "top" ,tool],
		multiselect: false,
        multiboxonly: true,

		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		autowidth: true,
	}).jqGrid('setGridParam', {
	    page : 1,
	    url:context+'/drillRpt.do?'+queryString,
	    datatype : "json"
	}).trigger("reloadGrid");
	
	//监听窗口变化，调整插件的宽度
    var resizeDelay = 600;
    var doResize = true;
    var resizer = function () {
       if (doResize) {
    	   $(grid_selector).setGridWidth($(window).width()-10);
           doResize = false;
       }
     };
     var resizerInterval = setInterval(resizer, resizeDelay);
     resizer();
     $(window).resize(function() {
       doResize = true;
     });
}

//调整分页图标
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'fa fa-angle-double-left',
		'ui-icon-seek-prev' : 'fa fa-angle-left',
		'ui-icon-seek-next' : 'fa fa-angle-right',
		'ui-icon-seek-end' : 'fa fa-angle-double-right'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	});
}

//导出Excel
var doExport = function(){
	var str = queryString.replace(/queryDrillRpt/g,"downloadDrillRpt");
	//禁用点击按钮
	var downBtn = document.getElementById("downBtn");
	downBtn.setAttribute("disabled",true);
	//生成唯一标识码cookie
	var cookieKey = guid();
	var submitForm = document.createElement("FORM");
	submitForm.setAttribute('style','display:none');
	submitForm.setAttribute('target','');
	submitForm.setAttribute('id','submitFormId');
	submitForm.setAttribute('method','post');
	submitForm.setAttribute('action',context+'/drillRpt.do?' + str);
	
	/*var inputPara = document.createElement("INPUT");
	inputPara.setAttribute('type','hidden');
	inputPara.setAttribute('name','queryString');
	inputPara.setAttribute('value',queryString);
	submitForm.appendChild(inputPara);*/
	
	var cookie = document.createElement("INPUT");
	cookie.setAttribute("type","hidden");
	cookie.setAttribute("name","cookieKey");
	cookie.setAttribute("value",cookieKey);
	submitForm.appendChild(cookie);
	
	document.getElementById("alertFormId").appendChild(submitForm);
	
	submitForm.submit();
	
	window.setTimeout(function(){getCookie(cookieKey);}, 1000);
};

function getCookie(cookieKey){
	var downBtn = document.getElementById("downBtn");
	console.log(cookieKey);
	$.ajax({
		url: context+'/drillRpt.do?method=getCookie',
		type: 'POST',
		dataType: 'json',
		data: {cookieKey:cookieKey},
		async:false,
		cache:false,
		success:function(msg){
			if(msg.success){
				//1.释放导出按钮,清除置灰效果
				downBtn.removeAttribute("disabled");
				return ;
			}
			//若未获取到返回定时就递归调用自身,延时1秒
			window.setTimeout(function(){getCookie(cookieKey);}, 1000);
		}
	});
};

//生成唯一标识
function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
};
