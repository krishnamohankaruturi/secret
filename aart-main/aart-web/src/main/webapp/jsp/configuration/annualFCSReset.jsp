<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/jsp/include.jsp"%>
<style>
	 #resetFCSContainer .disclaimerMsg {
   	font-size: 15px;
}
</style>
<script type="text/javascript" src="<c:url value='/js/configuration/annualFCSReset.js'/>"> </script>
<security:authorize access="!hasRole('PERM_ANNUAL_RESET')">
	<div class="messages">
		<span class="error_message ui-state-error permissionDeniedMessage" id="annualFcsPermissionDeniedMessage"><fmt:message key="error.permissionDenied" /></span>
	</div>
</security:authorize>

<security:authorize access="hasRole('PERM_ANNUAL_RESET')">
	<div id="schoolYearDatesSuccess" class="" style="color: green; padding-left: 25px; font-size: 15px;"></div>
	<div id="schoolYearDatesError" class="error" style="padding-left: 25px; font-size: 15px;"></div>
	<div id="resetSchoolYearContainer">
		<form id="resetSchoolYearForm" name="resetSchoolYearForm" class="form">
			<div id="resetSchoolYearFilter"></div>
			<div id="stateDiv"  class="form-fields">
				<label for="schoolYearStateSelect" class="isrequired field-label">State:<span class="lbl-required">*</span></label>
				<select id="schoolYearStateSelect" title="State for School year" name="schoolYearStateSelect" class="bcg_select required" multiple="multiple">
				</select>
			</div>
			<div class="inline_block">
					<input type="button" id="resetSchoolYearBtn" style="padding-top: 10px;padding-bottom:10px;" value="<fmt:message key='reset.schoolyear.button'/>" />
			</div>
			
		</form>
				
	</div>
	
	<div id="resetFCSContainer">
	<span id="updatefcs-success" class="error_message ui-state-highlight hidden"><fmt:message key='label.reset.fcs.status.success'/></span>
		<span id="updatefcs-error" class="error_message ui-state-error hidden"><fmt:message key='label.fcs.annual.reset.error'/></span>
		<span class="disclaimerMsg" style="padding-left: 25px; padding-top: 20px">
			<p style="padding-left: 25px"><b>Note:</b>&nbsp;Please make sure that an academic year was selected for the organizations on <b>Set Academic Year</b> tab and Include Science setting was verified on 
				<b>First Contact Survey</b> tab before doing FCS reset.
			</p>
		</span>
		<form id="resetFCSForm" name="resetFCSForm" class="form">
			<div id="resetFCSFilter"></div>
			<div id="dlmStateDiv"  class="form-fields">
				<label for="dlmStateSelect" class="isrequired field-label">State:<span class="lbl-required">*</span></label>
				<select id="dlmStateSelect" title="State for FCS" name="dlmStateSelect" class="bcg_select required" multiple="multiple">
				</select>
			</div>
			<div class="inline_block">
					<input type="button" id="resetFCStatusBtn" value="<fmt:message key='reset.fcs.button'/>" />
			</div>
			
		</form>			
		
	</div>
	
	
	<p> &nbsp; </p>
	
</security:authorize>

