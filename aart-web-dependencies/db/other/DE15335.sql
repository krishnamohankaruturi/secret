EP
------------------------------------
update taskvariant
set taskstem = replace(taskstem,'coordinate_plane_x-3squared–2.svg','coordinate_plane_x-3squared-2.svg')
where externalid=59566 and taskstem ilike '%coordinate_plane_x-3squared–2.svg%';
 
update stimulusvariant
set stimuluscontent = replace(stimuluscontent,'coordinate_plane_x-3squared–2.svg','coordinate_plane_x-3squared-2.svg'),
stimulustitle = replace(stimulustitle,'coordinate_plane_x-3squared–2_General','coordinate_plane_x-3squared-2_General')
where externalid=190198;

delete from testjson where testid in (
select t.id from test t
join testsection ts on (t.id = ts.testid)
join testsectionstaskvariants tstv on (tstv.testsectionid = ts.id)
where tstv.taskvariantid in (select id from taskvariant where externalid = 59566));


/* rename the file located at /media/media/15009/190198/to coordinate_plane_x-3squared-2.svg */

CB
---------------------------------------
update cb.taskvariant
set taskstem = replace(taskstem,'coordinate_plane_x-3squared–2.svg','coordinate_plane_x-3squared-2.svg')
where taskvariantid=59566 and taskstem ilike '%coordinate_plane_x-3squared–2.svg%';


update cb.mediavariant
set mediavariantcontent = replace(mediavariantcontent,'coordinate_plane_x-3squared–2.svg','coordinate_plane_x-3squared-2.svg'),
name = replace(name,'coordinate_plane_x-3squared–2_General','coordinate_plane_x-3squared-2_General')
where mediavariantid = 190198;
