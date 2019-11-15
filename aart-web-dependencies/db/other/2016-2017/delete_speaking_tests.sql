--select * from testsectionstaskvariants where testsectionid=87974 and taskvariantid in (439353,439352,439351,439339,439338,439337) order by taskvariantposition,taskvariantid;

delete from testsectionstaskvariants where testsectionid=87974 and taskvariantid in (439353,439351,439352);

--select * from testsectionstaskvariants where testsectionid=87975 and taskvariantid in (439367,439366,439359,439358,439357,439356) order by taskvariantposition,taskvariantid;

delete from testsectionstaskvariants where testsectionid=87975 and taskvariantid in (439359,439357,439367);

--select * from testsectionstaskvariants where testsectionid=87977 and taskvariantid in (439382,439381,439378,439377,439374,439373,439372,439371) order by taskvariantposition,taskvariantid;

delete from testsectionstaskvariants where testsectionid=87977 and taskvariantid in (439374,439372,439382,439378);

--select count(*) from testjson where testid in (select testid from testsection where id in (87974, 87975, 87977));

delete from testjson where testid in (select testid from testsection where id in (87974, 87975, 87977));