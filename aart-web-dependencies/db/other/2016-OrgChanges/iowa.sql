update usersorganizations
set organizationid = 69223
where organizationid = 58722;

update usersorganizations
set organizationid = 21262
where id = 21662;

update enrollment 
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where currentschoolyear = 2016 and activeflag = true and attendanceschoolid = 20528;

update enrollment
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where currentschoolyear = 2016 and activeflag = true and attendanceschoolid in (
  select id
  from organization
  where activeflag = true
  and displayidentifier = any(array
    ['613119 427','250027 409','905049 409','731197 118','970270 172','240355 409','320333 409','926768 218','792709 409','470504 209','550126 409','571053 800','550126 412','494041 409','341116 172','071044 172','571062 409','041071 405','041071 454','231082 118','041071 409','653978 409','821611 472','821611 965','821611 962','821611 961','041071 118','041071 415','871224 409','463060 409','574777 409','501332 418','851359 418','851359 409','591107 418','021431 209','411449 109','453029 418','881503 418','322124 412','562322 409','091719 409','771737 218','905049 421','991854 418','783645 418','103105 436','706975 427','181152 418','116219 409','103105 409','641968 418','653978 118','653978 209','076795 445','453029 427','574086 409','976039 454','504725 436','546012 509','140999 409','792709 436','952295 118','562322 127','622367 409','472376 409','041071 421','412403 209','771737 154','852466 409','462493 209','862502 418','862502 209','633375 114','946096 427','371967 109','680081 409','571053 478','120153 409','406867 118','832826 418','334774 418','881503 118','073042 218','613119 109','613119 209','946096 109','846990 418','976039 528','961638 440','216102 427','976039 535','680081 454','676987 418','453029 436','080729 436','041071 436','334869 454','181152 118','976039 553','231278 209','243168 427','184068 172','184068 418','905049 301','771737 688','771737 706','571053 550','041071 445','555868 172','546012 118','362772 209','120153 109','163691 109','334774 209','352781 427','652511 406','404775 109','404775 409','850225 961','775805 427','554778 109','534905 172','625013 481','914797 418','080729 454','395121 509','334869 436','776957 463','905049 490','423150 436','406867 445','135301 409','025328 409','495337 409','495337 109','695463 463','371967 418','942313 508','523141 454','976039 589','491965 411','136091 209','555868 409','975877 409','534446 418','546012 418','271093 436','771576 427','946096 209','946096 172','574086 445','926768 418','420009 427','556417 409','086561 427','240355 418','336591 109','060609 427','176633 409','176633 172','696651 109','781476 571','786750 109','786750 209','341116 454','976039 616','231278 218','563312 463','030135 427','336943 172','850472 425','633375 445','355922 418','961638 463','976039 634','905049 508','487029 427','417083 409','771737 923','061062 209','501332 109','851359 427','216102 409','060609 418','216102 436','174131 500','071044 509','785510 209','311863 607','571053 8001','789000 500'])
  and id in (
    select id from organization_children((select id from organization where displayidentifier = 'IA'))
  )
);

update enrollmentsrosters
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where activeflag = true and enrollmentid in (
  select id from enrollment where currentschoolyear = 2016 and activeflag = false and attendanceschoolid = 20528
);

update enrollmentsrosters
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where activeflag = true and enrollmentid in (
  select id from enrollment where currentschoolyear = 2016 and activeflag = false and attendanceschoolid in (
    select id
    from organization
    where activeflag = true
    and displayidentifier = any(
      array['613119 427','250027 409','905049 409','731197 118','970270 172','240355 409','320333 409','926768 218','792709 409','470504 209','550126 409','571053 800','550126 412','494041 409','341116 172','071044 172','571062 409','041071 405','041071 454','231082 118','041071 409','653978 409','821611 472','821611 965','821611 962','821611 961','041071 118','041071 415','871224 409','463060 409','574777 409','501332 418','851359 418','851359 409','591107 418','021431 209','411449 109','453029 418','881503 418','322124 412','562322 409','091719 409','771737 218','905049 421','991854 418','783645 418','103105 436','706975 427','181152 418','116219 409','103105 409','641968 418','653978 118','653978 209','076795 445','453029 427','574086 409','976039 454','504725 436','546012 509','140999 409','792709 436','952295 118','562322 127','622367 409','472376 409','041071 421','412403 209','771737 154','852466 409','462493 209','862502 418','862502 209','633375 114','946096 427','371967 109','680081 409','571053 478','120153 409','406867 118','832826 418','334774 418','881503 118','073042 218','613119 109','613119 209','946096 109','846990 418','976039 528','961638 440','216102 427','976039 535','680081 454','676987 418','453029 436','080729 436','041071 436','334869 454','181152 118','976039 553','231278 209','243168 427','184068 172','184068 418','905049 301','771737 688','771737 706','571053 550','041071 445','555868 172','546012 118','362772 209','120153 109','163691 109','334774 209','352781 427','652511 406','404775 109','404775 409','850225 961','775805 427','554778 109','534905 172','625013 481','914797 418','080729 454','395121 509','334869 436','776957 463','905049 490','423150 436','406867 445','135301 409','025328 409','495337 409','495337 109','695463 463','371967 418','942313 508','523141 454','976039 589','491965 411','136091 209','555868 409','975877 409','534446 418','546012 418','271093 436','771576 427','946096 209','946096 172','574086 445','926768 418','420009 427','556417 409','086561 427','240355 418','336591 109','060609 427','176633 409','176633 172','696651 109','781476 571','786750 109','786750 209','341116 454','976039 616','231278 218','563312 463','030135 427','336943 172','850472 425','633375 445','355922 418','961638 463','976039 634','905049 508','487029 427','417083 409','771737 923','061062 209','501332 109','851359 427','216102 409','060609 418','216102 436','174131 500','071044 509','785510 209','311863 607','571053 8001','789000 500']
    )
    and id in (
      select id from organization_children((select id from organization where displayidentifier = 'IA'))
    )
  )
);

