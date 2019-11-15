<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
 <link rel="stylesheet" type="text/css" href="css/theme/professionalDevelopment.css">
 <security:authorize access="hasRole('VIEW_PROFESSIONAL_DEVELOPMENT')">
	<div>
	 <!-- 	<div class="breadcrumb">
			<h1><label id="pdbreadCrumMessage"> </label></h1>
			<h2><label id="pdbreadCrumMessageTag"></label></h2>
		</div> -->
		<div  id="professionalDevelopmentTabs" class="panel_full">
			
			<ul class="tabs">
			  <li><a href="#tabs_home"> <fmt:message key="label.pd.home"/> </a></li>
			  <security:authorize access="hasRole('VIEW_MODULES')">
			  	<li><a href="#tabs_modules" id="modules">  <fmt:message key="label.pd.modules"/>  </a></li>
			    <li><a href="#tabs_Transcript" id="transcript"> <fmt:message key="label.pd.transcripts"/>  </a></li>
			  </security:authorize>
			  <security:authorize access="hasRole('VIEW_ADMIN')">
			  	<li><a href="#tabs_admin" id="admin"> <fmt:message key="label.pd.admin"/>  </a></li>
			  </security:authorize>
			  <li><a href="#tabs_Help"> <fmt:message key="label.pd.help"/> </a></li>		  
			</ul>
		   
			<div id="tabs_home">
				<jsp:include page="professionalDevelopmentHome.jsp" />
			</div>
			
			<security:authorize access="hasRole('VIEW_MODULES')">
				<div id="tabs_modules">
					<jsp:include page="professionalDevelopmentModules.jsp" />
				</div>
				<div id="tabs_Transcript">
					<jsp:include page="professionalDevelopmentTranscript.jsp" />
				</div>				
			</security:authorize>
			
			<security:authorize access="hasRole('VIEW_ADMIN')">		
				<div id="tabs_admin">
					<jsp:include page="professionalDevelopmentAdmin.jsp" />
				</div>
			</security:authorize>
			
			<div id="tabs_Help">
				<jsp:include page="professionalDevelopmentHelp.jsp" />
			</div>
			
		</div>
	
	</div>

<script>
  	//Setting up the tabs.
	$(function() {
		
		$('#professionalDevelopmentTabs').tabs({
			beforeActivate: function(event, ui) {
				//console.log(ui.newPanel[0].id);
        		if(ui.newPanel[0].id == 'tabs_home') {
					$('#pdbreadCrumMessage').text("Professional Development");
					$('#pdbreadCrumMessageTag').text("<fmt:message key='label.pd.home.heading'/>");					
				} else if(ui.newPanel[0].id == 'tabs_modules') {
					$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.Modules.heading'/>");
					$('#pdbreadCrumMessageTag').text("");
					
			        var $tabs = $("#moduleTabs").tabs();
			        var selectedTabIndex = $tabs.tabs("option", "active"); // => 0
     
		            switch(selectedTabIndex)
		            {
		             case 0:
		 				$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.Modules.heading'/> - <fmt:message key='label.pd.modules.browseModulesHeading'/>");
						$('#pdbreadCrumMessageTag').text("");
		            
		         		break;			         
		             case 1:
		            	$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.Modules.heading'/> - <fmt:message key='label.pd.modules.myModulesHeading'/>");
						$('#pdbreadCrumMessageTag').text("");
			            
			         	break;
		            }
				} else if(ui.newPanel[0].id == 'tabs_Transcript') {
					$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.transcripts.heading'/>");
					$('#pdbreadCrumMessageTag').text("");
				} else if(ui.newPanel[0].id == 'tabs_admin') {
					$('#pdbreadCrumMessage').text("Professional Development: <fmt:message key='label.pd.admin.heading'/>");
					$('#pdbreadCrumMessageTag').text("");
				} else if(ui.newPanel[0].id== 'tabs_Help') {
					$('#pdbreadCrumMessage').text("Professional Development: Help");
					$('#pdbreadCrumMessageTag').text("");
				} 		
        	}			
		});

    }); 

</script>
</security:authorize>

<security:authorize access="!hasRole('VIEW_PROFESSIONAL_DEVELOPMENT')">
	<fmt:message key="error.permissionDenied"/>
</security:authorize>