package edu.ku.cete.score.util;

public class ResponseLabelUtil {

	public static String getResponseLabel(Integer responseOrder, String layoutFormat) {
		String responseLabel = "";
		if ("letters".equalsIgnoreCase(layoutFormat)){
			switch (responseOrder){
			case 0: 
				responseLabel = "A";
				break;
			case 1: 
				responseLabel = "B";
				break;
			case 2: 
				responseLabel = "C";
				break;
			case 3: 
				responseLabel = "D";
				break;
			case 4: 
				responseLabel = "E";
				break;
			case 5: 
				responseLabel = "F";
				break;
			case 6: 
				responseLabel = "G";
				break;
			case 7: 
				responseLabel = "H";
				break;
			case 8: 
				responseLabel = "I";
				break;
			case 9: 
				responseLabel = "J";
				break;
			}
		}else if ("numbers".equalsIgnoreCase(layoutFormat)){
			//responseOrder is zero based, so add one and convert to string
			responseLabel = String.valueOf(responseOrder+1);
		}else {
			//no label
			responseLabel = "";
		}
		return responseLabel;
	}
	
}
