package edu.ku.cete.batch.upload;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.InitializingBean;

public class BatchUploadLineMapper<T> implements LineMapper<T>, InitializingBean{

	private DelimitedLineTokenizer tokenizer;

	private FieldSetMapper<T> fieldSetMapper;

	private StepExecution stepExecution;

    @Override
	public T mapLine(String line, int lineNumber) throws Exception {
    		if(!tokenizer.hasNames()) {
    			String[] fieldNames = (String[]) stepExecution.getExecutionContext().get("fieldNames");
    			tokenizer.setNames(fieldNames); 
    		}
    		line = lineNumber + "," + line;
		return fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
	}

	public void setLineTokenizer(DelimitedLineTokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
		this.fieldSetMapper = fieldSetMapper;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
}
