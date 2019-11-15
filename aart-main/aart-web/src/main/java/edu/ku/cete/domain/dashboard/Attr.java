package edu.ku.cete.domain.dashboard;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Attr{
	private JsonNode assessmentProgram;

	public Attr(int rowSpan){
		ObjectMapper mapper = new ObjectMapper();
		this.assessmentProgram = mapper.createObjectNode();
		if (rowSpan==0){
			((ObjectNode)this.assessmentProgram).put("display", "none");
		}else{
			((ObjectNode)this.assessmentProgram).put("rowspan", rowSpan);
		}
	}
	public JsonNode getAssessmentProgram() {
		return assessmentProgram;
	}
	public void setAssessmentProgram(JsonNode assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
}