--EP
update taskvariant
set scoringdata ='[{"correctResponse":{"value":"-2","score":"1.0000"},"acceptableResponses":[]}]'
where externalid = 59204;

delete from testjson where testid in (
select t.id from test t
join testsection ts on (t.id = ts.testid)
join testsectionstaskvariants tstv on (tstv.testsectionid = ts.id)
where tstv.taskvariantid in (select id from taskvariant where externalid in(59204)));

--CB
update cb.taskvariant
set scoringdata ='[{"correctResponse":{"value":"-2","score":"1.0000"},"acceptableResponses":[]}]'
where taskvariantid= 59204;