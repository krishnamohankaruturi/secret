
--empty
update fieldspecification set allowablevalues = '{0,1,2,3,4,5,6}'
where id = (
select id from fieldspecification 
	where fieldname='esolParticipationCode' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);

update fieldspecification set allowablevalues = '{0,2,3,C}'
where id = (
select id from fieldspecification 
	where fieldname='stateELAAssessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 

update fieldspecification set allowablevalues = '{0,2,C}'
where id = (
select id from fieldspecification 
	where fieldname='generalCTEAssessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 	
            
update fieldspecification set allowablevalues = '{0,2,3,C}'
where id = (
select id from fieldspecification 
	where fieldname='stateSciAssessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);            

update fieldspecification set allowablevalues = '{0,2,3,C}'
where id = (
select id from fieldspecification 
	where fieldname='stateHistGovAssessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);

update fieldspecification set allowablevalues = '{0,A,B,D,E,F,G,H,I,C}'
where id = (
select id from fieldspecification 
	where fieldname='endOfPathwaysAssessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);	

update fieldspecification set allowablevalues = '{0,2,3,C}', fieldlength=1, rejectifinvalid=true, showerror=true
where id = (
select id from fieldspecification 
	where fieldname ='stateMathAssess' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);