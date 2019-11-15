var gridStateName = function (grid) {
	var currentUserId = window.sessionStorage.getItem("currentUserId");
	
	if(currentUserId != null && typeof currentUserId != 'undefined') {
		return currentUserId+window.location.pathname + '#' + grid[0].id;
	}
    return window.location.pathname + '#' + grid[0].id;
};

var removeGridState = function (grid) {
    window.sessionStorage.removeItem(gridStateName(grid));
};

(function($){
	// requiere load multiselect before grid
	$.jgrid._multiselect = false;
	if($.ui) {
		if ($.ui.gridmultiselect ) {
			if($.ui.gridmultiselect.prototype._setSelected) {
				var setSelected = $.ui.gridmultiselect.prototype._setSelected;
			    $.ui.gridmultiselect.prototype._setSelected = function(item,selected) {
			        var ret = setSelected.call(this,item,selected);
			        if (selected && this.selectedList) {
			            var elt = this.element;
					    this.selectedList.find('li').each(function() {
						    if ($(this).data('optionLink')) {
							    $(this).data('optionLink').remove().appendTo(elt);
						    }
					    });
			        }
			        return ret;
				};
			}
			if($.ui.gridmultiselect.prototype.destroy) {
				$.ui.gridmultiselect.prototype.destroy = function() {
					this.element.show();
					this.container.remove();
					if ($.Widget === undefined) {
						$.widget.prototype.destroy.apply(this, arguments);
					} else {
						$.Widget.prototype.destroy.apply(this, arguments);
		            }
				};
			}
			$.jgrid._multiselect = true;
		}
	}
	
	//Extend JQGrid column chooser to avoid conflict
	$.jgrid.extend({		
	    columnChooser : function(opts) {
	        var self = this;
	        if($("#colchooser_"+$.jgrid.jqID(self[0].p.id)).length ) { return; }
	        var selector = $('<div id="colchooser_'+self[0].p.id+'" style="position:relative;overflow:hidden;"><div><select multiple="multiple"></select></div></div>');
	        $(selector).addClass("col-choosen-ui-dialog");
	        var select = $('select', selector);	      
	        function insert(perm,i,v) {
	            if(i>=0){
	                var a = perm.slice();
	                var b = a.splice(i,Math.max(perm.length-i,i));
	                if(i>perm.length) { i = perm.length; }
	                a[i] = v;
	                return a.concat(b);
	            }
	        }
	        opts = $.extend({
	            "width" : 470,
	            "height" : 240,
	            "classname" : null,
//	            "done" : function(perm) { 
//	            	if (perm) { 
//	            		self.jqGrid("remapColumns", perm, true); 
//	            	}
//	            	opts.saveOptionsFunction();
//	            },
	            /* msel is either the name of a ui widget class that
	               extends a multiselect, or a function that supports
	               creating a multiselect object (with no argument,
	               or when passed an object), and destroying it (when
	               passed the string "destroy"). */
	            "msel" : "gridmultiselect",
	            /* "msel_opts" : {}, */

	            /* dlog is either the name of a ui widget class that 
	               behaves in a dialog-like way, or a function, that
	               supports creating a dialog (when passed dlog_opts)
	               or destroying a dialog (when passed the string
	               "destroy")
	               */
	            "dlog" : "dialog",

	            /* dlog_opts is either an option object to be passed 
	               to "dlog", or (more likely) a function that creates
	               the options object.
	               The default produces a suitable options object for
	               ui.dialog */
	            
	            "done" : function(perm) { if (perm) this.jqGrid("remapColumns", perm, true) },
	            
	            "dlog_opts" : function(opts) {
	                var buttons = {};
	                buttons[opts.bSubmit] = function() {
	                    opts.apply_perm();
	                    opts.cleanup(false);
	                };
	                buttons[opts.bCancel] = function() {
	                    opts.cleanup(true);
	                };
	                return $.extend(true, {
	                    "buttons": buttons,
	                    "close": function() {
	                        opts.cleanup(true);
	                    },
	                    "modal" : opts.modal ? opts.modal : false,
	                    "resizable": opts.resizable ? opts.resizable : true,
	                    "width": opts.width+20,
	                    resize: function (e, ui) {
	                        var $container = $(this).find('>div>div.ui-grid-multiselect'),
	                            containerWidth = $container.width(),
	                            containerHeight = $container.height(),
	                            $selectedContainer = $container.find('>div.selected'),
	                            $availableContainer = $container.find('>div.available'),
	                            $selectedActions = $selectedContainer.find('>div.actions'),
	                            $availableActions = $availableContainer.find('>div.actions'),
	                            $selectedList = $selectedContainer.find('>ul.connected-list'),
	                            $availableList = $availableContainer.find('>ul.connected-list'),
	                            dividerLocation = opts.msel_opts.dividerLocation || $.ui.multiselect.defaults.dividerLocation;

	                        $container.width(containerWidth); // to fix width like 398.96px                     
	                        $availableContainer.width(Math.floor((containerWidth*(1-dividerLocation))-10));
	                        $selectedContainer.width(containerWidth - $availableContainer.outerWidth()-10 - ($.browser.webkit ? 1: 0));

	                        $availableContainer.height(containerHeight);
	                        $selectedContainer.height(containerHeight);
	                        $selectedList.height(Math.max(containerHeight-$selectedActions.outerHeight()-1,1));
	                        $availableList.height(Math.max(containerHeight-$availableActions.outerHeight()-1,1));
	                    }
	                }, opts.dialog_opts || {});
	            },
	            /* Function to get the permutation array, and pass it to the
	               "done" function */
	            "apply_perm" : function() {
	                $('option',select).each(function(i) {
	                    if (this.selected) {
	                        self.jqGrid("showCol", colModel[this.value].name);
	                    } else {
	                        self.jqGrid("hideCol", colModel[this.value].name);
	                    }
	                });

	                var perm = [];
	                //fixedCols.slice(0);
	                $('option:selected',select).each(function() { perm.push(parseInt(this.value,10)); });
	                $.each(perm, function() { delete colMap[colModel[parseInt(this,10)].name]; });
	                $.each(colMap, function() {
	                    var ti = parseInt(this,10);
	                    perm = insert(perm,ti,ti);
	                });
	                if (opts.done) {
	                    opts.done.call(self, perm);
	                }
	            },
	            /* Function to cleanup the dialog, and select. Also calls the
	               done function with no permutation (to indicate that the
	               columnChooser was aborted */
	            "cleanup" : function(calldone) {
	                call(opts.dlog, selector, 'destroy');
	                call(opts.msel, select, 'destroy');
	                selector.remove();
	                if (calldone && opts.done) {
	                    opts.done.call(self);
	                }
	            },
	            "msel_opts" : {}
	        }, $.jgrid.col, opts || {});
	        if($.ui) {
	            if ($.ui.gridmultiselect ) {
	                if(opts.msel == "gridmultiselect") {
	                    if(!$.jgrid._multiselect) {
	                        // should be in language file
	                        alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");
	                        return;
	                    }
	                    opts.msel_opts = $.extend($.ui.gridmultiselect.defaults,opts.msel_opts);
	                }
	            }
	        }
	        if (opts.caption) {
	            selector.attr("title", opts.caption);
	        }
	        if (opts.classname) {
	            selector.addClass(opts.classname);
	            select.addClass(opts.classname);
	        }
	        if (opts.width) {
	            $(">div",selector).css({"width": opts.width,"margin":"0 auto"});
	            select.css("width", opts.width);
	        }
	        if (opts.height) {
	            $(">div",selector).css("height", opts.height);
	            select.css("height", opts.height - 10);
	        }
	        //alert("self:"+self[0].p.id);
	        var colModel = self.jqGrid("getGridParam", "colModel");
	        var colNames = self.jqGrid("getGridParam", "colNames");
	        var colMap = {}, fixedCols = [];

	        select.empty();
	        $.each(colModel, function(i) {
	            colMap[this.name] = i;
	            if (this.hidedlg) {
	                if (!this.hidden) {
	                    fixedCols.push(i);
	                }
	                return;
	            }

	            select.append("<option value='"+i+"' "+
	                          (this.hidden?"":"selected='selected'")+">"+colNames[i]+"</option>");
	        });
	        
	        //sort available options alpha
	        /*
	        var op = select.children("option");
	        op.sort(function(a, b) {
	            return a.text > b.text ? 1 : -1;
	        })
	        select.empty().append(op);
	        */
	        
	        function call(fn, obj) {
	            if (!fn) { return; }
	            if (typeof fn == 'string') {
	                if ($.fn[fn]) {
	                    $.fn[fn].apply(obj, $.makeArray(arguments).slice(2));
	                }
	            } else if ($.isFunction(fn)) {
	                fn.apply(obj, $.makeArray(arguments).slice(2));
	            }
	        }

	        var dopts = $.isFunction(opts.dlog_opts) ? opts.dlog_opts.call(self, opts) : opts.dlog_opts;
	        //alert("dopts: "+selector);
	        call(opts.dlog, selector, dopts);
	        var mopts = $.isFunction(opts.msel_opts) ? opts.msel_opts.call(self, opts) : opts.msel_opts;
	        call(opts.msel, select, mopts);
	        // fix height of elements of the multiselect widget
	        var resizeSel = "#colchooser_"+$.jgrid.jqID(self[0].p.id),
	            $container = $(resizeSel + '>div>div.ui-grid-multiselect'),
	            $selectedContainer = $(resizeSel + '>div>div.ui-grid-multiselect>div.selected'),
	            $availableContainer = $(resizeSel + '>div>div.ui-grid-multiselect>div.available'),
	            containerHeight,
	            $selectedActions = $selectedContainer.find('>div.actions'),
	            $availableActions = $availableContainer.find('>div.actions'),
	            $selectedList = $selectedContainer.find('>ul.connected-list'),
	            $availableList = $availableContainer.find('>ul.connected-list');
	        
	        $container.height($container.parent().height()); // increase the container height
	        containerHeight = $container.height();
	        $selectedContainer.height(containerHeight);
	        $availableContainer.height(containerHeight);
	        $selectedList.height(Math.max(containerHeight-$selectedActions.outerHeight()-1,1));
	        
	        $selectedContainer.css({"overflow": "auto"});
	        $availableContainer.css({"overflow": "auto"});
	        
	        $availableList.height(Math.max(containerHeight-$availableActions.outerHeight()-1,1));
	        // extend the list of components which will be also-resized
	        selector.data('dialog',1).dialog('widget').resizable("option", "alsoResize",
	            resizeSel + ',' + resizeSel +'>div' + ',' + resizeSel + '>div>div.ui-grid-multiselect');
	    }
	});
}) ( jQuery );

