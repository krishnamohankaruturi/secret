package edu.ku.cete.batch.upload.validator;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.service.UploadFileService;

public class BatchUploadValidator implements Validator{
 
	final static Log logger = LogFactory.getLog(BatchUploadValidator.class);
	
	@Autowired
	private UploadFileService uploadFileService;
	
	private Long uploadTypeId;
	
	private StepExecution stepExecution;
	
	private String uploadTypeCode;
	
	@Value("${scoringRecordType}")
	private String scoringRecordType;

	@Override
	public boolean supports(Class<?> classObject) {
		return classObject == DefaultFieldSet.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object object, Errors errors) {
	 	Map<String, FieldSpecification> feildSpecRecords = (Map<String, FieldSpecification>) stepExecution.getJobExecution().getExecutionContext().get("feildSpecRecords");
		logger.debug("Started common validation.");
		FieldSet fieldsFromFile = (FieldSet)object;
		Set<String> feildSpecHeadings = feildSpecRecords.keySet();
		for(String feildSpecHeading : feildSpecHeadings){
			validateFeild(feildSpecRecords.get(feildSpecHeading), fieldsFromFile, fieldsFromFile.readRawString("linenumber") ,errors);
		}
		logger.debug("Completed common validation.");
	}
	
	private void validateFeild(FieldSpecification fieldSpecification, FieldSet fieldsFromFile, String lineNumber, Errors errors ){
		String fieldValueFromFile = fieldsFromFile.readRawString(fieldSpecification.getFieldName()).trim();
		
		//reject if empty
		if(fieldSpecification.getRejectIfEmpty()){
			if(fieldValueFromFile == null || fieldValueFromFile.length() == 0){
				if(fieldSpecification.getFieldName().equalsIgnoreCase("Test_Type")) { //FIXME: Work around for TEC EXIT scenario
				     fieldValueFromFile = fieldsFromFile.readRawString("Record_Type").trim();
				     if(fieldValueFromFile == null || (fieldValueFromFile != null && !fieldValueFromFile.equalsIgnoreCase("Exit"))) {
				    	 setErrorMessage(errors, fieldSpecification, "", lineNumber, "" +  fieldSpecification.getMappedName() + " not specified."); 
				    	 return;
				     }
			    } else {
			    	setErrorMessage(errors, fieldSpecification, "", lineNumber, "" +  fieldSpecification.getMappedName() + " not specified."); 
			    	return;
			    }
			}
		}
		
		//if Numeric field. Check for numeric value
		if(fieldSpecification.getFieldType() != null){
			if(fieldSpecification.getFieldType().equalsIgnoreCase("number")){
				if(fieldValueFromFile!=null && !fieldValueFromFile.isEmpty()){
					if(!StringUtils.isNumeric(fieldValueFromFile) && fieldValueFromFile.charAt(0)!='-'){
						if(!scoringRecordType.equals(uploadTypeCode) || !StringUtils.equalsIgnoreCase("C", fieldValueFromFile)){//Accept code C for clear on scoring upload
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value must be a numeric and integer."); 
							return;
						}
					}
				}
				if(!scoringRecordType.equals(uploadTypeCode) || !StringUtils.equalsIgnoreCase("C", fieldValueFromFile)){//Accept code C for clear on scoring upload
					if(fieldSpecification.getMinimum() != null){
						if(fieldValueFromFile != null && !fieldValueFromFile.isEmpty() && !(Long.parseLong(fieldValueFromFile) >= fieldSpecification.getMinimum())){
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be greater than or equal to " + fieldSpecification.getMinimum() +"."); 
							return;
						}
					}
					
					if(fieldValueFromFile != null && !fieldValueFromFile.isEmpty() && fieldSpecification.getMaximum() != null){
						if(!(Long.parseLong(fieldValueFromFile) <= fieldSpecification.getMaximum())){
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be less than or equal to " + fieldSpecification.getMaximum() +"."); 
							return;
						}
					}
				}
			}
		}
		
		//if Decimal field. Check for decimal value
		if(fieldSpecification.getFieldType() != null){
			if(fieldSpecification.getFieldType().equalsIgnoreCase("decimal")){
				if(fieldValueFromFile!=null && !fieldValueFromFile.isEmpty()){
					if(!NumberUtils.isNumber(fieldValueFromFile)){
						setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value must be a numeric or a decimal."); 
						return;
					}
				
					if(fieldSpecification.getMinimum() != null){
						BigDecimal minDecimal = new BigDecimal(fieldSpecification.getMinimum());
						if(!(new BigDecimal(fieldValueFromFile).compareTo(minDecimal) >=0 )){
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Decimal value in field (" +  fieldSpecification.getMappedName() + ") must be greater than or equal to " + fieldSpecification.getMinimum() +"."); 
							return;
						}
					}
					
					if(fieldSpecification.getMaximum() != null){
						BigDecimal maxDecimal = new BigDecimal(fieldSpecification.getMaximum());
						if(!(new BigDecimal(fieldValueFromFile).compareTo(maxDecimal) <=0 )){
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Decimal value in field (" +  fieldSpecification.getMappedName() + ") must be less than or equal to " + fieldSpecification.getMaximum() +"."); 
							return;
						}
					}
				}
				//minimum regular expression
				if(fieldSpecification.getMinimumRegex() != null){
					Pattern regexPattern = Pattern.compile(fieldSpecification.getMinimumRegex());
					Matcher regexMatcher = regexPattern.matcher(fieldValueFromFile);
					if (!regexMatcher.matches()) {
						setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be match minimum criteria."); 
						return;
					}
				}
				
				//maximum regular expression
				if(fieldSpecification.getMaximumRegex() != null){
					Pattern regexPattern = Pattern.compile(fieldSpecification.getMaximumRegex());
					Matcher regexMatcher = regexPattern.matcher(fieldValueFromFile);
					if (!regexMatcher.matches()) {
						setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be match maximum criteria."); 
						return;
					}
				}
				
			}
		}
		
		//allowable values
		if(fieldSpecification.getAllowableValuesArray() != null){
			if(!Arrays.asList(fieldSpecification.getAllowableValuesArray()).contains(fieldValueFromFile.toLowerCase())){
				setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") is not allowed."); 
				return;
			}
		}
		
