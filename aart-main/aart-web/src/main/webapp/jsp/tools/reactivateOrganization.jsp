<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<h1 id="reactivateSchoolHeader">Reactivate Organization</h1>
<div style="margin-left:28px;margin-top: 20px;color: #82a53d;" class="assignScorerpanel_head">Select criteria, then click Reactivate: </div>

<div>
	<form id="toolsReactivateOrgFilterForm" name="toolsReactivateOrgFilterForm" class="form">
	<!--  AMP Changes -->
      <!--  <div class="btn-bar">
			<div id="searchFilterErrors" style="display:none;"></div>
			<div id="searchFilterMessage" style="padding: 20px" class="hidden"></div>
		</div> -->
       
       <table>
	       <tr>
		       <td>
			       <div class="form-fields" id="reactivateStateContainer">
						<label for="stateReactivateOrg" class="field-label">State: <span class="lbl-required">*</span></label> <select
							id="stateReactivateOrg" class="bcg_select required" name="stateReactivateOrg">
							<option value="">Select</option>
						</select>
					</div>
		       </td>
		       <td>
			       <div class="form-fields" id="reactivateOrgTypeContainer">
						<label for="typesOfOrganization" class="field-label">ORGANIZATION TYPE: <span class="lbl-required">*</span></label> <select
							id="typesOfOrganization" class="bcg_select required" name="typesOfOrganization">
							<option value="">Select</option>
						</select>
				  </div>
		       </td>
	       </tr>
	       <tr>
		       <td>
			        <div class="form-fields" id="reactivateDistrictsContainer">
						<label for="districtsReactivateOrg" class="field-label">District: <span class="lbl-required">*</span></label> <select
							id="districtsReactivateOrg" class="bcg_select required" name="districtsReactivateOrg">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields" id="reactivateDistrictContainer">
						<label for="districtReactivateOrg" class="field-label">District: <span class="lbl-required">*</span></label> <select
							id="districtReactivateOrg" class="bcg_select required" name="districtReactivateOrg">
							<option value="">Select</option>
						</select>
					</div>
		       </td>
		       <td>
			       <div class="form-fields" id="reactivateSchoolContainer">
						<label for="schoolsReactivateOrg" class="field-label">SCHOOL: <span class="lbl-required">*</span></label> <select
							id="schoolsReactivateOrg" class="bcg_select required" name="schoolsReactivateOrg">
							<option value="">Select</option>
						</select>
				  </div>
		       </td>
	       </tr>
       </table>
	</form>
	<div id="reactivateOrgSummary" style="display:none;"></div>
	<div id="reactivateOrgSubmitDilg" style="display: none;"></div>
	<div style="float: right; margin-right: 15px;">
		<button class="btn_blue" id="reactivateOrgSubmit">Reactivate</button>
	</div>
	
</div>

<script type="text/javascript" src="<c:url value='/js/tools/reactivateOrganization.js'/>"> 
</script>