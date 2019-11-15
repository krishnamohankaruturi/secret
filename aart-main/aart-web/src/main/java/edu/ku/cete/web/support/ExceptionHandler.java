package edu.ku.cete.web.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import edu.ku.cete.service.exception.NoAccessToResourceException;

public class ExceptionHandler extends SimpleMappingExceptionResolver {
	private String ajaxErrorView;
	private String ajaxErrorMessage = "An error occurred";
	private String ajaxDefaultErrorMessage = "An error has occurred"; 
	private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
    protected ModelAndView doResolveException(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);
		if (isAjax(httpRequest)) {
        	String exceptionMessage = ajaxDefaultErrorMessage;

        	if (ex instanceof NoAccessToResourceException){
        		exceptionMessage = "{\"errorType\": \"UNKNOWN\", \"errorMessage\": \"You don't have permission to access this information.\"}";
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }else{
        		exceptionMessage = "{\"errorType\": \"UNKNOWN\", \"errorMessage\": \"Internal Server Error Occurred.\"}";
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            ModelAndView mv = new ModelAndView(this.getAjaxErrorView());
            mv.addObject("exceptionMessage", exceptionMessage);
            return mv;
        } else {
            return super.doResolveException(httpRequest, httpResponse, handler, ex);
        }
    }
	  
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public void setAjaxErrorMessage(String ajaxErrorMessage) {
        this.ajaxErrorMessage = ajaxErrorMessage;
    }

    public void setAjaxErrorView(String ajaxErrorView) {
        this.ajaxErrorView = ajaxErrorView;
    }

    public String getAjaxErrorMessage() {
        return ajaxErrorMessage;
    }

    public String getAjaxErrorView() {
        return ajaxErrorView;
    }
	
	
	 
	     
}