DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;

BEGIN

FOR STUDENTTRACKERBAND_ROW IN 

select stb.* from studenttrackerband stb
inner join blueprintessentialelements bpee on stb.essentialelementid=bpee.essentialelementid
inner join blueprint bp on bp.id=bpee.blueprintid and bp.activeflag=true
inner join studenttracker st on st.id=stb.studenttrackerid and st.activeflag=true
where stb.activeflag=true and stb.testsessionid is null and bp.writingtestlet=true

LOOP

update studenttrackerband set activeflag=false where id=STUDENTTRACKERBAND_ROW.id;
update studenttracker set status='UNTRACKED' where id=STUDENTTRACKERBAND_ROW.studenttrackerid;

END LOOP;

END$$;