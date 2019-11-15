-- Incorrect - 9521241986, Correct - 9521241985, there is only one enrollment exists with incorrect statestudentid and all tests done using this enrollmentid, it is just required to swap statestudentids.

UPDATE student SET statestudentidentifier = '9521241985' WHERE id = 873155;
UPDATE student SET statestudentidentifier = '9521241986',activeflag = false WHERE id = 860584;

