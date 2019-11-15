<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

	<div  id="createModules">
	    
	    <form:form id="createModuleForm" name="createModuleForm" class="form">
		<div class="messages">
			<!-- Language & Braille tab errors -->
	        <span class="info_message ui-state-highlight successMessage hidden" id="successMessage" ><fmt:message key="label.pd.createModuleSuccess"/></span>
	        <span class="error_message ui-state-error selectAllLabels hidden validate" id="failMessage"><fmt:message key="label.pd.validate"/></span>
	        <span class="error_message ui-state-error selectAllLabels hidden duplicate" id="duplicateMessageError" ><fmt:message key="label.pd.duplicate"/></span>
	        <span class="error_message ui-state-error selectAllLabels hidden passingScoreInvalid" id="passingScoreInvalidError" ><fmt:message key="label.pd.passingScoreInvalid"/></span>
		    <div><br>
		        <div>
		            <label for="moduleName" class="isrequired">Module Name:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		            <input type="text" id="moduleName" name="moduleName" maxlength="50" size ="31"/><br><br>
	        	</div>
		        <div>
					<label for="assessmentPrograms" class="isrequired"><fmt:message key="label.setuptestsession.assessment.assessmentprogram" />:<font size="5" color="red">*</font></label>&nbsp;
					<select id="assessmentPrograms" name="assessmentPrograms" class="bcg_select">
						<option value="">Select Assessment Program</option>
			     		<c:if test="${fn:length(assessmentPrograms) > 0 }">
							<c:forEach items="${assessmentPrograms }" var="assessmentProgram" varStatus="index">
								<option value="${assessmentProgram.id }">${assessmentProgram.programName}</option>
							</c:forEach>					
						</c:if>	 
					</select>					
				</div><br>        	
				<label for="tests" class="isrequired">Choose Test:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="tests" class="bcg_select" name="tests">
				<option value="">Select Test</option>
				 <c:if test="${fn:length(tests) > 0 }">						
					<c:forEach items="${tests}" var="test" varStatus="index">
						<option value="${test.id}" maxScore="${test.maxScore}">${test.testName}</option>
					</c:forEach>
				</c:if>	  
				</select>
			</div> <br>
			<div>
				<label for="tutorials" class="isrequired">Choose Module:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="tutorials" class="bcg_select" name="tutorials">
					<option value="">Select Module</option>
				 	<c:if test="${fn:length(tutorials) > 0 }">						
						<c:forEach items="${tutorials}" var="test" varStatus="index">
							<option value="${test.id}">${test.testName}</option>
						</c:forEach>					
					</c:if>
				</select>
			</div> <br>
			<div>
				<label for="passingScoreLabel" id="passingScoreLabel"></label>
			</div> <br>
			<div>
				<label for="passingScore" class="isrequired">Minimum Passing Score:<font id="passingScoreAstrick" size="5" color="red"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="passingScoreSpinner" name="passingScoreSpinner" value="0"/>
			</div> <br>
	        <div>
	        <p class="formfield">
	            <label for="description" class="isrequired">Description:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	             <textarea id="description" name="description" style="resize: none;" rows="5" cols="64" title="Description" ></textarea>
	            <!-- <input type="text" id="description1" name="description" maxlength="50" size ="31"/><span class="error">*</span><br><br> -->
	            </p>
	        </div><br>
	        <div>
	        <p class="formfield">
	            <label for="suggestAudience">Suggested Audiences:&nbsp;</label>
	             <textarea id="suggestAudience" style="resize: none;" rows="5" cols="65"></textarea>
	          <!--   <input type="text" id="suggestAudience1" name="suggestAudience" maxlength="50" size ="31"/><br><br> -->
	          </p>
	        </div>
	         <div>
				<label for="roles" class="isrequired"><fmt:message key="label.pd.myModules.roles" />:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="rolesSelect" name="roles" class="bcg_select" style="width:385px;" multiple="multiple">
				 	<c:if test="${fn:length(roles) > 0 }">						
						<c:forEach items="${roles }" var="role" varStatus="index">
							<option value="${role.groupId }">${role.groupName}</option>
						</c:forEach>					
					</c:if>	 
				</select>
			</div><br>
			<div>
				<label for="requiredflag" class="isrequired">Module Required:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input type="radio" id="requiredflag" name="requiredflag" value="Yes">Yes</input>
				<input type="radio" id="requiredflag" name="requiredflag" value="No">No</input>
			</div> <br>
		    <div>
				<span id="addTagError"></span>
		        <span class="error_message ui-state-error selectAllLabels hidden tagValidate" id="tagValidateError" ><fmt:message key="label.pd.tag.validate"/></span>
		        <span class="error_message ui-state-error selectAllLabels hidden tagInputValidate" id="tagInputValidateError" ><fmt:message key="label.pd.tag.length.validate"/></span>				
				<label for="addTagInput">Tags:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="addTagInput" size="50" maxlength="75"/>
				<input type="button" value="Add Tag" id="addTagButton"/>
		    </div>		
		    <div>
				<p><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>(click to remove)<p>
				<div id="pdCreateTags">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
			</div><br>	
			<!-- <div>
	           <input id="status" type="text" class="hidden" />
	        </div> -->
	        <div style="text-align:center;">
		        <span>
		        	<a class="btn_blue" href="#" id="createModuleSave">Save</a>
		        </span>
		        <span>
		            <a class="btn_blue" href="#" id="createModuleSavePublish">Save &amp; Publish</a>
		        </span>
		        <span>
		            <a class="btn_blue cancel" href="#" id="clearSelections"><fmt:message key='label.common.cancel'/></a>
		        </span>	        
	        </div>
	    </div>
	    </form:form>
    </div>
    <script>
	    $(function() {
		   	//$('#passingScoreSpinner').spinner();
		   	$('input.required').each(function(){
		   	    $(this).after('*');
		   	});
		   	
		    var $radios = $('input:radio[name=requiredflag]');
		    if($radios.is(':checked') === false) {
		        $radios.filter('[value=No]').prop('checked', true);
		    }
		    
			$.validator.addMethod("valueNotInRange", function(value, element, arg){
		   		var maxScore = $('option:selected', $("#tests")).attr('maxScore');
		   		var passingScore = $.trim($("#passingScoreSpinner").val());
		   		if(maxScore != undefined && parseInt(maxScore) >= 0 && !isNaN(parseInt(passingScore)) && 
		   				(parseInt(passingScore) < 0 || parseInt(passingScore) > parseInt(maxScore))) {
		   			return false;
		   		} else {
		   			return true;
		   		}
			}, "");	
			
	    	$('#createModuleForm').validate({
	    		ignore: "",
				onfocusout: false,
			    onkeyup: false,
			    onclick: false,
	    		rules: {
	    				moduleName: {required: true},
	    				tests:{required: true},
	    				description:{required: true},
	    				assessmentPrograms:{
	    					required: true
	    				},
	    				passingScoreSpinner:{
	    					required: {
								depends: function() {
							   		var maxScore = $('option:selected', $("#tests")).attr('maxScore');
							   		var passingScore =  $.trim($("#passingScoreSpinner").val());

							   		if(maxScore != undefined && parseInt(maxScore) >= 0 && passingScore == ""){
										return true;
									} else {
										return false;
									}
								}
							},
							number: {
								depends: function() {
						   		var maxScore = $('option:selected', $("#tests")).attr('maxScore');
						   		var passingScore = $.trim($("#passingScoreSpinner").val());
						   		if(maxScore != undefined && parseInt(maxScore) >= 0 
						   				&& isNaN(parseInt(passingScore))) {
									return true;
								} else {
									return false;
								}
							}
						},
						valueNotInRange : $("#passingScoreSpinner").val()
	    				}
	    		},
	    		 messages: {
	    			 passingScoreSpinner:{
	    				 valueNotInRange: function () {
		    					 var maxScore = $('option:selected', $("#tests")).attr('maxScore');
		    					 return '<fmt:message key="label.pd.passingScoreInvalid"><fmt:param value="' + maxScore +'"/></fmt:message>';
	    					 }
	    			 }
	    		 }
	    	});    	
	    });
	    
	    $("#rolesSelect").select2();
	   	
	    $("#tests").change(function(){
            var maxScore = $('option:selected', $(this)).attr('maxScore');
            if(maxScore == undefined) {
            	$("#passingScoreLabel").html("");
            	$("#passingScoreAstrick").html("");
            } else if(maxScore <= 0) {
            	$("#passingScoreLabel").html("The selected test's is not scored, nor is a there a required passing value.");
            	$("#passingScoreAstrick").html("");
            } else {
            	$("#passingScoreLabel").html("The selected test's highest possible score is "+ maxScore + " points.");
            	$("#passingScoreAstrick").html("*");
            }			
        });
		
	   	function createValidate(){

	   		var ddl = document.getElementById("tests");
	   		var selectedValue = ddl.options[ddl.selectedIndex].value;
	   		var maxScore = $('option:selected', $("#tests")).attr('maxScore');
	   		var passingScore = $("#passingScoreSpinner").val();
	   		
	   		//alert('maxScore:'+ maxScore);
	   		//alert('passingScore:'+ passingScore);
	   		
			var valid = true;
	   		if(($("#moduleName").val() == ' ') || ($('#moduleName').val() == '')){
	   			valid = false;
	   		} else if(($("#description").val() == ' ') || ($('#description').val() == '')){
	   			valid = false;
	   		} else if($("#assessmentPrograms").val() < 0){
	   			valid = false;
	   		} else  if(selectedValue == ""){
	   			valid = false;
	   		} else if(passingScore == "") {   			
	   			valid = false;
	   		}	   		

	   		if(!valid) {
		   		$('.validate').show();
		   		setTimeout("aart.clearMessages()", 5000);
	   		} else {
	   			$('.validate').hide();
	   		}
	   		
	   		if(passingScore != "" && maxScore != undefined && parseInt(maxScore) >= 0 && (isNaN(parseInt(passingScore)) 
	   				|| parseInt(passingScore) < 0 || parseInt(passingScore) > parseInt(maxScore))) {	   			
		   		$('.passingScoreInvalid').show();
		   		setTimeout("aart.clearMessages()", 5000);
	   			valid = false;
	   		}

			var tagText = $('#addTagInput').val();
			if(tagText != ''){//make sure that tag text was added
				if(tagText.length > 0){
			   		$('.tagValidate').show();
			   		setTimeout("aart.clearMessages()", 5000);
					valid = false;
				}
			}

	   		return valid;
	   	}
	   		
   		$("#createModuleSave").unbind('click').bind('click', function(){
	   		
	   		var formValid = $('#createModuleForm').valid();

			var tagText = $('#addTagInput').val();
			if(tagText != ''){//make sure that tag text was added
				if(tagText.length > 0){
			   		$('.tagValidate').show();
			   		setTimeout("aart.clearMessages()", 5000);
			   		formValid = false;
				}
			}
			
	   		if(formValid){    

	   			var testId = $("#tests").val();
	   			var tutorialId = $("#tutorials").val();
	   			var moduleName = $("#moduleName").val();
	   			var description = $("#description").val();
	   			var suggestAudience = $("#suggestAudience").val();
	   			//var passingScore = $("#passingScoreSpinner").spinner("value");
	   			var passingScore = $("#passingScoreSpinner").val();
	   			var assessmentProgramIdValue = $("#assessmentPrograms").val();	
	   			var requiredflagValue = $('input:radio[name=requiredflag]:checked').val();
	   			var roleIds = $("#rolesSelect").val();
	   			var state = "UNPUBLISHED";	   			
		   		var tagIds = [];
		   		$(".tag").each(function() {
		   			tagIds.push($("input[name=pdTagIds]", this).val());
		   		});
		   		//alert(tagIds.join(", "));
	   			$('#msges').html('');
	   				 
	   			$.ajax({
	   				url: 'createModules.htm',					
	   				dataType: 'json',
	   				data:{
	   					testId : testId,
	   					tutorialId: tutorialId,
	       				moduleName: moduleName,
	   					description: description,
	   					passingScore : passingScore,
	   					suggestAudience: suggestAudience,
	   					assessmentProgramId: assessmentProgramIdValue,
	   					roleIds : roleIds,
	   					state: state,
	   					tagIds : tagIds,
	   					requiredflag:requiredflagValue
	   				},
	   				type: "POST",
	   				success: function(response) {
	   					if(response){
	   						$('.successMessage').show();
	   						setTimeout("aart.clearMessages()", 5000);
	   						clearSelectionsPD();
	   					}else{
	   						$('.duplicate').show();
	   						setTimeout("aart.clearMessages()", 5000);
	   					}
	   				}
	   			});	
	   		}	   		
	   	});	
	   	
	   	$("#createModuleSavePublish").unbind('click').bind('click', function(){
	   		//$("#status").val("PUBLISHED");
		   		var formValid = $('#createModuleForm').valid();
				var tagText = $('#addTagInput').val();
				if(tagText != ''){//make sure that tag text was added
					if(tagText.length > 0){
				   		$('.tagValidate').show();
				   		setTimeout("aart.clearMessages()", 5000);
				   		formValid = false;
					}
				}
				
		   		if(formValid){  
					
	   			var testId = $("#tests").val();
	   			var tutorialId = $("#tutorials").val();
	   			var moduleName = $("#moduleName").val();
	   			var description = $("#description").val();	   			
	   			var suggestAudience = $("#suggestAudience").val();
	   			//var passingScore = $("#passingScoreSpinner").spinner("value");
	   			var passingScore = $("#passingScoreSpinner").val();
	   			var assessmentProgramIdValue = $("#assessmentPrograms").val();
	   			var requiredflagValue = $('input:radio[name=requiredflag]:checked').val();
	   			var roleIds = $("#rolesSelect").val();
	   			var state = "PUBLISHED";
		   		var tagIds = [];
		   		$(".tag").each(function() {
		   			tagIds.push($("input[name=pdTagIds]", this).val());
		   		});   			
	   			
	   			$('#msges').html('');
	   			$.ajax({
	   				url: "createModules.htm",
	   				data: {
	   					testId : testId,
	   					tutorialId : tutorialId,
	       				moduleName: moduleName,
	   					description: description,
	   					passingScore : passingScore,
	   					suggestAudience: suggestAudience,
	   					assessmentProgramId: assessmentProgramIdValue,
	   					roleIds: roleIds,
	   					state: state,
	   					tagIds : tagIds,
	   					requiredflag: requiredflagValue
	   				},
	   				dataType: 'json',
	   				type: 'POST',
	   				success: function(response) {
	   					if(response){
	   						$('.successMessage').show();
	   						setTimeout("aart.clearMessages()", 5000);
	   						clearSelectionsPD();
	   					}else{
	   						$('.duplicate').show();
	   						setTimeout("aart.clearMessages()", 5000);
	   					}
	   				}
	   			});	
	   		
	   		}
	   	});
	   	
	   	$("#clearSelections").click(function(){
	   		$("#createModuleForm").data('validator').resetForm();
	   		clearSelectionsPD();
	   	});
	   	
	   function clearSelectionsPD(){
		    $("#tests").val("");
		    $("#tutorials").val("");
			$("#moduleName").val("");
			$("#description").val("");
			$("#suggestAudience").val("");
			$("#assessmentPrograms").val("-1");
			$("#[id$=rolesSelect]").val('').trigger('change.select2');
			//$("#passingScoreSpinner").spinner("value", 0);
			$("#passingScoreSpinner").val("0");
			$("#passingScoreLabel").html("");
			$("#passingScoreAstrick").html("");
			
		    var $radios = $('input:radio[name=requiredflag]');
		    $radios.filter('[value=No]').prop('checked', true);
		    
		    $('#pdCreateTags').find('span').each(function() { 
			   $(this).remove();
			});			   
	   }
	   
		//setup event binding for the add tag button
		$('#addTagButton').unbind('click').bind('click',function(){
			var tagText = $('#addTagInput').val();
			if(tagText != ''){//make sure that tag text was added
				if(tagText.length > 75){//make sure that the length of the text is less than 75
			   		$('.tagInputValidate').show();
			   		setTimeout("aart.clearMessages()", 5000);
				} else {//if there is text and it is less than 75 characters than add the tag
					//$('#addTagError').html('').removeClass('errors');
					//add the specified tag to the task
					addTag(tagText);
				}						
			}
		});				
		//delegated onclick event for removing the tags
		$('#pdCreateTags').delegate('.tag', 'click', function(){
			$(this).remove();
	    });
		
	   function addTag(tagName){
				
   			$.ajax({
   				url: "addTag.htm",
   				data: {
       				tagName: tagName
   				},
   				dataType: 'json',
   				type: 'POST',
   				success: function(tag) {   					
					//clear the input field
					$('#addTagInput').val('');
					//append the tag to the list
					populateTagList(tag);   					   					
   				}
   			});
		}
	   
	   function populateTagList(pdTag){		
			$('#pdCreateTags').append("<span class='tag'><input type='hidden' name='pdTagIds' value='" + pdTag.id + "'/><a href='#'>" + pdTag.tagName + "</a></span>");
		}
	   
    </script>