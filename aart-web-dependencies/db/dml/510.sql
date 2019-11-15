--dml/510.sql

update complexityband set minrange = 0, maxrange = 1.999 where bandname = 'FOUNDATIONAL' and contentareaid = (select id from contentarea where abbreviatedname = 'Sci');
update complexityband set minrange = 0, maxrange = 1.999 where bandname = 'BAND_1' and contentareaid  = (select id from contentarea where abbreviatedname = 'Sci');
update complexityband set minrange = 2, maxrange = 2.999 where bandname = 'BAND_2' and contentareaid  = (select id from contentarea where abbreviatedname = 'Sci');
update complexityband set minrange = 3, maxrange = 4 where bandname = 'BAND_3' and contentareaid  = (select id from contentarea where abbreviatedname = 'Sci');


--DE12555: EP: Production - Student has confirmed tests that have not been administered but they are not available in KITE Client         
update ititestsessionhistory set activeflag = false,
status=(SELECT c.id FROM category c,categorytype ct WHERE  c.categorytypeid = ct.id AND c.categorycode='exitclearunenrolled' AND ct.typecode = 'STUDENT_TEST_STATUS'),
modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
where testsessionid in (select testsessionid from studentstests where status=477 and activeflag is false and testsessionid in(select id from testsession where source='ITI'))
and status != (SELECT c.id FROM category c,categorytype ct WHERE  c.categorytypeid = ct.id AND c.categorycode='exitclearunenrolled' AND ct.typecode = 'STUDENT_TEST_STATUS');
