


update itemstatistic o set assessmentprogramid=(select tp.assessmentprogramid from testingprogram tp, taskvariant tv where o.taskvariantid=tv.id and tv.testingprogramid=tp.id);
