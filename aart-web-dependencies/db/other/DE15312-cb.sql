begin;
update cb.taskvariant
set scoringdata ='[{"correctResponse":{"value":"-2","score":"1.0000"},"acceptableResponses":[]}]'
where taskvariantid= 59204;
commit;