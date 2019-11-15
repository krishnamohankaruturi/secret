package edu.ku.cete.controller.sif;

import org.apache.commons.lang3.StringUtils;

public enum CEDSCodeMapping {

	CEDS_1907("1907", "1"), 
	CEDS_1908("1908", "2"), 
	CEDS_1909("1909", "3"), 
	CEDS_1910("1910", "4"), 
	CEDS_1911("1911", "23"), 
	CEDS_1912("1912", "5"), 
	CEDS_1913("1913", "24"),
	CEDS_1914("1914", "25"),
    CEDS_1915("1915", "26"),
    CEDS_1916("1916", "21"),
    CEDS_1917("1917", "15"),
    CEDS_1918("1918", "6"),
    CEDS_1919("1919", "27"),
    CEDS_1921("1921", "8"),
    CEDS_1922("1922", "28"),
    CEDS_1923("1923", "10"),
    CEDS_1924("1924", "11"),
    CEDS_1925("1925", "12"),
    CEDS_1926("1926", "29"),
    CEDS_1927("1927", "14"),
    CEDS_1928("1928", "30"),
    CEDS_1930("1930", "31"),
    CEDS_1931("1931", "17"),
    CEDS_3499("3499", "32"),
    CEDS_3502("3502", "16"),
    CEDS_3503("3503", "33"),
    CEDS_3504("3504", "34"),
    CEDS_3505("3505", "18"),
    CEDS_3508("3508", "35"),
    CEDS_3509("3509", "7"),
    CEDS_73060("73060", "22"),
    CEDS_73061("73061", "36"),
    CEDS_9999("9999", "98"),
    CANCEL("CANCEL", "99");

	private CEDSCodeMapping(String cedsCode, String epCode) {
		this.cedsCode = cedsCode;
		this.epCode = epCode;
	}

	private final String cedsCode;
	private final String epCode;

	public static CEDSCodeMapping getBycedsCode(String cedsCode){
		if (StringUtils.isNotBlank(cedsCode)) {
			for (CEDSCodeMapping code : CEDSCodeMapping.values()) {
				if (code.getCedsCode().equals(cedsCode)) {
					return code;
				}
			}
		}
		return CANCEL;
	}
	public static String getBycedsCodeSIF(String cedsCode){
		if (StringUtils.isNotBlank(cedsCode)) {
			for (CEDSCodeMapping code : CEDSCodeMapping.values()) {
				if (code.getCedsCode().equalsIgnoreCase(cedsCode)) {
					return code.getEpCode();
				}
			}
		}
		return "-9999";
	}

	public String getCedsCode() {
		return cedsCode;
	}

	public String getEpCode() {
		return epCode;
	}
}
