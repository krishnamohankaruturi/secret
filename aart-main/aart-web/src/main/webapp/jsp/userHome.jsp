<%@ include file="/jsp/include.jsp" %>
<link href="css/message.css" rel="stylesheet">

<div class="_bcg">
<div class="wrap_bcg"> 
<br/>
    <div class="left_panels">

                    <!--<div class="welcome">
                    	 <h1><fmt:message key="label.userHome.heading"/></h1> 
                        <form class="role"> 
                            <label for="role">Organization</label>
                                <select class="bcg_select sm" name="role">
                                    <option value="System Administrator">System Administrator</option>
                                    <option value="other">Other</option>
                                </select>
                        </form> 
                    </div>--><!-- /welcome -->
               

                  <%--   <security:authorize access="hasAnyRole('DLM_MESSAGE_VIEWER','DLM_MESSAGE_CREATOR')"> --%>
					<div id="messages_exist">
						<!-- <h1>Update</h1> -->
						<table align="left" 
							style="'table-layout:fixed'; width: 750px;border: 1px solid gray; border-radius: 5px; padding: 15px; line-height: 20px;">
							
							<tr style='height:20px;' id="announcementMsg" ><td class='panel_head'  style='padding: 0px 0px 5px 1px !important;' >ANNOUNCEMENTS</td></tr>
							<tr style="width: 100%;padding: 0px 0px 10px 0px !important;">
							<td id="messagelist" style="width: 100%;display:block;height:93px;overflow-y:scroll;margin-left:0px;"></td>
							</tr>
							<tr>
							<td>							
							<img src="images/${imageFolderName}/home_background.jpg" title="Welcome to Educator Portal" alt="Welcome to Educator Portal" style="height:495px;width:100%;border-top: 1px solid #d8d8d8;padding : 10px 0px 0px 0px;"/>
							</td>
							 </tr>
						</table>
	
	
						<div id="notificationMessageWindow" style="display: none;">
							<h5 id="messagedateparagraph"></h5>
							<p id="messagetitleparagraph"></p>
							<hr class="line"/>
							<p id="messagecontentparagraph"></p>
						</div>
					</div>
				<%-- </security:authorize> --%>
                  <%--  <security:authorize access="!hasAnyRole('DLM_MESSAGE_VIEWER','DLM_MESSAGE_CREATOR')">
                    <div id="background_image_visible">
                    <img src="images/home_background.jpg" title="Welcome to Educator Portal" alt="Welcome to Educator Portal" style="height:495px;"/>
                  			 </div>
                  	</security:authorize> --%> <!-- /panel_2span -->
                  	
	            	<div id="background_image_visible_view" >
	                     <img src="images/${imageFolderName}/home_background.jpg" title="Welcome to Educator Portal" alt="Welcome to Educator Portal" style="height:495px;"/> 
					</div>
               </div><!-- /left_panels -->
            
             
         
               <div class="panel_side">
                   <div class="message_center">
                       <h1 class="panel_head"><fmt:message key="label.userHome.myProfile" /></h1>
                       <ul class="links">
                           <li>
                               <a href="javascript:openProfileDialog(${user.id})" class="my-profile-img" title="<fmt:message key="label.userHome.myProfile" />"><img src="images/myprofile.png" alt="<fmt:message key="label.userHome.myProfile" />"/></a>
                           </li>
                       </ul>
                   </div>
                   
                   <div class="message_center">
                       <span class="panel_head quickLinks"><fmt:message key="label.userHome.quicklinks" /></span>
                        <ul class="links">
                        	<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
                                            <c:if test="${imageFolderName eq 'PLTW'}">  
                        						<li><a class="sp pltw_student" href="students.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.student" /></span></a></li>
                        					</c:if>
                        					<c:if test="${imageFolderName eq 'Kite'}"> 
	                                			<li><a class="sp student" href="<c:url value="students.htm"/>"><span class="quicklinkstext"><fmt:message key="label.userHome.student" /></span></a></li>
	                            			</c:if>
                               
                            </security:authorize>
                        	<security:authorize access="hasAnyRole('PERM_ROSTERRECORD_VIEWALL','PERM_ROSTERRECORD_VIEW')">
                               
                                  	<c:if test="${imageFolderName eq 'PLTW'}">  
                        						<li><a class="sp pltw_rosters" href="rosters.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.rosters" /></span></a></li>
                        			</c:if>
                        			<c:if test="${imageFolderName eq 'Kite'}"> 
	                                			<li><a class="sp rosters" href="<c:url value="rosters.htm"/>"><span class="quicklinkstext"><fmt:message key="label.userHome.rosters" /></span></a></li>
	                            	</c:if>                             
                            </security:authorize>
                            
                        	<security:authorize access="hasRole('PERM_TESTSESSION_MONITOR')">
