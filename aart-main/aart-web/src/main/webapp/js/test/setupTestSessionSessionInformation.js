$(function() {
$('#setupTestSession').on("click",function() {
		// clear any previous messages ourselves, to correct any odd timings,
		// i.e. they enter a session name that already exists, but enter a new one
		// before the old message has cleared, sometimes the success message could be cleared
		// before intended.
		clearTimeout(setupTestSessionMessageTimeout);
		setupTestSessionMessageTimeout = null;
		aart.clearMessages();
		
		var $this = $(this);
		var testCollectionId;
        var testId;
        var validParams = true;
	    var students;
	    var testSessionName;
	    var arrayTestId;
	    
	    //Get selected test/testCollections.
	    testCollectionId = selectedTestCollectionId;
	    arrayTestId = arraySelectedTestId;
	    testId = selectedTestId;

	    //Get selected students.
	    students = getSelectedStudentEnrollmentID();

        //Get given testSession name.
        testSessionName = $('#testSessionName').val();
        testSessionName=testSessionName.trim();
        //Display errors on empty/null for testId/testCollectionId/students/testSessionName.
        if(testCollectionId == undefined || testCollectionId == null || testCollectionId == "") {
        	validParams = false;
	        $('#testsession_testortestcollection_required').show();	
        }
        if (students == undefined || (students == null && students.length <= 0) || students == "") {
       		validParams = false;        		
       		$('#testsession_student_required').show();        		
        }	        	        	      
        if(testSessionName == undefined || testSessionName == null || testSessionName.length < 3 || testSessionName == "") {
        	validParams = false;
        	$('#session_name_required').show();
        }
        
        if (validParams) {    			    			    
   		    if (students !== undefined && testId !== undefined && students !== null &&
   		    		testId !== null && students.length > 0 && testId.length>0) { 
   		    	$this.attr('disabled', 'disabled').addClass('ui-state-disabled');
   		    	
   		    	 $.ajax({
   		            url: 'assignStudentsToTest.htm',
   		            data: {
   		            	students: students,
   		            	testCollectionId: testCollectionId,
   		            	arraySelectedTestId : arrayTestId,
   		                testId: testId,
   		                sessionName: testSessionName
   		            },
   		            dataType: 'json',
   		            type: "POST"
   		    	 }).done(function(response) {
   		            	
   		            	var messageDelay = 10 * 1000; // 10s
   		                var redirectDelay = 10.5 * 1000; // 10.5s
   		                if (response.valid) {
   		                    $('#testSessionName').val('');
   		                    //$('#test_session_created', "#messages").removeAttr('style').show();
   		                    if(!response.multipleTests){
   		                    	$(".messages", "#sessionConfirmation").removeAttr('style').html("<span class='info_message ui-state-highlight'>" + setupTestSessionMessages['label.testsession.created'] + "</span>").show();
		                    }
   		                    
   		                    if(response.multipleTests){
   		                    	$(".messages", "#sessionConfirmation").removeAttr('style').html("<span class='info_message ui-state-highlight'>" + setupTestSessionMessages['label.testsession.request.status'] + "</span>").show();
   		                    }
   		                    
   		                   setTimeout(function(){
   		                    	window.location.href = 'viewTestSessions.htm?sourcePage=setupTestSessionSessionInformation&showPage=TM';
   		                    }, redirectDelay);
   		                } else {
   		                    if (response.duplicateKey) {
   		                    	$(".messages", "#sessionConfirmation").removeAttr('style').html("<span class='error_message ui-state-error'>" + setupTestSessionMessages['error.testsession.duplicatename'] + "</span>").show();
   		                    } else {
   		                    	$('#testSessionName').val('');
   		                        $('#test_session_error').show();
   		                    }
   		                }
   		                setupTestSessionMessageTimeout = setTimeout("aart.clearMessages()", messageDelay);
   		    	 }).always(function(){
   		            	$this.removeAttr('disabled').removeClass('ui-state-disabled');
   		    	 });
   		    }
    	} else {
    		setupTestSessionMessageTimeout = setTimeout("aart.clearMessages()", 3000);
    		return false;
    	}

});		

$('#testSessionName').on('keyup', function () {
	  testSessionName = $('#testSessionName').val();
	  if ((/[^A-Za-z0-9\#\\\/\.\,\=\+\&\'\:\(\)\s\_\-]+/.test(testSessionName))) {
	    validParams = false;
	    $('#session_name_required_only_characters').css({
	      'display': 'block',
	      'border-color': 'white'
	    });
	    $('#setupTestSession').attr('disabled','disabled').addClass('ui-state-disabled');
	  } 
	  else {
		validParams = true;
	    $('#session_name_required_only_characters').css({
	      'display': 'none',
	      'border-color': 'white'
	    });
	    $('#setupTestSession').prop('disabled', false).removeClass('ui-state-disabled');
	  }
});

});