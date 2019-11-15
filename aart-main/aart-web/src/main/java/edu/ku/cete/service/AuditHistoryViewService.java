package edu.ku.cete.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ku.cete.report.domain.AuditType;

public interface AuditHistoryViewService {


	void downloadAuditHistory(HttpServletResponse response, HttpServletRequest request);

	void downloadKAPCustomExtract(HttpServletResponse response, HttpServletRequest request);

	List<AuditType> getAuditTypeList();
}
