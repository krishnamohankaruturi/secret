getColumnIndex = function (grid, columnIndex) {
		        var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
		        for (i = 0; i < l; i++) {
		            if ((cm[i].index || cm[i].name) === columnIndex) {
		                return i; // return the colModel index
		            }
		        }
		        return -1;
		    }
			  refreshSerchingToolbar = function ($grid, myDefaultSearch) {
		          var postData = $grid.jqGrid('getGridParam', 'postData'), filters, i, l,
		              rules, rule, iCol, cm = $grid.jqGrid('getGridParam', 'colModel'),
		              cmi, control, tagName;

		          for (i = 0, l = cm.length; i < l; i++) {
		        	  if(typeof cm[i] != 'undefined') {
			              control = $("#gs_" + $.jgrid.jqID(cm[i].name));
			              if (control.length > 0) {
			                  tagName = control[0].tagName.toUpperCase();
			                  if (tagName === "SELECT") { // && cmi.stype === "select"
			                      control.find("option[value='']")
			                          .attr('selected', 'selected');
			                  } else if (tagName === "INPUT") {
			                      control.val('');
			                  }
			              }
		        	  }
		          }

		          if (typeof (postData.filters) === "string" &&
		                  typeof ($grid[0].ftoolbar) === "boolean" && $grid[0].ftoolbar) {

		              filters = $.parseJSON(postData.filters);
		              if (filters && filters.groupOp === "AND" && typeof (filters.groups) === "undefined") {
		                  // only in case of advance searching without grouping we import filters in the
		                  // searching toolbar
		                  rules = filters.rules;
		                  for (i = 0, l = rules.length; i < l; i++) {
		                      rule = rules[i];
		                      iCol = getColumnIndex($grid, rule.field);
		                      if (iCol >= 0) {
		                          cmi = cm[iCol];
		                          control = $("#gs_" + $.jgrid.jqID(cmi.name));
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
			saveObjectInLocalStorage = function (storageItemName, object) {
		        if (typeof window.sessionStorage !== 'undefined') {
		            window.sessionStorage.setItem(storageItemName, JSON.stringify(object));
		        }
		    }
		    removeObjectFromLocalStorage = function (storageItemName) {
		        if (typeof window.sessionStorage !== 'undefined') {
		            window.sessionStorage.removeItem(storageItemName);
		        }
		    }
		    getObjectFromLocalStorage = function (storageItemName) {
		        if (typeof window.sessionStorage !== 'undefined') {
		            return JSON.parse(window.sessionStorage.getItem(storageItemName));
		        }
		    }
		    
		    saveColumnState = function (perm, myColumnStateName) {
		        var colModel = this.jqGrid('getGridParam', 'colModel'), i, l = colModel.length, colItem, cmName,
		            postData = this.jqGrid('getGridParam', 'postData'),
		            columnsState = {
		                search: this.jqGrid('getGridParam', 'search'),
		                page: this.jqGrid('getGridParam', 'page'),
		                sortname: this.jqGrid('getGridParam', 'sortname'),
		                sortorder: this.jqGrid('getGridParam', 'sortorder'),
		                permutation: perm,
		                colStates: {}
		            },
		            colStates = columnsState.colStates;

		        if (typeof (postData.filters) !== 'undefined') {
		            columnsState.filters = postData.filters;
		        }

		        for (i = 0; i < l; i++) {
		            colItem = colModel[i];
		            if(typeof colItem != 'undefined') {
			            cmName = colItem.name;
			            if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
			                colStates[cmName] = {
			                    width: colItem.width,
			                    hidden: colItem.hidden
			                };
			            }
		            }
		        }
		        saveObjectInLocalStorage(myColumnStateName, columnsState);
		    }
		    restoreColumnState = function (colModel, myColumnStateName) {
		        var colItem, i, l = colModel.length, colStates, cmName,
		            columnsState = getObjectFromLocalStorage(myColumnStateName);
		        if (columnsState) {
		            colStates = columnsState.colStates;
		            for (i = 0; i < l; i++) {
		                colItem = colModel[i];
		                if(typeof colItem != 'undefined') {
			                cmName = colItem.name;
			                if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
			                    colModel[i] = $.extend(true, {}, colModel[i], colStates[cmName]);
			                }
		                }
		            }
		        }
		        return columnsState;
		    }
		    