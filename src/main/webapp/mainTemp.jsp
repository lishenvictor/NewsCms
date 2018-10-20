<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle }-powered by ssp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/blog.css">
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<style type="text/css">
	  body {
        padding-top: 10px;
        padding-bottom: 40px;
      }
</style>
</head>
<body class="container">
<jsp:include page="/foreground/common/head.jsp"/>
<jsp:include page="/foreground/common/menu.jsp"/>

<div class="row">
	
	<div class="col-md-9">
		<jsp:include page="${mainPage }"/>
    </div>
    
    <div class="col-md-3">
    	<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/byType_icon.png"/>
					按新闻类别
				</div>
				<div class="datas">
					<ul>
					 <c:forEach var="arcType" items="${arcTypeList }">
						<li><span><a href="${pageContext.request.contextPath}/index.html?typeId=${arcType.id}">${arcType.typeName }</a></span></li>
					 </c:forEach>				
					</ul>
				</div>
			</div>
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/hot_icon.png"/>
					热门新闻
				</div>
				<div class="hostDatas">
					<ul>
						<c:forEach var="hotArticle" items="${hotArticleList }" varStatus="status">
						<li><span><a href="${pageContext.request.contextPath}/article/${hotArticle.id }.html" title="${hotArticle.title }">${status.index+1 }、${hotArticle.title }</a></span></li>
					   </c:forEach>
					</ul>
				</div>
			</div>
			
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/link_icon.png"/>
					友情链接
				</div>
				<div class="datas">
					<ul>
					   <c:forEach var="link" items="${linkList }">
						<li><span><a href="${link.url }" target="_blank">${link.name }</a></span></li>
					   </c:forEach>
					</ul>
				</div>
			</div>
			
		</div>
    	
    </div>
</div>


<jsp:include page="/foreground/common/foot.jsp"/>
</body>
</html>