/* 
 * highlighter.js
 * 
 * Tool for highlighting words to add a given class.
 * 
 */
var highLighter = new function() {
	var mode="";
	
	this.isMode = function(newMode) {
		return mode == newMode;
	};
	
	this.activate = function(selector, newMode) {
		test.removePointerCursor();
		$('#tde-content').addClass('highlighterCursor');
		mode = newMode;
		// Turn off the previous mode.
		$(selector).die();
		//window.getSelection().getRangeAt(0).setStartAfter($('#tde-content'));
		$.support.touch = 'ontouchend' in document;

	    if ($.support.touch) {
		    $(selector).on("touchend", function() {
		    	highlight(newMode);
		    });
		} else {
			$(selector).on("mouseup", function() {
				highlight(newMode);
			});
		}
	};
	
	this.deactivate = function(selector) {
		$(selector).die();
		mode = "";
		test.defaultCursor();
		test.addPointerCursor();
	};
	
	function highlight(newMode) {
		rangy.init();
		var cssClassApplierModule = rangy.modules.CssClassApplier;
		if (rangy.supported && cssClassApplierModule && cssClassApplierModule.supported) {
			var textNodes = rangy.getSelection().getRangeAt(0).getNodes([3], function(node) {
			    return $(node).parents("#tde-content").length > 0 && !$(node).parent().hasClass('unselectable');
			});
			
			var sel = rangy.getSelection();
			var newRange = sel.getRangeAt(0).cloneRange();
//			for (var i = 0, textNode; textNode = textNodes[i++]; ) {
//			    console.log(textNode.data);
//			}
			
			newRange.setStartBefore(textNodes[0]);
			newRange.setEndAfter(textNodes[textNodes.length-1]);
			
			sel.setSingleRange(newRange);
			sel.getRangeAt(0).refresh();
	        cssApplier = rangy.createCssClassApplier(newMode, true, ["span"]);
	        cssApplier.applyToSelection();
	        
			var parentElement = sel.getRangeAt(0).commonAncestorContainer;
			sel.removeAllRanges();

			//alert($(parentElement).attr('class'));
			if(tasks[tde.testparam.currentQuestion].taskType != 'ITP' && tasks[tde.testparam.currentQuestion].taskType != 'CR' && tasks[tde.testparam.currentQuestion].taskType != 'ER') {
				if(parentElement != null && typeof parentElement != 'undefined') {
					domToTestObj.cache(parentElement);
				}
			}
		}
		magnifyingGlass.resetMagnifyingGlass();
	}
};
