<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<!-- Try to use two div's to represent data in columns like one column for labels and another for textboxes/select or any other controls  -->
<!-- This page needs to be changed to incorporate the above change and need to have some cleanup whenever we have sometime. -->

<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	
	<div id="createOrganization" style="padding:20px;">
		<div id="ARTSmessages" class="messages">
			<span class="error_message ui-state-error permissionDeniedMessage hidden" ><fmt:message key="error.permissionDenied"/></span>
	        <span class="info_message ui-state-highlight successMessage hidden" id="successMessage" >Successfully created organization.</span>
	        <span class="error_message ui-state-error selectAllLabels hidden validate" id="failMessage">Failed to create Organization.</span>
	        <span class="error_message ui-state-error selectAllLabels hidden validate requiredMessage" id="orgRequiredMessage">Choose all required fields.</span>
			<span class="error_message ui-state-error selectAllLabels hidden duplicate" id="duplicateMessage" >Organization already exists with same display identifier.</span>
		</div>
	    <div>
			<label for="actions" class="isrequired">Select Action</select><font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;			
			<select id="actions" class="bcg_select" name="actions">
				<option value="">Select</option>
			 	<option value="viewOrg">View Organization</option>
				<option value="uploadOrg">Upload Organization</option>
				<option value="createOrg">Create Organization</option>
			</select>
		</div>
		
		<br />
		<hr>
		<br />
		
		 <div id="orgNameDiv" class="hidden">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="organizationName" class="isrequired">Organization Name</select><font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="text" id="organizationName" name="organizationName" class="resizedTextbox" maxlength="50" size ="31"/>
        </div><br/>
        
		<div id="orgDisplayIdDiv" class="hidden">
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="organizationDisplayId" class="isrequired">Organization Display Id</select><font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           	<input type="text" id="organizationDisplayId" name="organizationDisplayId" class="resizedTextbox" maxlength="50" size ="31"/>
       	</div> <br/>
       	
	   	<div id="contractingOrgFlagDiv" class="hidden">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="contractingOrgFlag" class="isrequired">Contracting Organization</select><font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select id="contractingOrgFlag" class="bcg_select" name="contractingOrgFlag">
				<option value="">Select</option>
		 		<option value="yes">Yes</option>
				<option value="no">No</option>
			</select>			
		</div>
		
	  	<div id="assessmentProgramsDiv"  class="hidden">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="assessmentPrograms" class="isrequired">Select Assessment Program<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select id="assessmentProgramSelect" name="assessmentPrograms" class="bcg_select" style="width:300px;" multiple="multiple">
	 			<c:if test="${fn:length(assessmentPrograms) > 0 }">						
					<c:forEach items="${assessmentPrograms }" var="assessmentProgram" varStatus="index">
						<option value="${assessmentProgram.id }">${assessmentProgram.programName}</option>
					</c:forEach>					
				</c:if>	 
			</select>
		</div><br/>
		
		<div id="orgTypeDiv" class="hidden">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="orgType" class="isrequired">Organization Type<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select id="orgType" class="bcg_select" name="orgType">
				<option value="">Select</option>
		 			<c:if test="${fn:length(organizationTypes) > 0 }">						
						<c:forEach items="${organizationTypes}" var="organizationType" varStatus="index">
							<option value="${organizationType.typeCode}">${organizationType.typeName}</option>
						</c:forEach>					
					</c:if>	  
			</select>
		</div>
		
		<div id="dateDiv" class="hidden"> <br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="startDate" class="isrequired">Start Date</select><font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           	<input id="startDate" name="startDate" class="resizedTextbox" /><br><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="endDate" class="isrequired">End Date</select><font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           	<input id="endDate" name="endDate" class="resizedTextbox" /><br />
		</div>
		
		<div id="orgStructureDiv" class="hidden"> <br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="orgStructure" class="isrequired">Select Organization Structure<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select id="orgStructureSelect" name="orgStructure" class="bcg_select" style="width:300px;" multiple="multiple">
	 			<c:if test="${fn:length(organizationTypes) > 0 }">						
					<c:forEach items="${organizationTypes }" var="organizationType" varStatus="index">	
						<option value="${organizationType.typeCode}">${organizationType.typeName}</option>
					</c:forEach>					
				</c:if>	 
			</select> 
			<br /> <br />
	  	</div>
	  	
       	<div id="buldinguniqueDiv" class="hidden">
   			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="buldingunique" class="isrequired">Building Uniqueness<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   			
			<select id="buldingunique" name="orgStructure" class="bcg_select">
				<option value="">Select</option>
			</select>
       	</div>
       	
       	<div id="parentOrgDiv" class="hidden">
       		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="parentOrg" class="isrequired">Parent Organization<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      		
			<select id="parentOrg" name="parentOrg" class="bcg_select">
				<option value="">Select</option>
		 		<c:if test="${fn:length(organizationsConsortia) > 0 }">						
					<c:forEach items="${organizationsConsortia}" var="org" varStatus="index">
						<option value="${org.id}">${org.organizationName}</option>
					</c:forEach>					
				</c:if>	  
			</select>			
       	</div>
           
		<div id="orgHierarchyDiv" class="hidden">
			<div id="state"><br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="stateSelect">Select State<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="stateSelect" class="bcg_select" name="stateSelect">
					<option value="">Select</option>
			 		<c:if test="${fn:length(organizationsStates) > 0 }">						
						<c:forEach items="${organizationsStates}" var="state" varStatus="index">
							<option value="${state.id}">${state.organizationName}</option>
						</c:forEach>					
					</c:if>	  
				</select>
			</div>
			
		 	<div id="region" class="hidden"> <br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="regionSelect">Select Region<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="regionSelect" class="bcg_select" name="regionSelect"></select>
			</div>
			
		 	<div id="area" class="hidden"> <br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="areaSelect">Select Area<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="areaSelect" class="bcg_select" name="areaSelect"></select>
			</div>
			
			<div id="district" class="hidden"> <br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="districtSelect">Select District<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="districtSelect" class="bcg_select" name="districtSelect"></select>
			</div>
			
			<div id="building" class="hidden"> <br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="buildingSelect">Select Building<font size="5" color="red">*</font>:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="buildingSelect" class="bcg_select" name="buildingSelect"></select>
			</div>
		</div>
		
		<br /><br />
        
        <div id="createOrgSave" class="hidden" style="text-align:center;">
	        <span>
	            <button class ="btn_blue" id="createOrgSave">Save</button>
	        </span>
        </div>
    </div>
    
    <script>
    	$("#assessmentProgramSelect").select2();    
    	$("#orgStructureSelect").select2();
      	var flag= new Boolean();
    	flag=false;
    	var orgTypeCodes = [];
    	    	
    	$('#actions').change(function() {
    		if($('#actions').val() == "createOrg") {
    			<security:authorize access="hasRole('PERM_ORG_CREATE')">
	    			$('#orgNameDiv').show();
	    			$('#orgDisplayIdDiv').show();
	    			$('#contractingOrgFlagDiv').show();
	    			$('#orgTypeDiv').show();
	    			$('#createOrgSave').show();
	    		</security:authorize>
	    		<security:authorize access="!hasRole('PERM_ORG_CREATE')">
		    		$("#ARTSmessages").show();
					$('.permissionDeniedMessage').show();
					setTimeout("aart.clearMessages()", 5000);
					setTimeout(function(){ $("#ARTSmessages").hide(); },5000);
	    		</security:authorize>
    		} else {
    			resetSelection("all");
    		}
    	});
    	
    	$('#contractingOrgFlag').change(function() {
    		var contractingOrgFlag = $("#contractingOrgFlag").val();
    		//alert(contractingOrgFlag);
    		 if(contractingOrgFlag != null){
    			 if(contractingOrgFlag == "yes"){
    				 $("#dateDiv").show();
    				 $("#assessmentProgramsDiv").show();
    			 } else{
    				 $("#dateDiv").hide();
    				 $("#assessmentProgramsDiv").hide();
    			 }
    		 }
    	});

    	$('#orgType').change(function() {
    		var orgType = $("#orgType").val();
    		if(orgType != null) {
    			//alert(orgType);
				resetSelection("orgType");
    			  
				if(orgType == "ST") {
    	    		var valArr = ['CONS','ST'];
					for(var i=0; i < valArr.length ; i++) {
						$("#orgStructureSelect").prop("disabled","disabled");
						//$("#orgStructureSelect").multiselect("widget").find(":checkbox[value='"+valArr[i]+"']").attr("disabled","disabled");
	  					//$("#orgStructureSelect").multiselect("widget").find(":checkbox[value='"+valArr[i]+"']").prop("checked","checked");						
	  					$("#orgStructureSelect option[value='" + valArr[i] + "']").attr("selected", 1);	  					
	  					$("#orgStructureSelect").trigger('change.select2');
	  				}
					
					$("#orgStructureSelect").addClass('orgStructureSelectClass');
					$('.orgStructureSelectClass').find('input').each(function(){
					    if($.inArray($(this).val(), valArr) > -1)
							$(this).prop('disabled', true);
					});
				}
    			  
    			 if(orgType == "ST") {
    				 $("#orgStructureDiv").show();
    				 $("#parentOrgDiv").show();
    				 $("#orgHierarchyDiv").hide();
    			 } else if(orgType != "CONS") {
    				 orgTypeCodes.push("ST");
    				 $("#orgStructureDiv").hide();
    				 $("#buldinguniqueDiv").hide();
    				 $("#parentOrgDiv").hide();
    				 $("#orgHierarchyDiv").show();
    			 }
    		 } 
    	});
     	$('#state').change(function() {
     		var value = $("#stateSelect").val();
     		//alert($('#orgType').val());
     		createDropdowns("3",value);
     		$("#area").hide();
     		$("#district").hide();
     		$("#building").hide();
     		/* var htmlstring = '<label for="region">Select'+ Region+':;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>';
     		 */
    	});
     	$('#region').change(function() {
     		var value = $('#regionSelect').val();
     		createDropdowns("4",value);
     		$("#district").hide();
     		$("#building").hide();
     		/* var htmlstring = '<label for="region">Select'+ Region+':;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>';
     		 */
    	});
     	$('#area').change(function() {
     		var value = $('#areaSelect').val();
     		createDropdowns("5",value);
     		$("#building").hide();
     		/* var htmlstring = '<label for="region">Select'+ Region+':;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>';
     			 */
    	});
     	$('#district').change(function() {
     		var value = $('#districtSelect').val();
     		createDropdowns("6",value);
     		/* var htmlstring = '<label for="region">Select'+ Region+':;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>';
     		 */
    	});
     	$('#building').change(function() {
     		var value = $('#buildingSelect').val();
     		createDropdowns("7",value);
     		/* var htmlstring = '<label for="region">Select'+ Region+':;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>';
     		 */
    	});
     	
    	$("#orgStructureSelect").change(function() {
    		var orgStructureSelect = [];
    		var vals = [];
    		var textvals = [];
    		$('#orgStructureSelect :selected').each( function( i, selected ) {
    			textvals[i] = $( selected ).text();
    		});
    		$('#buldingunique').html('');    		
    		orgStructureSelect = $("#orgStructureSelect").val();
  			if($.inArray("BLDG", orgStructureSelect) >= 0) {
  				$("#buldinguniqueDiv").show();
  				for(var i=0;i<orgStructureSelect.length;i++){
  					$('#buldingunique').append(new Option(textvals[i],orgStructureSelect[i]));
  				}

  			} else {
  				$("#buldinguniqueDiv").hide();
  			}
  			
    	});
    	
    	$('input.required').each(function(){
    	    $(this).after('*');
    	});
    	
 		function createDropdowns(orgtype,value){
 			var selectedOrgType = $("#orgType").val();
 			if(orgtype != selectedOrgType) {
 			$.ajax({
				url: 'getChildrenOfOrganizationType.htm',					
				dataType: 'json',
				data:{
					organizationId : value,
					organizationTypeId: orgtype
				},
				type: "GET",
				success: function(response) {
					var orgTypeCode;
					if(response != null && response.length > 0) {
						orgTypeCode = response[0].organizationType.typeCode;
						orgTypeCodes.push(orgTypeCode);
						
						if(orgTypeCode == "RG" && ValidToShowDropdown("RG", selectedOrgType)) {
							$("#region").show();
							$('#regionSelect').html('');
							$('#regionSelect').append(new Option("Select",""));
							for(var i=0;i<response.length;i++) {
			  					$('#regionSelect').append(new Option(response[i].displayIdentifier,response[i].id));
			  				}
						} else if(orgTypeCode == "AR" && ValidToShowDropdown("AR", selectedOrgType)) {
							$("#area").show();
							$('#areaSelect').html('');
							
							$('#areaSelect').append(new Option("Select",""));
							for(var i=0;i<response.length;i++) {
			  					$('#areaSelect').append(new Option(response[i].displayIdentifier,response[i].id));
			  				}
						} else if(orgTypeCode == "DT" && ValidToShowDropdown("DT", selectedOrgType)) {
							$("#district").show();
							$('#districtSelect').html('');
							
							$('#districtSelect').append(new Option("Select",""));
							for(var i=0;i<response.length;i++) {
			  					$('#districtSelect').append(new Option(response[i].displayIdentifier,response[i].id));
			  				}
						} else if(orgTypeCode == "BLDG" && ValidToShowDropdown("BLDG", selectedOrgType)) {
							$("#building").show();
							$('#buildingSelect').html('');
							
							$('#buildingSelect').append(new Option("Select",""));
							for(var i=0;i<response.length;i++){
			  					$('#buildingSelect').append(new Option(response[i].displayIdentifier,response[i].id));
			  				}
						}
					}
				}
				});
 			}
 		}
 		
    	$("#startDate").datepicker();
 	    $("#endDate").datepicker();
 	    
 	    function ValidToShowDropdown(orgTypeCode, selectedOrgType) {
 	    	var showDropdown = false;
 	    	
 	    	if(orgTypeCode == "RG" && selectedOrgType == "RG" &&
 	    			selectedOrgType != "ST" && selectedOrgType != "CONS") {
				showDropdown = true;
			}
 	    	if(orgTypeCode == "AR" && selectedOrgType == "AR" &&
 	    			selectedOrgType != "RG" && selectedOrgType != "ST" &&
 	    			selectedOrgType != "CONS") {
				showDropdown = true;
			}
 	    	if(orgTypeCode == "DT" &&  selectedOrgType != "DT" && 
 	    			selectedOrgType != "AR" && selectedOrgType != "RG" && 
 	    			selectedOrgType != "ST" && selectedOrgType != "CONS") {
				showDropdown = true;
			}
 	    	if(orgTypeCode == "BLDG" && selectedOrgType == "BLDG" &&
 	    			selectedOrgType != "DT" && selectedOrgType != "AR" &&
 	    			selectedOrgType != "RG" && selectedOrgType != "ST" &&
 	    			selectedOrgType != "CONS") {
				showDropdown = true;
			}

 	    	return showDropdown;
 	    }
 	    
 	    
 	    function resetSelection(changeType) {
 			
 			var organizationName = $('#organizationName');
 			var organizationDisplayId = $('#organizationDisplayId');
 			var contractingOrgFlag = $('#contractingOrgFlag');
 			var assessmentProgramSelect = $('#assessmentProgramSelect');
 			var orgType = $('#orgType');
 			var startDate = $('#startDate');
 			var endDate = $('#endDate');
 			var orgStructureSelect = $('#orgStructureSelect');
 			var buldingunique = $('#buldingunique');
 			var parentOrg = $('#parentOrg');
 			var stateSelect = $('#stateSelect');
 			var regionSelect = $('#regionSelect');
 			var areaSelect = $('#areaSelect');
 			var districtSelect = $('#districtSelect');
 			var buildingSelect = $('#buildingSelect');
 			
 	    	if(changeType == "orgType") {
 	    		regionSelect.children().remove();
 	    		areaSelect.children().remove();
 	    		districtSelect.children().remove();
 	    		buildingSelect.children().remove();
 	    		orgStructureSelect.val($("#orgStructureSelect option:first").val());
 	    		parentOrg.val($("#parentOrg option:first").val());
 	    		
 	    		$('#region').hide();
 	    		$('#area').hide();
 	    		$('#district').hide();
 	    		$('#building').hide();
 	    		$("#orgStructureDiv").hide();
 	    		$("#parentOrgDiv").hide();
 	    	} else if(changeType == "all") {
 	    		organizationName.val("");
 	    		organizationDisplayId.val("");
 	    		contractingOrgFlag.val($("#contractingOrgFlag option:first").val());
 	    		assessmentProgramSelect.val($("#assessmentProgramSelect option:first").val());
 	    		orgType.val($("#orgType option:first").val());
 	    		startDate.val("");
 	    		endDate.val("");
 	    		orgStructureSelect.val($("#orgStructureSelect option:first").val());
 	    		buldingunique.val($("#buldingunique option:first").val());
 	    		parentOrg.val($("#parentOrg option:first").val());
 	    		stateSelect.val($("#stateSelect option:first").val());
 	    		regionSelect.val($("#regionSelect option:first").val());
 	    		areaSelect.val($("#areaSelect option:first").val());
 	    		districtSelect.val($("#districtSelect option:first").val());
 	    		buildingSelect.val($("#buildingSelect option:first").val());
 	    		assessmentProgramSelect.val('').trigger('change.select2');
 	    		orgStructureSelect.val('').trigger('change.select2');
 	    		
 	    		$('#orgNameDiv').hide();
    			$('#orgDisplayIdDiv').hide();
    			$('#contractingOrgFlagDiv').hide();
    			$('#orgTypeDiv').hide();
				$("#orgStructureDiv").hide();
				$("#buldinguniqueDiv").hide();
				$("#parentOrgDiv").hide();
				$("#orgHierarchyDiv").hide();
				$("#dateDiv").hide();
				$("#assessmentProgramsDiv").hide();
    			$('#createOrgSave').hide();
 	    		$('#region').hide();
 	    		$('#area').hide();
 	    		$('#district').hide();
 	    		$('#building').hide();
 	    		
 	    	}
 	    	
 	    }
 	    
 	    
    	function validate() {
    		var assessmentPrograms = new Array();
 			assessmentPrograms = $("#assessmentProgramSelect").val();
 			var orgType = $("#orgType").val();
 			var orgStructureSelect = $("#orgStructureSelect").val();
 			var validData = true;
 			
 			//alert($('#state').val());
 			
 			if(($("#organizationName").val() == ' ') || ($('#organizationName').val() == '')) {
 				validData = false;
 				//alert("organizationName");
    		}
 			
    		if(($("#organizationDisplayId").val() == ' ') || ($('#organizationDisplayId').val() == '')) {
    			validData = false;
    			//alert("organizationDisplayId");
    		}
 			
    		if(($("#contractingOrgFlag").val() == ' ') || ($('#contractingOrgFlag').val() == '')) {
    			validData = false;
    			//alert("contractingOrgFlag");
    		}
    		
    		if($("#contractingOrgFlag").val() == "yes" && 
    				(assessmentPrograms == null || assessmentPrograms.length < 1)) {
    			validData = false;
    			//alert("assessmentPrograms");
    		}
    		
    		if($("#contractingOrgFlag").val() == "yes" && 
    				($('#startDate').val() == ' ' || $('#startDate').val() == '')) {
    			validData = false;
    			//alert("startDate");
    		}
    		
    		if($("#contractingOrgFlag").val() == "yes" && 
    				($('#endDate').val() == ' ' || $('#endDate').val() == '')) {
    			validData = false;
    			//alert("endDate");
    		}
    		
    		if(orgType == null || orgType.length < 1) {
    			validData = false;
    			//alert("orgType");
    		}
    		
    		if(orgType == "ST" &&
    				(orgStructureSelect == null || orgStructureSelect.length < 1)) {
    			validData = false;
    			//alert("orgStructureSelect");
    		}
    		
    		if(($.inArray("BLDG", orgStructureSelect) >= 0) && 
    				(($("#contractingOrgFlag").val() == ' ') || ($('#contractingOrgFlag').val() == ''))) {
    			validData = false;
    			//alert("contractingOrgFlag");
    		}
 			
    		if(orgType == "ST" && 
    				(($("#parentOrg").val() == ' ') || ($('#parentOrg').val() == ''))) {
    			validData = false;
    			//alert("parentOrg");
    		}

    		$.each(orgTypeCodes, function (index, value) {
    			//alert(value);
    			if(value == "ST" && ($('#stateSelect').val() == ' ' || $('#stateSelect').val() == '')) {
    				validData = false;
    				//alert("st");
    			}
    			if(value == "RG" && ($('#regionSelect').val() == ' ' || $('#regionSelect').val() == '')) {
    				validData = false;
    				//alert("rg");
    			}
    			if(value == "AR" && ($('#areaSelect').val() == ' ' || $('#areaSelect').val() == '')) {
    				validData = false;
    				//alert("ar");
    			}
    			if(value == "DT" && ($('#districtSelect').val() == ' ' || $('#districtSelect').val() == '')) {
    				validData = false;
    				//alert("dt");
    			}
    			if(value == "BLDG" && ($('#buildingSelect').val() == ' ' || $('#buildingSelect').val() == '')) {
    				validData = false;
    				//alert("bldg");
    			}
    		});
    		
    		return validData;
    	}
    	
    	
    	$("#createOrgSave").on("click",function(){
			if(validate()) {
				var assessmentPrograms = [];
	 			assessmentPrograms = $("#assessmentProgramSelect").val();
	 			var orgType = $("#orgType").val();
	 			var orgStructureSelect = [];
	 			if($("#orgStructureSelect").val() != null && $("#orgStructureSelect").val() != '')
	 				orgStructureSelect = $("#orgStructureSelect").val();
	 			
	 			var orgName = $("#organizationName").val();
	 			var orgDisplayId = $("#organizationDisplayId").val();
	 			var contractingOrgFlag = $("#contractingOrgFlag").val();
	 			var buldingUniqueness = $('#buldingUniqueness').val();
	 			if(buldingUniqueness == null || buldingUniqueness == "undefined") {
	 				buldingUniqueness = "";
	 			}
	 			
	 			var parentOrg = $("#parentOrg").val();
	 			if(parentOrg == null || parentOrg == "undefined") {
	 				parentOrg = "";
	 			}
	 			
				var startDate = $('#startDate').val();
				if(startDate == null || startDate == "undefined") {
					startDate = "";
	 			}
				
				var endDate = $('#endDate').val();
				if(endDate == null || endDate == "undefined") {
					endDate = "";
	 			}
				
				var state = $('#stateSelect').val();
				if(state == null || state == "undefined") {
					state = "";
	 			}
				
				var region = $('#regionSelect').val();
				if(region == null || region == "undefined") {
					region = "";
	 			}
				
				var area = $('#areaSelect').val();
				if(area == null || area == "undefined") {
					area = "";
	 			}
				
				var district = $('#districtSelect').val();
				if(district == null || district == "undefined") {
					district = "";
	 			}
				
				var building = $('#buildingSelect').val();
				if(building == null || building == "undefined") {
					building = "";
	 			}
	 			
				$.ajax({
					url: 'createOrganization.htm',					
					//dataType: 'json',
   		            type: "POST",
					data:{
						orgName: orgName,
						orgDisplayId: orgDisplayId,
						orgType: orgType,
						assessmentProgramIds: assessmentPrograms,
						organizationStructure: orgStructureSelect,
						contractingOrgFlag: contractingOrgFlag,
						buldingUniqueness: buldingUniqueness,						
						parentOrg: parentOrg,
						startDate: startDate,
						endDate: endDate,
						state: state,
						region: region,
						area: area,
						district: district,
						building: building,
					},
					success: function(response) {
						if(response == "success") {
							resetSelection("all");
							$("#actions").val($("#actions option:first").val());
							$("#ARTSmessages").show();
							$('#successMessage').show();
							setTimeout("aart.clearMessages()", 3000);
							setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
						} else if(response == "failed") {
							$("#ARTSmessages").show();
							$('#failMessage').show();
							setTimeout("aart.clearMessages()", 3000);
							setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
						} else if(response == "duplicate") {
							$("#ARTSmessages").show();
							$('#duplicateMessage').show();
							setTimeout("aart.clearMessages()", 3000);
							setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
						}
						
					},
					error: function() {
						$("#ARTSmessages").show();
						$('#failMessage').show();
						setTimeout("aart.clearMessages()", 3000);
						setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
					}
					});
				
			} else {
				$("#ARTSmessages").show();
				$('.requiredMessage').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function(){ $("#ARTSmessages").hide(); },3000);	
			}
    		
    	});
    	
    	

    </script>
