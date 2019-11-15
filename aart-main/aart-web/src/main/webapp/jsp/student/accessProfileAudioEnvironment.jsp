<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>


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
	margin-right: 1.99203%;
	width: 30.50598%;
	padding: 0 10px;
	margin-bottom: 25px;
}

.divCenterCol {
	display: block;
	float: left;
	margin-right: 1.99203%;
	width: 30.50199%;
	margin-right: 0;
	padding: 0 10px;
	margin-bottom: 25px;
}

.divRightCol {
	display: block;
	float: left;
	margin-right: 1.99203%;
	width: 30.20199%;
	margin-right: 0;
	padding: 0 10px;
	margin-bottom: 25px;
}

.divBlockSpoken {
	width: 330px;
	float: left;
	z-index: 1000;
	padding: 30px;
}

.divBlockAE {
	width: 280px;
	float: left;
	padding: 31px;
}

.divBlock {
	width: 330px;
	float: left;
}

.divNestedBlock {
	width: 290px;
	padding: 10px 10px;
	margin-left: 10%;
	position: relative;
	float: left;
	color: #646567;
	font-weight: 300;
}

.divNestedBlock label {
	color: #94B54D;
	font: 17px Arial, Helvetica, sans-serif;
}

#audioEnvironmentContent ul li {
 	float: none;
    display: inline;
    width: 33%;
    margin-top: 5px;
    padding: 10px;
    background-color: #FFFFFF;
}
#audioEnvironmentContent ul{
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

