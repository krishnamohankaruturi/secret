 /**
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US14307 : User Management: Add Users Manually
 * Contains all javascript needful for add user functionality.
 */

$(function() {

    
    jQuery.validator.setDefaults({
        submitHandler: function() {
        },
        errorPlacement: function(error, element) {
            if (element.hasClass('required') || element.attr('type') == 'file') {
                error.insertAfter(element.next());
            } 
            else {
                error.insertAfter(element);
            }
        }
    });
    
    var firstRow = $('[id^=orgRow]')[0];
    $(firstRow).data('rowindex', 1);
    $('#orgTable').data('rowindex', 1);
    
    $('#btn_removeOrg_1').hide();

	/*
    * This method is executed when user clicks on add organization button.
    */
	$('[id^=btn_addOrg]').off('click').on('click', function (e){
		callAddOrgClick(e, '');
	});
	
	/**
	 * This method is called when user clicks to remove an organization row.
	 */
	$('[id^=btn_removeOrg]').off('click').on('click', function (e){
		callRemoveOrgClick(e, '');		
	});
	
	$('[id^=default_role]').on('click', function (e){
		callDefaultRoleClick(e,''); 
	});
	
	/**
	 * Clicks to save user data.
	 */
	// To prevent double Click
	function isDoubleClicked(element) {
	    //if already clicked return TRUE to indicate this click is not allowed
	    if (element.data("isclicked")) return true;

	    //mark as clicked for 1 second
	    element.data("isclicked", true);
	    setTimeout(function () {
	        element.removeData("isclicked");
	    }, 1000);

	    //return FALSE to indicate this click was allowed
	    return false;
	}
	
	$('#btn_bottomSaveUser, #btn_topSaveUser').on("click", function (e) {
	    if (isDoubleClicked($(this))) return;
	    callSaveUserClick(e,'', false);
	});
	
});


  
  

