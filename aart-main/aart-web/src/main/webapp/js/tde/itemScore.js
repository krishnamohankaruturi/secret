var itemScore = (function() {
	
	function fetch(taskNumber, foilSelection) {
		var score = null;
		var currentTask = tasks[taskNumber];
		if(currentTask.taskType == 'MC-K' || currentTask.taskType == 'MC-S' || currentTask.taskType == 'T-F') {
			if(currentTask.scoringNeeded) {
				if (currentTask.foils && currentTask.foils[foilSelection] && currentTask.foils[foilSelection].correctResponse == true) {
					score = currentTask.foils[foilSelection].responseScore;
				} else if (currentTask.foils && currentTask.foils[foilSelection] && currentTask.foils[foilSelection].correctResponse == false) {
					score = 0;
				}
			} 
		} else if (currentTask.taskType == 'MC-MS') {
			if(currentTask.scoringNeeded && currentTask.scoringData != null) {
				var data = JSON.parse(currentTask.scoringData);
				score = 0;
				if(currentTask.scoringMethod == 'partialquota') {
					//Count # of correct responses and then lookup the score with that number(quota)
					var correctResponseCount = 0;
					for(var foil in foilSelection) {
						var taskFoil = currentTask.foils[currentTask.foilmap['map_'+foilSelection[foil]]];
						if(taskFoil && taskFoil.correctResponse) {
							correctResponseCount++;
						}
					}
					if(correctResponseCount > 0) {
						var scoreObj = _.findWhere(data.partialResponses, {'quota': ''+correctResponseCount});
						if(scoreObj) {
							score = scoreObj.score;
						}
					}
				} else if(currentTask.scoringMethod == 'correctOnly') {
					// match externalIds in correctResponse array
					var correct = true;
					var correctFoilCount = 0;
					
					for(var i=0; i<foilSelection.length; i++) {
						var foil = _.findWhere(currentTask.foils,{'id': foilSelection[i]});
						if(foil) {
							var response = _.findWhere(data.correctResponse.responses, {'responseid': ''+foil.externalId});
							if(!response) {
								correct = false;
								break;
							} else {
								correctFoilCount++;
							}
						}
					}
					
					if(correct && (correctFoilCount == data.correctResponse.responses.length)) {
						score = data.correctResponse.score;
					}
					
				} else if(currentTask.scoringMethod == 'partialcredit' || currentTask.scoringMethod == 'subtractivemodel') {
					// add up scores if match in partialResponse array
					for(var i=0; i<foilSelection.length; i++) {
						var foil = _.findWhere(currentTask.foils,{'id': foilSelection[i]});
						if(foil) {
							var response = _.findWhere(data.partialResponses, {'responseid': ''+foil.externalId});
							if(response) {
								score += Number(response.score);
							}
						}
					}
					if(score < 0 && currentTask.scoringMethod == 'subtractivemodel') {
						score = 0;
					}
				}
			}
		} else if(currentTask.taskType == 'ITP') {
			if(currentTask.scoringNeeded && currentTask.scoringData != null) {
				var data = JSON.parse(currentTask.scoringData);
				
				if(currentTask.scoringMethod == 'correctOnly') {
					
					var length = 0;
					if(!_.isEmpty(foilSelection)) {
						if(foilSelection[0].options) {
							length = _.reduce(foilSelection, function(memo, sel){ return memo + sel.options.length; }, 0);
						} else {
							length = foilSelection.length;
						}
					}
					
					/*var keyObj = (typeof data.correctResponses[0] !== 'undefined') ? data.correctResponses[0] : data.correctResponses;
					var keyLength = (typeof keyObj.responses !== 'undefined') ? data.correctResponses[0].responses.length : 1;
					if(data.correctResponses[0]) {
						if(data.correctResponses[0].responses[0].answer && data.correctResponses[0].responses[0].answer.lines) {
							keyLength = data.correctResponses[0].responses[0].answer.lines.length;
						}
					}*/
					var keyLength = data.correctResponses[0].responses.length;
					if(keyLength > 0) {
						if(data.correctResponses[0].responses[0].answer && data.correctResponses[0].responses[0].answer.lines) {
							keyLength = data.correctResponses[0].responses[0].answer.lines.length;
						} else if(data.correctResponses[0].responses[0].partitions && data.correctResponses[0].responses[0].partitions.numberOfPartitions) {
							keyLength = length;//data.correctResponses[0].responses[0].partitions.numberOfPartitions;
						}
						
						if( keyLength == length) {//foilSelectionlength) { //change this...foilSelection have buckets (so lengths are not equal)
							score = scoreTeItem(data.correctResponses[0].responses, foilSelection, 'correctOnly', data.correctResponses[0].score);
						} else {
							score = 0;
						}
					} else {
						score = 0;
					}
					
					
				} else if(currentTask.scoringMethod == 'partialcredit') {
					score = scoreTeItem(data.partialResponses, foilSelection, 'partialcredit', null);
				}
			}
		} else if(currentTask.taskType == 'CR') {
			if(currentTask.scoringNeeded && currentTask.scoringData != null) {
				foilSelection = foilSelection.split('~~~');//foilSelection.replace(/~~~/,'');
				foilSelection.splice(-1,1);
				var data = JSON.parse(currentTask.scoringData);
				
				for(var foil in foilSelection) {
					var foilScore = null;
					var caseSensitive = currentTask.foils[foil].caseSensitive;
					var studentResponse = foilSelection[foil];
					if(!caseSensitive) {
						studentResponse = studentResponse.toLowerCase();
					}
					
					if(currentTask.scoringMethod == 'correctOnly' || currentTask.scoringMethod == 'partialcredit') {
						var key = data[foil].correctResponse.value;
						if(!caseSensitive) {
							key = key.toLowerCase();
						}
						if(key == studentResponse) {
							foilScore += data[foil].correctResponse.score;
						}
					}
					
					if(currentTask.scoringMethod == 'partialcredit' && foilScore == null) {
						for(var i in data[foil].acceptableResponses) {
							var option = data[foil].acceptableResponses[i];
							var key = option.value;
							if(!caseSensitive) {
								key = key.toLowerCase();
							}
							
							if(key == studentResponse) {
								foilScore += option.score;
								break;
							}
						}
					}
					if(currentTask.scoringMethod == 'partialcredit') {
						if(foilScore != null) {
							score += foilScore;
						}
					} else if(currentTask.scoringMethod == 'correctOnly') {
						if(foilScore == null) {
							score = null;
							break;
						} else {
							score += foilScore;
						}
					}
					
				}
				
				if(score == null) {
					score = 0;
				}
			}
		}
		return score;
	}
	
	/**
	 * score te items based on scoringData json and studentResponse json
	 * 
	 * Response Json
	 * Bg /labelling
		[{"dropId":$(this).attr("id"),"foilId":$item.attr("id")}]

		Ordering
		[{"id": jQuery(this).attr("id"),"optionText": jQuery(this).html(),"index": index}]

		MDB
		[{"id": $(this).attr("id"), //drop bucket id
			"options": [{"id": $(this).attr("id"),//foil id
			"optionText": $(this).html()}]}]
			
		///////////
		Scoring json
		{
			correctResponses: [{score: xyz, responses:[{foilid: , dropid/index: }, {foilid: , dropid/index: }]}],
			partialResponses: [{foilId: , dropId: , score: },{foilId: , dropId: , score: }]
		}

	*/
	function scoreTeItem(responseKey, foilSelection, scoringMethod, fullscore) {
		var incorrect = false;
		var score = 0;
		var keysObj = (typeof responseKey[0] !== 'undefined')? responseKey[0] : responseKey; // ["index", "score", "value", "foilid", "dropid"]
		var keys = _.keys(keysObj); //_.keys(responseKey[0])
		
		var matchtype = '';
		if(_.contains(keys, 'dropid')) { //xx, Graphic Organizer
			matchtype = 'drop';
		} else if(_.contains(keys, 'rowHeaders')) { // Punnett Square
			matchtype = 'psquare';
		} else if(_.contains(keys, 'dragid')) { // Editing
			matchtype = 'drag';
		} else if(_.contains(keys, 'value')) {
			matchtype = 'value';
		} else if(_.contains(keys, 'optionId')) {
			matchtype = 'optionid';
		} else if(_.contains(keys, 'answer')) {
			var inKeys = _.keys(responseKey[0].answer);
			if(_.contains(inKeys, 'xIntercept')) {
				matchtype = 'line';
			} else if(_.contains(inKeys, 'x')) {
				matchtype = 'point';
			} else if(_.contains(inKeys,'numberOfLines')) { // Straight Lines
				matchtype = 'st-lines';
			}
		} else if(_.contains(keys, 'leftid')) {
				matchtype = 'ml';
		} else if(_.contains(keys, 'numberOfSegments')) {
			matchtype = 'pos';
		} else if(_.contains(keys, 'index')) {
			if(typeof foilSelection[0] == 'object') {
				matchtype = 'index';
			} else {
				matchtype = 'select';
			}
		} else if(_.contains(keys, 'partitions')) { //Partition Number Line
			matchtype = 'partitions';
		}
		
		for(var i in foilSelection) {
			var resp = foilSelection[i];
			if(resp.options) {
				for(var j in resp.options) {
					var match = undefined;
					if(matchtype == 'drop') {
						match = _.findWhere(responseKey, {dropid: resp.id, foilid: resp.options[j].id});
					} else if(matchtype == 'drag') {
						match = _.findWhere(responseKey, {dragid: resp.id, foilid: resp.options[j].id});
					} else if(matchtype == 'value') {
						match = _.findWhere(responseKey, {value: resp.value, foilid: resp.options[j].id});
					} else if(matchtype == 'index') {
						match = _.findWhere(responseKey, {index: resp.options[j].index, foilid: resp.options[j].foilId}) || _.findWhere(responseKey, {index: ''+resp.options[j].index, foilid: resp.options[j].id}) || _.findWhere(responseKey, {index: resp.options[j].index, foilid: resp.options[j].id});
					} else if(matchtype == 'select') {
						match = _.findWhere(responseKey, {foilid: resp});
					} else if(matchtype == 'optionid') {
						match = _.findWhere(responseKey, {optionId: resp.options[0], foilId: resp.foilId});
					} else if(matchtype == 'ml') {
						match = _.findWhere(responseKey, {leftid: resp.srcid, textid: resp.destid}) || _.findWhere(responseKey, {leftid: resp.destid, textid: resp.srcid});
					} else if(matchtype == 'pos') {
						if(scoringMethod == 'correctOnly') {
							match = _.findWhere(responseKey, {numberOfSegments: ''+resp.numberOfSegments, numberOfSelections: ''+resp.numberOfSelections});
						} else {
							if(responseKey[0].numberOfSegments.number == resp.numberOfSegments || responseKey[0].numberOfSelections.number == resp.numberOfSelections) {
								match = {'score': score};
								if(responseKey[0].numberOfSegments.number == resp.numberOfSegments) {
									match.score += parseFloat(responseKey[0].numberOfSegments.score);
								}
								if(responseKey[0].numberOfSelections.number == resp.numberOfSelections) {
									match.score += parseFloat(responseKey[0].numberOfSelections.score);
								}
							}
						}
					} else if(matchtype == 'point') {
						for(var key in responseKey) {
							var answer = responseKey[key].answer;
							var tolx = Math.abs(answer.x - resp[0]);
							var toly = Math.abs(answer.y - resp[1]);
							if(tolx <= answer.tolerance && toly <= answer.tolerance) {
								match = responseKey[key];
								responseKey.splice(key, 1);
							}
						}
					} else if(matchtype == 'line') {
						for(var key in responseKey) {
							var answer = responseKey[key].answer;
							var incorrectConsider = false;
							
							var keysAns = _.keys(answer);
							
							for(var i in keysAns) {
								var prop = answer[keysAns[i]];
								if(prop.consider) {
									var keysIn = _.keys(prop);
									
									if(_.contains(keysIn, 'min')) {  //[x,y,tolerance] [min,max,tolerance]
										if(prop.max != null && prop.max != undefined) {
											if(resp[keysAns[i]] < prop.min || resp[keysAns[i]] > prop.max) {
												incorrectConsider = true;
												break;
											}
										} else {
											if(Math.abs(prop.min - resp[keysAns[i]]) > prop.tolerance) {
												incorrectConsider = true;
												break;
											}
											
										}
									}
									
									if(_.contains(keysIn, 'x')) {
										var tolx = Math.abs(prop.x - resp[keysAns[i]][0]);
										var toly = Math.abs(prop.y - resp[keysAns[i]][1]);
										
										if(tolx > prop.tolerance || toly > prop.tolerance) {
											incorrectConsider = true;
											break;
										}
										
									}
								}
							}
							
							if(!incorrectConsider) {
								match = responseKey[key];
							}
						}
					}
					
					if(match != undefined) {
						if(scoringMethod == 'partialcredit') {
							score = score + parseFloat(match.score);
						}
					} else {
						if (scoringMethod == 'correctOnly') {
							incorrect = true;
							break;
						}
					}
					
				}
			} else {
				var match = undefined;
				if(matchtype == 'drop') {
					match = _.findWhere(responseKey, {dropid: resp.dropId, foilid: resp.foilId});
				} else if(matchtype == 'drag') {
					match = _.findWhere(responseKey, {dragid: resp.dragId, foilid: resp.foilId});
				} else if(matchtype == 'value') {
					match = _.findWhere(responseKey, {value: resp.value, foilid: resp.foilId});
				} else if(matchtype == 'index') {
					match = _.findWhere(responseKey, {index: resp.index, foilid: resp.foilId}) || _.findWhere(responseKey, {index: ''+resp.index, foilid: resp.id}) || _.findWhere(responseKey, {index: resp.index, foilid: resp.id});
				} else if(matchtype == 'select') {
					match = _.findWhere(responseKey, {foilid: resp});
				} else if(matchtype == 'optionid') {
					match = _.findWhere(responseKey, {optionId: resp.options[0], foilId: resp.foilId});
				} else if(matchtype == 'ml') {
					match = _.findWhere(responseKey, {leftid: resp.srcid, textid: resp.destid}) || _.findWhere(responseKey, {leftid: resp.destid, textid: resp.srcid});
				} else if(matchtype == 'pos') {
					if(scoringMethod == 'correctOnly') {
						match = _.findWhere(responseKey, {numberOfSegments: ''+resp.numberOfSegments, numberOfSelections: ''+resp.numberOfSelections});
					} else {
						if(responseKey[0].numberOfSegments.number == resp.numberOfSegments || responseKey[0].numberOfSelections.number == resp.numberOfSelections) {
							match = {'score': score};
							if(responseKey[0].numberOfSegments.number == resp.numberOfSegments) {
								match.score += parseFloat(responseKey[0].numberOfSegments.score);
							}
							if(responseKey[0].numberOfSelections.number == resp.numberOfSelections) {
								match.score += parseFloat(responseKey[0].numberOfSelections.score);
							}
						}
					}
				} else if(matchtype == 'point') {
					
					for(var key in responseKey) {
						var answer = responseKey[key].answer;
						var tolx = Math.abs(answer.x - resp[0]);
						var toly = Math.abs(answer.y - resp[1]);
						if(tolx <= answer.tolerance && toly <= answer.tolerance) {
							match = responseKey[key];
							responseKey.splice(key, 1);
						}
					}
				} else if(matchtype == 'line') {  // check all [xIntercept, yIntercept, slope, end, start] -- min< response <max  OR Math.abs(response - [x/y/min]) < tolerance
					for(var key in responseKey) {
						var answer = responseKey[key].answer;
						var incorrectConsider = false;
						
						var keysAns = _.keys(answer);
						
						for(var i in keysAns) {
							var prop = answer[keysAns[i]];
							if(prop.consider) {
								var keysIn = _.keys(prop);
								
								if(_.contains(keysIn, 'min')) {  //[x,y,tolerance] [min,max,tolerance]
									if(prop.max != null && prop.max != undefined) {
										if(resp[keysAns[i]] < prop.min || resp[keysAns[i]] > prop.max) {
											incorrectConsider = true;
											break;
										}
									} else {
										if(Math.abs(prop.min - resp[keysAns[i]]) > prop.tolerance) {
											incorrectConsider = true;
											break;
										}
										
									}
								}
								
								if(_.contains(keysIn, 'x')) {
									var tolx = Math.abs(prop.x - resp[keysAns[i]][0]);
									var toly = Math.abs(prop.y - resp[keysAns[i]][1]);
									
									if(tolx > prop.tolerance || toly > prop.tolerance) {
										incorrectConsider = true;
										break;
									}
									
								}
							}
						}
						
						if(!incorrectConsider) {
							match = responseKey[key];
						}
					}
				} else if(matchtype == 'st-lines') {
					var correct = true;
					for(var key in responseKey) {
						var answer = responseKey[key].answer;//.lines[0];
						var incorrectConsider = false; //incorrectConsider
						
						var propertyKeys = _.keys(answer); // [parallel, perpendicular, intersecting, lines]
						for(var i in propertyKeys) {
							if(parseInt(answer.numberOfLines) == 2) { // if more than two lines.. don't check these parallel, perpendicular, intersecting
							
								var ansProp = answer[propertyKeys[i]];
								if(ansProp.consider) {
									var tolerance = Number(ansProp.tolerance);
									
									if(propertyKeys[i] == 'parallel' && !incorrectConsider) { // if no tolerance m1=m2 else abs(m1-m2) within tolerance
										var m1 = foilSelection[0].slope;;
										var m2 = foilSelection[1].slope;;
										if(!tolerance) {
											if(m1 != m2) {
												incorrectConsider = true;
												break;
											}
										} else if(Math.abs(m1-m2) > tolerance) { // diff of slope within tolerance range
											incorrectConsider = true;
											break;
										}
									} else if(propertyKeys[i] == 'perpendicular' && !incorrectConsider) {
										var m1 = foilSelection[0].slope;
										var m2 = foilSelection[1].slope;
										// check if product of slopes within tolerance range of -1.
										if(Math.abs( (m1*m2) - (-1)) > tolerance) {
											incorrectConsider = true;
											break;
										}
									} else if(propertyKeys[i] == 'intersecting' && !incorrectConsider) {
										
										/*
										 * y1 = ax + c, y2 = bx + d
										 * poi = ( (d-c/a-b) , a.(d-c/a-b) + c)
										 */
										
										var a = foilSelection[0].slope;
										var c = foilSelection[0].yIntercept;
										
										var b = foilSelection[1].slope;
										var d = foilSelection[1].yIntercept;
										
										var poi = {'x':null, 'y':null};
										poi['x'] = ((d-c)/(a-b));
										poi['y'] = (a*((d-c)/(a-b))) + c;
										
										// check if x,y coordinates of poi lie within tolerance range
										if(Math.abs(poi.x - ansProp.x) > tolerance || Math.abs(poi.y - ansProp.y) > tolerance) {
											incorrectConsider = true;
											break;
										}
										
									}
								}
							}
							
							if(propertyKeys[i] == 'lines' && !incorrectConsider) {
								var usedLineKeys = new Array();
								for(var j in foilSelection) {  // take one line from response and compare with all answers
									var responseLine = foilSelection[j];
									var matchedLines = {};
									
									for(var k=0;k<answer.lines.length;k++) { // iterate over the key
										
										if(!_.contains(usedLineKeys, k.toString())) {
											var keyLine = answer.lines[k];
											matchedLines[k]={'startPoint': null, 'endPoint': null, 'slope': null, 'xIntercept': null, 'yIntercept': null};
											
											var lineProp = _.keys(keyLine); 
											for(l in lineProp) { // [xintercept, yintercept, slope, start, end]
												var prop = keyLine[lineProp[l]];
												//console.log(prop);
												if(prop.consider) {
													var keysIn = _.keys(prop);
													if(_.contains(keysIn, 'min')) {
														if(prop.max) {
															if(responseLine[lineProp[l]] >= Number(prop.min) && responseLine[lineProp[l]] <= Number(prop.max)) {
																//console.log("1");
																//console.log(lineProp[l]);
																//console.log(responseLine[lineProp[l]]);
																//console.log(prop.min+ ' '+ prop.max);
																matchedLines[k][lineProp[l]] = Math.abs(((Number(prop.min)+Number(prop.max))/2) - responseLine[lineProp[l]]);
															} else {
																delete matchedLines[k];
																break;
															}
														} else {
															if(Math.abs(Number(prop.min) - responseLine[lineProp[l]]) <= prop.tolerance) {
																//console.log("2");
																matchedLines[k][lineProp[l]] = Math.abs(Number(prop.min) - responseLine[lineProp[l]]);
															} else {
																delete matchedLines[k];
																break;
															}
															
														}
														
													} else if(_.contains(keysIn, 'x')) {
														var tolx = Math.abs(Number(prop.x) - responseLine[lineProp[l]][0]);
														var toly = Math.abs(Number(prop.y) - responseLine[lineProp[l]][1]);
														
														if(tolx <= prop.tolerance && toly <= prop.tolerance) {
															//incorrectConsider = true;
															//break;
															//console.log("dist");
															//console.log(responseLine[lineProp[l]][0] +'  '+prop.x+ ' '+prop.y+' '+responseLine[lineProp[l]][1]);
															var dist = Math.sqrt((Number(prop.x) - responseLine[lineProp[l]][0])^2+(Number(prop.y) - responseLine[lineProp[l]][1])^2);
															matchedLines[k][lineProp[l]] = dist;
															//console.log("3");
														} else {
															delete matchedLines[k];
															break;
														}
														
													}
													
													
													
												}
											}
										}
									}
									//console.log(matchedLines);
									
									
									if(_.size(matchedLines) > 0) {
										// choose the closest line here from the stats collected when conditions are satisfied
										if(_.size(matchedLines) == 1) { // just one line in matchedLines array.. so thats the only choice
											usedLineKeys.push(_.keys(matchedLines)[0]);
										} else { // choose the best line from among the options in matchedLines array
												 // first check start and end distances, then yintercept	
											var keys = _.keys(matchedLines);
											var start = _.pluck(matchedLines, 'startPoint');
											var end = _.pluck(matchedLines, 'endPoint');
											//var slope = _.pluck(matchedLines, 'slope');
											//var yintercept = _.pluck(matchedLines, 'yintercept');
											
											if(_.size(start) > 0) {
												var sumDist = new Array();
												$.each(start, function(i, val){
													sumDist.push(val + end[i]);
												});
												
												var index = sumDist.indexOf(Math.min.apply(Math, sumDist));
												usedLineKeys.push(_.keys(matchedLines)[index]);
											} else {
												var slope = _.pluck(matchedLines, 'slope');
												var index = slope.indexOf(Math.min.apply(Math, slope));
												usedLineKeys.push(_.keys(matchedLines)[index]);
											}
											
										}
										
									} else {
										incorrectConsider = true;
										break;
									}
									
									
								}
								if(incorrectConsider) {
									break;
								}
							}
							
						}
						if(incorrectConsider) {
							correct = false;
							score = 0;
							break;
						}
					}
					if(correct) {
						match = {'score': fullscore};
					}
				} else if(matchtype == 'partitions') {
					//var incorrect = false;
					score = 0;
					if(keysObj.partitions.numberOfPartitions == resp.numberOfPartitions) {
						if(scoringMethod == 'partialcredit') {
							score += Number(keysObj.partitions.score);
						}
					} else {
						incorrect = true;
					}
					
					//match = {'score':0};
					if(!incorrect || (incorrect && scoringMethod == 'partialcredit')) {
						for(var i in resp.markers) {
							var matchMarker = _.findWhere(keysObj.markers, {pointIndex : resp.markers[i].pointIndex, value : ''+resp.markers[i].numberOfMarkers});
							if(matchMarker) {
								if(scoringMethod == 'partialcredit') {
									score += Number(matchMarker.score);
								}
							} else {
								if(scoringMethod == 'correctOnly') {
									incorrect = true;
									break;
								}
								
							}
						}
					}
					
					if(!incorrect && scoringMethod == 'correctOnly') {
						match = {};
						match['score'] = score;
					}
					
					
				} else if(matchtype == 'psquare') {
					for(var i=0; i<responseKey.length; i++) {
						if((responseKey[i].rowHeaders.join(' ') == resp.rowHeaders.join(' ')) && (responseKey[i].columnHeaders.join(' ') == resp.columnHeaders.join(' '))) {
							if(responseKey[i].value.join(' ') == resp.value.join(' ')) {
								match = responseKey[i];
								break;
							}
						}
					}
				}
				
				if(match != undefined) {
					if(scoringMethod == 'partialcredit') {
						score = score + parseFloat(match.score);
					}
				} else {
					if (scoringMethod == 'correctOnly') {
						incorrect = true;
						break;
					}
				}
			}
			if(scoringMethod == 'correctOnly' && incorrect) {
				break;
			}
		}
		
		if(scoringMethod == 'correctOnly' && !incorrect) {
			score = fullscore;
		}
		
		return score;
	}
	
	function checkRange() {
		
	}
	
	return {
		fetch : fetch
	};
	
})();