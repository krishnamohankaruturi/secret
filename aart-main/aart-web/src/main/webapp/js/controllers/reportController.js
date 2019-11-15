var Report = can.Model.extend({
	findOne : function(params) {
	    return $.ajax({
			url: 'getNewReport.htm',
			dataType: 'json',
			data: params,
			type: "POST"
		});
	}
},{});

var reportController = can.Control({
	defaults : {
		webRootContext : '',
		currentControlInstance : null
	}
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		$('#reportsTabs').tabs();
		this.reportFilter = new reportFilter('#reportFilter', options);
		this.report = {}; 
	},
	// this event fires after the 'click' in reportFilter.js, because of the reportFilter() call in init
	'#btnReset click': function(el, ev) {
		ev.preventDefault();
		this.report = null;
		if(this.currentControlInstance) {
			this.currentControlInstance.destroy();
		}
	},
	// this event fires before the 'click' in reportFilter.js, because of the reportFilter() call in init
	'#viewReport click' : function(el, ev){
		this.element.find('#reportViewport').html('');
	},
	// this event fires after the 'click' in reportFilter.js, because of the reportFilter() call in init
	'#viewReport selected' : function(el, ev, report){
		ev.preventDefault();
		this.report = report;
		if(this.currentControlInstance) {
			this.currentControlInstance.destroy();
		}
		this.currentControlInstance = this._lookUpController();
	 },
	_lookUpController: function() {
		var reportType = this.report.reportType;  
		if(reportType == 'PARENT_REPORT'){
			return new parentReport('#reportViewport',{report: this.report});
		}else if(reportType == 'STUDENT_REPORT'){
			return new studentReport('#reportViewport',{report: this.report});
		}else if(reportType == 'ROSTER_REPORT'){
			/**
			 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
			 * Build Roster Report for DLM organizations.
			 */
			return new rosterReportOnline('#reportViewport',{report: this.report});
		}else if(reportType == 'NOT_IMPLEMENTED_REPORT'){
			return new notImplementedReport('#reportViewport',{report: this.report});
		}else{
			return new rosterReport('#reportViewport',{report: this.report});
		}
	}
});

$.fn.sortOptions = function(){
    // Get options from select box
    var my_options = $("#" + this.attr('id') + ' option');
    
    var reA = /[^a-zA-Z]/g;
    var reN = /[^0-9]/g;
    // sort alphabetically
    my_options.sort(function(a,b) {
        var aA = a.text.replace(reA, "");
        var bA = b.text.replace(reA, "");
        if(aA === bA) {
            var aN = parseInt(a.text.replace(reN, ""), 10);
            var bN = parseInt(b.text.replace(reN, ""), 10);
            return aN === bN ? 0 : aN > bN ? 1 : -1;
        } else {
            return aA > bA ? 1 : -1;
        }
    });
    
   //replace with sorted my_options;
   $(this).empty().append( my_options );

   // clearing any selections
   return this;
};
