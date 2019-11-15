DO
$BODY$
DECLARE
    TVROW RECORD;
    report_tasklayout_formatid bigint;
BEGIN
                report_tasklayout_formatid = (select tlf.id from tasklayoutformat tlf where tlf.formatcode='letters' and originationcode= 'CB' LIMIT 1);
                IF (report_tasklayout_formatid IS NOT NULL AND report_tasklayout_formatid > 0)  THEN
                                FOR TVROW IN (
                                                SELECT tv.id
                                                FROM taskvariant tv
                                                INNER JOIN tasktype tt ON tv.tasktypeid = tt.id
                                                WHERE tv.reporttasklayoutformatid IS NULL AND tt.code NOT IN ('ITP', 'DD-G', 'SD', 'ER', 'CR', 'ML')
                                ) LOOP
                                
                                                RAISE NOTICE  '%', TVROW.id;                                       
                                                UPDATE taskvariant SET reporttasklayoutformatid=report_tasklayout_formatid where id=TVROW.id;
                                                RAISE NOTICE  '%', 'Updated taskvariant to set reporttasklayoutformatid';
                                                   
                                 END LOOP;
                ELSE
                                RAISE NOTICE  '%', 'Not able to update taskvariant as Letters reporttasklayoutformat doesnot exist';
                END IF;
END;
$BODY$;
