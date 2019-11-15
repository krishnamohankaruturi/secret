package edu.ku.service;

import java.util.Arrays;

public class TestIdentifyReturnValue {

	//private static final String emptyReturnValue = null; //new String("");
	private static final String STR_VALUE_NOTIF_OF_CHANGE = "NOTIFICATION OF CHANGE";
	private static final String STR_VALUE_RETURN = "RETURN";
	private static final String STR_VALUE_FORWARD = "FORWARD";
	private static final String STR_VALUE_UNDETERMINED = "UNDETERMINED";
	
	private static final String NACHA_STD_ENTRY_CLASS_CODE_COR ="COR";
	
	private static final Integer[] validAllTxnTypesArray = {21, 26, 31, 36, 41, 46, 51, 56, 22, 23, 24, 27, 28, 29, 32, 33, 34, 37, 38, 39, 42, 43, 44, 47, 48, 49, 52, 53, 54, 55};
	private static final Integer[] valid1stSetTxnTypesArray = {21, 26, 31, 36, 41, 46, 51, 56};
	private static final Integer[] valid2ndSetTxnTypesArray = {22, 23, 24, 27, 28, 29, 32, 33, 34, 37, 38, 39, 42, 43, 44, 47, 48, 49, 52, 53, 54};
	
	public static void main(String[] args)
	{
		/*
		If the NACHA transaction type in (21, 26, 31, 36, 41, 46, 51, 56) and Addenda Type Code = 98 Then 'NOTIFICATION OF CHANGE'
		If the NACHA transaction type in (21, 26, 31, 36, 41, 46, 51, 56) and Addenda Type Code = 99 and Nacha Standard Entry Class Code <> 'COR' Then 'RETURN'
		If the NACHA transaction type in (21, 26, 31, 36, 41, 46, 51, 56) and Addenda Type Code is not in (98, 99) and Nacha Standard Entry Class Code = 'COR' Then 'NOTIFICATION OF CHANGE'
		If the NACHA transaction type in (21, 26, 31, 36, 41, 46, 51, 56) and Addenda Type Code is not in (98, 99) and Nacha Standard Entry Class Code <> 'COR' Then 'RETURN'
		If the NACHA transaction type in (21, 26, 31, 36, 41, 46, 51, 56) and Addenda Type Code is not in (98, 99) and there is no Nacha Standard Entry Class Code Then 'UNDETERMINED'
		*/
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:98, stdEntryClassCode:; -->ReturnValue:" + identifyReturnValue(21, 98, ""));
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:99, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(21, 99, "COR"));
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:99, stdEntryClassCode:ABC; -->ReturnValue:" + identifyReturnValue(21, 99, "ABC"));
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:96, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(21, 96, "COR"));
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:96, stdEntryClassCode:ABC; -->ReturnValue:" + identifyReturnValue(21, 96, "ABC"));
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:96, stdEntryClassCode:; -->ReturnValue:" + identifyReturnValue(21, 96, ""));
		System.out.println("InPut-->nachaTransactionType:21, addendaTypeCode:96, stdEntryClassCode:null; -->ReturnValue:" + identifyReturnValue(21, 96, null));
		
		//If the NACHA transaction type in (22, 23, 24, 27, 28, 29, 32, 33, 34, 37, 38, 39, 42, 43, 44, 47, 48, 49, 52, 53, 54) 
		//    and Nacha Standard Entry Class Code <> 'COR' Then 'FORWARD'

		System.out.println("InPut-->nachaTransactionType:23, addendaTypeCode:-1, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(23, -1, "COR"));
		System.out.println("InPut-->nachaTransactionType:23, addendaTypeCode:-1, stdEntryClassCode:ABC; -->ReturnValue:" + identifyReturnValue(23, -1, "ABC"));
		System.out.println("InPut-->nachaTransactionType:23, addendaTypeCode:-1, stdEntryClassCode:null; -->ReturnValue:" + identifyReturnValue(23, -1, null));
		
		//If the NACHA transaction type in (55) and Addenda Type Code = 98 Then 'NOTIFICATION OF CHANGE'
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:98, stdEntryClassCode:; -->ReturnValue:" + identifyReturnValue(55, 98, ""));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:98, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(55, 98, "COR"));
		
		//If the NACHA transaction type in (55) and Addenda Type Code = 99 and Nacha Standard Entry Class Code <> 'COR' Then 'RETURN'
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:99, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(55, 99, "COR"));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:99, stdEntryClassCode:null; -->ReturnValue:" + identifyReturnValue(55, 99, null));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:99, stdEntryClassCode:ABC; -->ReturnValue:" + identifyReturnValue(55, 99, "ABC"));
		
		//If the NACHA transaction type in (55) and Addenda Type Code is not in (98, 99) and Nacha Standard Entry Class Code <> 'COR' Then 'FORWARD'
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(55, 95, "COR"));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:ABC; -->ReturnValue:" + identifyReturnValue(55, 95, "ABC"));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:; -->ReturnValue:" + identifyReturnValue(55, 95, ""));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:null; -->ReturnValue:" + identifyReturnValue(55, 95, null));
		
		//If the NACHA transaction type in (55) and Addenda Type Code is not in (98) and Nacha Standard Entry Class Code = 'COR' Then 'UNDETERMINED'
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:99, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(55, 99, "COR"));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:COR; -->ReturnValue:" + identifyReturnValue(55, 95, "COR"));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:ABC; -->ReturnValue:" + identifyReturnValue(55, 95, "ABC"));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:; -->ReturnValue:" + identifyReturnValue(55, 95, ""));
		System.out.println("InPut-->nachaTransactionType:55, addendaTypeCode:95, stdEntryClassCode:null; -->ReturnValue:" + identifyReturnValue(55, 95, null));
		
		//All other scenarios, then 'UNDETERMINED'
	}

	private static String identifyReturnValue(int nachaTransactionType, int addendaTypeCode, String stdEntryClassCode) 
	{
		String returnValue = STR_VALUE_UNDETERMINED;
		
		if(Arrays.asList(validAllTxnTypesArray).contains(nachaTransactionType))
		{
			if(Arrays.asList(valid1stSetTxnTypesArray).contains(nachaTransactionType))
			{
				if (addendaTypeCode == 98)
				{
					returnValue = STR_VALUE_NOTIF_OF_CHANGE;
				}
				else if (addendaTypeCode == 99 
						&& (stdEntryClassCode == null || !stdEntryClassCode.equalsIgnoreCase(NACHA_STD_ENTRY_CLASS_CODE_COR)))
				{
					returnValue = STR_VALUE_RETURN;
				}
				else if (addendaTypeCode != 98 && addendaTypeCode != 99)
				{
					if(stdEntryClassCode == null || stdEntryClassCode == "")
					{
						returnValue = STR_VALUE_UNDETERMINED;
					}
					else if (stdEntryClassCode.equalsIgnoreCase(NACHA_STD_ENTRY_CLASS_CODE_COR))
					{
						returnValue = STR_VALUE_NOTIF_OF_CHANGE;
					}
					else 
					{
						returnValue = STR_VALUE_RETURN;
					}
				}
			} 
			else if (Arrays.asList(valid2ndSetTxnTypesArray).contains(nachaTransactionType)
					&& (stdEntryClassCode == null || !stdEntryClassCode.equalsIgnoreCase(NACHA_STD_ENTRY_CLASS_CODE_COR)))
			{
				returnValue = STR_VALUE_FORWARD;
			}
			else if (nachaTransactionType == 55) {
				if (addendaTypeCode == 98)
				{
					returnValue = STR_VALUE_NOTIF_OF_CHANGE;
				}
				else if (addendaTypeCode == 99 
						&& (stdEntryClassCode == null || !stdEntryClassCode.equalsIgnoreCase(NACHA_STD_ENTRY_CLASS_CODE_COR)))
				{
					returnValue = STR_VALUE_RETURN;
				}
				else if (addendaTypeCode != 98 && addendaTypeCode != 99 
						&& (stdEntryClassCode == null || !stdEntryClassCode.equalsIgnoreCase(NACHA_STD_ENTRY_CLASS_CODE_COR)))
				{
					returnValue = STR_VALUE_FORWARD;
				}
				else if (addendaTypeCode != 98 && stdEntryClassCode != null 
						&& stdEntryClassCode.equalsIgnoreCase(NACHA_STD_ENTRY_CLASS_CODE_COR))
				{
					returnValue = STR_VALUE_UNDETERMINED;
				}
			}

		}
		
		return returnValue;
	}

}
