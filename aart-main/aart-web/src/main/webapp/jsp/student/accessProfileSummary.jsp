<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
<style>
.containerBlock {
	font: 20px Arial, Helvetica, sans-serif;
	color: #94b54d;
}
.divBlockFont_local {
	font: 1em Arial, Helvetica, sans-serif;
	color: #0e76bc;
	font-weight: bold;
}

.h2 {
	display: inline;
}

h2 {
	color: #4090E1;
	font-size: 1.5em;
	font-weight: 300;
}
.contrastColorDiv1 {
	border: 2px solid #cccccc;
	border-radius: 5px 5px 5px 5px;
	cursor: pointer;
	font-weight: bold;
	margin-bottom: 20px;
	margin-left: 20px;
	padding: 5px;
	text-align: center;
	width: 60px;
}

.stuInfo_local ul {
	font: 1em Arial, Helvetica, sans-serif;
	color: #0e76bc;
	float: left;
	margin: 0;
	padding: 0;
}

.stuInfo ul li {
	margin: 20px 0;
	font-size: .95em;
}

.stuInfo_local .stu_label_local li {
	background-color: #fff;
	color: #0e76bc;
	padding: 10px 0 10px 10px;
	text-transform: uppercase;
}

.stuInfo_local .stu_value_local li {
	padding: 10px;
	background-color: #fff;
	color: #646567;
	font-weight: 300;
}
.profileSummaryEditSettingsBtnDiv{
	box-sizing: unset;
}
</style>
<div class="profileSummaryEditSettingsBtnDiv" >
	<input id="studentIdPNP" value ="${studentId}" type="text" class="hidden" />
	<input id="fgcolor" value ="${fgcolor}" type="text" class="hidden" />
	<input id="bgcolor" value ="${bgcolor}" type="text" class="hidden" />
	<input id="colourOverlay" value ="${colourOverlay}" type="text" class="hidden" />
	<input id="studentInfo" value ="${studentInfo}" type="text" class="hidden" />
	<input id="currentOrgName" value="${user.organization.organizationName}" type="text" class="hidden" />
	<div style="display: block; float: left; margin-right: 1.99203%; width: 38%; padding: 0 10px; margin-bottom: 25px;">
		<div class="divBlockFont_local"><h2>Student Demographics</h2></div>		
		<div class="stuInfo_local">
			<ul class="stu_label_local">
				<li>First Name:</li>
				<li>Middle Name:</li>
				<li>Last Name:</li>
				<li>State ID:</li>
				<li>Grade:</li>
				<li>Gender:</li>
				<li>Date Of Birth:</li>
			</ul>
			<ul id="stu_value" class="stu_value_local">
			</ul>
		</div>
	</div>
	<div style="display: block; float: left; margin-right: 1.99203%; width: 60%; margin-right: 0; padding: 0 10px; margin-bottom: 25px;">
		<security:authorize access="hasAnyRole('CREATE_STUDENT_PNP','EDIT_STUDENT_PNP')">
			<c:if test="${previewAccessProfileFlag}">	 
			   <div style="float: right;"><button type="button" id="edit" class="btn_blue editSettings" style="width: 150px;" > Edit Settings </button></div>
		    </c:if>
        </security:authorize>
		<div class="divBlockFont_local"><h2>Current Profile Settings</h2></div>
		<div style="height: 320px; overflow-y:scroll;width: 100%;">
		
			<div id="errorMessage" class="errorMessage hidden">
				<b>No accessibility preferences have been set </b><br>
			</div>
	
			<div id="accessProfileSummaryDiv" >
				<div id="Magnification" class="Magnification containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Magnification </label><br>
				</div>
				<div id="Masking" class="Masking  containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Masking </label><br>
				</div>
				<div id="ColourOverlay" class="ColourOverlay containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Overlay Color </label><br>
					<div>
					<label style="color: #646567;"> Color :</label>
						<span class="colouroverlay" style="border: 1px solid #000000; display:inline-block; width:15px;height:15px;margin-left:1%; margin-bottom:-3px;"></span>
					</div>		
				</div>
				<div id="ForegroundColour" class="ForegroundColour containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Contrast Color </label><br><br>
					<div id="contrastColorDiv1" class="contrastColorDiv1" style="margin-left:0px; margin-top:-10px">ABC</div>
				</div>
				<div id="InvertColourChoice" class="InvertColourChoice containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Invert Color Choice </label><br>
				</div>
				<div id="itemTranslationDisplay" class="itemTranslationDisplay containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Item Translation Display </label><br>
				</div>
				<div id="keywordTranslationDisplay" class="keywordTranslationDisplay containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Keyword Translation Display </label><br>
				</div>
				<div id="Signing" class="Signing containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Signing </label><br>
				</div>
				<div id="Tactile" class="Tactile containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Tactile </label><br>
				</div>
				<div id="Braille" class="Braille containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Braille </label><br>
				</div>
				<div id="AuditoryBackground" class="AuditoryBackground containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Auditory Background </label><br><br>
				</div>
				<div id="breaks" class="breaks containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Breaks </label><br><br>
				</div>
				<div id="AdditionalTestingTime" class="AdditionalTestingTime containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Additional Testing Time </label><br>
				</div>
				<div id="Spoken" class="Spoken containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Spoken </label><br>
				</div>
				<div id="onscreenKeyboard" class="onscreenKeyboard containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Single Switches </label><br>
				</div>
				<div id="setting" class="setting containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Setting </label><br>
				</div>		
				<div id="presentation" class="presentation containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Presentation </label><br>
				</div>	
				<div id="response" class="response containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Response </label><br>
				</div>	
				<div id="supportsRequiringAdditionalTools" class="supportsRequiringAdditionalTools containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Supports Requiring Additional Tools </label><br>
				</div>
				<div id="supportsProvidedOutsideSystem" class="supportsProvidedOutsideSystem containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Supports Provided Outside System </label><br>
				</div>		
				<div id="supportsProvidedByAlternateForm" class="supportsProvidedByAlternateForm containerDiv hidden">
					<hr style="width: 100%; height:1px;"/>
					<label class='containerBlock'> Supports Provided By Alternate Form </label><br>
				</div>																					
			</div>			
		</div>
	</div>
	<br/><br/><br/><br/>
</div>
<input type="hidden" id="studentIdforProfileSummary" value="${studentId}"/>