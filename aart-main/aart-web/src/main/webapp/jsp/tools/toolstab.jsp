<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/toolstab.js?v=1'/>"></script>

 <security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">	 
    <script type="text/javascript" src="<c:url value='/js/tools/activationEmailTemplateView.js'/>"> </script>
 </security:authorize> 
 <link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />
 
<style>
.hideToolsTab {
	display: none
}
#org_mgmt_success{
	clear: both;
}
#org_mgmt_error{
	clear: both;
	color: red;
}
#moveSchoolHeader, #mergeSchoolsHeader, #manageHelpContentHeader, #reactivateSchoolHeader, #inactiveAccountMgmtHeader{
	text-align: center;
	color: #82a53d;
	font-size: 1em;
	text-transform: uppercase;
	font-weight: 400;
}
#manageHelpContentHeader{
	text-transform: none !important;
}
#sourceDistrictForMoving, #deactivateOrgTypeContainer, #deactivateSchoolContainer, #reactivateOrgTypeContainer, #reactivateSchoolContainer{
	margin-left: 20%;
}
</style>

<div id="toolsTabs" class="panel_full">
	<aside id="tools_sidebar">
		<div id="tools_menu">
			<ul>
	    		
	    		 <security:authorize access="hasRole('TOOLS_CREATE_HELP_CONTENT')"> 
					<li class="active"><a href="#"><fmt:message key="tools.helpManagement.main.label" /></a>
						<ul class="show">
								<li><a href="#tabs_helpContent"><fmt:message key="tools.helpManagement.helpContent.label" /></a></li>
						</ul>
					</li>
				</security:authorize>
	    		<security:authorize access="hasRole('VIEW_USER_MANAGEMENT')">	
					<li class="active"><a href="#"><fmt:message key="label.toolmainmenu.UserManagement"/></a>
			  		  <ul class="show">		  
				 		<security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">	
				 			<li><a href="#tabs_actemail"><fmt:message key="label.toolsubmenu.ActivationEmail"/></a> </li>
				 		</security:authorize>
						<security:authorize access="hasRole('EDIT_INACTIVE_ACCOUNTS')">	
				 			<li><a href="#tabs_invalid_accounts"><fmt:message key="label.toolsubmenu.InactiveAccounts"/></a> </li>
				 		</security:authorize>
				 		<security:authorize access="hasRole('EDIT_INTERNAL_USERS')">	
				 			<li><a href="#tabs_internal_users"><fmt:message key="label.toolsubmenu.InternalUsers"/></a> </li>
				 		</security:authorize>
			  		  </ul>
		     		</li>
		 		</security:authorize>
				<security:authorize access="hasRole('TOOLS_VIEW_ORGANIZATION_DATA')">
					<li class="active"><a href="#"><fmt:message key="tools.org.main.label" /></a>
						<ul class="show">
							<security:authorize access="hasRole('TOOLS_MERGE_SCHOOLS')">
								<li><a href="#tabs_org_merge"><fmt:message key="tools.org.merge.label" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('TOOLS_MOVE_A_SCHOOL')">
								<li><a href="#tabs_org_move"><fmt:message key="tools.org.move.label" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('TOOLS_DEACTIVATE_ORGANIZATION')">
								<li><a href="#tabs_org_deactivate"><fmt:message key="tools.org.deactivate.label" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('TOOLS_REACTIVATE_ORGANIZATION')">
								<li><a href="#tabs_org_reactivate"><fmt:message key="tools.org.reactivate.label" /></a></li>
							</security:authorize>
						</ul></li>
				</security:authorize>
				
				
				<security:authorize access="hasRole('VIEW_TEST_RESET')">
					<li class="active"><a href="#"><fmt:message key="tools.main.testreset.label" /></a>
						<ul class="show">
							<security:authorize access="hasRole('RESET_DLM_TESTLETS')">
								<li><a href="#tabs_org_dlmtestlets"><fmt:message key="tools.testlet.reset.label" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('MANAGE_LCS')">
								<li><a href="#tabs_org_managelcs"><fmt:message key="tools.manage.lcs.label" /></a></li>
							</security:authorize>
						</ul></li>
				</security:authorize>
				
				<security:authorize access="hasRole('VIEW_MISCELLANEOUS')">
					<li class="active"><a href="#"><fmt:message key="tools.org.miscellaneous.label" /></a>
						<ul class="show">
							<security:authorize access="hasRole('CUSTOM_REPORTS')">
								<li><a href="#tabs_org_customreports"><fmt:message key="tools.org.customreports.label" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('AUDIT_HISTORY')">
								<li><a href="#tabs_org_audithistory"><fmt:message key="tools.org.audithistory.label" /></a></li>
							</security:authorize>
						</ul></li>
				</security:authorize>
				
				<security:authorize access="hasRole('VIEW_STUDENT_INFORMATION')">
					<li class="active"><a href="#"><fmt:message key="tools.mainmenu.StudentInformation" /></a>
						<ul class="show">
							<security:authorize access="hasRole('EDIT_TDE_LOGIN')">
								<li><a href="#tabs_tdelogin"><fmt:message key="tools.submenu.EditTDEUsername" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('MERGE_STUDENT_RECORDS')">
								<li><a href="#tabs_student_records"><fmt:message key="tools.submenu.MergeStudentRecords" /></a></li>
							</security:authorize>
						</ul></li>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_PERMISSIONS_AND_ROLESTAB')">
					<li class="active"><a href="#"><fmt:message key="tools.mainmenu.PermissionsAndRoles" /></a>
						<ul class="show">
							<security:authorize access="hasRole('PERMISSIONS_AND_ROLES_EXTRACT')">
								<li><a href="#tabs_permissions"><fmt:message key="tools.submenu.PermissionsAndRolesExtractSubTab" /></a></li>
							</security:authorize>
						<c:if test="${user.groupCode == 'GSAD'}">
								<li><a href="#tab_permission_upload"><fmt:message key="tools.submenu.UploadPermissionsSubTab" /></a></li>
							</c:if>
						</ul>
					</li>
				</security:authorize>
			</ul>
		</div>
	</aside>
	<div class="with-sidebar-content">
		<security:authorize access="hasRole('TOOLS_VIEW_ORGANIZATION_DATA')">
			<div id=tabs_level class="hideViewOrganizationData"></div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_MERGE_SCHOOLS')">
			<div id="tabs_org_merge" class="hideToolsTab">
				<jsp:include page="mergeSchools.jsp" />
			</div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_MOVE_A_SCHOOL')">
			<div id="tabs_org_move" class="hideToolsTab">
				<jsp:include page="moveSchools.jsp" />
			</div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_DEACTIVATE_ORGANIZATION')">
			<div id="tabs_org_deactivate" class="hideToolsTab">
				<jsp:include page="deactivateOrganization.jsp" />
			</div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_REACTIVATE_ORGANIZATION')">
			<div id="tabs_org_reactivate" class="hideToolsTab">
				<jsp:include page="reactivateOrganization.jsp" />
			</div>
		</security:authorize>
		<security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">		
		    <div id="tabs_actemail" class="hideToolsTab" >
				<jsp:include page="ActivationEmailTemplateView.jsp" />	
		    </div>
 	    </security:authorize>
 	    <security:authorize access="hasRole('EDIT_INACTIVE_ACCOUNTS')">		
		    <div id="tabs_invalid_accounts" class="hideToolsTab" >
				<jsp:include page="viewInvalidUsers.jsp" />	
		    </div>
 	    </security:authorize>
 	    <security:authorize access="hasRole('EDIT_INTERNAL_USERS')">		
		    <div id="tabs_internal_users" class="hideToolsTab" >
				<jsp:include page="viewInternalUsers.jsp" />	
		    </div>
 	    </security:authorize>
 	    <security:authorize access="hasRole('VIEW_TEST_RESET')">
			<div id=tabs_level class="hideViewTestReset"></div>
		</security:authorize>
 	    <security:authorize access="hasRole('RESET_DLM_TESTLETS')">		
		    <div id="tabs_org_dlmtestlets" class="hideToolsTab" >
				<jsp:include page="DlmTestLets.jsp" />	
		    </div>
 	    </security:authorize> 
 	    <security:authorize access="hasRole('MANAGE_LCS')">		
		    <div id="tabs_org_managelcs" class="hideToolsTab" >
				<jsp:include page="ManageLcs.jsp" />	
		    </div>
 	    </security:authorize> 
 	    <security:authorize access="hasRole('VIEW_MISCELLANEOUS')">
			<div id=tabs_level class="hideViewMiscellaneous"></div>
		</security:authorize>
 	
 	<security:authorize access="hasRole('CUSTOM_REPORTS')">		
		    <div id="tabs_org_customreports" class="hideToolsTab" >
				<jsp:include page="CustomReports.jsp" />	
		    </div>
 	    </security:authorize> 
 	    <security:authorize access="hasRole('AUDIT_HISTORY')">		
		    <div id="tabs_org_audithistory" class="hideToolsTab" >
				<jsp:include page="AuditHistory.jsp" />	
		    </div>
 	    </security:authorize> 
 	    <security:authorize access="hasRole('VIEW_STUDENT_INFORMATION')">
			<div id=tabs_view_studentInform class="ViewStudentInformation"></div>
		</security:authorize>
 	    <security:authorize access="hasRole('EDIT_TDE_LOGIN')">
			<div id="tabs_tdelogin" class="hideToolsTab">
				<jsp:include page="editTdeLogin.jsp" />
			</div>
		</security:authorize>
		<security:authorize access="hasRole('MERGE_STUDENT_RECORDS')">
			<div id="tabs_student_records" class="hideToolsTab">
				<jsp:include page="mergeStudentRecords.jsp" />
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERMISSIONS_AND_ROLES_EXTRACT')">
			<div id="tabs_permissions" class="hideToolsTab">
				<jsp:include page="permissionsAndRolesExtract.jsp" />
			</div>
			<div id="tab_permission_upload" class="hideToolsTab">
				<jsp:include page="uploadPermissions.jsp" />
			</div>
		</security:authorize>
 	    <div id="tabs_helpContent" class="hideToolsTab" >
			<jsp:include page="helpContent.jsp" />
		</div>
 	
		<div id="org_mgmt_success" ></div>
		<div id="org_mgmt_error" ></div>
		
	</div>

 
