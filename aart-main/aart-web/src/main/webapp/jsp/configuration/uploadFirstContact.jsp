<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<div  class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">select file and organization level, click on Upload.</span>
    <form id="uploadFirstContactOrgFilterForm" name="uploadFirstContactOrgFilter">
		<div id="uploadFirstContactOrgFilter"></div>
		<div class="form-fields">
			<label for="firstContactFileData" class="form-label">File:<span class="lbl-required">*</span></label>
			<input id="firstContactFileData" name="firstContactFileData" type="file" class="hideFileUploader">
			<div class="input-append">
			    <input type="text" name="firstContactFileDataInput" id="firstContactFileDataInput" class="input-file" readOnly="readOnly"/>
			    <a class="add-on" onclick="$('input[id=firstContactFileData]').click();"><img src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
			</div>
		</div>
			 
		<script type="text/javascript">
		$('input[id=firstContactFileData]').change(function() {
			$('#firstContactFileDataInput').val($(this).val());
		});
		</script>
	</form>
    <security:authorize access="hasAnyRole('PERM_ENRL_UPLOAD', 'PERM_USER_UPLOAD', 'PERM_TEST_UPLOAD', 'PERM_ROSTERRECORD_UPLOAD')">
	 	<a class="panel_btn" href="#" id="uploadFirstContactBtn"><fmt:message key="upload.button"/></a>
	 	<div id="fcsprogressbar"></div>
	 </security:authorize>
</div>
<div class="full_main">
	<div id="uploadFirstContactGridCell">
			<div id="uploadFirstContactUploadReport" hidden="hidden"></div> <br/>
			<div id="uploadFirstContactUploadReportDetails" ></div>
			<div id="uploadFirstContact" hidden="hidden"></div>
		 	<div id="uploadFirstContactGridContainer" class="kite-table">
		 		<table class="responsive" id="orgGrid"><tr><td></td></tr></table>
				<div id="uploadFirstContactGridPager" style="width: auto;"></div>
		 	</div>
	</div>
</div> 

<script type="text/javascript">
$(function() {
	
    $("#uploadFirstContactBtn").click( function(){
    	uploadFirstContact();
    });
    
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required') || element.attr('type') == 'file') {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});
	
	$('#uploadFirstContactOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});

});

     
function uploadFirstContact(){
  var fd = new FormData();
  var filedata = $('#firstContactFileData');
  var filelist = filedata[0].files;
  var file = filelist[0];
  var stateId = $('#uploadFirstContactOrgFilter_state').val();
  var districtId = $('#uploadFirstContactOrgFilter_district').val();
  var schoolId = $('#uploadFirstContactOrgFilter_school').val();
  fd.append('stateId',stateId);
  fd.append('districtId',districtId);
  fd.append('schoolId',schoolId);
  fd.append('uploadFile',file);
  $.ajax({
	url: 'uploadFirstContact.htm',
    data: fd,
    dataType: 'json',
    processData: false,
    contentType: false,
    cache: false,
    type: 'POST',
    success: function(data){
    	$('#uploadFCReport').html('<span style="color:blue">Msg: Upload First Contact file is in-progress.</span>').show();
    	monitorUploadFirstContactFile(data.uploadFileRecordId);
    },
    error: function(jqXHR,textStatus,errorThrown) {
    	var e=errorThrown;
    }
   
  });
 
}

function monitorUploadFirstContactFile(uploadFileRecordId){
	$.ajax({
        url: 'monitorFileUploadConfiguration.htm',
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId},
        success: function(data) {
			if(data.uploadFileStatus === 'FAILED'){
        		if(data.errorFound) {
					uploadReport += "Upload Failed. ";
		    	}
        	}else if(data.uploadFileStatus === 'COMPLETED'){
		    	var uploadReport = "";
		    	if(data.uploadCompleted==true) {
		    		uploadReport += "Upload Completed Successfully. ";
		    	} else {
		    		uploadReport += "Upload Failed. ";
		    	}
		    	uploadReport += "Records Created:" + data.recordsCreatedCount +
		    						" Rejected:" + data.recordsRejectedCount +
		    						" Updated:" + data.recordsUpdatedCount;
		    	$('#uploadFCReport').html(uploadReport);
		    	$('#uploadFCReport').show();
			} else {
				$('#uploadFCReport').html('<span style="color:blue">Msg: Upload First Contact file is in-progress.</span>').show();
				window.setTimeout(function(){monitorUploadFirstContactFile(uploadFileRecordId)}, 1000);
			}  
        }
	});
}	

</script>