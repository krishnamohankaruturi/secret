package edu.ku.cete.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import edu.ku.cete.constants.validation.InvalidTypes;

public class InvalidTypesSerializer extends JsonSerializer<InvalidTypes> {
	private static Logger logger = LoggerFactory.getLogger(InvalidTypesSerializer.class);
	private Properties messages;
	
	public InvalidTypesSerializer(){
		messages = new Properties();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			messages.load(cl.getResourceAsStream("messages.properties"));
		} catch (Exception e) {
			logger.error("Exception: ", e);
		} 
	}
	
	@Override
	public void serialize(InvalidTypes value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeFieldName("message");
		jgen.writeString(messages.getProperty(value.name()));
		jgen.writeFieldName("name");
		jgen.writeString(value.name());
		jgen.writeEndObject();
		
	}

}
