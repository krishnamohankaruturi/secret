<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userFind.js'/>"> </script>


<div id="sentEmailSucessMsg" style="text-align: left; color: green"></div>
<div id="sentEmailErrorMsg" style="text-align: left; color: green"></div>
<div style="padding-left:15px;">
<security:authorize access="hasAnyRole('PERM_USER_CLAIM')">
<table role='presentation'>
	<tr>
		<td>
			<div class="form-fields">
			    <label class="field-label" for="fName">First Name:</label>
			    <div id="fNameValid">
			    <input type="text" id="fName" style="width: 70%;" required/>
			    </div>
		    </div>
		 </td>
		 <td>
		    <div class="form-fields">
			    <label class="field-label" for="lName">Last Name:</label> 
			     <div id="lNameValid">
			     <input type="text" id="lName" style="width: 70%;" required/>
			     </div>
		    </div>
		 </td>
		 <td>
		    <div class="form-fields">
			    <label class="field-label" for="educatorId">Educator Identifier:</label>
			    <div id="eIdentifierValid">
				    <input type="text" id="educatorId" style="width: 70%;"/>
			    </div>
		    </div>
		 </td>
		 <td>
			<a class="panel_btn" id="findUserButton">Search</a>
		 </td>
	 </tr>
</table>
</security:authorize>
</div>

<div>
	<label class="hidden error" id="messageFindUSers"></label>
	<div id="findUserGridCell" style="margin-top:2%;">
		<div id="findUser" hidden="hidden" class="hidden"></div>
		<div id="findUserGridContainer" class="kite-table" style="margin-left: 7px;">
			<table class="responsive" id="findUserGridTableId" role='presentation'></table>
			<div id="findUserGridPager" style="width: auto;"></div>
		</div>
	</div>
</div>

<div id="activeUsers" style="display:none;">
<div><label class="fluid" id="educatorFName"></label>
<label class="fluid" id="educatorLName"></label></div>
This user is currently active under the following district:
<div id="districtNames"></div>
Send a request to inactive this user to the above district.
</div>

<div id="inActiveUsers" style="display:none;">
Are you sure you want to claim this user within your organization?
</div>

<div id="errorMsgDailog" style="display:none;">
Enter First Name and Last Name or Educator Identifier
</div>

<script type="text/javascript">
var find_User_Select_Option_Loadonce = false;
</script>