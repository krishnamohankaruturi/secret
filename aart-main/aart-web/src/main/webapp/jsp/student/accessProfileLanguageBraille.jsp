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
	#divLanguageBraille .divBlock {
		padding: 10px 10px;
		float: left;
	}
	
	#divLanguageBraille .divNestedBlock {		 
		padding: 10px 10px;
		margin-left: 15%;
  	 	position: relative;
		float: left;
	}
	
	#divLanguageBrailleContent ul li {
  		float: none;
    	display: inline;
    	width: 33%;
    	margin-top: 5px;
    	padding: 10px;
    	background-color: #FFFFFF;
}
	#divLanguageBrailleContent ul{
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
<div id="divLanguageBraille">
	<div>
		 <div class="topInfoLeft">
			<label style="font:20px Arial,Helvetica,sans-serif;" class="divBlockFont"></label>
			<div class="messages">
    	       <br/><span class="info_message ui-state-highlight successMessage hidden" id="successMessageLanguageBrailleAccessProf" >
    	       	<fmt:message key="label.pnp.successful"/></span><br/>
            </div>
		</div>
		<div class="topInfoRight">
			<button type="button" id="saveAccessProfileLanguageBraile" class="btn_blue save" style="width: 90px; margin-left: 24px;float:right; margin-top:-87px" >
				<fmt:message key="button.save"/> </button>
		</div>
	</div>
	<div id="divLanguageBrailleContent" style="width: 100%;">
		<ul>
			<li class="divBlock"> 
				<div id="itemTranslationDisplayAssignedSupport" >
					<input type="checkbox" id="itemTranslationDisplayAssignedSupport_cb" class="itemTranslationDisplayAssignedSupport" />
					<label class="divBlockFont" for="itemTranslationDisplayAssignedSupport_cb">Item Translation Display</label>
				</div>
				<div class="divNestedBlock" id="itemTranslationDisplayActivateBydefault">
					<input type="checkbox" id="itemTranslationDisplayActivateBydefault_cb" class="itemTranslationDisplayActivateBydefault" disabled="disabled" title="Item Translation Display"/>
					Activate By Default
				</div>
				<div class="divNestedBlock" style="margin-left:55px; margin-top:-10px;" id="itemTranslationDisplayLanguage">
				<select id="itemTranslationDisplaylanguage_sl" class="itemTranslationDisplaylanguage" disabled="disabled" title="Item Translation Display">
					<option value="spa"> Default(Spanish-(SPA)) </option>
					<option value="eng"> English(ENG) </option>
					<option value="ger"> German(GER) </option>
					<option value="vie"> Vietnamese(VIE) </option>
				</select>
				</div>
			</li>
			<li class="divBlock">
				<div id="signingAssignedSupport">
					<input type="checkbox" id="signingAssignedSupport_cb" class="signingAssignedSupport" /><label class="divBlockFont" for="signingAssignedSupport_cb">Signing Type</label>
				</div>
				<div class="divNestedBlock" id="signingActivateByDefault">
					<input type="checkbox" id="signingActivateBydefault_cb" class="signingActivateBydefault" disabled="disabled" title="Signing Type"/>
					Activate By Default
				</div>
				<div class="divNestedBlock" style="margin-left:55px; margin-top:-10px;" id="signingSigningType">
					<select id="signingSigningType_sl" class="signingSigningType" disabled="disabled" title="Signing Type">
						<option value="asl">Default(American Sign Language)</option>
						<option value="SignedEnglish"> Signed English</option>
					</select>
				</div>
			</li> 
			<li class="divBlock">
			<div>
				<div id="brailleAssignedSupport">
					<input id="brailleAssignedSupport_cb" type="checkbox" class="brailleAssignedSupport" /> <label class="divBlockFont" for="brailleAssignedSupport_cb">Braille</label>
				</div>
				<div class="divNestedBlock" id="brailleActivateByDefault">
					<input id="brailleActivateBydefault_cb" type="checkbox" title="Activate By Default" class="brailleActivateBydefault" disabled="disabled" />
					Activate By Default
				</div>
				<div class="divNestedBlock" id="brailleFileType">
					<label class="labelGreen" style="margin-left:-15px;">Braille File Type</label><br/>
					<div class="divNestedBlock" style="margin-left:-30px;">
						<input type="radio" id="brailleEbaeFileType_EBAE" class="brailleEbaeFileType" name="brailleFileType" disabled="disabled" title="EBAE"/>
							EBAE<br/><br/>
						<input type="radio" id="brailleEbaeFileType_UEB" class="brailleUebFileType" name="brailleFileType" disabled="disabled" title="UEB"/>
							UEB<br/>
					</div>
				</div>
			</div>
			<div id="brailleUsage" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-5px;">Braille Usage</label><br/>
				<div class="divNestedBlock" style="margin-left:-15px;">
					<select id="brailleUsage_sl" class="brailleUsage" disabled="disabled" title="Braille Usage">
						<option value="preferred">Preferred</option>
						<option value="required">Required</option>
						<option value="prohibited">Prohibited</option>
						<option value="optionallyUse">Optionally Use</option>
					</select>
				</div>
			</div>
			<div id="brailleBrailleGrade" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-15px;">Braille Grade Type</label><br/>
				<div class="divNestedBlock" style="margin-left:-30px;">
					<input type="radio" id="brailleBrailleGrade_contracted" class="brailleBrailleGrade" name="brailleBrailleGrade" 
						value="contracted" disabled="disabled" title="Contracted">Contracted<br/><br/>
					<input type="radio" id="brailleBrailleGrade_uncontracted" class="brailleBrailleGrade" name="brailleBrailleGrade" 
						value="uncontracted" disabled="disabled" title="Uncontracted">Uncontracted <br/>
				</div>
			</div>
			<div id="brailleBrailleMark" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-15px;">Braille Mark</label><br/>
				<div class="divNestedBlock" style="margin-left:-30px;">
					<div style="-moz-column-count:2;">
						<input type="checkbox" id="MarkType_highlight" class="MarkType" 
							name="MarkType" value="highlight" disabled="disabled" title="Highlight">Highlight<br/><br/>
						<input type="checkbox" id="MarkType_bold" class="MarkType" 
							name="MarkType" value="bold" disabled="disabled" title="Bold">Bold <br/><br/>
						<input type="checkbox" id="MarkType_underline" class="MarkType" 
							name="MarkType" value="underline" disabled="disabled" title="Underline">Underline <br/><br/>
						<input type="checkbox" id="MarkType_italic" class="MarkType" 
							name="MarkType" value="italic" disabled="disabled" title="Italic">Italic <br/><br/>
						<input type="checkbox" id="MarkType_strikeout" class="MarkType" 
							name="MarkType" value="strikeout" disabled="disabled" title="Strikeout">Strikeout <br/><br/>
						<input type="checkbox" id="MarkType_color" class="MarkType" 
							name="MarkType" value="color" disabled="disabled" title="Color">Color <br/>
						<input type="text" id="brailleBrailleMark_hidden" class="brailleBrailleMark hidden" 
							name="brailleBrailleMark"/>
					</div>
				</div>
			</div>
	
			<div id="brailleBrailleStatusCell" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-15px;">Braille Status Cell Type</label><br/>
				<div class="divNestedBlock" style="margin-left:-30px;">
					<input type="radio" id="brailleBrailleStatusCell_off" class="brailleBrailleStatusCell" 
						name="brailleBrailleStatusCell" value="off" disabled="disabled" title="Off">Off
					<input type="radio" id="brailleBrailleStatusCell_left" class="brailleBrailleStatusCell" 
						name="brailleBrailleStatusCell" value="left" disabled="disabled" title="Left">Left 
					<input type="radio" id="brailleBrailleStatusCell_right" class="brailleBrailleStatusCell" 
						name="brailleBrailleStatusCell" value="right" disabled="disabled" title="Right">Right 
				</div>
			</div>
			<div id="brailleBrailleDotPressure" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-15px;">Braille Dot Pressure</label>
				<input type="text" id="brailleBrailleDotPressure_text" class="brailleBrailleDotPressure" 
					name="brailleBrailleDotPressure" size="3" disabled="disabled" title="Braille Dot Pressure">
			</div>
			<br/>
			<div id="brailleNumberOfBrailleCells" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-15px;">Number Of Braille Cells</label>
				<input type="text" id="brailleNumberOfBrailleCells_text" class="brailleNumberOfBrailleCells" 
					name="brailleNumberOfBrailleCells" size="3" disabled="disabled" title="Number Of Braille Cells">
			</div>
			<br/>
			<div id="brailleNumberOfBrailleDots" class="divNestedBlock">
				<label class="labelGreen" style="margin-left:-15px;">Number Of Braille Dots</label>
				<select class="brailleNumberOfBrailleDots" disabled="disabled" title="Number Of Braille Dots">
					<option value="6">Default(6)</option>
					<option value="8">8</option>
				</select>
			</div>
			</li>
			<li class="divBlock">
				<div id="keywordTranslationDisplayAssignedSupport">
					<input type="checkbox" id="keywordTranslationDisplayAssignedSupport_cb" class="keywordTranslationDisplayAssignedSupport" />
					<label class="divBlockFont" for="keywordTranslationDisplayAssignedSupport_cb">Keyword Translation Display</label>
				</div>
				<div class="divNestedBlock" id="keywordTranslationDisplayActivateByDefault">
					<input type="checkbox" id="keywordTranslationDisplayActivateBydefault" class="keywordTranslationDisplayActivateBydefault" disabled="disabled" title="Keyword Translation Display"/>
					Activate By Default
				</div>
				<div class="divNestedBlock" style="margin-left:55px; margin-top:-10px;" id="keywordTranslationDisplayLanguage">
					<select id="keywordTranslationDisplayLanguage_sl" class="keywordTranslationDisplayLanguage" disabled="disabled" title="Keyword Translation Display">
						<option value="spa"> Default(Spanish-(SPA)) </option>
						<option value="eng"> English(ENG) </option>
						<option value="ger"> German(GER) </option>
						<option value="vie"> Vietnamese(VIE) </option>
					</select>
				</div>
			</li>
			<li class="divBlock">
				<div id="tactileAssignedSupport">
					<input type="checkbox" id="tactileAssignedSupport_cb" class="tactileAssignedSupport" />
					<label class="divBlockFont" for="tactileAssignedSupport_cb">Tactile</label>
				</div>
				<div class="divNestedBlock" id="tactileActivateByDefault">
					<input type="checkbox" id="tactileActivateBydefault_cb" class="tactileActivateBydefault" disabled="disabled" title="Tactile"/>
					Activate By Default
				</div>
				<div class="divNestedBlock" style="margin-left:55px; margin-top:-10px;" id="tactileTactileFile">
					<select id="tactileTactileFile_sl" class="tactileTactileFile" disabled="disabled" title="Tactile">
						<option value="audioFile"> Audio File </option>
						<option value="audioText"> Audio Text </option>
						<option value="brailleText"> Braille Text </option>
					</select>
				</div>
			</li>
		</ul>
	</div>
	<div class="messages">
 	    <button type="button" id="saveAudioEnvironmentLanguageBraile" class="btn_blue save" style="width: 90px;display: inline-block; margin-left: 24px;float:right;"> 
		   <fmt:message key="button.save"/> 
	    </button>
		<!-- Language & Braille tab errors -->
        <span class="info_message ui-state-highlight successMessage hidden" id="successMessageLanguageBrailleAccessProfile" >
        <fmt:message key="label.pnp.successful"/></span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden errorDotPressure" id="errorDotPressureLanguageBrailleAccessProfile">
        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.dotPressure"/></span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleCells" id="errorBrailleCellsLanguageBrailleAccessProfile">
        <fmt:message key="label.pnp.language_braille"/> Tab - <fmt:message key="error.pnp.brailleCells"/></span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden errorBrailleFileType" id="errorgenericAccessProfile">Select at least one Braille File Type</span>
        <br /><span class="error_message ui-state-error selectAllLabels hidden" id="errorgenericNumericAccessProfile">Enter a Numeric Value</span>
	
		<!-- Audio & Environment tab errors -->
		<br /><span class="error_message ui-state-error selectAllLabels hidden showTimeMultiplierError" id="showTimeMultiplierErrorAccessProfile">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.timemultiplier"/></span>
		<br /><span class="error_message ui-state-error selectAllLabels hidden showScanSpeedError" id="showScanSpeedErrorAccessProfile">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.scanspeed"/></span>
		<br /><span class="error_message ui-state-error selectAllLabels hidden showAutomaticScanInitialDelayError" id="showAutomaticScanInitialDelayErrorAccessProfile">
		<fmt:message key="label.pnp.audio_environment"/> Tab - <fmt:message key="error.pnp.automaticscaninitialdelay"/></span>
	</div>
</div>