update roster
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where currentschoolyear = 2016 and activeflag = true and attendanceschoolid = 20528;

update roster
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where currentschoolyear = 2016 and activeflag = true and attendanceschoolid in (
  select id
  from organization
  where activeflag = true
  and displayidentifier = any(
    array['613119 427','250027 409','905049 409','731197 118','970270 172','240355 409','320333 409','926768 218','792709 409','470504 209','550126 409','571053 800','550126 412','494041 409','341116 172','071044 172','571062 409','041071 405','041071 454','231082 118','041071 409','653978 409','821611 472','821611 965','821611 962','821611 961','041071 118','041071 415','871224 409','463060 409','574777 409','501332 418','851359 418','851359 409','591107 418','021431 209','411449 109','453029 418','881503 418','322124 412','562322 409','091719 409','771737 218','905049 421','991854 418','783645 418','103105 436','706975 427','181152 418','116219 409','103105 409','641968 418','653978 118','653978 209','076795 445','453029 427','574086 409','976039 454','504725 436','546012 509','140999 409','792709 436','952295 118','562322 127','622367 409','472376 409','041071 421','412403 209','771737 154','852466 409','462493 209','862502 418','862502 209','633375 114','946096 427','371967 109','680081 409','571053 478','120153 409','406867 118','832826 418','334774 418','881503 118','073042 218','613119 109','613119 209','946096 109','846990 418','976039 528','961638 440','216102 427','976039 535','680081 454','676987 418','453029 436','080729 436','041071 436','334869 454','181152 118','976039 553','231278 209','243168 427','184068 172','184068 418','905049 301','771737 688','771737 706','571053 550','041071 445','555868 172','546012 118','362772 209','120153 109','163691 109','334774 209','352781 427','652511 406','404775 109','404775 409','850225 961','775805 427','554778 109','534905 172','625013 481','914797 418','080729 454','395121 509','334869 436','776957 463','905049 490','423150 436','406867 445','135301 409','025328 409','495337 409','495337 109','695463 463','371967 418','942313 508','523141 454','976039 589','491965 411','136091 209','555868 409','975877 409','534446 418','546012 418','271093 436','771576 427','946096 209','946096 172','574086 445','926768 418','420009 427','556417 409','086561 427','240355 418','336591 109','060609 427','176633 409','176633 172','696651 109','781476 571','786750 109','786750 209','341116 454','976039 616','231278 218','563312 463','030135 427','336943 172','850472 425','633375 445','355922 418','961638 463','976039 634','905049 508','487029 427','417083 409','771737 923','061062 209','501332 109','851359 427','216102 409','060609 418','216102 436','174131 500','071044 509','785510 209','311863 607','571053 8001','789000 500']
  )
  and id in (
    select id from organization_children((select id from organization where displayidentifier = 'IA'))
  )
);

-- Central Elementary School
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 20528;

