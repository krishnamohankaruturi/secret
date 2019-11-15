

//Created function for tiny text changes

function districtEventTinyTextChanges(selectedDistrict){	
	if(selectedDistrict!='' && selectedDistrict!='Select' && selectedDistrict!=null && selectedDistrict!='undefined'){
		$("#lblTinyTextDistrict").show().text("> "+selectedDistrict);		
	}else{
		$("#lblTinyTextDistrict").hide().text('');	
	}
	$("#lblTinyTextSchool").hide().text('');
	$("#lblTinyTextSubject").hide().text('');
	$("#lblTinyTextGrade").hide().text('');
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function schoolEventTinyTextChanges(selectedSchool){	
	if(selectedSchool!='' && selectedSchool!='Select' && selectedSchool!=null && selectedSchool!='undefined'){
		$("#lblTinyTextSchool").show().text("> "+selectedSchool);
	}	
	else{
		$("#lblTinyTextSchool").hide().text('');	
	}

	$("#lblTinyTextSubject").hide().text('');
	$("#lblTinyTextGrade").hide().text('');
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');	
}

function subjectEventTinyTextChanges(selectedSubject){	
	if(selectedSubject!='' && selectedSubject!='Select' && selectedSubject!=null && selectedSubject!='undefined'){
		$("#lblTinyTextSubject").show().text("> "+selectedSubject);		
	}else{
		$("#lblTinyTextSubject").hide().text('');
	}
	$("#lblTinyTextGrade").hide().text('');
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function gradeEventTinyTextChanges(selectedGrade){	
	if(selectedGrade!='' && selectedGrade!='Select' && selectedGrade!=null && selectedGrade!='undefined'){
		$("#lblTinyTextGrade").show().text("> "+selectedGrade);
	}else{
		$("#lblTinyTextGrade").hide().text('');
	}
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function stageEventTinyTextChanges(selectedStage){	
	if(selectedStage!='' && selectedStage!='Select' && selectedStage!=null && selectedStage!='undefined'){
		$("#lblTinyTextStage").show().text("> "+selectedStage);
	}else{
		$("#lblTinyTextStage").hide().text('');
	}
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function testEventTinyTextChanges(selectedTestName){	
	if(selectedTestName!='' && selectedTestName!='Select' && selectedTestName!=null && selectedTestName!='undefined'){
		$("#lblTinyTextTestName").show().text("> "+selectedTestName);
	}else{
		$("#lblTinyTextTestName").hide().text('');
	}
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function studentNameEventTinyTextChanges(selectedStudentName){	
	if(selectedStudentName!='' && selectedStudentName!='Select' && selectedStudentName!=null && selectedStudentName!='undefined'){
		$("#lblTinyTextStudentName").show().text("> "+selectedStudentName);
	}else{
		$("#lblTinyTextStudentName").hide().text('');
	}
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function stateStudentIdEventTinyTextChanges(selectedStateStudentId){	
	if(selectedStateStudentId!='' && selectedStateStudentId!='Select' && selectedStateStudentId!=null && selectedStateStudentId!='undefined'){
		$("#lblTinyTextStateStudentId").show().text("> "+"State Student ID: "+selectedStateStudentId);
	}else{
		$("#lblTinyTextStateStudentId").hide().text('');
	}
	$("#lblTinyTextItemQuestion").hide().text('');
}

function itemQuestionEventTinyTextChanges(itemQuestion){	
	if(itemQuestion!='' && itemQuestion!='Select' && itemQuestion!=null && itemQuestion!='undefined'){
		$("#lblTinyTextItemQuestion").show().text("> "+"Item: Question "+itemQuestion);
	}else{
		$("#lblTinyTextItemQuestion").hide().text('');
	}
}

function initTinytextChanges(userAccessLevel){
	$('#lblTinyTextAssmentPrg').show().text($('select#userDefaultAssessmentProgram option:selected').html());
	if(userAccessLevel==50) {
		districtEventTinyTextChanges("> "+$('select#userDefaultOrganization option:selected').html()); 
	}
	else if(userAccessLevel==70) {
		districtEventTinyTextChanges(''); 
		schoolEventTinyTextChanges("> "+$('select#userDefaultOrganization option:selected').html());
	}
	else{
		$('#lblTinyTextDistrict').addClass('clearTinyText');
		$('#lblTinyTextSchool').addClass('clearTinyText');
		$(".clearTinyText").text('');
	}
}