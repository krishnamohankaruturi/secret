--dml/692.sql

-- Feature 626
update testcollection
set phasetype = 'EOY'
where id in (
    select tc.id
    from testcollection tc
    join contentarea ca on tc.contentareaid = ca.id
    join assessmentstestcollections atc on tc.id = atc.testcollectionid
    join assessment a on atc.assessmentid = a.id
    join testingprogram tp on a.testingprogramid = tp.id
    join assessmentprogram ap on tp.assessmentprogramid = ap.id
    where ca.abbreviatedname = 'SS'
    and ap.abbreviatedname = 'DLM'
    and tc.phasetype is distinct from 'EOY'
);

