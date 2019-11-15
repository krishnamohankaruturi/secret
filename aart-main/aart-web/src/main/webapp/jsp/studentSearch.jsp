<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>


<style>
    #student_search_form {
        margin-bottom: 40px;
    }
    #student_search_form .ui-widget{
        font-size: .9em
    }
    #searchComboBoxes div {
        margin-bottom: 10px;
    }
    
    #resultsSection {
        margin-top: 20px;
    }
    #student_search_button {
        margin-left: 150px;
    }
    
    div.messages span {
        display: none;
    }
    #orgTree {
        border: 1px solid #a6c9e2;
        border-radius: 5px;
        -webkit-border-radius:5px;
        -moz-border-radius: 5px;
        margin: 5px;
        max-height: 200px;
        overflow-y: scroll;
        min-height: 200px;
    }
    
    #student_search_form {
        margin: 4px;
    }
    
    #gradeLevelDiv {
        float: left;
    }
    
    #gradeLevelDiv label {
        margin-left: 10px;
    }
    
    #searchComboBoxes hr.clear {
        visibility: hidden;
    }
</style>
<div>
    <div id="student_search_form">
    <security:authorize access="!hasRole('PERM_ORG_VIEW')">
        <span class="ui-state-error"><fmt:message key="error.common.permissiondenied.organization"/></span>
    </security:authorize>
    <security:authorize access="!hasRole('PERM_ROSTERRECORD_SEARCH')">
       <span class="ui-state-error"><fmt:message key="error.common.permissiondenied.roster.search"/></span>
    </security:authorize>
    <security:authorize access="!hasRole('PERM_ROSTERRECORD_VIEW')">
       <span class="ui-state-error"><fmt:message key="error.common.permissiondenied.roster.view"/></span>
    </security:authorize>
    <security:authorize access="!hasRole('PERM_STUDENTRECORD_VIEW')">
       <span class="ui-state-error"><fmt:message key="error.common.permissiondenied.student.view"/></span>
    </security:authorize>
    <h4><fmt:message key="label.search.selectorg"/></h4>
        <div class="messages">
            <span id="required_criteria_msg" class="ui-state-error"><fmt:message key="error.search.requiredcriteria"/></span>
            <span id="no_grades_for_org" class="ui-state-error"><fmt:message key="error.search.nogradesfororg"/></span>
            <span id="perm_denied_studentsearch" class="ui-state-error"><fmt:message key="error.common.permissiondenied.studentsearch"/></span>
        </div>
        <div id="searchComboBoxes">
            <security:authorize access="hasRole('PERM_ORG_VIEW')">
                <div id="orgTree"></div>
            </security:authorize>
	        <security:authorize access="hasRole('PERM_ROSTERRECORD_SEARCH')">
	           <input type="button" id="student_search_button" class="btn_blue" value="<fmt:message key='label.common.search'/>"/>
	        </security:authorize>
	        
	        <hr class="clear"/>
        </div>
        
    </div>
    <div id="rostersResults">
        <table id="rosters_table"></table>
        <div id="rosters_pager" class="kite-table"></div>
    </div>
    <div id="resultsSection">
        <table id="students_grid"></table>
        <div id="students_pager" class="kite-table"></div>
    </div>
</div>

<script type="text/javascript">
var search_page = search_page || {};
search_page.selectedOrg = 0;

$(function() {
	//make ajax call to get the grid.
	<security:authorize access="hasRole('PERM_ORG_VIEW')">
	buildOrgTree();
	</security:authorize>
	
	<security:authorize access="hasRole('PERM_ROSTERRECORD_SEARCH')">
	$('#student_search_button').button({
		icons: {
			primary: ".ui-icon-search"
		}
	});
	</security:authorize>
	
	<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
	   buildStudentTable();
	</security:authorize>

	<security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW')">
	   buildRosterTable();
	</security:authorize>

	<security:authorize access="hasRole('PERM_ROSTERRECORD_SEARCH')">
	$('#student_search_button').click(function() {
		$('#required_criteria_msg').hide();
		
		//Clear the students_grid before each search.
		$('#students_grid').jqGrid("clearGridData", true);

		if (search_page.selectedOrg !== undefined && search_page.selectedOrg !== null 
				&& !isNaN(search_page.selectedOrg) && Number(search_page.selectedOrg) > 0) {

			$.ajax({
		        url: 'getRosters.htm',
		        data: {
		        	selectedOrganizationId: search_page.selectedOrg,
		        },
		        type: 'POST',
		        dataType: 'json',
		        success: function(data) {
		            if (data !== undefined && data !== null) {
			            var rosters = [];
			            for (var i = 0, length = data.length; i < length; i++) {
			            	rosters[i] = {
			            		rosterId: data[i].roster.id,
			            		teacherName: data[i].roster.teacher.surName + ", " + data[i].roster.teacher.firstName,
			            		subject: data[i].roster.courseSectionName,
			            		numStudents: data[i].numStudents
			            	};
			            }
			            setDataForRostersTable(rosters);
		            }
		        }
		    });
		} else {
			$('#required_criteria_msg').show();
		}
	});
	</security:authorize>
});
<security:authorize access="hasRole('PERM_ORG_VIEW')">
function buildOrgTree() {
	$.ajax({
		url: 'getUsersOrgs.htm',
		data: {},
		type: 'POST',
		dataType: 'json',
		success: function(data) {
			$('#orgTree').jstree({
		        "json_data" :{
		            "data": data
		        },
		        "ui" :{
		            "select_limit": 1
		        },
		        "themes": {
		            "theme": "apple",
		            "dots": false,
		            "icons": false
		        },
		        "plugins": ["themes", "json_data", "ui"]
		    }).bind("select_node.jstree", function(event, data){
		    	search_page.selectedOrg = data.rslt.obj.data("id");
		    });
		}
	});
}
</security:authorize>

