<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<%-- <script type="text/javascript"
	src="<c:url value='/js/configuration/editStudent.js'/>"> </script> --%>

		
<script>
var isKSUser = false;
<c:if test="${user.contractingOrgDisplayIdentifier == 'KS'}">
	isKSUser = true;
</c:if>
</script>


<link rel="stylesheet"
	href="<c:url value='/css/theme/configuration.css'/>" type="text/css" />
<div class="config _bcg">
	<div class="breadcrumb">
		<!-- <h1><label id="breadCrumMessage"> </label></h1>
		<h2><label id="breadCrumMessageTag"></label></h2> -->
	</div>
	<!-- /breadcrumbs -->

	<div id="configurationTabs" class="panel_full">
	
		<Div class="tabs">
	 <ul role="list" >
			<security:authorize
				access="hasAnyRole('PERM_BATCH_REGISTER','PERM_BATCH_REPORT','UPLOAD_WEBSERVICE')">
				<li><a href="#tabs_batch"> <fmt:message
							key="label.reports.batch" /></a></li>
			</security:authorize>
			  <security:authorize access="hasRole('EP_MESSAGE_CREATOR')">
				<li><a href="#tabs_createmessage">
				 <fmt:message key="label.nav.createmessage" />
				</a></li>
			</security:authorize>	
			<security:authorize
				access="hasAnyRole('EDIT_FIRST_CONTACT_SETTINGS')">
				<li><a href="#tabs_fcssettings"> <fmt:message
							key="label.firstcontact.survey" />
				</a></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('PERM_ANNUAL_RESET','PERM_PNP_OPTIONS')">
				<li><a href="#tabs_general"> <fmt:message key="label.settings.general" /></a></li>
			</security:authorize>
			<security:authorize access="hasRole('CHANGE_ITI_CONFIG')">
				<li><a href="#tabs_ITI"> <fmt:message
							key="label.reports.ITI" />
				</a></li>
			</security:authorize>
			<security:authorize
				access="hasAnyRole('PERM_ORG_VIEW','PERM_ORG_UPLOAD','PERM_ORG_CREATE')">
				<li><a href="#tabs_organization"> <fmt:message
							key="label.reports.organization" /></a></li>
			</security:authorize>
			<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
				<li><a href="#tabs_qualitycontrol"> <fmt:message
							key="label.nav.qualitycontrol" />
				</a></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('SUMMATIVE_REPORTS_UPLOAD','VIEW_REPORT_CONTROL_ACCESS','VIEW_UPLOAD_RESULTS','CREATE_GRF_INFO','EDIT_GRF_INFO')">
				<li><a href="#tabs_uploadReportData"> <fmt:message
							key="label.reports.uploadreportdata" /></a></li>
			</security:authorize>
			<security:authorize access="hasAnyRole('PERM_ROLE_VIEW', 'PERM_ROLE_MODIFY', 'PERM_ROLE_CREATE', 'PERM_ROLE_DELETE', 'PERM_ROLE_SEARCH')">
				<li><a href="#tabs_roles"> <fmt:message
							key="label.reports.roles" /></a></li>
			</security:authorize>			
			<security:authorize
				access="hasAnyRole('PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_VIEWALL','PERM_ROSTERRECORD_UPLOAD', 'PERM_ROSTERRECORD_CREATE')">
				<li><a href="#tabs_rosters"> <fmt:message
							key="label.reports.rosters" /></a></li>
			</security:authorize>
			<security:authorize
				access="hasAnyRole('PERM_STUDENTRECORD_VIEW','PERM_ENRL_UPLOAD','PERM_STUDENTRECORD_CREATE')">
				<li><a href="#tabs_students"> <fmt:message
							key="label.reports.students" />
				</a></li>
			</security:authorize>
			
			<security:authorize
				access="hasAnyRole('PERM_TESTRECORD_CREATE','PERM_TESTRECORD_CLEAR','PERM_TESTRECORD_VIEW')">
				<li><a href="#tabs_testRecords"> <fmt:message
							key="label.reports.testRecords" />
				</a></li>
			</security:authorize>
			<security:authorize
				access="hasAnyRole('PERM_USER_VIEW','PERM_USER_UPLOAD','PERM_USER_CREATE')">
				<li><a href="#tabs_users"> <fmt:message
							key="label.reports.users" />
				</a></li>
			</security:authorize>
			
			</ul>
			</Div>
		<security:authorize access="hasAnyRole('SUMMATIVE_REPORTS_UPLOAD','VIEW_REPORT_CONTROL_ACCESS','VIEW_UPLOAD_RESULTS','CREATE_GRF_INFO','EDIT_GRF_INFO')">
			<div id="tabs_uploadReportData">
			</div>
		</security:authorize>
		<security:authorize access="hasRole('EDIT_FIRST_CONTACT_SETTINGS')">
			<div id="tabs_fcssettings">
			</div>
		</security:authorize>
		<security:authorize
			access="hasAnyRole('PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_VIEWALL','PERM_ROSTERRECORD_UPLOAD', 'PERM_ROSTERRECORD_CREATE')">
			<div id="tabs_rosters">
			</div>
		</security:authorize>
		<security:authorize
			access="hasAnyRole('PERM_STUDENTRECORD_VIEW','PERM_ENRL_UPLOAD','PERM_STUDENTRECORD_CREATE')">
			<div id="tabs_students">
			</div>
		</security:authorize>
		<security:authorize
			access="hasAnyRole('PERM_ORG_VIEW','PERM_ORG_UPLOAD','PERM_ORG_CREATE')">
			<div id="tabs_organization">
				
			</div>
		</security:authorize>
		<security:authorize access="hasAnyRole('PERM_ROLE_VIEW', 'PERM_ROLE_MODIFY', 'PERM_ROLE_CREATE', 'PERM_ROLE_DELETE', 'PERM_ROLE_SEARCH')">
			<div id="tabs_roles">
			</div>
		</security:authorize>
		<security:authorize
			access="hasAnyRole('PERM_BATCH_REGISTER','PERM_BATCH_REPORT','UPLOAD_WEBSERVICE')">
			<div id="tabs_batch">
			</div>
		</security:authorize>

		<security:authorize access="hasAnyRole('PERM_ANNUAL_RESET','PERM_PNP_OPTIONS')">
			<div id="tabs_general">
			</div>
		</security:authorize>

		<security:authorize access="hasRole('CHANGE_ITI_CONFIG')">
			<div id="tabs_ITI">
			</div>
		</security:authorize>

		<security:authorize
			access="hasAnyRole('PERM_USER_VIEW','PERM_USER_UPLOAD','PERM_USER_CREATE')">
			<div id="tabs_users">
			</div>
		</security:authorize>

		<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
			<div id="tabs_qualitycontrol">
			</div>
		</security:authorize>
		 <security:authorize access="hasRole('EP_MESSAGE_CREATOR')">
			<div id="tabs_createmessage"></div>
		 </security:authorize>
		<security:authorize access="hasAnyRole('PERM_TESTRECORD_CREATE','PERM_TESTRECORD_CLEAR','PERM_TESTRECORD_VIEW')">
			<div id="tabs_testRecords">
			</div>
		</security:authorize>
	</div>
