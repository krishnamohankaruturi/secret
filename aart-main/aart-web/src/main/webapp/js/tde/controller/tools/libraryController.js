var libraryController = can.Control({
	defaults : {},
}, {
	'init' : function() {
		$('#library').draggable({
			containment : "window",
			start : function() {
				tool.getOnTop($('#library'));
			}
		});
		
		$('#library').resizable({
			minHeight: 200,
			minWidth : 400,
			stop : function(event, ui) {
				var newHeight = ui.size.height;
				$('#library .viewarea').height(newHeight - 45);
				
				$('#library .viewarea object').height(newHeight - 45);
				$('#library .viewarea object').width($('#library .viewarea').width());
			}
		});
	},
	
	'#library .tool-close click' : function() {
		$('#librarytool').removeClass('selected');
		$('#library').remove();
	},
	
	'#libraryselect change' : function(el) {
		var currentSection = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]];
		var attachment = tde.config.mediaUrl + currentSection.resources[$(el).find("option:selected").val()].attachmentLocation.replace(/\s/g,'%20');
		var viewArea = null;
		
		if (Modernizr.touch) {
			$('#library .viewarea').jScrollPane({autoReinitialise: true});
			viewArea = $('#library .viewarea .jspPane');
		} else {
			viewArea = $('#library .viewarea');
		}
		
		viewArea.html('');
		if(!/\.(pdf)$/i.test(attachment) && !/\.(html)$/i.test(attachment)) {
			viewArea.append('<img src="'+attachment+'">');
		} else if(/\.(html)$/i.test(attachment)) {
			viewArea.load(attachment, function(responseText, statusText, xhr){
				if(statusText == "success") {
					if (!Modernizr.touch) {
						$('#library .viewarea').scrollTop(0);
					} else {
						$('#library .viewarea').data('jsp').scrollTo(0,0);
					}
				}else if(statusText == "error") {
	                    console.log("An error occurred: " + xhr.status + " - " + xhr.statusText);
	            }
	    	});
		} else {
			var pdfObj = $('<object type="application/pdf"></object>');
			pdfObj.attr('data', attachment);
			pdfObj.attr('width', viewArea.width());
			pdfObj.attr('height', viewArea.height());
			viewArea.append(pdfObj);
		}
	},
	
	'#library .tool-container click' : function() {
		tool.getOnTop($('#library'));
	}
	
});