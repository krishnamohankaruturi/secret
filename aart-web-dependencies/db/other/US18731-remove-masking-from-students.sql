update studentprofileitemattributevalue
set selectedvalue = 'false'
where id in (
  select spiav.id
  from student s
  inner join studentprofileitemattributevalue spiav on s.id = spiav.studentid
  inner join profileitemattributenameattributecontainer pianac on spiav.profileitemattributenameattributecontainerid = pianac.id
  inner join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id
  inner join profileitemattribute pia on pianac.attributenameid = pia.id
  where piac.attributecontainer = 'Masking'
  and pia.attributename = 'assignedSupport'
  and s.statestudentidentifier in ('1001654191', '1001654201')
);

update studentprofileitemattributevalue
set selectedvalue = 'false'
where id in (
  select spiav.id
  from student s
  inner join studentprofileitemattributevalue spiav on s.id = spiav.studentid
  inner join profileitemattributenameattributecontainer pianac on spiav.profileitemattributenameattributecontainerid = pianac.id
  inner join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id
  inner join profileitemattribute pia on pianac.attributenameid = pia.id
  where piac.attributecontainer = 'Masking'
  and pia.attributename = 'activateByDefault'
  and s.statestudentidentifier in ('1001654191', '1001654201')
);

update studentprofileitemattributevalue
set selectedvalue = ''
where id in (
  select spiav.id
  from student s
  inner join studentprofileitemattributevalue spiav on s.id = spiav.studentid
  inner join profileitemattributenameattributecontainer pianac on spiav.profileitemattributenameattributecontainerid = pianac.id
  inner join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id
  inner join profileitemattribute pia on pianac.attributenameid = pia.id
  where piac.attributecontainer = 'Masking'
  and pia.attributename = 'MaskingType'
  and s.statestudentidentifier in ('1001654191', '1001654201')
);