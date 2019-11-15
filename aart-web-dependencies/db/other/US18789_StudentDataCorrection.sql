-- Current Student IDs: Guadalupe Contreras - 000000001 , Correct Student IDs: 80001164
UPDATE student SET statestudentidentifier= '80001164', modifieduser=(select id from aartuser where username = 'cetesysadmin'), modifieddate= now() WHERE  id = 1315651;
-- Current Student IDs: Kenneth Baker - 000000003 , Correct Student IDs:1000329
UPDATE student SET statestudentidentifier= '1000329', modifieduser=(select id from aartuser where username = 'cetesysadmin'), modifieddate= now() WHERE id = 1315655;
-- Current Student IDs: Bennett Bosco - 000000002 , Correct Student IDs:000010102
UPDATE student SET statestudentidentifier= '000010102', modifieduser=(select id from aartuser where username = 'cetesysadmin'), modifieddate= now() WHERE id = 1315654;
-- Current Student IDs: Katherine Ruiz-Bernal - 000000000 , Correct Student IDs:840158
UPDATE student SET statestudentidentifier= '840158', modifieduser=(select id from aartuser where username = 'cetesysadmin'), modifieddate= now() WHERE id = 1315615;