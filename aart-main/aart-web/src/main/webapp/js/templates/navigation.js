var sessionTimeOutHeaderNavigationPage = $('#sessionTimeOutHeaderNavigationPage').val();
$(function() {
	setNavigation();
	prettifyMainDropdowns();
	
	try {
		$('#session_timeout_overlay').dialog({
			autoOpen: false,
			modal: true, 
		    closeOnEscape: false,
		    draggable: false,
		    resizable: false,
			width: 400,
			height: 200,
			title: sessionTimeOutHeaderNavigationPage,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			    $(".ui-dialog-titlebar-close", widget).hide();
			},
			buttons: {
		        'Yes, Keep Working': function(){
		                $(this).dialog('close');
		        },
		        'No, Sign Out': function(){
		                // fire whatever the configured onTimeout callback is.
		                // using .call(this) keeps the default behavior of "this" being the warning
		                // element (the dialog in this case) inside the callback.
		                $.idleTimeout.options.onTimeout.call(this);
		        }
			}
		});

		//cache a reference to the countdown element so we don't have to query the DOM for it on each ping.
		var $countdown = $("#dialog-countdown");
		var timeOutSessionMaxInactiveIntervalOfPage = $('#timeOutSessionMaxInactiveIntervalOfPage').val();
		var navigationRequestContextPathForCurrentPage = $('#navigationRequestContextPathForCurrentPage').val();
		//start the idle timer plugin
		$.idleTimeout('#session_timeout_overlay', 'div.ui-dialog-buttonpane button:first', {
		     idleAfter: timeOutSessionMaxInactiveIntervalOfPage,
		     pollingInterval: timeOutSessionMaxInactiveIntervalOfPage,
		     keepAliveURL: navigationRequestContextPathForCurrentPage+'/keep-alive.htm',
		     serverResponseEquals: 'OK',
		     onTimeout: function(){
		             window.location = navigationRequestContextPathForCurrentPage+'/j_spring_security_logout';
		     },
		     onIdle: function(){
		             $(this).dialog("open");
		     },
		     onCountdown: function(counter){
		             $countdown.html(counter); // update the counter
		     }
		});
		
		$('#userDefaultAssessmentProgram').on('change', function () {
			var $role = $(this);
			$('#userDefaultGroup').prop('disabled', true);
			$('#userDefaultOrganization').prop('disabled', true);
			var prevLink = $(location).attr('pathname');
			$.post( navigationRequestContextPathForCurrentPage+'/userHome/switchRole.htm', {
				organizationId:$('#userDefaultOrganization').val(),
				currentOrganizationType:$('#userCurrentOrganizationType').val(),
				groupId:$('#userDefaultGroup').val(),
				userAssessmentProgramId:$role.val()
			},function( data ) {}).always(function() {
				if(prevLink==null || prevLink=='' || prevLink==undefined)
					window.location = navigationRequestContextPathForCurrentPage+'/userHome.htm';
				else
					window.location = navigationRequestContextPathForCurrentPage+'/userHome.htm?previousURL='+prevLink;
			});
	    });
		
	} catch (e){
		console.log(e);
	}
	//
});

function setNavigation() {
    var path = window.location.pathname;
    path = path.replace(/\/$/, "");
    path = decodeURIComponent(path);
    
    // make it clear what page we're on in the nav
	$('.main-nav a[href$="'+path+'"]').tab('show');
}

function prettifyMainDropdowns(){
	$('.main-nav .dropdown').on('show.bs.dropdown', function(){
		$(this).find('.dropdown-menu').first().stop(true, true).slideDown(50);
	});
	$('.main-nav .dropdown').on('hide.bs.dropdown', function(){
		$(this).find('.dropdown-menu').first().stop(true, true).slideUp(50);
	});
	
}
