var tdeModel = can.Model({
	
	getStudentsTests : function(testTypeName, success) {
		$.ajax({
			url : contextPath + '/JSON/studentTest/getAll.htm',
			dataType : 'json',
			type : "GET",
			data : {
				testTypeName : testTypeName
			},
			success : success
		});
	}
	

	
}, {});