EP
------------------------------------
update taskvariant
set taskstem = replace(taskstem,'coorindate_plane_y=2x–0.5.svg','coorindate_plane_y=2x-0.5.svg')
where externalid=22358 and taskstem ilike '%coorindate_plane_y=2x–0.5.svg%';

update stimulusvariant
set stimuluscontent = replace(stimuluscontent,'coorindate_plane_y=2x–0.5.svg','coorindate_plane_y=2x-0.5.svg'),
stimulustitle = replace(stimulustitle,'coorindate_plane_y=2x–0.5_General','coorindate_plane_y=2x-0.5_General')
where externalid=91399;

delete from testjson where testid in (
select t.id from test t
join testsection ts on (t.id = ts.testid)
join testsectionstaskvariants tstv on (tstv.testsectionid = ts.id)
where tstv.taskvariantid in (select id from taskvariant where externalid in(22358)));

/* rename the file located at /media/media/15009/190198/to coorindate_plane_y=2x-0.5.svg */

CB
---------------------------------------
update cb.taskvariant
set taskstem = replace(taskstem,'coorindate_plane_y=2x–0.5.svg','coorindate_plane_y=2x-0.5.svg')
where taskvariantid=22358 and taskstem ilike '%coorindate_plane_y=2x–0.5.svg%';

update cb.mediavariant
set mediavariantcontent = replace(mediavariantcontent,'coorindate_plane_y=2x–0.5.svg','coorindate_plane_y=2x-0.5.svg'),
name = replace(name,'coorindate_plane_y=2x–0.5_General','coorindate_plane_y=2x-0.5_General')
where mediavariantid = 91399;
