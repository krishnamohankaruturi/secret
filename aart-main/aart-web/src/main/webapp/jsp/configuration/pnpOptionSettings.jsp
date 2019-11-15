<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="<c:url value='/css/pnpOptionSettings.css'/>" type="text/css" />
<div id="pnpOptionsControlDiv">
	
	<div class="pnp_description">
		<p>Set the availability of PNP options by assessment program. Option settings will apply to all states affiliated
			with the assessment program. Option settings may be overridden at the state level by clicking the State link for the
			option.</p>
	</div>
	<div id="pnpOptionsNotificationMsg"></div>
	<div id="pnpOptionsTabs" class="_bcg">
		<ul class="nav nav-tabs sub-nav">
			<li class="nav-item"><a href="#tabs_displayEnhancements" ><fmt:message
						key="label.pnp.display_enhancements" /></a></li>
			<li class="nav-item"><a href="#tabs_languageAndBraille" ><fmt:message
						key="label.pnp.language_braille" /></a></li>
			<li class="nav-item"><a href="#tabs_audioAndEnvironments" ><fmt:message
						key="label.pnp.audio_environment" /></a></li>
			<li class="nav-item"><a href="#tabs_otherSupport" ><fmt:message
						key="label.pnp.system_independent" /></a></li>
		</ul>
		
		<c:set var="categoryKey" value="${displayEnhancements.id}" />
		<c:set var="pnpCategoriesMap" value="${pnpAccomodationsMap[categoryKey]}" />
		<div id="tabs_displayEnhancements" style="width: 1094px;margin-left: -21px;" class="hidden">
			<table>
				<thead>
					<tr>
						<th class="fixed pnpOptionsFirstColumn">Accommodation</th>
						<c:forEach var="supportedAp" items="${supportedAps}">
							<th class="fluid">${supportedAp}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pnpCategory" items="${pnpCategoriesMap}">
						<tr>
							<td class="pnpOptionsFirstColumn"><span class="orgNameContent">${sortedAccomodations[pnpCategory.key]}</span></td>
							<c:forEach var="pnpAssessmentProgram" items="${pnpCategory.value}">
								<c:set var="pnpAccomodation" value="${pnpAssessmentProgram.value}"/>
								<c:if test="${not empty pnpAccomodation.assessmentProgram}">
									<c:if test="${fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kap') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'cpass') 
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kelpa2') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'dlm')
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart2')
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'pltw')}">
										<td>
											<div class="onoffswitch">
												<input type="checkbox" class="onoffswitch-checkbox myonoffswitch" 
													data-ap="${pnpAccomodation.assessmentProgram}" data-categoryid="${pnpAccomodation.categoryId}"
													data-pianacid="${pnpAccomodation.pianacid}"
													<c:if test="${(pnpAccomodation.viewOption == 'enable' || 
														pnpAccomodation.viewOption == null)}">
					                                	checked="true" 
					                                </c:if> />
												<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner"></span> <span
													class="onoffswitch-switch"></span>
												</label>
											</div>
											<c:if test="${fn:length(pnpStateSettingsMap[pnpAccomodation.assessmentProgram]) gt 0}">
												<a href="#" class="pnpStateLink" data-ap="${pnpAccomodation.assessmentProgram}" 
												data-pianacid="${pnpAccomodation.pianacid}" data-accomodationname="${sortedAccomodations[pnpCategory.key]}"
												><span>+</span> State</a>
											</c:if>
										</td>
									</c:if>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:set var="categoryKey" value="${languageAndBraille.id}" />
		<c:set var="pnpCategoriesMap" value="${pnpAccomodationsMap[categoryKey]}" />
		<div id="tabs_languageAndBraille" style="width: 1094px;margin-left: -21px;" class="hidden">
			<table>
				<thead>
					<tr>
						<th class="fixed pnpOptionsFirstColumn">Accommodation</th>
						<c:forEach var="supportedAp" items="${supportedAps}">
							<th class="fluid">${supportedAp}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pnpCategory" items="${pnpCategoriesMap}">
						<c:if test="${(sortedAccomodations[pnpCategory.key] != 'EBAE' && sortedAccomodations[pnpCategory.key] != 'UEB')}">
							<tr>
								<td class="pnpOptionsFirstColumn"><span class="orgNameContent">${sortedAccomodations[pnpCategory.key]}</span></td>
								<c:forEach var="pnpAssessmentProgram" items="${pnpCategory.value}">
									<c:set var="pnpAccomodation" value="${pnpAssessmentProgram.value}"/>
									<c:if test="${not empty pnpAccomodation.assessmentProgram}">
										<c:if test="${fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kap') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'cpass') 
											or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kelpa2') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'dlm')
											or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart2')
											or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'pltw')}">
											<td>
												<div class="onoffswitch">
													<input type="checkbox" class="onoffswitch-checkbox myonoffswitch" 
														data-ap="${pnpAssessmentProgram.key}" data-categoryid="${pnpAccomodation.categoryId}"
														data-pianacid="${pnpAccomodation.pianacid}"
														<c:if test="${(pnpAccomodation.viewOption == 'enable' || 
															pnpAccomodation.viewOption == null)}">
						                                	checked="true" 
						                                </c:if> />
													<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner"></span> <span
														class="onoffswitch-switch"></span>
													</label>
												</div>
												<c:if test="${fn:length(pnpStateSettingsMap[pnpAccomodation.assessmentProgram]) gt 0}">
													<a href="#" class="pnpStateLink" data-ap="${pnpAccomodation.assessmentProgram}" 
													data-pianacid="${pnpAccomodation.pianacid}" data-accomodationname="${sortedAccomodations[pnpCategory.key]}"
													><span>+</span> State</a>
												</c:if>
											</td>
										</c:if>
									</c:if>
								</c:forEach>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:set var="categoryKey" value="${audioAndEnvironmentSupport.id}" />
		<c:set var="pnpCategoriesMap" value="${pnpAccomodationsMap[categoryKey]}" />
		<div id="tabs_audioAndEnvironments" style="width: 1094px;margin-left: -21px;" class="hidden">
			<table>
				<thead>
					<tr>
						<th class="fixed pnpOptionsFirstColumn">Accommodation</th>
						<c:forEach var="supportedAp" items="${supportedAps}">
							<th class="fluid">${supportedAp}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pnpCategory" items="${pnpCategoriesMap}">
						<tr>
							<td class="pnpOptionsFirstColumn"><span class="orgNameContent">${sortedAccomodations[pnpCategory.key]}</span></td>
							<c:forEach var="pnpAssessmentProgram" items="${pnpCategory.value}">
								<c:set var="pnpAccomodation" value="${pnpAssessmentProgram.value}"/>
								<c:if test="${not empty pnpAccomodation.assessmentProgram}">
									<c:if test="${fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kap') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'cpass') 
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kelpa2') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'dlm')
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart2')
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'pltw')}">
										<td>
											<div class="onoffswitch">
												<input type="checkbox" class="onoffswitch-checkbox myonoffswitch" 
													data-ap="${pnpAssessmentProgram.key}" data-categoryid="${pnpAccomodation.categoryId}"
													data-pianacid="${pnpAccomodation.pianacid}"
													<c:if test="${(pnpAccomodation.viewOption == 'enable' || 
														pnpAccomodation.viewOption == null)}">
					                                	checked="true" 
					                                </c:if> />
												<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner"></span> <span
													class="onoffswitch-switch"></span>
												</label>
											</div>
											<c:if test="${fn:length(pnpStateSettingsMap[pnpAccomodation.assessmentProgram]) gt 0}">
												<a href="#" class="pnpStateLink" data-ap="${pnpAccomodation.assessmentProgram}" 
												data-pianacid="${pnpAccomodation.pianacid}" data-accomodationname="${sortedAccomodations[pnpCategory.key]}"
												><span>+</span> State</a>
											</c:if>
										</td>
									</c:if>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:set var="categoryKey" value="${otherSupports.id}" />
		<c:set var="pnpCategoriesMap" value="${pnpAccomodationsMap[categoryKey]}" />
		<div id="tabs_otherSupport" style="width: 1094px;margin-left: -21px;" class="hidden">
			<table>
				<thead>
					<tr>
						<th class="fixed pnpOptionsFirstColumn">Accommodation</th>
						<c:forEach var="supportedAp" items="${supportedAps}">
							<th class="fluid">${supportedAp}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pnpCategory" items="${pnpCategoriesMap}">
						<tr>
							<td class="pnpOptionsFirstColumn"><span class="orgNameContent">${sortedAccomodations[pnpCategory.key]}</span></td>
							<c:forEach var="pnpAssessmentProgram" items="${pnpCategory.value}">
								<c:set var="pnpAccomodation" value="${pnpAssessmentProgram.value}"/>
								<c:if test="${not empty pnpAccomodation.assessmentProgram}">
									<c:if test="${fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kap') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'cpass') 
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'kelpa2') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'dlm')
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart') or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'i-smart2')
										or fn:containsIgnoreCase(pnpAccomodation.assessmentProgram, 'pltw')}">
										<td>
											<div class="onoffswitch">
												<input type="checkbox" class="onoffswitch-checkbox myonoffswitch"
													data-ap="${pnpAssessmentProgram.key}" data-categoryid="${pnpAccomodation.categoryId}"
													data-pianacid="${pnpAccomodation.pianacid}" data-tabtype="othertab"
													<c:if test="${(pnpAccomodation.viewOption != 'hide' || 
														pnpAccomodation.viewOption == null)}">
					                                	checked="true" 
					                                </c:if> />
												<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner-other"></span> <span
													class="onoffswitch-switch"></span>
												</label>
											</div>
											<c:if test="${fn:length(pnpStateSettingsMap[pnpAccomodation.assessmentProgram]) gt 0}">
												<a href="#" class="pnpStateLink" data-ap="${pnpAccomodation.assessmentProgram}" 
												data-pianacid="${pnpAccomodation.pianacid}" data-accomodationname="${sortedAccomodations[pnpCategory.key]}"
											 	data-tabtype="othertab"
												><span>+</span> State</a> 
											</c:if>
										</td>
									</c:if>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div id="pnpStatesDialog" style="display: none;"></div>
