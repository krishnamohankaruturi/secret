/* 
 * postit.js
 * 
 * Tool for adding Postit Notes.
 * 
 */
var postIt = new function() {
	var seq = 0;
	var mode;
	
	this.isMode = function(newMode) {
		return mode == newMode;
	};
	
	this.getSeq = function(){
		return seq;
	};
	
	this.activate = function(selector, newMode) {
		mode = newMode;
		var txtarea = $('<textarea>').attr('id', 'postit-'+ seq);
		$('#postIt').clone(true)
					.attr('id', 'clonedPostIt-'+seq)
					.prependTo('#tool-arena')
					.show()
					/*.draggable({ cancel: '#clonedPostIt-' + seq +' div',
								 stop : function(){
									 $(this).children('div:last').children('div:first').blur();
								 },
								 containment: "#tde-content"
					})*/
					//.draggable({ handle: "#postitdrag" })
					.resizable({
						minHeight: 220,
						minWidth : 200,
						stop : function(event, ui) {
							console.log("resizing");
							var newWidth = ui.size.width;
							var newHeight = ui.size.height;
							/*var nicEditorDiv = $(this).children('div:last');
							var niceEditorContentDiv = $(nicEditorDiv).children('div:first');
							$(niceEditorContentDiv).blur();
							$(niceEditorContentDiv).width((newWidth-8));
							var contentHeight = $(niceEditorContentDiv).height();
							if((newHeight-40) < contentHeight) {
								$(nicEditorDiv).height(contentHeight);
								$(this).height((contentHeight+40));
							} else {
								$(nicEditorDiv).height(newHeight-40);
							}*/
							//$(niceEditorContentDiv).focus();
							
							var textarea = $(this).find('textarea');
							textarea.width(newWidth - 6);
							textarea.height(newHeight - 20);
						}
					});
		
		if(!/ipad/ig.test(userAgent)){
			$('#clonedPostIt-'+seq).draggable({ cancel: '#clonedPostIt-' + seq +' div',
				 stop : function(){
					 $('#clonedPostIt-'+seq).children('div:last').children('div:first').blur();
				 },
				 containment: '#tde-content'
			});
		} else {
			$('#clonedPostIt-'+seq).draggable({ handle: "#postitdrag",
												containment: "#tde-content"});
		}
		
		$(txtarea).appendTo($('#clonedPostIt-'+seq));
		//niceedit.create($(txtarea).attr('id'));
		$('#clonedPostIt-'+seq).on('click', function(){
			tool.getOnTop($(this));
			$(this).find('textarea').focus();
		});

		tde.testparam.postItNotes.push({
			"id": 'clonedPostIt-' + seq,
			"sequence": seq,
			"content":"",
			"group": task.getCurrentGroup()
		});
		seq++;
	};
	
	this.deactivate = function(selector) {
		$(selector).die();
		mode = "";
	};
	
	this.displayNote = function(noteId) {
		$('#' + noteId).show();
	};
	
	this.hideNotes = function() {
		for (var i=0;i<tde.testparam.postItNotes.length;i++) {
			$('#' + tde.testparam.postItNotes[i].id).hide();
		}
	};
	
	/*this.replaceNote = function(note) {
		
		var txtarea = $('<textarea>').attr('id', note.id);
		var splt_xpath = note.tagBefore.split(/\/form\/div\/div\/div\[\d\]/);
		
		var tags = splt_xpath[1].match(/\w+/ig);
		
		var element_bfr = $(tde.config.tool_selectors.postIt);
		for(var i=0; i<tags.length ; i++) {
			element_bfr = element_bfr.find(tags[i]+':nth-child('+tags[i+1]+')');
			i++;
		}
		
		$(txtarea).insertAfter(element_bfr);
		$(txtarea).val(note.content);
		//$(splt_xpath[0]+'/form').find('#passageText').find(':nth-child(3)')[0].childNodes[4]   /////$(tde.config.tool_selectors.postIt).find('p:nth-child(3)').find('span:nth-child(4)')
		niceedit.create(note.id);
	};*/
	
	this.deleteNote = function (currentGroup){
		for (var i=0;i<tde.testparam.postItNotes.length;i++) {
			if (tde.testparam.postItNotes[i].group != null && tde.testparam.postItNotes[i].group == currentGroup) {
				$('#'+ tde.testparam.postItNotes[i].id).remove();
				tde.testparam.postItNotes.splice(i,1);
			}
		}
	};
	
	/**
	 * Gets an XPath for an element which describes its hierarchical location.
	 */
	/*this.getElementXPath = function(element)
	{
	    if (element && element.id)
	        return '//*[@id="' + element.id + '"]';
	    else
	        return this.getElementTreeXPath(element);
	};*/

	/*this.getElementTreeXPath = function(element)
	{
	    var paths = [];

	    // Use nodeName (instead of localName) so namespace prefix is included (if any).
	    for (; element && element.nodeType == 1; element = element.parentNode)
	    {
	        var index = 0;
	        for (var sibling = element.previousSibling; sibling; sibling = sibling.previousSibling)
	        {
	            // Ignore document type declaration.
	            if (sibling.nodeType == Node.DOCUMENT_TYPE_NODE)
	                continue;

	            if (sibling.nodeName == element.nodeName)
	                ++index;
	        }

	        var tagName = element.nodeName.toLowerCase();
	        var pathIndex = (index ? "[" + (index+1) + "]" : "");
	        paths.splice(0, 0, tagName + pathIndex);
	    }

	    return paths.length ? "/" + paths.join("/") : null;
	};*/
	
	this.setDimensions = function(noteid) {
		$(tde.testparam.postItNotes).each(function(){
			if(noteid == $(this).attr('id')) {
				$(this).attr({'height':$('#'+noteid).height() , 'width':$('#'+noteid).width()}); 
			}
		});
	};
	this.getDimensions = function(noteid) {
		var ret;
		$(tde.testparam.postItNotes).each(function(){
			if($(this).attr('id') == noteid) {
				ret = {'height':$(this).attr('height'), 'width':$(this).attr('width')};
			}
		});
		return ret;
	};
};
