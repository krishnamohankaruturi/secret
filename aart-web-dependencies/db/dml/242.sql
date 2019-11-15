-- 242.sql

insert into category (categoryname, categorycode, categorydescription, categorytypeid, 
originationcode, createddate, activeflag) values
('Parent Report','PARENT_REPORT', 'Parent Report', (select id from categorytype where typecode= 'REPORT_CATEGORY') , 'AART_ORIG_CODE', current_timestamp, true);



DO
$BODY$
BEGIN
 IF ((SELECT count(id) FROM authorities where authority='PERM_REPORT_PERF_PARENT_VIEW' and activeflag=true) = 0) THEN
  RAISE NOTICE  '%', 'Permission with PERM_REPORT_PERF_PARENT_VIEW not exists. Inserting';
  INSERT INTO authorities(
       id, authority, displayname, objecttype, createddate, createduser, 
       activeflag, modifieddate, modifieduser)
      VALUES (nextval('authorities_id_seq'),'PERM_REPORT_PERF_PARENT_VIEW','View Parent Report',
    'Reports-Performance Reports', now(),(Select id from aartuser where username='cetesysadmin'), 
    TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
 ELSE 
  RAISE NOTICE  '%', 'Permission with PERM_REPORT_PERF_PARENT_VIEW exists. Skipping';
 END IF;
END;
$BODY$;