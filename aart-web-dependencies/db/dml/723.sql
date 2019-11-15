--Email IDs for GRF grade change notification
INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'GRF File Data', 'GRF_FILE_DATA', 'GRF File Data', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'atsmgmt@mailinator.com,atsmgmt1@mailinator.com', 'GRF_EMAIL', 'GRF grade change emails.',
    (select id from  categorytype where typecode ='GRF_FILE_DATA' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));