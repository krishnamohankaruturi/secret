function ATSUtil(){}

$(document).ready(function(e){
	$("#confirmCloseUnsavedDialog").dialog({
	    autoOpen: false,
	    modal: true
	});
});

ATSUtil.filterJQGridModelForAssessmentProgram = function(programName, colModel, colNames){
	var retObj = {};
	var colNamesPresent = colNames != null && typeof colNames !== 'undefined';
	var retModel = [];
	var retNames = [];
	if (colModel != null && typeof colModel !== 'undefined'){
		for (var i = 0; i < colModel.length; i++){
			var obj = colModel[i];
			var keepColumn = true;
			// a whitelist will ALWAYS take effect over a blacklist
			if (obj.presentFor != null || typeof (obj.presentFor) !== 'undefined'){
				// check for programs in a whitelist (they should have the column)
				if (typeof (obj.presentFor) == 'string'){
					keepColumn = programName === obj.presentFor;
				} else {
					keepColumn = $.inArray(programName, obj.presentFor) > -1;
				}
			} else {
				// check for programs in a blacklist (they should NOT have the column)
				if (obj.notPresentFor == null || typeof (obj.notPresentFor) === 'undefined'){
					keepColumn = true; // if notPresentFor is not specified, then assume the column is to be kept
				} else if (typeof (obj.notPresentFor) == 'string'){
					keepColumn = programName !== obj.notPresentFor;
				} else {
					keepColumn = $.inArray(programName, obj.notPresentFor) === -1;
				}
			}
			// if keeping column, copy to new array
			if (keepColumn !== false){
				retModel.push(obj);
				if (colNamesPresent === true){
					retNames.push(colNames[i]);
				}
			}
		}
		retObj.colModel = retModel;
		if (colNamesPresent){
			retObj.colNames = retNames;
		}
	} else {
		console.error('filterJQGridDataForAssessmentProgram - colModel is not a legal value (' + colModel + ')');
	}
	return retObj;
}

ATSUtil.dialogLockThisExitAfterChange = function(){
	var dialogWindow = this;
	$(dialogWindow).data("unsavedChanges", false);
	$(dialogWindow).find("input, select").off("change").on("change", function(){
		$(dialogWindow).data("unsavedChanges", true);
	});
}

ATSUtil.dialogUnlockExitChangesSaved = function(dialogWindow){
	$(dialogWindow).data("unsavedChanges", false);
}

ATSUtil.dialogPromptToSave = function(dialogWindow, confirmText){
	if($(dialogWindow).data("unsavedChanges") === true){
		var confirmDialog = $('#confirmCloseUnsavedDialog');
		if (typeof (confirmText) === 'string'){
			$('#confirmCloseText', confirmDialog).text(confirmText);
		} else {
			$('#confirmCloseText', confirmDialog).text($('#defaultConfirmText', confirmDialog).val());
		}
		confirmDialog.dialog({
			resizable: false,
			height: 200,
			width: 500,
			modal: true,
			autoOpen: true,
			buttons: {
				"Exit" : function() {
					$(this).dialog("close");
					$(dialogWindow).data("unsavedChanges", false);
					$(dialogWindow).dialog("close");
				},
				"Go Back" : function(){
					$(this).dialog("close");
				}
			}
		});
		return false
    }else{
    	return true;
    }
}
