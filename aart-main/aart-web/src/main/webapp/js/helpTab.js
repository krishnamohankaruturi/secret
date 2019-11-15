function populateFaqs(type) {

	if (type === undefined || type === null || type === '') {
		type = 'popular';
	}

	$.ajax({
		url : 'getUserFaqs.htm',
		data : {
			faqType : type
		},
		dataType : 'json',
		type : "POST"
	}).done(function(faqs) {
			var varHelpContenthelpImageUrl =  $('#helpContenthelpImageUrl').val();
			if (faqs.helpTopics !== undefined && faqs.helpTopics !== null && faqs.helpTopics.length > 0) {
				$.each(faqs.helpTopics, function(i, helpTopic) {
					var topicId = faqs.helpTopics[i].id;
					var titleSubHeader = $('<span>').text(faqs.helpTopics[i].name + ' ');
					$(titleSubHeader).append($('<img>').attr('src', varHelpContenthelpImageUrl)
						.attr('title', faqs.helpTopics[i].description).css('cursor', 'auto'));
					$("#faqaccordion").append($('<h3>').append($(titleSubHeader)));
					var accordionContent = $('<div class="accordionContent">');
					$.each(faqs.helpTopics[i].helpContent, function (j, helpContent){
						var anchorElement = $('<a>').text(faqs.helpTopics[i].helpContent[j].helpTitle).on('click',  function() {
							$('#viewHelpTopicName').text('');
							$('#viewHelpContentTitle').text('');
							$('#viewHelpContentText').html('');
							var slugURL = faqs.helpTopics[i].helpContent[j].slug;
							$.ajax({
								url : 'helpTopic.htm',
								data : {slugURL:slugURL},
								dataType : 'JSON',
								type : 'GET'
							}).done(function(data) {
									if (data != undefined && data != null && data != '') {
										if (data.status === 'success') {
											if (data.helpContent) {
												$('#viewHelpTopicName').text(data.helpContent.helpTopicName);
												$('#viewHelpContentTitle').text(data.helpContent.helpTitle);
												$('#viewHelpContentText').html(data.helpContent.content);
												if(data.helpContent.fileName != null && data.helpContent.fileName != ''){
													if(data.helpContent.fileName.toLowerCase().indexOf('pdf') > 0){
														$('#viewHelpContentFile').hide();
														$('#viewHelpContentFilePDF').show('');
														$('#viewHelpContentFilePDF').html('');
														var url = $('#hostContextPath').val() + 'getHelpContentFile.htm?fileName='+data.helpContent.fileName;
													}  else {
														$('#viewHelpContentFile').show();
														var filePath= $('#helpContentHostUrl').val();
														$('#viewHelpContentFile').attr('href', filePath + 'getHelpContentFile.htm?fileName='+data.helpContent.fileName);
														$('#viewHelpContentFile').text(data.helpContent.fileName);
													}
												} else {
													$('#viewHelpContentFile').hide();
												}
											}
											$("#faqaccordion").css('width', '25%').css('float', 'right');
											$('#ViewHelpTopicWindow').css('width', '74%').css('float', 'left');
											$('#ViewHelpTopicWindow').show();
										}
									}
							}).fail(function(jqXHR, textStatus, errorThrown) {
									console.log(errorThrown);
							});
						});

						$(accordionContent).append($('<li class="helpQuestion">').html(anchorElement));
					});
					$('#faqaccordion').append(accordionContent);
				});
				var icons = {
					header : "ui-icon-circle-arrow-e",
					activeHeader : "ui-icon-circle-arrow-s"
				};
				$("#faqaccordion").accordion({
					icons : icons
				});
				$('.ui-accordion-content').css('height', 'auto');
				$(".ui-accordion-content").show();
			}
		}
	);
}