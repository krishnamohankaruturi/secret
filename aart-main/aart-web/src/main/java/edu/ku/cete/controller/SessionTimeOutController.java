package edu.ku.cete.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SessionTimeOut form controller.
 */
@Controller 
public class SessionTimeOutController {

    /** Logger for this class and subclasses. */
    private final Log logger = LogFactory.getLog(getClass());   
     
    @RequestMapping(value = "sessionTimedOut.htm", method = RequestMethod.GET)
    public final void sessionTimedOut() {
        logger.trace("Enter/Exit the sessionTimedOut() method."); 
    }
    
    @RequestMapping(value = "/keep-alive.htm", method = RequestMethod.GET)
    @ResponseBody
    public final String keepAlive() {
        logger.trace("Enter/Exit the keepAlive method."); 
        return "OK";
    }

}
