<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/test/askforleave/">单表列表</a></li>
		<shiro:hasPermission name="test:askforleave:edit"><li><a href="${ctx}/test/askforleave/form">单表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="askforleave" action="${ctx}/test/askforleave/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>请假人</th>
			<th>请假类型</th>
			<th>请假原因</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>是否是经理</th>
				<shiro:hasPermission name="test:askforleave:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="askforleave">
			<tr>
			<td><a href="${ctx}/test/askforleave/form?id=${askforleave.id}">${askforleave.applicant}</a></td>
			<td>${askforleave.leavetype}</td>
			<td>${askforleave.reason}</td>
			<td><fmt:formatDate value="${askforleave.begintime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${askforleave.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${askforleave.ismanager}</td>
				<shiro:hasPermission name="test:askforleave:edit"><td>
    				<a href="${ctx}/test/askforleave/form?id=${askforleave.id}">修改</a>
					<a href="${ctx}/test/askforleave/delete?id=${askforleave.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>