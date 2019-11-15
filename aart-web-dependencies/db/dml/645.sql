--sif exit correction
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, 
modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where fieldname='exitDate';

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/exitReason' as mappedname
from fieldspecification where fieldname='exitWithdrawalType' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid =
(Select id from category where categorycode='EXIT_RECORD_TYPE'));

-- SIF field specification corrections.
update fieldspecification set fieldname ='exitWithdrawalDate'  where fieldname  ='exitDate';

update fieldspecification set allowablevalues='{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,98,99}' 
where fieldname='exitWithdrawalType' and allowablevalues is null;

update fieldspecification set fieldlength=2 
where fieldname='exitWithdrawalType' and fieldlength =10;

-- Claim user template update.
update activationemailtemplate set emailbody='<p>We are needing your assistance to update a user account in Kite Educator Portal.<br />
The following educator,[Educator display Name], has a district user who has identified 
them as an educator now working at their district and needing you to inactivate their account.</p>
<p>If this educator is no longer working in your district, we are needing you to remove them by inactivating their account. 
Please click the provided link to inactivate their current account.<br/>
[link]<br/></p>

<p>Thank you,<br/>
Kite Educator Portal Team.</p>' where emailsubject='KITE Educator Portal User Account Action Request';


update fieldspecification set allowablevalues='{1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,
1919,1921,1922,1923,1924,1925,
1926,1927,1928,1930,1931,3499,3502,3503,3504,3505,3508,3509,73060,73061,9999,CANCEL}' , fieldtype='String', 
formatregex= null, fieldlength=6
where fieldname='exitWithdrawalType' and id in (select fieldspecificationid from fieldspecificationsrecordtypes 
where recordtypeid = (select id from category where categorycode  ='UNENRL_XML_RECORD_TYPE') ) ;

update fieldspecification set allowablevalues='{1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,
1919,1921,1922,1923,1924,1925,
1926,1927,1928,1930,1931,3499,3502,3503,3504,3505,3508,3509,73060,73061,9999,CANCEL}' , fieldtype='String', 
formatregex= null, fieldlength=6
where fieldname='exitReason' and id in (select fieldspecificationid from fieldspecificationsrecordtypes 
where recordtypeid = (select id from category where categorycode  ='TEC_XML_RECORD_TYPE') ) ;

update fieldspecification set rejectifempty  = true, rejectifinvalid=true where id =(select id from fieldspecification  fs inner join fieldspecificationsrecordtypes  fsrt
on fsrt.fieldspecificationid = fs.id and fsrt.recordtypeid = (select id from category where  categorycode  = 'UNENRL_XML_RECORD_TYPE')
and fs.fieldname='exitWithdrawalType');