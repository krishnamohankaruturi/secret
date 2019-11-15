<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
	#createEmailTemplateContent input.text {
		width: 96%;
	}
	
	#createEmailTemplateContent .form-fields {
		vertical-align:top;
		width:320px;
	}
	.cke_button__activationlinkbtn_icon{
		width:200px;
	}
	#createEmailTemplateContent .field-label {
		text-transform: none;
		font-weight:bold;
	}
	#createEmailTemplateContent .message-content{
	    width: 700px;
	    font-family: "Helvetica Neue";
	    margin-top: 3%;
	    padding-left: 115px;
    }
    #createEmailTemplateContent .message_content{
	    position: relative;
	    width: 440px;
	    margin:10px auto;
	    padding: 10px 15px;
	    background: #ffffff;
	    border-radius: 5px;
	    color:black !important;
	    box-shadow: 0 1px 0 rgba(0,0,0,0.2);
	    outline: none;
	    transition: all 0.3s ease-out;
	}

    [for=emailTemplateLists]{   
       margin-left: 33%;
       margin-top: -0.5%;
	}
	
	[for=emailBody]{
	    margin-top: 1%;
	    margin-left: 0px;
	}
	label{
		margin-left:15px;
	}
	
	.rightNoButton{
		float:left;
		margin-left: 100px;
	}
		
	[for=statesSelect]{	
        margin-left: 0px;
    }
    
     .ui-widget-header {
            color: #FFFFFF;
            text-align: left;
         }
    
</style>

