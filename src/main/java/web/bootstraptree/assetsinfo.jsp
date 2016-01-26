<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.neusoft.cloud.web.webpage.customer.beans.PropertyAssetInfo" %>
<%@ page import="com.neusoft.cloud.web.webpage.customer.beans.PropertyAssetPointInfo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="<c:url value='/styles/js/component/common_4_front.js'/>"></script>

<div class="asset-main" style="overflow:auto;">
	<c:if test="${empty errorMsg}">
		<c:if test="${(alarmStatus)!= null}">
			<div class="asset-main-title">
				告警状态：<span class="text-orange">${alarmStatus}</span>
			</div>
		</c:if>
		<c:if test="${(assetInfo)!= null}">
			<div class="dcim-box">
				<div class="dcim-title">资源信息</div>
				<table cellspacing="0" cellpadding="0" border="0" class="dcim-table">
						<tr>
							<td>资源名称：</td>
							<td class="text-left"><span>${assetInfo.assetName}</span></td>
						</tr>
						<tr>
							<td>资源类型：</td>
							<td class="text-left"><span>${assetInfo.assetType}</span></td>
						</tr>
						<tr>
							<td>资源位置：</td>
							<td class="text-left"><span>${assetInfo.locationName}</span></td>
						</tr>
				</table>
			</div>
		</c:if>
		<c:if
			test="${(listPointInfo1)!= null && fn:length(listPointInfo1) > 0}">
			<div class="dcim-box">
				<div class="dcim-title">状态量</div>
				<table cellspacing="0" cellpadding="0" border="0" class="dcim-table">
					<c:set var="count1" value="${0}" />
					<c:forEach var="item1" items="${listPointInfo1}">
						<c:if test="${0 eq count1%2}">
							<tr>
						</c:if>
						<td>${item1.displayName}：</td>
						<td class="text-left"><span>${item1.value}</span>${item1.unit}</td>
						<c:if test="${0 eq (count1+1)%2}">
							</tr>
						</c:if>
						<c:set var="count1" value="${count1+1}" />
					</c:forEach>
				</table>
			</div>
		</c:if>
		<c:if
			test="${(listPointInfo2)!= null && fn:length(listPointInfo2) > 0}">
			<div class="dcim-box">
				<div class="dcim-title">模拟量</div>
				<table cellspacing="0" cellpadding="0" border="0" class="dcim-table">
					<c:set var="count1" value="${0}" />
					<c:forEach var="item2" items="${listPointInfo2}">
						<c:if test="${0 eq count1%2}">
							<tr>
						</c:if>
						<td>${item2.displayName}：</td>
						<td class="text-left"><span>${item2.value}</span>${item2.unit}</td>
						<c:if test="${0 eq (count1+1)%2}">
							</tr>
						</c:if>
						<c:set var="count1" value="${count1+1}" />
					</c:forEach>
				</table>
			</div>
		</c:if>
		<c:if
			test="${(listPointInfo3)!= null && fn:length(listPointInfo3) > 0}">
			<div class="dcim-box">
				<div class="dcim-title">综合参数模拟量</div>
				<table cellspacing="0" cellpadding="0" border="0" class="dcim-table">
					<c:set var="count1" value="${0}" />
					<c:forEach var="item3" items="${listPointInfo3}">
						<c:if test="${0 eq count1%2}">
							<tr>
						</c:if>
						<td>${item3.displayName}：</td>
						<td class="text-left"><span>${item3.value}</span>${item3.unit}</td>
						<c:if test="${0 eq (count1+1)%2}">
							</tr>
						</c:if>
						<c:set var="count1" value="${count1+1}" />
					</c:forEach>
				</table>
			</div>
		</c:if>
		<c:if
			test="${(listPointInfo4)!= null && fn:length(listPointInfo4) > 0}">
			<div class="dcim-box">
				<div class="dcim-title">综合参数状态量</div>
				<table cellspacing="0" cellpadding="0" border="0" class="dcim-table">
					<c:set var="count1" value="${0}" />
					<c:forEach var="item4" items="${listPointInfo4}">
						<c:if test="${0 eq count1%2}">
							<tr>
						</c:if>
						<td>${item4.displayName}：</td>
						<td class="text-left"><span>${item4.value}</span>${item4.unit}</td>
						<c:if test="${0 eq (count1+1)%2}">
							</tr>
						</c:if>
						<c:set var="count1" value="${count1+1}" />
					</c:forEach>
				</table>
			</div>
		</c:if>
	</c:if>
	
	<%-- 异常错误展示 --%>
	<c:if test="${not empty errorMsg}">
		<input id="id-error-msg" type="hidden" value="${errorMsg}"/>
		<div id="id-alert"></div>
		<script>
			$(document).ready(function (){
				var errormsg = $("#id-error-msg").val();
				if(errormsg!=""&&errormsg!=null){
					showAlert("danger",errormsg,0);
				}
			});
		</script>
	</c:if>
</div>