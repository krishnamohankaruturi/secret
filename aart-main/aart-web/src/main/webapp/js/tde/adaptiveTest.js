
var apdaptiveTest = new function() { 

	this.calculateInterimTheta = function(results) {

		var interimThetaResult = {};
		var adaptiveTestPartObj = results.adaptiveTestPartObj;
		var adaptiveTestTaskVariantFoils = results.adaptiveTestTaskVariantFoils;
		var adaptiveTestSectionContainerThetas = results.adaptiveTestSectionContainerThetas; 
		var studentResponseTaskVariantFoilId = results.studentResponseTaskVariantFoilId;
		
		if(null != adaptiveTestTaskVariantFoils && null != adaptiveTestPartObj.adaptiveTestParts)
			for(var i in adaptiveTestPartObj.adaptiveTestParts){ 
				if(null != adaptiveTestPartObj.adaptiveTestParts[i])
					for(var x in adaptiveTestTaskVariantFoils){
						//console.log('getNextAdaptiveTestPart task foils adaptiveTestTaskVariantFoils[x] :' + adaptiveTestTaskVariantFoils[x].testPartNumber + ',' + adaptiveTestTaskVariantFoils[x].testSectionContainerNumber + ',' 
								// + adaptiveTestTaskVariantFoils[x].testSectionId + ',' + adaptiveTestTaskVariantFoils[x].taskVariantId + ',' + adaptiveTestTaskVariantFoils[x].adaptiveTestPartSectionContainerSectionFoils.length);
						if(null != adaptiveTestPartObj.adaptiveTestParts[i] && 
								null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers && 
								null != adaptiveTestTaskVariantFoils[x] 
						//&& 
						//null != adaptiveTestTaskVariantFoils[x].testPartNumber && 
						//null != adaptiveTestPartObj.adaptiveTestParts[i].testPartNumber && 
						//adaptiveTestTaskVariantFoils[x].testPartNumber ==  adaptiveTestPartObj.adaptiveTestParts[i].testPartNumber
						){ 
							for(var j in adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers){
								if(null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j] && 
										null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections && 
										null != adaptiveTestTaskVariantFoils[x].testSectionContainerNumber && 
										null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].testSectionContainerNumber && 
										adaptiveTestTaskVariantFoils[x].testSectionContainerNumber ==  adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].testSectionContainerNumber){
									for(var k in adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections){
										if(null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k] && 
												null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks && 														 
												null != adaptiveTestTaskVariantFoils[x].testSectionId && 
												null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].testSectionId && 
												adaptiveTestTaskVariantFoils[x].testSectionId ==  adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].testSectionId){
											for(var l in adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks){
												if(null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks[l] && 
														null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks[l].adaptiveTestPartSectionContainerSectionFoils && 
														null != adaptiveTestTaskVariantFoils[x].taskVariantId && 
														null != adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks[l].taskVariantId && 
														adaptiveTestTaskVariantFoils[x].taskVariantId ==  adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks[l].taskVariantId){
													adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks[l].adaptiveTestPartSectionContainerSectionFoils = adaptiveTestTaskVariantFoils[x].adaptiveTestPartSectionContainerSectionFoils;
													//console.log('getNextAdaptiveTestPart task foils mapping results :' + adaptiveTestTaskVariantFoils[x].taskVariantId + ',' + adaptiveTestPartObj.adaptiveTestParts[i].adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks[l].adaptiveTestPartSectionContainerSectionFoils.length);
												}
											}
										}
									}
								}
							}
						}
					}
			}

		var numberOfContructs = adaptiveTestPartObj.numberOfconstruct;
		var cJ = 0; //cj
		var numberOfDimensionsOfCurrentSelectionTheta = adaptiveTestPartObj.ndcst;

		if(null != adaptiveTestPartObj && null != adaptiveTestPartObj.adaptiveTestParts){  
			adaptiveTestPartObj.adaptiveTestParts.forEach(function(adaptiveTestPartsObj, i){  
				if(null != adaptiveTestPartsObj.adaptiveTestPartSectionContainers){
					//Section Loop
					adaptiveTestPartsObj.adaptiveTestPartSectionContainers.forEach(function(item, j){ //for( var j in adaptiveTestPartsObj.adaptiveTestPartSectionContainers){
						if(null != adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j] && null != adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections){
							var shortListedLogLikeliHood = null; 
							var interimThetaForsection = null;
							var sectionThetaLogLikeliHood = {};	
							var testSectionContainerThetas = getSectionContainerThetas(adaptiveTestSectionContainerThetas, adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j].testSectionContainerNumber);

							// Sub Section Loop
							if (null != studentResponseTaskVariantFoilId)
								for ( var k in adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections){  
									var tasks = adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j].adaptiveTestPartSectionContainerSections[k].adaptiveTestPartSectionContainerSectionTasks;
									if(null != tasks){
										for(var l in tasks){
											var sectionThetaNodeValues = [];
											var sectionThetaNodeIndex = 0;
											//loop through each theta associated with the section to calculate log for each item under the section
											for(var m in testSectionContainerThetas){ 
												sectionThetaNodeValues = testSectionContainerThetas[m].thetaNodeValues; 
												sectionThetaNodeIndex = testSectionContainerThetas[m].thetaNodeIndex;
												var itemThetaLogLikeliHood = 0;  
												var itemDiscrimationParameter = [];
												var resultValue1 = 0;
												var resultValue2 = 0;
												var responseOrder = 0;
												var formulaNumber = -1; 
												var responseScore = 0;
												var isMaxScore = false;

												var foils = tasks[l].adaptiveTestPartSectionContainerSectionFoils;
												var foilScores = []; 
												for(var fl in foils){
													if (null != foils[fl] && null != foils[fl].responseScore 
															&& foilScores.indexOf(foils[fl].responseScore) == -1)
														foilScores.push(foils[fl].responseScore);   
												}

												var orderedScore = _.sortBy(foilScores, function(num) {
													return num;
												});

												// determine which formula to use
												if(orderedScore.length > 0 && null != studentResponseTaskVariantFoilId[tasks[l].taskVariantId])
													for(var n in foils){
														if (null != tasks[l] && null != foils[n] 
														&& studentResponseTaskVariantFoilId[tasks[l].taskVariantId] == foils[n].foilId){

															for(var score in orderedScore) {
																if(foils[n].responseScore == orderedScore[score])
																	responseScore = parseInt(score) + 1;
															}

															responseOrder = foils[n].responseOrder; 

															if (responseScore == 1 && orderedScore.length != responseScore){ 
																formulaNumber = 0; 
															} else if (orderedScore.length == responseScore){
																isMaxScore = true; 
																formulaNumber = 1;
															} else { 
																formulaNumber = 2;
															}  
														}
													}

												//calculate loglikelihood for only the answered questions
												if(formulaNumber >= 0){
													//get Item discriminator value and response value
													for(var x in tasks[l].adaptiveTestPartSectionContainerSectionItemStatistics){
														var itemStatistic = tasks[l].adaptiveTestPartSectionContainerSectionItemStatistics[x];
														if(itemStatistic.itemStatisticName.slice(0, "a".length) == "a"){
															itemDiscrimationParameter.push(itemStatistic.itemStatisticValue);
														}

														if(responseScore == 1 && itemStatistic.itemStatisticName.slice(0, ("b" + (responseScore + 1)).length) == ("b" + (responseScore + 1))){
															resultValue1 = itemStatistic.itemStatisticValue;
														}else if(isMaxScore && itemStatistic.itemStatisticName.slice(0, ("b" + responseScore).length) == ("b" + responseScore)){
															resultValue1 = itemStatistic.itemStatisticValue;
														}else{
															if(itemStatistic.itemStatisticName.slice(0, ("b" + responseScore).length) == ("b" + responseScore))
																resultValue1 = itemStatistic.itemStatisticValue;
															if(itemStatistic.itemStatisticName.slice(0, ("b" + responseScore).length) == ("b" + (responseScore+1)))
																resultValue2 = itemStatistic.itemStatisticValue; 
														}
													}
													if(resultValue1 != 0.0 || resultValue2 != 0.0){
														itemThetaLogLikeliHood = logLikeLiHood(formulaNumber, cJ, numberOfDimensionsOfCurrentSelectionTheta, itemDiscrimationParameter, sectionThetaNodeValues, resultValue1, resultValue2);
														if(null != sectionThetaLogLikeliHood[sectionThetaNodeIndex]){
															var existingItemLogLikeliHood = sectionThetaLogLikeliHood[sectionThetaNodeIndex];
															sectionThetaLogLikeliHood[sectionThetaNodeIndex] = existingItemLogLikeliHood + itemThetaLogLikeliHood;
														} else{
															sectionThetaLogLikeliHood[sectionThetaNodeIndex] = itemThetaLogLikeliHood;
														}
													}
													//console.log("getNextAdaptiveTestPart sectionThetaNodeIndex :, sectionThetaLogLikeliHood : " + sectionThetaNodeIndex + ',' + sectionThetaLogLikeliHood[sectionThetaNodeIndex]);
												}
											}
										}
									} 
								}

							//Determine the Interim thet for the section 
							for (var key in sectionThetaLogLikeliHood) { 
								//console.log("getNextAdaptiveTestPart thetaIndex : , logLikeliHood : " + key + ',' + sectionThetaLogLikeliHood[key]);
								if(null == shortListedLogLikeliHood || sectionThetaLogLikeliHood[key] > shortListedLogLikeliHood){
									shortListedLogLikeliHood = sectionThetaLogLikeliHood[key];
									interimThetaForsection = key;
								} 
							}	

							//console.log("getNextAdaptiveTestPart testPartNumber: , testSectionContainerNumber: , interimThetaForsection : , shortListedLogLikeliHood : " + adaptiveTestPartsObj.testPartNumber + ',' + adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j].testSectionContainerNumber + ',' + interimThetaForsection + ',' + shortListedLogLikeliHood);
							interimThetaResult[adaptiveTestPartsObj.adaptiveTestPartSectionContainers[j].testSectionContainerId] = interimThetaForsection;											
						}   
					});	 
				} 	
			}); 
		} 
		//return interimThetaResult;
		return JSON.stringify(interimThetaResult);
	};
	
	function getSectionContainerThetas(adaptiveTestSectionContainerThetas, testSectionContainerNumber){
		var toReturn = null;
		if(null != adaptiveTestSectionContainerThetas){
			for (var i in adaptiveTestSectionContainerThetas){ 
				if(null != adaptiveTestSectionContainerThetas[i] 
							&& null != adaptiveTestSectionContainerThetas[i].testSectionContainerNumber
							&& adaptiveTestSectionContainerThetas[i].testSectionContainerNumber == testSectionContainerNumber){
						toReturn = adaptiveTestSectionContainerThetas[i].adaptiveTestPartSectionContainerThetas; 
				} 
			}
		} 
		return toReturn;
	}
    
	function logLikeLiHood(formulaNumber, cJ, ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues, resultValue1, resultValue2){
		//console.log("Enter logLikeLiHood with :" + formulaNumber + ',' + cJ + ',' + ajThetaDLengh + ',' + itemDiscrimationParameter.length + ',' + thetaNodeValues.length + ',' + resultValue1 + ',' + resultValue2);
		var pJtheta = calculatePjTheta(formulaNumber, cJ, ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues, resultValue1, resultValue2);
		var logPjTheta = Math.log(pJtheta);
		//console.log("pJtheta, logPjTheta :" + pJtheta + ',' + logPjTheta);
		return logPjTheta;
	}
	
	function calculatePjTheta(formulaNumber, cJ, ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues, resultValue1, resultValue2){
		var toReturn = 0;
		switch(formulaNumber){
		case 0:
			var ajThetaResponseOne = calculateAjTheta(ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues);
			toReturn = 1 - (cJ + ((1-cJ)/(1+Math.exp((-1 * ajThetaResponseOne) + resultValue1))));
			break;
		case 1:
			var ajThetaResponseLast = calculateAjTheta(ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues);
			toReturn = cJ + ((1-cJ)/(1+Math.exp((-1 * ajThetaResponseLast) + resultValue1)));
			break;
		case 2:
			var ajThetaResponse = calculateAjTheta(ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues);
			var value1 = cJ + ((1-cJ)/(1+Math.exp((-1 * ajThetaResponse) + resultValue1)));
			var value2 = cJ + ((1-cJ)/(1+Math.exp((-1 * ajThetaResponse) + resultValue2)));
			toReturn = value1 - value2; 
		} 
		return toReturn;
	}
	
	function calculateAjTheta(ajThetaDLengh, itemDiscrimationParameter, thetaNodeValues){
		var result = 0;
		for (var d=0; d < ajThetaDLengh; d++){
			result = result + (itemDiscrimationParameter[d] * thetaNodeValues[d]);
		}
		return result;
	}
};