<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<style>
	.nav-pills *:focus {
		outline:none;
	}
	
	.dropdown-menu {
		box-shadow: 3px 3px 2px grey;
	}
	
	.settings-nav li {
		margin: 0 1px;
		/*text-transform: uppercase;*/
		font-weight: bold;
		border: 1px solid lightgrey;
		border-radius: .25rem;
	}
	
	.nav-pills a {
		font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		font-size: 10pt;
	}
	
	.nav-pills.green .nav-item .nav-link {
		background-color: #769846;
		color: white;
	}
	
	.nav-pills.green .nav-item a.active,
	.nav-pills.green .nav-item > a:hover,
	.nav-pills.green .nav-item a:active,
	.nav-pills.green .nav-item.show > a {
		background-color: #cbec8b;
		color: black;
	}
</style>

<div class="config _bcg">
	<ul class="nav nav-pills settings-nav green" id="settingsNav">
		<security:authorize access="hasAnyRole('PERM_BATCH_REGISTER', 'PERM_BATCH_REPORT', 'UPLOAD_WEBSERVICE')">
			<li class="nav-item">
				<a class="nav-link" href="#pills-home" data-toggle="pill" role="tab">Batch Processes</a>
			</li>
		</security:authorize>
		<li class="nav-item">
			<a class="nav-link" href="#pills-messages" data-toggle="pill" role="tab">Create User Messages</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#pills-fcs" data-toggle="pill" role="tab">First Contact Survey</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#pills-general" data-toggle="pill" role="tab">General</a>
		</li>
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">Organization</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#pills-create-organization" data-toggle="pill" role="tab">Create Organization</a>
				<a class="dropdown-item" href="#pills-upload-organization" data-toggle="pill" role="tab">Upload Organization</a>
				<a class="dropdown-item" href="#pills-view-organization" data-toggle="pill" role="tab">View Organization</a>
			</div>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#pills-quality-control" data-toggle="pill" role="tab">Quality Control</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#pills-reports-setup" data-toggle="pill" role="tab">Reports Setup</a>
		</li>
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#pills-roles" data-toggle="dropdown" role="tab">Roles</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#pills-edit-roles" data-toggle="pill" role="tab">Edit Roles</a>
				<a class="dropdown-item" href="#pills-view-roles" data-toggle="pill" role="tab">View Roles</a>
			</div>
		</li>
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">Rosters</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#pills-create-roster" data-toggle="pill" role="tab">Create Roster</a>
				<a class="dropdown-item" href="#pills-upload-roster" data-toggle="pill" role="tab">Upload Roster</a>
				<a class="dropdown-item" href="#pills-view-roster" data-toggle="pill" role="tab">View Roster</a>
			</div>
		</li>
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">Students</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#pills-add-student" data-toggle="pill" role="tab">Add Student</a>
				<a class="dropdown-item" href="#pills-exit-student" data-toggle="pill" role="tab">Exit Student</a>
				<a class="dropdown-item" href="#pills-find-student" data-toggle="pill" role="tab">Find Student</a>
				<a class="dropdown-item" href="#pills-transfer-students" data-toggle="pill" role="tab">Transfer Students</a>
				<a class="dropdown-item" href="#pills-upload-enrollment" data-toggle="pill" role="tab">Upload Enrollment</a>
				<a class="dropdown-item" href="#pills-upload-tec" data-toggle="pill" role="tab">Upload TEC</a>
				<a class="dropdown-item" href="#pills-view-students" data-toggle="pill" role="tab">View Students</a>
			</div>
		</li>
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">Test Records</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#pills-clear-test-record" data-toggle="pill" role="tab">Clear Test Record</a>
				<a class="dropdown-item" href="#pills-create-test-record" data-toggle="pill" role="tab">Create Test Record</a>
				<a class="dropdown-item" href="#pills-upload-tec" data-toggle="pill" role="tab">Upload TEC</a>
				<a class="dropdown-item" href="#pills-view-test-record" data-toggle="pill" role="tab">View Test Record</a>
			</div>
		</li>
		
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">Users</a>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="#pills-add-user" data-toggle="pill" role="tab">Add User</a>
				<a class="dropdown-item" href="#pills-claim-users" data-toggle="pill" role="tab">Claim Users</a>
				<a class="dropdown-item" href="#pills-merge-users" data-toggle="pill" role="tab">Merge Users</a>
				<a class="dropdown-item" href="#pills-move-users" data-toggle="pill" role="tab">Move Users</a>
				<a class="dropdown-item" href="#pills-upload-pd-results" data-toggle="pill" role="tab">Upload PD Training Results</a>
				<a class="dropdown-item" href="#pills-upload-users" data-toggle="pill" role="tab">Upload Users</a>
				<a class="dropdown-item" href="#pills-view-users" data-toggle="pill" role="tab">View Users</a>
			</div>
		</li>
	</ul>
