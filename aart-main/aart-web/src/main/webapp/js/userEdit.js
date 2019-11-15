/**
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US11240 : User Management ViewEdit User
 * Contains all javascript needful for edit user functionality.
 */

$(function() {
	
	$(function() {
		/**
		 * Initialize organization filter
		 */
		jQuery.validator.setDefaults({
			submitHandler: function() {		
			},
			errorPlacement: function(error, element) {
				if(element.hasClass('required') || element.attr('type') == 'file') {
					error.insertAfter(element.next());
				}
				else {
		    		error.insertAfter(element);
				}
		    }
		});
		
	});

	/*
    * This method is executed when user clicks on add organization button.
    */
	$('[id^=ebtn_addOrg]').off('click').on('click', function (e){
		callAddOrgClick(e, 'e');
	});
	
	/**
	 * This method is called when user clicks to remove an organization row.
	 */
	$('[id^=ebtn_removeOrg]').off('click').on('click', function (e){
		callRemoveOrgClick(e, 'e');		
	});
	
	$('[id^=default_role]').on('click', function (e){
		callDefaultRoleClick(e, 'e');
	});
	
	/**
	 * Clicks to save user data.
	 */
	$('#btn_editTop, #btn_editBottom').off('click').on('click', function (e) {
		callSaveUserClick(e, 'e', false);
	});
	
	$('#btn_editTop_Admin, #btn_editBottom_Admin').off('click').on('click', function (e) {
		callSaveUserClick(e, 'e', true);
	});
	
	$('#btn_editTop_Find, #btn_editBottom_Find').off('click').on('click', function (e) {
		callSaveClaimUserClick(e, 'e',true);
	});
	
});


  
  

