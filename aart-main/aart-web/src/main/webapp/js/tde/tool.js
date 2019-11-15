var tool = new function() {
	var taskTools = new Array();
	var tools = {
			'calculator':{'boxid': 'calctool', 'arenaid': 'calc'},
			'graph_calculator':{'boxid': 'graphcalctool', 'arenaid': 'gWrap'},
			'talking_calculator':{'boxid': 'talkingcalctool', 'arenaid': 'talkingcalc'},
			'key_word_highlight':{'boxid': 'highlightertool', 'arenaid': null},
			'striker':{'boxid': 'strikethroughtool', 'arenaid': null},
			'eraser':{'boxid': 'eraser', 'arenaid': null},
			'search':{'boxid': 'searchtool', 'arenaid': 'search'},
			'tags':{'boxid': 'tagtool', 'arenaid': 'tag'},
			'notes':{'boxid': 'notestool', 'arenaid': ''},
			'periodic_table':{'boxid': 'periodictool', 'arenaid': 'pt'},
			'guided_reader':{'boxid': 'guidestool', 'arenaid': 'guideHandle'},
			'WSM':{'boxid': 'magnifytool', 'arenaid': 'magnify'},
			'magnifying_glass':{'boxid':'zoomintool', 'arenaid':'magnifying_glass_c'}
	};
	var tiCalcInstance=null;
	
	this.setUpTools = function() {
		
		var currentSection = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]];
		
		// Check if toolUsageCode given -- if not then default to something 
		if(!currentSection.toolUsageCode) {
			if(tde.testparam.jO.testFormatCode == 'NADP') {
				toolusage = 'SECTION_TOOLS';
			} else {
				toolusage = 'TASK_TOOLS';
			}
			currentSection.toolUsageCode = toolusage;
		}
		
		// The keys used in the student accommodations are the codeNames from
		// the accommodation.
		$('#tools-c ul').html('');

		$('#tools-c ul').append(
				'<li id="pointertool" class="tool-pointer">'
						+ tool_names.pointer + '<span title="'
						+ tool_names.pointer + '"></span></li>');
		$("#pointertool").click(function() {
			//$(document).trigger('tracker.tool', [ 'tool-pointer' ]);
			tool.deactivateCursorTools();

			try {
				ruler.deactivate(tde.config.tool_selectors.ruler);
				$("#rulertool").removeClass("selected");
			} catch (e) { /* Ignore guideline not loaded. */
			}

			$("#pointertool").addClass("selected");
		});
		
		//get library resources  as a tool
		if(currentSection.resources != null
		    && !_.isEmpty(currentSection.resources)) {
			
			$('#tools-c ul').append('<li id="librarytool" class="tool-library">'
									+ tool_names.library.name + '<span title="'
									+ tool_names.library.name + '"></span></li>');
			
			
			$('#librarytool').click(function() {
				var libtool = $('#library');
				if(!libtool.length) {
					$('#tool-arena').append(new EJS({
						url : 'js/views/tool/library.ejs'
					}).render({library: _.pluck(currentSection.resources,'resourceName')}));
					
					//$(document).trigger('tracker.tool', [ 'resource_library' ]);
					libtool = $('#library');
					libtool.show();
					$(this).addClass('selected');
					
					tool.positionTool(libtool);
					tool.getOnTop(libtool);
					var libraryControl = new libraryController( '#library', {} );
				} else {
					$(this).removeClass('selected');
					libtool.remove();
				}	
			});	
		}

		if (test.canUseTool("graph_calculator")) {
			taskTools.push("graph_calculator");
			$('#tools-c ul').append('<li id="graphcalctool" class="tool-graphcalc">'
							+ tool_names.graphingCalculator + '<span title="'+ tool_names.graphingCalculator + '"></span></li>');

			$('#tool-arena')
					.append(
							'<div id="gWrap"><div class="tool-hdr"><a href="#" class="ind-tool-close tool-close">X</a></div>'
									+ '<iframe scrolling="no" id="gcal" src="'
									+ contextPath
									+ '/js/views/tool/graphingCalculator.html"></iframe>'
									+ '</div>');
			
//			$('#tool-arena').append('<div id="gWrap"><div class="tool-hdr"><a href="#" class="ind-tool-close tool-close">X</a></div>'
//							+ '<div id="calculatorDiv" tabindex="0" class="calculatorDiv">'
//							+ '<div id="displayDiv" class="displayDiv"><canvas id="display" class="display" width="288" height="179"></canvas>'
//							+ '</div></div></div>');


			$('#gWrap').draggable({
				containment : "window",
				start : function() {
					tool.getOnTop($('#gWrap'));
				}
			});
			

			$('#graphcalctool, #gWrap .tool-close').click(function() {
				//$(document).trigger('tracker.tool', [ 'graph_calculator' ]);
				$('#gWrap').toggle();
				$('#graphcalctool').toggleClass('selected');
				tool.positionTool($('#gWrap'));
				if ($('#gWrap').is(':visible')) {
					tool.getOnTop($('#gWrap'));
				}
			});
			
//			$('#graphcalctool, #gWrap .tool-close').click(function() {
//				//$(document).trigger('tracker.tool', [ 'graph_calculator' ]);
//				$('#gWrap').toggle();
//				$('#graphcalctool').toggleClass('selected');
//				tool.positionTool($('#gWrap'));
//				if ($('#gWrap').is(':visible')) {
//					tool.getOnTop($('#gWrap'));
//					if(tiCalcInstance == null) {
//						$('<link rel="stylesheet" type="text/css" href="/TI-84P/TI-84P/css/ti84p-styles-min.css" >').appendTo("head");
//						$.getScript( "/TI-84P/TI-84P/js/ti84p-min.js").done(function( script, textStatus ) {
//							tiCalcInstance = new TI84(contextPath+'/js/tools/configurationFile.json');
//							tiCalcInstance.resize("small");
//						}).fail(function( jqxhr, settings, exception ) {
//						    $( "#gWrap" ).text( "Loading of graphing calculator failed." );
//						});
//					} else {
//						tiCalcInstance.showCalculator();
//					}
//				}
//				else {
//					tiCalcInstance.hideCalculator();
//				}
//			});
			
			$('#gWrap').click( function() {
				tool.getOnTop($('#gWrap'));
			});
		}

		if (test.canUseTool("calculator")) {
			taskTools.push("calculator");
			$('#tools-c ul').append(
					'<li id="calctool" class="tool-calc">'
							+ tool_names.calculator + '<span title="'
							+ tool_names.calculator + '"></span></li>');

			$('#tool-arena').append(new EJS({
				url : 'js/views/tool/calculator.ejs'
			}).render({
				type : "calculator"
			}));

			$("#calc").draggable({
				containment : "window",
				cancel : "#calc .calcHistory",
				start : function() {
					tool.getOnTop($('#calc'));
				}
			});
			$("#calctool, #calc .tool-close").click(function() {
				//$(document).trigger('tracker.tool', [ 'calculator' ]);
				$("#calc").toggle();
				$("#calctool").toggleClass("selected");
				$('#calculator .calcDisplay').focus();
				tool.positionTool($('#calc'));
				if ($('#calc').is(':visible')) {
					tool.getOnTop($('#calc'));
				}
			});
			resetCalculator("0", $('#calculator .calcDisplay'));
		}

		if (test.canUseTool("talking_calculator")) {
			taskTools.push("talking_calculator");
			$('#tools-c ul').append(
					'<li id="talkingcalctool" class="tool-calc">'
							+ tool_names.talkingCalculator + '<span title="'
							+ tool_names.talkingCalculator + '"></span></li>');

			$('#tool-arena').append(new EJS({
				url : 'js/views/tool/calculator.ejs'
			}).render({
				type : "talkingCalculator"
			}));
			$("#talkingcalc").draggable({
				containment : "window",
				cancel : "#talkingcalc .calcHistory",
				start : function() {
					tool.getOnTop($('#talkingcalc'));
				}
			});
			$("#talkingcalctool, #talkingcalc .tool-close").click(function() {
				//$(document).trigger('tracker.tool', [ 'talking_calculator' ]);
				$("#talkingcalc").toggle();
				$("#talkingcalctool").toggleClass("selected");
				$('#talkingCalculator .calcDisplay').focus();
				tool.positionTool($('#talkingcalc'));
				if ($('#talkingcalc').is(':visible')) {
					tool.getOnTop($('#talkingcalc'));
				}
			});

			$('#calculatorAudio').jPlayer({
				supplied : "mp3, oga",
				errorAlerts : false,
				warningAlerts : false
			});
			resetCalculator("0", $('#talkingCalculator .calcDisplay'));
		}

		// reference sheet
		if (currentSection.helpNotes != null && currentSection.helpNotes != "") {
			$('#tools-c ul').append(
					'<li id="referencestool" class="tool-references">'
							+ tool_names.reference + '<span title="'
							+ tool_names.reference + '"></span></li>');
			$('#tool-arena').append(new EJS({
				url : 'js/views/tool/reference.ejs'
			}).render());

			$('#reference .tool-content').html(currentSection.helpNotes);
			$("#reference").draggable({
				containment : "window",
				start : function() {
					tool.getOnTop($('#reference'));
				}
			});
			$("#referencestool, #reference .tool-close").click(function() {
				//$(document).trigger('tracker.tool', [ 'referencestool' ]);
				$("#reference").toggle();
				$("#referencestool").toggleClass("selected");
				tool.positionTool($('#reference'));
				if ($('#reference').is(':visible')) {
					tool.getOnTop($('#reference'));
				}
			});
			
			$('#reference').click( function() {
				tool.getOnTop($('#reference'));
			});
		}

		// highlighter and eraser tool
		if (test.canUseTool("key_word_highlight")) {
			taskTools.push("key_word_highlight");
			$('#tools-c ul').append(
					'<li id="highlightertool" title="' + tool_names.highlighter
							+ '" class="tool-highlighter">'
							+ tool_names.highlighter + '<span title="'
							+ tool_names.highlighter + '"></span></li>');

			$("#highlightertool")
					.click(
							function() {
								//$(document).trigger('tracker.tool', [ 'highlightertool' ]);
								if (!highLighter.isMode("highlight")) {
									test.defaultCursor();
									tool.deactivateCursorTools();

									try {
										ruler
												.deactivate(tde.config.tool_selectors.ruler);
										$("#rulertool").removeClass("selected");
									} catch (e) { /*
													 * Ignore guideline not
													 * loaded.
													 */
									}

									$("#pointertool").removeClass("selected");

									$("#highlightertool").addClass("selected");
									highLighter
											.activate(
													tde.config.tool_selectors.highlighter,
													"highlight");

								} else {
									$("#highlightertool").removeClass(
											"selected");
									$("#pointertool").addClass("selected");
									highLighter
											.deactivate(tde.config.tool_selectors.highlighter);
								}
							});
		}
		
		if (test.canUseTool("key_word_highlight") || test.canUseTool("striker")) {
			taskTools.push("eraser");
			$('#tools-c ul').append(
					'<li id="eraser" title="' + tool_names.eraser
							+ '" class="tool-eraser">' + tool_names.eraser
							+ '<span title="' + tool_names.eraser
							+ '"></span></li>');

			$('#eraser')
					.click(
							function() {
								//$(document).trigger('tracker.tool', [ 'eraser' ]);
								if (!Eraser.isMode("highlight")) {
									test.defaultCursor();
									tool.deactivateCursorTools();

									try {
										ruler
												.deactivate(tde.config.tool_selectors.ruler);
										$("#rulertool").removeClass("selected");
									} catch (e) { /*
													 * Ignore guideline not
													 * loaded.
													 */
									}

									$("#pointertool").removeClass("selected");

									$("#eraser").addClass("selected");
									Eraser.activate(
											tde.config.tool_selectors.eraser,
											"highlight");
								} else {
									$("#eraser").removeClass("selected");
									$("#pointertool").addClass("selected");
									Eraser
											.deactivate(tde.config.tool_selectors.eraser);
								}
							});
		}

		// String through tool
		if (test.canUseTool("striker")) {
			taskTools.push("striker");
			$('#tools-c ul').append(
					'<li id="strikethroughtool" class="tool-maskanswers">'
							+ tool_names.striker + '<span title="'
							+ tool_names.striker + '"></span></li>');

			$("#strikethroughtool")
					.click(
							function() {
								//$(document).trigger('tracker.tool', [ 'striker' ]);
								if (!striker.isMode("strikethroughText")) {
									test.defaultCursor();
									tool.deactivateCursorTools();

									try {
										ruler
												.deactivate(tde.config.tool_selectors.ruler);
										$("#rulertool").removeClass("selected");
									} catch (e) { /*
													 * Ignore guideline not
													 * loaded.
													 */
									}

									$("#pointertool").removeClass("selected");

									$("#strikethroughtool")
											.addClass("selected");
									striker.activate(
											tde.config.tool_selectors.striker,
											"strikethroughText");

								} else {
									$("#strikethroughtool").removeClass(
											"selected");
									$("#pointertool").addClass("selected");
									striker
											.deactivate(tde.config.tool_selectors.striker);
								}
							});
		}

		if (test.canUseTool("audio_test")) {
			$('#testAudio').jPlayer({
				supplied : "mp3, oga",
				errorAlerts : false,
				warningAlerts : false
			});
			$('#foilAudio').jPlayer({
				supplied : "mp3, oga",
				swfPath : "http://www.jplayer.org/latest/js/Jplayer.swf",
				solution : "html, flash",
				errorAlerts : false,
				warningAlerts : false
			});
		}

		// Magnifying Glass
		/* if (test.canUseTool('magnifying_glass')) {
			$('#tools-c ul').append(
					'<li id="zoomintool" class="tool-zoomin">'
							+ tool_names.magnifyingGlass.name + '<span title="'
							+ tool_names.magnifyingGlass.name
							+ '"></span></li>');
			// $('#tool-arena').append(new EJS({url:
			// 'js/views/tool/magnifyingGlass.ejs'}).render({}));

			$("#zoomintool")
					.click(
							function() {
								/*
								 * if (magnifyingtools == "magnification")
								 * return; setMagnifyingGlass(this); if
								 * (magnifyingtools == "") {magnifyingtools =
								 * "magnifyingglass";} else if (magnifyingtools ==
								 * "magnifyingglass") {magnifyingtools = "";}
								 *  /
								var currentMode = magnifyingGlass.getMode();
								if (currentMode.indexOf("scale") == -1) {
									tool.deactivateCursorTools();

									// console.log("clicked zoom tool open");
									$(document).trigger('tracker.tool',
											[ 'magnifying_glass' ]);
									$("#pointertool").removeClass("selected");
									magnifyingGlass
											.setMode("mscale_" + 2 + "x");
									$("#zoomintool").addClass("selected");
									magnifyingGlass
											.activate(tde.config.tool_selectors.magnifyingglass);
									// tool.positionTool($('#magnifying_glass_c'));
								} else {
									console.log("clicked zoom tool close");
									$("#zoomintool").removeClass("selected");
									$("#pointertool").addClass("selected");
									magnifyingGlass.deactivate();
								}
							});
		}
        */
		// Search Tool
		if (test.canUseTool('search')) {
			taskTools.push("search");
			$('#tools-c ul').append(
					'<li id="searchtool" class="tool-search">'
							+ tool_names.search.name + '<span title="'
							+ tool_names.search.name + '"></span></li>');

			$('#searchtool').on('click', function() {
				if(!$('#search').length) {
					$('#tool-arena').append(new EJS({
						url : 'js/views/tool/search.ejs'
					}).render({}));
					
					//$(document).trigger('tracker.tool', [ 'search' ]);
					$('#search').show();
					$(this).addClass("selected");
					$('#search ul.search-inputs input').focus();
					
					tool.positionTool($('#search'));
					tool.getOnTop($('#search'));
					var searchControl = new searchController( '#search', {} );
				} else {
					$(this).removeClass("selected");
					$('#search').remove();
				}
			});
		}

		if (test.canUseTool('periodic_table')) {
			taskTools.push("periodic_table");
			$('#tools-c ul').append(
					'<li id="periodictool" class="tool-periodic">'
							+ tool_names.periodicTable.name + '<span title="'
							+ tool_names.periodicTable.name + '"></span></li>');

			$('#periodictool').on('click',function() {
				if(!$('#pt').length) {
					$('#tool-arena').append(new EJS({
						url : 'js/views/tool/periodicTable.ejs'
					}).render({}));
					
					//$(document).trigger('tracker.tool', [ 'periodic_table' ]);
					$('#pt').show();
					$('#pTElem').hide();
					$(this).addClass("selected");
					
					tool.positionTool($('#pt'));
					tool.getOnTop($('#pt'));
					var periodictableControl = new periodictableController( '#pt', {} );
				} else {
					$(this).removeClass("selected");
					$('#pt').remove();
				}
			});
		}

		if (test.canUseTool('tags')) {
			taskTools.push("tags");
			$('#tools-c ul').append(
					'<li id="tagtool" class="tool-tags">'
							+ tool_names.tags.name + '<span title="'
							+ tool_names.tags.name + '"></span></li>');

			$('#tool-arena').append(new EJS({
				url : 'js/views/tool/tags.ejs'
			}).render({}));

			$('#tag').draggable({
				containment : "window",
				start : function() {
					tool.getOnTop($('#tag'));
				}
			});
			$('#tagtool, #tag .tool-hdr a.tool-close').click(function() {
				//$(document).trigger('tracker.tool', [ 'tags' ]);
				$('#tag').toggle();
				$('#tagtool').toggleClass('selected');
				tool.positionTool($('#tag'));
				if ($('#tag').is(':visible')) {
					tool.getOnTop($('#tag'));
				}
			});

			$('#tag_main_idea').click(
					function(e) {
						tags.placeTag(e, ".test-passage .passage", "tag-mainidea", task
								.findPart(tde.testparam.currentQuestion), $(
								this).attr('title'));
						$('#tag').hide();
						$("#tagtool").toggleClass("selected");
					});

			$('#tag_supporting_detail').click(
					function(e) {
						tags.placeTag(e, ".test-passage .passage", "tag-support", task
								.findPart(tde.testparam.currentQuestion), $(
								this).attr('title'));
						$('#tag').hide();
						$("#tagtool").toggleClass("selected");
					});

			$('#tag_keywords').mousedown(
					function(e) {
						tags.placeTag(e, ".test-passage .passage", "tag-keyword", task
								.findPart(tde.testparam.currentQuestion), $(
								this).attr('title'));
						$('#tag').hide();
						$("#tagtool").toggleClass("selected");
					});

			$('#tag_evidence').mousedown(
					function(e) {
						tags.placeTag(e, ".test-passage .passage", "tag-evidence", task
								.findPart(tde.testparam.currentQuestion), $(
								this).attr('title'));
						$('#tag').hide();
						$("#tagtool").toggleClass("selected");
					});

			$('#tag_readagain').mousedown(
					function(e) {
						tags.placeTag(e, ".test-passage .passage", "tag-readagain", task
								.findPart(tde.testparam.currentQuestion), $(
								this).attr('title'));
						$('#tag').hide();
						$("#tagtool").toggleClass("selected");
					});

			$('#tag_important').mousedown(
					function(e) {
						tags.placeTag(e, ".test-passage .passage", "tag-important", task
								.findPart(tde.testparam.currentQuestion), $(
								this).attr('title'));
						$('#tag').hide();
						$("#tagtool").toggleClass("selected");
					});

			$('#tag_clear').click(function(e) {
				tags.clearTags();
			});
			
			$('#tag').click( function() {
				tool.getOnTop($('#tag'));
			});
		}

		if (test.canUseTool('notes')) {
			taskTools.push("notes");
			$('#tools-c ul').append(
					'<li id="notestool" class="tool-notes">'
							+ tool_names.postItNotes.name + '<span title="'
							+ tool_names.postItNotes.name + '"></span></li>');
			$('#tool-arena').append(new EJS({
				url : 'js/views/tool/postit.ejs'
			}).render({}));

			var numOfNotes = function() {
				var notecount = 0;
				var currentGroup = task.getCurrentGroup();
				for ( var j = 0; j < tde.testparam.postItNotes.length; j++) {
					if (tde.testparam.postItNotes[j].group == currentGroup) {
						notecount++;
					}
				}
				return notecount;
			};

			$('#notestool').click(
					function() {
						//$(document).trigger('tracker.tool', [ 'notes' ]);
						if (numOfNotes() < 1) {
							postIt.activate(tde.config.tool_selectors.postIt,
									"postIt");
							var note =  _.find(tde.testparam.postItNotes, function(note){
								if(note.group == task.getCurrentGroup()) {
									return note;
								}
							});
							tool.positionTool($('#'+note.id));
							if ($('#'+note.id).is(':visible')) {
								tool.getOnTop($('#'+note.id));
							}
						} else {
							$('#posit-notes').show();
							test.showAlertBox('postItWarn');
						}
					});

			$('#postIt-minimize').click(
					function(event) {
						var parentId = event.target.parentNode.parentNode.id;
						if ($('#' + parentId).find('#postIt-close').css(
								'display') == 'none') {
							// ///$('#' + parentId).find('#postIt-minimize
							// img').attr('src',
							// 'images/tools/minimize-postit.png');
							$(this).removeClass('ind-postit-maximize')
									.addClass('ind-tool-min');
							// $('#' +
							// parentId).find('div:eq(23),div:eq(0)').show();
							$('#' + parentId).find('div').show();
							$('#' + parentId).find('textarea').show();
							$('#' + parentId).find('#postIt-close').show();
							$('#' + parentId).css(
									postIt.getDimensions(parentId));
							
							if (!/ipad/ig.test(userAgent)) {
								$('#postitdrag').hide();
							}
							tool.getOnTop($('#'+parentId));
						} else {
							// ///$('#' + parentId).find('#postIt-minimize
							// img').attr('src',
							// 'images/tools/maximize-postit.png');
							$(this).removeClass('ind-tool-min').addClass(
									'ind-postit-maximize');
							// $('#' +
							// parentId).find('div:eq(23),div:eq(0)').hide();
							postIt.setDimensions(parentId);
							$('#' + parentId).find('div').hide();
							$('#' + parentId).find('textarea').hide();
							$('#' + parentId).find('div.tool-hdr').show();
							$('#' + parentId).find('#postIt-close').hide();
							$('#' + parentId).css({
								'height' : 30 + 'px',
								'width' : 30 + 'px'
							});
						}
					});
			$('#confirmDeleteNoteButton').click(function() {
				postIt.deleteNote(task.getCurrentGroup());
				$('#confirmNoteDelete').fadeOut("slow");
			});

			$('#confirmCancelDeleteNoteButton').click(function() {
				$('#confirmNoteDelete').fadeOut("slow");
			});

			$('#postIt-close').click(function() {
				$('#posit-notes').show();
				test.showAlertBox('confirmNoteDelete');
			});

			$('#postItWarnConfirm').click(function() {
				$("#backgroundMask").fadeOut("slow");
				$("#postItWarn").fadeOut("slow");
			});
		}

		if (test.canUseTool('guided_reader')) {
			taskTools.push("guided_reader");
			$('#tools-c ul').append(
					'<li id="guidestool" class="tool-guides">'
							+ tool_names.guideLine + '<span title="'
							+ tool_names.guideLine + '"></span></li>');

			$("#guidestool")
					.click(
							function() {
								//$(document).trigger('tracker.tool', [ 'guided_reader' ]);
								if (!guideLine.isMode("active")) {
									if (/ipad/ig.test(userAgent)) {
										$("#guideHandle").show();
										$('#guideHandle').draggable({
											axis : "y",
											constraint : ".test-sections"
										});
									}
									test.defaultCursor();
									tool.deactivateCursorTools();

									try {
										ruler
												.deactivate(tde.config.tool_selectors.ruler);
										$("#rulertool").removeClass("selected");
									} catch (e) { /*
													 * Ignore guideline not
													 * loaded.
													 */
									}

									$("#pointertool").removeClass("selected");

									$("#guidestool").addClass("selected");
									guideLine
											.activate(
													tde.config.tool_selectors.guideline,
													"#guideTop");
								} else {
									$("#guidestool").removeClass("selected");
									$("#pointertool").addClass("selected");
									guideLine
											.deactivate(tde.config.tool_selectors.guideline);
									$("#guideHandle").hide();
								}
							});
		}

		// ContentSpoken ..//SpokenAssignedSupport SpokenassignedSupport
		/*
		 * if(test.canUseAccommodation('ContentSpoken')) {
		 * if(_.findWhere(test.canUseAccommodation('ContentSpoken'),
		 * {SpokenassignedSupport:'true'})) {
		 * 
		 * $('#tools-c ul').append('<li id="ttstool" class="tool-tts">'+
		 * tool_names.textToSpeech.name +'<span
		 * title="'+tool_names.textToSpeech.name+'"></span></li>');
		 * $('#tool-arena').append(new EJS({url:
		 * 'js/views/tool/tts.ejs'}).render({}));
		 * 
		 * $('#textToSpeech').draggable({containment: "window"}); $('#ttstool,
		 * #textToSpeech .tool-hdr a.tool-close').click(function(){
		 * $('#textToSpeech').toggle(); $('#ttstool').toggleClass('selected');
		 * tool.positionTool($('#textToSpeech')); }); } }
		 */
		// All Accommodations Will be Called from Here..
		var profile = JSON.parse(sessionStorage.getItem('profile'));
		
		magnificationAccommodation.init(profile);
		alternateColorsAccommodation.init(profile);
		auditoryCalmingAccommodation.init(profile);
		colorContrastAccommodation.init(profile);
		maskingAccommodation.init(profile);
		reverseContrastAccommodation.init(profile);
		vplayerAccommodation.init(profile);
		
		/*var testSectionTools = _.pluck(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tools, 'codeName');
		if(_.contains(testSectionTools, 'key_word_highlight') || _.contains(testSectionTools, 'striker')) {
			testSectionTools.push('eraser');
		}
		
		$('#tools-c ul li').hide();
		$('#pointertool').show();
		for(var i in testSectionTools) {
			if(tools[testSectionTools[i]]) {
				$('#'+tools[testSectionTools[i]].boxid).show();
			}
		}*/
		
		tool.positionToolIcons();		
	};

	this.hideAllUITools = function() {
		$('#tool-arena').hide();
		$('body').removeClassRegEx(/^contrast_/);
	};

	this.showAllUITools = function() {
		$('#tool-arena').show();
		$('body').addClass(tde.testparam.colorcontrast);
	};

	this.deactivateCursorTools = function() {
		try {
			highLighter.deactivate(tde.config.tool_selectors.highlighter);
			$("#highlightertool").removeClass("selected");
		} catch (e) { /* Ignore highlighter not loaded. */
		}

		try {
			Eraser.deactivate(tde.config.tool_selectors.eraser);
			$("#eraser").removeClass("selected");
		} catch (e) { /* Ignore eraser not loaded. */
		}

		try {
			striker.deactivate(tde.config.tool_selectors.striker);
			$("#strikethroughtool").removeClass("selected");
		} catch (e) { /* Ignore striker not loaded. */
		}
		
		/*try {
			guideLine.deactivate(tde.config.tool_selectors.guideline);
			$("#guidestool").removeClass("selected");
		} catch (e) {  Ignore striker not loaded. }*/

	};

	
	this.positionTool=function(toolid) {
		if(toolid.attr('id') == 'gWrap') {
			toolid.css({
				'top' : $(window).scrollTop() + parseInt($('.hdr').height()),
				'z-index': 9998,
				left : 100+$(window).scrollLeft()
			});
		} else {
			toolid.css({
				'top' : $(window).scrollTop() + parseInt($('.hdr').height()),
				'z-index': 9994,
				left : 100+$(window).scrollLeft()
			});
		}
	};
	
	this.getOnTop = function(elem) {
		var visibletools = $('#tool-arena').children(':visible');
		visibletools.each(function() {
			$(this).css('z-index', 9994);
		});
		
		if(elem.attr('id') == 'gWrap') {
			elem.css('z-index', 9998);
		} else {
			elem.css('z-index', 9995);
		}
	};
	
	this.setUpTaskTools = function() {
		
		$('#tools-c ul li').removeClass('selected');
		$('#pointertool').addClass('selected');
		var currentTaskTools = _.pluck(tasks[tde.testparam.currentQuestion].tools, 'codeName');
		//var testSectionTools = _.pluck(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tools, 'codeName');
		
		/*if(_.contains(testSectionTools, 'key_word_highlight') || _.contains(testSectionTools, 'striker')) {
			testSectionTools.push('eraser');
		} else */
		if(_.contains(currentTaskTools, 'key_word_highlight') || _.contains(currentTaskTools, 'striker')) {
			currentTaskTools.push('eraser');
		}
		
		for(var i in taskTools) {
			if(!_.contains(currentTaskTools, taskTools[i])) {
				if(tools[taskTools[i]]) {
					var toolinfo = tools[taskTools[i]];
					$('#'+toolinfo.boxid).hide();
					if(toolinfo.arenaid != null) {
						$('#'+toolinfo.arenaid).hide();
					}
					taskTools[i] = null;
				}
			}
		}
		
		for(var i in currentTaskTools) {
			if(!_.contains(currentTaskTools[i], taskTools) /*&& !_.contains(currentTaskTools[i], testSectionTools)*/){
				//$('#'+tools[currentTaskTools[i]].boxid).show();
				if(tools[currentTaskTools[i]]) {
					$('#'+tools[currentTaskTools[i]].boxid).show();
					taskTools.push(currentTaskTools[i]);
				}
			}
		}
	};
	
	this.showTool = function(toolName){
		switch(toolName){
			case 'rubric' : if($("#openRubric").length != 0)
								$("#openRubric").show();
							else{
								$('#tools-c ul').append('<li id="openRubric" class="rubric">'
															+ tool_names.rubric + '<span title="' + tool_names.rubric + '"></span></li>');
								tool.positionToolIcons();
								$("#openRubric")
								.click(
										function() {
											//$(document).trigger('tracker.tool', [ 'rubric' ]);										 
											$("#openRubric").toggleClass("selected");  
											if($("#openRubric").hasClass("selected")){
												tool.rubricPopUp();
												tool.getOnTop($('#rubricInfo'));
											} else {
												$('#rubricInfo').remove(); 
											}
										});
							}
							break;
			default : break;
		}
	};

	this.hideTool = function(toolName){
		switch(toolName){
			case 'rubric' : if($('#openRubric').length != 0){
				                if($("#openRubric").hasClass("selected"))
								   $("#openRubric").toggleClass("selected");  
			
							    $("#openRubric").hide();
							
							    if($('#rubricInfo').length != 0)
								   $('#rubricInfo').remove(); 
			                }
							break;
			default : break;
		}
	};
	
	this.rubricPopUp = function() {
		$('#tool-arena').append(new EJS({
			url : 'js/views/rubricInfo.ejs'
		}).render({rubricDirections : tasks[tde.testparam.currentQuestion].rubricDirections, taskRubricCategories : tasks[tde.testparam.currentQuestion].rubricCategories}));

		var windowWidth = $(window).width(),
			popupHeight = $(window).height()/1.5,
			popupWidth = $(window).width()/2;

		$('#rubricInfo').css({
			"position" : "relative",
			"top" : $(window).scrollTop() + parseInt($('.hdr').height()),
			"left" : windowWidth / 2,
			"height" : popupHeight - 10,
			"width" : popupWidth
		});
		
		$('#rubricContent').css({ 
			"height" : popupHeight - 10,
			"width" : popupWidth
		});
				
		$('.tool-hdr .ind-tool-close').css({
			"top" : "-40px",
			"left" : "-35px"
		});
		
		$('#rubricInfo').show();  
		$('#rubricContent').jScrollPane();
   
		$('#rubricInfo').draggable({ 
			containment : "window",
			start : function() {
				tool.getOnTop($('#rubricInfo'));
			}
		}); 
			
		$('#rubricInfo').click(function() {  
			tool.getOnTop($('#rubricInfo'));
		});
		
		$('#rubricInfo a.rubric-close').click(function() {  
			$("#openRubric").trigger("click");
		});
	};
	
	this.positionToolIcons=function(){
		if(testObj.uiname == 'genTest') {
			if ( Modernizr.touch ) {
				$('#tools-c').jScrollPane();
				if($('#tools-c').has('.jspVerticalBar').length > 0){
					$('#tools-c ul li').css({ "margin-right": "4px" });
				} else {
					$('#tools-c ul li').css({ "margin-right": "26px" });
				}
			} else {
				if($('#tools-c')[0].scrollHeight > $('#tools-c')[0].clientHeight){
					$('#tools-c ul li').css({ "margin-right": "10px" });
				} else {
					$('#tools-c ul li').css({ "margin-right": "26px" });
				}
			}
		}
	};
};