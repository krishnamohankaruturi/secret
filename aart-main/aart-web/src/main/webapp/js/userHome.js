function getURLParameter(name) {
   return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}
function showOverview(message) {
	$('.m-success').hide();
	if(message!=null && message.trim().length>0) {
		$("#ovMessage").html(message);
		$("#ovMessage").show();
	}else {
		$('#ovMessage').hide();
	}
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	
	$(".myProfileTabs").removeClass("active");
	$(".nav-link").removeClass("active");
	$(".overview").addClass("active");
	$("#overview").show();
}
function getMessageUserDetails() {
	$.ajax({
		url: 'getUserMessageList.htm',
		type: 'POST',
		dataType: 'json'
	}).done(function (data) { 
		var message="";
		var htmldata="";
		var newData=[];
		if($('#dynamicmessage').text()!='null'){
			var ckcontent=CKEDITOR.instances.messageContent.getData();				
			message=JSON.parse(unescape($('#dynamicmessage').text()));
			message.messageContent=ckcontent;
			newData.push(message);
			
			var persistanceData=_.reject(data,function(dataMessage){
					return dataMessage.messageId==message.messageId; 
				});
			$.each(persistanceData, function(index, messageData) {
				newData.push(messageData);
			});
		}else{
			newData=data;
		}
		if(newData && newData.length===0){
			$('#messages_exist').hide();
			$('#background_image_visible').hide();
			$('#background_image_visible_view').show();
		}else if(newData.length > 0){
			
			$('#background_image_visible_view').hide();
			$('#background_image_visible').hide();
			$('#messages_exist').show();
			
		}else{
			
			$('#background_image_visible_view').show();
		}
		for(x in newData)
    	{
    		var newFormattedDate = $.datepicker.formatDate('mm/dd/yy', new Date(newData[x].displayDate));
    		htmldata+="<tr style='height:20px '>";
    		htmldata+="<td style='width:90%'>"+newData[x].messageTitle+""+"&nbsp&nbsp"+"<a style='cursor: pointer' onclick='openPopUp("+newData[x].messageId+")' href='#'>Read More </a>";
	    	htmldata+="<td style='width:100px;'  align='center'><input type='hidden' id='messagecontent"+newData[x].messageId+"' value='"+newData[x].messageContent+"'/><input type='hidden' id='messagetitle"+newData[x].messageId+"' value='"+newData[x].messageTitle+"'/><input type='hidden' id='date"+newData[x].messageId+"' value='"+newFormattedDate+"'/></td>";
	    	htmldata+="<td style= align='bottom'>"+newFormattedDate+"</td>"; 	
    	
    		htmldata+="</tr><br>";
    	}
    	$("#messagelist").append(htmldata);
    	$('#messages_exist').css('pointer-events', 'auto');
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}
function openPopUp(mid)
{	
	$('#messagecontentparagraph').html($("#messagecontent"+mid).val());
	$('#messagetitleparagraph').html($("#messagetitle"+mid).val());
	$('#messagedateparagraph').html($("#date"+mid).val());
	$('#messagecontentparagraph a').css('pointer-events', 'auto');
	
	$('#notificationMessageWindow').dialog(
	{
		autoOpen : true,
		modal : true,
		title : 'Announcement',
		width : '600px',
		buttons : {
			"OK": {     
			     text: "Ok",
			                 click: function (event, ui) {
			                     $(this).dialog("close");                    
			                 },                 
			                 class: 'ui-custome-title'
			                  }
		},
		 open : function(event, ui) {
			   $('.ui-custome-title').attr('title','OK');
			   $(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
			  },
		create : function(event, ui) {
			var widget = $(this).dialog("widget");
		}
	});
}
function getSecurityAgreement() {
	/**
	* Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
	* Check if security agreement modal needs to be displayed on login for DLM users.
	*/
	$.ajax({
		url: 'getSecurityAgreementInfo.htm',
		data: { },
		type: 'POST',
		dataType: 'json'
	})
	.done(function (data) {
		var securityagreementdata = data.securityAgreement;
		if(securityagreementdata != null && securityagreementdata != undefined){
			$('#profileViewDiv').data('securityagreement', securityagreementdata);
			if(securityagreementdata.dlm == true && securityagreementdata.expired == true){
				var vv = $("#userHomeId").val();
				openProfileDialog(vv);
			} 
			
		}  
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}

/**
 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
 * Show security agreement link, upon clicking it use will see the security agreement form.
 * Only DLM users. 
 */
function showSecurityAgreement(message) {
	$('.m-success').hide();
	if(message!=null && message.trim().length>0) {
		$("#saMessage").html(message);
		$("#saMessage").show();
	}else {
		$('#saMessage').hide();
	}
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	
	$(".myProfileTabs").removeClass("active");
	
	$("#securityAgreement").show();
	$(".securityAgreement").addClass("active");
	 	
	var securityagreementdata = $('#profileViewDiv').data('securityagreement');
	if(securityagreementdata != null && securityagreementdata != undefined){
		$("#signerName").val(securityagreementdata.signerName);
		$("#schoolYearLbl").html(securityagreementdata.schoolYear);
		if(securityagreementdata.agreementElection == true){
			$("#agreementElection1").val(securityagreementdata.agreementElection);
			$('#agreementElection1').prop('checked', true);
		} else{
			$("#agreementElection2").val(securityagreementdata.agreementElection);
			$('#agreementElection2').prop('checked', true);
		}
		
	}

}

function showSecurity(message) {
	$('.m-success').hide();
	if(message!=null && message.trim().length>0) {
		$("#saMessage").html(message);
		$("#saMessage").show();
	}else {
		$('#saMessage').hide();
	}
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	
	$(".myProfileTabs").removeClass("active");
	
	$("#security").show();
	$(".security").addClass("active");
 	
}

/**
 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
 * Show renew/expiration link, only for DLM users
 */
function showRenewalExpiration(message) {
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	$('.m-success').hide();
	$(".myProfileTabs").removeClass("active");
	
	$("#renewexpiration").show();
	$(".renewexpiration").addClass("active");
	
	var securityagreementdata = $('#profileViewDiv').data('securityagreement');
		if(securityagreementdata != null && securityagreementdata != undefined){
			$("#schoolyear").text(securityagreementdata.schoolYear);
			var v = securityagreementdata.agreementElection;
			v = (v == true) ? 'Agreed To' : 'Did Not Agree To';
			$("#dagreementelection").text(v);
			if(securityagreementdata.agreementSignedDate != null)
				$("#agreementSignedDate").text(securityagreementdata.agreementSignedDate);
			else
				$("#agreementSignedDate").text("");
			$("#expiredate").text(securityagreementdata.expireDate);				
		}
}


function showEditDisplayName() {
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	$('.m-success').hide();
	$("#dispName").val($('#ovDisplayName').html());
	$("#editDisplayName").show();
	$(".editDisplayName").addClass('active');
}
function showChangePassword() {
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	$('.m-success').hide();
	$("#changePassForm").trigger("reset");
	$("#cpErrors").hide();
	$("#changePassword").show();
	$(".changePassword").addClass('active');
}

function showChangeDefaultOrgAndRole() {
	$(".m-item-container").hide();
	$(".linkBox").removeClass("linkBox");
	$('.m-success').hide();
	$("#changeDefaultOrgAndRole").show();
	$(".changeDefaultOrgAndRole").addClass('active');
	
	var rows = $('#changeDefaultOrgAndRoleTable tbody  tr').get();
	
	rows.sort(function(a, b) {
		 var A = $(a).children('td').eq(1).text().toUpperCase();
		 var B = $(b).children('td').eq(1).text().toUpperCase();
		
		 if(A < B) {
		   return -1;
		 }
		
		 if(A > B) {
		   return 1;
		 }
		
		 return 0;
	});
	
	$.each(rows, function(index, row) {
		$('#changeDefaultOrgAndRoleTable').children('tbody').append(row);
	});
	
}

function warnSignOutDialog() {
	$('#warnSignOutDiv').dialog({
		autoOpen: false,
		modal: true,
		resizable: false,
		width: "400px",
		title: "Sign Out",
		closeOnEscape: false,
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).removeClass("ui-icon-closethick").removeClass('ui-icon').hide();
		},
		open: function(event, ui) { $(".ui-dialog-titlebar-close").hide();} 
	}).dialog('open');	
}

function openProfileDialog(userId) {
	$('#profileViewDiv').dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: '98%',
        height: $(window).height()-80,
		title: "My Profile",
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
		},
		close: function(ev, ui) {				
		    $(this).html('');
		}
	}).load('templates/profileView.htm?&userId='+userId, function() {
		$.validator.addMethod("notEqual", function(value, element, param) {
			 return this.optional(element) || value != $(param).val();
			}, "This has to be different...");
		/**
		 * Regular expression Mtethod for Enhancing password security
		*/	
	$.validator.addMethod("pwcheckchars", function (value) {
		   return ( /[^a-zA-Z0-9]/.test(value) && /[a-z]/.test(value) && /\d/.test(value) && /[A-Z]/.test(value));
		     }, "The password must be 8 - 32 characters long and at least 1 special character, 1 upper case letter, 1 lower case letter and 1 number.");	
		
		/**
		* Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
		* Check if security agreement modal needs to be displayed on login for DLM users.
		*/
		var securityagreementdata = $('#profileViewDiv').data('securityagreement');
		if(securityagreementdata != null && securityagreementdata != undefined){
			if(securityagreementdata.dlm == true){
				$("#saLink").show();
				$("#reLink").show();			
				if(securityagreementdata.expired == true){
					showSecurityAgreement('');
				} 
			} else{
				$("#saLink").hide();
				$("#reLink").hide();
				$("#securityAgreement").hide();
				$("#renewexpiration").hide();
				$(".securityAgreement").removeClass("active");
				$(".renewexpiration").removeClass("active");
			}
		} else{
			$("#saLink").hide();
			$("#reLink").hide();
			$("#securityAgreement").hide();
			$("#renewexpiration").hide();
			$(".securityAgreement").removeClass("active");
			$(".renewexpiration").removeClass("active");
		}
			
		/**
		* Added for US-14985 
		*/
		$('#changePassForm').validate({
			rules : {
				curPass : {
					required: true
				},
				newPass : {
					required : true,
					pwcheckchars :true,
					notEqual : "#curPass",
					minlength:8,
					maxlength:32
					
				},
				conPass : {
					required : true,
					equalTo : '#newPass'
				}
			},
			errorClass: "error",
			messages : {
				curPass: {
					required: "Please enter current password"
				},
				newPass: {
		            required: "Please enter new password",
		            notEqual: "New Password cannot equal current password"
		        },
		        conPass: {
		            required: "Please re-enter password",
		            equalTo: "New and confirm passwords don't match"
		        },
			}
		
		});
		/**
		* Changed for US-14985 
		*/
		$('#savePass').off('click').on('click',function(e) {
			e.preventDefault();
		 	if($('#changePassForm').valid()) {
		    	$('#cpErrors').hide();
		    	$.ajax({
		    		url: 'templates/changepassword.htm',
	                data: {
	                    currentPassword: $('#curPass').val(),
	                    newPassword: $('#newPass').val()
	                },
	                dataType: 'json',
	                type: 'POST'
		    	}).done(function (data) { 
		    		if(data.success) {
                		warnSignOutDialog();
			    	} else{
                		$('#cpErrors').html(data.error);
                		$('#cpErrors').show();
                	}
		    	}).fail(function (jqXHR, textStatus, errorThrown) {
		    		$('#cpErrors').html("Password NOT updated: " + errorThrown);
            		$('#cpErrors').show();
		    	});
		    }
		    
		});
		$('#displayNameForm').validate({
			rules : {
				dispName : {
					required: true
				}
			},
			errorPlacement: function (error, element) {
	           $('#dnErrorCell').html(error);
			},
			errorClass: "displayNameErrors",
			messages : {
				dispName: {
					required: "Display Name is required"
				}
			}	
		});
		$('#saveDispName').off('click').on('click',function(e) {
			e.preventDefault();
		 	if($('#displayNameForm').valid()) {
		    	$('#dnErrors').hide();
		    	$.ajax({
		    		url: 'templates/editdisplayname.htm',
		            data: {
		                  displayName: $('#dispName').val()
		              },
		            dataType: 'json',
		            type: 'POST'
		    	}).done(function (data) { 
		    		if(data==true) {
	              		//$('#ovDisplayName').html($('#dispName').val());
	              		//$('#currentDisplayName').html($('#dispName').val());
	              		$('#ovDisplayName').html(escapeHtml($('#dispName').val()));
	                    $('#currentDisplayName').html(escapeHtml($('#dispName').val()));
	              		showOverview("Display Name Successfully Changed");
	              	} else {
	              		$('#dnErrors').html("Display Name NOT updated");
	              		$('#dnErrors').show();
	              	}
		    	}).fail(function (jqXHR, textStatus, errorThrown) {
		    		$('#dnErrors').html("Display Name NOT updated: " + errorThrown);
             		$('#dnErrors').show();
		    	});
		    }
		});
		
		$('#saveChangeDefaultOrgAndRole').off('click').on('click',function(e) {
			e.preventDefault();
			var defaultUserGroup = $("#defaultUserGroup").val();
			var defaultUserOrganization = $("#defaultUserOrganization").val();
			var defaultUserAssessmentProgram = $("#defaultUserAssessmentProgram").val();
			var orgName=$( "#defaultUserOrganization option:selected" ).text();
			var role=$( "#defaultUserGroup option:selected" ).text();
			
	      	$('#cdorErrors').hide();
	      	$.ajax({
	      		 url: 'templates/changeDefaultOrgAndRole.htm',
	              data: {
	            	  groupId: defaultUserGroup,
	            	  organizationId: defaultUserOrganization,
	            	  assessmentProgramId: defaultUserAssessmentProgram
	              },
	              dataType: 'json',
	              type: 'POST'
	      	}).done(function (data) {
	      		if(data==true) {
              		showOverview(fmtUserHomeMsg.sucessMyProfileChangeDefaultOrgAndRole);
              	} else {
              		$('#cdorErrors').html(fmtUserHomeMsg.errorMyProfileChangeDefaultOrgAndRole).show();
              	}
	      	}).fail(function (jqXHR, textStatus, errorThrown) { 
	      		$('#cdorErrors').html(fmtUserHomeMsg.errorMyProfileChangeDefaultOrgAndRole +" "+ errorThrown).show();
	      	});
		});


		$('#securityAgreementForm').validate({
			rules : {
				signerName : {
					required: true
				}
			},
			errorPlacement: function (error, element) {
		       $('#signerNameMessage').html(error);
		       $('#signerNameMessage').show();
			},
			errorClass: "signerNameErrors",
			messages : {
				signerName: {
					required: "Signer Name is required"
				}
			}	
		});

		/**
		 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
		 * Save user security agreement details for assessment program.
		 */
		$('#saveSecurityAgreement').off('click').on('click',function(e) {
			e.preventDefault();
		 	if($('#securityAgreementForm').valid()) {
		    	$('#saMessage').hide();
		    	$.ajax({
		    		url: 'saveSecurityAgreementInfo.htm',
	                data: {
	                	signerName: $('#signerName').val(),
	                	agreementElection: $('input[name=agreementElection]:checked').val()
	                },
	                dataType: 'json',
	                type: 'POST'
		    	}).done(function (data) { 
		    		if(data.success==true) {
                		//$('body, html').animate({scrollTop:0}, 'slow');
                		//$('#dsds', window.parent.document).scrollTop(0);
                		$('#saMessage').html('Successfully saved security agreement information.');
                		$('#saMessage').show();
                	} else {
                		$('#saMessage').html("Error while saving security agreement information.");
                		$('#saMessage').show();
                	}
                	getSecurityAgreement();
		    	}).fail(function (jqXHR, textStatus, errorThrown) { 
		    		$('#saMessage').html("Error while saving security agreement information."+ errorThrown);
            		$('#saMessage').show();
		    	});
		    }
		});
	}).dialog('open');
}
