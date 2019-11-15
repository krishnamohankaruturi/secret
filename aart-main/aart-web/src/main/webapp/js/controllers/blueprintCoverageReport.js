var blueprintCoverageReport = can.Control({
}, {
	'init' : function(element, options){
		this.options = options;
		this.element = element;
		this._renderReport();
	},
	_renderReport: function(){
		var me = this;
		var params = me.options.params;
		can.view.render('js/views/new_reports/blueprintCoverageContent.ejs', {reportData: this.options.report, params: params},function(fragment){
			me.initializeContent(fragment, params);
 		});
	},
	initializeContent: function(fragment, params){
		$('#blueprintCoverageReportDisplayDiv').html(fragment).show();
			
		$('.blueprintcoverage table', this.element).each(function(i){
			var table = $(this);
			var colModel = [{width: 100}, {width: 120}, {width: 210}];
			
			var numberOfStudentColumns = $('thead tr', table).first().find('th.studentheader').length;
			
			for (var x = 0; x < numberOfStudentColumns; x++){
				colModel.push({width: 110});
			}
			
			var tableOuterHeight = table.outerHeight(false) + 25; // give some extra room
			var defaultHeight = 400;
			
			table.fxdHdrCol({
				fixedCols: 3,
				sort: false,
				width: '100%',
				height: (tableOuterHeight > defaultHeight ? defaultHeight : tableOuterHeight),
				colModel: colModel,
			});
		});
			
		$('#saveBlueprintCoveragePDF', this.element).on('click', function(){
			window.location = 'getAlternateBlueprintCoveragePDF.htm?' + $.param(params);
		});
	}
});