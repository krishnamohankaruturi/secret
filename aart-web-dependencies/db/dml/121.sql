
--Fixes for CB publishing okay for R9 or R10 so checking in now
update public.contentgroup set htmlelementid= null where htmlelementid like 'mediaContent%';
update public.contentgroup set htmlelementid= null where htmlelementid like 'taskFoilContent%';
update public.contentgroup set htmlelementid= null where htmlelementid like 'taskStemContent%';
update public.contentgroup set htmlelementid= null where htmlelementid = 'stemContent';
 
update public.contentgroup set charindexstart = null, charindexend=null where charindexstart = 0 and charindexend=0;
 
update public.taskvariant set taskstem=regexp_replace(taskstem, '(math display="block")', 'math display="inline"') where taskstem ~* '(math display="block")';
update public.foil set foiltext=regexp_replace(foiltext, '(math display="block")', 'math display="inline"') where foiltext ~* '(math display="block")';
update public.stimulusvariant set stimuluscontent =regexp_replace(stimuluscontent, '(math display="block")', 'math display="inline"') where stimuluscontent ~* '(math display="block")';
