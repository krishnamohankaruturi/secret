<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<h1 id="mergeSchoolsHeader">Merge Schools</h1>
<div style="margin-left:28px;margin-top: 20px;color: #82a53d;" class="assignScorerpanel_head">Select criteria, then click Merge: </div>
<div id="toolsMergeSchoolFilter">
	<form id="toolsMergeSchoolFilterForm" name="toolsMergeSchoolFilterForm" class="form">
<!--  AMP Changes -->
		<!-- <div class="btn-bar">
			<div id="searchFilterErrors" style="display:none;"></div>
			<div id="searchFilterMessage" style="padding: 20px" class="hidden"></div>
		</div> -->

		<table>
			<tr>
				<td>
					<div class="form-fields" id="mergeSourceStateContainer">
						<label for="sourceStateForMerging" class="field-label">State: <span class="lbl-required">*</span></label> <select
							id="sourceStateForMerging" class="bcg_select required" name="sourceStateForMerging">
							<option value="">Select</option>
						</select>
					</div>
				</td>
				<td>
					<div class="form-fields" id="mergeSourceDistrictContainer" >
						<label for="sourceDistrictForMerging" class="field-label">Source District: <span class="lbl-required">*</span></label> <select
							id="sourceDistrictForMerging" class="bcg_select required" name="sourceDistrictForMerging">
							<option value="">Select</option>
						</select>
					</div>
				</td>
				<td>
					<div class="form-fields" id="mergeSourceSchoolContainer">
						<label for="sourceschoolForMerging" class="field-label">Source School: <span class="lbl-required">*</span></label> <select
							id="sourceschoolForMerging" class="bcg_select required" name="sourceschoolForMerging">
							<option value="">Select</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-fields" id="mergeDestinationDistrictContainer">
						<label for="destinationDistrictForMerging" class="field-label">Destination District: <span class="lbl-required">*</span></label> <select
							id="destinationDistrictForMerging" class="bcg_select required" name="destinationDistrictForMerging">
							<option value="">Select</option>
						</select>
					</div>
				</td>
				<td>
					<div class="form-fields" id="mergeDestinationSchoolContainer">
						<label for="destinationschoolForMerging" class="field-label">Destination School: <span class="lbl-required">*</span>
						</label> <select id="destinationschoolForMerging" class="bcg_select required" name="destinationschoolForMerging">
							<option value="">Select</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
	</form>

	<div id="mergeSchoolsSummary" style="display:none;"></div>
	<div id="mergeSchoolSubmitdilg" style="display: none;"></div>
	<div style="float: right; margin-right: 15px;">
		<button class="btn_blue" id="mergeSchoolSubmit">Merge</button>
	</div>
</div>		
		

<script type="text/javascript" src="<c:url value='/js/tools/mergeSchools.js'/>"> 
</script>