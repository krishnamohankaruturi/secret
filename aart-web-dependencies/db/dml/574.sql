-- DML:574
--US18979 & US18904
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Eligible Individual',  'EI', 'Eligible Individual', (select id from categorytype where typecode = 'PRIMARY_DISABILITY_CODES'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Declined to Answer',  'DA', 'Declined to Answer', (select id from categorytype where typecode = 'PRIMARY_DISABILITY_CODES'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));

--US18903
update fieldspecification set allowablevalues='{AM,DB,DD,ED,HI,LD,MD,MR,ID,OH,OI,SL,TB,VI,ND,WD,EI,DA}' ,
rejectifempty=true  where fieldname like 'primaryDisabilityCode' and mappedname like 'Primary_Disability_Code'