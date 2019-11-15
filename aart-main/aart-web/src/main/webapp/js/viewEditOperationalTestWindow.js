
function editTestWindowInitMethod(id){
	$('#assessmentProgramTestWindow').change(function(){
//		populateTestWindowTestCollection($(this).val());
	});
//	$('#editOperationalTestWindowViewSetup :input').attr('readonly','readonly');
//	window.location = "newOperationalTestWindowView.htm?Id="+id;
	editTestWindowPageMethod();
	populateEditTestWindowAssessmentProgram(0);
}
function editTestWindowPageMethod(){

			$("#SelectedCollectionId").jqGrid({
				datatype: "local",
				height: 150,
				rowNum: 10,
				rowList: [10,20,30],
			   	colNames:['Id','Collections','ProgramName'],
			   	colModel:[
			   		{name:'Id', index:'Id', width:60, sortable: false, search:false},
			   		{name:'Collections', index:'Collections', width:200, sortable: false, search:true},
			   		{name:'ProgramName', index:'ProgramName', width:200, hidden:true, sortable: true, search:true}
			   	],
			   	pager: "#page2",
			   	viewrecords: true,
			   	scroll:true,
			   	multiselect:true,
			   	height:'370px',
			 	width:'270px'
			});
			
			$("#SelectedCollectionId").jqGrid('filterToolbar');
			
			var Selecteddatas = [];
			$("#AddInoperationalTestcollection").on('click',function(){
				  var SelectedgridId = $('#SelectedCollectionId');
				  var SelectgridId = $('#SelectCollectionId');
			    var valueselected = SelectgridId.jqGrid('getGridParam', 'selarrrow');
			    var SelectedGridDataId = [];
			    SelectedgridId[0].refreshIndex();
			    var SelectedGridData = SelectedgridId.jqGrid('getDataIDs');
			    $.each(SelectedGridData,function(index,value){
			  	  SelectedGridDataId[index] = value;
			    });
			    console.log(SelectedGridDataId);
			    $.each(valueselected,function(index,value){
			  	  Selecteddata = SelectgridId.getRowData(value);
			  	  Selecteddatas[index] = Selecteddata;
			  	  console.log($.inArray(value,SelectedGridDataId));
			  	  var check = $.inArray(value,SelectedGridDataId);
			  	  var condition = -1;
					  if(check==condition){
			  	 	SelectedgridId.jqGrid('addRowData',value, Selecteddatas[index]);
			  	  }
			  	  var deleterow = "#SelectCollectionId #"+value;
			  	  $(deleterow).hide();
			    });
				  SelectedgridId[0].refreshIndex();
			});
			
			$("#MoveOutoperationalTestcollection").on('click',function(){
				  var SelectedgridId = $('#SelectedCollectionId');
				  var SelectgridId = $('#SelectCollectionId');
			      var valueselected = SelectedgridId.jqGrid('getGridParam', 'selarrrow');
			    alert(valueselected);
			    $.each(valueselected,function(index,value){
			  	  var SelectrowId = "#SelectCollectionId #"+value;
			  	  $(SelectrowId).show();
			  	  var SelectedrowId = "#SelectedCollectionId #"+value;
			  	  $(SelectedrowId).remove();
			    });
			});
/*
	      $("#testwindowstartDate").datepicker();
	      $("#testwindowendDate").datepicker();

	      $("#testwindowstartDate").datepicker({
				dateFormat: "mm-dd-yyyy"
		  });
	      
	      $("#testwindowendDate").datepicker({
				dateFormat: "mm-dd-yyyy"
		  });	
	      $('#OperationalTestWindowSetupsave').on('click',function(){
	    	  
	      	var assessmentProgramSelectStudent = $('#assessmentProgramSelectStudent').val();
	  		var windowName = $('#windowName').val();
	  		var windowsuspend = $('#windowsuspend').is(':checked')
	  		var managedbyCodeSelect = $('#managedbyCodeSelect option:selected').val();
	  		var randomizedCodeSelect = $('#randomizedCodeSelect option:selected').val();
	  		var ticketingflag = $('#ticketingon').is(':checked');
	  		var testExit = $('#testExitId1').is(':checked');
	  		//id = testExitId1
	  		var graceperoid = $('#graceperoidon').is(':checked');
	  		//id = graceperoidon
	  		var gracetimeInMin = $('#gracetimeInMin').val();
	  		var effectivestartDateTime = $('#testwindowstartDate').val()+" "+$('#testwindowstartTime').val();
	  		var expirationendDateTime = $('#testwindowendDate').val()+" "+$('#testwindowendTime').val();

			var postSelecteddataId = "";
			var SelectgridId = $('#SelectedCollectionId');
			var valueselected = $("#SelectedCollectionId").jqGrid('getDataIDs');
			$.each(valueselected, function (index, value) {
				  Selecteddata = $('#SelectedCollectionId').jqGrid('getCell', value, 'Id');
				  alert(Selecteddata);
				  postSelecteddataId = Selecteddata+","+postSelecteddataId;
				});
			var pathname =  $(location).attr('search');
			pathvalue = pathname.split('=');
			windowId = 0;
			if(pathvalue[0]=='?Id'){
				windowId = pathvalue[1];
			}
			alert("pathname "+pathvalue+" "+windowId);
	  		$.ajax({
		            url: 'addOperationalTestWindowContent.htm',
		            data: {
		            	testCollectionId: postSelecteddataId,
		            	windowName: windowName,
		            	ticketingFlag: ticketingflag,
		            	requiredToCompleteTest: testExit,
		            	effectiveDatetime: effectivestartDateTime,
		            	expirationDatetime: expirationendDateTime,
		            	suspendWindow: windowsuspend,
		            	managedBy: managedbyCodeSelect,
		            	randomized: randomizedCodeSelect,
		            	gracePeroid: graceperoid,
		            	graceTimeInMin: gracetimeInMin,
		            	windowId:windowId
		            },
		            dataType: 'json',
		            type: "POST",
		            success: function(data) {
		            	alert("Save");
		            },
		            error: function(){
		            	alert("save error");	
		            }
	  		 });
	        });
	      
	      $("#managedbyCodeSelect option").filter(function() {
	          return $(this).text() == $("#managedbyCodeSelect").data( "codeselectvalue"); 
	      }).prop('selected', true);
	      
	      
	      $("#randomizedCodeSelect option").filter(function() {
	          return $(this).text() == $("#randomizedCodeSelect").data( "codeselectvalue"); 
	      }).prop('selected', true);
	      
	      
	      $('#testwindowstartDate').data('codeselectvalue');
	      $('#testwindowendDate').data('codeselectvalue');
	          	
		  arr = $('#testwindowstartDate').data('codeselectvalue').split(' ');
	      var Year = 0;
	      var Month = 0;
	      var Dates = 0;
	      var Time = arr[3];
	      
	      Month = arr[1];
	      Dates = arr[2];
	      Year = arr[5];
	      var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		  var monthmnumber =0;
		  for(i=0;i<11;i++){
		    if(months[i] == Month){
		      monthmnumber = i+1;
		    }
		  }
	      DateString =monthmnumber+"-"+Dates+"-"+Year+"";
	     // $("#testwindowstartDate").datepicker('setDate', '01/26/2014');
	      $("#testwindowstartDate").datepicker('setDate', monthmnumber+'/'+Dates+'/'+Year);
	      $("#testwindowstartDate").datepicker({
				dateFormat: "mm-dd-yyyy"
		  });

	      arraytime = Time.split(':');
	      var Hours = arraytime[0];
	      var Min = arraytime[1];
	      var Sec = arraytime[2];
	      var Period = "AM";
		  if(Hours >= 12) {		
				 if(Hours != 12){
					 Hours = Hours - 12;				 
				 }
				 Period = "PM";
		  }      
	      
	      $('#testwindowstartTime').val(Hours+":"+Min+":"+Sec+" "+Period);
	      arr = $('#testwindowendDate').data('codeselectvalue').split(' ');
	      var Year = 0;
	      var Month = 0;
	      var Dates = 0;
	      var Time = arr[3];
	      
	      Month = arr[1];
	      Dates = arr[2];
	      Year = arr[5];
	      arraytime = Time.split(':');
	      var Hours = arraytime[0];
	      var Min = arraytime[1];
	      var Sec = arraytime[2];
	      var Period = "AM";
		  if(Hours >= 12) {		
				 if(Hours != 12){
					 Hours = Hours - 12;				 
				 }
				 Period = "PM";
		  }      
		  var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		  var monthmnumber =0;
		  for(i=0;i<11;i++){
		    if(months[i] == Month){
		      monthmnumber = i+1;
		    }
		  }
		  DateString =monthmnumber+"-"+Dates+"-"+Year+"";
		  $("#testwindowendDate").datepicker('setDate', monthmnumber+'/'+Dates+'/'+Year);
		    $("#testwindowendDate").datepicker({
				dateFormat: "mm-dd-yyyy"
		  });	
	     $('#testwindowendTime').val(Hours+":"+Min+":"+Sec+" "+Period);
	  		  	
	  	$('#testwindowstartDate').data('codeselectvalue');
	    $('#testwindowendDate').data('codeselectvalue');
	    
	    $('#EditableWindowColectionsGrids').hide();

	    Width = $('#OpTestWindowSetupContentDiv').width();
	    Height = $('#OpTestWindowSetupContentDiv').height();
	    */
	//	$('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':Height,'width':Width,"margin-top":"0px","z-index":999});
		
		$('#button-group-new-OperationalTestWindow').hide();
		
	    tableToGrid("#ViewingWindowColections", {
	        autowidth:true,
	        pager: '#pager',
	        rowNum: 20,
	        viewrecords: true,
	        loadui: true,
	        rowList: [10,20,50],
	        scroll:true,
		   	height:'370px',
		 	width:'270px'  
		});
	    
		$("#OperationalTestWindowEditMode").hide();
		
		$('#TestCollectionEditwindow').on('click',function(){
	//		$('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':0,'width':0,"margin-top":"0px","z-index":999});
			$('#button-group-new-OperationalTestWindow').show();
			$('#EditableWindowColectionsGrids').show();
			$('#ViewingWindowColectionscontantDiv').hide();		
			$('#TestCollectionEditwindow').hide();
			$("#OperationalTestWindowEditMode").show();
			
			var SelectedgridId = $('#SelectedCollectionId');
			 var SelectgridId = $('#ViewingWindowColections');
		     var valueselected = $('#ViewingWindowColections').jqGrid('getGridParam', 'selarrrow');
		
		    var Selecteddatas = [];
			var SelectgridId = $('#ViewingWindowColections');
		  	$.each($('#ViewingWindowColections').jqGrid('getGridParam','data'),function(index,value){
		  	 	  Selecteddata = SelectgridId.getRowData(value.id);
		  	 	  Selecteddatas[index] = Selecteddata;
		  	 	  jQuery('#SelectedCollectionId').jqGrid('addRowData',"JqcollectionId"+index, Selecteddata);
		  	});
	});

}

