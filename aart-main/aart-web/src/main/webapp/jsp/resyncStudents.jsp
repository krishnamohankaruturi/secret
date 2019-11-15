<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<security:authorize access="hasRole('CETE_SYS_ADMIN')">
<style>
.studentRow {
    display: table;
}

#unsyncedStudents {
    height: 400px;
    overflow: auto;
    border: 1px solid black;
}

</style>
<div>
    <h4>Un-Synced Students:</h4>
    <div class="messages">
        <span class="ui-state-error hidden" id="selectAStudent">
        	Please select at least one student to re-sync.
        </span>
        <span class="ui-state-error hidden" id="syncFailed">
        	Re-sync failed to complete successfully. Please try again.
        </span>
    </div>

        <input id="limit" type="text" value="1000" />
           <input id="selectAllCheckBoxId" type="checkbox" class="checkAll" onclick="checkAll()"/> <b>Check All</b>
    <div id="unsyncedStudents">
	    <c:forEach items="${unSyncedStudents}" var="student" varStatus="index">
	        <span class="studentRow">
	            <input type="checkbox" class="unsynced" value="${student.identifier}" />
	            <label>${student.stateStudentIdentifier }: ${student.legalLastName }, ${student.legalFirstName }</label>
	        </span>
	    </c:forEach>
    </div>

    <input type="button" id="resyncStudents" value="Re-Sync"/>
</div>

<script>
$(function(){
    //add a click handler for the function
    $("#resyncStudents").click(function() {
    	$('#selectAStudent').hide();
    	$('#syncFailed').hide();

        var students = [];
        var counter = 0;
        var stuToResync = $('.unsynced:checked');
        var limit = $('#limit').val();

        if (stuToResync.length) {
            $('.unsynced:checked').each(function() {
                students[counter] = $(this).val();
                counter++;
            });

            var params = {
                students: students,
                limit: limit,
                length: counter
            };

            $.ajax({
                url: 'syncStudents.htm',
                data: params,
                dataType: 'json',
                type: 'POST',
                success: function(data, textStatus, jqXHR) {
                    if (data.synced && data !== null && data !== undefined) {
                    	//
                    	var htmlString = "";
                    	for (var i = 0, length = data.students.length; i < length; i++) {
                    		htmlString += "<span class='studentRow'><input type='checkbox' class='unsynced' value='" + data.students[i].id +"' />";
                    		htmlString += "<label>" + data.students[i].stateStudentIdentifier + ": " + data.students[i].legalLastName + ", " + data.students[i].legalFirstName + "</label>";
                    		htmlString += "</span>";
                    	}
                    	$('#unsyncedStudents').html(htmlString);
                    } else {
                    	$('#syncFailed').show();
                    }
                }
            });
        } else {
            // the user needs to select at least one student to resync.
            $('#selectAStudent').show();
        }
    });
});
function checkAll() {
	if($('#selectAllCheckBoxId').attr("checked")) {
		$('input:checkbox').attr('checked','checked');
	} else {
		$('input:checkbox').removeAttr('checked');
	}
} 
</script>
</security:authorize>
<security:authorize access="!hasRole('CETE_SYS_ADMIN')">
    <span class="error"><fmt:message key="error.permissionDenied"/></span>
</security:authorize>