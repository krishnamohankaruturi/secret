/**
 * http://jsfiddle.net/Bua2d/
 */
(function ($) {
    var settings = {
        barheight: 55
    };    

    $.fn.scrollabletab = function (options) {

        var ops = $.extend(settings, options);
        var liFullWidth = 0;
        var allow;
        var ul = this.children('.tabs').first();
        //var ulHtmlOld = ul.html();
        var tabBarWidth = $(this).width()-80;
        ul.wrapInner('<div class="fixedContainer" style="width: ' + tabBarWidth + 'px; overflow: hidden; float: left;"><div class="moveableContainer" style="height: ' + ops.barheight + 'px; width: 5000px; position: relative; left: 0px;"></div></div>');
        ul.append('<div class="actions"/>');
        ul.children('.actions').append('<div style="width: 20px; float: left; height: ' + (ops.barheight - 15) + 'px; margin-left: 5px; margin-right: 0;"></div>');
        var leftArrow = ul.children('.actions').children().last();
        leftArrow.button({  label: "&#171;"}).attr('title', 'Previous Tab').addClass("tabBtn");
        //leftArrow.children('.ui-icon-carat-1-w').first().css('left', '2px');        

        ul.children('.actions').append('<div style="width: 20px; float: left; height: ' + (ops.barheight - 15) + 'px; margin-left: 1px; margin-right: 0;"></div>');
        var rightArrow = ul.children('.actions').children().last();
        rightArrow.button({ label: "&#187;"}).attr('title', 'Next Tab').addClass("tabBtn");
        
        //rightArrow.children('.ui-icon-carat-1-e').first().css('left', '2px');
        $("#configurationTabs .actions div:first-child ").attr('class','left-arrow');
        $("#configurationTabs .actions div:last-child").attr('class','right-arrow');
        $("#configurationTabs .actions .left-arrow span").addClass("arrowDefault");
        ul.find('li').each(function (index, element) {
        	liFullWidth += $(element).width();          
        });
       
        if(liFullWidth >= $(".fixedContainer").width()) {
        	$(".left-arrow,.right-arrow").show();
        	allow = true;
        }
        else {
        	$(".left-arrow,.right-arrow").addClass("arrowDefault");
        	$("#configurationTabs .actions .right-arrow span").addClass("arrowDefault");
        	allow = false;
        }
        var moveable = ul.find('.moveableContainer').first();
        leftArrow.click(function () {
        	if(allow) {
	        	$("#configurationTabs .actions .right-arrow span").removeClass("arrowDefault");
	            var offset = tabBarWidth / 6;
	            var currentPosition = moveable.css('left').replace('px', '') / 1;
	            
	            if (currentPosition + offset >= 0) {
	                moveable.stop().animate({ left: '0' }, 'slow');
	                $("#configurationTabs .actions .left-arrow span").addClass("arrowDefault");
	            }
	            else {
	                moveable.stop().animate({ left: currentPosition + offset + 'px' }, 'slow');
	            }
        	}
        });

        rightArrow.click(function () {
        	if(allow) {
	        	$("#configurationTabs .actions .left-arrow span").removeClass("arrowDefault");
	        	var offset = tabBarWidth / 6;
	            var currentPosition = moveable.css('left').replace('px', '') / 1;
	            var tabsRealWidth = 0;
	            ul.find('li').each(function (index, element) {
	                tabsRealWidth += $(element).width();
	                tabsRealWidth += ($(element).css('margin-right').replace('px', '') / 1);
	            });
	
	            tabsRealWidth *= -1;
	
	            if (currentPosition - tabBarWidth > tabsRealWidth) {
	                moveable.stop().animate({ left: currentPosition - offset  + 'px' }, 'slow');
	            }
	            else {
	            	 $("#configurationTabs .actions .right-arrow span").addClass("arrowDefault");
	            }
        	}
        });


        return this;
    }; // end of functions

})(jQuery);