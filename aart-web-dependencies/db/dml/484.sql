--dml/484.sql
update scoringassignment set ccqtestname = 'CCQTestName-'||id where  ccqtestname is null;
