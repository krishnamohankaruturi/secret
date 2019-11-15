<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>


<link rel="stylesheet" href="./css/colorpicker/colorPicker.css" type="text/css" />

<style>
.topInfoLeft {
	font: 15px Arial, Helvetica, sans-serif;
	margin-left: 2%;
	display: inline-block;
	width: 85%;
}

.topInfoRight {
	margin-right: 1px;
	display: inline-block;
}

.divLeftCol {
	display: block;
	float: left;
	margin-right: 4.99203%;
	width: 22.50598%;
	padding: 0 10px;
	margin-bottom: 25px;
}

.divCenterCol {
	display: block;
	float: left;
	margin-right: 1.99203%;
	width: 33.50199%;
	margin-right: 0;
	padding: 0 10px;
	margin-bottom: 25px;
}

.divRightCol {
	display: block;
	float: left;
	margin-right: 1.99203%;
	width: 33.20199%;
	margin-right: 0;
	padding: 0 10px;
	margin-bottom: 25px;
}

.divBlock {
	width: 345px;
	padding: 30px 30px;
	float: left;
}

.height200 {
	height: 234px;
}

.divNestedBlock {
	width: 260px;
	padding: 10px 10px;
	margin-left: 10%;
	position: relative;
	float: left;
}

.controlset {
	display: block;
	float: left;
	width: 30%;
	padding: 0.25em 0;
}

#systemIndependentContent ul li {
 	float: none;
    display: inline;
    width: 33%;
    margin-top: 5px;
    background-color: #FFFFFF;
}
#systemIndependentContent ul{
		display: -webkit-box;
    	display: -ms-flexbox;
    	display: flex;
   	 	-ms-flex-wrap: wrap;
        flex-wrap: wrap;
}

.messages{
margin-left:590px !important;
margin-top:53px  !important;
}
</style>

