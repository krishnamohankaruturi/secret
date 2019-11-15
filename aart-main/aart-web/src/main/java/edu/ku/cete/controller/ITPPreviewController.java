package edu.ku.cete.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ITPPreviewController {
	private Logger LOGGER = LoggerFactory.getLogger(ITPPreviewController.class);
	
    @Value("${nfs.url}")
	private String MEDIA_PATH;
    
	@RequestMapping(value = "getItpPreview.htm", method = RequestMethod.GET)
	public final ModelAndView getItpPreview(final @RequestParam String packagePath) {
		LOGGER.debug("--->getItpPreview");
		ModelAndView mav = new ModelAndView("previewITPTest");
		mav.addObject("packagePath", packagePath);
		mav.addObject("MEDIA_PATH", MEDIA_PATH);
		LOGGER.debug("<---getItpPreview");
		return mav;
	}

}