(function( $ ) {

	var oldResetSelection = $.fn.jqGrid.resetSelection;
	$.jgrid.extend({
		resetSelection: function (){
			$(this).jqGrid("setGridParam", {"allselectedrows": []});
	        var ret = oldResetSelection.call(this);
	        // do something after
	        return ret;
	    }
	});
	
	//Extended for Number Format and Date Format Util.Due to New JqGird5.min we dont have DateFormat function. 
	//So we overrides and added in this JS 
	$.fmatter.util = {
			  NumberFormat: function(b, c) {
		            if ($.fmatter.isNumber(b) || (b *= 1), $.fmatter.isNumber(b)) {
		                var
		                    d, e = 0 > b,
		                    f = String(b),
		                    g = c.decimalSeparator || ".";
		                if ($.fmatter.isNumber(c.decimalPlaces)) {
		                    var
		                        h = c.decimalPlaces,
		                        i = Math.pow(10, h);
		                    if (f = String(Math.round(b * i) / i), d = f.lastIndexOf("."), h > 0)
		                        for (0 > d ? (f += g, d = f.length - 1) : "." !== g && (f = f.replace(".", g)); f.length - 1 - d < h;) f += "0"
		                }
		                if (c.thousandsSeparator) {
		                    var
		                        j = c.thousandsSeparator;
		                    d = f.lastIndexOf(g), d = d > -1 ? d : f.length;
		                    var
		                        k, l = f.substring(d),
		                        m = -1;
		                    for (k = d; k > 0; k--) m++, m % 3 === 0 && k !== d && (!e || k > 1) && (l = j + l), l = f.charAt(k - 1) + l;
		                    f = l
		                }
		                return f = c.prefix ? c.prefix + f : f, f = c.suffix ? f + c.suffix : f
		            }
		            return b
		        },
			   DateFormat: function(a, c, f, d) {
		            var e = /^\/Date\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\)\/$/,
		                g = "string" ===
		                typeof c ? c.match(e) : null,
		                e = function(a, b) {
		                    a = "" + a;
		                    for (b = parseInt(b, 10) || 2; a.length < b;) a = "0" + a;
		                    return a
		                },
		                h = {
		                    m: 1,
		                    d: 1,
		                    y: 1970,
		                    h: 0,
		                    i: 0,
		                    s: 0,
		                    u: 0
		                },
		                i = 0,
		                j, k = ["i18n"];
		            k.i18n = {
		                dayNames: d.dayNames,
		                monthNames: d.monthNames
		            };
		            a in d.masks && (a = d.masks[a]);
		            if (!isNaN(c - 0) && "u" == ("" + a).toLowerCase()) i = new Date(1E3 * parseFloat(c));
		            else if (c.constructor === Date) i = c;
		            else if (null !== g) i = new Date(parseInt(g[1], 10)), g[3] && (a = 60 * Number(g[5]) + Number(g[6]), a *= "-" == g[4] ? 1 : -1, a -= i.getTimezoneOffset(), i.setTime(Number(Number(i) + 6E4 * a)));
		            else {
		                c = ("" + c).split(/[\\\/:_;.,\t\T\s-]/);
		                a = a.split(/[\\\/:_;.,\t\T\s-]/);
		                g = 0;
		                for (j = a.length; g < j; g++) "M" == a[g] && (i = b.inArray(c[g], k.i18n.monthNames), -1 !== i && 12 > i && (c[g] = i + 1)), "F" == a[g] && (i = b.inArray(c[g], k.i18n.monthNames), -1 !== i && 11 < i && (c[g] = i + 1 - 12)), c[g] && (h[a[g].toLowerCase()] = parseInt(c[g], 10));
		                h.f && (h.m = h.f);
		                if (0 === h.m && 0 === h.y && 0 === h.d) return "&#160;";
		                h.m = parseInt(h.m, 10) - 1;
		                i = h.y;
		                70 <= i && 99 >= i ? h.y = 1900 + h.y : 0 <= i && 69 >= i && (h.y = 2E3 + h.y);
		                i = new Date(h.y, h.m, h.d, h.h, h.i, h.s, h.u)
		            }
		            f in d.masks ? f = d.masks[f] :
		                f || (f = "Y-m-d");
		            a = i.getHours();
		            c = i.getMinutes();
		            h = i.getDate();
		            g = i.getMonth() + 1;
		            j = i.getTimezoneOffset();
		            var l = i.getSeconds(),
		                r = i.getMilliseconds(),
		                n = i.getDay(),
		                m = i.getFullYear(),
		                o = (n + 6) % 7 + 1,
		                p = (new Date(m, g - 1, h) - new Date(m, 0, 1)) / 864E5,
		                q = {
		                    d: e(h),
		                    D: k.i18n.dayNames[n],
		                    j: h,
		                    l: k.i18n.dayNames[n + 7],
		                    N: o,
		                    S: d.S(h),
		                    w: n,
		                    z: p,
		                    W: 5 > o ? Math.floor((p + o - 1) / 7) + 1 : Math.floor((p + o - 1) / 7) || (4 > ((new Date(m - 1, 0, 1)).getDay() + 6) % 7 ? 53 : 52),
		                    F: k.i18n.monthNames[g - 1 + 12],
		                    m: e(g),
		                    M: k.i18n.monthNames[g - 1],
		                    n: g,
		                    t: "?",
		                    L: "?",
		                    o: "?",
		                    Y: m,
		                    y: ("" +
		                        m).substring(2),
		                    a: 12 > a ? d.AmPm[0] : d.AmPm[1],
		                    A: 12 > a ? d.AmPm[2] : d.AmPm[3],
		                    B: "?",
		                    g: a % 12 || 12,
		                    G: a,
		                    h: e(a % 12 || 12),
		                    H: e(a),
		                    i: e(c),
		                    s: e(l),
		                    u: r,
		                    e: "?",
		                    I: "?",
		                    O: (0 < j ? "-" : "+") + e(100 * Math.floor(Math.abs(j) / 60) + Math.abs(j) % 60, 4),
		                    P: "?",
		                    T: (("" + i).match(/\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g) || [""]).pop().replace(/[^-+\dA-Z]/g, ""),
		                    Z: "?",
		                    c: "?",
		                    r: "?",
		                    U: Math.floor(i / 1E3)
		                };
		            return f.replace(/\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,
		                function(a) {
		                    return a in q ? q[a] : a.substring(1)
		                })
		        }	
	}
	
	
	
	//For Content Browser
	$.fn.scb = function(options) {
		var gridId = $(this).attr("id"),$self = $(this);
        
		if(!$self.data("firstLoad")) {
			$self.data("firstLoad", true);
		} 
		
		var filterToolbar = true,
		columnChooser = true,
		$grid,
		saveObjectInLocalStorage = function (storageItemName, object) {
			if(options.saveOptionsURL !== undefined && options.saveOptionsError !== undefined && options.saveOptionsFailure !== undefined){
				$.post(options.saveOptionsURL
						,{browserOptions : JSON.stringify(object), ajax: 'true'}
						,function(d) {
							console.log('Successfully Saved Browser State');
						});
			}
            window.sessionStorage.setItem(storageItemName, JSON.stringify(object));
        },
        /*removeObjectFromLocalStorage = function (storageItemName) {
            window.sessionStorage.removeItem(storageItemName);
        },*/
        getObjectFromLocalStorage = function (storageItemName) {
        	//if($self.data("firstLoad")) {
        	//	removeObjectFromLocalStorage(storageItemName);
        	//}
            if (window.sessionStorage.getItem(storageItemName) !== null) {
                return JSON.parse(window.sessionStorage.getItem(storageItemName));
            } else if(options.browserOptions !== undefined && options.browserOptions !== null  && options.browserOptions !== ''){
            	window.sessionStorage.setItem(storageItemName, JSON.stringify(options.browserOptions));
    			return options.browserOptions;
            } else {
            	return null;
            }
        },
        /*myColumnStateName = function (grid) {
        	var currentUserId = window.sessionStorage.getItem("currentUserId");
        	
        	if(currentUserId != null && typeof currentUserId != 'undefined') {
        		return currentUserId+window.location.pathname + '#' + grid[0].id;
        	}
            return window.location.pathname + '#' + grid[0].id;
        },*/
        saveColumnState = function (perm) {
            var colModel = this.jqGrid('getGridParam', 'colModel'), i, l = colModel.length, colItem, cmName,
                postData = this.jqGrid('getGridParam', 'postData'),
                columnsState = {
                    search: this.jqGrid('getGridParam', 'search'),
                    rowNum: this.jqGrid('getGridParam', 'rowNum'),
                    sortname: this.jqGrid('getGridParam', 'sortname'),
                    sortorder: this.jqGrid('getGridParam', 'sortorder'),
                    permutation: perm,
                    colStates: {},
                    orgIds: []
                },
                colStates = columnsState.colStates;
            
            if (typeof (postData.filters) !== 'undefined' && options.filterstatesave) {
                columnsState.filters = postData.filters;
            }
            if (options.pagestatesave){
            	columnsState.page = this.jqGrid('getGridParam', 'page');
            }
            for (i = 0; i < l; i++) {
                colItem = colModel[i];
                cmName = colItem.name;
                if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                    colStates[cmName] = {
                        width: colItem.width,
                        hidden: colItem.hidden
                    };
                }
    			var checkedIds = [],columnProps = this.jqGrid('getColProp', cmName);
				if(columnProps != null && typeof columnProps != 'undefined' && columnProps.stype === 'orgtree') {
					checkedIds= $('#gs_orgFilterNav_'+$self.attr('id'), $self.hDiv).organizationFilter('getChecked');
					//alert(JSON.stringify(checkedIds));
					if(checkedIds.length>0) {
						columnsState.orgIds = checkedIds;
					}
				}
            }
            saveObjectInLocalStorage(gridStateName(this), columnsState);
            return true;
        },
        myDefaultSearch = 'cn',
        myColumnsState,
        isColState,
        restoreColumnState = function (colModel) {
            var colItem, i, l = colModel.length, colStates, cmName,
                columnsState = getObjectFromLocalStorage(gridStateName(this));

            if (columnsState) {
                colStates = columnsState.colStates;
                for (i = 0; i < l; i++) {
                    colItem = colModel[i];
                    cmName = colItem.name;
                    if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                        colModel[i] = $.extend(true, {}, colModel[i], colStates[cmName]);
                    }
                }
            }
            return columnsState;
        },
        updateIdsOfSelectedRows = function (id, isSelected) {
        	idsOfSelectedRows = $(this).jqGrid("getGridParam", "allselectedrows");
        	if(typeof (idsOfSelectedRows) === 'undefined' || idsOfSelectedRows === null) {
        		idsOfSelectedRows = [];
        	}
        	
            var contains = $.inArray(id, idsOfSelectedRows );
            if (!isSelected && contains > -1) {
                for(var i=0; i<idsOfSelectedRows.length; i++) {
                    if(idsOfSelectedRows[i] == id) {
                        idsOfSelectedRows.splice(i, 1);
                        break;
                    }
                }
            }
            else if (contains === -1 ) {
                idsOfSelectedRows.push(id);
            }
            
            $(this).jqGrid("setGridParam", {"allselectedrows": idsOfSelectedRows});
        };
        myColumnsState = restoreColumnState.call($self, options.colModel);
        isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
        
		var defOptions = {	    	
	    	datatype: 'json'
	    	,mtype: 'POST'
	        ,shrinkToFit: false
	        ,pager : '#'+gridId+'Pager'
	        ,rowNum : 10
	        ,rowList : [ 10, 20, 30 ]
			,sortname : 'name'
	        ,sortorder : 'asc'
	        ,filterstatesave : false
	        ,pagestatesave : false
	        ,sortable: {
	        	update: function() {
					setTimeout(function () { 
		        		$self[0].grid.bDiv.scrollLeft=$self[0].grid.hDiv.scrollLeft;
						}, 0);
	        		saveColumnState.call($self, $self[0].p.remapColumns);
	        	}
	        }
			,onPaging: function (b) {
				$self.data("isfilter", true);

        		saveColumnState.call($self, $self[0].p.remapColumns);
			}
	        ,viewrecords: true
	        ,hoverrows : true
	        ,altRows: true
	        ,altclass: 'altrow'
	        ,gridview: true
	        ,loadonce: false
	        ,loadui: 'block'
	        ,deselectAfterSort: false
	        ,height: 'auto'
	        ,viewsortcols: [true,'vertical',true]
	        ,editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}
	        ,allselectedrows:[]
	    };
		if(options.filterToolbar !== undefined) {
			filterToolbar = options.filterToolbar;
		}
		if(options.columnChooser !== undefined) {
			columnChooser = options.columnChooser;
		}
		
		if(options.singleselect !== undefined && options.singleselect === true) {
			var actBeforeSelectRow = options.beforeSelectRow;
			options = $.extend(options,
			{
				beforeSelectRow: function(rowid, e) {
					$(this).jqGrid('resetSelection');
					if(actBeforeSelectRow !== undefined) {
						return actBeforeSelectRow.apply(this, arguments);
					} else {
						return true;
					}
				},
				multiselect: true,
			});
		}
		 
		var actOnSelectRow = options.onSelectRow;
		options = $.extend(options,{
			onSelectRow : function(rowid, status){
				updateIdsOfSelectedRows.call($self, rowid, status);
				if(actOnSelectRow !== undefined){
					actOnSelectRow.apply(this, arguments);
				}                
			}
		});
		
		var actOnSelectAll = options.onSelectAll;
		options = $.extend(options,{
			onSelectAll: function (aRowids, status) {
				if(actOnSelectAll !== undefined){
					actOnSelectAll.apply(this, arguments);
				}
				var allRowsIds;
				if(status) {
					allRowsIds = $(this).jqGrid('getGridParam', 'selarrrow') ;
				} else {
					allRowsIds = $(this).jqGrid("getDataIDs");
				}
				var i, count, id;
		        for (i = 0, count = allRowsIds.length; i < count; i++) {
		            id = allRowsIds[i];
		            updateIdsOfSelectedRows.call($self, id, status);
		        }
			}
		});
		var actResizeStop = options.resizeStop;
		options = $.extend(options,{
			resizeStop: function(){
	        	//saveBrowserOptions();
	        	saveColumnState.call($self, $self[0].p.remapColumns);
	        	if(actResizeStop !== undefined){
	        		actResizeStop.apply(this, arguments);
	        	}
                
			}
		});
		
		var actOnSortCol = options.onSortCol;
		options = $.extend(options,{
			onSortCol : function(){
				saveColumnState.call($self, $self[0].p.remapColumns);
				//saveBrowserOptions();
				if(actOnSortCol !== undefined){
					actOnSortCol.apply(this, arguments);
				}                
			}
		});
		
		var actBeforeProcessing = options.beforeProcessing;

        $self.data("reloading", false);
		options = $.extend(true, options,
		{
			beforeProcessing: function(data) {
				var returnVal = true;
			    if(actBeforeProcessing !== undefined) {
			    	returnVal = actBeforeProcessing.apply(this, arguments);
			    }
			    if(returnVal) {			 	          
			    	$self.data("reloading", true);
			    }
			    
			    return returnVal;
			},
		});
		
		var actLoadComplete = options.loadComplete;
		options = $.extend(options, {
			loadComplete: function (data) {
				$('.ui-search-toolbar input[id^="gs_"]').off('input').on('input',  function(){
					var searchONToolBarValue = $(this).val();
					if((/[^A-Za-z0-9\\\/\.\,\=\+\&\'\:\(\) \_\-!@&$#^%*{}\[\]|~`?;"'<>,.]+/.test(searchONToolBarValue))){
						$(this).attr('disabled', true).css('background','white');
						$("<div id='jqgridInvalidJunkFilters' style='color:red; margin-left:300px;'>Invalid Junk Characters Not Allowed</div>").prependTo('.kite-table');
						var $this = $(this);
						setTimeout(function(){
							$this.val('');
							$('.kite-table').find('#jqgridInvalidJunkFilters').remove();
							$this.attr('disabled', false);
						},1000);
					}					
				});
				//alert('loadComplete');
				var $this = $(this);

	            var reload = $this.data('reload');
	            
	            if(typeof reload != 'undefined' && reload == true) {
	            	$this[0].p.allselectedrows = [];
	            }
	            $this.data('reload', false);
	            if ($(this).jqGrid('getGridParam', 'reccount') == 0)
	            	$($this[0].grid.hDiv).css("overflow-x", "auto");
	            else
	            	$($this[0].grid.hDiv).css("overflow-x", "hidden");
	            
	            if($(this).jqGrid('getGridParam', 'reccount') == 0 && $this[0].grid.bDiv.scrollWidth == $this[0].grid.bDiv.clientWidth)
	            {	 
	            	$($this[0].grid.bDiv).css("overflow-x", "hidden");
	            }
	            else{
	            	$($this[0].grid.hDiv).css("overflow-x", "hidden");
	            	$($this[0].grid.bDiv).css("overflow-x", "auto");
	            }
	            	

	            var rowIds = $(this).jqGrid("getDataIDs");
	            var msel = $(this).jqGrid('getGridParam', 'multiselect');
	            if(null != rowIds && msel == true) {
					var allsel = $(this).jqGrid("getGridParam", "allselectedrows");
		            for (var i = 0; i < rowIds.length; i++) {
		                if ($.inArray( rowIds[i], allsel ) > -1) {
		                	$(this).jqGrid('setSelection', rowIds[i], true);
		                }
		            }
	            }
	            if (isColState && $self.data("firstLoad") && myColumnsState.permutation.length > 0) {
	            	var colperm = myColumnsState.permutation, cplength = colperm.length, ipj=1;
	            	$.each(colperm, function(i) { 
	            		 if(colperm[i] == null) {
	            			 colperm[i] = cplength - ipj;
	            			 ipj++;
	            		 } 
	            	});
                    $this.jqGrid("remapColumns", myColumnsState.permutation, true);
                }

	            if(filterToolbar) {
		            if ($self.data("firstLoad")) {
	                	if (typeof (this.ftoolbar) !== "boolean" || !this.ftoolbar) {
		    	    		$self.jqGrid('filterToolbar',
	                                {	
		    	    					stringResult: true, 
		    	    					searchOnEnter: true, 
		    	    					defaultSearch: myDefaultSearch,
		    	    					beforeSearch: function() { 
		    	    						$self.data("isfilter", true);
		    	    			        },
		    	    					afterSearch: function() { 
		    	    						$self.data("isfilter", false);
		    	    						saveColumnState.call($this, $self[0].p.remapColumns);
		    	    			        }
		    	    			    });
		    	    		
		    	    		initOrganizationTreeFilter(isColState ? myColumnsState.orgIds:[], $self);
		    	    	}
		    	    }
		            
		            if($self.data("orgfilterapplied")) {
		            	$self.data("orgfilterapplied", false);
		            	saveColumnState.call($this, $self[0].p.remapColumns);
		            }
		            // The below line of code is commentted for fix DE3644 IE8 browser issue. 
//		            console.debug("isfilter:" & $self.data("isfilter"));
		            if(!$self.data("isfilter")){
		            	reloadFilterToolbar(this, $this.jqGrid('getGridParam','data'));
		            	setTimeout(function () {
		            		if(options.loadonce) {
		            			$this[0].triggerToolbar();
		            		}
		            		$self.closest('.ui-jqgrid').show();		            	
	                    }, 0);
		            } else {
		            	$self.closest('.ui-jqgrid').show();
		            }		            
	            }
	            else {
	            	$self.closest('.ui-jqgrid').show();
	            }
	            
	            if($self.data("firstLoad")) {
	            	$self.data("firstLoad", false);
	            }
	            $self.data("isfilter", false);
	            
	            if(actLoadComplete !== undefined){
	            	actLoadComplete.apply(this, arguments);
				}
	            
	          //jqgrid rows alternate color
	          //$("tr.jqgrow:odd").addClass('recordBrowserAltRow');

	          //Increasing page edit box size for jqGrid pager.
			  $('.ui-pg-input').prop("size", "4");
				
			}
		});
		
		options = $.extend(false, {}, defOptions, options);
    	
		var col = options.colModel, isOrgTree=false;
		if(filterToolbar) {
	    	for(var i=0; i < col.length; i++) {
				if(col[i] != null && typeof col[i] != 'undefined' 
					&& col[i].stype != null && typeof col[i].stype != 'undefined' 
					&& col[i].stype === 'orgtree') {
					isOrgTree = true;
					break;
				}
	    	}
    	}
    	
		if(isColState) {
			options.page = myColumnsState.page;
			options.search = myColumnsState.search;
			options.sortname = myColumnsState.sortname;
			options.sortorder = myColumnsState.sortorder;
			options.rowNum = myColumnsState.rowNum;
			
			var pdata = {};
			if(isOrgTree){
				//if(myColumnsState.orgIds.length ===0) {
				//	pdata = {"orgIds[]": window.sessionStorage.getItem("defaultOrganizationId")};
				//} else {
					pdata =  {"orgIds": myColumnsState.orgIds, "filters": myColumnsState.filters};
				//}
			} else {
				pdata =  {"filters": myColumnsState.filters};
			}
			options.postData =  $.extend(true,{},options.postData, pdata);
		} else if(isOrgTree){
			pdata = {"orgIds[]": window.sessionStorage.getItem("defaultOrganizationId")};

			options.postData =  $.extend(true,{},options.postData, pdata);
		}
		$grid = $(this).jqGrid(options);
		$(this).closest('.ui-jqgrid').hide();
		var detailViewCaption = $("#detailViewCaption").val();
		var jqgridId = $.jgrid.jqID(this[0].p.id);
		//$grid.jqGrid('navGrid', options.pager, {edit:false, add: false, del: false, search: false, refresh: false});
		//Adding this here brings up a lot of options like view edit and add record.
		//Under New In Version 3.5 explore Form Navigation.
		//INFO instead of adding view and edit, using edit in read only mode,
		// because it can be extended to edit and it can be extended to edit.
		//For editing the view options.
		var refresh=true;
		if(document.getElementById("refreshGrid")!=null && $("#refreshGrid").val()=='false'){
			refresh= false;
		}
		if(document.getElementById("refreshGridManageTestMonitor")!=null && $("#refreshGridManageTestMonitor").val()=='false'){
			refresh= false;
		}
		if(document.getElementById("columnChooserGrid")!=null && $("#columnChooserGrid").val()=='false'){
			columnChooser= false;
		}
		$grid.jqGrid('navGrid', options.pager, {edit:false, add:false, del:false, search:false, refresh:refresh, view:options.viewable, viewtext:detailViewCaption},
				{}, {}, {}, {}, {recreateForm: true, dataheight:400,height:500,
						beforeShowForm: function(form) {
							var Id = jqgridId;
							//Positioning the detial view modal window.
							var dlgDiv = $("#viewmod" + jqgridId);	
							var parentDiv = dlgDiv.parent();
							dlgDiv.css("position","absolute");
							$('#nData').prop('title', 'Next');
							$('#pData').prop('title', 'Previous');
						       dlgDiv.css("left", ( $(window).width() - dlgDiv.width() ) / 3+$(window).scrollLeft() + "px");
						   	$('#nData, #pData').on('mousedown',function(Id){
								$('#'+jqgridId).jqGrid('resetSelection');
							});
						}, caption:detailViewCaption, bClose:'Click to <i>close</i> Detail', width:'auto'});
	    if(columnChooser) {
	    	$.extend(true, $.jgrid.col, {
                width: 600,
                modal: false,
                msel_opts: {dividerLocation: 0.5},
                dialog_opts: {
                    minWidth: 470
                }
            });

	    	$grid.jqGrid('navButtonAdd', options.pager,
	        {
	    		caption: "&nbsp;", //options.columnchoosercaption,
		    	title: "Choose Columns", //options.columnchoosercaption,
		    	buttonicon:'ui-icon-calculator',
		    	position:"first",
		        onClickButton: function() {
//		        	 $self.jqGrid('columnChooser', {
//		                 done: function (perm) {
//		                     if (perm) {
//		                         this.jqGrid("remapColumns", perm, true);
//		                         saveColumnState.call(this, perm);
//		                     }
//		                 }
//		             });
		        	$self.jqGrid('columnChooser');
		        },
		        id: gridId+"buttonColumnChooser"
	        });
	    }
	    
	    if(typeof(options.singleselect) !== 'undefined' && options.singleselect === true) {
	    	$("#cb_"+$(this).attr("id")).hide();
	    }
	    
	    function initOrganizationTreeFilter(checkedIds, $grid) {
	        var col = $grid.jqGrid('getGridParam','colModel');
	        for(var i=0; i < col.length; i++) {
	           if(typeof col[i] !== 'undefined') {
	             var columnName = col[i].name;
	             var columnProps = $grid.jqGrid('getColProp', columnName);
	             //var gridId = $grid.attr('id');
	             if(columnProps != null && typeof columnProps != 'undefined' && columnProps.stype === 'orgtree') {
	               if(columnProps.orgtreeloaded != true) {
	                 var htmlString = '<a href="javascript:void(0)"><div id="gs_orgFilterNav_'+gridId+'" class="filter-tree-element">Filter</div></a>';
	                 $(htmlString).appendTo($("tr.ui-search-toolbar", $grid[0].grid.hDiv).find("th").eq(i).find(':first-child'));
	                 var filterTree = $('#gs_orgFilterNav_'+gridId, $grid[0].grid.hDiv);
	                 filterTree.organizationFilter({"url": columnProps.searchoptions.url, grid: $grid});
	                 filterTree.organizationFilter('setChecked', checkedIds);
	         
	                 filterTree.on('click', function() {
	                 filterTree.organizationFilter('open');
	                 });
	                 columnProps.orgtreeloaded = true;
	             } else {
	                var filterTree = $('#gs_orgFilterNav_'+gridId, $grid[0].grid.hDiv);
	                filterTree.organizationFilter('setChecked', checkedIds);
	              }
	           }
	       }
	    }
	 }

 	    function getColumnIndex(grid, columnIndex) {
           var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
           for (i = 0; i < l; i++) {
               if ((cm[i].index || cm[i].name) === columnIndex) {
                   return i; // return the colModel index
               }
           }
           return -1;
       };
       
	   function reloadFilterToolbar(g, data) {
			var $grid = $(g), postData = $grid.jqGrid('getGridParam', 'postData'), filters,  l,
	        rules, rule, iCol, cm = $grid.jqGrid('getGridParam', 'colModel'),
	        cmi, control, tagName;
//			console.debug("reloadFilterToolbar1 : "+cm.length);
			
			if (typeof (postData.filters) === "string" &&
                typeof ($grid[0].ftoolbar) === "boolean" && $grid[0].ftoolbar) {
				filters = JSON.parse(JSON.stringify(postData.filters));
	            if (filters && filters.groupOp === "AND" && typeof (filters.groups) === "undefined") {
	                // only in case of advance searching without grouping we import filters in the
	                // searching toolbar
	                rules = filters.rules;
	                for (i = 0, l = rules.length; i < l; i++) {
	                    rule = rules[i];
	                    iCol = getColumnIndex($grid, rule.field);
	                    if (iCol >= 0) {
	                        cmi = cm[iCol];
	                        control = $("#gs_" + $.jgrid.jqID(cmi.name), g.grid.hDiv);
	                        if (control.length > 0 &&
	                                (((typeof (cmi.searchoptions) === "undefined" ||
	                                typeof (cmi.searchoptions.sopt) === "undefined")
	                                && rule.op === myDefaultSearch) ||
	                                  (typeof (cmi.searchoptions) === "object" &&
	                                      $.isArray(cmi.searchoptions.sopt) &&
	                                      cmi.searchoptions.sopt.length > 0 &&
	                                      cmi.searchoptions.sopt[0] === rule.op))) {
	                            tagName = control[0].tagName.toUpperCase();
	                            if (tagName === "SELECT") { // && cmi.stype === "select"
	                                control.find("option[value='" + $.jgrid.jqID(rule.data) + "']")
	                                    .attr('selected', 'selected');
	                            } else if (tagName === "INPUT") {
	                                control.val(rule.data);
	                            }
	                        }
	                    }
	                }
	            }
				if(postData.filters){
					filters = JSON.parse(postData.filters);
		            if (filters && filters.groupOp === "AND" && typeof (filters.groups) === "undefined") {
		                // only in case of advance searching without grouping we import filters in the
		                // searching toolbar
		                rules = filters.rules;
		                for (i = 0, l = rules.length; i < l; i++) {
		                    rule = rules[i];
		                    iCol = getColumnIndex($grid, rule.field);
		                    if (iCol >= 0) {
		                        cmi = cm[iCol];
		                        control = $("#gs_" + $.jgrid.jqID(cmi.name), g.grid.hDiv);
		                        if (control.length > 0 &&
		                                (((typeof (cmi.searchoptions) === "undefined" ||
		                                typeof (cmi.searchoptions.sopt) === "undefined")
		                                && rule.op === myDefaultSearch) ||
		                                  (typeof (cmi.searchoptions) === "object" &&
		                                      $.isArray(cmi.searchoptions.sopt) &&
		                                      cmi.searchoptions.sopt.length > 0 &&
		                                      cmi.searchoptions.sopt[0] === rule.op))) {
		                            tagName = control[0].tagName.toUpperCase();
		                            if (tagName === "SELECT") { // && cmi.stype === "select"
		                                control.find("option[value='" + $.jgrid.jqID(rule.data) + "']")
		                                    .attr('selected', 'selected');
		                            } else if (tagName === "INPUT") {
		                                control.val(rule.data);
		                            }
		                        }
		                    }
		                }
		            }
				}   
			}

		};
		
		// to listen to the event and clear allselectedrows
		$grid.on('reloadGrid', function() {
			//$(this).jqGrid("setGridParam", {"allselectedrows": []});
			$(this).data('reload', true);
		});
		
		return $grid;
	};
	
	$.widget("ui.organizationFilter", {
		options: {
        	url: '',
        	grid: null,
        	dialog: {
        		autoOpen : false,
        		draggable: true,
				width: 360,
				height: 350,
				minHeight: 350,
				minWidth: 360
        	},
        	jsTree: { 
    			"core" : {"animation" : 0},
    			"plugins" : ["themes", "json_data", "checkbox", "ui"], 
    			"checkbox": {
    				"override_ui": true,
    				"checked_parent_open": true,
    				"two_state": true
    			},
    			"themes" : {"theme" : "classic", icons: false},
    			progressive_unload:false,
    			progressive_render: true
    		}
        },

		_create: function(){
			var me = this, o = this.options, e = this.element;
			
			// by default, consider this thing closed.
			me._isOpen = false;
			me._checkedIds = [];
			// remember this instance
			$.ui.organizationFilter.instances.push(e);

        	var content = '<div id="dialogId"><div id="filterTree"></div></div>';
        	
        	var $dialog = $(content).dialog(o.dialog);
        	$dialog.dialog("option", "buttons", [{ text: "Apply", click: function() { me._apply(); }}]);
        	
        	//AART - Below one line is commented out as we don't need these.
        	//var toolbar = '<a href="?#" id="orgTreeCheckAll">Select All</a>&nbsp;|&nbsp;<a href="?#" id="orgTreeUncheckAll">Deselect All</a>';
        	var toolbar = '<a href="?#" id="orgTreeUncheckAll">Deselect All</a>';
    		//toolbar += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="?#" id="orgTreeExpandAll">Expand All</a>&nbsp;|&nbsp;';
    		//toolbar += '<a href="?#" id="orgTreeCollapseAll">Collapse All</a>&nbsp;&nbsp';
        	$(toolbar).insertBefore($dialog.dialog().parents(".ui-dialog").find('.ui-dialog-titlebar-close'));
        	$dialog.dialog().parents(".ui-dialog").find(".ui-dialog-title").remove();
        	
        	//AART - Below two lines are commented out as we don't have any default or selection. 
        	//var helpText = '<div class="help">* indicates your default organization.</div>';
        	//$(helpText).insertAfter($dialog.dialog().parents(".ui-dialog").find(".ui-dialog-buttonpane").children(":first"));
        	me._dialog = $dialog;
        	me._defaultOrganizationId = 0;
        	me._filterTree = $('#filterTree', $dialog);        	        
        	
        	//Commented to not to store in the cache        	
        	//var obj = window.sessionStorage.getItem(window.sessionStorage.getItem("loggedInUserId")+"#orgTreeData"),jstreeoptions;
        	var obj = "";
        	//console.debug(obj);
        	if(obj) {
        		obj = JSON.parse(window.sessionStorage.getItem(window.sessionStorage.getItem("loggedInUserId")+"#orgTreeData"));
        		//AART - Below two lines are commented out as we don't have any default or selection.
        		//me._defaultOrganizationId = obj.defaultOrganizationId;
	        	//jstreeoptions = $.extend(true, o.jsTree, {"json_data" : {"data": obj.data}});
	        	jstreeoptions = $.extend(true, o.jsTree, {"json_data" : {"data": obj, "progressive_unload":false, "progressive_render" : true}});
        	} else {
        		jstreeoptions = $.extend(true, o.jsTree, {"json_data" : {
		        	"ajax": { "url": o.url,
	    		            "type": "POST",
	    		            "data": function (n) {
	    		                return { id: n.attr ? n.attr("id") : 0 };	    		            	
	    		            },
	    		            "success": function (res) {
					            //	window.sessionStorage.setItem(window.sessionStorage.getItem("loggedInUserId")+"#orgTreeData", JSON.stringify(res));
					        		//AART - Below one line is commented out as we don't have any default or selection.
					            	//me._defaultOrganizationId = res.defaultOrganizationId;
					            	
					            	//console.log("in sessionStorage - " + JSON.stringify(window.sessionStorage.getItem(window.sessionStorage.getItem("loggedInUserId")+"#orgTreeData")));
					            	if (res == null) {
					            		$dialog.dialog().parents(".ui-dialog").find(".ui-dialog-title").find('#orgTreeUncheckAll').remove();
					            		$('.ui-dialog-titlebar').find('a').remove();
					            		$('#dialogId').html("You do not have permission to view organizations");
					            		$('#dialogId').dialog({ buttons: [ { text: "Ok", click: function() { $( this ).dialog( "close" ); } } ] });
					            		return "Access Denied";
					            		
					            	} else {
					            		return res.data;
					            	}
					            }
				        	},
				        	progressive_unload:false, 
				        	progressive_render : true
			        	}
			        	
		        	});
        	}        	
        	
        	me._filterTree.jstree(jstreeoptions);
        	//uncheck what was previously checked, then check what was last applied
			me._filterTree.jstree('uncheck_all').on("loaded.jstree", function (event, data) {
				if(me._checkedIds) {
					for(var i=0; i < me._checkedIds.length; i++) {
						me._filterTree.jstree('check_node', "#"+me._checkedIds[i]); 
					}
				}
           });
			
        	$dialog.dialog().parents(".ui-dialog").find('#orgTreeCollapseAll').on('click', function(event) {
    			event.preventDefault();
    			me._filterTree.jstree('close_all');
    		});
        	$dialog.dialog().parents(".ui-dialog").find('#orgTreeExpandAll').on('click', function(event) {
    			event.preventDefault();
    			me._filterTree.jstree('open_all');
    		});
        	$dialog.dialog().parents(".ui-dialog").find('#orgTreeCheckAll').on('click', function(event) {
    			event.preventDefault();
    			me._filterTree.jstree('check_all');
    		});
        	$dialog.dialog().parents(".ui-dialog").find('#orgTreeUncheckAll').on('click', function(event) {
    			event.preventDefault();
    			me._filterTree.jstree('uncheck_all');
    		});        	                
		},

		_init: function(){
			// call open if this instance should be open by default
			if( this.options.dialog.autoOpen ){
				this.open();
			}
		},

		open: function(){
			this._isOpen = true;

			// trigger beforeopen event.  if beforeopen returns false,
			// prevent bail out of this method. 
			if( this._trigger("beforeopen") === false ){
				return;
			}

			// call methods on every other instance of this dialog
			$.each( this._getOtherInstances(), function(){
				var $this = $(this);

				if($this.organizationFilter("isOpen")){
					$this.organizationFilter("close");
				}
			});
			
			var position =  this.element.offset();
			this._dialog.dialog("option", "position", [position.left, position.top+20]);
			// more open related code here
			this._dialog.dialog('open');
			
			//uncheck what was previously checked, then check what was last applied
			this._filterTree.jstree('uncheck_all');
			if(this._checkedIds) {
				for(var i=0; i < this._checkedIds.length; i++) {
					this._filterTree.jstree('check_node', "#"+this._checkedIds[i]); 
				}
			}
			
			if(this._defaultOrganizationId > 0) {
				var defOrgNode = $("#"+this._defaultOrganizationId+" a", this._filterTree).first();
				defOrgNode.addClass('organization-tree-default-org');
				defOrgNode.addClass('ui-priority-primary');
				var ntext = this._filterTree.jstree('get_text', "#"+this._defaultOrganizationId);
				if(ntext.charAt( 0 ) !== '*') {
					this._filterTree.jstree('rename_node', "#"+this._defaultOrganizationId,
							"* "+ntext);
				}
				
				if(!this._checkedIds || this._checkedIds.length === 0) {
					this._filterTree.jstree('check_node', "#"+this._defaultOrganizationId);
				}
			}
			// trigger open event
			this._trigger("open");

			return this;
		},

		close: function(){
			this._isOpen = false;
			this._dialog.dialog('close');
			// trigger close event
			this._trigger("close");

			return this;
		},

		isOpen: function(){
			return this._isOpen;
		},
		
		setChecked: function(ids) {
			this._checkedIds = ids;
			//uncheck what was previously checked, then check what was last applied
			this._filterTree.jstree('uncheck_all');
			if(this._checkedIds) {
				for(var i=0; i < this._checkedIds.length; i++) {
					this._filterTree.jstree('check_node', "#"+this._checkedIds[i]); 
				}
			}			
		},
		
		getChecked: function() {
			var checkedIds = []; 
	        this._filterTree.jstree("get_checked",null,true).each(function () { 
	            checkedIds.push(this.id); 
	        }); 
			this._checkedIds = checkedIds;			
			return this._checkedIds;
		},

		destroy: function(){
			// remove this instance from $.ui.organizationFilter.instances
			var element = this.element,
				position = $.inArray(element, $.ui.organizationFilter.instances);

			// if this instance was found, splice it off
			if(position > -1){
				$.ui.organizationFilter.instances.splice(position, 1);
			}

			// call the original destroy method since we overwrote it
			$.Widget.prototype.destroy.call( this );
		},

		_getOtherInstances: function(){
			var element = this.element;

			return $.grep($.ui.organizationFilter.instances, function(el){
				return el !== element;
			});
		},
		
		/*_setOption: function(key, value){
			this.options[key] = value;

			switch(key){
				case "something":
					// perform some additional logic if just setting the new
					// value in this.options is not enough. 
					break;
			}
		},
		*/
		
		_apply: function() {
			//var checkedIds = [];			
			var checkedIds = new Array();
	        this._filterTree.jstree("get_checked",null,true).each(function () { 
	            checkedIds.push(this.id);	            	           
	        }); 
			this._checkedIds = checkedIds;
			
			//console.log("Outside checkedIds this - " + JSON.stringify(this._checkedIds));
			
			if(this.options.grid) {
				
				var grid = $(this.options.grid);
				//pass 0 for not to get from session
				if(checkedIds.length === 0) {
					checkedIds.push(0);
					this._checkedIds = $('#orgChildrenIds').val();					
				}
				//server side comment
				/*
				var col = grid.jqGrid('getGridParam','colModel'); 
				for(var i=0; i < col.length; i++) {
					var columnName = col[i].name;
				 	var columnProps = grid.jqGrid('getColProp', columnName);
					if(columnProps.stype === 'select') {
						$("#gs_" + $.jgrid.jqID(columnName)+' option:gt(0)', grid[0].hDiv).remove();
					}
				}
				 */
				
				grid.data("orgfilterapplied", true);
				var postData = grid.jqGrid('getGridParam', 'postData');

	            grid.setGridParam({postData: null});
	            //grid.setGridParam({postData: {_search : true, orgChildrenIds: this._checkedIds, filters: postData.filters }});
	            grid.setGridParam({postData: {_search : true, orgChildrenIds:  this._checkedIds.toString(), filters: postData.filters}});
	            grid.setGridParam({datatype: 'json'});
	            //grid.trigger('reloadGrid');
	            grid.trigger('reloadGrid',[{page:1}]);
			}
            this.close();
		}
	});

	$.extend($.ui.organizationFilter, {
		instances: []
	});
})( jQuery );