</div>
<div id="settingsNavContent" class="tab-content">
	<div id="pills-home" class="tab-pane" role="tabpanel">Batch Processes</div>
	<div id="pills-messages" class="tab-pane" role="tabpanel">Create User Messages</div>
	<div id="pills-fcs" class="tab-pane" role="tabpanel">First Contact Survey</div>
	<div id="pills-general" class="tab-pane" role="tabpanel">General</div>
	<div id="pills-create-organization" class="tab-pane" role="tabpanel">Create Organization</div>
	<div id="pills-upload-organization" class="tab-pane" role="tabpanel">Upload Organization</div>
	<div id="pills-view-organization" class="tab-pane" role="tabpanel">View Organization</div>
	<div id="pills-quality-control" class="tab-pane" role="tabpanel">Quality Control</div>
	<div id="pills-reports-setup" class="tab-pane" role="tabpanel">Reports Setup</div>
	<div id="pills-edit-roles" class="tab-pane" role="tabpanel">Edit Roles</div>
	<div id="pills-view-roles" class="tab-pane" role="tabpanel">View Roles</div>
	<div id="pills-create-roster" class="tab-pane" role="tabpanel">Create Roster</div>
	<div id="pills-upload-roster" class="tab-pane" role="tabpanel">Upload Roster</div>
	<div id="pills-view-roster" class="tab-pane" role="tabpanel">View Roster</div>
	<div id="pills-add-student" class="tab-pane" role="tabpanel">Add Student</div>
	<div id="pills-exit-student" class="tab-pane" role="tabpanel">Exit Student</div>
	<div id="pills-find-student" class="tab-pane" role="tabpanel">Find Student</div>
	<div id="pills-transfer-students" class="tab-pane" role="tabpanel">Transfer Students</div>
	<div id="pills-upload-enrollment" class="tab-pane" role="tabpanel">Upload Enrollment</div>
	<div id="pills-upload-tec" class="tab-pane" role="tabpanel">Upload TEC</div>
	<div id="pills-view-students" class="tab-pane" role="tabpanel">View Students</div>
	<div id="pills-clear-test-record" class="tab-pane" role="tabpanel">Clear Test Record</div>
	<div id="pills-create-test-record" class="tab-pane" role="tabpanel">Create Test Record</div>
	<div id="pills-upload-tec" class="tab-pane" role="tabpanel">Upload TEC</div>
	<div id="pills-view-test-record" class="tab-pane" role="tabpanel">View Test Record</div>
	<div id="pills-add-user" class="tab-pane" role="tabpanel">Add User</div>
	<div id="pills-claim-users" class="tab-pane" role="tabpanel">Claim Users</div>
	<div id="pills-merge-users" class="tab-pane" role="tabpanel">Merge Users</div>
	<div id="pills-move-users" class="tab-pane" role="tabpanel">Move Users</div>
	<div id="pills-upload-pd-results" class="tab-pane" role="tabpanel">Upload PD Training Results</div>
	<div id="pills-upload-users" class="tab-pane" role="tabpanel">Upload Users</div>
	<div id="pills-view-users" class="tab-pane" role="tabpanel">View Users</div>
</div>

<script>
$(document).ready(function(){
	$('#settingsNav .dropdown').on('show.bs.dropdown', function() {
		$(this).find('.dropdown-menu').first().stop(true, true).slideDown(50);
	});
	$('#settingsNav .dropdown').on('hide.bs.dropdown', function() {
		$(this).find('.dropdown-menu').first().stop(true, true).slideUp(50);
	});
});
</script>