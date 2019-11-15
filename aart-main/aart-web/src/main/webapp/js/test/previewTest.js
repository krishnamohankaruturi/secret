var jsList = {};
function loadItemType(taskSubTypeCode, itemData, containerId) {
	if (taskSubTypeCode == 'ML') {
		renderCorrectResponseML(itemData, containerId);
	} else if (taskSubTypeCode == 'SD') {
		renderCorrectResponseSD(itemData, containerId);
	} else if (taskSubTypeCode == 'MDB') {
		renderCorrectResponseMDB(itemData, containerId);
	} else if (taskSubTypeCode == 'BG') {
		renderCorrectResponseBG(itemData, containerId);
	} else if (taskSubTypeCode == 'BG-UL') {
		renderCorrectResponseBGUL(itemData, containerId);
	} else if (taskSubTypeCode == 'ORD') {
		renderCorrectResponseORD(itemData, containerId);
	} else if (taskSubTypeCode == 'ORD-LR') {
		renderCorrectResponseORD_LR(itemData, containerId);
	} else if (taskSubTypeCode == 'S1L') {
		renderCorrectResponseS1L(itemData, containerId);
	} else if (taskSubTypeCode == 'MCRB') {
		renderCorrectResponseMCRB(itemData, containerId);
	} else if (taskSubTypeCode == 'VD') {
		renderCorrectResponseVD(itemData, containerId);
	} else if (taskSubTypeCode == 'POS') {
		renderCorrectResponsePOS(itemData, containerId);
	} else if (taskSubTypeCode == 'EDIT') {
		renderCorrectResponseEDIT(itemData, containerId);
	} else if (taskSubTypeCode == 'PS') {
		renderCorrectResponsePS(itemData, containerId);
	} else if (taskSubTypeCode == 'LAB') {
		renderCorrectResponseLAB(itemData, containerId);
	} else if (taskSubTypeCode == 'LAB-T') {
		renderCorrectResponseLAB_T(itemData, containerId);
	} else if (taskSubTypeCode == 'LAB-CT') {
		renderCorrectResponseLAB_CT(itemData, containerId);
	} else if (taskSubTypeCode == 'LAB-C') {
		renderCorrectResponseLAB_C(itemData, containerId);
	} else if (taskSubTypeCode == 'LAB-TM') {
		renderCorrectResponseLAB_TM(itemData, containerId);
	} else if (taskSubTypeCode == 'DD') {
		renderCorrectResponseDD(itemData, containerId);
	} else if (taskSubTypeCode == 'ST') {
		renderCorrectResponseST(itemData, containerId);
	} else if (taskSubTypeCode == 'PP') {
		renderCorrectResponsePP(itemData, containerId);
	} else if (taskSubTypeCode == 'SL') {
		renderCorrectResponseSL(itemData, containerId);
	} else if (taskSubTypeCode == 'DD-G') {
		renderCorrectResponseDDG(itemData, containerId);
	} else if (taskSubTypeCode == 'SELTXT') {
		renderCorrectResponseSELTXT(itemData, containerId);
	} else if (taskSubTypeCode == 'GEN') {
		// not implemented
	}
}

