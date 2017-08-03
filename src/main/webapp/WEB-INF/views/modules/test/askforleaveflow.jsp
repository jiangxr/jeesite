<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/test/askforleave/">单表列表</a></li>
		<li class="active"><a href="${ctx}/test/askforleave/form?id=${askforleave.id}">单表<shiro:hasPermission name="test:askforleave:edit">${not empty askforleave.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="test:askforleave:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<div class="control-group">
			<!--<jsp:useBean id="act" class="com.thinkgem.jeesite.modules.act.entity.Act" scope="request" ></jsp:useBean>-->
			<form:form id="backForm" modelAttribute="askforleave"  action="${ctx}/act/task/taskBackOne/${askforleave.act.procInsId}" method="post">
			<form:hidden path="act.procInsId" />
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procDefId"/>
			<sys:message content="${message}"/>
			<input id="btnSubmit3" class="btn btn-primary" type="submit" onclick="" value="回退">
			</form:form>
	</div>		
	<form:form id="inputForm" modelAttribute="askforleave" action="${ctx}/test/askforleave/saveFlow" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">请假人：</label>
			<div class="controls">
				<form:input path="applicant" htmlEscape="true" maxlength="50" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假类型：</label>
			<div class="controls">
				<form:select path="leavetype" class="input-xlarge " disabled="true" >
					<form:option value="事假" label="事假"/>
					<form:option value="病假" label="病假"></form:option>
					<form:option value="其他" label="其他" ></form:option>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假原因：</label>
			<div class="controls">
				<form:input path="reason" htmlEscape="false" maxlength="100" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="begintime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${askforleave.begintime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${askforleave.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否是经理：</label>
			<div class="controls">
				是<form:radiobutton path="ismanager" value="yes"/>
				否<form:radiobutton path="ismanager" value="no"/>
				<form:radiobuttons path="ismanager" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" disabled="true" />
			</div>
		</div>
			<table class="table-form">
				<tr>
					<td class="tit">您的意见</td>
					<td colspan="5">
						<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
					</td>
				</tr>
			</table>
		<div>
			
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="test:askforleave:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="同意" onclick="$('#flag').val('yes')"/>&nbsp;
			<!-- <c:if test="${not empty askforleave.id}">
					<input id="btnSubmit2" class="btn btn-inverse" type="submit" value="驳回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if> -->
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty askforleave.id}">
			<act:histoicFlow procInsId="${askforleave.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>