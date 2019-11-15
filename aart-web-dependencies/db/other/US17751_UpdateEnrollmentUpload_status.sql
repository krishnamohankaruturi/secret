--US17751 
update batchupload 
set status = 'Pending',
modifieddate = now()
where id = 33048 and stateid=69352 and createduser = 146987 and uploadtypeid=10 