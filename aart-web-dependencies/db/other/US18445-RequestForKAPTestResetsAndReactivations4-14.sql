-- US18445: EP: Prod - Request for KAP Test Resets and Reactivations 4/14

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9789574, 9574659, 13041171, 9847290, 9847339, 9695236, 12686811, 12735365, 9938304, 9874050,9938476, 12149112, 13166736, 10357617, 10366183, 10366165, 10366230);

update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9789574, 9574659, 13041171, 9847290, 9847339, 9695236, 12686811, 12735365, 9938304, 9874050,9938476, 12149112, 13166736, 10357617, 10366183, 10366165, 10366230);
 

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12673785,12783607, 12489261, 13072907, 13086322, 13301222, 13071826, 12463884, 9931233, 13214593, 13269000, 9931224, 9858076,
          13046700, 12497867, 13257151, 12565815, 13250237, 12565821, 12403067, 9695232, 13229614, 12742870, 12708311, 13174288, 13206531, 12507395, 13177738, 13205547, 
          12499723, 13169447, 13206597, 12507419, 12221478, 13172962, 13230245, 12318849, 12595645, 12507526, 12298477, 12345675, 10366178, 12270125, 12300929, 10366161,
          12300951, 12318390, 10366226);



update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12673785,12783607, 12489261, 13072907, 13086322, 13301222, 13071826, 12463884, 9931233, 13214593, 13269000, 9931224, 9858076,
          13046700, 12497867, 13257151, 12565815, 13250237, 12565821, 12403067, 9695232, 13229614, 12742870, 12708311, 13174288, 13206531, 12507395, 13177738, 13205547, 
          12499723, 13169447, 13206597, 12507419, 12221478, 13172962, 13230245, 12318849, 12595645, 12507526, 12298477, 12345675, 10366178, 12270125, 12300929, 10366161,
          12300951, 12318390, 10366226);



update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12673785,12783607, 12489261, 13072907, 13086322, 13301222, 13071826, 12463884, 9931233, 13214593, 13269000, 9931224, 9858076,
          13046700, 12497867, 13257151, 12565815, 13250237, 12565821, 12403067, 9695232, 13229614, 12742870, 12708311, 13174288, 13206531, 12507395, 13177738, 13205547, 
          12499723, 13169447, 13206597, 12507419, 12221478, 13172962, 13230245, 12318849, 12595645, 12507526, 12298477, 12345675, 10366178, 12270125, 12300929, 10366161,
          12300951, 12318390, 10366226);