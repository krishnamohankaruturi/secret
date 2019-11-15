
$.widget('custom.orgFilter', {
	 options: {
		 url: 'getStatesOrgsForUser.htm',
		 childOrgUrl: 'getOrgsBasedOnUserContext.htm',
		 requiredLevels: [],
		 containerClass: 'config orgFilterDivVert full_side full_side_custom',
		 showLevelsUntil: 70,
		 getAllUserOrgs : false,
		 screen : 'normal',
		 selectedIds: null //{lvlNum: id}
		 
	 },
	_orgHierarchies: {},
	_childOrg: 20,
	_create: function() {
		var me = this;
		var myId = this.element[0].id;
		
		//var currentOrganizationType = $('#switchRole option:selected' ).attr('data-currentOrganizationType');
		var currentOrganizationType = $('#userCurrentOrganizationType' ).val();
		
		//OTHER WAYS OF USING JQUERY TO GET DATA ATTRIBUTE VALUE FROM SELECTED OPTION
		//var currentOrganizationType = $('#switchRole').find(':selected' ).attr('data-currentOrganizationType');
		//var currentOrganizationType = $('#switchRole').find(":selected" ).data('currentOrganizationType');
		//var currentOrganizationType = $('#switchRole').find(":selected" ).attr('data-currentOrganizationType');
		
		if(currentOrganizationType == 'ST' || currentOrganizationType == 'CONS'){
			this.options.requiredLevels = [20];
		} else if(currentOrganizationType == 'RG'){
			this.options.requiredLevels = [30];
		} else if(currentOrganizationType == 'AR'){
			this.options.requiredLevels = [40];
		} else if(currentOrganizationType == 'DT'){
			this.options.requiredLevels = [50];
		} else if(currentOrganizationType == 'BLDG'){
			this.options.requiredLevels = [60];
		} else if(currentOrganizationType == 'SCH'){
			this.options.requiredLevels = [70];
		}
		
		if(this.options.requiredLevels && this.options.requiredLevels.length >0) {
			this._maxLevelRequired = Math.max.apply(Math, this.options.requiredLevels);
		} else {
			this._maxLevelRequired = -1;
		}
		var myIdState = myId + '_state',
			myIdRegion = myId + '_region',
			myIdArea = myId + '_area',
			myIdDistrict = myId + '_district',
			myIdBuilding = myId + '_building',
			myIdSchool = myId + '_school';
		
		var myHtml = me._createFilterHtml(myIdState,'State', 20) +
		me._createFilterHtml(myIdRegion,'Region', 30) +
		me._createFilterHtml(myIdArea,'Area', 40) +
		me._createFilterHtml(myIdDistrict,'District', 50) +
		me._createFilterHtml(myIdBuilding,'Building', 60) +
		me._createFilterHtml(myIdSchool,'School',70);	
			
		$('<div>',{
			html: myHtml
		}).addClass(me.options.containerClass).appendTo(this.element);
		
		me._hideAllOrgsFilters(myId);
		me._setSelectedValues(myId);
	
		var multiselectOpts = {
			placeholder:'Select',
			multiple: false,
			allowClear : true	
		};
		
		$('#'+myIdState).select2({
			placeholder:'Select',
			multiple: false,
			allowClear : true
		});
		$('#'+myIdRegion).select2(multiselectOpts);
		$('#'+myIdArea).select2(multiselectOpts);
		$('#'+myIdDistrict).select2(multiselectOpts);
		$('#'+myIdBuilding).select2(multiselectOpts);
		$('#'+myIdSchool).select2(multiselectOpts);
		$.ajax({
			url: me.options.url,
			data: { getAllUserOrgs : this.options.getAllUserOrgs },
			dataType: 'json',
			type: "GET",
			cache: false
		}).done(function(states) {
				var stateDrpdwn = $('#'+myIdState);
				if (states != null) {
					states.sort(dynamicSort("organizationName"));
				//	$('#'+myIdState).combobox("clear");
					
					$.each(states, function(i, state) {
						stateDrpdwn.append($('<option></option>').attr("value", state.id).text(state.organizationName));
						if(state.organizationHierarchies != null) {
							me._orgHierarchies[state.id] = state.organizationHierarchies.map(function (x) { 
								return parseInt(x, 10); 
							});
						}
					});
					
					stateDrpdwn.val([]);
					if (states.length == 1 && me.options.screen != 'edit') {
						$('#'+myIdState +' > option').eq(1).attr('selected','selected');
						me._findNextOrg(myId,me._childOrg);
						stateDrpdwn.trigger('change');
					} else {
						var lastid = stateDrpdwn.data('lastid');
						if(lastid != "") {
							stateDrpdwn.val(lastid);
							me._findNextOrg(myId,me._childOrg);
							stateDrpdwn.trigger('change');

							$.removeData( stateDrpdwn, "lastid" );
						}					
					}
					stateDrpdwn.trigger('change.select2');
					$('.'+myId+'_stateRow').show();
					if(me._maxLevelRequired > 10) {
						stateDrpdwn.addClass('required');
					}
				} else {
					$('.'+myId+'_stateRow').show();

					if(me._maxLevelRequired > 10) {
						stateDrpdwn.addClass('required');
					}
				}
			});

		$('#'+ myIdState + ', #' + myIdRegion + ', #' + myIdArea + ', #'+myIdDistrict+ ', #' + myIdBuilding).change(function(event) {	
			var orgId;
			var defaultSelectId = $('#'+event.target.id).val();
			
			if($(this).children().length==2 && me.options.screen != 'edit'){
				$(this).find("option:nth-child(2)").prop("selected", true);			
			}
			
			if(event.target.id == myIdSchool) {
				orgId = $('#'+myIdSchool).val();
			} else if(event.target.id == myIdBuilding) {
				orgId = $('#'+myIdBuilding).val();		
				me._findNextOrg(myId,60);
			} else if(event.target.id == myIdDistrict) {
				orgId = $('#'+myIdDistrict).val();
				me._findNextOrg(myId,50);
			} else if(event.target.id == myIdArea) {
				orgId = $('#'+myIdArea).val();
				me._findNextOrg(myId,40);
			} else if(event.target.id == myIdRegion) {
				orgId = $('#'+myIdRegion).val();
				me._findNextOrg(myId,30);		
			} else if(event.target.id == myIdState) {
				orgId = $('#'+myIdState).val();
				me._clearPreviousSelection(myId,me._childOrg);

				me._hideAllOrgsFilters(myId);
				if(orgId != 0) {
					me._findNextOrg(myId,20);
					me._showRequiredOrgsFilters(myId);
				} else {
					me._childOrg = 30;
				}
			}

			var url;
			var dataValues;
			var filter;
			var filterOption;

			url = me.options.childOrgUrl;
			if(me._childOrg == '30') {
				filter = '#'+myIdRegion;
				filterOption = filter + " option";
				dataValues =  { orgId: orgId, orgType: 'RG',orgLevel:30 };
			} else if(me._childOrg == '40') {
				filter = '#'+myIdArea;
				filterOption = filter + " option";
				dataValues =  { orgId: orgId, orgType: 'AR',orgLevel:40 };
			} else if(me._childOrg == '50') {
				dataValues =  { orgId: orgId, orgType: 'DT',orgLevel:50 };
				filter = '#'+ myIdDistrict;
				filterOption = filter + " option";
			} else if(me._childOrg == '60') {
				filter = '#' + myIdBuilding;
				filterOption = filter + " option";
				dataValues =  { orgId: orgId, orgType: 'BLDG',orgLevel:60 };
			} else if(me._childOrg == '70') {
				dataValues =  { orgId: orgId, orgType: 'SCH',orgLevel:70 };
				filter = '#'+ myIdSchool;
				filterOption = filter + " option";
			}
            
			if (orgId != null && orgId != 0 && me._childOrg < 80  && defaultSelectId != '') {
				$.ajax({
					url: url,
					data: dataValues,
					dataType: 'json',
					type: "GET",
					cache: false 
				}).done(function(orgs) {
					me._clearPreviousSelection(myId, me._childOrg);
					if (orgs != null) {
						orgs.sort(dynamicSort("organizationName"));
						$.each(orgs, function(i, org) {
							if(!$(filter).find("[value="+org.id+"]").length){
							$(filter).append($('<option></option>').attr("value", org.id).text(org.organizationName));
							}
						});
					}
					$(filter).val([]);
					me._findNextOrg(myId,me._childOrg);
					
					if (orgs.length == 1 && me.options.screen != 'edit') {
						$(filter +' option:nth-child(2)').prop('selected',true);
						$(filter).trigger('change');
					} else {
					var lastid = $(filter).data('lastid');
					if(typeof lastid != 'undefined' && lastid != "") {
						$(filter).val(lastid);
						$(filter).trigger('change');
						$.removeData( $(filter), "lastid" );
					}
					}
					$(filter).trigger('change.select2');
				});
			} else {
				me._clearPreviousSelection(myId,me._childOrg);
			}
			
			if($(this).children().length==2 && me.options.screen != 'edit' && defaultSelectId ===''){
				$(this).find("option:nth-child(1)").prop("selected", true);			
			}
		});
	},
	_findNextOrg: function(id,currentOrg) {
		var stateId = $('#'+id+'_state').val();
		if(stateId == -1)
			stateId = 0;
		
		for(var i=10;i<80;i=i+10) {
			if($.inArray(parseInt(currentOrg)+i, this._orgHierarchies[stateId]) >= 0) {
				this._childOrg = parseInt(currentOrg)+i;
				return;
			}
		}
		this._childOrg = 80;
	},
	_createFilterHtml: function (id,label, lvlNum) {
		var filterHtml = [];
		filterHtml.push('<div class="'+id+'Row hidden form-fields org-tree">');
		filterHtml.push('<label for="'+id+'" class="field-label">'+label+':');	
		if (lvlNum <= this._maxLevelRequired) {
			filterHtml.push('<span class="lbl-required">*</span>');
		}
		filterHtml.push('</label>');

		filterHtml.push('<select id="' + id +'" class="bcg_select" title="'+label+'" name="orgLevel_'+lvlNum+'"><option value="">Select</option></select>');
		filterHtml.push('</div>');
		return filterHtml.join('');
	},
	_clearPreviousSelection: function(myId,childOrg) {
		var state = '#' + myId + '_state',
			region = '#' + myId + '_region',
			area = '#' + myId + '_area',
			district = '#' + myId + '_district',
			building = '#' + myId + '_building',
			school = '#' + myId + '_school';
		if(childOrg == 20) {
			$(state).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(region).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(area).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(district).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(building).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(school).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			
			$(state).trigger('change.select2');
			$(region).trigger('change.select2');
			$(area).trigger('change.select2');
			$(district).trigger('change.select2');
			$(building).trigger('change.select2');
			$(school).trigger('change.select2');
		} else if(childOrg == 30) {
			$(region).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(area).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(district).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(building).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(school).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			
			$(region).trigger('change.select2');
			$(area).trigger('change.select2');
			$(district).trigger('change.select2');
			$(building).trigger('change.select2');
			$(school).trigger('change.select2');
		} if(childOrg == 40) {
			$(area).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(district).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(building).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(school).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			
			$(area).trigger('change.select2');
			$(district).trigger('change.select2');
			$(building).trigger('change.select2');
			$(school).trigger('change.select2');
		} else if(childOrg == 50) {
			$(district).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(building).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(school).find('option').filter(function(){return $(this).val() !='';}).remove().end();

			$(district).trigger('change.select2');
			$(building).trigger('change.select2');
			$(school).trigger('change.select2');
		} else if(childOrg == 60) {
			$(building).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			$(school).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			
			$(building).trigger('change.select2');
			$(school).trigger('change.select2');
		} else if(childOrg == 70) {
			$(school).find('option').filter(function(){return $(this).val() !='';}).remove().end();
			
			$(school).trigger('change.select2');
		}
	},
	_showRequiredOrgsFilters: function(myId) {
		var stateId = $('#'+myId+'_state').val();
		if(stateId == -1)
			stateId = 0;
		$('.'+myId+'_stateRow').show();
		for(var i=10;i<80;i=i+10) {
			if($.inArray(i, this._orgHierarchies[stateId]) !=-1) {
				if(i <= this.options.showLevelsUntil ) {
					if(i == 30) {
						$('.'+myId+'_regionRow').show();
					} else if(i == 40) {
						$('.'+myId+'_areaRow').show();
					} else if(i == 50) {
						$('.'+myId+'_districtRow').show();
					} else if(i == 60) {
						$('.'+myId+'_buildingRow').show();
					} else if(i == 70) {
						$('.'+myId+'_schoolRow').show();
					}
				}
			}
		}

		this._setRequiredOrgsFilters(myId);
	},
	_setRequiredOrgsFilters: function(myId) {
		var stateId = $('#'+myId+'_state').val();
		if(stateId == -1)
			stateId = 0;
		if(this._maxLevelRequired > 10) {
			if($('#'+myId+'_state').prev().has( 'span.lbl-required').length == 0) {
				$('#'+myId+'_state').prev().append('<span class="lbl-required">*</span>');
			}
			$('#'+myId+'_state').addClass('required');
		} else {
			$('#'+myId+'_state').removeClass('required');
			$('#'+myId+'_state').prev().find('span.lbl-required').remove();
		}
		$('#'+myId+'_region').removeClass('required');
		$('#'+myId+'_region').prev().find('span.lbl-required').remove();
		$('#'+myId+'_area').removeClass('required');
		$('#'+myId+'_area').prev().find('span.lbl-required').remove();
		$('#'+myId+'_district').removeClass('required');
		$('#'+myId+'_district').prev().find('span.lbl-required').remove();
		$('#'+myId+'_building').removeClass('required');
		$('#'+myId+'_building').prev().find('span.lbl-required').remove();
		$('#'+myId+'_school').removeClass('required');
		$('#'+myId+'_school').prev().find('span.lbl-required').remove();
		
		for(var i=10;i<80;i=i+10) {
			if($.inArray(i, this._orgHierarchies[stateId]) !=-1) {
				if(i <= this.options.showLevelsUntil ) {
					if(i == 30) {
						if (i <= this._maxLevelRequired) {
							$('#'+myId+'_region').addClass('required');
							if($('#'+myId+'_region').prev().has( 'span.lbl-required').length == 0) {
								$('#'+myId+'_region').prev().append('<span class="lbl-required">*</span>');
							}
						}
					} else if(i == 40) {
						if (i <= this._maxLevelRequired) {
							$('#'+myId+'_area').addClass('required');
							if($('#'+myId+'_area').prev().has( 'span.lbl-required').length == 0) {
								$('#'+myId+'_area').prev().append('<span class="lbl-required">*</span>');
							}
						}
					} else if(i == 50) {
						if (i <= this._maxLevelRequired) {
							$('#'+myId+'_district').addClass('required');
							if($('#'+myId+'_district').prev().has( 'span.lbl-required').length == 0) {
								$('#'+myId+'_district').prev().append('<span class="lbl-required">*</span>');
							}
						}
					} else if(i == 60) {
						if (i <= this._maxLevelRequired) {
							$('#'+myId+'_building').addClass('required');
							if($('#'+myId+'_building').prev().has( 'span.lbl-required').length == 0) {
								$('#'+myId+'_building').prev().append('<span class="lbl-required">*</span>');
							}
						}
					} else if(i == 70) {
						if (i <= this._maxLevelRequired) {
							$('#'+myId+'_school').addClass('required');
							if($('#'+myId+'_school').prev().has( 'span.lbl-required').length == 0) {
								$('#'+myId+'_school').prev().append('<span class="lbl-required">*</span>');
							}
						}
					}
				}
			}
		}
	},
	_showAllOrgsFilters: function(myId) {
		$('.'+myId+'_regionRow').show();
		$('.'+myId+'_areaRow').show();
		$('.'+myId+'_districtRow').show();
		$('.'+myId+'_buildingRow').show();
		$('.'+myId+'_schoolRow').show();
		this._setRequiredOrgsFilters(myId);
	},
	_hideAllOrgsFilters: function(myId) {
		$('.'+myId+'_regionRow').hide();
		$('.'+myId+'_areaRow').hide();
		$('.'+myId+'_districtRow').hide();
		$('.'+myId+'_buildingRow').hide();
		$('.'+myId+'_schoolRow').hide();
		
		$('#'+myId+'_region').removeClass('required');
		$('#'+myId+'_area').removeClass('required');
		$('#'+myId+'_district').removeClass('required');
		$('#'+myId+'_building').removeClass('required');
		$('#'+myId+'_school').removeClass('required');
	},
	_setSelectedValues: function(myId) {
		var selIds = this.options.selectedIds;
		if(null != selIds) {
			for(var i=20;i<80;i=i+10) {
				//console.log(selIds[''+i]);
				if(selIds[''+i] != null && typeof selIds[''+i] != 'undefined' && selIds[''+i] != '') {
					if(i == 20) {
						$('#'+myId + '_state').data('lastid', selIds[''+i]);
					} else if(i == 30) {
						$('#'+myId+'_region').data('lastid', selIds[''+i]);
					} else if(i == 40) {
						$('#'+myId+'_area').data('lastid', selIds[''+i]);
					} else if(i == 50) {
						$('#'+myId+'_district').data('lastid', selIds[''+i]);
					} else if(i == 60) {
						$('#'+myId+'_building').data('lastid', selIds[''+i]);
					} else if(i == 70) {
						$('#'+myId+'_school').data('lastid', selIds[''+i]);
					}
				}
			}
		}
		//$('#'+myId+'_state').removeClass('required');
		$('#'+myId+'_region').removeClass('required');
		$('#'+myId+'_area').removeClass('required');
		$('#'+myId+'_district').removeClass('required');
		$('#'+myId+'_building').removeClass('required');
		$('#'+myId+'_school').removeClass('required');
	},
	_hideOrgsFilters: function(myId,currentOrg) {
		var state = '.' + myId + '_stateRow',
			region = '.' + myId + '_regionRow',
			area = '.' + myId + '_areaRow',
			district = '.' + myId + '_districtRow',
			building = '.' + myId + '_buildingRow',
			school = '.' + myId + '_schoolRow';

		if(currentOrg == 30) {
			$(state).hide();
		} else if(currentOrg == 40) {
			$(state).hide();
			$(region).hide();
		} else if(currentOrg == 50) {
			$(state).hide();
			$(region).hide();
			$(area).hide();
		} else if(currentOrg == 60) {
			$(state).hide();
			$(region).hide();
			$(area).hide();
			$(district).hide();
		} else if(currentOrg == 70) {
			$(state).hide();
			$(region).hide();
			$(area).hide();
			$(district).hide();
			$(building).hide();
		}
	},
	_setOption: function( key, value ) {
        this.setOption(key, value);
	},
	setOption: function( key, value ) {
		var myId = this.element[0].id;
        this.options[ key ] = value;
        if(key == 'requiredLevels') {
	        if(this.options.requiredLevels && this.options.requiredLevels.length > 0) {
				this._maxLevelRequired = Math.max.apply(Math, this.options.requiredLevels);
			} else {
				this._maxLevelRequired = -1;
			}
	        this._setRequiredOrgsFilters(myId);
        }
        this._setSelectedValues(myId);
        var stateDrpdwn = $('#'+myId + '_state');
        var lastid = stateDrpdwn.data('lastid');
		if(lastid != "") {
			stateDrpdwn.val(lastid);
			this._findNextOrg(myId,this._childOrg);
			stateDrpdwn.trigger('change');

			$.removeData( stateDrpdwn, "lastid" );
			stateDrpdwn.trigger('change.select2');
		}
        
	},
	// create a public method
	value: function( lvl, value ) {
		var myId = this.element[0].id;
		// no value passed, act as a getter
		if ( value === undefined || lvl === undefined) {
			var orgId = null;
			for(var i=20;i<80;i=i+10) {
				if(i == 20 && $('#'+myId + '_state').val() != null
						&& $('#'+myId + '_state').val() != '') {
					orgId = $('#'+myId + '_state').val();
				} else if(i == 30 && $('#'+myId + '_region').val() != null
						&& $('#'+myId + '_region').val() != '') {
					orgId = $('#'+myId+'_region').val();
				} else if(i == 40 && $('#'+myId + '_area').val() != null
						&& $('#'+myId + '_area').val() != '') {
					orgId = $('#'+myId+'_area').val();
				} else if(i == 50 && $('#'+myId + '_district').val() != null
						&& $('#'+myId + '_district').val() != '') {
					orgId = $('#'+myId+'_district').val();
				} else if(i == 60 && $('#'+myId + '_building').val() != null
						&& $('#'+myId + '_building').val() != '') {
					orgId = $('#'+myId+'_building').val();
				} else if(i == 70 && $('#'+myId + '_school').val() != null
						&& $('#'+myId + '_school').val() != '') {
					orgId = $('#'+myId+'_school').val();
				}
			}
			//console.log('orgId:', orgId, $('#'+myId + '_state').val());
			return orgId;
		// value passed, act as a setter
		} else {
			//this.options.value = this._constrain( value );
			
			for(var i=20;i<80;i=i+10) {
				if(i == lvl) {
					$('#'+myId + '_state').val(value).trigger('change');
				} else if(i == lvl) {
					$('#'+myId+'_region').val(value).trigger('change');
				} else if(i == lvl) {
					$('#'+myId+'_area').val(value).trigger('change');
				} else if(i == lvl) {
					$('#'+myId+'_district').val(value).trigger('change');
				} else if(i == lvl) {
					$('#'+myId+'_building').val(value).trigger('change');
				} else if(i == lvl) {
					$('#'+myId+'_school').val(value).trigger('change');
				}
			}
		}
	},
	// create a public method
	getOrgType: function( lvl) {
			var myId = this.element[0].id;
			var orgTypeId = null;
			for(var i=20;i<80;i=i+10) {
				if(i == 20 && $('#'+myId + '_state').val() != null && $('#'+myId + '_state').val() != '') {
					orgTypeId = 2;
				} else if(i == 30 && $('#'+myId + '_region').val() != null && $('#'+myId + '_region').val() != '') {
					orgTypeId = 3;
				} else if(i == 40 && $('#'+myId + '_area').val() != null && $('#'+myId + '_area').val() != '') {
					orgTypeId = 4;
				} else if(i == 50 && $('#'+myId + '_district').val() != null && $('#'+myId + '_district').val() != '') {
					orgTypeId = 5;
				} else if(i == 60 && $('#'+myId + '_building').val() != null && $('#'+myId + '_building').val() != '') {
					orgTypeId = 6;
				} else if(i == 70 && $('#'+myId + '_school').val() != null && $('#'+myId + '_school').val() != '') {
					orgTypeId = 7;
				}
			}
			return orgTypeId;
	},
	reset: function() {
		var myId = this.element[0].id;
		//this._clearPreviousSelection(myId, 30);
		$('#'+myId + '_state').val([]).trigger('change');
		$('#'+myId + '_state').trigger('change.select2');
	},
	// Use the destroy method to clean up any modifications your widget has made to the DOM
    destroy: function() {
      // In jQuery UI 1.8, you must invoke the destroy method from the base widget
      this.element.empty();
      $.Widget.prototype.destroy.call( this );
      // In jQuery UI 1.9 and above, you would define _destroy instead of destroy and not call the base method
    }
});

