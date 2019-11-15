-- Move Social studies test to correct school
--4709142742
UPDATE studentstests set activeflag=true, enrollmentid = 2364910,testsessionid = 2268640, modifieduser = 12, modifieddate = now()
where id = 10549134;

UPDATE studentstests set activeflag = false, modifieduser = 12, modifieddate = now() where id=10667606;

--5405808776
UPDATE studentstests set activeflag=true, enrollmentid = 2057903,testsessionid = 2268948, modifieduser = 12, modifieddate = now()
where id = 10564911;

UPDATE studentstests set activeflag = false, modifieduser = 12, modifieddate = now() where id=10571027;

--6899831325
UPDATE studentstests set activeflag=true, enrollmentid = 2050963,testsessionid = 2268950, modifieduser = 12, modifieddate = now()
where id = 10571395;

UPDATE studentstests set activeflag = false, modifieduser = 12, modifieddate = now() where id=10567239;

--7154343826
UPDATE studentstests set activeflag=true, enrollmentid = 2107480,testsessionid = 2268834, modifieduser = 12, modifieddate = now()
where id = 10562823;

UPDATE studentstests set activeflag = false, modifieduser = 12, modifieddate = now() where id=10578785;

--7406898142
UPDATE studentstests set activeflag=true, enrollmentid = 2349799,testsessionid = 2267062, modifieduser = 12, modifieddate = now()
where id = 10547840;

UPDATE studentstests set activeflag = false, modifieduser = 12, modifieddate = now() where id=10658594;