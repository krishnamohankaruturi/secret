package edu.ku.cete.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.lm.webservice.client.LMNodeParser;

public class JsonParserTest {
	File file = null;
	ObjectMapper mapper = null;	
	FileInputStream fileInputStream = null;
	@Autowired
	private LmAttributeConfiguration lmAttributeConfiguration;
	
	@Before
	public void setUp() throws Exception {
		file = new File("src/test/resources/file-json2.txt");
		mapper = new ObjectMapper();
		fileInputStream = new FileInputStream(file);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void verifyNodes() throws JsonParseException,
			JsonMappingException, IOException {
		Map<String, Object> nodeData = mapper.readValue(file, Map.class);
		Assert.notNull(nodeData, "Node data is null");
		Assert.notNull(nodeData.get("nodes"), "Nodes are not present");
	}
	
	@Test
	public final void verifyNodeId() throws JsonParseException,
			JsonMappingException, IOException {
		Set<Long> nodeIds = new HashSet<Long>();
		nodeIds.add((long) 17797);
		nodeIds.add((long) 17798);
		Set<LmNode> lmNodes = LMNodeParser.getInstance(lmAttributeConfiguration).getLmNodes(fileInputStream);
		Assert.notEmpty(lmNodes, "No LMNodes parsed");
	}
}
