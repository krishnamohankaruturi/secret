DELETE FROM studentstestsections WHERE studentstestid IN
    (SELECT id
     FROM studentstests
     WHERE testsessionid IN
         (select testsessionid from studenttrackerband where studenttrackerid in ( select id from studenttracker where createddate >= '2016-02-26' and createddate < '2016-02-27')));
 

DELETE FROM studentstests WHERE testsessionid IN
    (select testsessionid from studenttrackerband where studenttrackerid in ( select id from studenttracker where createddate >= '2016-02-26' and createddate < '2016-02-27'));
 

UPDATE studenttrackerband SET testsessionid=NULL where studenttrackerid in ( select id from studenttracker where createddate >= '2016-02-26' and createddate < '2016-02-27');

DELETE FROM testsession WHERE id in (select testsessionid from studenttrackerband where studenttrackerid in ( select id from studenttracker where createddate >= '2016-02-26' and createddate < '2016-02-27'));

delete from studenttrackerband where studenttrackerid in ( select id from studenttracker where createddate >= '2016-02-26' and createddate < '2016-02-27');

delete from studenttracker where createddate >= '2016-02-26' and createddate < '2016-02-27';


 