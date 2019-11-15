$(function(){
	batchReportingInit();
	$('#reportHistoryBtn').click();
	
	var invalidGridData=[];
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			var div = element.parents('.form-fields');
			error.appendTo(div);
		}
	});
	jQuery.validator.addMethod('notZero', function(value, element){
		return value != 0;
	});
	jQuery.validator.addMethod(
		    "greaterThanOrEqualTo",
		    function(value, element, params) {
		        var target = $(params).val();
		        var isValueNumeric = !isNaN(parseFloat(value)) && isFinite(value);
		        var isTargetNumeric = !isNaN(parseFloat(target)) && isFinite(target);
		        if (isValueNumeric && isTargetNumeric) {
		            return Number(value) >= Number(target);
		        }

		        if (!/Invalid|NaN/.test(new Date(value))) {
		            return new Date(value) >= new Date(target);
		        }

		        return false;
		    },
		    '');
	
	jQuery.validator.addMethod(
		    "isDate",
		    function(value, element) {
		    	if(value=='')
		    		return false;
		    	
		    	var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		    	var dateMatch = value.match(regexDatePattern); 
		    	
		    	if (dateMatch == null)
		    		return false;
		    	
		    	var dtMonth = dateMatch[1];
		    	var dtDay= dateMatch[3];
		    	    var dtYear = dateMatch[5];
		    	    
		    	    if (dtMonth < 1 || dtMonth > 12) 
   		          return false;
   		        else if (dtDay < 1 || dtDay> 31) 
   		          return false;
   		        else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) 
   		          return false;
   		        else if (dtMonth == 2) 
   		        {
   		          var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
   		          if (dtDay> 29 || (dtDay ==29 && !isleap)) 
   		                  return false;
   		        }
		    	    return true;
	        },
		    'Please enter a valid date');
		
	jQuery.validator.addMethod('processCheck', function (value, element) {
		return $('.processCheck:checked').length > 0;
	}, 'Please select a process to submit.');
	
	
	$('input[name=generateForSpecificStudentOrAllInDistrict]').change(function() {
		if ($('input[name="generateForSpecificStudentOrAllInDistrict').is(':checked') && $('input[name=processByStudentId]:checked').length==0)
			$('#processByStudentId').attr('checked', true);
	});
	
	$('#processByStudentId').change(function() {
		if (!$("input[name=generateForSpecificStudentOrAllInDistrict]").is(":checked")) 	
			$("input:radio[name=generateForSpecificStudentOrAllInDistrict]:first").attr('checked', true);
		else
			$("input:radio[name=generateForSpecificStudentOrAllInDistrict]").attr('checked', false);
	});
	
	
	$('#generateStudentReport').change(function() {
		if($('input[name=processReport]:checked').length==0 && $('input[name=generateStudentReport]:checked').length==0 && $('input[name=processByStudentId]:checked').length>0){
			//$('#processByStudentId').prop('checked', false);
			//$("input:radio[name=generateForSpecificStudentOrAllInDistrict]").attr('checked', false);
		}
	});
	
	$('#processReport').change(function() {
		if($('input[name=generateStudentReport]:checked').length==0 && $('input[name=processReport]:checked').length==0 && $('input[name=processByStudentId]:checked').length>0){
			//$('#processByStudentId').prop('checked', false);
			//$("input:radio[name=generateForSpecificStudentOrAllInDistrict]").attr('checked', false);
		}
	});
	
	var processCheckBoxes= $('.processCheck'); 
	console.log(processCheckBoxes);
	
	var getProcessCheckBoxesNames = $.map(processCheckBoxes, function(e, i) {
	    return $(e).attr("name")
	}).join(" ");
	
	
	//$.merge($.merge($('.processCheck'), $('.processCheck2')), $('.processCheck3'));
	$('#batchReportForm').validate({
		ignore: '',
		groups: {
	        checks: getProcessCheckBoxesNames
	    },
	    rules: {
			assessmentReportPrograms: 'required notZero',
			coursesReport: 'required notZero',
			gradesReport: 'required notZero'
		},
		messages: {
			assessmentReportPrograms: 'Please select an assessment program.',
			coursesReport: 'Please select a course.',
			gradesReport: 'Please select a grade.'
		}
	});

	$('#batchReportHistoryForm').validate({
		ignore: '',
		rules: {
			reportFromDate: 
			{
				required: true,
				isDate: true
			},
			reportToDate: 
			{
				required: true,
				greaterThanOrEqualTo: '#reportFromDate',
				isDate: true
			}
		},
		messages: {
			reportFromDate: 'Please enter a valid date.',
			reportToDate: 'Please enter a valid date.'
		}
	});
});
