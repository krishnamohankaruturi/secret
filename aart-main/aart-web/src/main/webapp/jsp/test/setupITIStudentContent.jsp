<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
	
<div>	
	<div>
	<div style='float: left;'>
		<input id="setupITIStudentBackButton" class="panel_btn nextButton setupITIBackButton" type="button" value="Back">
 	</div>
	<div style='float: right;' >
 		<input id="setupITIStudentContentNextButton" class="panel_btn backButton itiContentSelectionNext setupITINextButton ui-state-disabled" type="button" value="Next" disabled="disabled">
	</div>
	</div>
		<div class="divFontForITI studentInfo" id="studentDetailsStudentContent"></div>
	<div>
			<span class="error_message ui-state-error hidden selectStudentContentError" id="selectStudentContentError"><fmt:message key="error.testsessionITI.noContent"/></span> <br />
			<span class="error_message ui-state-error hidden noDataError" id="noDataError"><fmt:message key="error.testsessionITI.noDataEssentialElement"/></span> <br />
			<span class="error_message ui-state-error hidden multipleOverviewsError" id="multipleOverviewsError"><fmt:message key="error.testsessionITI.noDataEssentialElement" /></span><br />	
	</div>
	<br>
	
	<div class="divFontForITI hidden" id="bluePrintCoverageMsg">&nbsp;&nbsp;<fmt:message key="label.iti.bluePrintCoverageMsg" /></div>
	<div> <!-- style="padding:40px;" style="margin-left:55px; margin-top:-10px;"> -->
		<table>
			<tr>
				<td>
				  <div>
					<select id="selectedEEMsg" class="eeSelected" hidden="true">
						<option value="">Essential Element</option>
					</select>
					<div id="eeSelectedDiv" hidden="true" class="eeSelected" style="border:1px solid black;"></div>
				 </div>
				</td>
				<td>
					<form id="contentForm">
						<select id="EEDropdown" title="Essential Element">
							<option value=""> Select Essential Element </option>
						</select>
					</form>
					<span id="width_tmp" style="display:none;"></span>
				</td>
				</tr>
				<tr>
				<td></td><td></td>
				<td>
					<div id="descriptions" style="float: right;" class="hidden">
		  				<span>Essential Element:</span> <span id ="eeDescription"></span> <br><br><br>
		  				<span>Claim:</span> <span id="claimDescription"></span> <br><br><br>
		  				<span>Conceptual Area:</span><span id="conceptualAreaDesc"></span> <br>
					</div>
				</td>
			</tr>
		</table>
	</div>	
	<div class="table_wrap">
		<span class="error_message ui-state-error hidden noLinkageLevelFound" id="noLinkageLevelFound"><fmt:message key="error.testsessionITI.noLinkageLevelsFound"/></span>
	    <span class="error_message ui-state-error hidden noRecTestLetsFound" id="noRecTestLetsFound"><fmt:message key="error.testsessionITI.noRecTestLetsFound"/></span>
	    <span class="error_message ui-state-error hidden noTestLetsFound" id="noTestLetsFound"><fmt:message key="error.testsessionITI.noTestLetsFound"/></span>
		<span id="selectLevelMsg" style="text-decoration: underline;"><fmt:message key="testsessionITI.selectingLevelMessage"/></span> <br><br><br>
		<div class="kite-table">
			<table id="linkageLevelsITITableId" class="responsive"></table>
			<div id="plinkageLevelsITITableId"   class="responsive"></div>
		</div>
	</div>
	<br><br>
	<span class="recommendedLevel" style="padding:20px;">	<br><br></span>
	<div id="longDesc">
		<span id="longDescDetails"></span>
	</div>
</div>
<script type="text/javascript"
	src="<c:url value='/js/test/setupITIStudentContent.js'/>"> </script>


<script type="text/javascript">
var check = '${ITIRecommendedLevel}';
</script>