<!--                         	Display the icon only if the user have Manage Test Tabs -->
                        		<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">
<!--                         		User have access to view the Test CO-ordination : Link Test Co-ordination-->
                        		<security:authorize access="hasRole('HIGH_STAKES_TICKETING')"> 
                        			<c:if test="${!(user.currentAssessmentProgramName == 'DLM' || user.currentAssessmentProgramName == 'I-SMART' || user.currentAssessmentProgramName == 'I-SMART2')}">
                        				   <c:if test="${imageFolderName eq 'PLTW'}">  
                        						<li><a class="sp pltw_monitor" href="viewTestCoordination.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.monitorsession" /></span></a></li>
                        					</c:if>
                        					<c:if test="${imageFolderName eq 'Kite'}"> 
	                                			<li><a class="sp monitor" href="viewTestCoordination.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.monitorsession" /></span></a></li>
	                            			</c:if>
	                            	</c:if>
	                            	<c:if test="${(user.currentAssessmentProgramName == 'DLM' || user.currentAssessmentProgramName == 'I-SMART' || user.currentAssessmentProgramName == 'I-SMART2')}">                    				 
	                                		<li><a class="sp monitor" href="viewTestSessions.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.monitorsession" /></span></a></li>
	                            	</c:if>
                        		</security:authorize>
<!--                         		if Assesment program is DLM, I-Smart and I-Smart2 : link Test Management -->
<!-- 									OR -->
<!--                         		User doesn't have access to view the Test Co-ordination : Link Test Management-->
                        		<security:authorize access="!hasRole('HIGH_STAKES_TICKETING')">
									<li><a class="sp monitor" href="viewTestSessions.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.monitorsession" /></span></a></li>								
	                        	</security:authorize>
	                          </security:authorize>   
                            </security:authorize>
                            
                        	<security:authorize access="hasAnyRole('PERM_REPORT_VIEW','PD_TRAINING_EXPORT_FILE_CREATOR', 'PERM_DLM_TRAINING_STATUS_EXTRACT')" >
                        		<c:if test="${imageFolderName eq 'PLTW'}">  
                        			<li><a class="sp pltw_extracts" href="dataExtracts.htm"><span class="quicklinkstext"><fmt:message key="label.userHome.Extracts" /></span></a></li>
                        		</c:if>
                        		<c:if test="${imageFolderName eq 'Kite'}"> 
                               		<li><a class="sp extracts" href="<c:url value='dataExtracts.htm'/>"><span class="quicklinkstext"><fmt:message key="label.userHome.Extracts" /></span></a></li>
                            	</c:if>
                            </security:authorize>
                        </ul>
                   </div><!-- /message_center -->
               </div><!-- /panel_side -->   
		</div>
			<div id="profileViewDiv"></div>
</div>
<span style="display:none;" id="dynamicmessage"><%=request.getParameter("message")%></span>
<input id="userHomeId" value="${user.id}" type="hidden">
<%-- <script src="js/logger.localstorage.min.js"> </script>--%>
<script src="js/localstorage.min.js"> </script>
<script type="text/javascript" src="<c:url value='/js/external/passfield.min.js'/>"></script>
<script>
var fmtUserHomeMsg = {
	  errorMyProfileChangeDefaultOrgAndRoleInvalid : "<fmt:message key='error.myprofile.changeDefaultOrgAndRole.invalid'/>",
	  sucessMyProfileChangeDefaultOrgAndRole : "<fmt:message key='sucess.myprofile.changeDefaultOrgAndRole'/>",
	  errorMyProfileChangeDefaultOrgAndRole : "<fmt:message key='error.myprofile.changeDefaultOrgAndRole'/>"
};

;
//variables passed from UserHomeController

var URL='${previousURL}';

//Function to redirect user to same URL they were on provided they have permission to access the same URL 
//after toggling Role/Organization/Assessment. 
$(function() 
{
	if (URL != null && URL!= '' && URL != undefined  && URL.indexOf("userHome.htm")==-1)
	{
		var urlToClick= $('.main-nav a[href="' + URL + '"]');
		if (urlToClick.length > 0)
		{
			window.location = urlToClick.get(0);
		}
	}
});
$(function() {
	getMessageUserDetails();
	getSecurityAgreement();
	
	if($('#externalLink').val()==='true'){
		window.location.href='users.htm';
	}
});
</script>