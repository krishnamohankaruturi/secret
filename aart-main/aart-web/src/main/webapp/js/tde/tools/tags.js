/*
 * Tag Tool
 */
var tags = new function() {
	var imgSeq = 0;
	
	/*
	 * method: dragStop
	 * This method handles updating the global and removing the element if it goes out of scope.
	 */
	this.dragStop = function(element, ui, container) {
		if (ui.position.left < 0 || ui.position.top < 0 || ui.position.left > container.width()) {
			$(element).draggable("destroy");
			$(element).remove();
			delete tde.testparam.taskTags[$(element).attr('id')];  // Remove from global
		} else {
			// Just update the stored position.
			tde.testparam.taskTags[$(element).attr('id')].rX = $(element).css('left');
			tde.testparam.taskTags[$(element).attr('id')].rY = $(element).css('top');
		}
	};
	
	/*
	 * method: placeTag
	 * This method palces the new tag onto the page.
	 */
	this.placeTag = function(e, newSelector, tagId, group, title) {
		var count = 0;
		for (var tag in tde.testparam.taskTags) {
			if(tde.testparam.taskTags[tag].title == title ){
				count++;
			}
		}
		if(count>=10){
			return;
		}
		
		var selector = jQuery(newSelector),
			newImg = document.createElement( 'img' ),
			offset = selector.offset(),
			left = e.pageX + selector.scrollLeft();
			//left = e.pageX + selector.scrollLeft() - offset.left;
		
		$(newImg).attr({
			id: 'tagImg' + imgSeq + '-' + group,
			src: '../images/' + tagId + '.png',
			title : title
		});
		
		$(newImg).css({
			'position': 'absolute',
			'cursor': 'move',
			'left': (left > 0 && left < selector.width()) ? left : 0,
			'top': e.pageY + selector.scrollTop()
			//'top': e.pageY + selector.scrollTop() - offset.top
		});
		
		$(newImg).addClass('tag');
		$(newImg).appendTo($('.tagholder'));
		
		tde.testparam.taskTags[$(newImg).attr('id')] = {
				"id": $(newImg).attr('id'), 
				"group": group,
				"imageName": tagId,
				"sequence": imgSeq,
				"rX": $(newImg).css('left'),
				"rY": $(newImg).css('top'),
				title: title
		};
		
		$(newImg).draggable({
			containment: ".test-passage .passage",
			stop: function(event, ui) { 
					tags.dragStop(this, ui, selector);
			},
			drag: function(event, ui) {
			    if( ui.position.left < 0 ){ ui.position.left = 0; }
			    if( ui.position.top < 0 ){ ui.position.top = 0; }
			    //max right
			    var maxRight = selector.width();
			    if( ui.position.left > maxRight) {
			        ui.position.left = maxRight;
			    }
			    //max down
			    var maxDown = selector.height()+20;
			    if( ui.position.top > maxDown) {
			        ui.position.top = maxDown;
			    }
			}
		});
		
		imgSeq++;
	};
	
	/*
	 * method: replaceTag
	 * This method puts a tag from the global back on the page.
	 */
	this.replaceTag = function(newSelector, tag) {
		var selector = jQuery(newSelector),
			newImg = document.createElement( 'img' );
		
		$(newImg).attr({ 
			id: tag.id,
			src: '../images/' + tag.imageName + '.png',
			title: tag.title
		});
		
        $(newImg).css({
        	'position': 'relative',
            'cursor': 'move',
    		'left': tag.rX,
    		'top': tag.rY
        });
        $(newImg).addClass('tag');
		$(".test-passage").prepend(newImg);
		
		$(newImg).draggable({
            stop: function(event, ui) { 
                    tags.dragStop(this, ui, selector);
            }
        });
	};
	
	this.clearTags = function() {
		for (var tag in tde.testparam.taskTags) {
			$('#'+tde.testparam.taskTags[tag].id).draggable('destroy');
			$('#'+tde.testparam.taskTags[tag].id).remove();
			delete tde.testparam.taskTags[tag];
		}
		imgSeq = tde.testparam.taskTags.length;
	};
};