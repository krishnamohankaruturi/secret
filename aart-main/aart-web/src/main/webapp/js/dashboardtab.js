
$(function() {	
	$('#dashboard li.nav-item:first a').tab('show');
});

var extractConfig = function () {
    return {
        delimiter: ',',
        header: true,
        dynamicTyping: false,
        skipEmptyLines: true,
        preview: 0,
        step: undefined,
        encoding: '',
        worker: false,
        comments: '',
        complete: completeFn,
        error: errorFn
    };
};

//Added to get List of all State Organizations for User
function getLoggedInUserStateDetails(){	
	var stateIds=[];
	$.ajax({
		async: false,
        url: 'getStatesForUser.htm',
        data: { },
        dataType: 'json',
        type: "GET"
	}).done(function(states) {
        	if (states != null) {
				$.each(states, function(i, state) {
					stateIds.push(state.id);
				});
        	}
    });
	return stateIds;
}


function getDistrictsForOrganization(stateId, select){
	select.select2({
		placeholder:'Select',
		multiple: false,
		width: 300
	});
	select.val('').find('option:not(:first)').remove();
	var url = 'getDistrictsForUser.htm';
	var data = {};
	if (typeof(stateId) != 'undefined'){
		if (stateId == ''){
			return;
		}
		url = 'getDistrictsInState.htm';
		data.stateId = stateId;
		data.isInactiveOrgReq = true;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET'
	}).done(function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
				populateOrgSelect(select, data);
			} else {
				// error
			}
	});
}

function getSchools(districtId, select){
	select.select2({
		placeholder:'Select',
		multiple: false,
		width: 300
	});
	select.val('').find('option:not(:first)').remove();
	var url = 'getSchoolsForUser.htm';
	var data = {};
	if (typeof(districtId) != 'undefined'){
		if (districtId == ''){
			return;
		}
		url = 'getSchoolsInDistrict.htm';
		data.districtId = districtId;
	}
	$.ajax({
		url : url,
		data: data,
		dataType: 'json',
		type: 'GET'
	}).done(function(data) {				
			if (data !== undefined && data !== null && data.length > 0){
        		populateOrgSelect(select, data);
        	} else {
        		// error
        	}
	});
}

function populateOrgSelect($select, orgs){
	for (var i = 0; i < orgs.length; i++){
		$select.append($('<option>', {value: orgs[i].id, text: orgs[i].organizationName}));
	}
	if (orgs.length == 1){
		$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
		$select.trigger('change');
	} else {
		$select.prop('disabled', false);
	}
}

function getExtractFile(response, fileName) {
    var extractFile = null;
    var makeTextFile = function (data) {
        var data = new Blob([data], { type: 'text/csv' });

        // If we are replacing a previously generated file we need to
        // manually revoke the object URL to avoid memory leaks.
        if (extractFile !== null) {
            window.URL.revokeObjectURL(extractFile);
        }
        extractFile = window.URL.createObjectURL(data);
        return extractFile;
    };
    
    var csv = Papa.unparse(JSON.stringify(response), extractConfig);
    if (navigator.msSaveOrOpenBlob) {
	     // Works for Internet Explorer and Microsoft Edge
    	 var blob = new Blob([csv], { type: 'text/csv' });
	     navigator.msSaveOrOpenBlob(blob, fileName);
	} else {
		var link = document.createElement('a');
	    link.setAttribute('download', fileName);
		link.href = makeTextFile(csv);
	    document.body.appendChild(link);

	    // wait for the link to be added to the document
	    window.requestAnimationFrame(function () {
	        var event = new MouseEvent('click');
	        link.dispatchEvent(event);
	        document.body.removeChild(link);
	    });		
	}    
}


function getExtract(stateid, districtid, schoolid, csvUrl, filename) {
	var orgData = {
			stateOrgId:stateid
	};
	if (districtid != null){
		if (districtid != ''){
			orgData.districtOrgId = districtid;
		}/*else{
			orgData.districtOrgId = userOrg;
		}*/
	}
	if (schoolid != null){
		orgData.schoolOrgId = schoolid;
	}
	 $.ajax({
			url: csvUrl,
			data: orgData,
			dataType: 'json',
			type: "GET"
	}).done(function(response) {
				getExtractFile(response, filename);
	});	
}

function naOrNumberFormatter(cellValue, option, rowObject) {
	if (cellValue == 'n/a')
		return cellValue;
	else 
		return $.fmatter.util.NumberFormat(cellValue, {thousandsSeparator: ",", defaultValue: '0'});
}