</div>	

 <security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">
	<input type="hidden" id="hdnScoringEnableActivationEmail" value="Y"/>
 </security:authorize>
 
<script type="text/javascript">
$(function() {
	$(document).on( "click","#menuToggle", function(e) {
		e.preventDefault();
		var left="-185px";
		var padLeft="0";
		if($(this).hasClass("menuShow")) {
			 left="0px";
		     padLeft="185px";
		}
		$("#sidebar").stop().animate({"left":left}, 300);
		$("#container").stop().animate({"padding-left": padLeft }, 300,
			function(){
				 $("#menuToggle").toggleClass("menuShow menuHide");
		    });
	});	
	$('#tools_menu > ul > li > ul > li a').on("click",function(e) {
		e.preventDefault();
		$('#org_mgmt_success').html('');
		$('#org_mgmt_error').html('');
		
		$('#mergeSchoolsSummary').html('');
		$('#moveSchoolSummary').html('');
		$('#deactivateOrgSummary').html('');
		$('#reactivateOrgSummary').html('');
		var hrf = $(this).attr('href');
		if (hrf == undefined) {
			return;
		}
		$('#tools_menu li ul li a').each(function() {
			$(this).parent().prop('class', '');
			var id = $(this).attr('href');
			if (id != undefined && id != "#")
				$(id).hide();
		});
		var cId = $(this).attr('href');		
		if( cId != "#"){
			alert("Hellooo");
			$(this).parent().prop('class', 'current');
			
			if(cId == "#tabs_helpContent" )
			{
				$(cId).show();
			}
			
			if(cId == "#tabs_actemail" )
			{
				$(cId).show();
			}
			if(cId == "#tabs_invalid_accounts" )
			{
				$(cId).show();
			}
			if(cId == "#tabs_internal_users" )
			{
				<security:authorize access="hasRole('EDIT_INTERNAL_USERS')">
				$('#viewInternalUserGridTableId').jqGrid('clearGridData');
				selectedUserArray = [];
				$(cId).show();
				</security:authorize>
			}
			if( cId == "#tabs_org_merge" )
			{
			<security:authorize access="hasRole('TOOLS_MERGE_SCHOOLS')">
				$('#sourceStateForMerging').val('').select2().trigger('change.select2');
				$('#sourceDistrictForMerging').val('').select2().trigger('change.select2');
				$('#sourceschoolForMerging').val('').select2().trigger('change.select2');
				$('#mergeSchoolSubmit').attr('disabled',true);
				$('#mergeSchoolSubmit').addClass('btn_disabled');
				$('#mergeDestinationDistrictContainer').hide();
				$('#mergeDestinationSchoolContainer').hide();
				var type = $('#sourceDistrictForMerging').val();
				if(type===0||type===undefined||type===''){
					$('#sourceschoolForMerging option').remove();
				}
				populateStatesForMerge();
				$('#sourceStateForMerging').change(function(){
					 populateDistrictsForMerge($(this).val());
				});
				$('#sourceDistrictForMerging').change(function(){
					 populateSchoolsForMerge($(this).val(),'source');
				});
				$('#destinationDistrictForMerging').change(function(){
					 populateSchoolsForMerge($(this).val(),'destination');
				});
				$(cId).show();
			</security:authorize>
			}
			else if(cId == "#tabs_org_move" )
		    {
			<security:authorize access="hasRole('TOOLS_MOVE_A_SCHOOL')">
			$('#sourceStateForMoving').val('').select2().trigger('change.select2');
			$('#sourceDistrictForMoving').val('').select2().trigger('change.select2');
			$('#sourceSchoolForMoving').val('').select2().trigger('change.select2');
			$('#moveDestinationDistrictContainer').hide();
			$('#moveSchoolSubmit').attr('disabled',true);
			$('#moveSchoolSubmit').addClass('btn_disabled');
				populateStatesForMove();
				$('#sourceStateForMoving').change(function(){
					populateDistrictsForMove($(this).val());
				});
				$('#sourceDistrictForMoving').change(function(){
					populateSchoolsForMove($(this).val());
				});
				$(cId).show();
			</security:authorize>	
			}
			else if(cId == "#tabs_org_deactivate" )
		    {
			<security:authorize access="hasRole('TOOLS_DEACTIVATE_ORGANIZATION')">
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#deactivateDistrictContainer').hide();
				$('#deactivateSchoolContainer').hide();
				$('#deactivateDistrictsContainer').hide();
				populateStates('deactivate');
				$('#stateDeactivateOrg').on("change",function(){
					populateDistrictsForDeactivation($(this).val());
				});
				$('#stateDeactivateOrg').on("change",function(){
					populateDistricts($(this).val(),'deactivate');
				});
				getOrganizationTypes('deactivate');
				$('#districtsDeactivateOrg').on("change",function(){
					populateSchoolsForDeactivation($(this).val());
				});
				$(cId).show();
			</security:authorize>	
			}
			else if(cId == "#tabs_org_reactivate" )
		    {
			<security:authorize access="hasRole('TOOLS_REACTIVATE_ORGANIZATION')">
			$('#districtReactivateOrg').val('').select2().trigger('change.select2');
			$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
			$('#reactivateDistrictContainer').hide();
			$('#reactivateSchoolContainer').hide();
			$('#reactivateDistrictsContainer').hide();
			populateStates('reactivate');
			$('#stateReactivateOrg').change(function(){
				populateDistrictsForReactivation($(this).val());
			});
			$('#stateReactivateOrg').change(function(){
				populateDistricts($(this).val(),'reactivate');
			});
			getOrganizationTypes('reactivate');
			$('#districtsReactivateOrg').on("change",function(){
				populateSchoolsForReactivation($(this).val());
			});
				$(cId).show();
			</security:authorize>	
			}
			
			if(cId == "#tabs_tdelogin" )
			{
				<security:authorize access="hasRole('EDIT_TDE_LOGIN')">
				$('#studentUserNameGridTableId').jqGrid('clearGridData');
				$("label.error").html('');
				$(cId).show();
				</security:authorize>
			}else if(cId == "#tabs_student_records"){
				<security:authorize access="hasRole('MERGE_STUDENT_RECORDS')">
				$('#mergeStudentGridTableId').jqGrid('clearGridData');
				selectedStudentArr = [];
				enableDisableStudentBtn(0);
				$(cId).show();
				</security:authorize>
			}
			
			else if(cId == "#tabs_org_dlmtestlets" )
		    {
			<security:authorize access="hasRole('RESET_DLM_TESTLETS')">
				$(cId).show();
			</security:authorize>	
			}
			else if(cId == "#tabs_org_managelcs" )
		    {
			<security:authorize access="hasRole('MANAGE_LCS')">
				$(cId).show();
			</security:authorize>	
			}
			
			else if(cId == "#tabs_org_customreports" )
		    {
			<security:authorize access="hasRole('CUSTOM_REPORTS')">
				$(cId).show();
			</security:authorize>	
			}
			else if(cId == "#tabs_org_audithistory" )
		    {
			<security:authorize access="hasRole('AUDIT_HISTORY')">
				$(cId).show();
				viewAuditHistoryInit();
			</security:authorize>	
			}
			
			if(cId == "#tabs_permissions" )
			{
				<security:authorize access="hasRole('PERMISSIONS_AND_ROLES_EXTRACT')">
					$(cId).show();
					permissionsAndRolesExtract_Initmethod();
				</security:authorize>
			}else if(cId == "#tb_permission_upload" ){
				<c:if test="${user.groupCode == 'GSAD'}">
				$(cId).show();
				permissionsUpload_Initmethod();
			</c:if>
			}
		}	
		e.preventDefault();
   	});
	$('#tools_menu > ul > li > a').on("click",
		function(e) {
			e.preventDefault();
			var exist = $(this).attr('href');
			if (exist == undefined)
				return;
			if (!$(this).closest('li').hasClass("current")) {
				$(this).closest('li').toggleClass("current")
						.find('>ul').removeClass('hide');
				$(this).closest('li').toggleClass("current")
						.find('>ul').stop(true, true).toggleClass("show").end();
				$(this).closest('li').siblings("li").removeClass("current");
				$(this).closest('li').find("li").removeClass("current");
			}
	});
	
});
</script>
