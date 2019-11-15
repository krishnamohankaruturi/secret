<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorpicker/colorPicker.css" type="text/css" />

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

.divBlockContrast {
	width: 330px;
	float: left;
	z-index: 1000;
	padding: 30px;
}

.divBlockDE {
	width: 280px;
	float: left;
	padding: 31px;
}

.divBlock {
	width: 330px;
	padding: 30px 30px;
	float: left;
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
.messages{
margin-left:590px !important;
margin-top:53px  !important;
}
.topInfoLeft{
margin-top: -95px;
}

#displayEnhancementsContent ul li {
 	float: none;
    display: inline;
    width: 33%;
    margin-top: 5px;
    padding: 10px;
    background-color: #FFFFFF;
}
#displayEnhancementsContent ul{
		display: -webkit-box;
    	display: -ms-flexbox;
    	display: flex;
   	 	-ms-flex-wrap: wrap;
        flex-wrap: wrap;
}
</style>

<div>
	<div>
		<div class="topInfoLeft">
			<label style="font:20px Arial,Helvetica,sans-serif;" class="divBlockFont"></label>
			<div class="messages">
    	      <span class="error_message ui-state-error selectAllLabels hidden" id="selectAllLabelsAccessProfileDisplay"><fmt:message key=""/></span>
    	      <br /><span class="info_message ui-state-highlight successMessage hidden" id="successMessageDisplayEnhancementsAccessProfileDisplay" ><fmt:message key="label.pnp.successful"/></span><br>
            </div>
		</div>
		<div class="topInfoRight">
			<button type="button" id="saveAccessProfileDisplayEnhancements" class="btn_blue save" style="width: 90px; margin-left: 24px;float:right; margin-top:-87px" > <fmt:message key="button.save"/> </button>
		</div>
	</div>
	<div id="displayEnhancementsContent" style="font:20px; width: 100%;">
		<ul>
			<li class='divBlockDE'>
				<div id="magnificationAssignedSupport">
					<input type="checkbox" id="magnificationAssignedSupport_cb" class="magnificationAssignedSupport" /> 
					<label class="divBlockFont" for="magnificationAssignedSupport_cb" > <fmt:message key="label.pnp.magnification"/> </label>
				</div>

				 <div id="magnificationActivateByDefault" class='divNestedBlock'>
				 	<input type="checkbox" id="magnificationActivateByDefault_cb" class="magnificationActivateByDefault" title="Magnification Activate by Default"
				 		disabled="disabled" /> 
				 		<fmt:message key="label.pnp.activatebydefault"/>
				 </div>
				 
				 <div id="magnificationMagnification" class='divNestedBlock' style="margin-left:55px; margin-top:-10px;">
					 <select id="magnificationMagnification_sl" class="magnificationMagnification" title="Magnification" disabled="disabled">
					 	<option>2x </option>
					 	<option>3x </option>
					 	<option>4x </option>
					 	<option>5x </option>			 
					 </select>
				 </div>
			 </li>
			 <li class='divBlock'>
		 	 <div id= "colourOverlayAssignedSupport">
				 <input type="checkbox" id="colourOverlayAssignedSupport_cb" class="colourOverlayAssignedSupport" /> 
				 <label class="divBlockFont" for="colourOverlayAssignedSupport_cb" > <fmt:message key="label.pnp.overlaycolor"/> </label>
			 </div>
				 <div class='divNestedBlock' id="colourOverlayActivateByDefault">
				 	<input type="checkbox" class="colourOverlayActivateByDefault" disabled="disabled" title="Overlay Color Activate by Default " /> <fmt:message key="label.pnp.activatebydefault"/>
				 </div>
				 
				 <div class='divNestedBlock' id="colourOverlayColour">					 
					 <div class="controlset" style="margin-left:25px; margin-top:-15px"> 
					 	<input class= "color3" id="color3" type="text" name="color3" value="#fff" disabled="disabled"/>
					 </div>
					 <div  style="float: left; margin-top:-10px">
					 	<input type="text" class="ColourOverlaycolour" title="Overlay Color" size="7"  disabled="disabled" />
					 </div>
				</div>
			</li>
			<li class='divBlock'>
			<div id= "invertColourChoiceAssignedSupport">
				<input type="checkbox" class="invertColourChoiceAssignedSupport" title="Invert Color Choice"/> 
				<label class="divBlockFont" for="invertColourChoiceAssignedSupport" > <fmt:message key="label.pnp.invertcolorchoice"/> </label>
			 </div>
				<div class='divNestedBlock' id= "invertColourChoiceActivateByDefault">
					<input type="checkbox" class="invertColourChoiceActivateByDefault" disabled="disabled" title="Invert Color Choice Activate by Default"/> <fmt:message key="label.pnp.activatebydefault"/>
				</div>
			</li>	
			 <li class='divBlockDE'>
			 	<div id="maskingAssignedSupport">
				  <input type="checkbox" id="maskingAssignedSupport_cb" class="maskingAssignedSupport" title="Masking Assigned Support" /> 
				  <label class="divBlockFont"> 
				  <fmt:message key="label.pnp.masking"/> </label>
				</div>
				 <div class='divNestedBlock' id="maskingActivateByDefault">
				 	<input type="checkbox" id="maskingActivateByDefault_cb" class="maskingActivateByDefault" title="Masking Activate by Default"
				 	disabled="disabled" /> 
				 	<fmt:message key="label.pnp.activatebydefault"/>
				 </div>
				 
				 <div class='divNestedBlock' id="maskingMaskingType">
					<input type="radio" id="maskingMaskingType_answermask" class="maskingMaskingType" title="Answer Masking" 
						name="maskingMaskingType" value="answermask" disabled="disabled" /> 
						<fmt:message key="label.pnp.answermasking"/> <br /><br />
					<input type="radio" id="maskingMaskingType_custommask"  class="maskingMaskingType" title="Custom Masking" 
						name="maskingMaskingType" value="custommask" disabled="disabled" /> 
						<fmt:message key="label.pnp.custommasking"/>
				 </div>
			 </li>
			 <li class='divBlockContrast'>
			 <div id= "backgroundColourAssignedSupport">
			 	<input type="checkbox" class="backgroundColourAssignedSupport" title="Contrast Color"/> 
			 	<label class="divBlockFont" for="backgroundColourAssignedSupport" > <fmt:message key="label.pnp.contrastcolor"/> </label>
			 </div>
			 	<input type="checkbox" class="foregroundColourAssignedSupport hidden" />
			
			 	<div class='divNestedBlock' id="backgroundColourActivateByDefault">
			 		<input type="checkbox" class="backgroundColourActivateByDefault" title="Background Color Activate by Default" disabled="disabled" /> <fmt:message key="label.pnp.activatebydefault"/>
			 		<input type="checkbox" class="foregroundColourActivateByDefault hidden" title="Foreground Color Activate by Default" />
			 	</div>
			 
			 	<div id="contrastColor" style="margin-left:50px;">
					<div class="contrastColorDiv" style="float:left; background-color:#000000; color:#999999;border-width: 4px;">ABC</div>
					<div class="contrastColorDiv" style="float:left; background-color:#000000; color:#FEFE22;border-width: 4px;">ABC</div>
					<br />
					<div style="clear:both"/>
					<div class="contrastColorDiv" style="float:left; background-color:#ffffff; color:#3b9e24;border-width: 4px;">ABC</div>
					<div class="contrastColorDiv" style="float:left; background-color:#ffffff; color:#c62424;border-width: 4px;">ABC</div>
					<br />
			 	</div>
			 	
			 	<div class='divNestedBlock' style="margin-left:35px;">
					<fmt:message key="label.pnp.backgroundcolorhex"/> 
					<input type="text" class="backgroundColourColour" value="" title="Background Color Hex" size = "8" disabled="disabled" /> <br/><br/>
					<fmt:message key="label.pnp.foregroundcolorhex"/> 
					<input type="text" class="foregroundColourColour" value="" title="Background Color Hex" size = "8"  disabled="disabled" />
			 	</div>
			 	
			</li>
		</ul>

	</div>
   
	<div style="display: block; float: left; width: 100%;">
	    <div class="messages">
	 	    <button type="button" id="saveAudioEnvironmentDisplayEnhancement" class="btn_blue save" style="width: 90px;display: inline-block; margin-left: 24px;float:right;">
			   <fmt:message key="button.save"/>
		    </button>
			<!-- Language & Braille tab errors -->
	        <span class="info_message ui-state-highlight successMessage hidden" id="successMessageLanguageBrailleDisplayEnhan" >
	        <fmt:message key="label.pnp.successful"/></span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden errorDotPressure" id="errorDotPressureDisplayEnhan">
	        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.dotPressure"/></span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleCells" id="errorBrailleCellsDisplayEnhan">
	        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.brailleCells"/></span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleFileType" id="errorgenericDisplayEnhan">Select at least one Braille File Type</span>
	        <br /><span class="error_message ui-state-error selectAllLabels hidden" id="errorgenericNumericDisplayEnhan">Enter a Numeric Value</span>
		
			<!-- Audio & Environment tab errors -->
			<br /><span class="error_message ui-state-error selectAllLabels hidden showTimeMultiplierError" id="showTimeMultiplierErrorDisplayEnhan">
			<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.timemultiplier"/></span>
			<br /><span class="error_message ui-state-error selectAllLabels hidden showScanSpeedError" id="showScanSpeedErrorDisplayEnhan">
			<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.scanspeed"/></span>
			<br /><span class="error_message ui-state-error selectAllLabels hidden showAutomaticScanInitialDelayError" id="showAutomaticScanInitialDelayErrorDisplayEnhan">
			<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.automaticscaninitialdelay"/></span>
		</div>
	</div>
</div>