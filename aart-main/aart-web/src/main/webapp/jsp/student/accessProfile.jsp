<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
	
.active {
	background: white;
	color: #94b54d;
	border: 0;
}

.labelDiv {
	width: 90%;
	margin-left: 10px;
	margin-top: 40px;
	float: left;
}

.labelOptionsDiv {
	width: 90%;
	margin-left: 40px;
	float: left;
}

.tableth {
	border-right: 1px solid black;
}

.tableEmptyTd {
	border-top: 1px solid black;
}

.topInfoLeft {
	font: 25px Arial, Helvetica, sans-serif;
	margin-left: 1px;
	display: inline-block;
	width: 810px;
}

.topInfoRight {
	margin-right: 1px;
	display: inline-block;
}

.pages {
	background-color: #FDA20E;
	color: #FDA20E;
	font: 5px Arial, Helvetica, sans-serif;
	margin: 0 2px;
	padding: 1px 1px;
	text-decoration: none;
	margin-left: 15px;
}


#accessProfileTabs .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active, a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover{
	background-color: white;
    border-color: DimGray DimGray White !important;
}
#accessProfileTabs .ui-widget-header{
	border:none;
	background:none;

}

</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/colorpicker/jquery.colorPicker.min.js"/></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/colorpicker/jquery.colorPicker.js"/></script>

<script type="text/javascript"	src="<c:url value='/js/student/accessProfileSummary.js'/>"> </script>
<script type="text/javascript"	src="<c:url value='/js/student/accessProfileDisplayEnhancements.js'/>"> </script>
<script type="text/javascript"	src="<c:url value='/js/student/accessProfileLanguageBraille.js'/>"> </script>
<script type="text/javascript"	src="<c:url value='/js/student/accessProfileAudioEnvironment.js'/>"> </script>
<script type="text/javascript"	src="<c:url value='/js/student/accessProfileSystemIndependent.js'/>"> </script>
<script type="text/javascript"	src="<c:url value='/js/student/accessProfile.js'/>"> </script>
<input type="hidden" id="studentAssessmentProgramCode" value="${assessmentProgramCode}" />
<div id ="accessProfileDivAccessProfile" class="_bcg">
	<div  id="accessProfileTabs" class="panel_full">
		
		<ul class="tabs nav nav-tabs sub-nav" > 
			<li class="nav-item" >
				<a href="#tabs_summary"><fmt:message key="label.pnp.summary" /> </a>
		  	</li>
		  	<security:authorize access="hasAnyRole('CREATE_STUDENT_PNP','EDIT_STUDENT_PNP')">	
			  	<c:if test="${previewAccessProfileFlag}">
			  		<c:if test="${previewAccessProfileDisplayEnhancementsFlag}">
				  	<li class="nav-item" >
				  		<a class="nav-link" href="#tabs_display_enhancements" ><fmt:message key="label.pnp.display_enhancements" />  </a>
				  	</li>
				  	</c:if>
				  	<c:if test="${previewAccessProfileLanguageAndBrailleFlag}">
				  	<li class="nav-item" >
				  		<a class="nav-link" href="#tabs_language_braille" ><fmt:message key="label.pnp.language_braille" />  </a>
				  	</li>
				  	</c:if>
				  	<c:if test="${previewAccessProfileAudioAndEnvironmentSetupFlag}">
				  	<li class="nav-item" >
				  		<a class="nav-link" href="#tabs_audio_environment" ><fmt:message key="label.pnp.audio_environment" />  </a>
				  	</li>
				  	</c:if>
				  	<c:if test="${previewAccessProfileOtherSupportsFlag}">
				  	<li class="nav-item" >
				  		<a class="nav-link" href="#tabs_system_independent" ><fmt:message key="label.pnp.system_independent" />  </a>
				  	</li>
				  	</c:if>
			  	</c:if>
		  	</security:authorize>
		</ul>
	<div>
		<div id="tabs_summary">
			<jsp:include page="accessProfileSummary.jsp" />
		</div>
		
		<security:authorize access="hasAnyRole('CREATE_STUDENT_PNP','EDIT_STUDENT_PNP')">
			<c:if test="${previewAccessProfileFlag}">
			<c:if test="${previewAccessProfileDisplayEnhancementsFlag}">
				<div id="tabs_display_enhancements">
					<jsp:include page="accessProfileDisplayEnhancements.jsp" />
				</div>
			</c:if>
			<c:if test="${previewAccessProfileLanguageAndBrailleFlag}">	
				<div id="tabs_language_braille">
					<jsp:include page="accessProfileLanguageBraille.jsp" />
				</div>
			</c:if>
			<c:if test="${previewAccessProfileAudioAndEnvironmentSetupFlag}">
				<div id="tabs_audio_environment">
					<jsp:include page="accessProfileAudioEnvironment.jsp" />
				</div>
			</c:if>
			<c:if test="${previewAccessProfileOtherSupportsFlag}">
				<div id="tabs_system_independent">
					<jsp:include page="accessProfileSystemIndependent.jsp" />
				</div>
			</c:if>
			</c:if>
		</security:authorize>
	</div>
	</div>
</div>
<input id="previewAccessProfileFlag" value ='${previewAccessProfileFlag}' type="text" class="hidden" />
<input id="previewAccessProfile" value ="${previewAccessProfile}" type="text" class="hidden" />