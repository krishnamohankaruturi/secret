/* 
 * guideline.js
 * 
 * Tool for GuideLine.
 * 
 */

var guideLine = new function() {
	var mode;
	var timerScroll = 0;
	
	this.isMode = function(newMode) {
		return mode == newMode;
	};
	
	this.getTimerScroll = function() {
		return timerScroll;
	};
	
	this.setTimerScroll = function(timerScroll) {
		this.timerScroll = timerScroll;
	};
	
	this.activate = function(selector,topper) {
		
		mode = "active";
		
		$(selector).on("mousemove", function(e) {
			var cY = e.pageY - ($(selector).offset().top),
	    		selHeight = $(selector)[0].clientHeight,
	    		topperHeight = $(topper).height();
			
			$(topper).css({'display' : 'block'});
			if ( !Modernizr.touch )
				$(selector).stop(true,true); 
			
			$(topper).css({'left': $(selector).position()['left']});
			$(topper).css({'top': cY});
			
			if (cY > selHeight && cY <= (selHeight + topperHeight)) {
				// Scroll Down.
				if ( Modernizr.touch && timerScroll == 0 ){
					timerScroll = 1;
					console.log(cY);
					task.getTestletDivScroll().data('jsp').scrollByY(200); 
					setTimeout(function(){
						timerScroll = 0; 
					},300);
				}
				else
					$(selector).animate({scrollTop: $(selector)[0].scrollTop + 20}, 250);
				
			} else if (cY > 40 && cY < 60) {
				// Scroll Down
				if ( Modernizr.touch && timerScroll == 0 ){
					timerScroll = 1;
					console.log(cY);
					task.getTestletDivScroll().data('jsp').scrollByY(-800); 
					setTimeout(function(){
						timerScroll = 0; 
					},300);
				}
				else
					$(selector).animate({scrollTop:  $(selector)[0].scrollTop - 20}, 250);
				
			} else if (cY > (selHeight + topperHeight)) {
				$(topper).css({'display' : 'none'});
				
			} else if (cY < 40) {
				$(topper).css({'display' : 'none'});
			}
		});
		
		$(selector).on("mouseout", function() {
			$(topper).css({'display' : 'none'});
		});
		
		$(selector).on("mouseover", function(ev) {
			$(topper).css({'display' : 'block'});
		});
	};
	
	this.deactivate = function(selector) {
		$(selector).die();
		mode = "";
	};
};