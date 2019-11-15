package edu.ku.cete.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.domain.validation.FieldSpecification;

public class PermissionUploadFile {
	
	private Logger LOGGER = LoggerFactory.getLogger(PermissionUploadFile.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	private Long id;
	private Long uploadId;
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

	
	
	public Long getUploadId() {
		return uploadId;
	}

	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
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
				LOGGER.info("Header validation failed while processing JSON in Upload Permission "+ e.getMessage());
			} catch (IOException e) {
				LOGGER.info("Header validation failed for Upload Permission "+ e.getMessage());
			}
		}	
		return this.fieldSpecifications;
	}

}
