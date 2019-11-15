<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/kidserrorsdashboard.css'/>" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/external/select2/4.0.1/select2.min.js'/>"></script>

<script type="text/javascript" src="<c:url value='/js/kidserrorsdashboard.js?v=1'/>"></script>
<br>
<div  class="kidserrormsgs" >
	<div id="ssidfilters">
		<form id="searchKidsErrosBySsidForm" class ="form">
			<fieldset class="fieldset-auto">
				<legend>Search by SSID</legend>
							
				<div class="searchFields">
					<label for="ssid" class="field-label"><span class="lbl-required">*</span>State Student Identifier: </label>
					<input id="ssid" name="ssid" />
					<label id="ssidErrorDis" style="display:none;font-size:0.8em;" class="error"></label>										
				</div>
				
				<div class="searchFields">
					<button id="searchByssidBtn" class="btn_blue_kids">Search by SSID</button>
				</div>
			</fieldset>
		</form>
	</div>
	
	<h3>OR</h3>
	
	<div id="orgsfilter">
		<form id="searchKidsErrorsbyOrgForm" class ="form">
			<fieldset class="fieldset-auto" style="padding-top:0px;padding-bottom:0px">
				<legend>Search by District/School</legend>
					<div class="form-fields" id="stateFilter">
						<label for="stateOrgFilter" class="field-label"><span class="lbl-required">*</span>State:</label>
						<select id="stateOrgFilter" title="State" class="bcg_select required" name="stateOrgFilter">
							<option value="">Select</option>
						</select>
						<label id="stateErrorDis" style="display:none;font-size:0.8em;" class="error"></label>
					</div>

					<div class="form-fields" id="districtFilter">
						<label for="districtOrgsFilter" class="field-label"><span class="lbl-required">*</span>DISTRICT:</label>
						<select id="districtOrgsFilter" title="District" class="bcg_select required" name="districtOrgsFilter">
							<option value="">Select</option>
						</select>
						<label id="districtKidsErrorDis" style="display:none;font-size:0.8em;" class="error"></label>
					</div>
				
				<div class="form-fields" id="schoolFilter">
					<label for="schoolOrgsFilter" class="field-label">SCHOOL:</label>
					<select id="schoolOrgsFilter" title="School" class="bcg_select required" name="schoolOrgsFilter">
						<option value="">Select</option>
					</select>
				</div>
				
				<div class="form-fields">
					<button class="btn_blue_kids" id="searchByOrgBtn">Search</button>
				</div>
			</fieldset>
		</form>
	</div>			
	
	<div class ="table_wrap">
	<div class="kite-table">		
		<table id="kidsErrorTable"  class="responsive" role='presentation'></table>
		<div id="pkidsErrorTable" class="responsive"></div>
	</div>
	<div id="viewKidRecordsPopup" class="_bcg config hidden" style="padding-left: 20px;">
		<div id="viewKidRecordsContainer"  class="kite-table">
 			<table class="responsive" id="viewKidsRecordsGrid" role='presentation'></table>
			<div id="viewKidRecordsGridPager"></div>
		</div>
	</div>
	
</div>
	
</div>