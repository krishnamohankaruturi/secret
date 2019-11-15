var studentReport = can.Control({
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		this._renderReport();
	},
	_renderReport: function() {
		can.view.render('js/views/studentReport.ejs', {reportData: this.options.report},function(fragment){
			$('#altReportDisplayDiv').html(fragment);
				$('#altReportDisplayDiv').show();
			});
	},	
});