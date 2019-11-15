--448.sql

update fieldspecification set rejectifempty = false where fieldname = 'levelDescription';

--Script bees changes for FCS
UPDATE SURVEYLABEL SET OPTIONAL='true' where labelnumber in ('Q49_1','Q49_2','Q49_3','Q49_4','Q49_5','Q49_6','Q41');
UPDATE SURVEYLABEL SET LABEL='Select the student''s primary sign system' WHERE LABELNUMBER='Q41';
update surveylabel set label='Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%". -A) Can point to, look at, or touch things in the immediate vicinity when asked (e.g., pictures, objects, body parts)' where labelnumber='Q49_1';
