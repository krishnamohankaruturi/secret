begin;
/*
--ssid:-ELA,inactive stage 2
*/
update studentstests
set    activeflag =false,
      manualupdatereason ='as for ticket #514796', 
      modifieddate=now(),
	  modifieduser =174744
where id in (17382598,
17365766,
17204833,
17204842,
17204762,
17204993,
17204730,
17322606,
17204777,
17302234,
17364904,
17204896,
17322627,
17322588,
17322653,
17263316,
17242445,
17322568,
17276941,
17263285,
17263306,
17242459,
17242442,
17322564,
17323534,
17242465,
17263302,
17263321,
17263318,
17242462,
17276868,
17242468,
17263294,
17242453,
17278138,
17263390,
17323726,
17324079,
17323734,
17323675,
17323640,
17323653,
17323658,
17323667,
17323719,
17323677,
17323722,
17323730,
17323693,
17323648,
17323671,
17489782,
17323647,
17323642
) ;


update studentstestsections
set    activeflag =false,
      modifieddate=now(),
	  modifieduser =174744
where studentstestid in (17382598,
17365766,
17204833,
17204842,
17204762,
17204993,
17204730,
17322606,
17204777,
17302234,
17364904,
17204896,
17322627,
17322588,
17322653,
17263316,
17242445,
17322568,
17276941,
17263285,
17263306,
17242459,
17242442,
17322564,
17323534,
17242465,
17263302,
17263321,
17263318,
17242462,
17276868,
17242468,
17263294,
17242453,
17278138,
17263390,
17323726,
17324079,
17323734,
17323675,
17323640,
17323653,
17323658,
17323667,
17323719,
17323677,
17323722,
17323730,
17323693,
17323648,
17323671,
17489782,
17323647,
17323642
)   ;


update studentsresponses
set    activeflag =false,
      modifieddate=now(),
	  modifieduser =174744
where studentstestsid in (17382598,
17365766,
17204833,
17204842,
17204762,
17204993,
17204730,
17322606,
17204777,
17302234,
17364904,
17204896,
17322627,
17322588,
17322653,
17263316,
17242445,
17322568,
17276941,
17263285,
17263306,
17242459,
17242442,
17322564,
17323534,
17242465,
17263302,
17263321,
17263318,
17242462,
17276868,
17242468,
17263294,
17242453,
17278138,
17263390,
17323726,
17324079,
17323734,
17323675,
17323640,
17323653,
17323658,
17323667,
17323719,
17323677,
17323722,
17323730,
17323693,
17323648,
17323671,
17489782,
17323647,
17323642
)  and activeflag is true ;



--set stage 1 status to in process

update studentstests
set    status =85,
       enddatetime=null,
	   manualupdatereason ='as for ticket #514796', 
      modifieddate=now(),
	  modifieduser =174744
where id in (16040485,
16039964,
16038709,
16038903,
16037435,
16043926,
16036653,
16041482,
16037838,
16037410,
16038205,
16040480,
16042025,
16041368,
16043459,
16041444,
16036557,
16039921,
16042762,
16036145,
16040892,
16037948,
16035902,
16039211,
16288714,
16040139,
16039549,
16043233,
16041586,
16039125,
16040913,
16040442,
16037389,
16036857,
16040409,
16218891,
16029627,
16257334,
16030195,
16025926,
16021700,
16024316,
16024363,
16025247,
16028347,
16026310,
16029009,
16030135,
16027343,
16023231,
16025608,
16023936,
16022120,
16022038
)  ;


update studentstestsections
set    statusid =126,
      modifieddate=now(),
	  modifieduser =174744
where studentstestid in (16040485,
16039964,
16038709,
16038903,
16037435,
16043926,
16036653,
16041482,
16037838,
16037410,
16038205,
16040480,
16042025,
16041368,
16043459,
16041444,
16036557,
16039921,
16042762,
16036145,
16040892,
16037948,
16035902,
16039211,
16288714,
16040139,
16039549,
16043233,
16041586,
16039125,
16040913,
16040442,
16037389,
16036857,
16040409,
16218891,
16029627,
16257334,
16030195,
16025926,
16021700,
16024316,
16024363,
16025247,
16028347,
16026310,
16029009,
16030135,
16027343,
16023231,
16025608,
16023936,
16022120,
16022038
) ;
--MATH RESET STAGE 2 deactive
update studentstests
set    activeflag =false,
      manualupdatereason ='as for ticket #514796', 
      modifieddate=now(),
	  modifieduser =174744
where id in (17014911,17015010,16956891,17016313,17016586,17361370,16956878,17014952);


update studentstestsections
set    activeflag =false,
      modifieddate=now(),
	  modifieduser =174744
where studentstestid in (17014911,17015010,16956891,17016313,17016586,17361370,16956878,17014952);

update studentsresponses
set    activeflag =false,
      modifieddate=now(),
	  modifieduser =174744
where studentstestsid in (17014911,17015010,16956891,17016313,17016586,17361370,16956878,17014952) and activeflag is true;

--reset stage 1 in process
update studentstests
set    status =85,
       enddatetime=null,
	   manualupdatereason ='as for ticket #514796', 
      modifieddate=now(),
	  modifieduser =174744
where id in (15719950,15724332,15721145,15722685,15998418,15718835,15717885,15720828);

update studentstestsections
set    statusid =126,
      modifieddate=now(),
	  modifieduser =174744
where studentstestid in (15719950,15724332,15721145,15722685,15998418,15718835,15717885,15720828);
commit;