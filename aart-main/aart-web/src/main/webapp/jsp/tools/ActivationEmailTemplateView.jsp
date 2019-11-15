<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>

.emailbuttons{
	padding:30px 0px 0px 160px;
}
 
 .editdefaultemail{
	height:60px;
	width:325px;
	float:left;
	font-size:13.3333px;
}

 .createcustomemail{
	height:60px;
	width:325px;
	font-size:13.3333px;
} 
.programTextDiv{

    font-weight: bold;
    margin-left: 10px;
    margin-top: 40px;
    line-height: 0px;
}
  
</style>
<link rel="stylesheet" href="<c:url value='/css/epfacelift.css'/>" type="text/css" />
 
 
<div class="emailbuttons" >
<!-- editdefaultemail -->
<!-- createcustomemail -->
	<button type="button" class="editdefaultemail" id="editdefaultemail" ><b>Edit Default <br>User Activation Email Template</b></button>&nbsp;&nbsp;
	<button type="button" class="createcustomemail" id="createcustomemail" ><b>Create Custom <br> User Activation Email</b></button>
</div>

<div class="programTextDiv" >
	Program and State-Specific Templates in Use
</div>
<br>
<!-- class="full_main"  -->

<div id="viewActivationEmailGridCell" >
	<div id="viewActivationEmail" hidden="hidden"></div>
 	<div id=viewActivationEmailGridContainer class="kite-table">
 		<table class="responsive" id="viewActivationEmailGridTableId"></table>
		<div id="viewActivationEmailGridPager" style="width: auto;"></div>
 	</div>
</div>
<form id="editActivationEmailForm">
	<div id="editActivationEmailDiv" ></div>
</form>
