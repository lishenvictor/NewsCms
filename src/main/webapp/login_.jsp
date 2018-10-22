<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>用户登录页面</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layui/css/layui.css">
<style type="text/css">

	table tr td{
		padding: 10px;
	}

</style>
</head>
<body>
<div style="padding: 20px">
	<form name="myForm" class="layui-form" action="">
	  <div class="layui-form-item">
	    <label class="layui-form-label">用户名：</label>
	    <div class="layui-input-block">
	      <input type="text" id="userName" name="userName"  placeholder="请输入用户名" class="layui-input" style="width: 200px">&nbsp;&nbsp;
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">密 &nbsp;&nbsp;&nbsp;码：</label>
	    <div class="layui-input-block">
	      <input type="password" id="password" name="password"  placeholder="请输入密码" class="layui-input" style="width: 200px">&nbsp;&nbsp;
	    </div>
	  </div>
	  <div class="layui-form-item" pane="">
	    <div class="layui-input-block">
	      <input type="checkbox" id="rememberMe" value="rememberMe"  lay-skin="primary" title="记住密码">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" onclick="submitData();return false;">登录</button>
	      &nbsp;&nbsp;<font id="errorInfo" color="red"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.showFindPassword()">忘记密码</a>
	    </div>
	  </div>
	  </form>
	
</div>
<script src="${pageContext.request.contextPath}/layui/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/common.js"></script>
<script src="${pageContext.request.contextPath}/layui/js/jquery.cookie.js"></script>
<script src="https://cdn.vaptcha.com/v2.js"></script>
<script>
  
	layui.use(['form'], function(){
		var form = layui.form
	});

	function submitData(){
		 if($("#userName").val().trim()==""){
			  $("#errorInfo").text("请输入用户名！");
			  $("#userName").focus();
			  return false;
		  }
		  
		  if($("#password").val().trim()==""){
			  $("#errorInfo").text("请输入密码！");
			  $("#password").focus();
			  return false;
		  }
		  
		  var rememberMe=$("#rememberMe").prop('checked');
		  
		  $.post("${pageContext.request.contextPath}/user/login.do",{userName:$("#userName").val().trim(),password:$("#password").val().trim()},function(result){
			  if(result.success){
				  parent.reloadPage();
			  }else{
                  $("#errorInfo").text(result.info);
              }
		  },"json");
		  
		  if(rememberMe){ // 记住密码
			  $.cookie('user', $("#userName").val().trim()+'-'+$("#password").val().trim(), { expires: 7 });
		  }
		  
	}
	
	
	$(function(){ 
		
	　　var user=$.cookie('user');
	   if (typeof(user) != "undefined"){
	     var userNameAndPassword=user.split("-");
	     $("#userName").val(userNameAndPassword[0])
	     $("#password").val(userNameAndPassword[1])
       }
	}); 

</script>


</body>
</html>