function dynamicSort(property) {
    var sortOrder = 1;
    var reA = /[^a-zA-Z]/g;
    var reN = /[^0-9]/g;
    if(property[0] === "-") {
        sortOrder = -1;
        property = property.substr(1);
    }
    return function (a,b) {
    	var aA = (''+a[property]).replace(reA, "");
        var bA = (''+b[property]).replace(reA, "");
        if(aA.toLowerCase() === bA.toLowerCase()) {
            var aN = parseInt((''+a[property]).replace(reN, ""), 10);
            var bN = parseInt((''+b[property]).replace(reN, ""), 10);
            return aN === bN ? 0 : aN > bN ? 1 : -1;
        } else {
            return aA.toLowerCase() > bA.toLowerCase() ? 1 : -1;
        }
        return result * sortOrder;
    };
}

/* Multi-Select Filtering Starts */

function filteringOrganizationSet(This){	
	
	if(!('contains' in String.prototype)) {
	       String.prototype.contains = function(str, startIndex) {
	                return -1 !== String.prototype.indexOf.call(this, str, startIndex);
	       };
	}
	
	$.each(This.find('select'),function(){
		var viewOrganizationFilterId = ''; viewOrganizationFilterId = $(this).attr('id');
		var filterLoadedBeforeCheck = $('.ui-multiselect-menu .ui-multiselect-checkboxes').find("li:first-child").has('input[name $= '+viewOrganizationFilterId+']').parents('ul').siblings('div').hasClass('ui-multiselect-filter-menu');
		
		if(filterLoadedBeforeCheck != true){
			$('.ui-multiselect-menu .ui-multiselect-checkboxes').find("li:first-child").has('input[name $= '+viewOrganizationFilterId+']').parents('.ui-multiselect-menu').prepend($('<div class="ui-multiselect-filter-menu"> <input name='+viewOrganizationFilterId+' type="search" class="ui-multiselect-filter-menu-input" placeholder="Filter" /> </div>'));
			$('.ui-multiselect-filter-menu-input').off('input focus').on('input focus', function(){
  				  //$(this).parents('.ui-multiselect-menu').css({'left':'161px'});	
				  var inputs = $(this).val(); search = inputs.toLowerCase();

				  $.each($(this).parents('.ui-multiselect-filter-menu').siblings('ul').find('li'),function(index,value){
					  
					  $this = $(this); var $values = $(this).find('span').text(); lists = $values.toLowerCase();
					  if(lists.contains(search)){
					   	 $this.show();
					  }else{
					     $this.hide();
					  }
					  
					  if(lists.contains("select")){
						  $this.show();
					  }
					  
				  }); 
			});
			
		}
		
	});
	
}

