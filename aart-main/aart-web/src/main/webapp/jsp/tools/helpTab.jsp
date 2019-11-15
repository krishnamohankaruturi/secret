<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/helpTab.js?v=1'/>"></script>
<style>
.hidehelpTab {
	display: none
}
#helptab_mgmt_success {
	clear: both;
}
#helptab_mgmt_error {
	clear: both;
	color: red;
}
.faqTitleLink {
	font-family: helvetica;
   	font-size: 14pt;
   	color: #2b4098;
   	text-decoration: underline;
   	cursor: pointer;
   	display: block;
   	margin-top: 20px;
}
.helpContentContentContainer *{
	font-family: helvetica;
   	font-size: 12pt;
   	color: #000000;
}
#fagsContainer{
	margin-top: 20px;
}
.helpContentContent {
	margin-bottom: 30px;
	margin-top: 30px;
}
.helpPdfImage{
	width: 830px;
}
</style>

<div id="helpTabs" class="panel_full">
	<aside id="help_sidebar" >
		<div id="help_menu" style="display: none;">
			<ul>
				<li class="active"><a href="#" style="text-align: center;cursor:auto;"><fmt:message key="tools.help.faqs.main.label" /></a>
					<c:forEach var="helpTopic" items="${helpTopics}">
					<ul class="show">
						<li><a id="tab_helpTopic${helpTopic.id}" data-id="${helpTopic.id}" href="#helpTopicsContainer"><c:out value="${helpTopic.name}"></c:out></a></li>
					</ul>
					</c:forEach>
				</li>
			</ul>
		</div>
	</aside>
	<div id="help_option">
		<div id="tabs_faqs_popular_ques" >
			<jsp:include page="faqsContent.jsp" />
		</div>
		<div id="helptab_mgmt_success"></div>
		<div id="helptab_mgmt_error"></div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$(document).on("click", "#menuToggle", function(e) {
			e.preventDefault();
			var left = "-185px";
			var padLeft = "0";
			if ($(this).hasClass("menuShow")) {
				left = "0px";
				padLeft = "185px";
			}
			$("#sidebar").stop().animate({
				"left" : left
			}, 300);
			$("#container").stop().animate({
				"padding-left" : padLeft
			}, 300, function() {
				$("#menuToggle").toggleClass("menuShow menuHide");
			});
		});
		$('#help_menu > ul > li > ul > li a, .helpTopicLinks').on("click", function(e) {
			e.preventDefault();
			$('#helptab_mgmt_success').html('');
			$('#helptab_mgmt_error').html('');

			var hrf = $(this).attr('href');
			if (hrf == undefined) {
				return;
			}
			$('#help_menu li ul li a').each(function() {
				$(this).parent().removeClass('current');
				var hrfvar = $(this).attr('href');
				if (hrfvar != undefined && hrfvar != "#"){
					$(hrfvar).hide();
					$('#ViewHelpTopicWindow').css('width', '74%').css('float', 'left').css('padding', '1%');
					$('#ViewHelpTopicWindow').show();
				}
			});
			if (hrf != "#") {
				var menuItemId = $(this).attr('id');
				var helpTopicId  = $(this).data('id');
				$('#'+menuItemId).parent().parent().find('li').addClass('current');
				$.ajax({
					url : 'getHelpContentsByHelpTopicId.htm',
					data : {id:helpTopicId},
					dataType : 'JSON',
					type : 'GET'
				}).done(function(data) {
						if (data != undefined && data != null && data != '') {
							if (data.status === 'success') {
								if (data.helpContents) {
									$('#fagsContainer').html('');
									
									for (i=0; i<data.helpContents.length; i++) {
										var helpTitle = $('<a class="faqTitleLink">');
										helpTitle.text(data.helpContents[i].helpTitle);
										helpTitle.attr('id', 'helpContent'+data.helpContents[i].id);
										helpTitle.data('id', data.helpContents[i].id);
										helpTitle.data('status','');
										
										$(helpTitle).on("click", function(){
											var helpContentId = $(this).data('id');
											var helpContentLink = $(this);
											if(helpContentLink.data('status') === ''){

												$.ajax({
													url : 'getViewHelpContentById.htm',
													data : {id:helpContentId},
													dataType : 'JSON',
													type : 'GET'
												}).done(function(data) {
														if (data != undefined && data != null && data != '') {
															if (data.status === 'success') {
																helpContentLink.data('status','loaded');
																$(helpContentLink).next('.helpContentContent').show();
																$(helpContentLink).next('.helpContentContent').html('');
																var helpContentElement = $('<div class="helpContentContentContainer">');
																helpContentElement.html(data.helpContent.content);
																$(helpContentLink).next('.helpContentContent').append(helpContentElement);
																
																if(data.helpContent.fileName != null && data.helpContent.fileName != ''){
							 										var filePath= $('#helpContentHostUrl').val();
							 										var fileName = $('<a>');
																	fileName.attr('href', filePath + 'getHelpContentFile.htm?fileName='+data.helpContent.fileName);
																	fileName.text(data.helpContent.fileName);
																	$(helpContentLink).next('.helpContentContent').append(fileName);
																	
																	if(data.helpContent.fileName.toLowerCase().indexOf('pdf') > 0){
																		var pdfFileName = data.helpContent.fileName;
																		$.ajax({
																			url : 'getHelpContentPDFFile.htm',
																			data : {fileName:pdfFileName},
																			dataType : 'JSON',
																			type : 'GET'
																		}).done(function(data) {
																				if (data != undefined && data != null && data != '') {
																					if (data.status === 'success') {
																						var filePath= $('#helpContentHostUrl').val();
																						$(helpContentLink).next('.helpContentContent').append($('<div id="imagesList">'));
																						$.each(data.hcPdfImages, function(index){
																						    $('<img class="helpPdfImage" id="helpPdfImage'+index+'" />').appendTo('#imagesList'); 
																						});
																						$.each(data.hcPdfImages, function(index){
																							$('#imagesList, #helpPdfImage'+index).attr('src', filePath+ 'getHelpContentFile.htm?fileName='+this+'&op=test');
																						});
																					}	
																				}
																		}).fail( function(jqXHR, textStatus, errorThrown) {
																				console.log(errorThrown);
																		});
																		
																	}
							 									}
															}
														}
												}).fail( function(jqXHR, textStatus, errorThrown) {
														console.log(errorThrown);
													});
											} else if (helpContentLink.data('status') === 'loaded' || helpContentLink.data('status') === 'visible'){
												helpContentLink.data('status','hidden');
												helpContentLink.next('.helpContentContent').hide();
											} else {
												helpContentLink.data('status','visible');
												helpContentLink.next('.helpContentContent').show();
											}
										});
										$('#fagsContainer').append($(helpTitle));
										$('#fagsContainer').append($('<div class="helpContentContent">'));
					           	 	}
								}
							}
						}
				}).fail( function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
				});
				
				
				
			}
			e.preventDefault();
		});
		$('#help_menu > ul > li > a').on("click", function(e) {
			e.preventDefault();
			var exist = $(this).attr('href');
			if (exist == undefined){
				return;
			}
		});

	});
</script>