<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻类别管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	var url;
    function crawler(type){
        $("#result").html("正在爬取新闻......");
		$.post("${pageContext.request.contextPath}/admin/crawler/getNews.do",{type:type},function(result){
			if(result.success){
			    if(type == "1") {
                    $("#result").html("爬取网易新闻"+result.count+"篇");
                }else if(type == "2"){
                    $("#result").html("爬取腾讯新闻"+result.count+"篇");
                }else{
                    $("#result").html("爬取搜狐新闻"+result.count+"篇");
                }
				$.messager.alert("系统提示","爬取新闻成功！");
			}else{
				$.messager.alert("系统提示","爬取新闻失败！");
			}
		},"json");
	}
	
</script>
</head>
<body style="margin: 1px">
<div style="padding-top: 0px">
<table id="dg" class="easyui-datagrid" title="新闻爬虫管理" fit="true" toolbar="#tb">
</table>
</div>
<div align="center" style="padding-top: 100px"><font id="result" color="red" size="10"></font></div>

<div id="tb">
	<a href="javascript:crawler('1')" class="easyui-linkbutton" iconCls="icon-add" plain="true">网易</a>
	<a href="javascript:crawler('2')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">腾讯</a>
	<a href="javascript:crawler('3')" class="easyui-linkbutton" iconCls="icon-remove" plain="true">搜狐</a>
</div>
</body>
</html>