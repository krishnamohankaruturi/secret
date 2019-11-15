-- DML for 525.sql

--see other folder US17669.sql

-- remove record improperly added in 516.sql for DE12775 by scriptbees
delete from profileitemattrnameattrcontainerviewoptions
where id in (
	select vo.id
	from profileitemattribute pia
	inner join profileitemattributenameattributecontainer pianac on pia.id = pianac.attributenameid
	inner join profileitemattributecontainer piac on pianac.attributecontainerid = piac.id
	inner join profileitemattrnameattrcontainerviewoptions vo on pianac.id = vo.pianacid
	where vo.assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'DLM')
	and piac.attributecontainer = 'Braille'
	and pia.attributename = 'assignedSupport'
);