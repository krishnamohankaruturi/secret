package edu.ku.cete.controller.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import edu.ku.cete.controller.DownloadController;
import edu.ku.cete.controller.NewReportController;

@Configuration
@EnableWebMvc
public class WebAppContext  extends WebMvcConfigurerAdapter {

	
	@Bean
	NewReportController newReportController() {
		NewReportController c = new NewReportController();
		return c;
	}
	
	@Bean
	DownloadController downloadController() {
		DownloadController d = new DownloadController();
		return d;
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	    configurer
	    	.favorPathExtension(false)
	    	.ignoreAcceptHeader(false)
	    	.defaultContentType(MediaType.APPLICATION_JSON);
	}
}