function buildStudentTable(studentData) {
	var grid_width = $('#students_grid').parent().width();
	var cell_width = grid_width/6; 
	
	$('#students_grid').jqGrid({
		datatype: 'local',
		colNames: [
		           'studentId',
		           '<fmt:message key="label.search.studentlocalid"/>',
		           '<fmt:message key="label.search.firstname"/>',
		           '<fmt:message key="label.search.lastname"/>',
		           '<fmt:message key="label.search.gender"/>',
		           '<fmt:message key="label.search.birthdate"/>',
		           '<fmt:message key="label.search.enrolledgrade"/>'
		           ],
		colModel: [
		           {name: 'studentId', index: 'studentId', hidden: true},
		           {name: 'studentLocalId', index: 'studentLocalId', width: cell_width},
		           {name: 'firstName', index: 'firstName', width: cell_width},
		           {name: 'lastName', index: 'lastName', width: cell_width},
		           {name: 'gender', index: 'gender', width: cell_width},
		           {name: 'dateOfBirth', index: 'dateOfBirth', width: cell_width},
		           {name: 'enrolledGrade', index: 'enrolledGrade', width: cell_width}
		           ],
		viewsortcols : [ false, 'vertical', true ],
        height : 'auto',
        shrinkToFit: false,
        width: grid_width,
        pager : '#students_pager',
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : '',
        sortorder : 'asc',
        viewrecords : true,
        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
        altrows : true,
        hoverrows : true,
        multiselect : false,
        altclass: 'altrow',
        caption: '<fmt:message key="label.search.students"/>'
	});
	
	if (studentData !== undefined && studentData !== null) {
        setDataForStudentTable(studentData);
    }
}

function setDataForStudentTable(students) {
	$('#students_grid').jqGrid("clearGridData", true);
	$('#students_grid').jqGrid("setGridParam", {data: students});
    $('#students_grid').trigger("reloadGrid");	
}

function buildRosterTable(rosters) {
	var grid_width = $('#rosters_table').parent().width();
	var cell_width = grid_width/3;

	$('#rosters_table').jqGrid({
        datatype: 'local',
        colNames: [
                   'rosterId',
                   '<fmt:message key="label.search.teacher"/>',
                   '<fmt:message key="label.search.subject"/>',
                   '<fmt:message key="label.search.numstudents"/>'
                   ],
        colModel: [
                   {name: 'rosterId', index: 'rosterId', hidden: true},
                   {name: 'teacherName', index: 'teacherName', width: cell_width},
                   {name: 'subject', index: 'subject', width: cell_width},
                   {name: 'numStudents', index: 'numStudents', width: cell_width}
                   ],
        viewsortcols : [ false, 'vertical', true ],
        height : 'auto',
        shrinkToFit: false,
        width: grid_width,
        pager : '#rosters_pager',
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : '',
        sortorder : 'asc',
        viewrecords : true,
        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
        altrows : true,
        hoverrows : true,
        multiselect : false,
        altclass: 'altrow',
        caption: '<fmt:message key="label.search.rosters"/>',
        onSelectRow: function(rowid, status){
        	<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
        	var students = getStudentsForRoster($(this).jqGrid("getCell", rowid, 'rosterId'));        	
        	students.success(function(data) {
        		if (data !== undefined && data !== null && data.length > 0) {
        			var students = [];
        			var date;
        			var formattedDate;
        			for (var i = 0, length = data.length; i < length; i++) {
        				if(data[i].student.dateOfBirth != null) {
        					date = new Date(data[i].student.dateOfBirth);
        					formattedDate = $.datepicker.formatDate("mm/dd/yy", date);
        				} else {
        					formattedDate = null;
        				}
        				students[i] = {
        					studentId: data[i].student.studentId,
        					studentLocalId: data[i].studentLocalId,
        					firstName: data[i].student.legalFirstName,
        					lastName: data[i].student.legalLastName,
        					gender: data[i].student.gender,
        					dateOfBirth: formattedDate,
        					enrolledGrade: data[i].enrolledGrade
        				};
        			}
        			setDataForStudentTable(students);
        		}
        	});
        	</security:authorize>
        }
    });
	
	if (rosters !== undefined && rosters !== null) {
		setDataForRostersTable(rosters);
	}
}

function setDataForRostersTable(rosters) {
	$('#rosters_table').jqGrid("clearGridData", true);
	$('#rosters_table').jqGrid("setGridParam", {data: rosters});
	$('#rosters_table').trigger("reloadGrid");
}
<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
function getStudentsForRoster(rosterId) {
	if (rosterId !== undefined && rosterId !== null && !isNaN(rosterId) && Number(rosterId) > 0) {
		var params = {
			rosterId: rosterId
		};
		
		return $.ajax({
			url: 'getStudents.htm',
			data: params,
			type: 'POST',
			dataType: 'json'
		});
	}
}
</security:authorize>
</script>