$(document).on('click',function(event) { 
    if(!$(event.target).closest('.ui-multiselect-filter-menu-input').length) {
       inputsZ = $('.ui-multiselect-filter-menu-input').val(); 
       if( (inputsZ !='') || (inputsZ != null) ){
          $('.ui-multiselect-filter-menu-input').val('');
          setTimeout(function(){
            $('.ui-multiselect-filter-menu-input').trigger('input'); 
          }, 300);
        }
    }        
});

function filteringOrganization(This){	
	
	   if(!('contains' in String.prototype)) {
	       String.prototype.contains = function(str, startIndex) {
	                return -1 !== String.prototype.indexOf.call(this, str, startIndex);
	       };
	    }
	   	
		var viewOrganizationFilterId = ''; viewOrganizationFilterId = This.attr('id');				
		var filterLoadedBeforeCheck = $('.ui-multiselect-menu .ui-multiselect-checkboxes').find("li:first-child").has('input[name $= '+viewOrganizationFilterId+']').parents('ul').siblings('div').hasClass('ui-multiselect-filter-menu');
		
		if(filterLoadedBeforeCheck != true){
			$('.ui-multiselect-menu .ui-multiselect-checkboxes').find("li:first-child").has('input[name $= '+viewOrganizationFilterId+']').parents('.ui-multiselect-menu').prepend($('<div class="ui-multiselect-filter-menu"> <input name="viewOrganizationFilterId" id="'+viewOrganizationFilterId+'_filter" type="search" class="ui-multiselect-filter-menu-input" placeholder="Filter" /> </div>'));					
			$('.ui-multiselect-filter-menu-input').off('input').on('input', function(){
				//$(this).parents('.ui-multiselect-menu').css({'left':'161px'});
				var inputs = $(this).val(); search = inputs.toLowerCase();
				  
				$.each($(this).parents('.ui-multiselect-filter-menu').siblings('ul').find('li'),function(index,value){
					  
					  $this = $(this); var $values = $(this).find('span').text(); lists = $values.toLowerCase();
					  if(lists.contains(search)){
					   	 $this.show();
					  }else{
					     $this.hide();
					  }
					  
					  if(lists.contains("select")){
						  $this.show();
					  }
				}); 
			});
			
		}
		
}

