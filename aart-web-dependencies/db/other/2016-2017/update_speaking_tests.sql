update studentsresponses set taskvariantid=439366 where studentstestsectionsid=25390265 and taskvariantid=439367;
update studentsresponses set taskvariantid=439381 where studentstestsectionsid=25496540 and taskvariantid=439382;
update ccqscoreitem set activeflag=false where  taskvariantid in (439359,439357,439367);
update ccqscoreitem set activeflag=false where  taskvariantid in (439374,439372,439382,439378);