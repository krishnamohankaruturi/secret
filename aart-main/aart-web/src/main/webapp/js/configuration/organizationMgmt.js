 $(function(){	
	$('#orgNav li.nav-item:first a').tab('show');	
	 $('li.get-orgs').on('click',function(e){		 
	    	var clickedURL = $(this).find("a").attr('href');     
	    	organizationMgmtInit(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });   	
	 var usersItemMenu = $('#orgNav li.nav-item:first a');
	  if(usersItemMenu.length>0){
	   var clickedURL = usersItemMenu.attr('href');     
	   organizationMgmtInit(clickedURL.substring(1, clickedURL.length));
	  }	    
});
 
function organizationMgmtInit(action){
	$('#orgNameDiv').hide();
	$('#orgDisplayIdDiv').hide();
	$('#contractingOrgFlagDiv').hide();
	$('#orgTypeDiv').hide();
	$("#orgStructureDiv").hide();
	$("#buldinguniqueDiv").hide();
	$("#parentOrgDiv").hide();
	$("#orgHierarchyDiv").hide();
	$(".dateDiv").hide();
	$("#assessmentProgramsDiv").hide();
	$('#createOrgSaveBtnDiv').hide();
	$('#region').hide();
	$('#area').hide();
	$('#orgDistrictDiv').hide();
	$('#building').hide();
	$("#expirePasswordsFlagDiv").hide();
	$("#expirationDateTypeSelectDiv").hide();    	
	$("#programHeaderDiv").hide();
	$("#statusSchoolYearHeaderDiv").hide();
	$("#reportYearDiv").hide();		
	
	$("#assessmentProgramSelect").select2();    
	$("#orgStructureSelect").select2();
	$('#actions').select2();
	$('#contractingOrgFlag').val('').select2().trigger('change.select2');
	$('#orgType').val('').select2().trigger('change.select2');
	$('#testingModelSelect').val('').select2().trigger('change.select2');
  	var flag= new Boolean();
	flag=false;
	
		$("#message").html('');		
		if(action == "createOrgDev") {	
			$("#testingModelDiv").hide();
			if(!gCreateOrganizationLoadOnce){				
				createOrganizationInit();
			}
			$('#createOrganizationForm')[0].reset();
			resetOrgSelection("all");
			$("label.error").html('');
			$('#breadCrumMessage').text("Configuration: Organization - Create Organization");
			$('#uploadOrgDev').hide();
			$('#viewOrgDev').hide();
			$('#createOrgDev').show();
			$('#orgNameDiv').show();
    		$('#orgDisplayIdDiv').show();
    		$('#contractingOrgFlagDiv').show();
    		$('#orgTypeDiv').show();
    		$('#createOrgSaveBtnDiv').show();   	
			$('#expirePasswordsFlagDiv').hide();
			$('#expirationDateTypeSelectDiv').hide();
		} else if(action == "uploadOrgDev") {
			if(!gUploadOrganizationLoadOnce){
				uploadOrganizationsInit();
			}else{
				$('#uploadOrgFilterForm')[0].reset();
				$('#uploadOrgOrgFilter').orgFilter('reset');
				$('#orgUploadGrid').jqGrid('clearGridData');
				if($("#orgUploadGrid")[0].grid && $("#orgUploadGrid")[0]['clearToolbar'])
					$("#orgUploadGrid")[0].clearToolbar();
			}
			$('#uploadOrgFilterForm')[0].reset();
			$('#breadCrumMessage').text("Configuration: Organization - Upload Organization");
			resetOrgSelection("all");
			$("#orgUploadReport").html('');
			$("#message").html('');
			$("#orgUploadReportDetails").html('');
			$("label.error").html('');
			$('#viewOrgDev').hide();
			$('#createOrgDev').hide();
			$('#uploadOrgDev').show();
			loadOrgUploadData();
			$("#Organization_TempdownnquickHelpPopupClose").on('click',function(){
				$("#Organizations_TemplatedownloadquickHelpPopup").hide();
			});
			$(".QuickHelpHint").hide();
			$('#Roster_TemplatedownloadquickHelp').on('mouseover',function(){
				$(".QuickHelpHint").show();
			});
			$('#Roster_TemplatedownloadquickHelp').on('mouseout',function(){
				$(".QuickHelpHint").hide();
			});
			$("#Organizations_TemplatedownloadquickHelp").on('click keypress',function(event){
				if(event.type=='keypress'){
					   if(event.which !=13){
					    return false;
					   }
				}
				$("#Organizations_TemplatedownloadquickHelpPopup").show();
			});
			$("#Organizations_TemplatedownloadquickHelpPopup").hide();
		} else if(action == "viewOrgDev") {
			if(!gViewOrganizationLoadOnce){
				viewOrganizationsInit();
			}else{
				$('#viewOrgOrgFilter').orgFilter('reset');
				$('#viewOrgGridTableId').jqGrid('clearGridData');
				$grid = $("#viewOrgGridTableId");
				var myStopReload = function () {
		            $grid.off("jqGridToolbarBeforeClear", myStopReload);
		            return "stop"; // stop reload
		        };
		        $grid.on("jqGridToolbarBeforeClear", myStopReload);
		        if ($grid[0].ftoolbar) {
		        	$grid[0].clearToolbar();
		        }
				
			}
			$("label.error").html('');
			$('#breadCrumMessage').text("Configuration: Organization - View Organization");
			$('#viewOrgDev').show();
			$('#createOrgDev').hide();
			$('#uploadOrgDev').hide();
		} else {
			$('#breadCrumMessage').text("Configuration: Organization");
			$('#uploadOrgDev').hide();
			$('#createOrgDev').hide();
			$('#viewOrgDev').hide();    			
		}
	/*});*/
	
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required')) {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});	 
};

function resetOrganization(){
	
	var options = sortDropdownOptions($("#actions option"));
	options.appendTo("#actions");
	
	$('#uploadOrgDev').hide();
	$('#createOrgDev').hide();
	$('#viewOrgDev').hide(); 
	$('#actions').val('');    
	$("#message").html('');
}

function resetValue()
{
window.location.reload();
}

    
    function resetOrgSelection(changeType) {
		
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
		var expirePasswords = $('#expirePasswords');
		var expirationDateType = $('#expirationDateType');
		
    	if(changeType == "orgType") {
    		regionSelect.children().remove();
    		areaSelect.children().remove();
    		districtSelect.children().remove();
    		buildingSelect.children().remove();
    		orgStructureSelect.val($("#orgStructureSelect option:first").val());
    		parentOrg.val($("#parentOrg option:first").val());
    		
    		$('#region').hide();
    		$('#area').hide();
    		$('#orgDistrictDiv').hide();
    		$('#building').hide();
    		$("#orgStructureDiv").hide();
    		$("#parentOrgDiv").hide();
    		$("#expirePasswords").hide();
    		$("#expirationDateType").hide();
    	} else if(changeType == "all") {
    		organizationName.val("");
    		organizationDisplayId.val("");
    		contractingOrgFlag.val($("#contractingOrgFlag option:first").val());
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
    		assessmentProgramSelect.select2('val', '');
    		orgStructureSelect.select2('val', '');
    		expirePasswords.val($("#expirePasswords option:first").val());
    		expirationDateType.val($("#expirationDateType option:first").val());
    		
    		$('#orgNameDiv').hide();
			$('#orgDisplayIdDiv').hide();
			$('#contractingOrgFlagDiv').hide();
			$('#orgTypeDiv').hide();
			$("#orgStructureDiv").hide();
			$("#buldinguniqueDiv").hide();
			$("#parentOrgDiv").hide();
			$("#orgHierarchyDiv").hide();
			$(".dateDiv").hide();
			$("#assessmentProgramsDiv").hide();
			$('#createOrgSaveBtnDiv').hide();
    		$('#region').hide();
    		$('#area').hide();
    		$('#orgDistrictDiv').hide();
    		$('#building').hide();
    		$("#expirePasswordsFlagDiv").hide();
    		$("#expirationDateTypeSelectDiv").hide();    	
			$("#programHeaderDiv").hide();
			$("#statusSchoolYearHeaderDiv").hide();
			$("#reportYearDiv").hide();			
    	};
    	
    }
    
   
  



