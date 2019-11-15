-- change any student with an empty string to 'false' for directionsOnly in AK and KS

update studentprofileitemattributevalue
set selectedvalue = 'false'
where id in (
	select spiav.id
	from profileitemattribute pia
	inner join profileitemattributenameattributecontainer pianac on pia.id = pianac.attributenameid
	inner join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id
	inner join studentprofileitemattributevalue spiav on pianac.id = spiav.profileitemattributenameattributecontainerid
	inner join student s on spiav.studentid = s.id
	where s.stateid in (select id from organization where displayidentifier in ('AK', 'KS'))
	and piac.attributecontainer = 'Spoken'
	and pia.attributename = 'directionsOnly'
	and spiav.selectedvalue = ''
);