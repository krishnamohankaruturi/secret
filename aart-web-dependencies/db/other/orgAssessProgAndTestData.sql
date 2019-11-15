Begin;
INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) 
    (select id, (select id from assessmentprogram where programname = 'Formative') from organization) except (select organizationid, assessmentprogramid from orgassessmentprogram);
INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) 
    (select id, (select id from assessmentprogram where programname = 'CPASS') from organization) except (select organizationid, assessmentprogramid from orgassessmentprogram);

UPDATE test set testname = 'General Assessment' where testname = 'CPASS 1.1';

--TODO Assessments will no longer have test type and test subject.
--TODO when consolidating sql get rid of these inserts.

INSERT INTO test (assessmentid, subjectid, gradeid, testname, numitems) 
    (select (select id from assessment where assessmentname = 'Formative 1'), 
        (select id from category where categorycode = 'ELA Common Core'), 
        (select id from category where categorycode = 'GRADE_1'), '3a~', 25);
INSERT INTO test (assessmentid, subjectid, gradeid, testname, numitems) 
    (select (select id from assessment where assessmentname = 'Formative 1'), 
        (select id from category where categorycode = 'ELA Common Core'), 
        (select id from category where categorycode = 'GRADE_2'), 'Apple*', 25);
INSERT INTO test (assessmentid, subjectid, gradeid, testname, numitems) 
    (select (select id from assessment where assessmentname = 'Formative 1'), 
        (select id from category where categorycode = 'ELA Common Core'), 
        (select id from category where categorycode = 'GRADE_3'), '(Car$)', 25);
INSERT INTO test (assessmentid, subjectid, gradeid, testname, numitems) 
    (select (select id from assessment where assessmentname = 'Formative 1'), 
        (select id from category where categorycode = 'ELA Common Core'), 
        (select id from category where categorycode = 'GREADE_4'), '<>Test Me#', 25);
INSERT INTO test (assessmentid, subjectid, gradeid, testname, numitems) 
    (select (select id from assessment where assessmentname = 'Formative 1'),
        (select id from category where categorycode = 'ELA Common Core'), 
        (select id from category where categorycode = 'GREADE_5'), 'My-Test!', 25);
Commit;