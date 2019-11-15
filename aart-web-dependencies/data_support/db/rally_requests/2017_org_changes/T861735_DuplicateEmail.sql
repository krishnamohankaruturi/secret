BEGIN;

 INSERT INTO  userorganizationsgroups ( groupid, status,userorganizationid,isdefault,createddate,createduser,modifieddate,modifieduser,activeflag) 
              select      (select id from groups where groupcode='TEA')  groupid,
                           2                                             status,
                           133357                                         userorganizationid,
			   true                                       isdefault,
			   now()                                      createddate,
			   12                                         createduser,
			   now()                                      modifieddate,
			   12                                         modifieduser, 
			   true                                      activeflag;
			   
			   
			   
INSERT INTO  userassessmentprogram (aartuserid,assessmentprogramid,activeflag,isdefault,createddate,createduser,modifieddate,modifieduser,userorganizationsgroupsid) 
              select       124111                                  aartuserid,
                           (select id from assessmentprogram where abbreviatedname ='DLM') assessmentprogramid,
                true                                          activeflag,
			   true                                          isdefault,
			   now()                                      createddate,
			   12                                         createduser,
			   now()                                      modifieddate,
			   12                                        modifieduser, 
			   (select id from userorganizationsgroups where userorganizationid=133357)       userorganizationsgroupsid;			   
			   
commit;
			   