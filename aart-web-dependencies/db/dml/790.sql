--dml/790.sql

-- there doesn't seem to be a better way to find writing content...for some reason.
update blueprintcriteriadescription
set criteriatext = 'All students are assessed in writing and must take one writing testlet. Select the appropriate linkage level for the writing testlet.'
where criteriatext ~* 'emergent|conventional writing';
