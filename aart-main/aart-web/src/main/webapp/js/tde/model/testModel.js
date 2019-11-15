var testModel = can.Model({
	
	getStudentTest : function(jsonObj, testSections, success) {
		
		$.ajax({
			url : contextPath + '/JSON/studentTest/getById.htm',
			dataType : 'json',
			type : "POST",
			data : {
				studentsTestsId : jsonObj.id,
				pluckedTestSections : testSections,
				testTypeName : jsonObj.testTypeName
			},
			success : success
		});
	},
	
	getTest : function(jsonObj, success) {
		$.ajax({
			url : contextPath + '/JSON/studentTest/getTestById.htm',
			dataType : 'json',
			type : "GET",
			data : {
				testId : jsonObj.testId,
				testFormatCode : jsonObj.testFormatCode,
				studentsTestId : jsonObj.id,
				userName : tde.config.student.userName,
				testTypeName : jsonObj.testTypeName
			},
			success : success
		});
	},
	
	getTicket : function(studentsTestsId, success) {
		$.ajax({
			url : contextPath + '/JSON/studentTest/checkTicket.htm',
			dataType : 'json',
			type : "GET",
			data : {
				studentsTestsId : studentsTestsId,
				userName : tde.config.student.userName
			},

			success : success
			
		});
	}

	
	
}, {});