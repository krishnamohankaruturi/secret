
--346.sql
DO
$BODY$ 
DECLARE TSRECORD RECORD;
BEGIN
	FOR TSRECORD IN (select lar.testspecificationid, max(lar.ranking) as minimumnumberofees
				from lmassessmentmodelrule lar 
				inner join testspecification ts on lar.testspecificationid=ts.id
			where ts.contentpool in ('MULTIEEOFG', 'MULTIEEOFC')
			group by lar.testspecificationid
			order by lar.testspecificationid)
	LOOP
		UPDATE testspecification SET minimumnumberofees=TSRECORD.minimumnumberofees
		WHERE id=TSRECORD.testspecificationid;           
	END LOOP;
END;
$BODY$;
