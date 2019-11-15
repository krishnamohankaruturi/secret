INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='ELA' and activeflag=true limit 1),'01';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='ELA' and activeflag=true limit 1),'51';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='ELA' and activeflag=true limit 1),'81';

INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='M' and activeflag=true limit 1),'02';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='M' and activeflag=true limit 1),'52';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='M' and activeflag=true limit 1),'82';
		
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='Sci' and activeflag=true limit 1),'03';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='Sci' and activeflag=true limit 1),'53';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='Sci' and activeflag=true limit 1),'83';

INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='SS' and activeflag=true limit 1),'04';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='SS' and activeflag=true limit 1),'54';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='SS' and activeflag=true limit 1),'84';

INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='ELA' and activeflag=true limit 1),'80';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='M' and activeflag=true limit 1),'80';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='Sci' and activeflag=true limit 1),'80';
INSERT INTO organizationcontentarea(organizationid, contentareaid, organizationcontentareacode)
    SELECT (select id from organization where organizationname like'Kansas%' and organizationtypeid=2 and activeflag=true limit 1),
		(select id from contentarea where abbreviatedname='SS' and activeflag=true limit 1),'80';

--update prevstatesubjectareaid
update roster set prevstatesubjectareaid=statesubjectareaid;