function renderCorrectResponseML(itemData, containerId) {
	var rowList = itemData.correctResponse.rowList;
	var stateData = [];
	$.each(rowList, function(key, value) {
		stateData.push({
			"srcid" : value.leftId,
			"destid" : value.textId,
			"color" : 'black'
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseDDG(itemData, containerId) {
	var dropList = itemData.correctResponse.dropList;
	var stateData = [];
	$.each(dropList, function(key, value) {
		var responsesList = value.responses;
		$.each(responsesList, function(key, dropvalue) {
			stateData.push({
				"dropId" : value.id,
				"foilId" : dropvalue,
				"dropContainerId" : value.id
			});
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseSD(itemData, containerId) {
	var dropList = itemData.correctResponse.dropList;
	var stateData = [];
	var listresp = [];
	$.each(dropList, function(key, value) {
		var responses = value.responses;
		$.each(responses, function(key, respvalue) {
			listresp.push({
				"id" : respvalue
			});
		});
		stateData.push({
			"id" : value.id,
			"options" : listresp
		});
		if (listresp.length == responses.length)
			listresp = [];
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseMDB(itemData, containerId) {
	var stateData = [];
	var listdata = [];
	var dropList = itemData.correctResponse.dropList;
	$.each(dropList, function(key, value) {
		var responses = value.responses;
		$.each(responses, function(key, respvalue) {
			listdata.push({
				"id" : respvalue
			});
		});
		stateData.push({
			"id" : value.id,
			"options" : listdata
		});
		if (listdata.length == responses.length)
			listdata = [];
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseBG(itemData, containerId) {
	var dropList = itemData.correctResponse;
	var stateData = [];
	$.each(dropList, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseBGUL(itemData, containerId) {
	var dropList = itemData.correctResponse;
	var stateData = [];
	$.each(dropList, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseORD(itemData, containerId) {
	var ids = itemData.correctResponse;
	var dragList = itemData.dragList;
	var stateData = [];
	var listData = [];
	$.each(dragList, function(key, respvalue) {
		$.each(ids, function(key, value) {
			listData.push({
				"id" : value.id,
				"index" : value.index
			});
		});
		stateData.push({
			"id" : respvalue.id,
			"options" : listData
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseORD_LR(itemData, containerId) {
	var stateData = [];
	var listData = [];
	var ids = itemData.correctResponse;

	$.each(ids, function(key, listvalue) {
		listData.push({
			"id" : listvalue.id,
			"index" : listvalue.index
		});
	});
	stateData.push({
		"id" : "rearrangeList",
		"options" : listData
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseS1L(itemData, containerId) {
	var corrrespval = itemData.correctResponse;
	var start = itemData.correctResponse.start;
	var end = itemData.correctResponse.end;
	var xIntercept = itemData.correctResponse.xIntercept;
	var yIntercept = itemData.correctResponse.yIntercept;
	var slope = itemData.correctResponse.slope;
	var stateData = [];
	var startList = [];
	var endList = [];
	startList.push(start.x, start.y);
	var stl = startList.map(Number);
	endList.push(end.x, end.y);
	var elt = endList.map(Number);
	stateData.push({
		"start" : stl,
		"end" : elt,
		"slope" : slope.min,
		"xIntercept" : xIntercept.min,
		"yIntercept" : yIntercept.max
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseMCRB(itemData, containerId) {
	var correctResponses = itemData.correctResponse;
	$.each(correctResponses, function(key, value) {
		var foilId = value.foilId;
		$.each(value.options, function(keyOption, valueOption) {
			var elementId = "#" + valueOption;
			var observer = new MutationObserver(function(mutations) {
		      if ($(elementId).length > 0){
					// To render the correct responses if they have customized styles.
		    	  	if($('[name='+foilId+']'+elementId).next().length > 0 && 
		    	  			$('[name='+foilId+']'+elementId).next().hasClass('tabable')){
						setTimeout(function(){ $('#'+ ($('[name='+foilId+']'+elementId).next().attr('id'))).click(); }, 3000);
						
						observer.disconnect();
		    	  	} else if ($('[name='+foilId+']'+elementId).attr('id')){
		    	  		$('[name='+foilId+']'+elementId).click();
				        observer.disconnect();
		    	  	}
			    } else if ($('[name='+foilId+']').length > 0) {
			    	// To render the correct responses if they have customized styles.
			    	var responseDOMObject = $('[name='+foilId+']').parent().find('[data-inputid='+valueOption+']');
			    	if(responseDOMObject.length > 0){
			    		if(responseDOMObject.hasClass('tabable')){
			    			setTimeout(function(){ $('[name='+foilId+']').parent().find('[data-inputid='+valueOption+']').click(); }, 3000);
							observer.disconnect();
			    		}else{
			    			responseDOMObject.prev('input').click();
					        observer.disconnect();
			    		}
			    	}
			    }else {
			    	console.log('observer not found element: ' + elementId);
			    }
			});
			observer.observe(document, {attributes: false, childList: true, characterData: false, subtree:true});
		});
	});
}
function renderCorrectResponseVD(itemData, containerId) {
	var dropList = itemData.correctResponse.dropList;
	var rowList = itemData.rowList;
	var stateData = [];
	var responsedata = [];
	$.each(dropList, function(key, value) {
		$.each(value.responses, function(key, responseId) {
			responsedata.push({
				"id" : responseId
			});
		});
		stateData.push({
			"id" : value.id,
			options : responsedata
		});
		if (responsedata.length == value.responses.length)
			responsedata = [];
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponsePOS(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var parentId = itemData.uuid;
	var numberOfSegments = parseInt(correctResponse.numberOfSegments);
	var numberOfSelections = parseInt(correctResponse.numberOfSelections);
	var shape = itemData.shape === 'circle' ? 'circle': 'rectangle';
	if ($(containerId).find("#reviewContent").has("#addVLine")) {
		for (var i = 1; i <= (numberOfSegments-1); i++) {
			$(containerId).find(".partition-container-"+ shape +" #addVLine").click();
		}
	} else {
		for (var i = 1; i <= (numberOfSegments-1); i++) {
			$(containerId).find(".partition-container-"+ shape +" #addHLine").click();
		}
	}
	$(containerId + " .partition-container-"+ shape).find("tbody tr").children().each(function(index, value) {
		if (index < numberOfSelections) {
			$(containerId).find(".partition-container-"+ shape +" #" + $(this).attr('id')).addClass('filled');
		}
	});
}

function renderCorrectResponseEDIT(itemData, containerId) {
	var stateData = [];
	var correctResponse = itemData.correctResponse;
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"foilId" : value.foilId,
			"dragId" : value.dragId
		});
	});

	waitForElementsRendering(itemData, stateData);
}
function renderCorrectResponsePS(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"rowHeaders" : value.rowHeaders,
			"columnHeaders" : value.columnHeaders,
			"value" : value.value,
			"rowIndex" : value.rowIndex,
			"columnIndex" : value.columnIndex
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseLAB(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseLAB_T(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseLAB_CT(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseLAB_C(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseLAB_TM(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	$.each(correctResponse, function(key, value) {
		stateData.push({
			"dropId" : value.dropId,
			"foilId" : value.foilId
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseDD(itemData, containerId) {
	var foillist = itemData.foils;
	var ids = itemData.correctResponse;
	var stateData = [];
	var menulist = [];
	$.each(ids, function(key, menuvalue) {
		menulist.push(menuvalue.foilId);
		stateData.push({
			"foilId" : menuvalue.foilId,
			"value" : menuvalue.value,
			"menuIndex" : menulist.indexOf(menuvalue.foilId)
		});
	});
	waitForElementsRendering(itemData, stateData);
}


function renderCorrectResponseST(itemData, containerId) {
	var responses = itemData.correctResponse.responses;
	var stateData = [];
	$.each(responses, function(key, value) {
		stateData.push(value);
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponsePP(itemData, containerId) {
	var correctResponse = itemData.correctResponse;
	var stateData = [];
	nwArray = [];
	for (i = 0; i < correctResponse.length; i++) {
		nwArray.push(correctResponse[i].x);
		nwArray.push(correctResponse[i].y);
		nwArray.push(correctResponse[i].tolerance);
		stateData.push(nwArray);
		if (nwArray.length == 3)
			nwArray = [];
	}

	waitForElementsRendering(itemData, stateData);
}

function drawCircle(ctx, xAxis, yAxis) {
	ctx.beginPath();
	ctx.arc(xAxis, yAxis, 8, 0, Math.PI * 2, true);
	ctx.closePath();
	ctx.fillStyle = '#7fbf7f';
	ctx.fill();
}

function renderCorrectResponseSL(itemData, containerId) {
	var lines = itemData.correctResponse.lines;
	var stateData = [];

	$.each(lines, function(key, value) {
		var start = [ value.start.x, value.start.y ];
		var end = [ value.end.x, value.end.y ];
		var slope = ((value.end.y - value.start.y) / (value.end.x - value.start.x)), yintercept, xintercept;

		if (slope === 0) {
			yintercept = value.start.y;
			xintercept = -yintercept / slope;
		} else if (!isFinite(slope)) {
			yintercept = slope;
			xintercept = value.start.x;
		} else {
			yintercept = value.start.y - (slope * value.start.x);
			xintercept = -yintercept / slope;
		}
		stateData.push({
			"start" : start,
			"end" : end,
			"xIntercept" : xintercept,
			"yIntercept" : yintercept,
			"slope" : slope
		});
	});

	waitForElementsRendering(itemData, stateData);
}

function renderCorrectResponseSELTXT(itemData, containerId) {
	var response = itemData.correctResponse.responses;
	var stateData = [];
	$.each(response, function(key, value) {
		stateData.push(value);

	});

	waitForElementsRendering(itemData, stateData);
}

function waitForElementsRendering(itemData, stateData){
	var uuid = itemData.uuid;
	var elementId = "#" + itemData.uuid;
	var observer = new MutationObserver(function(mutations) {
      if ($(elementId).length > 0){
    	  if(jsList[uuid] != null ){
    		  jsList[uuid].initItem(uuid, stateData);
        	  observer.disconnect();
    	  }else{
    		  console.log('operationalJS is not defined: ' + uuid);
    	  }
	    } else {
	    	console.log('observer not found element: ' + elementId);
	    }
	});
	if($(elementId).length > 0 && jsList[uuid] != null ){
		  jsList[uuid].initItem(uuid, stateData);
	}else{
		observer.observe(document, {attributes: false, childList: true, characterData: false, subtree:true});
	}
	
}

$.extend({
	getUrlVars : function() {
		var vars = [], hash;
		var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		for (var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=');
			vars.push(hash[0]);
			vars[hash[0]] = hash[1];
		}
		return vars;
	},
	getUrlVar : function(name) {
		return $.getUrlVars()[name];
	}
});

function loadItem(index) {
	try {
		var sourceUrl = itpDivs.eq(index).attr('value');
		var taskId = itpDivs.eq(index).attr('id');
		var taskSubType = itpDivs.eq(index).attr('data-task-subtype');

		$.getScript(sourceUrl.replace('index.html', 'js/itemScript.js')).done(
				function(script, textStatus) {
					$('<link class="itpCss" rel="stylesheet" type="text/css" href="'
						+ sourceUrl.replace('index.html', 'css/itemStyles.css') + '" >').appendTo('head');
					$('#itpContent_' + taskId).load(sourceUrl, function(responseText, statusText, xhr) {
						try {
							if (statusText == 'success') {
								$.getJSON(sourceUrl.replace('index.html', 'js/itemData.json')).done(function(itemData) {
									try {
										var uuid = itemData.uuid;
										jsList[uuid] = operationalJS;
										
										loadItemType(taskSubType, itemData, ('#itpContent_' + taskId));
										$(this).removeAttr('task').removeAttr('itp-loc');
										// override image zone width
										$('#imagezone').css('width', 'auto');
										
										if (index < (itpDivs.length - 1)) {
											loadItem(index + 1);
										} else {
											// Find mathMl rows that contain bare text and wrap them in <mi> tags. Prevents 'unexpected text node' errors
											$('mrow').contents().filter(function(){ 
												// nodeType === 3 selects text from the <mrow> that isn't part of another html tag. nodetype 3 is the text node.
												return (this.nodeType == 3 && this.nodeValue.trim() !== ''); 
												}).wrap( "<mi></mi>" );
											// Rerender any MathJax (that may have been added by an itemscript)
											MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
											delete window.itpDivs;
										}
									} catch (exception) {
										console.log('1) got error ' + exception);
									}
								}).fail(function( jqxhr, textStatus, error ) {
								    var err = textStatus + ", " + error;
								    console.log( "Request Failed: " + err );
								});
							} else if (statusText == 'error') {
								switch (xhr.status) {
							    case 404:
							     $('#itpContent_' + taskId).text('Html Resource not available.');
							     break;
							    default:
							    	$('#itpContent_' + taskId).text('Html Resource not available');
							     break;
							   }
							}
						} catch (exception) {
							console.log('2) got error : ' + exception);
						}
					});
				}).fail(function( jqxhr, settings, exception ) {
				    $('#itpContent_' + taskId).text('Script Resource not available.');
				});
	} catch (exception) {
		console.log('3) got error : ' + exception);
	}
}

function loadItems() {
	window.itpDivs = $('body').find('.itp-element');
	if (window.itpDivs && window.itpDivs.length > 0) {
		loadItem(0);
	}
}