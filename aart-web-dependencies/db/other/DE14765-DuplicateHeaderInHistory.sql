--DE14765: Duplicate header in History
-- For studentid = 1032361 and testcollectionid = 2521 and wrong ee = 'ELA.EE.RL.4.5 - Identify elements that are characteristic of stories.'
UPDATE ititestsessionhistory set essentialelement = 'ELA.EE.RL.4.3 - Use details from the text to describe characters in the story.',
       leveldescription = 'identify characters, setting, and major events', essentialelementid = 4733, modifieduser = 12, modifieddate = now()
       where id = 581725 and studentid = 1032361 and testcollectionid = 2521;



-- For studentid = 1006498 and testcollectionid = 3365 and wrong ee = 'M.EE.HS.N.CN.2.c - Solve real-world problems involving multiplication of decimals and whole numbers, using models when needed.'
UPDATE ititestsessionhistory set activeflag = false, modifieduser = 12, modifieddate = now(),
     status = (select id from category where categorycode = 'iticancel' and categorytypeid =(select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))
     where id = 581389 and studentid = 1006498 and testcollectionid = 3365;


--For studentid =142952   and testcollectionid =1456 and wrong ee ='M.EE.5.NBT.3 - Compare whole numbers up to 100 using symbols (<, >, =).'
UPDATE ititestsessionhistory set essentialelement ='M.EE.5.NBT.6-7 - Illustrate the concept of division using fair and equal shares.',
     leveldescription = 'recognize equal, same number of, model equal set', essentialelementid = 23207, modifieduser = 12, modifieddate = now()
       where id =562664  and studentid =142952  and testcollectionid =1456;


--For studentid = 143105  and testcollectionid =3309 and wrong ee ='M.EE.4.NF.3 - Differentiate between whole and half.'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RI.4.9 - Compare details presented in two texts on the same topic.',
     leveldescription = 'understand object names', essentialelementid = 4875, modifieduser = 12, modifieddate = now()
       where id = 562241 and studentid =143105  and testcollectionid =3309;


--For studentid =915684 and testcollectionid =2623 and wrong ee ='ELA.EE.RL.4.3 - Use details from the text to describe characters in the story.'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RL.4.1 - Use details from the text to recount what the text says.',
     leveldescription = 'identify character actions', essentialelementid = 4732, modifieduser = 12, modifieddate = now()
       where id =562280  and studentid =915684  and testcollectionid =2623;


--For studentid =1207545   and testcollectionid =2342 and wrong ee ='ELA.EE.RI.11-12.2 - Determine the central idea of a text; recount the text.'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RI.11-12.3 - Determine how individuals, ideas, or events change over the course of the text.',
     leveldescription = 'identify how details change across the text', essentialelementid = 24703, modifieduser = 12, modifieddate = now()
       where id =581585 and studentid =1207545  and testcollectionid =2342;


--For studentid = 1206233  and testcollectionid =2300 and wrong ee =' ELA.EE.EW.4.DP - Emergent Writing'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.EW.4.IP - Emergent Writing', linkagelevel = 'Initial Precursor',
     leveldescription = 'understands names and that letters make words', essentialelementid = 30180, modifieduser = 12, modifieddate = now()
       where id =582896  and studentid = 1206233 and testcollectionid =2300;



--For studentid =1328265   and testcollectionid =2358 and wrong ee ='ELA.EE.L.3.5.c - Identify words that describe personal emotional states.'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RI.3.4 - Determine words and phrases that complete literal sentences in a text.',
     leveldescription = 'understand names/words for absent objects/people', essentialelementid = 23048, modifieduser = 12, modifieddate = now()
       where id =561468  and studentid =1328265  and testcollectionid =2358;


--For studentid =855431   and testcollectionid =2716 and wrong ee ='M.EE.7.G.4 - Determine the perimeter of a rectangle by adding the measures of the sides.'
UPDATE ititestsessionhistory set essentialelement ='M.EE.7.NS.2.c-d - Express a fraction with a denominator of 10 as a decimal.',
     leveldescription = 'recognize whole on a set model', essentialelementid = 4832, modifieduser = 12, modifieddate = now()
       where id =553545  and studentid =855431  and testcollectionid =2716;


