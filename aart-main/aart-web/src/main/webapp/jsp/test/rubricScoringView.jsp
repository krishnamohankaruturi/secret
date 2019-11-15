<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="rubricDiv">	
 <div>
	<br><br><br>
	<div style="padding-left: 10px; padding-right: 10px; border: 0px solid #646567;">
		<b> Question: <br><br></b> ${questionResponseMap.rubricQuestion}
	</div>
	<br><br><br>
	</div>
	<div>
		<jsp:include page="setupTestSessionRubric.jsp" />
	</div>
	<br><br><br><br><br><br>
	<div style="padding-left: 10px; padding-right: 10px; clear: both; overflow-y: scroll; height: 200px; border: 0px solid #646567;">
		<b> Answer: </b> ${questionResponseMap.rubricResponse}
	</div>
	<input type="button" id="closeButton" value="Close" style="float:right;" class="btn_blue closeButton"/>
</div>

