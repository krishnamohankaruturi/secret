<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
<%@ page isELIgnored="false" %>

<script type="text/javascript" src="<c:url value='/js/userMerge.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/userEditMerging.js'/>"> </script>


<script type="text/javascript" src="<c:url value='/js/external/jquery.json-2.4.min.js'/>"> </script>
<style>
.vertical_border {
    border-right-style:solid;
    border-right-color: #FF9933;
	border-width:2px;
}

#popup-new .form .form-fields {
margin:3px;
}

#editMergingUsers table td {
	vertical-align: bottom !important;
} 
 
#editMergingUsers .form .form-fields {
    margin: 3px 20px;
}
 #onL {
  display: none; 
}
#onR {
  display: none;
} 
 #byL {
  display: none; 
}
#byR {
  display: none;
} 
</style>
<input id="doesUserHaveHighRolesEditMergingUsers" type="hidden" value="${doesUserHaveHighRoles}" />
<div class="_bcg" id="popup-new">
	<div id="editMergingUsers-contain" class="form wrap_bcg">
	
	<div style="margin: 0px;width: 50%" id="editMergingUserLeft">
	<table role='presentation'></table>
	</div>
	
	<div style="margin: 0px;width: 50%" id="editMergingUserRight">
	</div>
		<table id="editMergingcontainertable" role='presentation' cellspacing="0">
            <tr>
                <td style="text-align:right;" colspan ="4" class="vertical_border">
                		<div id="editMergingUserL_div">	<button id="btn_editMergingUserL" class="btn_blue" style="float:left;"><fmt:message key="label.config.user.create.select"/></button></div>
                </td>
                 <td style="text-align:right;" colspan ="4">
                		<div id="editMergingUserR_div" style="margin-left: 20px;"><button id="btn_editMergingUserR" class="btn_blue" style="float:left;"><fmt:message key="label.config.user.create.select"/></button></div>
                		<div id="editMergingUserdiv_Next"><button id="btn_editMergingUser_Next" class="btn_blue" style="float:right;"><fmt:message key="label.config.user.create.next"/></button></div>
                </td>
            </tr>
       
            
            <tr>
            	<td colspan="4" class="vertical_border">
           			<div>
           				<span class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></span>
           			</div>
           		</td>
           		<td colspan="4" style="padding-left: 25px;">
           			<div>
           				<span class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></span>
           			</div>
           		</td>
            </tr>
            
            
           <tr style="border-right-color: red">
           		<td colspan="2" id="userInfoLefttd">
           			<div class="form-fields">
        					<label class="field-label" for="mfirstNameL"><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mfirstNameL" class="input-medium"/>
         			</div>
         			<div class="form-fields">
         			<input type="radio"  id="educatorLeft"  name= "email" > 
         			
        					<label class="field-label" for="memailAddressL"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
        					<input type="text" id="memailAddressL" class="input-medium"/>
         			</div>        			
           		</td>
           		
           		
           		<td colspan="2" class="vertical_border">
					<div class="form-fields">
        					<label class="field-label" for="mlastNameL"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mlastNameL" class="input-medium"/>
         				</div>
         				<div class="form-fields">
         				
         			<input type="radio"  id="educatorLeft"  name= "educator" >
        					<label class="field-label" for="meducatorIdentifierL"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
        					<input type="text" id="meducatorIdentifierL" class="input-medium"/>
         				</div>
           		</td>
                <td colspan="2" style="padding-left: 25px;">
						<div class="form-fields" >
        					<label class="field-label" for="mfirstNameR"><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mfirstNameR" class="input-medium"/>
         				</div>
         				<div class="form-fields">
         				<input type="radio"  id="emailLeft"  name= "email" > 
        					<label class="field-label" for="memailAddressR"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
        					<input type="text" id="memailAddressR" class="input-medium"/>
         			</div>
           		</td>
           		
           		
           		<td colspan="2">
           			<div class="form-fields" >
							<label class="field-label" for="mlastNameR"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mlastNameR" class="input-medium"/>
         			</div>
         			<div class="form-fields" >
         			<input type="radio"  id="educatorRight"  name= "educator" > 
        					<label class="field-label" for="meducatorIdentifierR"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
        					<input type="text" id="meducatorIdentifierR" class="input-medium"/>
         			</div>
         			
           		</td>
           </tr>
           
            <tr>
	            	<td id="eorgTableL" colspan="4" class="vertical_border">
		            	 <div id="mergeUserGridContainer1" class="kite-table">
						     <table id="mergeUserGrid1" class="responsive" role='presentation' ></table>
						     <div id="mergeUserGrid1Pager" style="width: auto;"></div>
				         </div>
	                </td>
	                <td id="eorgTableR" colspan="4" style="padding-left: 25px;">
		                 <div id="mergeUserGridContainer2" class="kite-table">
						     <table id="mergeUserGrid2" class="responsive"></table>
						     <div id="mergeUserGrid2Pager" style="width: auto;"></div>
				         </div>
	                </td>
            </tr>
      <!--  -->  
            <tr>
       
            <td id="mergeRoster" colspan="4" class="vertical_border">
            <div id="mergeUserRoosterGridContainer" class="kite-table">
            <table id="mergeUserRoosterGrid" class="reponsive" role='presentation'></table>
            <div class="responsive" id="viewRosterGridPager1"></div>	
            </div> 
                                                                          
        <input type="radio" id="left_security"  name ="date" ><span class="panel_head sub"><fmt:message key="label.config.user.create.security"/></span><br><span id=agreedL></span>&nbsp;&nbsp;<span id=expireidL></span>&nbsp;<span id=byL>by</span>&nbsp;<span id=signerL></span><br>
        
         <input type="radio" id="left_training"  name ="training" ><span class="panel_head sub"><fmt:message key="label.config.user.create.training"/></span><br><span id=trainingL></span>&nbsp;<span id=onL>on</span>&nbsp;<span id=dateL></span><br>
          
            </td>
         
             <td id="mergeRosterRight" colspan="4" class="vertical_border">
            <div id="mergeUserRoosterGridContainer2" class="kite-table">
            <table id="mergeUserRoosterGrid2" class="reponsive" role='presentation'></table>
            <div class="responsive" id="viewRosterGridPager2"></div>	
            </div>
  <input type="radio"  id="right_security"  name= "date" ><span class="panel_head sub"><fmt:message key="label.config.user.create.security"/></span><br><span id=agreedR></span>&nbsp;&nbsp;<span id=expireidR></span>&nbsp;<span id=byR>by</span>&nbsp;<span id=signerR></span><br>
    <input type="radio"  id="right_training"  name= "training" ><span class="panel_head sub"><fmt:message key="label.config.user.create.training"/></span><br><span id=trainingR></span>&nbsp;<span id=onR>on</span>&nbsp;<span id=dateR></span><br>
            </td>
            
            
            
            
            </tr>
 
    </table>
     
			    <input type="hidden" name="hdneuserIdL" id="hdneuserIdL" value="">
			    <input type="hidden" name="hdneuserIdR" id="hdneuserIdR" value="">
			  
	    
	</div>
	
</div>