--For studentid =873156   and testcollectionid =2318 and wrong ee ='ELA.EE.RI.6.2 - Determine the main idea of a passage and details or facts related to it.'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RL.6.5 - Determine the structure of a text (e.g., story, poem, or drama).',
     leveldescription = 'identify familiar people, objects, places, events', essentialelementid =23532, modifieduser = 12, modifieddate = now()
       where id =544554  and studentid =873156  and testcollectionid =2318;



         
--For studentid = 1325018  and testcollectionid =3289 and wrong ee ='ELA.EE.L.3.5.c - Identify words that describe personal emotional states.'
-- Kid for correct ee = 'ELA.EE.RI.3.4 - Determine words and phrases that complete literal sentences in a text.' is assigned and student test status is unused
-- And incorrect ee plan is saved so inactivating the incorrect plan
UPDATE ititestsessionhistory set activeflag = false, modifieduser = 12, modifieddate = now(),
      status = (select id from category where categorycode = 'iticancel' and categorytypeid =(select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))      
       where id =544878  and studentid =1325018  and testcollectionid = 3289;



--For studentid =885882 and testcollectionid =2897 and wrong ee ='ELA.EE.RL.5.2 - Identify the central idea or theme of a story, drama or poem.'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RL.5.1 - Identify words in the text to answer a question about explicit information.',   
     leveldescription = 'identify characters, setting, and major events', essentialelementid = 23410, modifieduser = 12, modifieddate = now(),
      linkagelevel = 'Proximal Precursor'
       where id = 559675 and studentid =885882  and testcollectionid =2897;


--For studentid =32031  and testcollectionid =1962 and wrong ee ='ELA.EE.EW.8.DP - Emergent Writing'
UPDATE ititestsessionhistory set essentialelement ='ELA.EE.RI.8.3 - Recount events in the order they were presented in the text.',
     leveldescription = 'identify concrete detail in informational text', essentialelementid = 23600, modifieduser = 12, modifieddate = now()
       where id = 545077 and studentid =32031  and testcollectionid =1962;
       
       
-- Updating incorrect claims and conceptualareas
update ititestsessionhistory set claim = 'ELA.C1  Students can comprehend text in increasingly complex ways.', 
	conceptualarea='ELA.C1.3  Integrate ideas and information from text'
	where id = 545077;


update ititestsessionhistory set claim ='ELA.C1  Students can comprehend text in increasingly complex ways.',
      conceptualarea='ELA.C1.1  Determine critical elements of text'
      where id = 559675;


update ititestsessionhistory set claim ='ELA.C1  Students can comprehend text in increasingly complex ways.',
      conceptualarea='ELA.C1.3  Integrate ideas and information from text'
      where id = 544554;

update ititestsessionhistory set claim ='M.C1  NUMBER SENSE:  Students demonstrate increasingly complex understanding of number sense.',
      conceptualarea='M.C1.1  Understand number structures (counting, place value, fraction)'
      where id = 553545;


update ititestsessionhistory set claim ='ELA.C1  Students can comprehend text in increasingly complex ways.',
      conceptualarea='ELA.C1.3  Integrate ideas and information from text'
      where id = 581585;

update ititestsessionhistory set claim ='ELA.C1  Students can comprehend text in increasingly complex ways.',
      conceptualarea='ELA.C1.3  Integrate ideas and information from text'
      where id = 562241;

update ititestsessionhistory set claim ='M.C1  NUMBER SENSE:  Students demonstrate increasingly complex understanding of number sense.',
      conceptualarea='M.C1.3  Calculate accurately and efficiently using simple arithmetic operations'
      where id = 562664;
      
      
update ititestsessionhistory set claim = 'ELA.C1  Students can comprehend text in increasingly complex ways.', 
	conceptualarea='ELA.C1.3  Integrate ideas and information from text'
	where id = 545077;


update ititestsessionhistory set claim ='ELA.C1  Students can comprehend text in increasingly complex ways.',
      conceptualarea='ELA.C1.1  Determine critical elements of text'
      where id = 559675;       
       