--dml/37.SQL 

INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'activationtemplateaudittrailhistory', 'Activation Email Template Audit', true, now(), 12,now(), 12);
	    
INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'firstcontactsurveyaudithistory', 'First Contact Survey Changes', true, now(), 12,now(), 12);
	    
INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'organizationmanagementaudit', 'Organization Changes', true, now(), 12,now(), 12);
	    
INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'organizationaudittrailhistory', 'Organization Name Changes', true, now(), 12,now(), 12);
	    
INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'rosteraudittrailhistory', 'Roster Changes', true, now(), 12,now(), 12);
	    
INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'studentaudittrailhistory', 'Student Changes', true, now(), 12,now(), 12);
	    
INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'studentpnpsaudithistory', 'Student PNP Changes', true, now(), 12,now(), 12);

INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'useraudittrailhistory', 'User Account Changes', true, now(), 12,now(), 12);

INSERT INTO audittype(auditname, dispalyname, activeflag, createddate, createduser,modifieddate, modifieduser)
	    VALUES ( 'roleaudittrailhistory', 'User Role Audit', true, now(), 12,now(), 12);
	    