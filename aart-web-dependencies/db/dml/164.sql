
--US13980
update testtype tt set assessmentid = (
select id from assessment a where assessmentname='General' and testingprogramid = (
select id from testingprogram tp where tp.programname='Summative' and assessmentprogramid=(
select id from assessmentprogram ap where ap.programname='Kansas Assessment Program')))
where assessmentid = (select id from assessment a where assessmentname='Kansas Assessment General' and testingprogramid = (
select id from testingprogram tp where tp.programname='Summative Testing Program' and assessmentprogramid=(
select id from assessmentprogram ap where ap.programname='Kansas Assessment Program'))
);