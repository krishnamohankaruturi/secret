--dml/682.sql F638 - Interim Predictive School District Summary Reports

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, 
modifieddate, modifieduser)
VALUES ('VIEW_INT_PRED_SCHOOL_REPORT', 'View Interim Predictive School Report', 'Interim-View Results',
CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true,
CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, 
modifieddate, modifieduser)
VALUES ('VIEW_INT_PRED_DISTRICT_REPORT', 'View Interim Predictive District Report', 'Interim-View Results',
CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true,
CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));