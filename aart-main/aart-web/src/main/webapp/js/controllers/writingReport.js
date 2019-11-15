var writingReport = can.Control({
}, {
	'init' : function(element, options) {
		this.options = options;
		this.element = element;
		this._renderReport();
	},
	_renderReport: function() {
		var me = this;
		can.view.render('js/views/writingReportView.ejs', {reportData: this.options.report}, function(fragment){
			me.element.html(fragment);
			
			$('#writingReportContent .response p').each(function(i){
				var $this = $(this);
				if (!/[^\s]/g.test($this.text())){
					$this.remove();
				}
			});
			
			$('#writingReportContent .response').each(function(i){
				var $this = $(this);
				var html = '<p>A written answer was not captured in the system.</p>';
				if (!/[^\s]/g.test($this.text())){
					$this.html(html);
				}
			});
			
			$('#writingReportContent .response [style]').each(function(i){
				$(this).removeAttr('style');
			});
			
			
			$('html, body').animate({
				scrollTop: $('#writingReportContent').offset().top - 100
			}, 300);
		});
	}	
});