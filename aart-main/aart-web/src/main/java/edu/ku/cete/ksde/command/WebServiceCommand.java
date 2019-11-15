package edu.ku.cete.ksde.command;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
/**
 * @author 
 * 
 */
public interface WebServiceCommand {
	
	/**
	 * @param mv
	 * @return  ModelAndView
	 */
	public ModelAndView execute(ModelAndView mv);

}
