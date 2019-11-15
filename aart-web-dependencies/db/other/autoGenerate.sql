--for category--
Select categoryname,categorycode,categorydescription,categorytypeid from category where categorytypeid=14

Select 'INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid)
(Select '''||categoryname||''','''||categorycode||''','''||categorydescription
||''',catType.id from categoryType catType where catType.typecode = '''||catType.typeCode
||''' and not exists'
||' ( select 1 from category where categorycode = '''|| cat.categorycode ||''') );'
 from category cat,categorytype catType where categorytypeid=14 and cattype.id = cat.categorytypeid

 --

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid)
(Select 'State_Reading_Assess','State_Reading_Assess','Kansas State math assessment',catType.id
from categoryType catType where catType.typecode = 'KANSAS_ASSESSMENT_TAGS' and not exists
( select 1 from category where categorycode = 'State_Reading_Assess') ); 

--update field specification

select 'update fieldspecification set mappedname = '''||mappedname||''' where fieldname = '''||fieldname||' '' and mappedname <> '''||mappedname||''';'
 from fieldspecification where mappedname is not null; 
