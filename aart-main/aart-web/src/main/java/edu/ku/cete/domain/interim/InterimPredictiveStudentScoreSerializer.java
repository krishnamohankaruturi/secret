package edu.ku.cete.domain.interim;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InterimPredictiveStudentScoreSerializer extends StdSerializer<InterimPredictiveStudentScore> {

	private static final long serialVersionUID = -2129150472746858585L;

	public InterimPredictiveStudentScoreSerializer() {
		this(null);
	}

	public InterimPredictiveStudentScoreSerializer(Class<InterimPredictiveStudentScore> t) {
		super(t);
	}

	@Override
	public void serialize(InterimPredictiveStudentScore irs, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		jgen.writeNumberField("schoolYear", irs.getSchoolYear());
		jgen.writeStringField("stateStudentIdentifier", irs.getStateStudentIdentifier());
		jgen.writeStringField("studentLegalLastName", irs.getStudentLegalLastName());		
		jgen.writeStringField("studentLegalFirstName", irs.getStudentLegalFirstName());		
		jgen.writeStringField("subject", irs.getSubject());		
		jgen.writeStringField("districtIdentifier", irs.getDistrictIdentifer());		
		jgen.writeStringField("districtName", irs.getDistrictName());		
		jgen.writeStringField("schoolIdentifier", irs.getSchoolIdentifier());		
		jgen.writeStringField("schoolName", irs.getSchoolName());		
		jgen.writeStringField("grade", irs.getGrade());		
		jgen.writeStringField("summativeScaleScore", irs.getSummativeScaleScore());		
		jgen.writeStringField("summativeLevel", irs.getSummativeLevel());
		
		for (InterimPredictiveStudentScoreRange ipr: irs.getInterimPredictiveStudentScoreRange()){
			jgen.writeStringField(ipr.getTestingCycleName()+"_Predicted_Score_Range_Low", ipr.getLow() );
			jgen.writeStringField(ipr.getTestingCycleName()+"_Predicted_Score_Range_High", ipr.getHigh() );
		}

		jgen.writeEndObject();


	}

}
