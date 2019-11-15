truncate table taskvariantlearningmapnode;

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'ELA-10' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid
limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'ELA-123' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 100
limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'ELA-1104' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 200
limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'ELA-1105' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 300
limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'ELA-1106' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 400
limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'M-90' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 500
limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'M-91' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 600 limit 100);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'M-92' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id)
order by externalid offset 600 limit 100);

--insert an additional node for all.
insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'M-92' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id and nodecode='M-92')
);

insert into taskvariantlearningmapnode(taskvariantid,externalid,nodecode,originationcode)
(Select id as taskvariantid,id as externalid,'ELA-1104' as nodecode,
'AART' as originationcode from taskvariant
where not exists (select taskvariantid from taskvariantlearningmapnode where taskvariantid = taskvariant.id and nodecode='ELA-1104')
);