<div id="createEmailTemplateContent"  class="_bcg config" >
<label class="hidden error" id="messageActivationEmailTemplate"></label>
	<br/>
	<form:form id="createEmailActivationForm" commandName="ActivationEmailTemplate" class="form">
	    <input type="hidden" id="statesAlreadyHaving" name="statesAlreadyHaving" value="false" />
	    <div id="hiddenparameters">
	    </div>		
		<form:input type="hidden" path="id"/>
		<form:input type="hidden" path="isDefault" />
				
			<c:if test="${ActivationEmailTemplate.isDefault eq 'false' and ActivationEmailTemplate.id==null}">		
			
				<div class="form-fields" style="width: 95%;border-bottom:1px solid #ccc;" >	
					  
					  <div style="width: 35%;float:left;" >
						   	<label class="field-label" >Begin with Content of Default Template?</label>
				    	   	<input type="radio" name="defaultTemplate" checked="checked" id="defaultTemplateYes" value="true" title="defaultTemplateYes">Yes
						   	<input type="radio" name="defaultTemplate" id="defaultTemplateNo" value="false" title="defaultTemplateNo">No	
					   </div>
					   <div style="width: 70%;" id="emailTemplateDiv" >	
							<label for="emailTemplateLists" class="field-label" >Select a Different Template</label>      		
							<select id="emailTemplateLists" name="emailTemplateLists" class="bcg_select">
									<option value="">Select</option>							 		  
							</select>
						</div>
							
				</div>			
			
			</c:if>
			
			<c:if test="${ActivationEmailTemplate.isDefault}">							
				<div class="form-fields" style="width: 95%;" >					
					<label class="field-label" for="templateName"><span class="lbl-required">*</span>&nbsp;&nbsp;Template Name</label>
					<label class="label_height" style="line-height: 20px;" >User Activation Email Template - Default</label>&nbsp;&nbsp;&nbsp;
					<form:input type="hidden" path="templateName" value="User Activation Email Template - Default" />
				</div>				
			</c:if>
				
			<c:if test="${ActivationEmailTemplate.isDefault eq 'false'}">					
				<div class="form-fields" style="width: 95%;" >	
				
					<div style="width: 25%;float:left;" >
						<label class="field-label" style="margin-left: 2px;" for="templateName"><span class="lbl-required">*</span>&nbsp;&nbsp;Template Name</label>
						&nbsp;&nbsp;&nbsp;&nbsp;<form:input type="text" style="width: 80%;" class="text required" path="templateName" maxlength="200" />
						<div style="font-size: 12px;margin:-5px 0 0 133px; padding-top: 10px;">(max 200 characters)</div>	
					</div>	
						
					<div style="width: 22%;float:left;" >	
							<label for="assessmentProgramId" class="isrequired field-label" ><span class="lbl-required">*</span>&nbsp;&nbsp;&nbsp;&nbsp;Assessment Program</label>      		
							
							<form:select path="assessmentProgramId" class="bcg_select required">
								<form:option value="" label="Select"/>
								<form:options items="${assessProgLists}"/>
							</form:select>
					</div>	
					
					<div class="form-fields" style="width: 13%;float:left;line-height: 22px;" >
					    <label class="field-label" style="width: 70%;"><span class="lbl-required">*</span>&nbsp;&nbsp;All States?</label>
				   	    &nbsp;&nbsp;<form:radiobutton path="allStates" class="checkedAllStates" checked="checked" style="width: 60%;" value="true" title="allStatesYes"/> Yes
	                	&nbsp;&nbsp;<form:radiobutton path="allStates" class="unCheckedAllStates" style="width: 60%;" value="false" title="allStatesNo"/> No				  	
					</div>
				
					<div class="form-fields" style="width: 20%;" id="statesDiv" >
					  		<label for="statesSelect" class="field-label" >State(s)</label>      		
							<select id="statesSelect" name="states" multiple="multiple" class="bcg_select" title="Select State">													 		 
							</select>		  	
					</div>
					
				</div>       	 						
			</c:if>
		

		    <div class="form-fields" style="width: 95%;margin-top: 30px;" >
		    	<label class="field-label" style="float:left;width: 8%;margin-top: 2px;" for="emailSubject"><span class="lbl-required">*</span>&nbsp;&nbsp;Subject:</label>
				<form:input type="text" style="width: 60%;" class="text required" path="emailSubject"  maxlength="200" title="emailSubject"/>
				<div style="font-size: 12px;margin: -5px 0 0 628px; padding-top: 10px;">(max 200 characters)</div>			
			</div>

		    <div class="form-fields" style="width: 95%;" >
		    	<label class="field-label" style="float:left;width: 13%;" for="emailBody"><span class="lbl-required">*</span>&nbsp;&nbsp;Body of Email:</label>
				<input type="hidden" id="emailBodyHidden" />
				<div class="message-content" style="width:103%;">				
					<textarea name="emailBody" id="emailBody" class="message_content required" >${ActivationEmailTemplate.emailBody}</textarea>
				</div>	
				<div style="font-size: 12px;margin: 3px 0 0 700px;">(max 1000 characters)</div>			
			</div>
								
			<div class="form-fields" style="width: 95%;margin-top: 15px;" >
				 <label class="field-label" style="float:left;width: 14%;"><span class="lbl-required">*</span>&nbsp;&nbsp;Include EP Logo?</label>
			   <div style="padding-top: 12px;">	 
				 <form:radiobutton path="includeEpLogo" class="checkedEpLogo required" checked="checked" style="width: 60%;" value="true" title="includeEpLogoYes"/> Yes
                 <form:radiobutton path="includeEpLogo" class="unCheckedEpLogo required" style="width: 60%;" value="false" title="includeEpLogoNo"/> No
			   </div>
			</div>
                	 
			<br/>
			<div  style="float: right;" >
				<c:if test="${ActivationEmailTemplate.id==null}">
					<button type="button" class="btn_blue" id="emailTemplateClose" style="width: 90px; margin-left: 24px;" >Cancel</button>		
				</c:if>
				<c:if test="${ActivationEmailTemplate.id!=null}">
					<button type="button" class="btn_blue" id="emailTemplateClose" style="width: 150px; margin-left: 24px;" >Cancel & Close</button>
				    <button type="reset" class="btn_blue" id="EditEmailTemplateReset" style="width: 90px; margin-left: 24px;" >Reset</button>	
				</c:if>	
				<button type="button" id="editEmailTemplateSave" value="${ActivationEmailTemplate.id}" class="btn_blue save" style="width: 90px; margin-left: 24px;"> <fmt:message key="button.save"/> </button>
			</div>
	</form:form>
</div>	

<div id="confirmDialogStatesMove" style="width : 510px !important; display:none;margin-top: 20px; " title=""></div>

<script type="text/javascript" src="<c:url value='/js/configuration/createActivationEmailTemplate.js'/>"> </script>