		//format regex
		if(fieldSpecification.getFormatRegex() != null && StringUtils.isNotEmpty(fieldValueFromFile)){
			try {
				//check if inValue is not null.It should not be null to do regex validation.
				
				Integer regexFlags = getRegexFlags(fieldSpecification);
				Pattern regexPattern = regexFlags != null ?
						Pattern.compile(fieldSpecification.getFormatRegex(), regexFlags) :
						Pattern.compile(fieldSpecification.getFormatRegex());
				Matcher regexMatcher = regexPattern.matcher(fieldValueFromFile);
				if (!regexMatcher.matches()) {
					setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be valid."); 
					return;
				}
				/*if (regexMatcher.matches()) {
					//passed regex so invalid is false.
					result = false;
				} else {
					if(formatRegex.equals("(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]") | formatRegex.equals("(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]|^$"))
						this.addInvalidField(bean, inValue, rejectIfInvalid, " is not valid. Date does not match the required date format of MM/DD/YYYY");
					else 
						this.addInvalidField(bean, inValue, rejectIfInvalid, " is not valid.");
				}*/
			} catch (Exception e) {
				logger.error("Error in regex", e);
			}
		}

		if(fieldSpecification.getFieldType() != null){
			if(fieldSpecification.getFieldType().equalsIgnoreCase("date")){
				fieldValueFromFile = fieldValueFromFile.trim(); 
				if(fieldValueFromFile.length() > 0 ){
					String splitChar = null;
					if(fieldValueFromFile.indexOf("/") > 0 ){
						splitChar = "/";
					}
					else if(fieldValueFromFile.indexOf("-") > 0 ){
						splitChar = "-";
					}
					if( splitChar != null ){
						String[] fieldDateValues = 	fieldValueFromFile.split(splitChar);
						if(fieldDateValues.length != 3){
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be valid");
							return;
							
						}else if(fieldDateValues[2].length()  != 4 )
						{
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Year Value in field (" +  fieldSpecification.getMappedName() + ") must be equal to 4 characters.");
							return;
						}
						if( ! isValidDate( Integer.parseInt(fieldDateValues[0]),Integer.parseInt(fieldDateValues[1]),Integer.parseInt(fieldDateValues[2]))){
							setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") is not valid Date, check number of days in month.");
							return;
						}
					}
				}
			}
		}
		//School Year Validation. 
		if(fieldSpecification.getFieldName().equalsIgnoreCase("schoolYear")){
			if(stepExecution.getJobExecution().getExecutionContext().get("schoolYear") != null){
				Long schoolYear = (Long) stepExecution.getJobExecution().getExecutionContext().get("schoolYear");
				if(!schoolYear.equals(Long.valueOf(fieldValueFromFile))){
					setErrorMessage(errors, fieldSpecification, "", lineNumber, "All school years in the file are not the same school year."); 
					return;
				}
			}else{
				stepExecution.getJobExecution().getExecutionContext().put("schoolYear", Long.valueOf(fieldValueFromFile));
			}
		}
		
		//fieldlength
		if(fieldSpecification.getFieldLength() != null){
			if(fieldValueFromFile.trim().length() > fieldSpecification.getFieldLength()){
				setErrorMessage(errors, fieldSpecification, "", lineNumber, "Value in field (" +  fieldSpecification.getMappedName() + ") must be less than or equal to " + fieldSpecification.getFieldLength() + " characters."); 
				return;
			}
		}
	}
	
	private void setErrorMessage(Errors errors, FieldSpecification fieldSpecification, String errorCode, String lineNumber, String errorMessage ){
		if(fieldSpecification.getRejectIfInvalid() && fieldSpecification.isShowError()){
			Object[] arguments = new Object[2];
			arguments[0]=lineNumber;
			arguments[1]=fieldSpecification.getMappedName();
			if(scoringRecordType.equals(uploadTypeCode)){//change to support dynamic column in scoring upload
				errors.rejectValue("", errorCode, arguments, errorMessage);
			}else{
				errors.rejectValue(fieldSpecification.getFieldName(), errorCode, arguments, errorMessage);			 
			}
		}
	}

	private boolean isValidDate(int month, int day, int year){
		boolean validDate = false;
		if((month >= 1 && month <= 12) && (day >= 1 && day <= 31)){
            //For months with 30 days
            if((month == 4 || month == 6 || month == 9 || month == 11) && (day <= 30))            {
                validDate = true;
            }
            //For months with 31 days
            if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day <= 31)){
                validDate = true;
            }
            //For February
            if((month == 2) && (day < 30)){
                //Boolean for valid leap year
                boolean validLeapYear = false;
                //A leap year is any year that is divisible by 4 but not divisible by 100 unless it is also divisible by 400
                if((year % 400 == 0) || ((year % 4 == 0) && (year %100 !=0))){
                    validLeapYear = true;
                }
                if (validLeapYear == true && day <= 29){
                    validDate = true;
                }else if (validLeapYear == false && day <= 28){
                    validDate = true;
                }
            }
        }
		return validDate;
	}
	
	private Integer getRegexFlags(FieldSpecification fieldSpecification) {
		Integer regexFlags = null;
		if (StringUtils.isNotEmpty(fieldSpecification.getRegexModeFlags())) {
			String flags = fieldSpecification.getRegexModeFlags();
			List<Integer> convertedFlags = new ArrayList<Integer>(flags.length());
			for (char c : flags.toCharArray()) {
				switch (c) {
					case 'i': convertedFlags.add(Pattern.CASE_INSENSITIVE); break;
					case 'm': convertedFlags.add(Pattern.MULTILINE); break;
					default: break;
				}
			}
			
			for (Integer flag : convertedFlags) {
				if (regexFlags == null) {
					regexFlags = flag;
				} else {
					regexFlags |= flag;
				}
			}
		}
		return regexFlags;
	}
	
	public Long getUploadTypeId() {
		return uploadTypeId;
	}

	public void setUploadTypeId(Long uploadTypeId) {
		this.uploadTypeId = uploadTypeId;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	
	public String getUploadTypeCode() {
		return uploadTypeCode;
	}

	public void setUploadTypeCode(String uploadTypeCode) {
		this.uploadTypeCode = uploadTypeCode;
	}
	
	
}