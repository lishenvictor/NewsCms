<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>修改密码页面</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layui/css/layui.css">
<style type="text/css">

	table tr td{
		padding: 10px;
	}

</style>
</head>
<body>
<div style="padding: 20px">
	
	<form method="post">
		<table>
			<tr>
				<td>原密码：</td>
				<td><input type="password" id="oldpassword" name="oldpassword" class="layui-input"/></td>
			</tr>
			<tr>
				<td>新密码：</td>
				<td><input type="password" id="password" name="password" class="layui-input"/></td>
			</tr>
			<tr>
				<td>确认新密码：</td>
				<td><input type="password" id="password2" name="password2" class="layui-input"/></td>
			</tr>
			<tr>
				<td><button class="layui-btn" onclick="submitData();return false;">提交</button></td>
				<td><font id="errorInfo" color="red"></font></td>
			</tr>
		</table>
		 
	</form>
</div>
<script src="${pageContext.request.contextPath}/layui/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/common.js"></script>
<script>


	
	function submitData(){
		 if($("#oldpassword").val().trim()==""){
			  $("#errorInfo").text("请输入原密码！");
			  $("#oldpassword").focus();
			  return false;
		  }
		  
		  if($("#password").val().trim()==""){
			  $("#errorInfo").text("请输入新密码！");
			  $("#password").focus();
			  return false;
		  }
		  
		  if($("#password2").val().trim()==""){
			  $("#errorInfo").text("请输入确认新密码！");
			  $("#password2").focus();
			  return false;
		  }
		  
		  if($("#password").val().trim().length<6){
			  $("#errorInfo").text("密码至少6位！");
			  return false;
		  }
		  
		  if($("#password").val().trim() != $("#password2").val().trim()){
			  $("#errorInfo").text("确认新密码错误！");
			  $("#password2").focus();
			  return false;
		  }
		  
		  
		  $.post("${pageContext.request.contextPath}/user/modifyPassword.do",{oldpassword:$("#oldpassword").val().trim(),password:$("#password").val().trim()},function(result){
			  if(result.success){
				  alert("密码修改成功，下一次登录生效！");
				  parent.reloadPage();
			  }else{
				  $("#errorInfo").text(result.info);
			  }
		  },"json");
	}

</script>
</body>
</html>