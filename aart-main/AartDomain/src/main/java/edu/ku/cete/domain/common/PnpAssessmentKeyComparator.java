package edu.ku.cete.domain.common;

import java.util.Comparator;

public class PnpAssessmentKeyComparator implements Comparator<String> {
	final String ORDER = "KAPDLMCPASSKELPA2I-SMARTI-SMART2PLTW";
	@Override
	public int compare(String o1, String o2) {
		if(o1== null || o2 ==  null){
			return 99;
		}
		return ORDER.indexOf(o1) -  ORDER.indexOf(o2);
	}

}
