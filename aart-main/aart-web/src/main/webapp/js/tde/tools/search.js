var search = (function() {
	var settings = {
			className : 'search_highlight',
			element : 'span',
			caseSensitive : null,
			wordsOnly : null
		};
	
	function highlight(node, words, options) {
		settings.caseSensitive = options.caseSensitive;
		settings.wordsOnly = options.wordsOnly;
			
			for ( var i = 0; i < words.length; i++) {
				words[i] = words[i].replace(/^\s+/, "");
				words[i] = words[i].replace(/\s+$/, ""); 
				if(words[i] == ""){
					words.splice(i,1);
				}
			}

			if (words.constructor === String) {
				words = [ words ];
			}

			words = jQuery.grep(words, function(word, i) {
				return word != '';
			});

			words = jQuery.map(words, function(word, i) {
				return word.trim().replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
			});
			if (words.length == 0) {
				return this;
			}

			var flag = settings.caseSensitive ? "" : "i"+"g", pattern = "("
					+ words.join("|") + ")";

			if (settings.wordsOnly) {
				pattern = "\\b" + pattern + "\\b";
			}
			
			var re = new RegExp(pattern, flag);
			
			var span = document.createElement('span');
			span.className = settings.className;

			node.each(function() {
				findAndReplaceDOMText(re, $(this).get(0), span, null, null);
			});
		
		
	}
	
	function unhighlight(node) {
		$(node.find(settings.element + "." + settings.className).get().reverse()).each(function(){
			if(!this.parentNode) {
				return;
			}
			
			if ($(this).attr('class').split(' ').length > 1) {
				$(this).removeClass(settings.className);
			} else {
				$(this).contents().unwrap();
			}
		}).end();
	}
	
	return {
		highlight : highlight,
		unhighlight : unhighlight
	};
	
})();
