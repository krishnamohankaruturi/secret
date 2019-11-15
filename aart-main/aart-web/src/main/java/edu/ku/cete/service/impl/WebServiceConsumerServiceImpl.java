package edu.ku.cete.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.ksde.command.KidsCommand;
import edu.ku.cete.ksde.command.RosterCommand;
import edu.ku.cete.service.WebServiceConsumerService;

@Service
public class WebServiceConsumerServiceImpl implements WebServiceConsumerService {

	private static final Log LOGGER = LogFactory.getLog(WebServiceConsumerServiceImpl.class);
	
	@Autowired
	private KidsCommand kidsCommand;

	@Autowired
	private RosterCommand rostersCommand;
	
	@Override
	public ModelAndView invokeWebServices(ModelAndView mv) {
		try {
		mv = kidsCommand.execute(mv);
		} catch (Exception e) {
			LOGGER.error("kidscommand failed",e);
		}
		try {
			//mv = rostersCommand.execute(mv);
		} catch (Exception e) {
			LOGGER.error("rostercommand failed",e);
		}
		return mv;
	}

}
