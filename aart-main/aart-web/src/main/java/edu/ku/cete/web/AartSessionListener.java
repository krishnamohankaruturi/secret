package edu.ku.cete.web;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartSessionListener implements HttpSessionListener{

    private static final ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();
    private final Logger logger = LoggerFactory.getLogger(AartSessionListener.class);
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		sessions.put(arg0.getSession().getId(),arg0.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		sessions.remove(arg0.getSession().getId());
		logger.info(arg0.getSession().getId() + " destroyed");
	}
	
	public static HttpSession find(String sessionId) {
        return sessions.get(sessionId);
    }

	public static Collection<HttpSession> getTotalActiveSessions() {
		return sessions.values();
	}


}
