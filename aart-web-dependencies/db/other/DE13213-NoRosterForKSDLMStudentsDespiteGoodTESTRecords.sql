-- DE13213: EP Normal- Prod - No Roster for KS DLM Students Despite Good TEST Records
-- Reactivating the enrollmentsrosters records

UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    WHERE enrollmentid in (1829098, 1829093) AND activeflag IS false;

 -- Update count : 6
 
    
    