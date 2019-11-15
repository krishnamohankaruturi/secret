
--related to DE5995 - set the start and end dates for schools in all the states so we can check the school year
update organization set schoolstartdate='09/02/2013', schoolenddate='05/30/2014' where organizationtypeid=2 and schoolstartdate is null and schoolenddate is null;