<div id="audioEnvironmentDivContent">

	<div>
		 <div class="topInfoLeft">
			<label style="font:20px Arial,Helvetica,sans-serif;" class="divBlockFont"></label>
			<div class="messages">
    	       <br /><span class="info_message ui-state-highlight successMessage hidden" id="successMessageAudioEnvironment">
    	       	<fmt:message key="label.pnp.successful"/></span><br>
            </div>
		</div>
		<div class="topInfoRight">
			<button type="button" id="saveAudioEnvironmentAccessProfilePNP" class="btn_blue save" style="width: 90px; margin-left: 24px;float:right; margin-top:-87px" > 
				<fmt:message key="button.save"/> </button>
		</div>
	</div>	
	<div id="audioEnvironmentContent" style="font:20px; width: 100%;"> 
		<ul>
			<li class='divBlockAE'>
				<div id="auditoryBackgroundAssignedSupport">
					<input id="auditoryBackgroundAssignedSupport_cb" type="checkbox" class="auditoryBackgroundAssignedSupport" /> 
					<label class="divBlockFont" for="auditoryBackgroundAssignedSupport_cb"> <fmt:message key="label.pnp.auditorybackground"/> </label>
				</div>
				 <div class='divNestedBlock' id="auditoryBackgroundActivateByDefault">
				 	<input type="checkbox" id="auditoryBackgroundActivateByDefault_cb" class="auditoryBackgroundActivateByDefault" disabled="disabled" title="Auditory Background Activate by Default" /> 
				 		<fmt:message key="label.pnp.activatebydefault"/>
				 </div>
			 </li>
			 <li class='divBlockSpoken'>
		 	 <div id="spokenAssignedSupport">
				 <input type="checkbox" id="spokenAssignedSupport_cb" class="spokenAssignedSupport" /> 
				 <label class="divBlockFont" for="spokenAssignedSupport_cb" > <fmt:message key="label.pnp.spokenaudio"/> </label>
			 </div>
				 <div class='divNestedBlock' id="spokenActivateByDefault">
				 	<input type="checkbox" id="spokenActivateByDefault_cb" class="spokenActivateByDefault" title="Spoken Audio Activate by Default"
				 		disabled="disabled" /> 
				 		<fmt:message key="label.pnp.activatebydefault"/>
				 </div>
				 
				 <div class='divNestedBlock' style="margin-left:-10px;">	
				 	<label> <fmt:message key="label.pnp.voicesource"/> </label><br><br>
				 	<div style="margin-left:-5px;">
				 		<div id="human">
						<input type="radio" id="spokenSpokenSourcePreference_humanSpokenSource" class="spokenSpokenSourcePreference spokenAudio humanSpokenSource" title="View source Human"
							name="voiceSource" value="human" disabled="disabled" />
							<fmt:message key="label.pnp.human"/> <br /><br />
						</div>
						<input type="radio" id="spokenSpokenSourcePreference_syntheticSpokenSource" class="spokenSpokenSourcePreference spokenAudio syntheticSpokenSource" title="View source Synthetic"
							name="voiceSource" value="synthetic" disabled="disabled" /> 
							<fmt:message key="label.pnp.synthetic"/>  <br />
					</div> <br />
					<div style="margin-left:-5px;" id="spokenReadAtStartPreference">	
						<label> <fmt:message key="label.pnp.readatstart"/> </label><br><br>			 
						<input type="radio" id="spokenReadAtStartPreference_true" class="spokenReadAtStartPreference spokenAudio" title="Read at Start True"
							class="spokenReadAtStartPreference spokenAudio" name="readAtStart" value="true" disabled="disabled" /> 
							<fmt:message key="label.pnp.true"/> <br /><br />
						<input type="radio" id="spokenReadAtStartPreference_false"  class="spokenReadAtStartPreference spokenAudio" title="Read at Start False"
							name="readAtStart" value="false" disabled="disabled" /> 
							<fmt:message key="label.pnp.false"/> <br />
					</div> <br />
					<label> <fmt:message key="label.pnp.spokenpreference"/> </label><br><br>
					<div style="margin-left:-5px;">
						<div id="textonly">
						<input type="radio" id="spokenUserSpokenPreference_textonly" title="TextOnly" class="spokenUserSpokenPreference spokenAudio" 
							 name="spokenPreference" value="textonly" disabled="disabled" /> 
							<fmt:message key="label.pnp.textonly"/> <br /><br />
						</div>
						<div id="textandgraphics">
						<input type="radio" id="spokenUserSpokenPreference_textandgraphics" class="spokenUserSpokenPreference spokenAudio" 
							title="Text & Graphics" name="spokenPreference" value="textandgraphics" disabled="disabled" /> 
							<fmt:message key="label.pnp.textandgraphics"/> <br /><br />
						</div>
						<div id="graphicsonly">
						<input type="radio" id="spokenUserSpokenPreference_graphicsonly" class="spokenUserSpokenPreference spokenAudio" 
							title="GraphicsOnly" name="spokenPreference" value="graphicsonly" disabled="disabled" /> 
							<fmt:message key="label.pnp.graphicsonly"/>  <br /><br />
						</div>
						<div id="nonvisual">
						<input type="radio" id="spokenUserSpokenPreference_nonvisual" class="spokenUserSpokenPreference spokenAudio" 
							title="NonVisual" name="spokenPreference" value="nonvisual" disabled="disabled" /> 
							<fmt:message key="label.pnp.nonvisual"/> <br/>
						</div>
					</div> <br />
					<div id="spokenDirectionsOnly">
						<label> <fmt:message key="label.pnp.audiofordirectionsonly"/>  </label><br><br>
						<div style="margin-left:-5px;">
							<input type="radio" id="spokenDirectionsOnly_true" class="spokenDirectionsOnly spokenAudio" 
								title="True" name="audioDirections" value="true" disabled="disabled" /> 
								<fmt:message key="label.pnp.true"/> <br /><br />
							<input type="radio" id="spokenDirectionsOnly_false" class="spokenDirectionsOnly spokenAudio" 
								title="False" name="audioDirections" value="false" disabled="disabled" /> 
								<fmt:message key="label.pnp.false"/>
						</div>
					</div> <br />
					<div id="spokenSubject">
						<label> <fmt:message key="label.pnp.spokenpreferencesubjects"/>  </label><br><br>
						<div style="margin-left:-5px;">
							<c:choose>
								<c:when test="${user.organization.organizationName.equalsIgnoreCase('cpass school') || user.organization.organizationName.equalsIgnoreCase('armm school')}" >
									<input type="radio" id="spokenPreferenceSubject_math" class="spokenPreferenceSubject spokenAudio" 
										title="Mathematics only" name="preferenceSubject" value="math" disabled="disabled" />
										<fmt:message key="label.pnp.mathonly"/> <br /><br />
								    <input type="radio" id="spokenPreferenceSubject_math_and_ela" class="spokenPreferenceSubject spokenAudio" 
								    	title="Mathematics and English Language Arts" name="preferenceSubject" value="math and ela" disabled="disabled" />
								    	<fmt:message key="label.pnp.mathenglish"/>
								</c:when>
								<c:otherwise>
									<input type="radio" id="spokenPreferenceSubject_math_and_science" class="spokenPreferenceSubject spokenAudio" 
										title="Mathematics and Science only" name="preferenceSubject" value="math_and_science" disabled="disabled" /> 
										<fmt:message key="label.pnp.mathandscienceonly"/> <br /><br />
									<input type="radio" id="spokenPreferenceSubject_math_science_and_ELA" class="spokenPreferenceSubject spokenAudio" 
										title="Mathematics, Science and English Language Arts" name="preferenceSubject" value="math_science_and_ELA" disabled="disabled" /> 
										<fmt:message key="label.pnp.mathandela"/>
								</c:otherwise>
							</c:choose>
							</div>
						</div>
					</div>
				</li>
				<li class='divBlock'>
				<div id="onscreenKeyboardAssignedSupport">
					<input type="checkbox" id="onscreenKeyboardAssignedSupport_cb" class="onscreenKeyboardAssignedSupport" /> 
					<label class="divBlockFont" for="onscreenKeyboardAssignedSupport_cb" > <fmt:message key="label.pnp.switches"/> </label> <br />
				</div>
				<div class='divNestedBlock' id="onscreenKeyboardActivateByDefault">
					<input type="checkbox" id="onscreenKeyboardActivateByDefault_cb"  class="onscreenKeyboardActivateByDefault" title="Single Switches Activate by Default"
						disabled="disabled" /> 
						<fmt:message key="label.pnp.activatebydefault"/>
				</div>
			 	
			 	<div style="margin-left:-10px;" class='divNestedBlock'>
			 		<div id="onscreenKeyboardScanSpeedDiv">
				 	<label> <fmt:message key="label.pnp.scanspeed"/> </label> 
				 		<input type="text" id="onscreenKeyboardScanSpeed" class="onscreenKeyboardScanSpeed breaks" size="6" disabled="disabled" title="Scan Speed" /> <br /> <br />
					</div>
					<div id="onscreenKeyboardAutomaticScanInitialDelay">
				 	<label> <fmt:message key="label.pnp.automaticscaninitialdelay"/> </label> <br />
				 		<div style="margin-left:-15px;" class='divNestedBlock'>
					 		<input type="radio" id="onscreenKeyboardAutomaticScanInitialDelaySelection_valueInSeconds" class="onscreenKeyboardAutomaticScanInitialDelaySelection breaks"  title="Value in Seconds"
					 			name="timeAutomaticScan" value="valueInSeconds" disabled="disabled" /> 
					 			<fmt:message key="label.pnp.valueinseconds"/> 
					 			<input type="text" class="onscreenKeyboardAutomaticScanInitialDelay breaks" disabled="disabled" size="4" title="Initial Display Value in Seconds" /> <br /><br/>
							<input type="radio" id="onscreenKeyboardAutomaticScanInitialDelaySelection_manual" class="onscreenKeyboardAutomaticScanInitialDelaySelection breaks"  title="Manual Override"
								name="timeAutomaticScan" value="manual" disabled="disabled" /> 
								<fmt:message key="label.pnp.manualoverride"/> <br />
						</div> <br />
					</div>
					<div id="onscreenKeyboardAutomaticScanRepeat">
				 	<label> <fmt:message key="label.pnp.automaticscanrepeatfrequency"/> </label> <br />
					<div style="margin-left:-15px;" class='divNestedBlock' >
					 	<input type="radio" id="automaticScanRepeat_1" class="onscreenKeyboardAutomaticScanRepeat breaks" title="Automatic Scan Repeat Frequency 1"
					 		name="automaticScanRepeat" value="1" disabled="disabled" /> 
					 			<fmt:message key="label.pnp.1"/>
						<input type="radio" id="automaticScanRepeat_4" class="onscreenKeyboardAutomaticScanRepeat breaks" title="Automatic Scan Repeat Frequency 4"
							name="automaticScanRepeat" value="4" disabled="disabled" /> 
							<fmt:message key="label.pnp.4"/> <br /><br />
						<input type="radio" id="automaticScanRepeat_2" class="onscreenKeyboardAutomaticScanRepeat breaks" title="Automatic Scan Repeat Frequency 2"
							name="automaticScanRepeat" value="2" disabled="disabled" /> 
							<fmt:message key="label.pnp.2"/>
						<input type="radio" id="automaticScanRepeat_5" class="onscreenKeyboardAutomaticScanRepeat breaks" title="Automatic Scan Repeat Frequency 5"
							name="automaticScanRepeat" value="5" disabled="disabled" /> 
							<fmt:message key="label.pnp.5"/> <br/><br />
						<input type="radio" id="automaticScanRepeat_3" class="onscreenKeyboardAutomaticScanRepeat breaks" title="Automatic Scan Repeat Frequency 3"
							name="automaticScanRepeat" value="3" disabled="disabled" /> 
							<fmt:message key="label.pnp.3"/>
						<input type="radio" id="automaticScanRepeat_infinity" class="onscreenKeyboardAutomaticScanRepeat breaks" title="Automatic Scan Repeat Frequency Infinity"
							name="automaticScanRepeat" value="infinity" disabled="disabled" /> 
							<fmt:message key="label.pnp.infinity"/> <br />
						</div>
					</div>	
					</div>
				</li>	
				<li class='divBlockAE' id="breaksAssignedSupport">
				  <input id="breaksAssignedSupport_cb" type="checkbox" class="breaksAssignedSupport" /> 
				  <label class="divBlockFont" for="breaksAssignedSupport_cb" > <fmt:message key="label.pnp.breaks"/> </label>
			 	</li>
			 	<li class='divBlockAE'>
			 	<div id="additionalTestingTimeAssignedSupport">
				 <input type="checkbox" id="additionalTestingTimeAssignedSupport_cb" class="additionalTestingTimeAssignedSupport" /> 
				 <label class="divBlockFont" for="additionalTestingTimeAssignedSupport_cb"> <fmt:message key="label.pnp.additionaltestingtime"/> </label>
				</div>
				 <div class='divNestedBlock' id="additionalTestingTimeActivateByDefault">
				 	<input type="checkbox" id="additionalTestingTimeActivateByDefault_cb" class="additionalTestingTimeActivateByDefault" title="Additional Testing Time Activate by Default"
				 		disabled="disabled" /> 
				 		<fmt:message key="label.pnp.activatebydefault"/>
				 </div>
				<div class='divNestedBlock' id="additionalTestingTimeTimeMultiplierSelection">
					<input type="radio" id="additionalTestingTimeTimeMultiplierSelection_unlimited" class="additionalTestingTimeTimeMultiplierSelection" title="Additional Testing Time Multiplier Unlimited"
						name="timeAssignedSupport" value="unlimited" disabled="disabled" /> 
						<fmt:message key="label.pnp.unlimited"/>  <br /><br />
					<input type="radio" id="additionalTestingTimeTimeMultiplierSelection_specifytimemultiplier" class="additionalTestingTimeTimeMultiplierSelection" title="Additional Specify Time Multiplier"
						name="timeAssignedSupport" id="specifytimemultiplier" value="specifytimemultiplier" disabled="disabled" /> 
						<fmt:message key="label.pnp.specifytimemultiplier"/>
					<input type="text" id="additionalTestingTimeTimeMultiplierSelection_text" class="additionalTestingTimeTimeMultiplier" title="Additional Testing Time Multiplier"
						disabled="disabled" size="8"/>
				 </div>
			 	 </li>
		</ul>
		
	</div>

 	<div class="messages">
 	    <button type="button" id="saveAudioEnvironmentAccessProfile" class="btn_blue save" style="width: 90px;display: inline-block; margin-left: 24px;float:right;"> 
		   <fmt:message key="button.save"/> 
	    </button>
		<!-- Language & Braille tab errors -->
        <span class="info_message ui-state-highlight successMessage hidden" id="successMessageLanguageBrailleAudioEnv" >
        <fmt:message key="label.pnp.successful"/></span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden errorDotPressure" id="errorDotPressureAudioEnv">
        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.dotPressure"/></span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleCells" id="errorBrailleCellsAudioEnv">
        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.brailleCells"/></span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleFileType" id="errorgenericAudioEnv">Select at least one Braille File Type</span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden" id="errorgenericNumericAudioEnv">Enter a Numeric Value</span>
	
		<!-- Audio & Environment tab errors -->
		<br /><span class="error_message ui-state-error selectAllLabels hidden showTimeMultiplierError" id="showTimeMultiplierErrorAudioEnv">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.timemultiplier"/></span>
		<br /><span class="error_message ui-state-error selectAllLabels hidden showScanSpeedError" id="showScanSpeedErrorAudioEnv">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.scanspeed"/></span>
		<br /><span class="error_message ui-state-error selectAllLabels hidden showAutomaticScanInitialDelayError" id="showAutomaticScanInitialDelayErrorAudioEnv">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.automaticscaninitialdelay"/></span>
		<br /><span class="error_message ui-state-error selectAllLabels hidden showSpokenSubjectError" id="showSpokenSubjectError">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.spokensubject"/></span>
	</div>
</div> 