/* 
 * magnifyingGlass.js
 * 
 * Tool for magnifying glass.
 * 
 */
var magnifyingGlass = new function() {
	var mode = "",
		outerDiv,
		//element,
		magnifying_glass,
		magnifiedContent,
		maxWidth,
		maxHeight,
		leftoffset,
		glassX = 0,
		glassY = 0,
		keys = {},
		distance = 3,
		left_arrow = 37,
		up_arrow = 38,
		right_arrow = 39,
		bottom_arrow = 40,
		mScale = 1,
		keyMoveInterval = 0,
		containerXpath = '/html/body/div[2]/section';
	
	this.isMode = function(newMode) {
		return mode == newMode;
	}
	
	this.getMode = function() {
		return mode;
	}
	
	

	this.activate = function(selector) {
		//mode = newMode;
		if (mode == "")
			mode = "mscale_2x";
		var newScale = mode.replace("mscale_","").replace("x","");
		initGlass(selector,newScale);
		$(window).bind("keydown",onKeyDown);
		$(window).bind("keyup",onKeyUp);
	}
	
	this.deactivate = function() {
		$(window).unbind("keydown",onKeyDown);
		$(window).unbind("keyup",onKeyUp);
		outerDiv.remove();
		mode = "";
		$('#tde-content:not(.magnified-content)').unbind('click');
	}
	
	/**
	 * Gets an XPath for an element which describes its hierarchical location.
	 */
	function getElementXPath(element)
	{
	    if (element && element.id)
	        return '//*[@id="' + element.id + '"]';
	    else
	        return getElementTreeXPath(element);
	};
	
	function getElementTreeXPath(element)
	{
	    var paths = [];

	    // Use nodeName (instead of localName) so namespace prefix is included (if any).
	    for (; element && element.nodeType == 1; element = element.parentNode)
	    {
	        var index = 0;
	        for (var sibling = element.previousSibling; sibling; sibling = sibling.previousSibling)
	        {
	            // Ignore document type declaration.
	            if (sibling.nodeType == Node.DOCUMENT_TYPE_NODE)
	                continue;

	            if (sibling.nodeName == element.nodeName)
	                ++index;
	        }

	        var tagName = element.nodeName.toLowerCase();
	        var pathIndex = (index ? "[" + (index+1) + "]" : "");
	        paths.splice(0, 0, tagName + pathIndex);
	    }
	    return paths.length ? "/" + paths.join("/") : null;
	};
	
	/*function initGlass(selector,newScale) {
		//element = $('<div id="magnifyingGlass"></div>');
		//elementContent = $('<div id="magnifiedContent"></div>');
		console.log("init glass");
		element = $('<div class="magnifying_glass"></div>');
		elementContent = $('<div class="magnified_content"></div>');
		magnifyingLens = $('<div class="magnifying_lens"></div>' +
								'<ul class="mag"><li><a title="Zoom In" class="mag-max">+</a></li>' +
								'<li><a title="Zoom Out" class="mag-min">-</a></li></ul>');
		
		outerDiv = $('<div class="magnifying_glass_c"></div>');
		$(outerDiv).resizable({
			minHeight: 110,
			minWidth : 110,
			stop: function(event, ui){
				var newWidth = ui.size.width - 5;
				var newHeight = ui.size.height - 5;
				$(element).css('width', newWidth);
				$(element).css('height', newHeight);
			}
		});
		$(outerDiv).draggable();			  
					  
		elementContent.css({
			backgroundColor: $("html").css("background-color") || $("body").css("background-color"),
			backgroundImage: $("html").css("background-image") || $("body").css("background-image"),
			backgroundAttachment: $("html").css("background-attachment")  || $("body").css("background-attachment"),
			backgroundPosition: $("html").css("background-position") || $("body").css("background-position")
		});
		mScale = newScale;
		maxWidth = $(document.body).width() - element.width();
		maxHeight = $(document.body).height() - element.height();
		leftoffset = 0;//$(selector)[0].offsetLeft;
		element.css('display','block');
		setMagnifyingScale(mScale);
		if (glassX == 0 && glassY == 0) {
			console.log("glassx, glassy--0");
			//outerDiv.css('left',leftoffset);
			element.css('left',leftoffset);
			elementContent.css('left',elementContent.css('left')+leftoffset);
		}
		else {
			console.log("in else of glass");
			outerDiv.css({left: glassX,top: glassY});
			element.css({left: glassX,top: glassY});
			elementContent.css({left: -glassX*mScale, top: -glassY*mScale});
		}
		var contentCopy = $(selector).clone();
		contentCopy.find("script").remove();
		elementContent.html(contentCopy);
		element.append(elementContent);
		//element.append(magnifyingLens);
		outerDiv.append(element);
		//$(selector).append(element);
		$('#tool-arena').append(outerDiv);
		updateViewSize();
		$(window).resize(updateViewSize);
		keyMoveInterval = setInterval(keyMove, 20);
		bindAllChildren($('.magnified_content .w'));
	}*/
	
	
	function initGlass(selector,newScale) {
		$('#tde-content:not(.magnified-content)').bind('click', magnifyingGlass.resetMagnifyingGlass);
		
		var scale = newScale;
		magnifying_glass = $('<div class="magnifying_glass"></div>');
		magnifiedContent = $('<div id="magnified_content" class="magnified_content"></div>');
		//var magnifyingLens = $('<div class="magnifying_lens"></div>');
		var changeScale = $('<ul class="mag">'+
								'<li><a title="Zoom In" class="mag-max" >+</a></li>' +
								'<li><a title="Zoom Out" class="mag-min" >-</a></li>'+
							'</ul>');
		
		//setup
		magnifiedContent.css({
			backgroundColor: $("html").css("background-color") || $("body").css("background-color"),
			backgroundImage: $("html").css("background-image") || $("body").css("background-image"),
			backgroundAttachment: $("html").css("background-attachment") || $("body").css("background-attachment"),
			backgroundPosition: $("html").css("background-position") || $("body").css("background-position")
		});
		magnifiedContent.css('-moz-transform','scale(' + newScale + ')');
		magnifiedContent.css('-webkit-transform','scale(' + newScale + ')');
		
		outerDiv = $('<div class="magnifying_glass_c"></div>');
		
		$(outerDiv).resizable({
			minHeight: 110,
			minWidth : 110,
			stop: function(event, ui){
				var newWidth = ui.size.width - 5;
				var newHeight = ui.size.height - 5;
				magnifying_glass.css('width', newWidth);
				magnifying_glass.css('height', newHeight);
			}
		});
		$(outerDiv).draggable();
		
		mScale = scale;
		
		//$magnifiedContent.html(innerShiv($(document.body).html())); //fix html5 for ie<8, must also include script
		var contentCopy = $(selector).clone();
		contentCopy.find("script").remove();
		contentCopy.addClass('magnified-content');
		magnifiedContent.html(contentCopy);
		magnifying_glass.append(magnifiedContent);
		//magnifying_glass.append(magnifyingLens); //comment this line to allow interaction
		magnifying_glass.append(changeScale);
		$(outerDiv).append(magnifying_glass);
		$('#tool-arena').append(outerDiv);

		//funcs
		function updateViewSize() {
			magnifiedContent.css({width: $(document).width(), height: $(document).height()});
			//magnifiedContent.css({left: -newX*mScale, top: -newY*mScale});
		} 
		
		//begin
		updateViewSize();
		//events
		$(window).resize(updateViewSize);
		
		magnifying_glass.bind('mousedown touchstart',function(e) {
			e.preventDefault();
			$(this).data("drag", {mouse: {top: e.pageY, left: e.pageX}, offset: {top: $(this).offset().top, left: $(this).offset().left}});
		});
		magnifying_glass.bind('mousemove touchmove',function(e) {
			if (magnifying_glass.data("drag")) {
				var drag =magnifying_glass.data("drag");
				var left = drag.offset.left + (e.pageX-drag.mouse.left);
				var top = drag.offset.top + (e.pageY-drag.mouse.top);
				magnifying_glass.css({left: left, top: top});
				if (!$.browser.webkit) {
					magnifiedContent.css({left: -left*scale, top: -top*scale});
				} else {
					magnifiedContent.css({left: (3*scale*parseInt($(selector).css('left')))+(-left*scale), top: (3*scale*parseInt($(selector).css('top')))+(-top*scale)});
				}
			}
		}).bind('mouseup', function() {
			magnifying_glass.removeData("drag");
		});
		
		$('.mag-max').bind('click touchend',function(){
			var newScale = parseInt(mode.replace("mscale_","").replace("x","")) + 1;
			if(newScale > 5) {
				newScale = 5;
			}
			changescale(newScale);
		});
		$('.mag-min').bind('click touchend',function(){
			var newScale = parseInt(mode.replace("mscale_","").replace("x","")) - 1;
			if(newScale < 2) {
				newScale = 2;
			}
			changescale(newScale);
		});
		
		$('#magnified_content #tde-content').on('click', function(ev){
			var xp = getElementXPath(ev.target);
			//var magContainerXpath = getElementTreeXPath(getANode(this, 'container')); //was getting parent upto #container
			var magContainerXpath = getElementTreeXPath(document.getElementById('magnified_content')) + '/section';
			xp = xp.replace(magContainerXpath,containerXpath);
			//var container = document.evaluate(xp, document, null, XPathResult.ANY_TYPE, null);
			//var containerElem = container.iterateNext();
			var containerElem = document.evaluate(xp, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
			$(containerElem).click();
			
			magnifyingGlass.resetMagnifyingGlass();
			ev.stopPropagation();
		});
		
		magnifiedContent.css('left',-(parseInt(magnifiedContent.css('left'))+$(selector)[0].offsetLeft));
		magnifiedContent.css('top',-(parseInt(magnifiedContent.css('top'))+$(selector)[0].offsetTop));
		
		var scrollTo = $('.test-questions:not(.magnified-content .test-questions)').scrollTop();
		$('.magnified-content .test-questions').scrollTop(scrollTo);
	}
	
	function changescale(change) {
		var height = $(outerDiv).height();
		var width = $(outerDiv).width();
		
		glassX = $(outerDiv).css('left');
		glassY =  $(outerDiv).css('top');
		magnifyingGlass.deactivate();
		magnifyingGlass.setMode("mscale_" + change + "x");
		magnifyingGlass.activate(tde.config.tool_selectors.magnifyingglass);
		magnifyingGlass.setPrevPosition();
		$(outerDiv).css({'height':height,'width':width});
	}
	
	this.setMode = function(newMode) {
		mode = newMode;
	};
	
	this.setPrevPosition = function() {
		outerDiv.css({left: glassX,top: glassY});
		magnifying_glass.css({left: glassX,top: glassY});
		magnifiedContent.css({left: -glassX*mScale, top: -glassY*mScale});
	};
	
	function setMagnifyingScale(newScale) {
		elementContent.css('transform','scale(' + newScale + ')');
		elementContent.css('-moz-transform','scale(' + newScale + ')');
		elementContent.css('-webkit-transform','scale(' + newScale + ')');
		elementContent.css('-ms-transform','scale(' + newScale + ')');
		elementContent.css('-o-transform','scale(' + newScale + ')');
	}
	
	function keyMove(event) {
		var newX = calculateNewPos(magnifying_glass.css('left'), left_arrow, right_arrow,maxWidth);
		var newY = calculateNewPos(magnifying_glass.css('top'), up_arrow, bottom_arrow,maxHeight);
		magnifying_glass.css({left: newX,top: newY});
		outerDiv.css({left: newX,top: newY});
		glassX = newX;
		glassY= newY;
		//magnifying_glass.data('mouseX', event.clientX);
		//magnifying_glass.data('drag', event.clientY);
		magnifying_glass.data('drag', {mouse: {top: newY, left: newX}, offset: {top: magnifying_glass.offset().top, left: magnifying_glass.offset().left}})
		magnifiedContent.css({left: -newX*mScale, top: -newY*mScale});
	}
	
	function calculateNewPos(oldValue,key1,key2,maxLimit) {
		var newValue = parseInt(oldValue, 10)
                   - (keys[key1] ? distance : 0)
                   + (keys[key2] ? distance : 0);
		return newValue < 0 ? 0 : newValue > maxLimit ? maxLimit : newValue;
	}
	
	function onKeyDown(event) {
		keys[event.which] = true;
		if(! keyMoveInterval){
			keyMoveInterval = setInterval(keyMove, 10);
		}
	}
	
	function onKeyUp(event) {
		keys[event.which] = false;
		clearInterval(keyMoveInterval);
		if(keyMoveInterval){
			keyMoveInterval = 0;
		}
		magnifying_glass.removeData('drag');
	}
		
	function onMouseDown(event) {
		magnifying_glass.data('mouseX', event.clientX);
		magnifying_glass.data('mouseY', event.clientY);
		$(document).unbind("mousemove touchmove");
		$(document).bind("mousemove touchmove", onMouseMove);
	}
				
	function onMouseMove(event) {
		if (magnifying_glass.data("mouseX")) {
			var changeX = event.clientX - magnifying_glass.data('mouseX'),
				changeY = event.clientY - magnifying_glass.data('mouseY'),
				newX = parseInt(magnifying_glass.css('left')) + changeX,
				newY = parseInt(magnifying_glass.css('top')) + changeY;
			magnifying_glass.css('left', newX);
			magnifying_glass.css('top', newY);
			glassX = newX;
			glassY= newY;
			magnifying_glass.data('mouseX', event.clientX);
			magnifying_glass.data('mouseY', event.clientY);
			magnifiedContent.css({left: (-newX+leftoffset)*mScale, top: -newY*mScale});		
		}
	}
	
	function onMouseUp(event) {
		magnifying_glass.removeData('mouseX');
		magnifying_glass.removeData('mouseY');
	};
	
	this.resetMagnifyingGlass = function() {
		//if (magnifyingGlass.getMode().indexOf("scale") !=-1) {
		if(magnifyingGlass.getMode() != "") {
			var height = $(outerDiv).height();
			var width = $(outerDiv).width();
			glassX = $(outerDiv).css('left');
			glassY =  $(outerDiv).css('top');
			
			var magLeft = parseInt(magnifiedContent.css('left'));
			var magTop = parseInt(magnifiedContent.css('top'));
			
			magnifyingGlass.deactivate();
			magnifyingGlass.activate(tde.config.tool_selectors.magnifyingglass);
			magnifyingGlass.setPrevPosition();
			$(outerDiv).css({'height':height,'width':width});
			
			magnifiedContent.css('left',(magLeft));
			magnifiedContent.css('top',(magTop));
		}
	};
};