<div>
	<div>
		<div class="topInfoLeft">
			<label style="font:20px Arial,Helvetica,sans-serif;" class="divBlockFont"></label>
			<div class="messages">
    	      <span class="error_message ui-state-error selectAllLabels hidden" id="selectAllLabelsAccessProfileSystem"><fmt:message key=""/></span>
    	      <br />
    	      <span class="info_message ui-state-highlight successMessage hidden" id="successMessageDisplayEnhancementsAccessProfileSystem" >
    	      	<fmt:message key="label.pnp.successful"/>
    	      </span>
    	      <br/>
            </div>
		</div>
		<div class="topInfoRight">
			<button type="button" id="saveAccessProfileSystemIndependent" class="btn_blue save" style="width: 90px; margin-left: 24px;float:right; margin-top:-87px" > 
				<fmt:message key="button.save"/> 
			</button>
		</div>
	</div>
	<div id="systemIndependentContent" style="font:20px; width: 100%;">
		<ul>
			<li>
			<div id='settingBlock'> 
				 <div id="settingSeparateQuiteSettingOther">
				 	<label class="divBlockFont"> <fmt:message key="label.pnp.setting"/> </label>
				 </div>
				 <div class='divNestedBlock'>
				 	<div id="quietSettting">
				 		<input type="checkbox" id="quietSettting_cb" title="Separate, quiet, or individual setting" class="setting" name="setting" value="setting">
			 			<fmt:message key="label.pnp.separate.quite.setting"/><br/><br/>	
				 	</div>
				 	<input type="text" id="settingseparateQuiteSetting_hidden" class="settingseparateQuiteSetting hidden" name="setting"/>				
				 </div>
			 </div>
			</li>
			<li>
			<div id='presentationBlock'>	
		 		<div id="presentationOther">			 
				 <label class="divBlockFont" for="presentationOther"> <fmt:message key="label.pnp.presentation"/> </label>
				</div>
				 <div class='divNestedBlock'>
					 <div id="accommodation">
					 	<input type="checkbox" id="presentationsomeotheraccommodation" class="presentationsomeotheraccommodation" 
					 		name="presentation" value="accommodation" title="Some other accommodation was used">
					 		<fmt:message key="label.pnp.some.other.accommodation"/><br/><br/>
					 </div>		
					 <div id="assessment">
					 	<input type="checkbox" id="presentationreadsAssessmentOutLoud" class="presentationreadsAssessmentOutLoud" 
					 		title="Student reads the assessment aloud to self" name="presentation" value="assessment">
					 		<fmt:message key="label.pnp.student.reads.assessment"/> <br/><br/>	
					 </div>
					 <div id="translations">		
					 	<input type="checkbox" id="presentationuseTranslationsDictionary" class="presentationuseTranslationsDictionary" 
					 		title="Student used a translation dictionary" name="presentation" value="translations">
					 		<fmt:message key="label.pnp.student.use.translations"/> <br/><br/>	
					 </div>	
				 </div>
			</div>
			</li>
			<li>
			<div id='responseBlock'>
				<div id="responseOther">
					<label class="divBlockFont" for="responseOther"> <fmt:message key="label.pnp.response"/> </label>
			 	</div>
				<div class='divNestedBlock'>
					<div id="dictated">
						<input type="checkbox" id="responsedictated" class="responsedictated" 
							title=" Student dictated his/her answers to a scribe" name="response" value="dictated">
							<fmt:message key="label.pnp.student.dictated"/> <br/><br/>	
					</div>
					<div id="communication">	
				 		<input type="checkbox" id="responseusedCommunicationDevice" 
				 			class="responseusedCommunicationDevice" title="Student used a communication device" name="response" value="communication">
				 			<fmt:message key="label.pnp.student.used.communication"/> <br/><br/>
				 	</div>
				 	<div id="responses">		
				 		<input type="checkbox" id="responsesignedResponses" class="responsesignedResponses" 
				 			title="Student signed responses" name="response" value="responses">
				 			<fmt:message key="label.pnp.student.signed.responses"/> <br/><br/>
				 	</div>
				</div>
			</div>
			</li>
			<li>
			<div id="alternateFormBlock">
				<div id="supportsProvidedByAlternateFormOther">
					<label class="divBlockFont" for="supportsProvidedByAlternateFormOther"> 
						<fmt:message key="label.pnp.supports.provided.by.alternate.form"/> 
					</label>
				</div>
				 <div class='divNestedBlock'>
				 	<div id="visualImpairment">
				 		<input type="checkbox" id="visualImpairment_cb" class="supportsProvidedByAlternateFormVisualImpairment" name="visualImpairment" title="Visual Impairment" >
				 		<fmt:message key="label.pnp.alternate.visual"/><br/><br/>
				 	</div>
				 	<div id="largePrintBooklet">
				 		<input type="checkbox" id="largePrintBooklet_cb" title="Alternate Form - Large print booklet" class="supportsProvidedByAlternateFormLargePrintBooklet" name="largePrintBooklet" >
				 		<fmt:message key="label.pnp.alternate.booklet"/><br/><br/>	
				 	</div>
				 	<div id="paperAndPencil">		
				 		<input type="checkbox" id="paperAndPencil_cb" title="Alternate Form - Paper and Pencil" class="supportsProvidedByAlternateFormPaperAndPencil" name="paperAndPencil" >
				 		<fmt:message key="label.pnp.alternate.paper.and.pencil"/><br/><br/>	
				 	</div>			
				 </div>
			 </div>
			</li>
			<li>
			<div id="additionalToolsBlock">
				<div id="supportsRequiringAdditionalToolsOther">
					<label class="divBlockFont" for="supportsRequiringAdditionalToolsOther"> <fmt:message key="label.pnp.supports.requiring.addtl.tools"/> </label>
				</div>
				<div class='divNestedBlock'>
					<div id="supportsTwoSwitch">
				 		<input type="checkbox" id="supportsRequiringAdditionalToolsSupportsTwoSwitch" 
				 			class="supportsRequiringAdditionalToolsSupportsTwoSwitch" name="supportsTwoSwitch" title="Two switch system" >
				 		<fmt:message key="label.pnp.two.switch.system"/><br/><br/>
				 	</div>
				 	<div id="supportsAdminIpad">
				 		<input type="checkbox" id="supportsRequiringAdditionalToolsSupportsAdminIpad" 
				 			class="supportsRequiringAdditionalToolsSupportsAdminIpad" title="Administration via iPad" name="supportsAdminIpad" >
				 		<fmt:message key="label.pnp.admin.via.ipad"/><br/><br/>	
				 	</div>
				 	<div id="supportsAdaptiveEquip">		
				 		<input type="checkbox" id="supportsRequiringAdditionalToolsSupportsAdaptiveEquip" 
				 			class="supportsRequiringAdditionalToolsSupportsAdaptiveEquip" title="Adaptive equipment" name="supportsAdaptiveEquip" >
				 		<fmt:message key="label.pnp.adaptive.equipment"/><br/><br/>	
				 	</div>
				 	<div id="supportsIndividualizedManipulatives">	
				 		<input type="checkbox" id="supportsRequiringAdditionalToolsSupportsIndividualizedManipulatives" 
				 			class="supportsRequiringAdditionalToolsSupportsIndividualizedManipulatives" title="Individualized manipulatives" name="supportsIndividualizedManipulatives" title="Individualized manipulatives" >
				 		<fmt:message key="label.pnp.individualized.manipulatives"/><br/><br/>	
				 	</div>	
				 	<div id="supportsCalculator">    
                         <input type="checkbox" id="supportsRequiringAdditionalToolsSupportsCalculator" 
                         	class="supportsRequiringAdditionalToolsSupportsCalculator" name="supportsCalculator" title="Calculator" >
                         <fmt:message key="label.pnp.calculator"/><br/><br/>    
                    </div> 
				 </div>
			 </div>	
			</li>
			<li>
			<div id="outsideSystemBlock">	
				<div id="supportsProvidedOutsideSystemOther">			 
				 <label class="divBlockFont" for="supportsProvidedOutsideSystemOther"> <fmt:message key="label.pnp.supports.provided.outside.system"/> </label>
				</div>
				<div class='divNestedBlock'>
					 <div id="supportsHumanReadAloud">
					 	<input type="checkbox" id="supportsProvidedOutsideSystemSupportsHumanReadAloud" 
					 		class="supportsProvidedOutsideSystemSupportsHumanReadAloud" name="supportsHumanReadAloud" title="Human read aloud" >
					 		<fmt:message key="label.pnp.human.readaloud"/><br/><br/>
					 </div>		
					 <div id="supportsSignInterpretation">
					 	<input type="checkbox" id="supportsProvidedOutsideSystemSupportsSignInterpretation" 
					 		class="supportsProvidedOutsideSystemSupportsSignInterpretation" name="supportsSignInterpretation" title="Sign interpretation" >
					 		<fmt:message key="label.pnp.sign.interpretation"/> <br/><br/>	
					 </div>
					 <div id="supportsLanguageTranslation">		
					 	<input type="checkbox" id="supportsProvidedOutsideSystemSupportsLanguageTranslation" 
					 		class="supportsProvidedOutsideSystemSupportsLanguageTranslation" name="supportsLanguageTranslation" title="Language translation" >
					 		<fmt:message key="label.pnp.language.translation"/> <br/><br/>	
					 </div>	
					 <div id="supportsTestAdminEnteredResponses">		
					 	<input type="checkbox" id="supportsProvidedOutsideSystemSupportsTestAdminEnteredResponses" 
					 		class="supportsProvidedOutsideSystemSupportsTestAdminEnteredResponses" name="supportsTestAdminEnteredResponses" title="Test admin enters responses for student" >
					 		<fmt:message key="label.pnp.test.admin.enters.responses"/> <br/><br/>	
					 </div>	
					 <div id="supportsPartnerAssistedScanning">		
					 	<input type="checkbox" id="supportsProvidedOutsideSystemSupportsPartnerAssistedScanning" 
					 		class="supportsProvidedOutsideSystemSupportsPartnerAssistedScanning" name="supportsPartnerAssistedScanning" title="Partner assisted scanning" >
					 		<fmt:message key="label.pnp.partner.assisted.scanning"/> <br/><br/>	
					 </div>	
					 <div id="supportsStudentProvidedAccommodations">		
					 	<input type="checkbox" id="supportsProvidedOutsideSystemSupportsStudentProvidedAccommodations" 
					 		class="supportsProvidedOutsideSystemSupportsStudentProvidedAccommodations" title="Student provided non-embedded accommodations as noted in IEP" name="supportsStudentProvidedAccommodations" >
					 	<fmt:message key="label.pnp.student.provided.accommodations"/> <br/><br/>	
					 </div>					 
				</div>
			</div>
			</li>
		</ul>	
	</div>

	<div style="display: block; width: 100%;" > 
	    <div class="messages" style="text-align:right;">
	 	    <button type="button" id="saveAudioEnvironmentSystemIndependent" class="btn_blue save" style="width: 90px;margin-left: 24px;">
			   <fmt:message key="button.save"/>
		    </button>
			<!-- Language & Braille tab errors -->
	        <span class="info_message ui-state-highlight successMessage hidden" id="successMessageLanguageBrailleSystemIndependent" style="float:left;">
	        <fmt:message key="label.pnp.successful"/></span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden errorDotPressure" id="errorDotPressureSystemIndependent">
	        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.dotPressure"/></span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleCells" id="errorBrailleCellsSystemIndependent">
	        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.brailleCells"/></span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleFileType" id="errorgenericSystemIndependent">Select at least one Braille File Type</span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden" id="errorgenericNumericSystemIndependent">Enter a Numeric Value</span>
		
			<!-- Audio & Environment tab errors -->
			<br /><span class="error_message ui-state-error selectAllLabels hidden showTimeMultiplierError" id="showTimeMultiplierErrorSystemIndependent">
			<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.timemultiplier"/></span>
			<br /><span class="error_message ui-state-error selectAllLabels hidden showScanSpeedError" id="showScanSpeedErrorSystemIndependent">
			<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.scanspeed"/></span>
			<br /><span class="error_message ui-state-error selectAllLabels hidden showAutomaticScanInitialDelayError" id="showAutomaticScanInitialDelayErrorSystemIndependent">
			<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.automaticscaninitialdelay"/></span>
		</div>
	</div>
</div>