var searchController = can.Control({
	defaults : {},
}, {
	'init' : function() {
		$('#search').draggable({
			containment : "window",
			start : function() {
				tool.getOnTop($('#search'));
			}
		});
	},
	
	'#search .tool-hdr a.tool-close click' : function() {
		$("#searchtool").toggleClass("selected");
		$('#search').remove();
	},
	
	'#clearSearch click' : function() {
		search.unhighlight($('#tde-content'));
		$('#search ul.search-inputs input').val('');
	},
	
	'#search ul.search-inputs button click' : function(){
		var search_array = $('#search ul.search-inputs input')
				.val().split(",");
		search.highlight($('#tde-content'),search_array, {
			"caseSensitive" : false,
			"wordsOnly" : false
		});
		$('#search ul.search-inputs input').blur();
	},
	
	'#search ul.search-inputs input keypress' : function(el, event) {
		if (event.keyCode == 13) {
			var search_array = $(
					'#search ul.search-inputs input').val()
					.split(",");
			search.highlight($('#tde-content'),search_array, {
				"caseSensitive" : false,
				"wordsOnly" : false
			});
			$('#search ul.search-inputs input').blur();
		}
	},
	
	'#search ul.search-inputs input click' : function(el, ev) {
		this.focusSearchBox();
		ev.stopPropagation();
	},
	
	'#search .tool-container click' : function() {
		this.focusSearchBox();
	},
	
	focusSearchBox : function() {
		$('#search ul.search-inputs input').focus();
		tool.getOnTop($('#search'));
	}
});