--602.sql (For ddl/602.sql)

update authorities 
	set displayname = 'Create EP Announcements', 
	objecttype='Administrative-General', 
	authority='EP_MESSAGE_CREATOR'
where displayname ='DLM Announcements Creator';

update authorities 
	set displayname = 'View EP Announcements', 
	objecttype='Administrative-General', 
	authority='EP_MESSAGE_VIEWER' 
 where displayname ='DLM Announcements Viewer';
