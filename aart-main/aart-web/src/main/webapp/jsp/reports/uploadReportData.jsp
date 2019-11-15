<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')">
	<div>	
		<div id="ARTSmessages" class="messages messagesOverloaded">
			<span class="error_message ui-state-error hidden fileExtensionError" id="fileExtensionError">
				<fmt:message key="error.uploadreport.invalidExtension"/>
			</span>
			<span class="error_message ui-state-error hidden fileTypeError" id="fileTypeError">
				<fmt:message key="error.filetype.invalid"/>
			</span>
		</div>
		<div class="reportUpload">
			<form method="post" id="fileUploadForm" action="uploadFileData.htm" enctype="multipart/form-data">
				<br />
				Upload File Type: 
				<select name="fileTypes" class="bcg_select" id="fileTypes">
					<option value="0">Select</option>
				</select> <br /><br />
				<input name="fileData" id= "fileData" type="file" cssStyle="border: 1px solid #777;"/>
				<br /><br />
			</form>
			<input type="submit" id= "uploadFile" value="<fmt:message key='upload.button'/>" />
			
			<br /> <br/> <br />
			
			<c:if test="${uploadCompleted}">
				<c:choose>
					<c:when test="${ ! empty inValidDetail }">
						<!-- For file rejection alone -->
						<fmt:message key="upload.file.rejected.prefix" />
						<!-- TODO for future display more like expected 3 but have 5 columns -->
						<fmt:message key="rejected.reason.format">
							<fmt:param value="${inValidDetail.fieldName}"/>
							<c:if test="${ not empty inValidDetail.invalidType }">
								<fmt:param>
								<fmt:message key="${inValidDetail.invalidType.invalidTypeName}"/>
								</fmt:param>
							</c:if>
						</fmt:message>
					</c:when>
					<c:when test="${ ! empty inValidRecords }">
						<fmt:message key="upload.summary">
							<fmt:param value="${recordsCreatedCount}" />
							<fmt:param value="${recordsUpdatedCount}" />
							<fmt:param value="${recordsRejectedCount}" />
							<fmt:param value="${totalRecordCount }"/>
						</fmt:message>
						<fmt:message key="upload.completed.witherrors" />
						<display:table name="inValidRecords" id="inValidRecord" class="gridtable">
					  		<c:set var="runningCount" value="0" scope="page" />
						<!-- TODO change it to record type.identifier -->
						<display:column property="identifier" title="Identifier" />
						<display:column title="Reasons For Not Valid">
							<c:forEach items="${inValidRecord.inValidDetails}"
							var="inValidDetail" varStatus="status">${status.count})
							  		<c:choose>
								  		<c:when test="${inValidDetail.rejectRecord}">
								  			<c:choose>
								  				<c:when test="${inValidDetail.invalidType.invalidTypeName == 'ErrorOccurred' }">
								  					<fmt:message key="reject.permissiondenied.createorganization" />
								  				</c:when>
								  				<c:otherwise>
											  		<fmt:message key="property.reject.invalid">
												  		<fmt:param value="${inValidDetail.fieldName}"/>
												  		<fmt:param value="${inValidDetail.formattedFieldValue}"/>
												  		<c:if test="${ not empty inValidDetail.invalidType }">
												  			<fmt:param>
												  				<fmt:message key="${inValidDetail.invalidType.invalidTypeName}"/>
												  			</fmt:param>
												  		</c:if>
											  		</fmt:message>
									  			</c:otherwise>
									  		</c:choose>
								  		</c:when>
								  		<c:otherwise>
									  		<fmt:message key="property.invalid">
										  		<fmt:param value="${inValidDetail.fieldName}"/>
										  		<fmt:param value="${inValidDetail.formattedFieldValue}"/>
										  		<c:if test="${ not empty inValidDetail.invalidType }">
										  			<fmt:param>
										  				<fmt:message
										  					key="${inValidDetail.invalidType.invalidTypeName}"/>
										  			</fmt:param>
										  		</c:if>
									  		</fmt:message>
								  		</c:otherwise>
							  		</c:choose>
							  		<br/>
							  		<c:set var="runningCount" value="${status.count}" scope="page" />
							  	</c:forEach>
						  	</display:column>
						</display:table>
			        </c:when>
			        <c:otherwise>
						<fmt:message key="upload.summary">
							<fmt:param value="${recordsCreatedCount}" />
							<fmt:param value="${recordsUpdatedCount}" />
							<fmt:param value="${recordsRejectedCount}" />
							<fmt:param value="${totalRecordCount }"/>
						</fmt:message>			        
			        <fmt:message key="csv.upload.successful"/>
			        </c:otherwise>
	        </c:choose>
	        </c:if>
	        
		</div>
					    
		<br />
		<div id="noReport" class="none" style="width:100%"></div>
		<div class="table_wrap">
			<div class="kite-table">
				<table id="uploadReportDataTableId" class="responsive"></table>
				<div id="puploadReportDataTableId"   class="responsive"></div>
			</div>
			<br />
			Be patient during uploads. Upload times may vary.
			<br /> <br />
		</div>
	</div>

	<div id="ConfirmationDiv">
	</div>

	<script>
	
		$(function() {
			
			//$("#ARTSmessages").hide();
			
			//Build the overlay
			$('#ConfirmationDiv').dialog({
				resizable: false,
				height: 150,
				width: 400,
				modal: true,
				autoOpen:false,
				title:'&nbsp;',
				buttons: {
					Ok: function() {
						$('#fileUploadForm').submit();
				    },
				    Cancel: function() {
				    	 $(this).dialog('close');
				    }			    
				}
			});
			
			// load File Type names		
			loadUploadFileTypes();
			
			//"Loading...." message display.
			var mousePosition;
		
			//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
			window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
			
			//Capture the mouse position for Loading message display.
			$(document).mousemove(function(e) {
				mousePosition = e.pageY;				
			}); 
			
			var gridWidth = $('#uploadReportDataTableId').parent().width();		
			if(gridWidth == 100 || gridWidth == 0) {
				gridWidth = 960;				
			}
	        var cellWidth = gridWidth/4;
			var gridParam;
			
			$gridAuto = $("#uploadReportDataTableId");
			
			var cmforAutoRegistration = [
					{ name : 'id', index : 'id', width : cellWidth, search : false, hidden: true, hidedlg: false },
	
					{ name : 'fileTypeCategoryName', index : 'fileTypeCategoryName', width : cellWidth, search : false, hidden: false, hidedlg: false },
	
					{ name : 'uploadedFileName', index : 'uploadedFileName', width : cellWidth, search : false, hidden : false, hidedlg : false},
					
					{ name : 'userName', index : 'userName', width : cellWidth, search : false, hidden : false, hidedlg : false},
					
					{ name : 'createdDate', index : 'createdDate', width : cellWidth, search : false, hidden : false, hidedlg : false},
				];
			
			myDefaultSearch = 'cn',				
		    myColumnStateName = 'ColumnChooserAndLocalStorage2.colState2';
		    
		    var myColumnsState;
		    var isColState;
		    
		    firstLoad = true;
			myColumnsState = restoreColumnState(cmforAutoRegistration,myColumnStateName);
			isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
				
			//JQGRID
			jQuery("#uploadReportDataTableId").scb({
				url : 'getUploadedReportData.htm?q=1',
				postData : {
					orgChildrenIds : function() {
						return null; //$('#orgChildrenIdsDLM').val();
					},
					filters : isColState ? myColumnsState.filters : null ,
				},
				mtype: "POST",
				datatype : "json",
				width: gridWidth,
				colNames : [
		   						'id', 'File Name', 'Uploaded File', 'User Name', 'Date/Time Uploaded'
		   		           ],
		   		colModel :cmforAutoRegistration,	
				rowNum : 10,
				rowList : [ 5,10, 20, 30, 40, 60, 90 ],
				height : 'auto',
				//pager : '#puploadReportDataTableId',
				sortname : 'linkagelabel',
				viewrecords : true,
				multiselect: false,
				page: isColState ? myColumnsState.page : 1,
				search: isColState ? myColumnsState.search : false,
		        sortname: isColState ? myColumnsState.sortname : 'linkagelabel',
		       	sortorder: isColState ? myColumnsState.sortorder : 'desc',
				sortable: {
					update: function() {
						saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
					}
				},
				caption : "",
				grouping : false,
				shrinkToFit : false,
				groupingView : {
					groupField : [ '' ],
					groupColumnShow : [ false ],
					groupText : [ '<b>{0}</b>' ],
					groupCollapse : false,
					groupOrder : [ 'asc' ],
					groupSummary : [ true ],
					groupDataSorted : true
				},
				footerrow : true,
				userDataOnFooter : true,
				loadBeforeSend: function () {
					//Code to position "Loading...." message.
			    	var gridIdAsSelector = $.jgrid.jqID(this.id),
			        $loadingDiv = $("#load_" + gridIdAsSelector),
			        $gbox = $("#gbox_" + gridIdAsSelector);
			    	$loadingDiv.show().css({
			    		top:  (Math.min($gbox.height(), mousePosition) - 200) + 'px',
			        	left: (Math.min($gbox.width(), $(window).width()) - $loadingDiv.outerWidth())/2 + 'px'
			    	});
			    },
			    beforeRequest: function() {
			    	//Set the page param to lastpage before sending the request when 
					  //the user entered current page number is greater than lastpage number.
					var currentPage = $(this).getGridParam('page');
	                var lastPage = $(this).getGridParam('lastpage');
	                
	                 if (lastPage!= 0 && currentPage > lastPage) {
	                	 $(this).setGridParam('page', lastPage);
	                	$(this).setGridParam({postData: {page : lastPage}});
	                }
			    },  
			    loadComplete: function () {
	                   var $this = $(this);
	                   if (firstLoad) {
	                       firstLoad = false;
	                       if (isColState) {
	                           $this.jqGrid("remapColumns", myColumnsState.permutation, true);
	                       }
	                       if (typeof (this.ftoolbar) !== "boolean" || !this.ftoolbar) {
	                           // create toolbar if needed
	                           $this.jqGrid('filterToolbar',
	                               {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearch});
	                       }
	                   }
	                   refreshSerchingToolbar($this, myDefaultSearch);
	                   saveColumnState.call($this, this.p.remapColumns,myColumnStateName);
	               },
	               resizeStop: function () {
	                   saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
	               },
			    gridComplete: function() {
				    var recs = parseInt($("#uploadReportDataTableId").getGridParam("records"));
					if (isNaN(recs) || recs == 0) {
					     //Set min height of 1px on no records found
					     $('.jqgfirstrow').css("height", "4px");
					 }
			    }
			});
	               
			$.extend($.jgrid.search, {
			    multipleSearch: true,
			    multipleGroup: true,
			    recreateFilter: true,
			    closeOnEscape: true,
			    closeAfterSearch: true,
			    overlay: 0
			});
			
			//$grid.jqGrid('navGrid', '#puploadReportDataTableId', {edit: false, add: false, del: false});
			//jQuery("#uploadReportDataTableId").jqGrid(
			//		'groupingRemove', false);
			//Clear the previous error messages
			setTimeout("aart.clearMessages()", 0);
			
		});
		
		function loadUploadFileTypes() {
			return $.ajax({
			        url: 'getUploadFileTypes.htm',
			        data: { },
			        dataType: 'json',
			        type: "GET",
			        success: function(fileTypes) {
			        	if (fileTypes != null) {
							$.each(fileTypes, function(i, fileType) {
								$('#fileTypes').append($('<option></option>').attr("value", fileType.id).text(fileType.categoryName));
							});
			        	}
			        }
				});		
		}
		
		
		$('#uploadFile').click(function(event) {
			var grid = $("#uploadReportDataTableId");
			var ids = grid.getDataIDs();
			var found = false;
			if($('#fileTypes option:selected').val() == 0) {
				$("#ARTSmessages").show();
				$('#fileTypeError').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
				return false;
			}
			
			if($('#fileData').val().split('.').pop() == "csv") {
					//$('#fileData').val().split('.').pop() == "xls" ||
					//$('#fileData').val().split('.').pop() == "xlsx") {
				$.each(ids, function (index, id) {
					if(grid.getCell(id,'fileTypeCategoryName') == $('#fileTypes option:selected').text()  &&
							grid.getCell(id,'uploadedFileName') == $('#fileData').val()) {
						console.log(grid.getCell(id,'fileTypeCategoryName') + " ==  " + $('#fileTypes option:selected').text() +
								" && " + grid.getCell(id,'uploadedFileName') + " == " + $('#fileData').val());
						$('#ConfirmationDiv').html("Are you sure, you want to continue?");
						$('#ConfirmationDiv').dialog('open');
						found = true;
					}
				});
				
				if(!found) {
					$('#fileUploadForm').submit();
				}
					
			} else {
				$("#ARTSmessages").show();
				$('#fileExtensionError').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function(){ $("#ARTSmessages").hide(); },3000);
				return false;
			}					
		});
	
	</script>

</security:authorize>