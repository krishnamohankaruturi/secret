<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<h1 id="deactivateOrganizationHeader">Deactivate Organization</h1>
<div style="margin-left:28px;margin-top: 20px;color: #82a53d;" class="assignScorerpanel_head">Select criteria, then click Deactivate: </div>

<div>
	<form id="toolsDeactivateOrgFilterForm" name="toolsDeactivateOrgFilterForm" class="form">
	<!--  AMP Changes -->
       <!--  <div class="btn-bar">
			<div id="searchFilterErrors" style="display:none;"></div>
			<div id="searchFilterMessage" style="padding: 20px" class="hidden"></div>
		</div> -->
       
       <table>
	       <tr>
		       <td>
			       <div class="form-fields" id="deactivateStateContainer">
						<label for="stateDeactivateOrg" class="field-label">State: <span class="lbl-required">*</span></label> <select
							id="stateDeactivateOrg" class="bcg_select required" name="stateDeactivateOrg">
							<option value="">Select</option>
						</select>
					</div>
		       </td>
		       <td>
			       <div class="form-fields" id="deactivateOrgTypeContainer">
						<label for="typeOfOrganization" class="field-label">ORGANIZATION TYPE: <span class="lbl-required">*</span></label> <select
							id="typeOfOrganization" class="bcg_select required" name="typeOfOrganization">
							<option value="">Select</option>
						</select>
				  </div>
		       </td>
	       </tr>
	       <tr>
		       <td>
			        <div class="form-fields" id="deactivateDistrictsContainer">
						<label for="districtsDeactivateOrg" class="field-label">District: <span class="lbl-required">*</span></label> <select
							id="districtsDeactivateOrg" class="bcg_select required" name="districtsDeactivateOrg">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields" id="deactivateDistrictContainer">
						<label for="districtDeactivateOrg" class="field-label">District: <span class="lbl-required">*</span></label> <select
							id="districtDeactivateOrg" class="bcg_select required" name="districtDeactivateOrg">
							<option value="">Select</option>
						</select>
					</div>
		       </td>
		       <td>
			       <div class="form-fields" id="deactivateSchoolContainer">
						<label for="schoolsDeactivateOrg" class="field-label">SCHOOL: <span class="lbl-required">*</span></label> <select
							id="schoolsDeactivateOrg" class="bcg_select required" name="schoolsDeactivateOrg">
							<option value="">Select</option>
						</select>
				  </div>
		       </td>
	       </tr>
       </table>
	</form>
	<div id="deactivateOrgSummary" style="display:none;"></div>
	<div id="deactivateOrgSubmitDilg" style="display: none;"></div>
	<div style="float: right; margin-right: 15px;">
		<button class="btn_blue" id="deactivateOrgSubmit">Deactivate</button>
	</div>
	
</div>

<script type="text/javascript" src="<c:url value='/js/tools/deactivateOrganization.js'/>"> 
</script>