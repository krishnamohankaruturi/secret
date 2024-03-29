/**
 * findAndReplaceDOMText v 0.2
 * @author James Padolsey http://james.padolsey.com
 * @license http://unlicense.org/UNLICENSE
 *
 * Matches the text of a DOM node against a regular expression
 * and replaces each match (or node-separated portions of the match)
 * in the specified element.
 *
 * Example: Wrap 'test' in <em>:
 *   <p id="target">This is a test</p>
 *   <script>
 *     findAndReplaceDOMText(
 *       /test/,
 *       document.getElementById('target'),
 *       'em'
 *     );
 *   </script>
 */
window.findAndReplaceDOMText = (function() {

  /** 
   * findAndReplaceDOMText
   * 
   * Locates matches and replaces with replacementNode
   *
   * @param {RegExp} regex The regular expression to match
   * @param {Node} node Element or Text node to search within
   * @param {String|Element|Function} replacementNode A NodeName,
   *  Node to clone, or a function which returns a node to use
   *  as the replacement node.
   * @param {Number} captureGroup A number specifiying which capture
   *  group to use in the match. (optional)
   */
  function findAndReplaceDOMText(regex, node, replacementNode, captureGroup, indexes) {	

    var m, matches = [], text = _getText(node);
    var replaceFn = _genReplacer(replacementNode);

    if (!text) { return; }

    if(!regex) {
    	matches.push(indexes);
    	_stepThroughMatches(node, matches, replaceFn, replacementNode);
    } else {
	    if (regex.global) {
	      while (m = regex.exec(text)) {
	        matches.push(_getMatchIndexes(m, captureGroup));
	      }
	    } else {
	      m = text.match(regex);
	      matches.push(_getMatchIndexes(m, captureGroup));
	    }
	
	    if (matches.length) {
	      _stepThroughMatches(node, matches, replaceFn, replacementNode);
	    }
    }
  }

  /**
   * Gets the start and end indexes of a match
   */
  function _getMatchIndexes(m, captureGroup) {

    captureGroup = captureGroup || 0;
 
    if (!m[0]) throw 'findAndReplaceDOMText cannot handle zero-length matches';
 
    var index = m.index;

    if (captureGroup > 0) {
      var cg = m[captureGroup];
      if (!cg) throw 'Invalid capture group';
      index += m[0].indexOf(cg);
      m[0] = cg;
    } 

    return [ index, index + m[0].length, [ m[0] ] ];
  };

  /**
   * Gets aggregate text of a node without resorting
   * to broken innerText/textContent
   */
  function _getText(node) {

    if (node.nodeType === 3) {
      return node.data;
    }

    var txt = '';

    if (node = node.firstChild) do {
      txt += _getText(node);
    } while (node = node.nextSibling);

    return txt;

  }

  /** 
   * Steps through the target node, looking for matches, and
   * calling replaceFn when a match is found.
   */
  function _stepThroughMatches(node, matches, replaceFn, replacementNode) {

    var after, before,
        startNode,
        endNode,
        startNodeIndex,
        endNodeIndex,
        innerNodes = [],
        atIndex = 0,
        curNode = node,
        matchLocation = matches.shift(),
        matchIndex = 0;

    out: while (true) {

      if (curNode.nodeType === 3){ //&& !($(curNode).parents('math') && /^\s*$/.test($(curNode).text()))) {
        if (!endNode && curNode.length + atIndex >= matchLocation[1]) {
          // We've found the ending
          endNode = curNode;
          endNodeIndex = matchLocation[1] - atIndex;
        } else if (startNode) {
        	// Intersecting node
        	if(!(($(curNode).parents('math') || $(curNode).parents('table')) && /^\s*$/.test($(curNode).text()))) { //dlm-add: exclude interior blank mathml interior nodes
        		innerNodes.push(curNode);
        	}
        }
        if (!startNode && curNode.length + atIndex > matchLocation[0]) {
          // We've found the match start
          startNode = curNode;
          startNodeIndex = matchLocation[0] - atIndex;
        }
        atIndex += curNode.length;
      } else if (curNode.nodeName.toLowerCase() == 'math') { // dlm-added : if mathml then just wrap the node with span
    	  var nodeTextLength = curNode.textContent.length;
    	  
    	  if(matchLocation[2][0] == curNode.textContent) {
    		  // full mathml node matches
    		  $(curNode).parent().contents().wrap(replacementNode);
    	  } else {
    		  if (!endNode && nodeTextLength + atIndex >= matchLocation[1]) {
	              // We've found the ending
    			  
    			  //1. handle cases like <span>abc</span><math> -- because of highlighting etc
    			  //2. abc<math>
    			  if(curNode.parentNode.previousSibling && curNode.parentNode.previousSibling.nodeType != 3) {
    				  endNode = $(curNode.parentNode.previousSibling).contents().filter(function() {
    					 return this.nodeType == 3; 
    				  }).get(0);
    			  } else if(curNode.parentNode.previousSibling && curNode.parentNode.previousSibling.nodeType == 3) {
    				  endNode = curNode.parentNode.previousSibling;
    			  }
    			  
    			  if(!endNode) {
    				  endNode = curNode.parentNode;
    			  }
    			  
	              //endNode = curNode.parentNode.previousSibling || curNode.parentNode;
	              endNodeIndex = endNode.textContent.length;//matchLocation[1] - atIndex; //curNode.parentNode.previousSibling.length
	              
	              matchLocation[2][0] = matchLocation[2][0].substring(0, matchLocation[2][0].lastIndexOf(curNode.textContent));
	          }
	    	  
    		  if (!startNode && nodeTextLength + atIndex > matchLocation[0]) {
	              // We've found the match start
	              
    			  if(curNode.parentNode.nextSibling && curNode.parentNode.nextSibling.nodeType != 3) {
    				  startNode = $(curNode.parentNode.nextSibling).contents().filter(function() {
    					 return this.nodeType == 3; 
    				  }).get(0);
    			  } else if(curNode.parentNode.nextSibling && curNode.parentNode.nextSibling.nodeType == 3) {
    				  startNode = curNode.parentNode.nextSibling;
    			  }
    			  
    			  if(!startNode) {
    				  startNode = curNode.parentNode;
    			  }
    			  //startNode = curNode.parentNode.nextSibling || curNode.parentNode;
	              startNodeIndex = 0;//curNode.parentNode.previousSibling.length;//matchLocation[0] - atIndex;
	              
	              matchLocation[2][0] = matchLocation[2][0].substring(matchLocation[2][0].indexOf(curNode.textContent) + nodeTextLength,matchLocation[2][0].length);
	          }
	    	  
	    	  if(startNode) {
	    		  $(curNode).parent().contents().wrap(replacementNode);
	    	  }
    	  }
    	  
    	  atIndex += nodeTextLength;
      }

      if (startNode && endNode && matchLocation[2] != "") {
        curNode = replaceFn({
          startNode: startNode,
          startNodeIndex: startNodeIndex,
          endNode: endNode,
          endNodeIndex: endNodeIndex,
          innerNodes: innerNodes,
          match: matchLocation[2],
          matchIndex: matchIndex
        });
        // replaceFn has to return the node that replaced the endNode
        // and then we step back so we can continue from the end of the 
        // match:
        atIndex -= (endNode.length - endNodeIndex);
        startNode = null;
        endNode = null;
        innerNodes = [];
        matchLocation = matches.shift();
        matchIndex++;
        if (!matchLocation) {
          break; // no more matches
        }
      } else if (curNode.nodeName.toLowerCase() == 'math') {
    	  // do nothing
      } else if (curNode.firstChild || curNode.nextSibling) {
        // Move down or forward:
        curNode = curNode.firstChild || curNode.nextSibling;
        continue;
      }

      // Move forward or up:
      while (true) {
        if (curNode.nextSibling) {
          curNode = curNode.nextSibling;
          break;
        } else if (curNode.parentNode !== node && !$(curNode).parent().hasClass('taghighlight')) {
          curNode = curNode.parentNode;
        } else {
          break out;
        }
      }

    }

  }

  var reverts;
  /**
   * Reverts the last findAndReplaceDOMText process
   */
  findAndReplaceDOMText.revert = function revert() {
    for (var i = 0, l = reverts.length; i < l; ++i) {
      reverts[i]();
    }
    reverts = [];
  };

  /** 
   * Generates the actual replaceFn which splits up text nodes
   * and inserts the replacement element.
   */
  function _genReplacer(nodeName) {

    reverts = [];

    var makeReplacementNode;

    if (typeof nodeName != 'function') {
      var stencilNode = nodeName.nodeType ? nodeName : document.createElement(nodeName);
      makeReplacementNode = function(fill) {
        var clone = document.createElement('div'),
            el;
        clone.innerHTML = stencilNode.outerHTML || new XMLSerializer().serializeToString(stencilNode);
        el = clone.firstChild;
        if (fill) {
          el.appendChild(document.createTextNode(fill));
        }
        return el;
      };
    } else {
      makeReplacementNode = nodeName;
    }

    return function replace(range) {

      var startNode = range.startNode,
          endNode = range.endNode,
          matchIndex = range.matchIndex;

      if (startNode === endNode) {
        var node = startNode;
        if (range.startNodeIndex > 0) {
          // Add `before` text node (before the match)
          var before = document.createTextNode(node.data.substring(0, range.startNodeIndex));
          node.parentNode.insertBefore(before, node);
        }

        // Create the replacement node:
        var el = makeReplacementNode(range.match[0], matchIndex, range.match[0]);
        node.parentNode.insertBefore(el, node);
        if (range.endNodeIndex < node.length) {
          // Add `after` text node (after the match)
          var after = document.createTextNode(node.data.substring(range.endNodeIndex));
          node.parentNode.insertBefore(after, node);
        }
        
        node.parentNode.removeChild(node);
        reverts.push(function() {
          var pnode = el.parentNode;
          pnode.insertBefore(el.firstChild, el);
          pnode.removeChild(el);
          pnode.normalize();
        });
        return el;
      } else {
        // Replace startNode -> [innerNodes...] -> endNode (in that order)
        var before = document.createTextNode(startNode.data.substring(0, range.startNodeIndex));
        var after = document.createTextNode(endNode.data.substring(range.endNodeIndex));
        var elA = makeReplacementNode(startNode.data.substring(range.startNodeIndex), matchIndex, range.match[0]);
        var innerEls = [];
        for (var i = 0, l = range.innerNodes.length; i < l; ++i) {
          var innerNode = range.innerNodes[i];
          var innerEl = makeReplacementNode(innerNode.data, matchIndex, range.match[0]);
          innerNode.parentNode.replaceChild(innerEl, innerNode);
          innerEls.push(innerEl);
        }
        var elB = makeReplacementNode(endNode.data.substring(0, range.endNodeIndex), matchIndex, range.match[0]);
        if(startNode.parentNode) {
        	startNode.parentNode.insertBefore(before, startNode);
        	startNode.parentNode.insertBefore(elA, startNode);
        	startNode.parentNode.removeChild(startNode);
        }
        if(endNode.parentNode) {
        	endNode.parentNode.insertBefore(elB, endNode);
            endNode.parentNode.insertBefore(after, endNode);
            endNode.parentNode.removeChild(endNode);
        }
        reverts.push(function() {
          innerEls.unshift(elA);
          innerEls.push(elB);
          for (var i = 0, l = innerEls.length; i < l; ++i) {
            var el = innerEls[i];
            var pnode = el.parentNode;
            pnode.insertBefore(el.firstChild, el);
            pnode.removeChild(el);
            pnode.normalize();
          }
        });
        return elB;
      }
    };

  }

  return findAndReplaceDOMText;

}());