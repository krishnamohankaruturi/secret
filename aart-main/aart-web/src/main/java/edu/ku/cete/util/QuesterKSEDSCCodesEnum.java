package edu.ku.cete.util;

public class QuesterKSEDSCCodesEnum {

	
	
	private String specialCircumstanceCode;

	
	public String getSpecialCircumstanceCode(String scCode) {
		
		if ("01".equalsIgnoreCase(scCode) || "1".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC07");
		}else if ("02".equalsIgnoreCase(scCode) || "2".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC08");
		}else if ("03".equalsIgnoreCase(scCode) || "3".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC39");
		}else if ("04".equalsIgnoreCase(scCode) || "4".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC27");
		}else if ("05".equalsIgnoreCase(scCode) || "5".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC41");
		}else if ("06".equalsIgnoreCase(scCode) || "6".equalsIgnoreCase(scCode)) {
			setSpecialCircumstanceCode("SC39");
		}else if ("07".equalsIgnoreCase(scCode) || "7".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC39");
		}else if ("08".equalsIgnoreCase(scCode) || "8".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC39");
		}else if ("09".equalsIgnoreCase(scCode) || "9".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC26");
		}else if ("10".equalsIgnoreCase(scCode)){
			setSpecialCircumstanceCode("SC41");
		}			
			return specialCircumstanceCode;
	}

	public void setSpecialCircumstanceCode(String specialCircumstanceCode) {
		this.specialCircumstanceCode = specialCircumstanceCode;
	}
}
