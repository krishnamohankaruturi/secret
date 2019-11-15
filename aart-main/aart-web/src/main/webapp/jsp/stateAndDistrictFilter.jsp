<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

	<%--
		When integrating this component with other pages, the surrounding form
		must be a spring form and the stateId, districtId, and schoolId must be integrated
		with the spring bean.
	 --%>
<div id='stateFilter' hidden="true">
	<div id='state'  data-lastid="<c:if test="${not empty stateId}">${stateId}</c:if>">
		<label for='stateId'><fmt:message key='label.state'/></label>
		<form:select path='stateId'>
			<form:option value="0">
				<fmt:message key='label.common.select'/>
			</form:option>
		</form:select>
		<form:errors path="stateId" />	
		<br>
	</div>
	<div id='district'  data-lastid="<c:if test="${not empty districtId}">${districtId}</c:if>">
		<label for='districtId'><fmt:message key='label.district'/></label>
		<form:select path='districtId'>
			<form:option value="0">
				<fmt:message key='label.common.select'/>
			</form:option>
		</form:select>
		<form:errors path="districtId" />	
	</div>
	<div id='school'  data-lastid="<c:if test="${not empty schoolId}">${schoolId}</c:if>">
		<label for='schoolId'><fmt:message key='label.school'/></label>
		<form:select path='schoolId'>
			<form:option value="0">
				<fmt:message key='label.common.select'/>
			</form:option>
		</form:select>
		<form:errors path="schoolId" />	
	</div>	
</div>

<script src="js/stateAndDistrictFilter.js"> </script>
