---- Exiting enrollments since we have not received EXIT records from KSDE KIDS

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2851655;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2867753;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2779492;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2794586;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2794588;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2867722;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2859890;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2794587;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2889013;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-10', notes = 'inactivated because we have not received EXIT from KIDS'
where id = 2767220;

--These were inactivated earlier, so activated now
update studentstests set activeflag = true, modifieddate = now(), modifieduser = 12    
where id in (15977797, 16256939, 15947116, 15910739, 15977715, 16256811, 16006199, 15956257, 15987781, 16033794, 15907991, 16134570);

--Date 04/28

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-16 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2815828;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-03 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2851045;

--UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-31 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
--where activeflag is true and id = 2927743;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-08 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2772806;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-08 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2772804;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-13 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2867468;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-07 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2776088;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-27 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2872746;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-13 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2885182;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-08 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2885312;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-21 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2764608;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-03-31 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2764955;

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = '2017-04-05 06:00:00+00', notes = 'inactivated because we have not received EXIT from KIDS'
where activeflag is true and id = 2885517;

update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12    
where activeflag is true and id in (16235302, 15927424, 16329449, 16329453, 16281606, 15954174);
