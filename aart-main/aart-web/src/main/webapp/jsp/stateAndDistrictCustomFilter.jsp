<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

	<%--
		When integrating this component with other pages, the surrounding page
		should not be a spring form and the stateId, districtId, and schoolId should not be integrated
		with the spring bean.
	 --%>
	 
<div id='stateFilter'>
	
	<div id='state'>
		<label for='stateId'><fmt:message key='label.state'/></label>&nbsp;&nbsp;
		<select id='stateId'>
			<option value="0">
				<fmt:message key='label.common.select'/>
			</option>
		</select>
	</div>
	<br><br>
	<div id='district'>
		<label for='districtId'><fmt:message key='label.district'/></label>
		<select id='districtId'>
			<option value="0">
				<fmt:message key='label.common.select'/>
			</option>
		</select>
			
	</div>
	<br><br>
	<div id='school'>
		<label for='schoolId'><fmt:message key='label.school'/></label>
		<select id='schoolId'>
			<option value="0">
				<fmt:message key='label.common.select'/>
			</option>
		</select>			
	</div>
	<br><br>
</div>

<script src="js/stateAndDistrictFilter.js"> </script>