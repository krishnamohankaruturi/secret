/**
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
 * Build Roster Report for DLM organizations.
 */
var rosterReportOnline = can.Control({
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		this._renderReport();
	},
	_renderReport: function() {
			can.view.render('js/views/rosterReportOnline.ejs', {reportData: this.options.report},function(fragment){
			$('#reportDisplayDiv').html(fragment);
 			$('#reportDisplayDiv').show();
 		});
	},	
});