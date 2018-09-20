<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/list_icon.png"/>
		最新新闻</div>
		<div class="datas">
			<ul>
				<c:forEach var="article" items="${articleList }">
				<li style="margin-bottom: 30px">
				  	<span class="date"><a href="${pageContext.request.contextPath}/article/${article.id }.html"><fmt:formatDate value="${article.releaseDate }" type="date" pattern="yyyy年MM月dd日"/></a></span>
				  	<span class="title"><a href="${pageContext.request.contextPath}/article/${article.id }.html">${article.title }</a></span>
				  	<span class="summary">摘要: ${article.summary }...</span>
				  	<span class="info">发布于 <fmt:formatDate value="${article.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/> 阅读(${article.clickHit }) </span>
				  </li>
				  <hr style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />
                </c:forEach>
            </ul>
        </div>
</div>

<div>
	<nav>
	  <ul class="pagination pagination-sm">
	  	${pageCode }
	  </ul>
	</nav>
 </div>
