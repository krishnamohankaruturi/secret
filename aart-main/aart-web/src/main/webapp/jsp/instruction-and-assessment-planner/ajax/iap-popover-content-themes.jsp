<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div id="themes-content">
	<div>
		<span class="bold">
			THEMES
		</span>
	</div>
	<br/>
	<div>
		<p>
			Some English Language Arts texts contain themes that may be sensitive for certain students.
			Choose the themes that are <span class="bold">not acceptable</span> for this student.
			These selections do not mean that you are choosing this content for specific testlets,
			only that testlets with these themes are <span class="bold">not acceptable</span> for this student.
			Theme selection for a student can be edited at any time and will impact subsequent testlet assignments but not existing testlet assignments.
		</p>
	</div>
	<select id="themes" multiple="true">
		<c:forEach items="${sensitivityTags}" var="tag">
			<c:choose>
				<c:when test="${tag.selectedForStudent}">
					<option value="${tag.id}" selected="selected">${tag.name}</option>
				</c:when>
				<c:otherwise>
					<option value="${tag.id}">${tag.name}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<br/>
	<br/>
	<div class="themes-buttons">
		<button id="themes-save" class="themes-button">
			Save
		</button>
		<button id="themes-cancel" class="themes-button">
			Cancel
		</button>
	</div>
</div>

<script type="text/javascript">
$(function(){
	$('#themes').select2({
		placeholder: 'Select',
		multiple: true,
		closeOnSelect: false,
		width: '95%'
	});
	
	$('#themes-save').on('click', function(){
		var $this = $(this);
		var data = {
			studentId: $('#studentId').val(),
			contentAreaId: $('#contentAreaId').val(),
			tagIds: $('#themes').val() || []
		};
		console.log(JSON.stringify(data));
		$.ajax({
			url: 'saveSensitiveThemes.htm',
			method: 'POST',
			data: data
		}).done(function(response){
			if (response.status === 'success'){
				$this.closest('.popover').popover('hide');
			}
		});
	});
	
	$('#themes-cancel').on('click', function(){
		$(this).closest('.popover').popover('hide');
	}).on('keydown', function(e){
		var keyCode = e.which;
		// 13 is Enter, 32 is Space, these should trigger for keyboard navigation
		if (keyCode === 13 || keyCode === 32){
			$(this).click();
		} else if (keyCode === 27) {
			// they hit escape
			$(this).closest('.popover').popover('hide');
		}
	});
});
</script>