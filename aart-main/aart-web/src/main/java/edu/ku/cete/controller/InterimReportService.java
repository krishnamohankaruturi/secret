package edu.ku.cete.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ku.cete.domain.user.User;

public interface InterimReportService {
	public void returnStudentReport(final HttpServletResponse response, final HttpServletRequest request, User user)
			throws IOException;

	public void returnItemReport(final HttpServletResponse response, final HttpServletRequest request, User user)
			throws IOException;

	public void returnClassReport(final HttpServletResponse response, final HttpServletRequest request, User user)
			throws IOException;

	public void returnClassReportCSV(final HttpServletResponse response, final HttpServletRequest request, User user)
			throws IOException;

	public void returnStudentReportCSV(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException;

	public void returnItemReportCSV(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException;

	public List<String[]> studentReportCSVByGrid(Long testSessionId, User user);

	public List<String[]> classReportCSVByGrid(Long testSessionId, User user);

	public List<String[]> itemReportCSVByGrid(Long testSessionId, User user);

}