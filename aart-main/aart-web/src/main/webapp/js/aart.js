(function(aart, $, undefined) {
	
	aart.clearMessages = function() {
		var msg = $('.success_message, .error_message, .info_message');
	    
	    msg.each(function(){
	        if ($(this).css("display") !== "none") {
	        	//$(this).fadeOut(20000);
	            $(this).hide();
	        }
	    });
	};
	
	aart.buildRosterAccordion = function(selector, delegateFn, errSelector, selectedOrg) {
		$.ajax({
			url: 'getRostersByTeacher.htm',
			dataType: 'json',
			type: "POST",
            data: {
                selectedOrg: selectedOrg
            },
			success: function(data) {
				if (data !== undefined && data !== null && data.length > 0) {
					var accordion = $(selector);
					if (accordion.length > 0) {
						for (var i = 0, length = data.length; i < length; i++) {
							var accordionName;
							if (data[i].subject.name !== null) {
								accordionName = data[i].subject.name + " - " + data[i].courseSectionName + " - " + data[i].teacher.surName;
							} else {
								accordionName = " - " + data[i].courseSectionName + " - " + data[i].teacher.surName;
							}
							accordion.append("<h3><a href='#'>" + accordionName + "</a><input class='rosterId' type='hidden' value='" + data[i].id +"'/></h3><div class='accordion'></div>");
						}
	
						//build the accordion panes.
						accordion.accordion({
							collapsible: true,
							alwaysOpen: false,
							active: false,
					        autoHeight: false
						}).delegate("h3.ui-state-active", "click", delegateFn);
					}
				} else {
					$(errSelector).show();
					setTimeout("aart.clearMessages()", 3000);
				}
			}
		});
	};
	
	aart.isValidPositiveNumber = function(number) {
		return typeof(number) !== undefined && number !== null && !isNaN(Number(number));
	};
}(window.aart = window.aart || {}, jQuery));