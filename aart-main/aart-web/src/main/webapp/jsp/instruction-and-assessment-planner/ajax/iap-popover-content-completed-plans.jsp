<%@ include file="/jsp/include.jsp" %>

<%--
this is just used to remove duplication of code, since this would be in 3ish different places
--%>

<c:if test="${not empty completedPlans}">
	<br/>
	<div class="container">
		COMPLETE
		<c:forEach items="${completedPlans}" var="completedPlan">
			<div class="row">
				<div class="col-sm-4">
					<fmt:formatDate value="${completedPlan.studentsTestsEndTime}" pattern="MM/dd"/>
				</div>
				<div class="col-sm-8">
					<c:choose>
						<c:when test="${isWriting or completedPlan.mastered == null}">
							<img class="mastery-image" src="<c:url value='images/icons/instruction-and-assessment-planner/svg/black-minusBoxTransparent.svg'/>"/>
						</c:when>
						<c:when test="${completedPlan.mastered}">
							<img class="mastery-image" src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/BlackStarTransparentBox.svg'/>"/>
						</c:when>
						<c:otherwise>
							<img class="mastery-image" src="<c:url value='/images/icons/instruction-and-assessment-planner/svg/BlackXTransparentBox.svg'/>"/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</div>
</c:if>