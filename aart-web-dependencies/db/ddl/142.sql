INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('PERM_REPORT_PERF_ROSTER_VIEW', 'View Performance Roster Report','Report',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));
INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('PERM_REPORT_PERF_STUDENT_VIEW', 'View Performance Student Report','Report',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));
INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('PERM_REPORT_PERF_SCHOOL_VIEW', 'View Performance School Report','Report',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));
INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('PERM_REPORT_PERF_DISTRICT_VIEW', 'View Performance District Report','Report',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));
INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('PERM_REPORT_PERF_STATE_VIEW', 'View Performance State Report','Report',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));
