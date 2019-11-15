/**
 * 
 */
package edu.ku.cete.domain.dashboard;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * @author pegiribidi_sta
 *
 */
public class CourseAttr {

	private JsonNode subject;

	public CourseAttr(int rowSpan){
		ObjectMapper mapper = new ObjectMapper();
		
		this.subject = mapper.createObjectNode();
		if (rowSpan==0){
			((ObjectNode)this.subject).put("display", "none");
		}else{
			((ObjectNode)this.subject).put("rowspan", rowSpan);
		}
	}
	/**
	 * @return the subject
	 */
	public JsonNode getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(JsonNode subject) {
		this.subject = subject;
	}
	
	
}
