
--US15407
update roster set currentSchoolYear = (case when createddate >= '2014-08-01' then 2015
                                        When createddate < '2014-08-01' and createddate >= '2013-08-01' Then 2014
                                        When createddate < '2013-08-01' and createddate >= '2012-08-01' Then 2013
					When createddate < '2012-08-01' and createddate >= '2011-08-01' Then 2012
					When createddate < '2011-08-01' and createddate >= '2010-08-01' Then 2011
					When createddate < '2010-08-01' and createddate >= '2009-08-01' Then 2010
					When createddate < '2009-08-01' and createddate >= '2008-08-01' Then 2009
					When createddate < '2008-08-01' and createddate >= '2007-08-01' Then 2008
					When createddate < '2007-08-01' and createddate >= '2006-08-01' Then 2007
                                        when createddate is null then null
                                        end) 
 where currentSchoolYear is null;
 
