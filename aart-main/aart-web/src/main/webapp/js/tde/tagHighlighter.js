var tagHighlighter = (function() {
	
	function highlight(contentGroup) {//Index) {
		
		var span = document.createElement('span');
		span.className = 'taghighlight';
		
		var node = $(contentGroup.htmlElementId);
		
		if(contentGroup.htmlElementId!= null && contentGroup.charIndexStart!=null && contentGroup.charIndexEnd!=null) {
			//findAndReplaceDOMText(null, node[0], span, null, [cg.charIndexStart, cg.charIndexEnd, [node.text().substring(cg.charIndexStart, cg.charIndexEnd)]]);
			findAndReplaceDOMText(null, node[0], span, null, [contentGroup.charIndexStart, contentGroup.charIndexEnd, [node.text().substring(contentGroup.charIndexStart, contentGroup.charIndexEnd)]]);
		} else {
			var prenode = '';
			
			if(contentGroup.grouptype == 'task') {
				prenode = '.taskstem ';
			} else if(/foil/.test(contentGroup.grouptype)) {
				prenode = '.'+contentGroup.grouptype+' ';
			} else if(contentGroup.grouptype == 'passage') {
				prenode = '.test-passage ';
			}
			
			if(contentGroup.compositeMediaId != null) {
				$(prenode+'div[mediaVariantId=' + contentGroup.compositeMediaId + ']').addClass('taghighlight');
			} else if(contentGroup.stimulusVariantId != null) {
				$(prenode+'div[mediaVariantId=' + contentGroup.stimulusVariantId + ']').addClass('taghighlight');
			} else {
				$(node).addClass('taghighlight');
			}
		}
		
	}
	
	function unhighlight() {
		var node = ($('#tde-content').is('*')) ? $('#tde-content') : $('#test-description');
		node.find('span.taghighlight').each(function(){
			this.parentNode.firstChild.nodeName;
			  
			with (this.parentNode) {
				if(this.firstChild) {
					replaceChild(this.firstChild, this);
					normalize();
				}
			}
		}).end();
		$('.taghighlight').removeClass('taghighlight');
	}
	
	return {
		highlight : highlight,
		unhighlight : unhighlight
	};
	
})();