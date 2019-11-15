package edu.ku.cete.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.JsonResultSet;

public class InvoiceJsonParserTest {
	File file = null;
	ObjectMapper mapper = null;	
	FileInputStream fileInputStream = null;
	
	@Before
	public void setUp() throws Exception {
		file = new File("src/test/resources/invoice-json.txt");
		mapper = new ObjectMapper();
		fileInputStream = new FileInputStream(file);		
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public final void verifyNodes() throws JsonParseException,
			JsonMappingException, IOException {
		Map<String, Object> nodeData = mapper.readValue(file, Map.class);
		Assert.notNull(nodeData, "Node data is null");
		Assert.notNull(nodeData.get("page"), "Nodes are not present");
		System.out.println(nodeData.get("page"));
		Assert.notNull(nodeData.get("total"), "Nodes are not present");
		System.out.println(nodeData.get("total"));
		Assert.notNull(nodeData.get("records"), "Nodes are not present");
		System.out.println(nodeData.get("records"));
		
	}
	@Test
	public final void verifyNodes2() throws JsonParseException,
			JsonMappingException, IOException {		
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		JsonResultSet jsonResultSet = mapper.readValue(file, JsonResultSet.class);
		System.out.println("Invoice Json "+jsonResultSet);
		mapper.writeValue(new File("src/test/resources/invoice-json2.txt"),
				jsonResultSet);
	}	

}
