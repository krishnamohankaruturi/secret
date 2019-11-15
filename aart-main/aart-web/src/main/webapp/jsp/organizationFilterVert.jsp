<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="config orgFilterDivVert" >
	<div class="tabTable">
		<div class="orgFilterStateDiv row">
			<div class="paddedcell" ><label for="orgFilterStateId"><fmt:message key="label.state"/></label></div>	
		</div>
		<div class="orgFilterStateDiv row">	
			<div class="selectcell">		
				<select id="orgFilterStateId">
					<option value="0">
						<fmt:message key="label.common.select"/>
					</option>
				</select>
			</div>
		</div>
		
		<div class="orgFilterRegionDiv row">
			<div class="paddedcell"><label for="orgFilterRegionId"><fmt:message key="label.region"/></label></div>
		</div>
		<div class="orgFilterRegionDiv row">
			<div class="selectcell">	
				<select id="orgFilterRegionId">
					<option value="0">
						<fmt:message key="label.common.select"/>
				    </option>
			    </select>
			</div>
		</div>
		
		<div class="orgFilterAreaDiv row">
			<div class="paddedcell"><label for="orgFilterAreaId"><fmt:message key="label.area"/></label></div>
		</div>
		<div class="orgFilterAreaDiv row">
			<div class="selectcell">
			<select id="orgFilterAreaId">
				<option value="0">
					<fmt:message key="label.common.select"/>
				</option>
			</select>
			</div>
		</div>
		
		<div class="orgFilterDistrictDiv row">
			<div class="paddedcell"><label for="orgFilterDistrictId"><fmt:message key="label.district"/></label></div>
		</div>
		<div class="orgFilterDistrictDiv row">		
			<div class="selectcell">
				<select id="orgFilterDistrictId">
					<option value="0">
						<fmt:message key="label.common.select"/>
					</option>
				</select>
			</div>
		</div>
		
		<div class="orgFilterBuildingDiv row">
			<div class="paddedcell"><label for="orgFilterBuildingId"><fmt:message key="label.building"/></label></div>
		</div>
		<div class="orgFilterBuildingDiv row">
			<div class="selectcell">
			<select id="orgFilterBuildingId">
				<option value="0">
					<fmt:message key="label.common.select"/>
				</option>
			</select>
			</div>
		</div>
		
		<div class="orgFilterSchoolDiv row">
			<div class="paddedcell"><label for="orgFilterSchoolId"><fmt:message key="label.school"/></label></div>
		</div>
		<div class="orgFilterSchoolDiv row">
			<div class="selectcell">
			<select id="orgFilterSchoolId">
				<option value="0">
					<fmt:message key="label.common.select"/>
				</option>
			</select>
			</div>
		</div>
	</div>
</div>

<script src="js/organizationFilter.js"> </script>