function strictFilteringOrganizationsGroups(This){	
	
	if(!('contains' in String.prototype)) {
	       String.prototype.contains = function(str, startIndex) {
	                return -1 !== String.prototype.indexOf.call(this, str, startIndex);
	       };
	}
	
	$.each(This.find('select'),function(){
		var viewOrganizationFilterId = ''; viewOrganizationFilterId = $(this).attr('id');
		
		var filterLoadedBeforeCheck = $('.ui-multiselect-menu .ui-multiselect-checkboxes').find("li:first-child").has('input[name == '+viewOrganizationFilterId+']').parents('ul').siblings('div').hasClass('ui-multiselect-filter-menu');

		if(filterLoadedBeforeCheck != true){
			$('.ui-multiselect-menu .ui-multiselect-checkboxes').find("li:first-child").has('input[name $= '+viewOrganizationFilterId+']').parents('.ui-multiselect-menu').prepend($('<div class="ui-multiselect-filter-menu"> <input name='+viewOrganizationFilterId+' type="search" class="ui-multiselect-filter-menu-input" placeholder="Filter" /> </div>'));
			$('.ui-multiselect-filter-menu-input').off('input focus').on('input focus', function(){
				  var inputs = $(this).val(); search = inputs.toLowerCase();

				  $.each($(this).parents('.ui-multiselect-filter-menu').siblings('ul').find('li'),function(index,value){
					  
					  $this = $(this); var $values = $(this).find('span').text(); lists = $values.toLowerCase();
					  if(lists.contains(search)){
					   	 $this.show();
					  }else{
					     $this.hide();
					  }
					  
					  if(lists.contains("select")){
						  $this.show();
					  }
					  
				  }); 
			});
			
		}
		
	});
	
}

/* Multi-Select Filtering Ends */