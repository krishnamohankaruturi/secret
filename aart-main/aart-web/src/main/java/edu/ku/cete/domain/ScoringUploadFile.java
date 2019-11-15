package edu.ku.cete.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.domain.validation.FieldSpecification;
/**
 * Added By Sudhansu.b
 * Feature: f430
 * Scoring Upload
 */
public class ScoringUploadFile {
	
	private ObjectMapper mapper = new ObjectMapper();
	private Long id;
	private Long testId;
	private String fileName;
	private String filePath;
	private Long assessmentProgramId;
	private String headerColumn;
	private Date createdDate;
	private Long createdUser;
	private Boolean activeFlag;
	private List<FieldSpecification> fieldSpecifications = new ArrayList<FieldSpecification>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getHeaderColumn() {
		return headerColumn;
	}

	public void setHeaderColumn(String headerColumn) {
		this.headerColumn = headerColumn;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public List<FieldSpecification> getFieldSpecifications() {
		
		if(this.fieldSpecifications.size() == 0 && this.headerColumn != null){
		   	try {
				JsonNode fieldList = mapper.readTree(headerColumn);
				Entry<String, JsonNode> entry = null;;
				String key  = null;
				JsonNode node = null;
				for(Iterator<Entry<String, JsonNode>> it = fieldList.fields();it.hasNext();){
					entry = it.next();
					key = entry.getKey();					
					if(fieldList.get(key) != null && fieldList.get(key).isArray()){ 					 
						 ArrayNode fields = (ArrayNode) fieldList.get(key);
						 if(fields.size() > 0 && fields.get(0).isObject() ){							 
							 for(int i = 0; i<fields.size();i++){
								 node = fields.get(i);
								 FieldSpecification fieldSpecification = mapper.treeToValue(node, FieldSpecification.class);
								 fieldSpecifications.add(fieldSpecification);
							 }
						 }
				    }
			   }
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return this.fieldSpecifications;
	}

}
