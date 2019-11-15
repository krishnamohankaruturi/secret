
--CO Incorrect:2146981 Correct:7268924164 
UPDATE student SET statestudentidentifier = '7268924164', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1320948;

UPDATE student SET statestudentidentifier = '2146981', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1110742;
     
--CO Incorrect: 853424501 Correct:8534242501 

UPDATE student SET statestudentidentifier = '853424501', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1128386;

UPDATE student SET statestudentidentifier = '8534242501', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1319195;       
          
--CO Incorrect:61718709570 Correct:6171870957 
UPDATE student SET statestudentidentifier = '6171870957', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1237346;

--CO Incorrect: 2670324900 Correct: 2670324902 

 UPDATE student SET statestudentidentifier = '2670324900', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1128379;

UPDATE student SET statestudentidentifier = '2670324902', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1321285; 
          
--CO Incorrect: 3354242301 Correct:3354242302 
 UPDATE student SET statestudentidentifier = '3354242301', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1104391;

UPDATE student SET statestudentidentifier = '3354242302', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1274937;
          
--CO Incorrect: 3571731681 Correct:3571731686 
UPDATE student SET statestudentidentifier = '3571731686', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 1320175;

--MO Incorrect:9521241985 Correct:9521241986 
UPDATE student SET statestudentidentifier = '9521241986', modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id = 873155;

