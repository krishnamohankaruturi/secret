var tdeController = can.Control({
	defaults : {
		testType : ''
	},
},  {
	'init' : function() {
		
	},
	
	'.studentsTests click' : function(el, ev) {
		this.options.testType = 'studentsTests';
		tde.config.uiblock = true;
		tde.testparam.testTypeName = 'NoPractice';
		tdeModel.getStudentsTests(tde.testparam.testTypeName, this.proxy('setUpTestHome'));
	},
	
	'.practiceTests click' : function(el, ev) {
		this.options.testType = 'practiceTests';
		//this.setUpTestHome();
		tde.config.uiblock = true;
		tde.testparam.testTypeName = 'Practice';
		tdeModel.getStudentsTests(tde.testparam.testTypeName, this.proxy('setUpTestHome'));
	},
	
	setUpTestHome : function(json) {
		$('.w').html('');
		
		if(this.options.testType == 'studentsTests') {
			$('body .w').append(new EJS({
				url : 'js/views/testHome.ejs'
			}).render({studentsTests : json, assessmentNames: _.keys(json), secureBrowser: secureBrowser, pageContextPath: contextPath}));
		} else { 
			$('body .w').append(new EJS({
				url : 'js/views/practiceTest.ejs' 
			}).render({studentsTests : json, assessmentNames: _.keys(json), secureBrowser: secureBrowser, pageContextPath: contextPath}));
			//$('body .w').append(can.view('js/views/practiceTest.ejs', {secureBrowser: false, pageContextPath: contextPath}));
		}
		var tasktypeid = null;
		if($('.tabs-c ul li:first-child a').attr('href')) {
			tasktypeid = $('.tabs-c ul li:first-child a').attr('href').replace('#tabset-', "");
		}
		
		if (tasktypeid != '#' && tasktypeid!=null) {
			this.loadTests(tasktypeid);
		} else {
			$('.select-missing').show();
		}
		// $('#selection-tabs section').hide();
		// $('#selection-tabs section:first').show();
		$('#selection-tabs ul li:first').addClass('active');
		
		if ( tde.config.myscroll != null ) {tde.config.myscroll.refresh();}
	},

	'#selection-tabs ul li a click' : function(el, ev) {

		$('#selection-tabs ul li').removeClass('active');
		el.parent().addClass('active');

		if ($('.select-missing').is(':visible')) {
			$('.select-missing').hide();
		}
		$('#tests').html('');
		// $('#tests').append('<h1>' + $(this).html() + '</h1>');
		var headertesttypeid = el.attr('href').replace('#tabset-', "");
		if (headertesttypeid != '#') {
			this.loadTests(headertesttypeid);
		} else {
			$('.select-missing').show();
		}
		
		if ( tde.config.myscroll != null ) {tde.config.myscroll.refresh();}
		
		return false;
	},
	
	'#closeBrowser click' : function() {
		$('body .w').append(new EJS({
			url : 'js/views/closeBrowser.ejs'
		}).render());
		test.showAlertBox('browserClosePopUp');
		
		if ( tde.config.myscroll != null ) {
			tde.config.myscroll.destroy();
		}
		
		tde.config.myscroll = null;
	},
	
	'#confirmCloseBrowser click' : function() {
		$.ajax({
			url : contextPath +  '/j_spring_security_logout',
			success : function(data) {
				$('#browserClosePopUp .overlay').fadeOut("slow");
				window.close();
			},
			error : function() {
				// TODO We should do something witht he errors here.
			}
		});
	},
	
	'#confirmCancelCloseBrowser click' : function() {
		$('#browserClosePopUp').fadeOut('slow');
		setTimeout(function() {
			$('#browserClosePopUp').remove();
		}, 500);
		
		if ( Modernizr.touch ) {
			tde.config.myscroll = new iScroll('wrapper');
		} else {
			tde.config.myscroll = null;
		}
	},
	
	loadTests : function(tasktypeid) {
		var jsonMap = $('#testdatabytesttype').data('json');
		var data = jsonMap[tasktypeid];
		
		if (data) {
			var groupedTests = _.groupBy(data, function(data1) {
				return data1.testTypeName;
			});

			var keys = _.keys(groupedTests);

			for (var iter in keys) {
				if (/summative/i.test(keys[iter])) {
					$('#tests').append('<h1>' + keys[iter] + '</h1>');
					$.each(groupedTests[keys[iter]], function() {
						$('#tests').append('<div class="test-select"><div class="test-meta">' + '<h2>' + this.testName + '</h2></div>' + '<div class="take-test">' + '<a class="btn checkticket" data-obj=\'' + $.toJSON(this).replace(/'/g, "&apos;") + '\'>Take Test<img src="/images/arrow-sm-icon.png" alt="Go" /></a></div></div>');
					});
					delete keys[iter];
				}
			}

			for (var iter in keys) {
				if (/interim/i.test(keys[iter])) {
					$('#tests').append('<h1>' + keys[iter] + '</h1>');
					$.each(groupedTests[keys[iter]], function() {
						$('#tests').append('<div class="test-select"><div class="test-meta">' + '<h2>' + this.testName + '</h2></div>' + '<div class="take-test">' + '<a class="btn checkticket" data-obj=\'' + $.toJSON(this).replace(/'/g, "&apos;")  + '\'>Take Test<img src="/images/arrow-sm-icon.png" alt="Go" /></a></div></div>');
					});
					delete keys[iter];
				}
			}

			for (var iter in keys) {
				if (/formative/i.test(keys[iter])) {
					$('#tests').append('<h1>' + keys[iter] + '</h1>');
					$.each(groupedTests[keys[iter]], function() {
						$('#tests').append('<div class="test-select"><div class="test-meta">' + '<h2>' + this.testName + '</h2></div>' + '<div class="take-test">' + '<a class="btn checkticket" data-obj=\'' + $.toJSON(this).replace(/'/g, "&apos;")  + '\'>Take Test<img src="/images/arrow-sm-icon.png" alt="Go" /></a></div></div>');
					});
					delete keys[iter];
				}
			}

			for (var iter in keys) {
				$('#tests').append('<h1>' + keys[iter] + '</h1>');

				$.each(groupedTests[keys[iter]], function() {
					$('#tests').append('<div class="test-select"><div class="test-meta">' + '<h2>' + this.testName + '</h2></div>' + '<div class="take-test">' + '<a class="btn checkticket" data-obj=\'' + $.toJSON(this).replace(/'/g, "&apos;")  + '\'>Take Test<img src="/images/arrow-sm-icon.png" alt="Go" /></a></div></div>');
				});
			}
		} else {
			$('.select-missing').show();
		}
	}
	
	
	
	
});