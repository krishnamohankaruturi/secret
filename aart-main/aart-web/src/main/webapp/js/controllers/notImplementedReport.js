var notImplementedReport = can.Control({
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		this._renderReport();
	},
	_renderReport: function() {
		this.element.html(can.view('js/views/notImplementedReport.ejs', {reportData: this.options.report}));
	},
});