</div>
<input type="hidden" id="refreshGrid" value="false" />
<script>
		var summativeGridRefreshInterval;
		var globalUserLevel = ${user.accessLevel};
		var dlmMultiAssignConfig = '${dlmmultiassignconfig}';
	  	//Setting up the tabs.
		$(function() {
			var firstLoadupReportDataClear = false;
			var firstLoaduploadReportData = true;
			var firstLoaduprosters = true;
			var firstLoadupstudents = true;
			var firstLoaduporganizationMgmt = true;
			var firstLoaduptestRecordsMgmt = true;
			var firstLoaduproles = true;
			var firstLoadupbatchProcesses = true;
			var firstLoadupconfigureITI = true;
			var firstLoadupusersMgmt = true;
			var firstLoadupfindQCTests = true;
			var firstLoadupcreateMessage = true;
			var firstGeneralTab = true;
			
			$.validator.addMethod('validDate', function(value, element, params){
				var optional = this.optional(element);
				var matches = /^\s*(\d{2})\/(\d{2})\/(\d{4})\s*$/.exec(value);
				if (matches != null){
					var month = matches[1];
					var day = matches[2];
					var year = matches[3];
					var valid = month >= 0 && month <= 12;
					if (valid){
						var daysInMonth = 31;
						if ([4,6,9,11].indexOf(month) > -1){
							daysInMonth = 30;
						} else if (month == 2){
							daysInMonth = 28;
							var isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
							if (isLeapYear){
								daysInMonth = 29;
							}
						}
						valid = day >= 0 && day <= daysInMonth;
						return optional || valid;
					}
				}
				return optional;
			}, 'Please enter a valid date (mm/dd/yyyy).');
			
			$('#configurationTabs').tabs({
				beforeActivate: function(event, ui) {

						//TODO need to have a better way of changing the bread crum messages
						var selected = $("#configurationTabs").tabs("option", "active");

		        		$('#configurationTabs li a').removeClass('active');
		        		 $('#configurationTabs li a').eq(ui.index).addClass('active');
		        		 
		        		/* if($(ui.panel).attr("id")!= "tabs_uploadReportData"){
		        	      $('#configurationTabs li a').eq(ui.index).addClass('active');
		        		}else
		        		if(selected){
			        	      $('#configurationTabs li a').eq(ui.index).addClass('active');
			        		} */
		        		
			        		if(ui.newPanel[0].id == 'tabs_uploadReportData') {
		        			if(firstLoadupReportDataClear == true){
		        				clearReportPage();
		        			}else{
		        				firstLoadupReportDataClear = true;
		        			}
		        		}
		        		
				},
				activate: function(event, ui) {
	        		if(ui.newPanel[0].id == 'tabs_uploadReportData') {
	        			if(firstLoaduploadReportData){
	        				var path = 'reportsSetup';
	        				
	        				$('#tabs_uploadReportData').load("configuration.htm?path="+path, function() {
	        					<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS') and !hasAnyRole('SUMMATIVE_REPORTS_UPLOAD','VIEW_UPLOAD_RESULTS','CREATE_GRF_INFO','EDIT_GRF_INFO')">
								$("#reportSetupReportAccess").click();
								</security:authorize>
								
	        					<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD') and !hasAnyRole('VIEW_REPORT_CONTROL_ACCESS','VIEW_UPLOAD_RESULTS','CREATE_GRF_INFO','EDIT_GRF_INFO')">
	        						resetUploadReport();
									loadUploadGrid(false);
									if (summativeGridRefreshInterval !== 'no_APs'){
					    				summativeGridRefreshInterval = setInterval(function(){
					    					loadUploadGrid(false);
					    				}, 10 * 1000);  
									 }
								</security:authorize>
								
								
	        					<security:authorize access="hasAnyRole('CREATE_GRF_INFO','EDIT_GRF_INFO') and !hasAnyRole('VIEW_REPORT_CONTROL_ACCESS','SUMMATIVE_REPORTS_UPLOAD','VIEW_UPLOAD_RESULTS')">
		        					loadGRFGridTable(false);		        					
		        	    			summativeGridRefreshInterval = setInterval(function(){
		        	    					loadGRFGridTable(false);
		        	    				}, 50 * 1000); 
		        					 
								</security:authorize>
								
								<security:authorize access="hasRole('VIEW_UPLOAD_RESULTS') and !hasAnyRole('SUMMATIVE_REPORTS_UPLOAD','VIEW_REPORT_CONTROL_ACCESS','CREATE_GRF_INFO','EDIT_GRF_INFO')">
									$('#reportSetupUploadResults').click();
								</security:authorize>
								
								<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS') and hasRole('SUMMATIVE_REPORTS_UPLOAD') and hasRole('VIEW_UPLOAD_RESULTS')">
								$("#reportSetupReportAccess").click();
								</security:authorize>
								
	        					<security:authorize access="hasAnyRole('CREATE_GRF_INFO','EDIT_GRF_INFO') and !hasAnyRole('VIEW_REPORT_CONTROL_ACCESS','SUMMATIVE_REPORTS_UPLOAD','VIEW_UPLOAD_RESULTS')">
								$("#reportSetupManageGRF").click();
								</security:authorize>
								
	        				});
	        				
	        			/* 	var path='uploadReportData';
		        			$('#tabs_uploadReportData').load("configuration.htm?path="+path, function() {
		        				resetUploadReport();
		        				loadUploadGrid(false);
		        				if (summativeGridRefreshInterval !== 'no_APs'){
			        				summativeGridRefreshInterval = setInterval(function(){
			        					loadUploadGrid(false);
			        				}, 10 * 1000);
		        				 }
		        		   });
	        			firstLoaduploadReportData = false;
	        			 */
	        			firstLoaduploadReportData = false;
	        			}else{
	        				//resetUploadReport();
	        			}
						$('#breadCrumMessage').text("<fmt:message key='label.reports.uploadreportdata'/>");
						$('#breadCrumMessageTag').text("");
					} else {
						if (summativeGridRefreshInterval != null && summativeGridRefreshInterval !== 'no_APs'){
							clearInterval(summativeGridRefreshInterval);
							summativeGridRefreshInterval = null;
						}
						
						if(ui.newPanel[0].id == 'tabs_rosters') {
							if(firstLoaduprosters){
								var path='rosters';
								$('#tabs_rosters').load("configuration.htm?path="+path, function() {
									rostersInit();
									resetRoster();
									<c:if test="${param.view == 'r'}">
										$('#rosterSelect').val('view').trigger('change');
									</c:if>
								});	
							firstLoaduprosters = false;
							}else{
								resetRoster();
							}
							$('#breadCrumMessage').text("Configuration: Rosters");
							$('#breadCrumMessageTag').text("");
						} else if(ui.newPanel[0].id == 'tabs_students') {
							if(firstLoadupstudents){
								var path='students';
								$('#tabs_students').load("configuration.htm?path="+path, function() {
									studentsInit();
									resetStudents();
									<c:if test="${param.view == 's'}">
										$('#studentActions').val('viewStudents').trigger('change');
									</c:if>
								});
							firstLoadupstudents = false;
							}else{
								resetStudents();
							} 
							//$('#breadCrumMessage').text("<fmt:message key='label.config.students'/>");
							//$('#breadCrumMessageTag').text("");							
						} else if(ui.newPanel[0].id == 'tabs_organization') {
							if(firstLoaduporganizationMgmt){
								var path='organizationMgmt';
								$('#tabs_organization').load("configuration.htm?path="+path, function() {
									organizationMgmtInit();
									resetOrganization();
								});
							firstLoaduporganizationMgmt = false;
							}else{
								resetOrganization();
							} 
							$('#breadCrumMessage').text("Configuration: Organization");
							$('#breadCrumMessageTag').text("");							
						} else if(ui.newPanel[0].id == 'tabs_testRecords') {
							if(firstLoaduptestRecordsMgmt){
								var path='testRecordsMgmt';
								$('#tabs_testRecords').load("configuration.htm?path="+path, function() {
									testRecordInit();
									resetTestRecord();
								});
							firstLoaduptestRecordsMgmt = false;
							}else{
								resetTestRecord();
							} 
						} else if(ui.newPanel[0].id == 'tabs_roles') {
							if(firstLoaduproles){
								var path='roles';
								$('#tabs_roles').load("configuration.htm?path="+path, function() {
									resetRoles();
									rolesInitMethod();
								});
							firstLoaduproles = false;
							}else{
								resetRoles();
							} 
							$('#breadCrumMessage').text("<fmt:message key='label.config.roles'/>");
							$('#breadCrumMessageTag').text("");
						} else if(ui.newPanel[0].id == 'tabs_batch') {
							if(firstLoadupbatchProcesses){
								var path='batchProcesses';
								$('#tabs_batch').load("configuration.htm?path="+path, function() {
									resetBatchRegistration();
									resetBatchReporting();
								<security:authorize access="hasRole('PERM_BATCH_REGISTER') and !hasAnyRole('UPLOAD_WEBSERVICE','PERM_BATCH_REPORT')">
									$("#registration").click();
								</security:authorize>
								<security:authorize access="hasRole('PERM_BATCH_REPORT') and !hasAnyRole('PERM_BATCH_REGISTER','UPLOAD_WEBSERVICE')"> 
									$("#reporting").click();
								</security:authorize>
								<security:authorize access="hasRole('UPLOAD_WEBSERVICE') and !hasAnyRole('PERM_BATCH_REGISTER','PERM_BATCH_REPORT')">
									$("#ksdeWebService").click();
								</security:authorize>	
								<security:authorize access="hasRole('PERM_BATCH_REGISTER') and hasRole('PERM_BATCH_REPORT') and hasRole('UPLOAD_WEBSERVICE')">
									$("#registration").click();
								</security:authorize>
								<security:authorize access="hasRole('PERM_BATCH_REPORT') and hasRole('UPLOAD_WEBSERVICE') and !hasRole('PERM_BATCH_REGISTER')"> 
									$("#reporting").click();
								</security:authorize>
								<security:authorize access="hasRole('PERM_BATCH_REGISTER') and !hasAnyRole('UPLOAD_WEBSERVICE','PERM_BATCH_REPORT')">
									$("#registration").click();
								</security:authorize>
								<security:authorize access="hasRole('PERM_BATCH_REPORT') and !hasAnyRole('PERM_BATCH_REGISTER','UPLOAD_WEBSERVICE')"> 
									$("#reporting").click();
								</security:authorize>
								<security:authorize access="hasRole('UPLOAD_WEBSERVICE') and !hasAnyRole('PERM_BATCH_REGISTER','PERM_BATCH_REPORT')">
									$("#ksdeWebService").click();
								</security:authorize>
							  });
							firstLoadupbatchProcesses = false;
							}else{
								resetBatchRegistration();
								resetBatchReporting();
							} 
						} 
						else if(ui.newPanel[0].id == 'tabs_general') {
							if(firstGeneralTab){
								var path='generalSettings';
								$('#tabs_general').load("configuration.htm?path="+path, function() {
									orgAcademicYearInit();
								});
								firstGeneralTab = false;
							}
							$('#breadCrumMessage').text("Configuration: General");
							$('#breadCrumMessageTag').text("");
						}
						else if(ui.newPanel[0].id == 'tabs_ITI') {
							if(firstLoadupconfigureITI){
								var path='configureITI';
								$('#tabs_ITI').load("configuration.htm?path="+path, function() {
									
								});
							firstLoadupconfigureITI = false;
							}else{

							} 
							$('#breadCrumMessage').text("Configuration: ITI");
							$('#breadCrumMessageTag').text("");
						} else if(ui.newPanel[0].id == 'tabs_users') {
								var path='usersMgmt';
								$('#tabs_users').load("configuration.htm?path="+path, function() {
									resetUser();
								});
							$('#breadCrumMessage').text("Configuration: Users");
							$('#breadCrumMessageTag').text("");
						}else if(ui.newPanel[0].id == 'tabs_fcssettings') {
							var path='firstContactSettings';
							$('#tabs_fcssettings').load("configuration.htm?path="+path, function() {
							});
							$('#breadCrumMessage').text("Configuration: First Contact Survey");
							$('#breadCrumMessageTag').text("");
						}
						else if(ui.newPanel[0].id == 'tabs_qualitycontrol') {
							if(firstLoadupfindQCTests){
								var path='findQCTests';
								$('#tabs_qualitycontrol').load("configuration.htm?path="+path, function() {
									findQCTestsInit();
								});
							firstLoadupfindQCTests = false;
							}else{
								
							} 
							$('#breadCrumMessage').text("Configuration: Quality Control");
							$('#breadCrumMessageTag').text("");
						} else if(ui.newPanel[0].id == 'tabs_createmessage') {
							if(firstLoadupcreateMessage){
								var path='createMessage';
								$('#tabs_createmessage').load("configuration.htm?path="+path, function() {
									findUpdateMessagesInitJS();
								 });
							firstLoadupcreateMessage = false;
							}else{

							} 
							$('#breadCrumMessage').text("Configuration: Quality Control");
							$('#breadCrumMessageTag').text(""); 
						} else if(ui.newPanel[0].id == 'tabs_testRecords') {
							var path='testRecordsMgmt';
							$('#tabs_testRecords').load("configuration.htm?path="+path, function() {
							resetTestRecord();
							});
						}
					}
				}
			}).scrollabletab();
			//$('#configurationTabs').tabs('paging', { cycle: true,tabsPerPage:6 });
			/* <security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')">
			$("#configurationTabs").tabs("select", "#tabs_uploadReportData");
			</security:authorize> */
			
			$('#createOrganization').on("newOrg", function(e){
				
				//add users manually
			    $('#addUserFilter_1').orgFilter('destroy');
			    $('#addUserFilter_1').orgFilter();
			    $('#addUserFilter_1').orgFilter('option','requiredLevels',[20]);
			    
			    //upload users
			    $('#uploadUserOrgFilter').orgFilter('destroy');
			    $('#uploadUserOrgFilter').orgFilter();
			    $('#uploadUserOrgFilter').orgFilter('option','requiredLevels',[50]);
			    
			    //view users
				$('#viewUserOrgFilter').orgFilter('destroy');
			    $('#viewUserOrgFilter').orgFilter();
			    $('#viewUserOrgFilter').orgFilter('option','requiredLevels',[20]);
				
				//view roster
				$('#viewRosterOrgFilter').orgFilter('destroy');
				$('#viewRosterOrgFilter').orgFilter();
				$('#viewRosterOrgFilter').orgFilter('option','requiredLevels',[70]);
				
				//upload roster
				$('#uploadRosterOrgFilter').orgFilter('destroy');
				$('#uploadRosterOrgFilter').orgFilter();
				$('#uploadRosterOrgFilter').orgFilter('option','requiredLevels',[70]);
				
				//create roster
				$('#createRosterOrgFilter').orgFilter('destroy');
				$('#createRosterOrgFilter').orgFilter();				
				$('#createRosterOrgFilter').orgFilter('option','requiredLevels',[70]);
				
				//view students
				$('#viewStudentsOrgFilter').orgFilter('destroy');
				$('#viewStudentsOrgFilter').orgFilter();	
				
				<c:choose>
					<c:when test="${user.accessLevel  <= 50 }">
						$('#viewStudentsOrgFilter').orgFilter('option','requiredLevels',[50]);
					</c:when>
					<c:otherwise>
						$('#viewStudentsOrgFilter').orgFilter('option','requiredLevels',[70]);
					</c:otherwise>
				</c:choose>
				
				//upload enrollment
				$('#uploadEnrollmentOrgFilter').orgFilter('destroy');
				$('#uploadEnrollmentOrgFilter').orgFilter();
				$('#uploadEnrollmentOrgFilter').orgFilter('option','requiredLevels',[60]);
				
				//add student manually
				$('#addStudentOrgFilter').orgFilter('destroy');
				$('#addStudentOrgFilter').orgFilter();
				$('#addStudentOrgFilter').orgFilter('option','requiredLevels',[70]);
				
				//view organization
				$('#viewOrgOrgFilter').orgFilter('destroy');
				$('#viewOrgOrgFilter').orgFilter();
				$('#viewOrgOrgFilter').orgFilter('option','requiredLevels',[20]);
				
				//upload organization
				$('#uploadOrgOrgFilter').orgFilter('destroy');
				$('#uploadOrgOrgFilter').orgFilter();							
				$('#uploadOrgOrgFilter').orgFilter('option','requiredLevels',[20]);
				
			});
			
		/* 	$('#configurationTabs li a').eq(selected).removeClass('active'); */
			// $('#configurationTabs').tabs({ selected: null, unselect: true });
		
			var selected = $("#configurationTabs").tabs("option", "active");
			$('#configurationTabs li').eq(selected).removeClass('ui-tabs-selected ui-state-active');	
	        $('#configurationTabs li a').eq(selected).removeClass('active');
	
			$('#configurationTabs').children('[id^=tabs]').addClass('ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide');

			 <c:if test="${param.view == 'r'}">
				$("#configurationTabs").tabs("option", "active", "#tabs_rosters");
			</c:if>
			 <c:if test="${param.view == 's'}">
				$("#configurationTabs").tabs("option", "active", "#tabs_students");
			</c:if>
			<c:if test="${param.view == 'externalLink'}">
			$("#configurationTabs").tabs("option", "active", "#tabs_users");
			$('#usersSelect').val('view').trigger('change.select2');
		</c:if>
				
	    });
	  	
		
</script>
