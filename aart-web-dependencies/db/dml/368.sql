
--De9175
update fieldspecification set mappedname = 'Grouping_Reading_1'
where id = (
select id from fieldspecification
                where mappedname='Grouping_ELA_1' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
                );            
update fieldspecification set mappedname = 'Grouping_Reading_2'
where id = (
select id from fieldspecification
                where mappedname='Grouping_ELA_2' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
                );  
 
update fieldspecification set mappedname = 'Grouping_History_1'
where id = (
select id from fieldspecification
                where mappedname='Grouping_History_Gov_1' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
                );            
update fieldspecification set mappedname = 'Grouping_History_2'
where id = (
select id from fieldspecification
                where mappedname='Grouping_History_Gov_2' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
                );
