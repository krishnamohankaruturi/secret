<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class='orgFilterDiv'>

	<div class="orgFilterLeftCol">
		<div class='orgFilterStateDiv'>
			<label for='orgFilterStateId'><fmt:message key='label.state'/></label>
			<br>
		</div>
		<div class='orgFilterRegionDiv'>
			<label for='orgFilterRegionId'><fmt:message key='label.region'/></label>
			<br>
		</div>
		<div class='orgFilterAreaDiv'>
			<label for='orgFilterAreaId'><fmt:message key='label.area'/></label>
			<br>
		</div>
		<div class='orgFilterDistrictDiv'>
			<label for='orgFilterDistrictId'><fmt:message key='label.district'/></label>
			<br>
		</div>
		<div class='orgFilterBuildingDiv'>
			<label for='orgFilterBuildingId'><fmt:message key='label.building'/></label>
			<br>
		</div>
		<div class='orgFilterSchoolDiv'>
			<label for='orgFilterSchoolId'><fmt:message key='label.school'/></label>
			<br>
		</div>
	</div>
	
	<div class="orgFilterRightCol">
		<div class='orgFilterStateDiv'>		
			<select id='orgFilterStateId'>
				<option value="0">
					<fmt:message key='label.common.select'/>
				</option>
			</select>
			<br>
		</div>
	
		<div class='orgFilterRegionDiv'>		
			<select id='orgFilterRegionId'>
				<option value="0">
					<fmt:message key='label.common.select'/>
				</option>
			</select>
			<br>
		</div>

		<div class='orgFilterAreaDiv'>
			<select id='orgFilterAreaId'>
				<option value="0">
					<fmt:message key='label.common.select'/>
				</option>
			</select>
			<br>			
		</div>
		
		<div class='orgFilterDistrictDiv'>		
			<select id='orgFilterDistrictId'>
				<option value="0">
					<fmt:message key='label.common.select'/>
				</option>
			</select>
			<br>
		</div>
		
		<div class='orgFilterBuildingDiv'>
			<select id='orgFilterBuildingId'>
				<option value="0">
					<fmt:message key='label.common.select'/>
				</option>
			</select>
			<br>
		</div>
		
		<div class='orgFilterSchoolDiv'>
			<select id='orgFilterSchoolId'>
				<option value="0">
					<fmt:message key='label.common.select'/>
				</option>
			</select>
			<br>
		</div>
	</div>
</div>

<script src="js/organizationFilter.js"> </script>