<script>
	$(function() {
		$('#pnpOptionsTabs').tabs({
			beforeActivate : function(event, ui) {
				if (ui.newPanel[0].id == 'tabs_displayEnhancements') {
					$('#tabs_displayEnhancements').show();
					$('#tabs_languageAndBraille').hide();
					$('#tabs_audioAndEnvironments').hide();
					$('#tabs_otherSupport').hide();
				} else if (ui.newPanel[0].id == 'tabs_languageAndBraille') {
					$('#tabs_displayEnhancements').hide();
					$('#tabs_languageAndBraille').show();
					$('#tabs_audioAndEnvironments').hide();
					$('#tabs_otherSupport').hide();
				} else if (ui.newPanel[0].id == 'tabs_audioAndEnvironments') {
					$('#tabs_displayEnhancements').hide();
					$('#tabs_languageAndBraille').hide();
					$('#tabs_audioAndEnvironments').show();
					$('#tabs_otherSupport').hide();
				} else if (ui.newPanel[0].id == 'tabs_otherSupport') {
					$('#tabs_displayEnhancements').hide();
					$('#tabs_languageAndBraille').hide();
					$('#tabs_audioAndEnvironments').hide();
					$('#tabs_otherSupport').show();
				}
			}
		});
		$('.pnpStateLink').on("click",function(ev) {
			ev.preventDefault();
			var assessmentProgram = $(this).data('ap');
			var pianacId = $(this).data('pianacid');
			var accomodationName = $(this).data('accomodationname');
			var tabtype = $(this).data('tabtype');
			$.ajax({
                url : 'getPnpStateSettings.htm',
                data : {
                	assessmentProgram : assessmentProgram,
                	pianacId : pianacId
                },
                dataType : 'json',
                type : "POST"
            }).done(function(response) {
                    	if(response.status === 'success'){
                    		var stateDialogHtml = '<div style="color: #5b9dcf;padding: 1%;">Set <b>'+ assessmentProgram + ' '+ accomodationName+'</b> option by State<br/><br/></div>';
                			stateDialogHtml = stateDialogHtml + '<div style="padding: 1%;">By default, the program level setting for this PNP option applies to all states'
                			+' affiliated with the program. Use the controls on this screen to override the current PNP option settings for all students in the state.</div>'+
                			'<div id="pnpStateNotificationsMsg"></div>';
                			$('#pnpStatesDialog').html(stateDialogHtml);
                			//TODO Rajendra : remove hard coding here
                			if(accomodationName == 'Braille'){
                    			stateDialogHtml = '<table><thead><tr><th  class="pnpStateOptionsFirstColumn" style="color: #5b9dcf;" >State</th><th>Braille</th><th>EBAE</th><th>UEB</th></tr></thead>';
                			} else {
                    			stateDialogHtml = '<table><thead><tr><th  class="pnpStateOptionsFirstColumn" style="color: #5b9dcf;" >State</th><td></td></tr></thead>';
                			}            			
                    		$.each(response.pnpStateSettings, function(i, pnpStateSetting){
                    			stateDialogHtml = stateDialogHtml + '<tr><td class="pnpStateOptionsFirstColumn">'
                    			+pnpStateSetting.stateName+'</td><td><div class="onoffswitch parent">'+
    								'<input type="checkbox" class="onoffswitch-checkbox myonoffswitch parent" '+
    								'data-ap="'+pnpStateSetting.assessmentProgram+'" '+
    								'data-pianacid="'+pnpStateSetting.pianacId+'" '+
    								'data-stateid="'+pnpStateSetting.stateId+'" ';
                    				var tabtypeString = '';
                    				if((tabtype  != undefined || tabtype  != null) 
                						&& tabtype === 'othertab'){
                    					stateDialogHtml = stateDialogHtml + 'data-tabtype="othertab" ';
                    					if(pnpStateSetting.viewOption !== 'hide'){
                        					stateDialogHtml = stateDialogHtml + 'checked="'+true + '"';
                        				}
                    					tabtypeString = '-other';
                    				} else {
                    					if(pnpStateSetting.viewOption === 'enable'){
                        					stateDialogHtml = stateDialogHtml + 'checked="'+true + '"';
                        				}
                    				}
                    				stateDialogHtml = stateDialogHtml + ' />'+
    								'<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner'+tabtypeString+'"></span> <span '+
    								'class="onoffswitch-switch"></span>'+
    								'</label></div></td>';
    								// Add child settings here in columns...
    								if(pnpStateSetting.childSettings != null){
    									$.each(pnpStateSetting.childSettings, function(j, childSetting){
    										stateDialogHtml = stateDialogHtml +'<td><div class="onoffswitch child">'+
    											'<input type="checkbox" class="onoffswitch-checkbox myonoffswitch child" '+
    											'data-ap="'+childSetting.assessmentProgram+'" '+
    											'data-pianacid="'+childSetting.pianacId+'" '+
    											'data-stateid="'+childSetting.stateId+'" ';
    			                				var tabtypeString = '';
    			                				if((tabtype  != undefined || tabtype  != null) 
    			            						&& tabtype === 'othertab'){
    			                					stateDialogHtml = stateDialogHtml + 'data-tabtype="othertab" ';
    			                					if(childSetting.viewOption !== 'hide'){
    			                    					stateDialogHtml = stateDialogHtml + 'checked="'+true + '"';
    			                    				}
    			                					tabtypeString = '-other';
    			                				} else {
    			                					if(childSetting.viewOption === 'enable'){
    			                    					stateDialogHtml = stateDialogHtml + 'checked="'+true + '"';
    			                    				}
    			                				}
    			                				stateDialogHtml = stateDialogHtml + ' />'+
    											'<label class="onoffswitch-label" for="myonoffswitch"> <span class="onoffswitch-inner'+tabtypeString+'"></span> <span '+
    											'class="onoffswitch-switch"></span>'+
    											'</label></div></td>';
    									});
    								}
    								stateDialogHtml = stateDialogHtml + '</tr>';
                    		});
                    		
                    		stateDialogHtml = stateDialogHtml + '</table>';
                    		$('#pnpStatesDialog').append(stateDialogHtml);
                    		$('#pnpStatesDialog').find('.onoffswitch').each(function(index){
                    			if($(this).hasClass('parent') && $(this).find('input:checkbox').prop("checked")){
                    				$(this).parent().parent().find('div.child').attr('disabled', false);
                    			} else if($(this).hasClass('parent')){
                    				$(this).parent().parent().find('div.child').attr('disabled', true);
                    			}
                    		});
                    		$('#pnpStatesDialog').find('.onoffswitch').on("click",function(ev) {
                    			ev.preventDefault();
                    			ev.stopPropagation();
                    			var stateId = $(this).find('input').data('stateid');
                    			var assessmentProgram = $(this).find('input').data('ap');
                    			var pianacId = $(this).find('input').data('pianacid');
                    			if ($(this).find('input:checkbox').prop("checked")) {
                    				$(this).find('input:checkbox').prop("checked",false);
                    				var pianacid = $(this).find('input:checkbox').data('pianacid');
                    				// Find other childs and disable if option is disable for parent.
                    				$(this).parent().parent().find('input:checkbox').each(function(index){
                    					if(index === 0 && pianacid === $(this).data('pianacid')){
                    						$(this).parent().parent().parent().find('input:checkbox').prop("checked",false);
                    						$(this).parent().parent().parent().find('div.child').attr('disabled', true);
                    					}
                    				});
                    				if((tabtype  != undefined || tabtype  != null) 
                    						&& tabtype === 'othertab'){
                    					saveStatePnpOptionsOverride('hide', stateId, assessmentProgram, pianacId);
                    				} else {
                    					saveStatePnpOptionsOverride('disable', stateId, assessmentProgram, pianacId);
                    				}
                    			} else {
                    				if($(this).attr('disabled') === 'disabled' || $(this).attr('disabled') === true ){
                    					alert('Operation not allowed until parent option is disabled');
                    				} else {
                    					$(this).find('input:checkbox').prop('checked', true);
                    					if($(this).hasClass('parent')){
                    						$(this).parent().parent().find('div.child').prop('disabled', false);
                    					}
                        				if((tabtype  != undefined || tabtype  != null) 
                        						&& tabtype === 'othertab'){
                        					saveStatePnpOptionsOverride('show', stateId, assessmentProgram, pianacId);
                        				} else {
                        					saveStatePnpOptionsOverride('enable', stateId, assessmentProgram, pianacId);
                        				}
                    				}
                    			}
                    		});
                    		
                    		$('#pnpStatesDialog').dialog({
                				resizable : false,
                				modal : true,
                				width : 600,
                				open: function() {
                					$(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
                					 $('.ui-dialog').removeAttr("role");
                				    },
                				buttons : {
                					"Close" : function(ev) {
                						$(this).dialog("close");
                						return true;
                					}
                				}
                			}).dialog('open');
                    	}
                    });
		});
	});
</script>