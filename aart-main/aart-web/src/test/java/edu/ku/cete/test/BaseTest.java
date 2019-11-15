package edu.ku.cete.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/springdisp-servlet.xml", 
	"file:src/main/webapp/WEB-INF/spring-batch.xml", 
	"file:src/main/webapp/WEB-INF/upload-batch.xml"
})
@WebAppConfiguration
public class BaseTest {
}