function populateEditTestWindowAssessmentProgram(){
	$('#assessmentProgramTestWindow').select2({
		placeholder:'Select',
		multiple: false
	});
	
	
	var vowAPSelect = $('#assessmentProgramTestWindow'), vowOptionText='';
	vowAPSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	$.ajax({
		url: 'getCurrentUserAssessmentPrograms.htm',
		dataType: 'json',
		type: "POST",
		success: function(vowAssessmentPrograms) {				
			if (vowAssessmentPrograms !== undefined && vowAssessmentPrograms !== null && vowAssessmentPrograms.length > 0) {
				$.each(vowAssessmentPrograms, function(i, vowAssessmentProgram) {
					vowOptionText = vowAssessmentPrograms[i].programName;
					vowAPSelect.append($('<option></option>').val(vowAssessmentProgram.id).html(vowOptionText));
				});
				
				
			} 
			$(' #assessmentProgramTestWindow').trigger('change.select2');
		}
	});
}

function populateTestWindowTestCollection(assessmentProgramId){
	 $.ajax({
			url: 'newOperationalTestWindowAPTCDTOList.htm',
			data: {
				assessmentProgramId:assessmentProgramId
			},
			type: 'POST',
			dataType: 'json',
			success: function(data) {		            			            
		    	var mydata1 = [];    	
		    	for (var i = 0, length = data['Rows'].length; i < length; i++) {
		    		mydata1[i] = {
			            Id: data['Rows'][i].testCollectionId,
			            Collection: data['Rows'][i].name,
			            ProgramName:data['Rows'][i].programName
				    };
				}
		    	$("#SelectCollectionId").jqGrid({
		    	    	datatype: "local",
		    		    data: mydata1,
		    	      	height: 150,
		    	      	rowNum: 10,
		    	      	rowList: [10,20,30],
		    	         	colNames:['Id','Collection','ProgramName'],
		    	         	colModel:[
		    	         		{name:'Id', index:'Id', width:60, sortable: true, search:false},
		    	         		{name:'Collection', index:'Collection', width:200, sortable: true, search:true},
		    	         		{name:'ProgramName', index:'ProgramName', width:200, hidden:true, sortable: true, search:true}
		    	         	],
		    	         	pager: "#page1",
		    	         	viewrecords: true,
		    	         	scroll:true,
		    	         	multiselect:true,
		    	         	height:'370px',
		    	         	width:'270px'
		    	});
		    	$("#SelectCollectionId").jqGrid('filterToolbar');
		    },
		  	error: function() {		        	  		        	  		        	 
            	
		  	}  
		}); 
}