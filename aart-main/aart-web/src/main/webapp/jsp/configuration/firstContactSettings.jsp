<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<%-- <script type="text/javascript" src="<c:url value='/js/firstContactSettings.js'/>" /> --%>
<div id="firstContactSettingsDiv">
    <input id="fcrOrgId" value="${organizationId}" type="text" class="hidden" />
    <div>
     <span id="top-savefcssettings-error" class="error_message ui-state-error hidden"><fmt:message key='label.savefcssettings.error'/></span>
        <span id="top-savefcssettings-success" class="error_message ui-state-highlight hidden"><fmt:message key='label.savefcssettings.success'/></span>
        <button type="button" id="savefirstContactSettings-top" class="btn_blue save">
            <fmt:message key="button.save" />
        </button> 
        </div>
        <div class="instructionHead" class="fluid">Set First Contact Survey State Configurations</div>
        <div id="firstContactSettingsTable">
            <table role="presentation" style="width:1030px;">
                <thead>
                    <tr>
                        <th>
				            <div class="instructionHead">State</div>
				            <div class="instructionExp1" style="text-align: left">For each state listed make selections for the Required Questions and Subjects.
				            </div>
                        </th>
                        <th>
				            <div class="instructionHead">Required Questions</div>
				            <div class="instructionExp1" style="text-align: left">Choose if educators are required to answer the Core Set of All Questions.
				            The Core Set will make only survey questions used in calculations of student comp bands required responses.
				            The All Questions option will make all questions in the First Contact Survey required for all students in
				            the state.
				            </div>
                        </th>
                        <th >
				            <div class="instructionHead">Survey Subject</div>
				            <div class="instructionExp1" style="text-align: left">Check the subjects the state will be offering to include the subject questions
				            in the survey. This will calculate complexity bands for these subjects.
				            </div>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="firstContactSetting" items="${firstContactSettings}">
                        <tr id="${firstContactSetting.organizationId}">
                            <td><span class="orgNameContent">${firstContactSetting.organizationName}</span></td>
                            <td><input type="radio" name="${firstContactSetting.organizationId}" value="${coreSet.id}" 
                            <c:if test="${coreSet.id == firstContactSetting.categoryId}">
                                checked="true" 
                            </c:if>
                            title="Core Set"/>Core Set
                            <input type="radio" name="${firstContactSetting.organizationId}" value="${allQuestions.id}"
                            <c:if test="${allQuestions.id == firstContactSetting.categoryId}">
                                checked="true" 
                            </c:if> 
                            title="All Questions"/>All Questions</td>
                             <td><input type="checkbox" name="${firstContactSetting.organizationId}" id="elaFlag${firstContactSetting.organizationId}" 
                           <c:if test="${firstContactSetting.elaFlag}">
 								checked="true"                           
                           </c:if>
                            title="ELA"/>ELA
                            <input type="checkbox" name="${firstContactSetting.organizationId}" id="mathFlag${firstContactSetting.organizationId}" 
                           <c:if test="${firstContactSetting.mathFlag}">
 								checked="true"                           
                           </c:if>
                            title="Math"/>Math 
                            <input type="checkbox" name="${firstContactSetting.organizationId}" id="scienceFlag${firstContactSetting.organizationId}" 
                           <c:if test="${firstContactSetting.scienceFlag}">
 								checked="true"                           
                           </c:if>
                            title="Science"/>Science
                            <span class="top-newsavefcssettings-error error_message ui-state-error hidden">
                            <fmt:message key='label.newsavefcssettings.error'/></span></td> 
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="bottomInfoRight">
            <span id="bottom-savefcssettings-error" class="error_message ui-state-error hidden"><fmt:message key='label.savefcssettings.error'/></span>
            <span id="bottom-savefcssettings-success" class="error_message ui-state-highlight hidden"><fmt:message key='label.savefcssettings.success'/></span>
            <button type="button" id="savefirstContactSettings-bottom" class="btn_blue save">
                <fmt:message key="button.save" />
            </button>
        </div>
    </div>

<script type="text/javascript" src="<c:url value='/js/firstContactSettings.js'/>"></script>