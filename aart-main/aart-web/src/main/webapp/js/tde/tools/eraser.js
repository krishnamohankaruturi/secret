/* 
 * highlighter.js
 * 
 * Tool for highlighting words to add a given class.
 * 
 */
var Eraser = new function() {
	var mode;
	
	this.isMode = function(newMode) {
		return mode == newMode;
	};
	
	this.activate = function(selector, newMode, classToRemove) {
		test.removePointerCursor();
		$('.w').addClass('eraserCursor');
		mode = newMode;
		// Turn off the previous mode.
		$(selector).off();
		
		$.support.touch = 'ontouchend' in document;
		if ($.support.touch) {
			$(selector).on("touchend", function() {
				erase(newMode, classToRemove);
			});
		} else {
			$(selector).on("mouseup mousedown", function() {
				erase(newMode, classToRemove);
			});
		}
		
		$('#tde-content').on("click", "img", imgErase);
	};

	this.deactivate = function(selector) {
		$(selector).off();
		mode = "";
		test.defaultCursor();
		test.addPointerCursor();
		$('#tde-content').off("click", "img", imgErase);
	};
	
	function erase(newMode, classToRemove) {
		
		rangy.init();
		var parentElement='';
		var cssClassApplierModule = rangy.modules.CssClassApplier;
		if (rangy.supported && cssClassApplierModule && cssClassApplierModule.supported) {
	        cssApplier = rangy.createCssClassApplier(newMode, true, ["span"]);
        	cssApplier.undoToSelection();
			
			var sel = rangy.getSelection();
			//This block will be used to get the Parent Element of the Selection.
			if (sel.rangeCount > 0) {
			    var range = sel.getRangeAt(0);
			    parentElement = range.commonAncestorContainer;
			    if (parentElement != null && typeof parentElement != 'undefined' && parentElement.nodeType == 3) {
			        parentElement = parentElement.parentNode;
			        
			        if(!$(parentElement).hasClass('foil') && !$(parentElement).hasClass('strikeDiv')) {
			        	do {
			        		if($(parentElement).is($('#tde-content'))) {
			        			break;
			        		}
			        		parentElement = parentElement.parentNode;
			        	} while(!$(parentElement).hasClass('foil') && !$(parentElement).hasClass('strikeDiv'));
			        }
			    }
			}
			sel.removeAllRanges();
			
			var $parentELement=$(parentElement);

			if($parentELement.hasClass(classToRemove)) {
				$parentELement.removeClass(classToRemove);
			}
						
			if($parentELement.hasClass('highlight')){
				$parentELement=$parentELement.closest('foil');
			}else if(!$parentELement.hasClass('foil')){
				$parentELement=$parentELement.closest('foil');
			}
			
			striker.removeStrike($(parentElement).parent());
			
			if(tasks[tde.testparam.currentQuestion].taskType != 'ITP' && tasks[tde.testparam.currentQuestion].taskType != 'CR' && tasks[tde.testparam.currentQuestion].taskType != 'ER') {
				domToTestObj.cache(parentElement);
			}
			
		}
		magnifyingGlass.resetMagnifyingGlass();
	}
	
	function imgErase (){		
		var $parentELement = $(this).parent();
		if(!$parentELement.hasClass('foil') && !$parentELement.hasClass('strikeDiv')) {
        	do {
        		if($parentELement.is($('#tde-content'))) {
        			break;
        		}
        		$parentELement = $parentELement.parent();
        	} while(!$parentELement.hasClass('foil') && !$parentELement.hasClass('strikeDiv'));
        }
		 
		if($parentELement.hasClass('highlight')){
			$parentELement=$parentELement.closest('foil');
		}else if(!$parentELement.hasClass('foil')){
			$parentELement=$parentELement.closest('foil');
		}
		
		var foilElement = $parentELement.parent();
		striker.removeStrike(foilElement);
		if(tasks[tde.testparam.currentQuestion].taskType != 'ITP' && tasks[tde.testparam.currentQuestion].taskType != 'CR' && tasks[tde.testparam.currentQuestion].taskType != 'ER') {
			domToTestObj.cache(foilElement[0]);
		}
	}
};
