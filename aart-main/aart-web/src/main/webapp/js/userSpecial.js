
 var selectedorgId = null;
 var selectedDistrictName = '';
 var selectedSchoolName = '';
 var selectedSchoolId = null;
 var selectedDistrictId = null;
 var selectedUserArrForMove = [];

function special_Init_User_tab() {
	debugger;
	$('#specialUserOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});
	
	if(globalUserLevel <= 50){
		$('#specialUserOrgFilter').orgFilter('option','requiredLevels',[50]);
	}
	
	$('#specialUserOrgFilterForm').validate({ignore: ""});
	
	filteringOrganizationSet($('#specialUserOrgFilterForm'));
	
   	
	jQuery.validator.setDefaults({
    		submitHandler: function() {		
    		},
    		errorPlacement: function(error, element) {
    			if(element.hasClass('required') || element.attr('type') == 'file') {
    				error.insertAfter(element.next());
    			}
    			else {
    	    		error.insertAfter(element);
    			}
    	    }
    	});
		
		buildSpecialOrgGrid();


	$('#moveUsersOrganizationNewDistrict, #moveUsersOrganizationNewSchool').select2({
		placeholder:'Select',
		multiple: false
	});
	
	
	$('#specialUserButton').on("click",function(event) {
		debugger;
		event.preventDefault();
		//moveEnableDisableBtn(0);
		selectedUserArrForMove = [];
		if($('#specialUserOrgFilterForm').valid()) {
			var $gridAuto = $("#specialUserGridTableId");
			$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getUsersByOrganization.htm?q=1',
				search: false, 
				postData: { "filters": "",
					         "requestFor":"special",}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});
	
	

	function buildSpecialOrgGrid() {
		
		var $gridAuto = $("#specialUserGridTableId");
		
		//Unload the grid before each request.
		$("#specialUserGridTableId").jqGrid('clearGridData');
		$("#specialUserGridTableId").jqGrid("GridUnload");
		
		var gridWidthForVO = $gridAuto.parent().width();		
		if(gridWidthForVO < 760) {
			gridWidthForVO = 760;				
		}
		var cellWidthForVO = gridWidthForVO/5;
		
		

		var cmforViewUserGrid = [
			{ name : 'id',label:'moveUser_id', index : 'id', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
			{ name : 'statusCode', index : 'statusCode', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},
			
			//Changed during  US16247 
			//{ name : 'statusCode',label:'moveUser_statusCode', index : 'statusCode', width : cellWidthForVO, search : true , stype : 'select', searchoptions: { value : ':All;Active:Active;Inactive:Inactive;Pending:Pending', sopt:['eq'] }, hidden : false, hidedlg : false},
			{ name : 'uniqueCommonIdentifier',label:'moveUser_uniqueCommonIdentifier', index : 'uniqueCommonIdentifier', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
				formatter: escapeHtml
			},
			{ name : 'firstName',label:'moveUser_firstName', index : 'firstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'surName',label:'moveUser_surName', index : 'surName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},				
			{ name : 'email',label:'moveUser_email', index : 'email', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'programNames',label:'moveUser_programNames', index : 'programNames', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
		];
		
	
		//JQGRID
		$gridAuto.scb({
			mtype: "POST",
			datatype : "local",
			width: gridWidthForVO,
			colNames: ["User Id",       // Changed During US16242
			           "Status",
					   "Educator Identifier",
					   "First Name",
					   "Last Name",					   
					   "Email",
					   "Assessment Programs"],
		  	colModel :cmforViewUserGrid,
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			pager : '#specialUserGridPager',
			viewrecords : true,
			multiselect: true,
			page: 1,
			// F-820 Grids default sort order
		    sortname: 'surName,firstName',
		    sortorder: 'asc',
			loadonce: false,
			viewable: false,
			beforeRequest: function()
			{
				   if($('#specialUserOrgFilter').orgFilter('value') != null)  {
			        	var orgs = new Array();
			        	orgs.push($('#specialUserOrgFilter').orgFilter('value'));
			        	$(this).setGridParam({postData: {
			        		'orgChildrenIds': function() {return orgs;}}
			        	});
			}
			}
		});
		
		$gridAuto.jqGrid('setGridParam',{
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
		
		// Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
			
		
	}
}