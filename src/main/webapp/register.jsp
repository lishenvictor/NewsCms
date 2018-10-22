
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>用户注册页面</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layui/css/layui.css">
<style type="text/css">

	table tr td{
		padding: 10px;
	}

</style>
</head>
<body>
<div style="padding: 20px">
	
	<form name="myForm" method="post">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="userName" name="userName" class="layui-input"/></td>
			</tr>
			<tr>
				<td>密   码：</td>
				<td><input type="password" id="password" name="password" class="layui-input"/></td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td><input type="password" id="password2" name="password2" class="layui-input"/></td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td><input type="text" id="email" name="email" class="layui-input"/></td>
			</tr>
			<tr>
				<td><button class="layui-btn" onclick="submitData();return false;">注册</button></td>
				<td><font id="errorInfo" color="red"></font></td>
			</tr>
		</table>
		 
	</form>
</div>
<script src="${pageContext.request.contextPath}/layui/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/common.js"></script>
<script src="https://cdn.vaptcha.com/v2.js"></script>
<script>


	
	function submitData(){
		  if($("#userName").val().trim()==""){
			  $("#errorInfo").text("请输入用户名！");
			  return false;
		  }
		  
		  if($("#password").val().trim()==""){
			  $("#errorInfo").text("请输入密码！");
			  return false;
		  }
		  
		  if($("#password2").val().trim()==""){
			  $("#errorInfo").text("请输入确认密码！");
			  return false;
		  }
		  
		  if($("#email").val().trim()==""){
			  $("#errorInfo").text("请输入邮箱！");
			  return false;
		  }
		
		  if($("#password").val().trim().length<6){
			  $("#errorInfo").text("密码至少6位！");
			  return false;
		  }
		  
		  if($("#password").val().trim()!=$("#password2").val().trim()){
			  $("#errorInfo").text("确认密码不对！");
		  	  return false;
		   }

		  
		  $.post("${pageContext.request.contextPath}/user/register.do",{userName:$("#userName").val().trim(),password:$("#password").val().trim(),email:$("#email").val().trim()},function(result){
			  if(result.success){
				  alert("恭喜您，注册成功！");
				  parent.reloadPage();
			  }else{
                  $("#errorInfo").text(result.info);
			  }
		  },"json");
		  
		  
	}
		

</script>
</body>
</html>