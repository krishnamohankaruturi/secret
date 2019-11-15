<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

<div class="prodev">
	<div style="clear:both;">
		<div id="moduleTabs">
		<div class="tabTable">
		<div class="row">
		<div class="tab-cell vertTabs">
			<ul>
			  <li><a href="#tabs_browsemodules" id="browseModules"> <fmt:message key="label.pd.modules.browsemodules"/> </a></li>
			  <security:authorize access="hasRole('ENROLL_MODULE')">
			  	<li><a href="#tabs_mymodules" id="myModules" > <fmt:message key="label.pd.modules.mymodules"/>  </a></li>
			  </security:authorize>		  
			</ul>
		</div>
		<div class="content-cell">
			<div id="tabs_browsemodules">			
				<jsp:include page="professionalDevelopmentBrowseModules.jsp" />
			</div>
			<security:authorize access="hasRole('ENROLL_MODULE')">
				<div id="tabs_mymodules"> 
					<jsp:include page="professionalDevelopmentMyModules.jsp" />
				</div>
			</security:authorize>
		
			<div id="ModuleInfoDiv">
				<div class= "pdModuleInfoCloseButton"></div>
			</div>
		</div>
		</div>
		</div>	
		</div>
	</div>

</div>


<script>
$(function () {
	$('#moduleTabs').tabs({
		beforeActivate: function (event, ui) {
			if(ui.tab.id=="browseModules") {
				$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.Modules.heading'/> - <fmt:message key='label.pd.modules.browseModulesHeading'/>");
				$('#pdbreadCrumMessageTag').text("");
				browseModules();
				$("#browseModules").addClass("active");
				$("#myModules").removeClass("active");
			} else if (ui.tab.id=="myModules") {
				$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.Modules.heading'/> - <fmt:message key='label.pd.modules.myModulesHeading'/>");
				$('#pdbreadCrumMessageTag').text("");
				myModules();
				$("#myModules").addClass("active");
				$("#browseModules").removeClass("active");
			}
	  	} 
	});
	$('#moduleTabs').tabs('option', 'active');
	browseModules();
	$("#browseModules").addClass("active");
	$("#myModules").removeClass("active");
});


</script>