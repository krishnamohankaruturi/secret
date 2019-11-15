<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

	<div  id="editModule" class="_bcg">
	
	<form:form id="editModuleForm" name="editModuleForm" class="form">
	
        <div style="text-align:right;">
			
        	<a class="btn_blue" style="float:right;" href="#" id="editModuleCancel">Cancel</a>
            <a class="btn_blue" style="float:right;" href="#" id="editModuleSave">Save</a>
		</div>
		<div class="messages">
			<!-- Language & Braille tab errors -->
	        <span class="error_message ui-state-error selectAllLabels hidden editValidate" id="failMessage"><fmt:message key="label.pd.edit.validate"/></span>
	        <span class="error_message ui-state-error selectAllLabels hidden editDuplicate" id="duplicateMessageError" ><fmt:message key="label.pd.duplicate"/></span>
   	        <span class="error_message ui-state-error selectAllLabels hidden editPassingScoreInvalid" id="passingScoreInvalidError" ><fmt:message key="label.pd.passingScoreInvalid"/></span>
		    <div><br>
			    <div>
			    	<input id="moduleId" type="hidden" name="moduleId"/>
			    </div>	    
		        <div>		        	
		            <label for="emoduleName" class="isrequired">Module Name:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		            <input type="text" id="emoduleName" name="emoduleName" maxlength="50" size ="31"/><br><br>
	        	</div>
		         <div>
					<label for="eassessmentPrograms" class="isrequired"><fmt:message key="label.setuptestsession.assessment.assessmentprogram" />:<font size="5" color="red">*</font></label>&nbsp;
					<select id="eassessmentPrograms" name="eassessmentPrograms" class="bcg_select">
						<option value="">Select Assessment Program</option>
			     		<c:if test="${fn:length(assessmentPrograms) > 0 }">
							<c:forEach items="${assessmentPrograms }" var="assessmentProgram" varStatus="index">
								<option value="${assessmentProgram.id }">${assessmentProgram.programName}</option>
							</c:forEach>					
						</c:if>	 
					</select>					
				</div><br>
				<label for="etests" class="isrequired">Choose Test:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="etests" class="bcg_select" name="etests">
				<option value="">Select Test</option>
				 <c:if test="${fn:length(tests) > 0 }">						
					<c:forEach items="${tests}" var="test" varStatus="index">
						<option value="${test.id}" maxScore="${test.maxScore}">${test.testName}</option>
					</c:forEach>
				</c:if>	  
				</select>
			</div> <br>
			<div>
				<label for="etutorials" class="isrequired">Choose Module:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="etutorials" class="bcg_select" name="etutorials">
					<option value="">Select Module</option>
				 	<c:if test="${fn:length(tutorials) > 0 }">						
						<c:forEach items="${tutorials}" var="test" varStatus="index">
							<option value="${test.id}">${test.testName}</option>
						</c:forEach>					
					</c:if>
				</select>
			</div> <br>
			<div>
				<label for="epassingScoreLabel" id="epassingScoreLabel"></label>
			</div> <br>
			<div>
				<label for="epassingScore" class="isrequired">Minimum Passing Score:<font id="epassingScoreAstrick" size="5" color="red"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input id="epassingScoreSpinner" name="epassingScoreSpinner" value="0"/>
			</div> <br>			
	        <div>
	        <p class="formfield">
	            <label for="edescription" class="isrequired">Description:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
	             <textarea id="edescription" name="edescription" style="resize: none;" rows="5" cols="64"></textarea>
	            </p>
	        </div><br>
	        <div>
	        <p class="formfield">
	            <label for="esuggestAudience">Suggested Audiences:&nbsp;</label>
	             <textarea id="esuggestAudience" style="resize: none;" rows="5" cols="65"></textarea>
	          </p>
	        </div>
	         <div>
				<label for="eroles" class="isrequired"><fmt:message key="label.pd.myModules.roles" />:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="erolesSelect" name="eroles" class="bcg_select" style="width:385px;" multiple="multiple">
				 	<c:if test="${fn:length(roles) > 0 }">						
						<c:forEach items="${roles }" var="role" varStatus="index">
							<option value="${role.groupId }">${role.groupName}</option>
						</c:forEach>					
					</c:if>	 
				</select>
			</div><br>
			<div>
				<label for="erequiredflag" class="isrequired">Module Required:<font size="5" color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input type="radio" id="erequiredflag" name="erequiredflag" value="Yes">Yes</input>
				<input type="radio" id="erequiredflag" name="erequiredflag" value="No">No</input>
			</div> <br>  
			<div id="divCEU"><br>
				<label for="eceu" class="isrequired">CEU :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<select id="eceu" class="bcg_select" name="eceu">
					<option value="">Select CEU</option>
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select><font size="5" color="red">*</font>
			</div> <br>
		    <div>
				<span id="addTagError"></span>
		        <span class="error_message ui-state-error selectAllLabels hidden editTagValidate" id="tagValidateError" ><fmt:message key="label.pd.tag.validate"/></span>
		        <span class="error_message ui-state-error selectAllLabels hidden editTagInputValidate" id="tagInputValidateError" ><fmt:message key="label.pd.tag.length.validate"/></span>				
				<label for="eaddTagInput">Tags :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input type="text" id="eaddTagInput" size="50" maxlength="75"/>
				<input type="button" value="Add Tag" id="eaddTagButton"/>
		    </div>		
		    <div>
				<p id="tagClickInfo"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>(click to remove)<p>
				<div id="pdEditTags">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
			</div><br>	
			<!-- <div>
	           <input id="status" type="text" class="hidden" />
	        </div> -->
	    </div>
	    </form:form>	    
    </div>
    <script>
	    $(function() {
	    
		   	$('input.required').each(function(){
		   	    $(this).after('*');
		   	});

			$.validator.addMethod("evalueNotInRange", function(value, element, arg){
		   		var maxScore = $('option:selected', $("#etests")).attr('maxScore');
		   		var passingScore = $.trim($("#epassingScoreSpinner").val());
		   		if(maxScore != undefined && parseInt(maxScore) >= 0 && !isNaN(parseInt(passingScore)) && 
		   				(parseInt(passingScore) < 0 || parseInt(passingScore) > parseInt(maxScore))) {
		   			return false;
		   		} else {
		   			return true;
		   		}
			}, "");	
			
	    	$('#editModuleForm').validate({
	    		ignore: "",
				onfocusout: false,
			    onkeyup: false,
			    onclick: false,
	    		rules: {
	    				emoduleName: {required: true},
	    				etests:{required: true},
	    				edescription:{required: true},
	    				eassessmentPrograms:{
	    					required: true
	    				},
	    				epassingScoreSpinner:{
	    					required: {
								depends: function() {
							   		var maxScore = $('option:selected', $("#etests")).attr('maxScore');
							   		var passingScore =  $.trim($("#epassingScoreSpinner").val());

							   		if(maxScore != undefined && parseInt(maxScore) >= 0 && passingScore == ""){
										return true;
									} else {
										return false;
									}
								}
							},
							number: {
								depends: function() {
						   		var maxScore = $('option:selected', $("#etests")).attr('maxScore');
						   		var passingScore = $.trim($("#epassingScoreSpinner").val());
						   		if(maxScore != undefined && parseInt(maxScore) >= 0 
						   				&& isNaN(parseInt(passingScore))) {
									return true;
								} else {
									return false;
								}
							}
						},
						evalueNotInRange : $("#epassingScoreSpinner").val()
	    				},
	    				eceu:{
	    					required: {
	    						depends: function() { 
	    							if(isPDStateAdmin && isStateCEUEditAccess){
	    								return true;
	    							} else {
		    							return false;	
	    							}
	    						}
	    					}
	    				}
	    		},
	    		 messages: {
	    			 epassingScoreSpinner:{
	    				 evalueNotInRange: function () {
		    					 var maxScore = $('option:selected', $("#etests")).attr('maxScore');
		    					 return '<fmt:message key="label.pd.passingScoreInvalid"><fmt:param value="' + maxScore +'"/></fmt:message>';
	    					 }
	    			 }
	    		 }
	    	});    	
	    });	   	
	 		
	    $("#etests").change(function(){
            var maxScore = $('option:selected', $(this)).attr('maxScore');
            if(maxScore == undefined) {
            	$("#epassingScoreLabel").html("");
            	$("#epassingScoreAstrick").html("");
            } else if(maxScore <= 0) {
            	$("#epassingScoreLabel").html("The selected test's is not scored, nor is a there a required passing value.");
            	$("#epassingScoreAstrick").html("");
            } else {
            	$("#epassingScoreLabel").html("The selected test's highest possible score is "+ maxScore + " points.");
            	$("#epassingScoreAstrick").html("*");
            }			
        });
	    
	  	function validate(){
	   		var ddl = document.getElementById("etests");
	   		var selectedValue = ddl.options[ddl.selectedIndex].value;
	   		var maxScore = $('option:selected', $("#etests")).attr('maxScore');
	   		var passingScore = $("#epassingScoreSpinner").val();

	   		//alert('maxScore:'+ maxScore);
	   		//alert('passingScore:'+ passingScore);
	   		
			var valid = true;
	   		if(($("#emoduleName").val() == ' ') || ($('#emoduleName').val() == '')){
	   			valid = false;
	   		} else if(($("#edescription").val() == ' ') || ($('#edescription').val() == '')){
	   			valid = false;
	   		} else if($("#eassessmentProgramSelect").val() < 0){
	   			valid = false;
	   		} else  if (isPDStateAdmin && isStateCEUEditAccess && ($('#eceu').val() < 0)){
	   			valid = false;
	   		} else  if (selectedValue == ""){
	   			valid = false;
	   		} else if(passingScore == "") {   			
	   			valid = false;
	   		}
	   		
	   		if(!valid) {
		   		$('.editValidate').show();
		   		setTimeout("aart.clearMessages()", 5000);
	   		} else {
	   			$('.editValidate').hide();
	   		}
	   		
	   		if(passingScore != "" && maxScore != undefined && parseInt(maxScore) >= 0 && (isNaN(parseInt(passingScore)) 
	   				|| parseInt(passingScore) < 0 || parseInt(passingScore) > parseInt(maxScore))) {	   			
		   		$('.editPassingScoreInvalid').show();
		   		setTimeout("aart.clearMessages()", 5000);
	   			valid = false;
	   		}
	   		
			var tagText = $('#eaddTagInput').val();
			if(tagText != ''){//make sure that tag text was added
				if(tagText.length > 0){
			   		$('.editTagValidate').show();
			   		setTimeout("aart.clearMessages()", 5000);
					valid = false;
				}
			}
			
	   		return valid;
	   	}
	   	
	  	$("#editModuleCancel").unbind('click').bind('click', function(){
	  		$("#editModuleForm").data('validator').resetForm();
	  		$('#editModule').dialog('close');
	  	});
	  	
	   	$("#editModuleSave").unbind('click').bind('click', function(){
	   		
	   		var formValid = $('#editModuleForm').valid();
			
			var tagText = $('#eaddTagInput').val();
			if(tagText != ''){//make sure that tag text was added
				if(tagText.length > 0){
			   		$('.editTagValidate').show();
			   		setTimeout("aart.clearMessages()", 5000);
					valid = false;
				}
			}			
			
	   		if(formValid){   
	   			
	   			var testId = $("#etests").val();
	   			var tutorialId = $("#etutorials").val();
	   			var moduleName = $("#emoduleName").val();
	   			var description = $("#edescription").val();
	   			var suggestAudience = $("#esuggestAudience").val();
	   			var ceu = $("#eceu").val();
	   			var passingScore = $("#epassingScoreSpinner").val();
	   			var assessmentProgramId = $("#eassessmentPrograms").val();	
	   			var requiredflagValue = $('input:radio[name=erequiredflag]:checked').val();
	   			var roleIds = $("#erolesSelect").val();
	   			
		   		var tagIds = [];
		   		$(".tag").each(function() {
		   			tagIds.push($("input[name=epdTagIds]", this).val());
		   		});

	   			$('#msges').html('');
	   			

	   			$.ajax({
	   				url: 'updateModule.htm',					
	   				dataType: 'json',
	   				data:{
	   					moduleId : $("#moduleId").val(),
	   					testId : testId,
	   					tutorialId: tutorialId,
	       				moduleName: moduleName,
	   					description: description,
	   					passingScore : passingScore,
	   					suggestAudience: suggestAudience,
	   					ceu: ceu,
	   					roleIds : roleIds,
	   					assessmentProgramId: assessmentProgramId,
	   					tagIds : tagIds,
	   					requiredflag:requiredflagValue
	   				},
	   				type: "POST",
	   				success: function(response) {
	   					if(response){
	         			
 				         	$('#editModule').dialog('close');
 				         	getModules();
 				         	$('#modulesTable').trigger("reloadGrid");
		        			
	   						$('.editSuccessMessage').show();
	   						setTimeout("aart.clearMessages()", 5000);
	   					}else{
	   						$('.editDuplicate').show();
	   						setTimeout("aart.clearMessages()", 5000);
	   					}
	   				}
	   			});	
	   		}	   		
	   	});
	   
		//setup event binding for the add tag button
		$('#eaddTagButton').unbind('click').bind('click',function(){
			var tagText = $('#eaddTagInput').val();
			if(tagText != ''){//make sure that tag text was added
				if(tagText.length > 75){//make sure that the length of the text is less than 75
			   		$('.editTagInputValidate').show();
			   		setTimeout("aart.clearMessages()", 5000);
				} else {//if there is text and it is less than 75 characters than add the tag
					//$('#addTagError').html('').removeClass('errors');
					//add the specified tag to the task
					eaddTag(tagText);
				}						
			}
		});
		
		//delegated onclick event for removing the tags
		$('#pdEditTags').delegate('.tag', 'click', function(){
			if(!isPDStateAdmin){
				$(this).remove();
			}
	    });
		
	   function eaddTag(tagName){
				
   			$.ajax({
   				url: "addTag.htm",
   				data: {
       				tagName: tagName
   				},
   				dataType: 'json',
   				type: 'POST',
   				success: function(tag) {   					
					//clear the input field
					$('#eaddTagInput').val('');
					//append the tag to the list
					epopulateTagList(tag);   					   					
   				}
   			});
		}
	   
	   function epopulateTagList(pdTag){
		   if(isPDStateAdmin){
			   $('#pdEditTags').append("<span class='tag'><input type='hidden' name='epdTagIds' value='" + pdTag.id + "'/>" + pdTag.tagName + "</span>");
		   } else {
			   $('#pdEditTags').append("<span class='tag'><input type='hidden' name='epdTagIds' value='" + pdTag.id + "'/><a href='#'>" + pdTag.tagName + "</a></span>");
		   }
		}
	   
    </script>