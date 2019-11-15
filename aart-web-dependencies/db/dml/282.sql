

--US15034

--assessment program abbreviations
update assessmentprogram set abbreviatedname='DLM' where programname='Dynamic Learning Maps' and abbreviatedname is null;
update assessmentprogram set abbreviatedname='KAP' where programname='KAP';
update assessmentprogram set abbreviatedname='AMP' where programname='Alaska';

--Masking -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Masking' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--Masking activate by default -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Masking' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--Masking Type -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Masking' and pia.attributename='MaskingType'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Item Translation Display Language -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='Language'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Item Translation Display activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Item Translation Display assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Item Translation Display Language -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='Language'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Item Translation Display activateByDefault -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Item Translation Display assignedSupport -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='itemTranslationDisplay' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);




--Keyword Translation Display Language -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='Language'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Keyword Translation Display activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Keyword Translation Display assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Keyword Translation Display Language -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='Language'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Keyword Translation Display activateByDefault -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Keyword Translation Display assignedSupport -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Signing Sign Type -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Signing' and pia.attributename='SigningType'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Signing activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Signing' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Signing assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Signing' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Tactile Tactile File -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='tactileFile'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Tactile activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Tactile assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Tactile Tactile File -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='tactileFile'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Tactile activateByDefault -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Tactile assignedSupport -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Tactile Tactile File -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='tactileFile'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Tactile activateByDefault -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Tactile assignedSupport -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Tactile' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille usage -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='usage'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Grade -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleGrade'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Mark -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleMark'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Status Cell-DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleStatusCell'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille Dot Pressure -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleDotPressure'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Number of Braille Cells -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='numberOfBrailleCells'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Number of Braille Dots -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='numberOfBrailleDots'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille usage -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='usage'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille activateByDefault -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Grade -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleGrade'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Mark -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleMark'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille Braille Status Cell-KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleStatusCell'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Dot Pressure -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleDotPressure'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Number of Braille Cells -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='numberOfBrailleCells'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Number of Braille Dots -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='numberOfBrailleDots'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille usage -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='usage'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Braille activateByDefault -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Grade -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleGrade'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Mark -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleMark'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Braille Status Cell-AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleStatusCell'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Dot Pressure -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='brailleDotPressure'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Number of Braille Cells -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='numberOfBrailleCells'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Braille Number of Braille Dots -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='numberOfBrailleDots'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Auditory Background activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AuditoryBackground' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Auditory Background assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AuditoryBackground' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Breaks assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='breaks' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Breaks assignedSupport -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='breaks' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Breaks assignedSupport -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='breaks' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);



--Additional Testing Time activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time assignedSupport -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time TimeMultiplier -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='TimeMultiplier'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time activateByDefault -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time assignedSupport -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time TimeMultiplier -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='TimeMultiplier'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time activateByDefault -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time assignedSupport -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Additional Testing Time TimeMultiplier -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='AdditionalTestingTime' and pia.attributename='TimeMultiplier'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--Spoken activateByDefault -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='activateByDefault'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Spoken ReadAtStartPreference -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='ReadAtStartPreference'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--Setting separate quiet setting -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='setting' and pia.attributename='separateQuiteSetting'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Presentation some other accomodation -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--Presentation some other accomodation -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Presentation some other accomodation -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Presentation reads assessment out loud -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='readsAssessmentOutLoud'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Presentation reads assessment out loud -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='useTranslationsDictionary'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Response dictated -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='dictated'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Response signed responses -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='signedResponses'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Response used Communication Device -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='usedCommunicationDevice'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);