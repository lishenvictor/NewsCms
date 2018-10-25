<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
	<div class="col-md-8">
		<iframe style="float: left;" width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
	</div>
	<div class="col-md-4" height="60" style="line-height:60px;">
		<ul>
			<c:choose>
				<c:when test="${not empty currentUser }">
					<li style="float: right;" style="padding-left: 8px;padding-right: 8px;">
						<font size="3">欢迎：<strong>${currentUser.userName }</strong></font>&nbsp;
						<a href="${pageContext.request.contextPath}/user/logout.do">安全退出</a>&nbsp;
						<a onclick="showModifyPassword()">修改密码</a>
					</li>
				</c:when>
				<c:otherwise>
					<li style="float: right;"><a onclick="showRegister()" style="padding-left: 8px;padding-right: 8px;" target="_blank">注册</a></li>
					<li style="float: right;"><a onclick="showLogin()" style="padding-left: 8px;padding-right: 8px;" target="_blank">登录</a></li>
				</c:otherwise>
			</c:choose>

		</ul>
	</div>
</div>
    