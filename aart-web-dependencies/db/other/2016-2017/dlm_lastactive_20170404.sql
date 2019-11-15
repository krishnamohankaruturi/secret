update studentstestsections 
set lastactive=(select max(modifieddate) from studentsresponses where studentstestsectionsid=27405145),modifieddate=now()
where id=27405145;