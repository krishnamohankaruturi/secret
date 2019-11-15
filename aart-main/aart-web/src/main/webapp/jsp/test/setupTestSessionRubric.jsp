<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
.col-3 {
	column-gap: 5px;
}

.ui-widget-overlay { 
    position: absolute; 
    top: 0; 
    left: 0; 
    width: 100%; 
    height: 100%; 
    background: #aaaaaa;
    opacity: 0.3;
}

</style>
<input type="hidden" id="rubricScoresGuidelines" name="rubricScoresGuidelines" value="${rubricScoresGuidelines}"/>
<div id="rubricScoreDiv" style="width: 100%; overflow-y:scroll; height:200px;" class="none">
	<div id="rubricScoringDiv" style="width: 100%;"></div>
</div>  
  <input type="button" id="clearButton" value="Clear Selections" style="float:right;" class="btn_blue clearButton"/>
  <input type="button" id="saveButton" value="Save" style="float:right;" class="btn_blue saveButton"/>
			
<div id="confirmRubricDialog"></div>
<div id="dialog-saved">Score is saved successfully</div>
<div id="saveCriteriaDialog"></div>
<script type="text/javascript">
	 
var scores = '${scores}';
var scoreList = '${scoreList}';
var rubricScoreId = '';
var rubricsLen=0;
var taskVariantId1 = '${taskVariantId}';
var clearFlag = new Boolean();
clearFlag = false;
	$.ajax({
		url: 'getRubricScoringGuidelines.htm',
		data: {
			taskVariantId: taskVariantId1,
			studentId: '${studentId}',
			testId:'${testId}'
		},			
		dataType: 'json',
		type: "POST",
		success: function(response) {
			var div = document.getElementById('rubricScoreDiv');
			var widthpercent = Math.floor(100/scores) - 2;
			widthpercent = widthpercent +"%";
/* 			 for(var j= 1; j <= scores; j++){
				i = scores - j;  */			 
 			if(scoreList!=null && scoreList!=undefined && scoreList!='' && scoreList.length>0){
				var scoreListArray = scoreList.split(',');
			    for (var h=0; h < scoreListArray.length; h++){
				i = scoreListArray[h]; 							
				var htmlString= "<div id='rating"+ i + "'";
				htmlString = htmlString + " class='rating rating"+ i + "'" + " style= 'width:" + widthpercent + ";'" + ">" + "<div style='height: 30px;'>Rating of "+ i + "</div><hr style='width: 100%'/>";
				var rubrics=response.rubrics;
				rubricsLen = rubrics.length/scores;
				for(var k=0; k < rubrics.length;k++){
					if(rubrics[k].rubricInfoScore == i){
						var checked="";
						if(rubrics[k].selected==true){
							checked="checked='checked'";
						}
					htmlString += "<div style='height: 80px;'><input type='radio' class='ratings ratingScores" + rubrics[k].rubricInfoScore + "' name='Catg_" + rubrics[k].rubricInfoCategoryId + "' id ='" + rubrics[k].rubricInfoCategoryId +":"+taskVariantId1+
					rubrics[k].rubricInfoScore +  "' value='" +rubrics[k].rubricInfoId+"_"+ rubrics[k].rubricInfoScore + "'" +checked+ " />  <b>"+rubrics[k].categoryName +": </b>" +rubrics[k].rubricInfoDesc+"</div><hr style='width: 100%'/><br><br>";
					}
				}
				htmlString = htmlString + "</div>";
				$(div).append(htmlString);
			   }
		     }
			 $('.ratings').click(function() {			    	
				 changed= true;
				});
			if(response.rubricScore!=null){
				rubricScoreId=response.rubricScore.id;
				var scrStr=response.rubricScore.rubricScore+'';
				if(scrStr.length>4){
					scrStr = scrStr.substring(0,3);
				}
				$("#scoreVal").html(scrStr+" ( of "+scores+" )");
				$('#ScorerName').show();
				$("#dateVal").html('Date: '+response.date);				
			}			
		}
	});
	
	var isSaved= false;
	var changed=false;
    $('#saveButton').click(function() {
		 validateRubricScore();
	});
    
    function saveScore(){
    	var selectedRubricIds='';
    	var score=0;
    	$('.ratings').each(function(){
			if($(this).attr('checked')=='checked'){
				var selectedValue=$(this).val().split('_');
				score= score+parseInt(selectedValue[1], 10);
				selectedRubricIds = selectedRubricIds+selectedValue[0]+',';
			}
		});
		score = score/rubricsLen;
		score = (Math.round(score * 100) / 100);		
    	$.ajax({
			url: 'saveRatings.htm',
			data:  {'rubricScoreId':rubricScoreId,'taskVariantId': '${taskVariantId}','rubricInfoIds':selectedRubricIds,'testId': '${testId}',studentId:'${studentId}','score':score},			
			dataType: 'json',
			type: "POST",
			success: function(response) {
				isSaved= true;
				changed = false;
				rubricScoreId=response.score.id;
				var scrStr=response.score.rubricScore+'';
				if(scrStr.length>4){
					scrStr = scrStr.substring(0,3);
				}
				$("#scoreVal").html(scrStr+" ( of "+scores+" )");
				$('#ScorerName').show();
				$("#dateVal").html('Date: '+response.date);
				$("#dialog-saved").dialog("open");
			}
		});
    }
    
    function validateRubricScore(){
    	var i=0;
    	$('.ratings').each(function(){
			if($(this).attr('checked')=='checked'){
			    i++;
			}
		});
    	if(i==rubricsLen){
    		saveScore();    
    		clearFlag = false;
    	}else{    		
    		$('#saveCriteriaDialog').html('One or more criteria are not selected to score. If you save it, system scores 0 for the empty criterion. Are you sure you want to continue?'); 
    		$('#saveCriteriaDialog').dialog('open');   		
    	 }
    }
    
    $(function() {
  	  $('#saveCriteriaDialog').dialog('close');
  	 });
	
	$("#clearButton").click(function() {
		for(var i=1; i <= scores;i++){
		$('.ratingScores'+i).prop("checked", false);
		}
		clearFlag = true;
	});
	$("#closeButton").click(function() {
		var flag = new Boolean();
		flag = false;
		if(!isSaved && changed){
			for(var i=0; i < scores;i++){
			if($('.ratingScores'+i).is(":checked")){
				flag = true;
			  }
			}
		}
		if(flag){
			$('#confirmRubricDialog').dialog('open');
			$('#confirmRubricDialog').html('You have unsaved changes, do you wish to continue?');
			}else if(clearFlag){
				$('#confirmRubricDialog').dialog('open');
				$('#confirmRubricDialog').html('You have unsaved changes, do you wish to continue?');
			}else{
				$('#confirmRubricDialog').html('');
				var locationUrl = window.location.href.slice(0, -1);
				window.location.href = (locationUrl) + '&enableRubric=true';
			}
	});
	
	function close(){
	var flag = new Boolean();
	flag = false;
	if(!isSaved && changed){
		for(var i=0; i < scores;i++){
		if($('.ratingScores'+i).is(":checked")){
			flag = true;
		  }
		}
	}
	if(flag){
		$('#confirmRubricDialog').dialog('open');
		$('#confirmRubricDialog').html('You have unsaved changes, do you wish to continue?');
		return false;
		}else{
			var locationUrl = window.location.href.slice(0, -1);
			window.location.href = (locationUrl) + '&enableRubric=true';
		}		
    };  

	$('#confirmRubricDialog').dialog({
		resizable: false,
		height: 150,
		width: 400,
		modal: true,
		autoOpen:false,			
		title: "Warning - Unsaved Changes",
		buttons: {
			Continue: function() {
				$(this).html('');
				var locationUrl = window.location.href.slice(0, -1);
				window.location.href = (locationUrl) + '&enableRubric=true';
		    },
		    Cancel: function() {
		    	 $(this).dialog("close");
		    }
		}
	});

	$('#saveCriteriaDialog').dialog({
		autoOpen:false,
		resizable: false,
		height: 150,
		width: 400,
		modal: true,
		title: "Warning - Unsaved Changes",
		buttons: {
			Continue: function() {
				saveScore();
				 $(this).dialog("close");
		    },
		    Cancel: function() {
		    	 $(this).dialog("close");
		    }
		}
	  });	

	$("#dialog-saved").dialog({
		resizable: false,
		height: 150,
		width: 300,
		modal: true,
		autoOpen:false,
		title:'&nbsp;',
		buttons: {
		      OK: function() {
		    	 $(this).dialog("close");
		    }
		}
	});

</script>