package edu.ku.cete.service;

import java.util.Map;

import org.springframework.validation.BeanPropertyBindingResult;

public interface BatchUploadCustomValidationService {
	Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames);
}
