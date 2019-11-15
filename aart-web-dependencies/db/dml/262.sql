
--US14801 
insert into surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
values ((select id from surveylabel where labelnumber='Q16_1'), 15, 'Eligible individual (for Iowa only)', now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'), 91);