update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where activeflag = true
and displayidentifier = any(
  array['613119 427','250027 409','905049 409','731197 118','970270 172','240355 409','320333 409','926768 218','792709 409','470504 209','550126 409','571053 800','550126 412','494041 409','341116 172','071044 172','571062 409','041071 405','041071 454','231082 118','041071 409','653978 409','821611 472','821611 965','821611 962','821611 961','041071 118','041071 415','871224 409','463060 409','574777 409','501332 418','851359 418','851359 409','591107 418','021431 209','411449 109','453029 418','881503 418','322124 412','562322 409','091719 409','771737 218','905049 421','991854 418','783645 418','103105 436','706975 427','181152 418','116219 409','103105 409','641968 418','653978 118','653978 209','076795 445','453029 427','574086 409','976039 454','504725 436','546012 509','140999 409','792709 436','952295 118','562322 127','622367 409','472376 409','041071 421','412403 209','771737 154','852466 409','462493 209','862502 418','862502 209','633375 114','946096 427','371967 109','680081 409','571053 478','120153 409','406867 118','832826 418','334774 418','881503 118','073042 218','613119 109','613119 209','946096 109','846990 418','976039 528','961638 440','216102 427','976039 535','680081 454','676987 418','453029 436','080729 436','041071 436','334869 454','181152 118','976039 553','231278 209','243168 427','184068 172','184068 418','905049 301','771737 688','771737 706','571053 550','041071 445','555868 172','546012 118','362772 209','120153 109','163691 109','334774 209','352781 427','652511 406','404775 109','404775 409','850225 961','775805 427','554778 109','534905 172','625013 481','914797 418','080729 454','395121 509','334869 436','776957 463','905049 490','423150 436','406867 445','135301 409','025328 409','495337 409','495337 109','695463 463','371967 418','942313 508','523141 454','976039 589','491965 411','136091 209','555868 409','975877 409','534446 418','546012 418','271093 436','771576 427','946096 209','946096 172','574086 445','926768 418','420009 427','556417 409','086561 427','240355 418','336591 109','060609 427','176633 409','176633 172','696651 109','781476 571','786750 109','786750 209','341116 454','976039 616','231278 218','563312 463','030135 427','336943 172','850472 425','633375 445','355922 418','961638 463','976039 634','905049 508','487029 427','417083 409','771737 923','061062 209','501332 109','851359 427','216102 409','060609 418','216102 436','174131 500','071044 509','785510 209','311863 607','571053 8001','789000 500']
)
and id in (
  select id from organization_children((select id from organization where displayidentifier = 'IA'))
);

update organization
set displayidentifier = '175922 000',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 9976;

update organization
set organizationname = 'AHSTW Secondary School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '780441 172';

update organization
set organizationname = 'Waukon Middle School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '030135 218';

update organization
set organizationname = 'Ballard Middle School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '850472 209';

update organization
set organizationname = 'Ballard High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '850472 109';

update organization
set organizationname = 'Central Jr-Sr High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '221080 109';

update organization
set organizationname = 'Garner-Hayfield-Ventura Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '412403 418';

update organization
set organizationname = 'Garner-Hayfield-Ventura High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '412403 109';

update organization
set organizationname = 'Harlan Intermediate School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '832826 427';

update organization
set organizationname = 'Harlan High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '832826 109';

update organization
set displayidentifier = '944023 409',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '134023 409';

update organization
set organizationname = 'Murray Jr/Sr High',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '204572 172';

update organization
set organizationname = 'Nashua-Plainfield Intermediate School',
displayidentifier = '094599 427',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '194599 209';

update organization
set organizationname = 'North Butler Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '120153 418';

update organization
set organizationname = 'North Butler Jr/Sr High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '120153 172';

update organization
set organizationname = 'North Cedar Jr/Sr High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '163691 172';

update organization
set organizationname = 'West Central Charter High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '336943 109';

update organization
set organizationname = 'United South',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '086561 418';

update organization
set organizationname = 'Van Buren Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '896592 418';

update organization
set organizationname = 'Van Buren High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '896592 109';

update organization
set displayidentifier = '175922 409',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '355922 409';

update organization
set displayidentifier = '175922 209',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '355922 209';

update organization
set displayidentifier = '094599 409',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '194599 409';

update organization
set displayidentifier = '094599 172',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '194599 172';

update organization
set displayidentifier = '094599 000',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '194599 000';

update organization
set organizationname = 'North Linn Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '574777 427';

update organization
set displayidentifier = '311863 607',
organizationname = 'Hillcrest/Lawther Academy - Dubuque',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '574777 427';

update organization
set organizationname = 'Hillcrest/Lawther Academy - Maquoketa',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '574777 427';

update organization
set displayidentifier = '241917 418',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '431917 418';

update organization
set displayidentifier = '832151 418',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '052151 418';

update organization
set displayidentifier = '832151 109',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '052151 109';

update organization
set displayidentifier = '106762 409',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '096762 409';

update organization
set displayidentifier = '096273 418',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '192349 409';

update organization
set displayidentifier = '302556 427',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '742556 427';

update organization
set displayidentifier = '302556 427',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '742556 427';

update organization
set displayidentifier = '515163 109',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '545163 109';

update organization
set displayidentifier = '515163 427',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '545163 427';

update organization
set displayidentifier = '515163 209',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '545163 209';

update organization
set displayidentifier = '973348 418',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '753348 418';

update organization
set displayidentifier = '973348 209',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '753348 209';

update organization
set displayidentifier = '926700 409',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '446700 409';

update organization
set displayidentifier = '524271 414',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '924271 414';

update organization
set displayidentifier = '175922 409',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '355922 409';

update organization
set displayidentifier = '175922 209',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '355922 209';

update organization
set organizationname = 'West Central PK - 8 School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '336943 409';

update organization
set displayidentifier = '071044 509',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '071044 500';
