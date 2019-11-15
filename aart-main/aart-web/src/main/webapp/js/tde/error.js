var error = new function() {
	this.execute = function(event, jqxhr, settings, exception){
		if(jqxhr != null && jqxhr != undefined && jqxhr.responseText != '' && !/saveList.htm/.test(settings.url)){

			if (/^[\],:{}\s]*$/.test(jqxhr.responseText.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
			                   .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
			                   .replace(/(?:^|:|,)(?:\s*\[)+/g, '')) || /^{/.test(jqxhr.responseText)) {
				jqxhr.responseText = messages.testHome.genericError;
			}
			
			$('body').append(new EJS({
				url : 'js/views/error.ejs'
			}).render({errormessage : jqxhr.responseText}));
			$('#erroridok').on('click', function() {
				window.location.replace(contextPath + "/studentHome.htm");
			});
			//error.showAlertBox('errorid');
			var elem = 'errorid';
			var windowWidth = document.documentElement.clientWidth,
				windowHeight = document.documentElement.clientHeight,
			popupHeight = $(window).height()*0.4;
			popupWidth = $('#'+elem+' .overlay-content').width(); 
			
			$('#'+elem+' .overlay-content').css({"position": "absolute",  
							  "top": windowHeight/2-popupHeight/2,
							  "left": windowWidth/2-popupWidth/2  });  
		
			$('#'+elem+' .overlay-content').show();
			$('#'+elem+' .overlay').show();
			$('#'+elem).show();
			if(!/ipad/ig.test(userAgent) && elem!='ticketConfirmation') {
				$('#'+elem+' .overlay-content').draggable({ containment: "window" });
			}
			var D = document;
			$('#' + elem).height(Math.max(Math.max(D.body.scrollHeight, D.documentElement.scrollHeight), Math.max(D.body.offsetHeight, D.documentElement.offsetHeight), Math.max(D.body.clientHeight, D.documentElement.clientHeight))-8);			
		}
	};
	
	this.show = function(error) {
		$('body').append(new EJS({
			url : 'js/views/error.ejs'
		}).render({errormessage : error}));
		$('#erroridok').on('click', function() {
			window.location.replace(contextPath + "/studentHome.htm");
		});
		
		test.showAlertBox('errorid');
	};
};