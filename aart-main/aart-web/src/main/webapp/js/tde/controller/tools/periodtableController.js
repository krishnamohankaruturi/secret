var periodictableController = can.Control({
	defaults : {},
}, {
	'init' : function() {
		$("#pt").draggable({
			containment : "window",
			start : function() {
				tool.getOnTop($('#pt'));
			}
		});
		
		$("#pTElem").draggable({
			containment : "#pt",
			start : function() {
				tool.getOnTop($('#pt'));
			}
		});
	},
	
	 '#ptCloser click' : function() {
		 $("#periodictool").removeClass("selected");
		 $('#pt').remove();
	 },
	 
	 '#ptElemCloser click' : function() {
		 $('#pTElem').hide();
	 },
	 
	 '#pt div.icon click' : function(el,ev) {
		 var elem = /[a-zA-Z]+/.exec($(el).text())[0];
		 periodicTable.showElement(elem);
	 },
	 
	 '#pt .tool-container' : function() {
		 tool.getOnTop($('#pt'));
	 }
});