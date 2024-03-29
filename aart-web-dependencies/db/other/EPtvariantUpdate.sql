-- update for DE15306 tolerance value
update taskvariant set 
scoringdata='{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"tolerance":"0.2","y":"5","x":"1"}}]}],"partialResponses":[{"index":0,"answer":{"tolerance":"0.2","y":"5","x":"1"},"score":"1.0000"}]}',
modifieddate=now()
where externalid=58275;

-- update for DE15317 tolerance value
update taskvariant set 
scoringdata='{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"tolerance":"0.2","y":"13","x":"7"}}]}],"partialResponses":[{"index":0,"answer":{"tolerance":"0.2","y":"13","x":"7"},"score":"1.0000"}]}',
modifieddate=now()
where externalid=59560;

-- update for DE15302 tolerance value
update taskvariant set 
scoringdata='{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"tolerance":"0.2","y":"0","x":"-3"}},{"index":1,"answer":{"tolerance":"0.2","y":"6","x":"-3"}},{"index":2,"answer":{"tolerance":"0.2","y":"3","x":"3"}}]}],"partialResponses":[{"index":0,"answer":{"tolerance":"0.2","y":"0","x":"-3"},"score":"0.3333"},{"index":1,"answer":{"tolerance":"0.2","y":"6","x":"-3"},"score":"0.3333"},{"index":2,"answer":{"tolerance":"0.2","y":"3","x":"3"},"score":"0.3333"}]}',
modifieddate=now()
where externalid=57140;

delete from testjson where testid in (
select t.id from test t
join testsection ts on (t.id = ts.testid)
join testsectionstaskvariants tstv on (tstv.testsectionid = ts.id)
where tstv.taskvariantid in (select id from taskvariant where externalid in(57140,59560,58275)));