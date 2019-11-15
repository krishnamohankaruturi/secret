
--/437.sql 
-- DE10420
delete from surveylabelprerequisite where surveylabelid in (
					Select id from surveylabel where labelnumber IN ('Q34_1','Q34_2','Q34_3'));
					
UPDATE SURVEYLABEL SET ACTIVEFLAG=false WHERE LABELNUMBER IN ('Q34_1','Q34_2','Q34_3');
 