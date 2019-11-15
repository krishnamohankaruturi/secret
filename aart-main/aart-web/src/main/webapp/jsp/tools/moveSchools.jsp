<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<h1 id="moveSchoolHeader">Move School</h1>
<div style="margin-left:28px;margin-top: 20px;color: #82a53d;" class="assignScorerpanel_head">Select criteria, then click Move: </div>
<div>
	<form id="moveSchoolFilterForm" name="moveSchoolFilterForm" class="form">
<!--  AMP Changes -->
		<!-- <div class="btn-bar">
			<div id="searchFilterErrors" style="display:none;"></div>
			<div id="searchFilterMessage" style="padding: 20px" class="hidden"></div>
		</div> -->

		<table>
			<tr>
				<td>
					<div class="form-fields" id="moveSourceStateContainer">
						<label for="sourceStateForMoving" class="field-label">State: <span
							class="lbl-required">*</span>
						</label> <select id="sourceStateForMoving" class="bcg_select required" name="sourceStateForMoving">
							<option value="">Select</option>
						</select>
					</div>
				</td>
				<td>
					<div class="form-fields" id="moveSourceDistrictContainer">
						<label for="sourceDistrictForMoving" class="field-label">Source District: <span
							class="lbl-required">*</span>
						</label> <select id="sourceDistrictForMoving" class="bcg_select required" name="sourceDistrictForMoving">
							<option value="">Select</option>
						</select>
					</div>
				</td>
				<td>
					<div class="form-fields" id="moveSourceSchoolContainer">
						<label for="sourceSchoolForMoving" class="field-label">Source School:<span class="lbl-required">*</span></label> <select
							id="sourceSchoolForMoving" class="bcg_select required" name="sourceSchoolForMoving">
							<option value="">Select</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-fields" id="moveDestinationDistrictContainer">
						<label for="destinationdistrictForMoving" class="field-label">Destination District: <span
							class="lbl-required">*</span>
						</label> <select id="destinationdistrictForMoving" class="bcg_select required" name="destinationdistrictForMoving">
							<option value="">Select</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<div id="moveSchoolSummary" style="display:none;"></div>
	<div id="moveSchoolSubmitDilg" style="display: none;"></div>
	<div style="float: right; margin-right: 15px;">
		<button class="btn_blue" id="moveSchoolSubmit">Move</button>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/tools/moveSchools.js'/>"> 
</script>