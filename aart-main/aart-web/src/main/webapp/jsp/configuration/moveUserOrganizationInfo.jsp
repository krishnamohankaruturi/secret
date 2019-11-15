
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/external/jquery.json-2.4.min.js'/>"> </script>
<style>
.moveVertical_border {
    border-right-style:solid;
    border-right-color: #FF9933;
	border-width:2px;
}
.moveUserPanelHeading {
	 text-transform: unset;
	 color: #5f7e1a;
	 font-size: 1.4em;
	 font-weight: 400;
}
#move-popup .form .form-fields {
     margin:3px;
}
#moveUsersOrganization table td {
	vertical-align: top !important;
} 
 
#moveUsersOrganization .form .form-fields {
    margin: 3px 20px;
}

</style>

<div class="_bcg" id="move-popup">
	<div id="moveUsersOrganization-contain" class="form wrap_bcg">
		<table id="moveUserOrganizationContainertable" role='presentation' cellspacing="0">
			<tr>
            	<td colspan="2" class="moveVertical_border">
           			<div style="margin-left: 13px;">
           				<span  class="moveUserPanelHeading">Organization moving from</span>
           			</div>
           		</td>
           		<td colspan="4" style="padding-left: 25px;">
           			<div>
           				<span class="moveUserPanelHeading">Select new Organization</span>
           			</div>
           		</td>
           		<td colspan="4" style="padding-left: 25px;">
           			<div>
           				 <button id="moveUsersOrganizationNext" class="btn_blue" style="float:right;"><fmt:message key="label.config.user.create.next"/></button>
           			</div>
           		</td>
            </tr>
            <tr style="border-right-color: red">
            	<td colspan="2" class="moveVertical_border" style="width:25%;">
           			<div  style="margin-left: 35px;">
        					<label for="moveUserOrganizationOldDistrict" class="field-label" style="margin: 9px; font-size: 1.1em; color: #0e76bc">DISTRICT :</label>
        					<span id="moveUserOrganizationOldDistrict"></span>
         			</div>
         			<div  style="margin-left: 35px;">
        					<label style="margin: 9px; font-size: 1.1em; color: #0e76bc"></label>
         			</div>
         			<div id="moveUserOrganizationOldSchoolDiv" style="margin-left: 35px;">
        					<label for="moveUserOrganizationOldSchool" class="field-label" style="margin: 9px; font-size: 1.1em; color: #0e76bc">SCHOOL :</label>
        					<span id="moveUserOrganizationOldSchool"></span>
         			</div> 
         			 <div style="margin-left: -5px;">
           				<p class="error_message ui-state-error selectedOrganizationExist hidden" id="selectedOrganizationExist" ><fmt:message key="error.config.user.move.selectedOrganizationExist"/></p>
           				<p class="error_message ui-state-error selectAtleastOneUserToMove hidden" id="selectAtleastOneUserToMove" ><fmt:message key="error.config.user.move.selectAtleastOneUser"/></p>
           			</div>         			  			
           		</td>
           		<td colspan="2" style="width: 35%;">
           			<div "style="margin-left: -5px;">
           				<p class="error_message ui-state-error selectOrganizationToMove hidden" id="selectOrganizationToMove" ><fmt:message key="error.config.user.move.selectOrganizationToMove"/></p>
           			</div>
           			<div id="moveUsersOrganizationNewDistrictDiv" class="form-fields" style="margin-left: 23px">
        					<label for="moveUsersOrganizationNewDistrict" style="font-size: 1em;" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
							<select id="moveUsersOrganizationNewDistrict" class="bcg_select required" name="moveUsersOrganizationNewDistrict" title="District">
								<option value="">Select</option>
							</select>        					
         			</div>
         			<div id="moveUsersOrganizationNewSchoolDiv" class="form-fields" style="margin-left: 23px">        					
						<label for="moveUsersOrganizationNewSchool" style="font-size: 1em;" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
						<select id="moveUsersOrganizationNewSchool" class="bcg_select required" name="moveUsersOrganizationNewSchool" title="School">
							<option value="">Select</option>
						</select>									
         			</div>         			
           		</td>
            </tr>
            <tr>
            	<td colspan="2" class="moveVertical_border">
           			<div style="margin-left: 13px; margin-top:-39px;">
           				<span class="moveUserPanelHeading">Selected users moving</span>
           			</div>
           		</td>
            </tr>
            <tr style="border-right-color: red">
            	<td colspan="2" class="moveVertical_border" style="width:25%;">
        				<div id="moveUserOrganizationInfoGridCell" style="margin-right: 9px; ">
							<div id="moveUserOrganization" hidden="hidden" class="hidden"></div>
						 	<div id="moveUserOrganizationInfoContainer" class="kite-table">
						 		<table class="responsive" id="moveUserOrganizationInfoGridTableId"></table>
								<div id="moveUserOrganizationInfoGridPager" style="width: auto;"></div>
						 	</div>
	                     </div>
           		</td>
            </tr>
		</table>		
	</div>
</div>
