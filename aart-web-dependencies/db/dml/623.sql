--DE14916 fix references to wrong General Knowledge and Skills contentarea

update reportassessmentprogram  set subjectid=(select id from contentarea where abbreviatedname='GKS') 
where id = ANY(
				select id from reportassessmentprogram rap 
				where reporttypeid=(select id from category where categorycode='ALL_ST_RPT_FOR_ST') 
				and assessmentprogramid=(select id from assessmentprogram where abbreviatedname='CPASS') 
				and subjectid=(select id from contentarea where abbreviatedname='G')
			)