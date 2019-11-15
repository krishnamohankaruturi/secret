-- Student::  890176828, Student is dual enrolled, but one of the locations is at the district level only. Please remove.

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to DE13452'
  where id = 2297235;