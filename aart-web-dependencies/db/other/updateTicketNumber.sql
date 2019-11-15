
--print before the update.

SELECT sttc.*
FROM   studentstests sttc              ,
       assessmentstestcollections astcs,
       assessment ast                  ,
       testingprogram tp               ,
       assessmentprogram ap
WHERE  sttc.testcollectionid = astcs.testcollectionid
AND    ast.id                = astcs.assessmentid
AND    ast.testingprogramid  = tp.id
AND    tp.assessmentprogramid= ap.id
AND    ticketno    IS NOT NULL
AND    ap.programname        ='ARMM';

--do the update

update studentstests
 set ticketno = null,
 modifieddate = now(),
 modifieduser = (select id from aartuser
 where username = 'cetesysadmin' )
where id in
(Select sttc.id
FROM   studentstests sttc              ,
       assessmentstestcollections astcs,
       assessment ast                  ,
       testingprogram tp               ,
       assessmentprogram ap
WHERE  sttc.testcollectionid = astcs.testcollectionid
AND    ast.id                = astcs.assessmentid
AND    ast.testingprogramid  = tp.id
AND    tp.assessmentprogramid= ap.id
AND    ticketno    IS NOT NULL
AND    ap.programname        ='ARMM');

--print after the update.

SELECT sttc.*
FROM   studentstests sttc              ,
       assessmentstestcollections astcs,
       assessment ast                  ,
       testingprogram tp               ,
       assessmentprogram ap
WHERE  sttc.testcollectionid = astcs.testcollectionid
AND    ast.id                = astcs.assessmentid
AND    ast.testingprogramid  = tp.id
AND    tp.assessmentprogramid= ap.id
AND    ticketno    IS NOT NULL
AND    ap.programname        ='ARMM';
