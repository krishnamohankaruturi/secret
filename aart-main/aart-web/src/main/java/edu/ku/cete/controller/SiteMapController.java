/**
 * 
 */
package edu.ku.cete.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author neil.howerton
 * 
 *  SiteMapController returns comingsoon and sitemap view.
 */
@Controller
public class SiteMapController {

    private Logger logger = LoggerFactory.getLogger(SiteMapController.class);
    private static final String COMING_SOON = "/comingSoon";
    private static final String SITE_MAP = "/siteMap";

    
    /**
     * Displays the Coming Soon page.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "comingSoon.htm")
    public final ModelAndView viewComingSoonPage(final HttpServletRequest request) {
        logger.trace("Enterind the viewComingSoonPage() method.");
        ModelAndView mav = new ModelAndView(COMING_SOON);
        logger.trace("Leaving the viewComingSoonPage() method");
        return mav;
    }
    
    /**
     * Displays the Site Map page.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "siteMap.htm")
    public final ModelAndView viewSiteMap(final HttpServletRequest request) {
        logger.trace("Enterind the viewSiteMapPage() method.");
        ModelAndView mav = new ModelAndView(SITE_MAP);
        logger.trace("Leaving the viewSiteMapPage() method");
        return mav;
    }    
    
    
}
