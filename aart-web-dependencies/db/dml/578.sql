--/dml/578.sql
-- update surveypagestatus to accomodate language page by enabling page 15.
update surveypagestatus set activeflag = true, 
    modifieduser = (select id from aartuser where username='cetesysadmin'), 
    modifieddate = now()
    where globalpagenum = 15 and activeflag is false;

-- The following are the result of adding Language page. 
--update 15th page iscompleted falg with 14th page iscompleted
update surveypagestatus sps1 set iscompleted = sps2.iscompleted from 
surveypagestatus sps2 where sps1.surveyid = sps2.surveyid and sps2.activeflag is true and sps2.globalpagenum=14 and sps1.globalpagenum=15;

--update 14th page iscompleted falg with 13th page iscompleted
update surveypagestatus sps1 set iscompleted = sps2.iscompleted from 
surveypagestatus sps2 where sps1.surveyid = sps2.surveyid and sps2.activeflag is true and sps2.globalpagenum=13 and sps1.globalpagenum=14;

--update 13th page iscompleted falg with 12th page iscompleted
update surveypagestatus sps1 set iscompleted = sps2.iscompleted from 
surveypagestatus sps2 where sps1.surveyid = sps2.surveyid and sps2.activeflag is true and sps2.globalpagenum=12 and sps1.globalpagenum=13;

--update 12th page iscompleted falg with 11th page iscompleted
update surveypagestatus sps1 set iscompleted = sps2.iscompleted from 
surveypagestatus sps2 where sps1.surveyid = sps2.surveyid and sps2.activeflag is true and sps2.globalpagenum=11 and sps1.globalpagenum=12;

-- update 11th page iscompleted falg to true as the questions in language page are non-mandatory
update surveypagestatus set iscompleted = true where globalpagenum = 11 and activeflag is true;
