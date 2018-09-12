<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帖子修改页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">

	function submitData(){
		var title=$("#title").val();
		var typeId=$("#typeId").combobox("getValue");
		var state=$("#state").combobox("getValue");
		var orUrl=$("#orUrl").val();
		var crawlerDate=$("#crawlerDate").datebox("getValue");
		var tags=$("#tags").val();
		var content=UE.getEditor("editor").getContent();
		var summary=UE.getEditor("editor").getContentTxt().substr(0,155);
		var contentNoTag=UE.getEditor("editor").getContentTxt();
		if(title==null || title==''){
			alert("请输入标题！");
		}else if(typeId==-1){
			alert("请选择帖子类别！");
		}else if(state==-1){
			alert("请选择帖子状态！");
		}else if(orUrl==null || orUrl==''){
			alert("请输入抓取地址！");
		}else if(crawlerDate==null || crawlerDate==''){
			alert("请输入抓取日期！");
		}else if(tags==null || tags==''){
			alert("请输入Tag标签！");
		}else if(content==null || content==''){
			alert("请输入内容！");
		}else{
			$.post("${pageContext.request.contextPath}/admin/article/save.do",{'id':'${param.id}','title':title,'contentNoTag':contentNoTag,'content':content,'summary':summary,'crawlerDate':crawlerDate,'arcType.id':typeId,'tags':tags,'orUrl':orUrl,'state':state},function(result){
				if(result.success){
					$.messager.alert("系统提示","帖子发布成功！");
					resetValue();
				}else{
					$.messager.alert("系统提示","帖子发布失败！");
				}
			},"json");
		}
	}
	
	function resetValue(){
		$("#title").val("");
		$("#typeId").combobox("setValue","-1");
		$("#state").combobox("setValue","-1");
		$("#orUrl").val("");
		$("#crawlerDate").datebox("setValue","");
		$("#tags").val("");
		UE.getEditor("editor").setContent("");
	}
	
</script>
</head>
<body style="margin: 10px">
	<div id="p" class="easyui-panel" title="修改帖子" style="padding: 10px">
		<table cellspacing="10px">
			<tr>
				<td width="80px">帖子标题：</td>
				<td>
					<input type="text" id="title" name="title" style="width: 400px"/>
				</td>
			</tr>
			<tr>
				<td>帖子类别：</td>
				<td>
					<select class="easyui-combobox" style="width: 154px" id="typeId" name="arcType.id" editable="false" panelHeight="auto">
						<option value="-1">请选择帖子类别...</option>
						<c:forEach var="arcType" items="${arcTypeList }">
							<option value="${arcType.id }">${arcType.typeName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>帖子状态：</td>
				<td>
					<select class="easyui-combobox" style="width: 154px" id="state" name="state" editable="false" panelHeight="auto">
						<option value="-1">请选择帖子类别...</option>
						<option value="0">未发布</option>
						<option value="1">已发布</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>抓取地址：</td>
				<td>
					<input type="text" id="orUrl" name="orUrl" style="width: 400px"/>
				</td>
			</tr>
			<tr>
				<td>抓取时间：</td>
				<td>
					<input type="text" id="crawlerDate" name="crawlerDate" class="easyui-datetimebox"/>
				</td>
			</tr>
			<tr>
				<td>Tag标签：</td>
				<td>
					<input type="text" id="tags" name="tags" style="width: 400px"/>&nbsp;&nbsp; (多个Tag之间用空格隔开)
				</td>
			</tr>
			<tr>
				<td valign="top">帖子内容：</td>
				<td width="900px">
					<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<a href="javascript:submitData()" class="easyui-linkbutton" iconCls="icon-submit">发布帖子</a>
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
		var ue=UE.getEditor("editor");
		
		ue.addListener("ready",function(){
			UE.ajax.request("${pageContext.request.contextPath}/admin/article/findById.do",{
				method:"post",
				async:false,
				data:{"id":"${param.id}"},
				onsuccess:function(result){
					result=eval("("+result.responseText+")");
					$("#title").val(result.title);
					$("#typeId").combobox("setValue",result.arcType.id);
					$("#state").combobox("setValue",result.state);
					$("#orUrl").val(result.orUrl);
					$("#crawlerDate").datebox("setValue",result.crawlerDate);
					$("#tags").val(result.tags);
					UE.getEditor("editor").setContent(result.content);
				}
			})
		});
	</script>
</body>
</html>