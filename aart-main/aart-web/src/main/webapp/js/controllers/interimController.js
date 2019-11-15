var interimController = can.Control({
	defaults : {
		root : '',
		view : 'js/views/assemble.ejs',
		data:''
	}

}, {
	getItemJson : function() {
		var jsonData = {};
		jsonData.name = $('#interimTestName').val();		
		jsonData.description = $('#description').val();

		return jsonData;
		
		
	}
});