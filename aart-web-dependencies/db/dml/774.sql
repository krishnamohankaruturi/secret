--DE18432: Predictive School Year for Kap
INSERT INTO public.appconfiguration 
(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate, assessmentprogramid)
select 'PredictiveSchoolYearKap', 'PredictiveSchoolYear', 'PredictiveSchoolYearKap', 
	'2016 - 2017', true, 12, now(), 12, now(), (select id from assessmentprogram where abbreviatedname='KAP')
where exists (select id from assessmentprogram where abbreviatedname='KAP');
