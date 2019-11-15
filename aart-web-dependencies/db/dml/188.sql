--DE6066

update public.specialcircumstance set ksdecode='01' where ksdecode='SC-01';
update public.specialcircumstance set ksdecode='02' where ksdecode='SC-012';
update public.specialcircumstance set ksdecode='03' where ksdecode='SC-03';
update public.specialcircumstance set ksdecode='04' where ksdecode='SC-04';
update public.specialcircumstance set ksdecode='05' where ksdecode='SC-05';
update public.specialcircumstance set ksdecode='06' where ksdecode='SC-06';
update public.specialcircumstance set ksdecode='07' where ksdecode='SC-07';
update public.specialcircumstance set ksdecode='08' where ksdecode='SC-08';
update public.specialcircumstance set ksdecode='16' where ksdecode='SC-16';
update public.specialcircumstance set ksdecode='20' where ksdecode='SC-20';
update public.specialcircumstance set ksdecode='24' where ksdecode='SC-24';
update public.specialcircumstance set ksdecode='25' where ksdecode='SC-25';
update public.specialcircumstance set ksdecode='26' where ksdecode='SC-26';
update public.specialcircumstance set ksdecode='31' where ksdecode='SC-31';
update public.specialcircumstance set ksdecode='32' where ksdecode='SC-32';
update public.specialcircumstance set ksdecode='33' where ksdecode='SC-33';
update public.specialcircumstance set ksdecode='34' where ksdecode='SC-34';
update public.specialcircumstance set ksdecode='36' where ksdecode='SC-36';
update public.specialcircumstance set ksdecode='37' where ksdecode='SC-37';
update public.specialcircumstance set ksdecode='39' where ksdecode='SC-39';
update public.specialcircumstance set ksdecode='41' where ksdecode='SC-41';
update public.specialcircumstance set ksdecode='42' where ksdecode='SC-42';
update public.specialcircumstance set ksdecode='00' where ksdecode like 'SC-%';
update public.specialcircumstance set ksdecode='00' where ksdecode in ('None','Call KSDE','N/A');
