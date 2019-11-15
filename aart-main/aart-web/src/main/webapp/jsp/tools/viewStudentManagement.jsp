<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
.select2-container{
width: 250px !important;
}
</style>
<div>
	<ul id="studentManagement" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('EDIT_TDE_LOGIN')">
			<li class="nav-item get_st">
				<a class="nav-link" href="#tabs_tdelogin" data-toggle="tab" role="tab"><fmt:message key="tools.submenu.EditTDEUsername" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('MERGE_STUDENT_RECORDS')">
			<li class="nav-item get_st">
				<a class="nav-link" href="#tabs_student_records" data-toggle="tab" role="tab"><fmt:message key="tools.submenu.MergeStudentRecords" /></a>
			</li>
		</security:authorize>		
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('EDIT_TDE_LOGIN')">
			<div id="tabs_tdelogin" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/editTdeLogin.jsp"/>
			</div>
		</security:authorize>		
		
		<security:authorize access="hasRole('MERGE_STUDENT_RECORDS')">
			<div id="tabs_student_records" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/mergeStudentRecords.jsp"/>				
			</div>
		</security:authorize>
		
	</div>
</div>
<script>
 $(function(){
	/* navigate to the first available tab */
	$('#studentManagement li.nav-item:first a').tab('show');
});
 <security:authorize access="hasRole('MERGE_STUDENT_RECORDS')">
 	enableDisableStudentBtn(0);
 </security:authorize>

</script>


<security:authorize access="hasRole('EDIT_TDE_LOGIN')"> 
    <script type="text/javascript" src="<c:url value='/js/tools/editTdeLogin.js'/>"> </script>
 </security:authorize>