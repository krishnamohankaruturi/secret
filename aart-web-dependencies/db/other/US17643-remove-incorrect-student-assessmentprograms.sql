DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['8139291463', 'DLM'],
		['2522426974', 'DLM'],
		['8087733789', 'KAP'],
		['8265286546', 'KAP'],
		['6146177482', 'KAP'],
		['6545994603', 'KAP'],
		['8401801486', 'KAP'],
		['6643962412', 'KAP'],
		['8700661074', 'KAP'],
		['8647890868', 'KAP'],
		['9380783388', 'KAP'],
		['2371583715', 'KAP'],
		['6443498119', 'KAP'],
		['8099730771', 'KAP'],
		['6643552494', 'KAP'],
		['6494752845', 'KAP'],
		['9565127428', 'KAP'],
		['5219014471', 'DLM'],
		['1175877115', 'KAP'],
		['9942120912', 'KAP'],
		['5524568159', 'DLM'],
		['2215878681', 'KAP'],
		['6900964575', 'DLM'],
		['4240365678', 'KAP'],
		['8009939382', 'DLM'],
		['4143465612', 'DLM'],
		['9957526316', 'DLM'],
		['2712601955', 'DLM'],
		['7859876272', 'DLM'],
		['5397230537', 'DLM'],
		['2699184896', 'DLM'],
		['6796788052', 'DLM'],
		['4640040091', 'DLM'],
		['5080104996', 'DLM'],
		['6894643318', 'DLM'],
		['1736894587', 'DLM'],
		['4865847146', 'DLM'],
		['7050108979', 'DLM'],
		['8472638944', 'DLM'],
		['4086806584', 'DLM'],
		['4110615674', 'DLM'],
		['4124732961', 'DLM'],
		['6897896497', 'DLM'],
		['7651950089', 'DLM'],
		['6835392959', 'DLM'],
		['7645919027', 'DLM'],
		['7382590852', 'DLM'],
		['3528747544', 'DLM'],
		['7571393116', 'DLM'],
		['4485174778', 'DLM'],
		['6185811715', 'DLM'],
		['1009752413', 'DLM'],
		['7392454099', 'DLM'],
		['7290982738', 'DLM'],
		['2268721477', 'DLM'],
		['3995199259', 'DLM'],
		['1331404606', 'DLM'],
		['7164329249', 'DLM'],
		['4206585954', 'DLM'],
		['2976006113', 'DLM'],
		['2622626215', 'DLM'],
		['7359857457', 'DLM'],
		['3217822781', 'DLM'],
		['6865414578', 'DLM'],
		['2262429839', 'DLM'],
		['8530903412', 'DLM'],
		['8457179012', 'DLM'],
		['8617553928', 'DLM'],
		['8734848169', 'DLM'],
		['1818561549', 'DLM'],
		['8238734177', 'DLM'],
		['3904482475', 'DLM'],
		['5947361787', 'DLM'],
		['4848935318', 'DLM'],
		['8633748894', 'DLM'],
		['9603784397', 'DLM'],
		['4008248862', 'DLM'],
		['3655141491', 'DLM'],
		['2407119711', 'DLM'],
		['9982196626', 'DLM'],
		['3402314037', 'DLM'],
		['8109377432', 'DLM'],
		['2351688929', 'DLM'],
		['6127621178', 'DLM'],
		['5320872399', 'DLM'],
		['3636997645', 'DLM'],
		['1678225118', 'DLM'],
		['2873261137', 'DLM'],
		['8908598804', 'DLM'],
		['2423532822', 'DLM'],
		['3492654142', 'DLM'],
		['9727528422', 'DLM'],
		['5531307098', 'DLM'],
		['1736713655', 'DLM'],
		['8132778227', 'DLM'],
		['6297140863', 'DLM'],
		['4858244164', 'DLM'],
		['6988601171', 'DLM'],
		['3520480468', 'DLM'],
		['7274713396', 'DLM'],
		['6414019402', 'DLM'],
		['7415887344', 'DLM'],
		['2179647638', 'DLM'],
		['5750513157', 'DLM'],
		['1045690384', 'DLM'],
		['1501664166', 'DLM'],
		['6822362916', 'DLM'],
		['5172096728', 'DLM'],
		['1181646162', 'DLM'],
		['4347720467', 'DLM'],
		['6399383005', 'DLM'],
		['4078440541', 'DLM'],
		['8958680709', 'DLM'],
		['8843742132', 'DLM'],
		['5259817982', 'DLM'],
		['8655815322', 'DLM'],
		['5592616856', 'DLM'],
		['1430631244', 'DLM'],
		['1918619166', 'DLM'],
		['9183821589', 'DLM'],
		['9181006918', 'DLM'],
		['4007241392', 'DLM'],
		['5750988678', 'DLM'],
		['8096766783', 'DLM'],
		['5685390459', 'DLM'],
		['1640408002', 'DLM'],
		['7788934232', 'DLM'],
		['8682386798', 'DLM'],
		['4487129419', 'DLM'],
		['1437275176', 'DLM'],
		['6864078523', 'DLM'],
		['4811804503', 'DLM'],
		['4166487736', 'DLM'],
		['2387718704', 'DLM'],
		['2131248776', 'DLM'],
		['3053958902', 'DLM'],
		['4472416808', 'DLM'],
		['4340864706', 'DLM'],
		['3324346134', 'DLM'],
		['1643704214', 'DLM'],
		['2681219051', 'DLM'],
		['3003709623', 'DLM'],
		['2104245567', 'DLM'],
		['1757261389', 'DLM'],
		['4898726968', 'DLM'],
		['7128093044', 'DLM'],
		['2068859297', 'DLM'],
		['7794011418', 'DLM'],
		['4233846836', 'DLM'],
		['5259289617', 'DLM'],
		['4251324609', 'DLM'],
		['8126645245', 'DLM'],
		['9823877386', 'DLM'],
		['6285995362', 'DLM'],
		['5615152583', 'DLM'],
		['5688004339', 'DLM'],
		['1879648903', 'DLM'],
		['1767260768', 'DLM'],
		['9872607672', 'DLM'],
		['9182745129', 'DLM'],
		['4801564321', 'DLM'],
		['1310027897', 'KAP'],
		['2529575193', 'KAP'],
		['1315268477', 'KAP'],
		['2810155909', 'KAP'],
		['7312593631', 'KAP'],
		['5814392886', 'KAP'],
		['5936870421', 'KAP'],
		['8847125898', 'KAP'],
		['3774906513', 'KAP'],
		['6337076494', 'KAP'],
		['2629975606', 'KAP'],
		['6359518813', 'KAP'],
		['8848048544', 'KAP'],
		['5937981316', 'KAP'],
		['9847883041', 'KAP'],
		['3509057821', 'KAP'],
		['8040844991', 'KAP'],
		['2339238447', 'KAP'],
		['3193207438', 'KAP'],
		['2839029421', 'KAP'],
		['4843128163', 'KAP'],
		['6489755378', 'KAP'],
		['5528475392', 'KAP'],
		['8613731422', 'KAP'],
		['9051634676', 'KAP'],
		['1347102353', 'KAP'],
		['4299855396', 'KAP'],
		['7315648593', 'KAP'],
		['3839350875', 'KAP'],
		['1001157389', 'KAP'],
		['7913903937', 'KAP'],
		['5360277297', 'KAP'],
		['2491912252', 'KAP'],
		['3282876939', 'KAP'],
		['8694315491', 'KAP'],
		['9641719394', 'KAP'],
		['7815324908', 'KAP'],
		['8248168336', 'KAP'],
		['1460925505', 'KAP'],
		['5280870714', 'KAP'],
		['2448451109', 'KAP'],
		['9684067089', 'KAP'],
		['2154152368', 'KAP'],
		['9540749174', 'KAP'],
		['3611473618', 'KAP'],
		['7336443168', 'KAP'],
		['9041526501', 'KAP'],
		['7121935902', 'KAP'],
		['4716206378', 'KAP'],
		['3401569201', 'KAP'],
		['2838850768', 'KAP'],
		['2846602484', 'KAP'],
		['9516962882', 'KAP'],
		['9606372472', 'KAP'],
		['4972918365', 'KAP'],
		['1162807644', 'DLM'],
		['5685063571', 'DLM'],
		['1758990295', 'DLM'],
		['2636832149', 'DLM'],
		['2585074579', 'DLM'],
		['1691004693', 'DLM'],
		['5621855817', 'KAP'],
		['4252237963', 'KAP'],
		['8221253812', 'KAP'],
		['7954790797', 'DLM'],
		['8495631105', 'KAP'],
		['2575548985', 'KAP'],
		['7145136767', 'KAP'],
		['3598330189', 'KAP'],
		['6501661455', 'KAP'],
		['5340937682', 'KAP'],
		['7377244844', 'KAP'],
		['7456988099', 'KAP'],
		['2718128453', 'KAP'],
		['9520487484', 'DLM'],
		['5994266205', 'DLM'],
		['3695937882', 'KAP'],
		['1408591316', 'KAP'],
		['4715317516', 'KAP'],
		['6248147108', 'KAP'],
		['7151736258', 'KAP'],
		['3182898612', 'KAP'],
		['7557965094', 'KAP'],
		['3743929791', 'KAP'],
		['7061881321', 'KAP'],
		['7976135171', 'KAP'],
		['9093163124', 'KAP'],
		['1156229073', 'KAP'],
		['4416411936', 'KAP'],
		['5030838279', 'KAP'],
		['6611583564', 'KAP'],
		['7997127984', 'KAP'],
		['3956881303', 'KAP'],
		['9011373456', 'DLM'],
		['4531887425', 'KAP'],
		['9827458264', 'KAP'],
		['3295031649', 'KAP'],
		['4917946204', 'DLM'],
		['3078940918', 'KAP'],
		['8990615046', 'KAP'],
		['7442394558', 'KAP'],
		['3487185431', 'KAP'],
		['6197098695', 'KAP'],
		['6052438932', 'KAP'],
		['7450534436', 'KAP'],
		['6448284184', 'KAP'],
		['8905871429', 'KAP'],
		['3016019922', 'KAP'],
		['7247477296', 'KAP'],
		['3935665245', 'KAP'],
		['2656327687', 'KAP'],
		['6939340653', 'KAP'],
		['5564316843', 'KAP'],
		['4113695083', 'KAP'],
		['1143917898', 'KAP'],
		['1565242149', 'KAP'],
		['9836690336', 'KAP'],
		['9704057318', 'KAP'],
		['1423427599', 'DLM'],
		['3428761286', 'KAP'],
		['1063193583', 'KAP'],
		['7250118986', 'KAP'],
		['1936247003', 'KAP'],
		['1797896814', 'KAP'],
		['6225304142', 'KAP'],
		['5817956004', 'KAP'],
		['9506519617', 'KAP'],
		['3942406357', 'KAP'],
		['6558493675', 'KAP'],
		['5437577311', 'KAP'],
		['4285272857', 'KAP'],
		['8224277577', 'KAP'],
		['1993822674', 'KAP'],
		['5749078175', 'KAP'],
		['6646624348', 'KAP'],
		['4626674844', 'KAP'],
		['5967204075', 'KAP'],
		['4302578203', 'KAP'],
		['9026390254', 'DLM'],
		['3525456891', 'KAP'],
		['7343379216', 'KAP'],
		['1830901311', 'KAP'],
		['8161704554', 'KAP'],
		['4966800544', 'KAP'],
		['5172469788', 'KAP'],
		['9620063996', 'KAP'],
		['1175968382', 'KAP'],
		['9439365153', 'KAP'],
		['9172868635', 'KAP'],
		['1991227949', 'KAP'],
		['4515608932', 'KAP'],
		['7901605286', 'DLM'],
		['2257649753', 'KAP'],
		['7498504626', 'KAP'],
		['9756970243', 'KAP'],
		['2455034461', 'KAP'],
		['2121576231', 'KAP'],
		['3534016769', 'KAP'],
		['5599246604', 'KAP'],
		['5286656609', 'KAP'],
		['6965759488', 'DLM'],
		['5102598775', 'KAP'],
		['3470463271', 'DLM'],
		['9929640134', 'KAP'],
		['7469096965', 'KAP'],
		['1047959305', 'KAP'],
		['4385969752', 'KAP'],
		['6715568928', 'KAP'],
		['4521985858', 'KAP'],
		['6013398372', 'KAP'],
		['6813527487', 'KAP'],
		['8706854265', 'KAP'],
		['1855997584', 'KAP'],
		['6688530643', 'KAP'],
		['1790860733', 'KAP'],
		['3100501373', 'KAP'],
		['6758641422', 'KAP'],
		['5785148757', 'KAP'],
		['8530422414', 'KAP'],
		['4030819788', 'KAP'],
		['6633229976', 'KAP'],
		['1569635439', 'DLM'],
		['3878998813', 'KAP'],
		['6024287879', 'KAP'],
		['1796481408', 'KAP'],
		['9465081795', 'KAP'],
		['6567591552', 'KAP'],
		['8503440768', 'DLM'],
		['5837857192', 'KAP'],
		['7866035911', 'KAP'],
		['1865846945', 'KAP'],
		['6294839661', 'KAP'],
		['5439277412', 'KAP'],
		['2848205431', 'DLM'],
		['2123974366', 'KAP'],
		['2871736723', 'KAP'],
		['5243154369', 'KAP'],
		['4035715824', 'KAP'],
		['2331528756', 'DLM'],
		['2254372238', 'DLM'],
		['6967236474', 'KAP'],
		['2613838825', 'KAP'],
		['2081367483', 'KAP'],
		['2985403464', 'DLM'],
		['4618384573', 'KAP'],
		['2043892983', 'DLM'],
		['1267282088', 'KAP'],
		['2656249619', 'DLM'],
		['7110844854', 'DLM'],
		['3488110044', 'KAP'],
		['6238415169', 'KAP'],
		['9547917781', 'KAP'],
		['3477868757', 'KAP'],
		['7662239551', 'KAP'],
		['2349768953', 'KAP'],
		['3965300792', 'KAP'],
		['6129261284', 'KAP'],
		['1954781857', 'KAP'],
		['2596872471', 'KAP'],
		['5971975935', 'KAP'],
		['5003454961', 'KAP'],
		['2715275579', 'KAP'],
		['5543096879', 'KAP'],
		['5129713737', 'KAP'],
		['3346298191', 'KAP'],
		['6185363593', 'DLM'],
		['9816387425', 'DLM'],
		['2567857613', 'KAP'],
		['2920710427', 'KAP'],
		['3549657331', 'DLM'],
		['7768030176', 'DLM'],
		['7946398948', 'KAP'],
		['2981988352', 'DLM'],
		['8194136636', 'KAP'],
		['7211571152', 'DLM'],
		['9856804981', 'KAP'],
		['9304034124', 'KAP'],
		['4808021218', 'KAP'],
		['2020159627', 'KAP'],
		['8585383194', 'DLM'],
		['4596061173', 'KAP'],
		['4590214741', 'KAP'],
		['3657401954', 'KAP'],
		['8763110822', 'KAP'],
		['6503794274', 'KAP'],
		['3997117233', 'KAP'],
		['6715476128', 'KAP'],
		['6603161389', 'DLM'],
		['2935341929', 'KAP'],
		['4966906431', 'KAP'],
		['9724745872', 'KAP'],
		['4198568626', 'KAP'],
		['9901322812', 'KAP'],
		['5395790721', 'KAP'],
		['3281838057', 'KAP'],
		['6864879895', 'KAP'],
		['9070237083', 'KAP'],
		['5537486644', 'KAP'],
		['7294768412', 'KAP'],
		['5834261906', 'KAP'],
		['9334049561', 'KAP'],
		['7180739065', 'KAP'],
		['6824369179', 'KAP'],
		['3617959717', 'KAP'],
		['7573260318', 'KAP'],
		['9118265472', 'KAP'],
		['2144960739', 'DLM'],
		['8163656425', 'DLM'],
		['1024079627', 'DLM'],
		['1286923581', 'DLM'],
		['2507035005', 'KAP'],
		['3291177606', 'KAP'],
		['1171400837', 'KAP'],
		['4215391806', 'KAP'],
		['7571099773', 'KAP'],
		['2069558363', 'KAP'],
		['3508418405', 'KAP'],
		['8831675257', 'DLM'],
		['8358469284', 'DLM']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		RAISE NOTICE '---- Attempting to remove ''%'' from ''%'' ----', data[i][1], data[i][2]; 
	
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS') AND statestudentidentifier = data[i][1]
		LIMIT 1
		INTO currentstudentid;
		
		IF currentstudentid IS NOT NULL THEN
			RAISE NOTICE 'Found student ''%'' as studentid %', data[i][1], currentstudentid;
			PERFORM remove_student_from_assessment_program(currentstudentid, data[i][2], 2016);
		ELSE
			RAISE NOTICE 'State student identifier ''%'' not found in KS, skipping...', data[i][1];
		END IF;
	END LOOP;
END;
$BODY$;