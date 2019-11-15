--dml/521.sql

INSERT INTO restrictionsauthorities(restrictionid, authorityid, isparent, ischild, isdifferential, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    (SELECT restrictionid, (select id from authorities where authority='PERM_STUDENTRECORD_CREATE'), 
	isparent, ischild, isdifferential, now(), createduser, activeflag, now(), modifieduser
  FROM restrictionsauthorities where authorityid = (select id from authorities where authority='PERM_ENRL_UPLOAD'));

INSERT INTO restrictionsauthorities(restrictionid, authorityid, isparent, ischild, isdifferential, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    (SELECT restrictionid, (select id from authorities where authority='PERM_STUDENTRECORD_MODIFY'), 
	isparent, ischild, isdifferential, now(), createduser, activeflag, now(), modifieduser
  FROM restrictionsauthorities where authorityid = (select id from authorities where authority='PERM_ENRL_UPLOAD'));
  