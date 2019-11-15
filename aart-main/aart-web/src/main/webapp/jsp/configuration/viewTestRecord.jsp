<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/include.jsp" %>
<style>
	#viewTestRecordMainContainer{
		margin-left:27px;
	}
	#viewTestRecordSelectViewType{
		margin-bottom:35px;
	}
	#searchBtnViewTestRecordDiv{
		float: right;
		position: relative;
		right: 80px;
    	top: -90px;
	}
	
	#viewStudentByTestRecordHeader,
	#viewTestRecordByStudentHeader{
		color:#81a64c;
		font-size:20px;
		margin-top:12px;
	}
  
.ui-widget-header
{
 background:#fff;
}

	
</style>

 <div id=viewTestRecordMainContainer>
	 <div id ="testRecordActions">
	<ul id="viewTestRecordNav" class="nav nav-tabs sub-nav">			
		 	<li class="nav-item get-sub-viewrecord">	
		 		<a class="nav-link" href="#ViewStudentByTestRecord" data-toggle="tab" role="tab">View Students by Test Record</a>
		 		</li>	
		 		
		 			 <li class="nav-item get-sub-viewrecord">	
		 		<a class="nav-link" href="#ViewTestRecordByStudent" data-toggle="tab" role="tab">View Test Records by Student</a>
		 		</li>			
		 		</ul>
		 		</div> 
		 		
	
	<div id=viewTestRecordContainer class="hidden">
		 <div class="noBorder">
				<div id="searchFilterViewTestRecordContainer">
					<form id="searchFilterViewTestRecordForm" name="searchFilterViewTestRecordForm" class="form">
							<div class="btn-bar">
								<div id="searchFilterViewTestRecordErrors" class="Usermessages"></div>
								<!-- <div id="searchFilterViewTestRecordMessage" style="padding:20px" class="hidden"></div> -->
								<span class="info_message ui-state-highlight successMessage hidden" id="viewRecordSuccessMessage" ><fmt:message key="label.config.testRecord.create.success"/></span>
								<span class="error_message ui-state-error selectAllLabels hidden validate" id="viewRecordFailMessage"><fmt:message key="error.config.testRecord.create.fail"/></span>
							</div>
							<div class="">
								<label id="viewStudentByTestRecordHeader" class="field-label">Select Organization, Assessment Program and Test Record, then click Search:</label>
								<label id="viewTestRecordByStudentHeader" class="field-label">Select Organization and Assessment Program, then click Search:</label>			
							</div>
							
							<div class="" style="margin-top:15px">
								<label class="field-label" style="font-size:18px">Organization:</label>			
							</div>
						    <div class="form-fields">
								<label for="stateOrgsViewTestRecord" for="stateOrgsViewTestRecord" class="field-label">STATE:<span class="lbl-required">*</span></label>			
								<select id="stateOrgsViewTestRecord" title="State" class="bcg_select required" name="stateOrgsViewTestRecord">
									<option value="">Select</option>
								</select>
							</div>
						
							    <div class="form-fields">
									<label for="districtOrgsViewTestRecord" for="districtOrgsViewTestRecord" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
									<select id="districtOrgsViewTestRecord" title="District" class="bcg_select required" name="districtOrgsViewTestRecord">
										<option value="">Select</option>
									</select>
								</div>
							
							<div class="form-fields">
								<label for="schoolOrgsViewTestRecord" for="schoolOrgsViewTestRecord" class="field-label">SCHOOL:</label>			
								<select id="schoolOrgsViewTestRecord" title="School" class="bcg_select" name="schoolOrgsViewTestRecord">
									<option value="">Select</option>
								</select>
							</div>
							
							<div style="margin-top:10px">												
								<div class="form-fields">
									<label for="assessmentProgramsViewTestRecord" for="assessmentProgramsViewTestRecord" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
									<select id="assessmentProgramsViewTestRecord" title="Assessment Program" class="bcg_select required" name="assessmentProgramsViewTestRecord">
										<option value="">Select</option>
									</select>
								</div>
							</div>	
							
							<div id=viewSubjectAndTestTypeSelectTestRecord class="hidden">
								<div style="margin-top:20px">
									<label class="field-label" style="font-size:18px;">Test Record:</label>		
								</div>
								
								<div class="form-fields">
										<label for="viewSubjectSelectTestRecord" for="viewSubjectSelectTestRecord" class="field-label">Subject:<span class="lbl-required">*</span></label>			
										<select id="viewSubjectSelectTestRecord" title="Subject" class="bcg_select required" name="viewSubjectSelectTestRecord">
											<option value="">Select</option>
										</select>
									</div>
								
								<div class="form-fields">
									<label for="viewTestTypeSelectTestRecord" for="viewTestTypeSelectTestRecord" class="field-label">Test Type:<span class="lbl-required">*</span></label>			
									<select id="viewTestTypeSelectTestRecord" title="Test Type" class="bcg_select required" name="viewTestTypeSelectTestRecord">
										<option value="">Select</option>
									</select>
								</div>
							</div>
							
							<div id="searchBtnViewTestRecordDiv" class="form-fields">
						            <button class="btn_blue" id="searchBtnViewTestRecord" type="button">Search</button> 			
						    </div>
										
					</form>
				</div>
				<div id="studentViewTestRecord" class="hidden">
					
					<form id="studentViewTestRecordform">
						<div id="viewTestRecordGridNextScreen">
							<div id=testRecordSubjectTestTypeHeader class="hidden">
							   <div style="margin-top:20px;margin-bottom:20px">
							       <label for="" class="field-label" style="font-size:20px;color:#81a64c;">Test Record:</label>  
							   </div>
							   <div style="margin-left:20px">
						         <span> 
						              <label class="field-label" style="font-size:18px;">SUBJECT:</label>
						              <label id="viewSelectSubjectVal" style="color:#0e76bc" ></label> 
						               
						         </span>
						         <span style="margin-top: 20px; margin-left:20px">
						              <label class="field-label" style="font-size:18px;">TEST TYPE:</label>
						              <label id="viewSelectTestTypeVal" style="color:#0e76bc"></label>  
						         </span>
						       </div> 	
   							   <div style="margin-top:20px; margin-left:20px">
   								 	<label id="testRecordRegisteredStudentLabel" style="font-size:18px;color:#81a64c;" class="field-label">Registered Student(s):</label>
   							   </div> 
							</div>
							<div>
								 <div class="btn-bar">
								 <label id="studentNameStateStuIdLabel" style="font-size:18px;color:#81a64c; " class="field-label">Select a student, then click Next:</label>
									<button type="button" id="viewTestRecordGridTopNextBtn" class="btn_blue viewTestRecordGridNextBtn" style="margin-right: 10px; margin-top:10px; float:right;">Next</button>  	
								</div>		 
							</div>
							<div class ="table_wrap" style="float:right">
								<div class="kite-table" style="margin-left: 2px;margin-top: -1px;" >
									<table id="viewTestRecordStudentsByOrgTableId"  class="responsive"></table>
									<div id="pViewTestRecordStudentsByOrgTableId" class="responsive"></div>
								</div>
							</div>
							
							<div style="margin-left:670px;" class="form-fields">
								<div class="btn-bar" style="float:right;margin-right: 10px;">
									<button type="button" class="btn_blue viewTestRecordGridNextBtn" id="viewTestRecordGridBottomNextBtn">Next</button>
								</div>				 
							</div>
						</div>	
					</form>
					
					<div id=viewTestStudentRecordScreen class="hidden">
						<div id=studentNameStateStuIdHeader>
							<div style="margin-top:20px">
								<label class="field-label" style="font-size:20px;color:#81a64c;">Student:</label>		
							</div>
					        <div style="margin-top:20px; margin-left:20px">	
				        		<label class="field-label" style="font-size:18px;">Name:</label>
				        		<label id="selectviewTestStudentVal" style="color:#0e76bc"></label>	
				        	</div>
				        	<div style="margin-left:20px; margin-top:5px">
					        	<label class="field-label" style="font-size:18px;">State Student ID:</label>
					        	<label id="selectviewTestStateStudentIdVal" style="color:#0e76bc"></label>		
				        	</div>
				        	<div style="margin-top:20px; margin-left:20px;color:#81a64c;">	
				        		<label class="field-label" style="font-size:18px;">Test Record(s) registered to:</label>
				        	</div>
			        	</div>
			        	<div class ="table_wrap">
							<div class="kite-table" style="margin-left: 2px;margin-top: -1px;" >
								<table id="viewStudentTestRecordStudentsByOrgTableId"  class="responsive"></table>
								<div id="pViewStudentTestRecordStudentsByOrgTableId" class="responsive"></div>
							</div>
						</div>
					</div>
					<div id="buttonBackOrDoneFromViewTestScreen" class="hidden">
						<div style="width:70%; float:left">	
			        		<p style=" margin-left:10px;color:#81a64c;" class="field-label">Click Back to view more/different records. Click Done, if done viewing.</p>
			        	</div>
						<div style="width:23%; float:right">
							<button type="button" id="backFromViewTestStudentRecordScreen" class="btn_blue">Back</button>
							<button type="button" id="doneFromViewTestStudentRecordScreen" class="btn_blue">Done</button>
						</div>
					</div>
				</div>
		</div>
	</div>
</div>
<script>
$('#viewTestRecordNav li.nav-item:first a').tab('show');	
</script>
