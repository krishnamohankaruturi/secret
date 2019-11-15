/**
 * 
 */
package edu.ku.cete.model.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author m802r921
 *
 */
public class FileUploadValidator implements Validator {

	@SuppressWarnings("rawtypes")
	@Override
	public final boolean supports(Class clazz) {
		//just validate the FileUpload instances
		return UploadFile.class.isAssignableFrom(clazz);
	}

	@Override
	public final void validate(Object target, Errors errors) {
		UploadFile file = (UploadFile) target;

		if (file == null || file.getFile() == null
				|| !file.getFile().exists()) {
			errors.rejectValue("fileData", "required.fileUpload");
		} else if (!file.getFile().getName().endsWith(ParsingConstants.CSV)) {
			errors.rejectValue("fileData", "invalid.filetype");
		}
		if (file.getSelectedRecordTypeId() < 1) {
			errors.rejectValue("selectedRecordTypeId", "required.recordType");
		}else{
			if (file.getRosterUpload() == 1 && file.getStateId() == 0){
				errors.rejectValue("stateId", "required.state");
			} else if (file.getRosterUpload() == 1 && file.getStateId() == -1 && file.getDistrictId() == 0){
				errors.rejectValue("districtId", "required.district");
			} else if (file.getRosterUpload() == 1 && file.getStateId() == -1 && file.getDistrictId() == -1 && file.getSchoolId() == 0){
				errors.rejectValue("schoolId", "required.school");
			}
		}
	}
}