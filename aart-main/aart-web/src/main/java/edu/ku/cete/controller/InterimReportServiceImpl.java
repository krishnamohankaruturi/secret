package edu.ku.cete.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.Foil;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.report.ClassRosters;
import edu.ku.cete.domain.report.InterimClassResults;
import edu.ku.cete.domain.report.InterimReportTasks;
import edu.ku.cete.domain.report.InterimReportsByStudent;
import edu.ku.cete.domain.report.InterimReportsByStudentList;
import edu.ku.cete.domain.report.InterimResultsTypes;
import edu.ku.cete.domain.report.InterimStudentResponse;
import edu.ku.cete.domain.report.InterimTestItems;
import edu.ku.cete.domain.report.ItemPercentCorrect;
import edu.ku.cete.domain.report.ItemReport;
import edu.ku.cete.domain.report.PercentCorrect;
import edu.ku.cete.domain.report.Questions;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.StudentsResponsesDao;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.test.FoilDao;
import edu.ku.cete.pdf.PDFGeneratorUtil;
import edu.ku.cete.service.InterimService;

@Service
public class InterimReportServiceImpl implements InterimReportService {

	private Logger LOGGER = LoggerFactory.getLogger(InterimReportServiceImpl.class);

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private InterimService interimService;

	@Autowired
	private StudentsResponsesDao studentsResponsesDao;

	@Autowired
	private FoilDao foilDao;

	@Autowired
	private GroupsDao groupsDao;
	
	@Autowired
	private StudentsTestsDao studentsTestsDao;


	@Override
	public void returnStudentReport(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException {

		OutputStream out = response.getOutputStream();
		response.setContentType("application/pdf");
		Long testSessionID = new Long(request.getParameter("testSessionId"));
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		ts.getName();
		String studentName = ts.getName() + "_" + ts.getId() + "_" + "StudentReportDto";

		String fileName = studentName + ".pdf";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		InterimReportsByStudentList irbsl = generateInterimReportsByStudent(testSessionID, user, false);
		try {
			voidSetupStudentPDFGeneration(irbsl, out, serverPath);
		} catch (Exception e) {
			LOGGER.error("Exception while generating student report", e);
		}
		return;
	}

	@Override
	public void returnItemReport(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException {
		OutputStream out = response.getOutputStream();
		response.setContentType("application/pdf");
		Long testSessionID = new Long(request.getParameter("testSessionId"));
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		ts.getName();
		String studentName = ts.getName() + "_" + ts.getId() + "_" + "ItemReport";

		String fileName = studentName + ".pdf";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		ItemReport ir = generateItemReport(ts.getId(), user, false);
		try {
			voidSetupItemPDFGeneration(ir, out, serverPath);
		} catch (Exception e) {
			LOGGER.error("Exception while generating Item report", e);
		}
		return;
	}

	@Override
	public void returnClassReport(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException {

		OutputStream out = response.getOutputStream();
		response.setContentType("application/pdf");
		Long testSessionID = new Long(request.getParameter("testSessionId"));
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		String studentName = ts.getName() + "_" + ts.getId() + "_" + "TestSummary";
		InterimClassResults icr = generateClassResultReport(testSessionID, user, false);

		String fileName = studentName + ".pdf";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		try {
			voidSetupClassPDFGeneration(icr, out, serverPath);
		} catch (Exception e) {
			LOGGER.error("Exception while generating Class report", e);
		}
		return;
	}

	@Override
	public void returnStudentReportCSV(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException {
		response.setContentType("application/force-download");
		Long testSessionID = new Long(request.getParameter("testSessionId"));
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		ts.getName();
		String studentName = ts.getName() + "_" + ts.getId() + "_" + "StudentReportDto";

		String fileName = studentName + ".csv";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		InterimReportsByStudentList irbsl = generateInterimReportsByStudent(testSessionID, user, true);
		try {
			writeClassStudentCSV(irbsl, fileName, response, serverPath);
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("Exception while generating student report (CSV)", e);
		}
		return;
	}

	@Override
	public void returnClassReportCSV(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException {
		response.setContentType("application/force-download");
		Long testSessionID = new Long(request.getParameter("testSessionId"));
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		String studentName = ts.getName() + "_" + ts.getId() + "_" + "TestSummary";
		InterimClassResults icr = generateClassResultReport(testSessionID, user, true);

		String fileName = studentName + ".csv";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		try {
			writeClassReportCSV(icr, fileName, response, serverPath);
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("Exception while generating class report (CSV)", e);
		}
		return;

	}

	@Override
	public void returnItemReportCSV(HttpServletResponse response, HttpServletRequest request, User user)
			throws IOException {
		response.setContentType("application/force-download");
		Long testSessionID = new Long(request.getParameter("testSessionId"));
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		ts.getName();
		String studentName = ts.getName() + "_" + ts.getId() + "_" + "ItemReport";

		String fileName = studentName + ".csv";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		ItemReport ir = generateItemReport(ts.getId(), user, true);
		try {
			writeItemReportCSV(ir, fileName, response, serverPath);
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("Exception while generating Item report (CSV)", e);
		}
		return;
	}

	private InterimReportsByStudentList generateInterimReportsByStudent(Long testSessionID, User user, boolean isCsv) {
		List<InterimReportsByStudent> irbsList = new ArrayList<InterimReportsByStudent>();
		Long groupId = user.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		List<Student> studentList = null;
		try {
			Long roleOrgTypeId = groups.getRoleorgtypeid();
			if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
				isTeacher=Boolean.TRUE;
			}
		} catch (Exception e) {
			LOGGER.error("Exception while generating report", e);
		}
		try {
			studentList = interimService.getStudentIdByTestsessionIdInterim(testSessionID,
					user.getCurrentOrganizationId(), user.getCurrentContextUserId(), isTeacher,
					user.getContractingOrganization().getCurrentSchoolYear());
		} catch (Exception e) {
			LOGGER.info("Exception in Fetching Students", e);
		}

		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionID);
		List<TaskVariant> tvList = interimService.getQuestionsForScoring(ts.getTestId());
		InterimReportsByStudentList irbsl = new InterimReportsByStudentList();

		irbsl.setTestName(" " + ts.getName());

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);

		for (Student student : studentList) {
			List<InterimStudentResponse> interimStudentResponseList = new ArrayList<InterimStudentResponse>();
			BigDecimal totalPoints = new BigDecimal(0.00);
			BigDecimal maxTotalScore = new BigDecimal(0.00);
			BigDecimal totalPercentage = new BigDecimal(0.00);
			Integer i = 1;
			for (TaskVariant tv : tvList) {
				String correctResponse = "";
				String studentResponse = "";
				BigDecimal percentage = new BigDecimal(0.00);
				BigDecimal points = new BigDecimal(0.00);
				StudentsResponses srs = studentsResponsesDao.getStudentResponseInterim(student.getId(), tv.getId(),
						ts.getTestId(), ts.getId());
				List<Long> pos = new ArrayList<Long>();
				/*
				 * Fetching Details of Tasks for each task and then storing
				 * their options (I.E. foils)
				 */
				if (tv.getTaskType() == null) {
					tv.setTaskType("MC-K");
				}
				if (tv.getTaskType().equalsIgnoreCase("itp")) {
					tv.setTaskType(tv.getTaskSubType());
				}
				if (tv.getTaskType().equalsIgnoreCase("MC-K") || tv.getTaskType().equalsIgnoreCase("MC-MS")) {
					List<Foil> foilList = foilDao.selectByTaskVariantId(tv.getId());
					for (Foil foil : foilList) {
						LOGGER.debug("Foil ID: " + foil.getId() + " For Task Variant" + tv.getId());
						pos.add(foil.getId());
					}
					for (Foil foil : foilList) {
						if (foil.getCorrectResponse()) {
							correctResponse = correctResponse + "Opt "
									+ new Long(pos.indexOf(foil.getId()) + 1l).toString() + " ";
						}
					}
				} else {
					correctResponse = "N/A";
				}
				if (srs != null) {
					List<Foil> studentResponseFoils = new ArrayList<Foil>();
					if (tv.getTaskType().equalsIgnoreCase("MC-K")) {
						studentResponseFoils.add(foilDao.selectByPrimaryKey(srs.getFoilId()));
						for (Foil foil : studentResponseFoils) {
							try {
								if(foil!= null){
								studentResponse = "" + "Opt " + new Long(pos.indexOf(foil.getId()) + 1l).toString()
										+ " ";
								}
								else{
									studentResponse="-";
								}
							} catch (Exception e) {
								LOGGER.info("Error in Fetching Student Response for student" + student.getId()
										+ "Foil Id=" + foil.getId().toString() + "Position Array=" + pos.toString(), e);
							}
						}
					} else if (tv.getTaskType().equalsIgnoreCase("MC-MS")) {
						if (srs.getResponse() != null) {
							String resp = srs.getResponse();
							resp = resp.replace("[", "");
							resp = resp.replace("]", "");
							String[] respList = resp.split(",");
							List<Long> respListLong = new ArrayList<Long>();

							for (String r : respList) {
								try {
									respListLong.add(new Long(r));
								} catch (Exception e) {

								}
							}
							for (Long respLong : respListLong) {
								try {
									studentResponse = studentResponse + "" + "Opt "
											+ new Long(pos.indexOf(respLong) + 1l).toString() + " ";
								} catch (Exception e) {
									LOGGER.info("Error in Fetching Student Response for student" + student.getId()
											+ "Foil Id=" + respLong.toString() + "Position Array=" + pos.toString(), e);
								}
							}
						}
					} else if (tv.getTaskType().equalsIgnoreCase("CR")) {
						if (srs.getResponse() != null) {
							studentResponse = srs.getResponse().replace("~", "");
						} else {
							studentResponse = "";
						}
					}
					if(srs.getScore()!=null){
					points = srs.getScore().setScale(2,BigDecimal.ROUND_HALF_DOWN);
					}

				} else {
					studentResponse = "-";
				}				
				if (tv.getMaxScore() == null) {
					tv.setMaxScore(1);
				}
				if (tv.getScoringMethod() == null) {
					tv.setScoringMethod("correctOnly");
				}
				BigDecimal maxScore = new BigDecimal(tv.getMaxScore());
				Double a=points.doubleValue();
				Double b=maxScore.doubleValue();
				percentage= new BigDecimal(a*100/b);
				
				InterimStudentResponse interimStudentResponse = new InterimStudentResponse();
			
				if(tv.getScoringNeeded()){
					totalPoints = totalPoints.add(points);
					maxTotalScore = maxTotalScore.add(maxScore.setScale(2,BigDecimal.ROUND_HALF_DOWN));
					interimStudentResponse.setPointValue(tv.getMaxScore().toString());
				}
				else {
					interimStudentResponse.setPointValue("0");
				}
				interimStudentResponse.setCorrectAnswer(correctResponse);
				interimStudentResponse.setItemType(tv.getTaskType());
				interimStudentResponse.setPercentage(df.format(percentage));
				interimStudentResponse.setQuestionName("Q" + i.toString());
				interimStudentResponse.setScore(df.format(points.setScale(2)));
				interimStudentResponse.setScoringType(tv.getScoringMethod().toUpperCase());

				if (isCsv) {
					interimStudentResponse.setStudentAnswer(studentResponse);
					interimStudentResponse.setAlignment(tv.getContentCode());
				} else {
					interimStudentResponse.setStudentAnswer(studentResponse.replaceAll("(.{4})", "$1\n"));
					interimStudentResponse.setAlignment(tv.getContentCode().replaceAll("(.{4})", "$1\n"));
				}
				i++;
				interimStudentResponseList.add(interimStudentResponse);
			}
			try {
				Double a=totalPoints.doubleValue();
				Double b=maxTotalScore.doubleValue();
				totalPercentage= new BigDecimal(a*100/b);
				
			
			} catch (Exception e) {
			}
			List<StudentsTests> stsp = studentsTestsDao.findByTestSessionAndStudentId(testSessionID,
					Long.valueOf(student.getId()));			
			InterimReportsByStudent interimReportsByStudent = new InterimReportsByStudent();
			interimReportsByStudent.setFirstName(student.getLegalFirstName());
			interimReportsByStudent.setLastName(student.getLegalLastName());
			interimReportsByStudent.setStateId(student.getStateStudentIdentifier());
			interimReportsByStudent
					.setTestDuration(ts.getWindowStartTimeString() + " " + " - " + " " + ts.getWindowEndTimeString());
			if(stsp.get(0).getEndDateTime()!=null){
			SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
			Date d=stsp.get(0).getEndDateTime();
			interimReportsByStudent.setTestEndDate(df2.format(d));
			}
			else{
				interimReportsByStudent.setTestEndDate("-");

			}
			interimReportsByStudent.setTestSessionName(ts.getName());
			interimReportsByStudent.setTestStartDate(ts.getWindowEffectiveDateString());
			interimReportsByStudent
					.setTotalpointsOrpercentage(df.format(totalPoints) + " / " + df.format(totalPercentage) + "%");
			interimReportsByStudent.setTotalPoint(df.format(totalPoints));
			interimReportsByStudent.setTotalPercentage(df.format(totalPercentage) + "%");
			interimReportsByStudent.setInterimStudentResponse(interimStudentResponseList);
			interimReportsByStudent.setMaxTotalScore(df.format(maxTotalScore));
			irbsList.add(interimReportsByStudent);

		}

		irbsl.setStudentList(irbsList);
		return irbsl;

	}

	private ItemReport generateItemReport(Long testSessionId, User user, boolean isCsv) {
		Long groupId = user.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		List<Student> studentList = null;
		int maxResp = 1;
		try {
			if (groups != null && groups.getGroupCode() != null && groups.getGroupCode().equalsIgnoreCase("tea")) {
				isTeacher = Boolean.TRUE;
			}
		} catch (Exception e) {

		}

		studentList = interimService.getStudentIdByTestsessionIdInterim(testSessionId, user.getCurrentOrganizationId(),
				user.getCurrentContextUserId(), isTeacher, user.getContractingOrganization().getCurrentSchoolYear());
		TestSession ts = interimService.getTestSessionByTestSessionId(testSessionId);
		List<TaskVariant> tvList = interimService.getQuestionsForScoring(ts.getTestId());
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		ItemReport itemReport = new ItemReport();
		List<InterimTestItems> itt = new ArrayList<InterimTestItems>();
		Integer i = 1;
		for (TaskVariant tv : tvList) {
			BigDecimal totalPoints = new BigDecimal(0.00);
			// BigDecimal maxTotalScore = new BigDecimal(0.000);
			// BigDecimal totalPercentage = new BigDecimal(0.000);
			Integer totalCorrect = new Integer(0);
			Integer totalIncorrect = new Integer(0);

			Map<Long, Integer> foilToResponseMap = new LinkedHashMap<Long, Integer>();
			Map<Long, Boolean> foilToCorrect = new LinkedHashMap<Long, Boolean>();
			List<Long> foilPosition = new ArrayList<Long>();
			if (tv.getTaskType() == null) {
				tv.setTaskType("MC-K");
			}
			if (tv.getScoringMethod() == null) {
				tv.setScoringMethod("correctOnly");
			}
			if (tv.getTaskType().equalsIgnoreCase("itp")) {
				tv.setTaskType(tv.getTaskSubType());
			}
			if (tv.getMaxScore() == null) {
				tv.setMaxScore(1);
			}
			if (tv.getTaskType().equalsIgnoreCase("MC-K") || tv.getTaskType().equalsIgnoreCase("MC-MS")) {

				List<Foil> foilList = foilDao.selectByTaskVariantId(tv.getId());
				for (Foil foil : foilList) {
					foilToCorrect.put(foil.getId(), foil.getCorrectResponse());
					foilPosition.add(foil.getId());
				}
			}

			for (Student student : studentList) {
				List<Foil> studentResponseFoils = new ArrayList<Foil>();
				StudentsResponses srs = studentsResponsesDao.getStudentResponseInterim(student.getId(), tv.getId(),
						ts.getTestId(), ts.getId());
				if (srs != null && tv.getScoringNeeded()) {
					try {
						totalPoints.add(srs.getScore().setScale(2,BigDecimal.ROUND_HALF_DOWN));
					} catch (Exception e) {
						srs.setScore(new BigDecimal(0.00));
						LOGGER.info("Exception while adding score for student " + student.getId() + "Item "
								+ srs.getTaskVariantId(), e);

					}
					if (srs.getScore().compareTo((new BigDecimal(0.00))) == 0) {
						totalIncorrect++;
					} else {
						totalCorrect++;
					}
					if (tv.getTaskType().equalsIgnoreCase("MC-K")) {
						Foil f = foilDao.selectByPrimaryKey(srs.getFoilId());
						if (f != null && f.getId() != null) {
							if (foilToResponseMap.get(f.getId()) == null) {
								foilToResponseMap.put(f.getId(), new Integer(1));
							} else {
								Integer in = foilToResponseMap.get(f.getId());
								in++;
								foilToResponseMap.put(f.getId(), in);
							}
						}

					} else if (tv.getTaskType().equalsIgnoreCase("MC-MS")) {
						if (srs.getResponse() != null) {
							String resp = srs.getResponse();
							resp = resp.replace("[", "");
							resp = resp.replace("]", "");
							String[] respList = resp.split(",");
							List<Long> respListLong = new ArrayList<Long>();

							for (String r : respList) {
								try {
									respListLong.add(new Long(r));
								} catch (Exception e) {

								}
							}
							for (Long respLong : respListLong) {

								if (foilToResponseMap.get(respLong) == null) {
									foilToResponseMap.put(respLong, new Integer(1));
								} else {
									Integer in = foilToResponseMap.get(respLong);
									in++;
									foilToResponseMap.put(respLong, in);
								}
							}
						}
					}
				} else {
					totalPoints.add(new BigDecimal(0.00));
				}

			}
			List<InterimReportTasks> interimReportTasks = new ArrayList<InterimReportTasks>();
			if (tv.getTaskType().equalsIgnoreCase("MC-K") || tv.getTaskType().equalsIgnoreCase("MC-MS")) {
				Integer j = 1;
				if (maxResp < foilPosition.size()) {
					maxResp = foilPosition.size();
				}
				for (Long position : foilPosition) {
					if (foilToResponseMap.get(position) == null) {
						foilToResponseMap.put(position, 0);
					}
					InterimReportTasks ir = new InterimReportTasks();
					if (foilToCorrect.get(position).equals(Boolean.TRUE)) {
						ir.setResponse("Opt" + j.toString() + " *");
					} else {
						ir.setResponse("Opt" + j.toString());
					}
					ir.setResponseNo(foilToResponseMap.get(position).toString());
					Double percentage = calculatePercentage(new Long(foilToResponseMap.get(position)),
							new Long(studentList.size()));
					ir.setResponsePercent(df.format(percentage) + "%");
					interimReportTasks.add(ir);
					j++;
				}

			}
			Double totalCorrectPercentage = calculatePercentage(new Long(totalCorrect),
					new Long(totalCorrect + totalIncorrect));
			Double totalInCorrectPercentage = calculatePercentage(new Long(totalIncorrect),
					new Long(totalCorrect + totalIncorrect));
			InterimTestItems it = new InterimTestItems();
			if (isCsv) {
				it.setAllignment(tv.getContentCode());
				it.setScoringType(tv.getScoringMethod().toUpperCase());
			}else {
				it.setAllignment(tv.getContentCode().replaceAll("(.{4})", "$1\n"));
				it.setScoringType(tv.getScoringMethod().toUpperCase().replaceAll("(.{5})", "$1\n"));
			}
			it.setInterimReportTasksme(totalIncorrect.toString());
			it.setItemName("Q" + i.toString());
			it.setItemType(tv.getTaskType());
			if(tv.getScoringNeeded()) {			
				it.setMaxScore(tv.getMaxScore().toString());
			}
			else { 	
				it.setMaxScore("0");
			}	
			it.setTestName(ts.getName());
			it.setTotalCorrect(df.format(totalCorrectPercentage) + "%");
			it.setTotalCorrectResponses(totalCorrect.toString());
			it.setTotalInCorrect(df.format(totalInCorrectPercentage) + "%");
			it.setResponses(interimReportTasks);
			itt.add(it);
			
			i++;
		}
		for (InterimTestItems it : itt) {
			Boolean isMcms = false;
			if (!it.getResponses().isEmpty()) {
				isMcms = true;
			}
			int responseSize = it.getResponses().size();
			for (int k = maxResp; k > responseSize; k--) {
				InterimReportTasks ir = new InterimReportTasks();

				if (!isMcms) {
					ir.setResponse("N/A");
					ir.setResponseNo("-");
					ir.setResponsePercent("-");
				}
				it.getResponses().add(ir);
			}
		}
		String width = "20in";
		if (maxResp > 5) {
			width = new Long(20 + (maxResp - 5) * 2).toString() + "in";
		}
		itemReport.setScheduledDate("Scheduled Dates: " + ts.getWindowEffectiveDateString() + " - "
				+ ts.getWindowEffectiveDateString() + " | ");
		itemReport.setScheduledTime(
				"Scheduled Time: " + ts.getWindowStartTimeString() + " - " + ts.getWindowEndTimeString() + " ");
		itemReport.setInterimTestItems(itt);
		itemReport.setTestName(ts.getName());
		itemReport.setWidth(width);
		itemReport.setRespNum(maxResp);
		return itemReport;
	}

	private InterimClassResults generateClassResultReport(Long testSessionId, User user, boolean isCsv) {
		Long groupId = user.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		List<Student> studentList = null;
		try {
			Long roleOrgTypeId = groups.getRoleorgtypeid();
			if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
				isTeacher=Boolean.TRUE;
			}
		} catch (Exception e) {

		}

		studentList = interimService.getStudentIdByTestsessionIdInterim(testSessionId, user.getCurrentOrganizationId(),
				user.getCurrentContextUserId(), isTeacher, user.getContractingOrganization().getCurrentSchoolYear());
		TestSession ts = interimService.getTestSessionDetailsByTestSessionId(testSessionId);
		List<TaskVariant> tvList = interimService.getQuestionsForScoring(ts.getTestId());
		/*
		 * Filling Data in Class Rosters: Each ClassRosters has ENtries
		 * Corresponding to all the students in that class/test
		 */
		BigDecimal highestScore = new BigDecimal(0.00);
		BigDecimal lowestScore = new BigDecimal(100000.00);
		BigDecimal lowestPercentage = null;
		BigDecimal highestPercentage = null;
		BigDecimal totalScore = new BigDecimal(0.00);
		BigDecimal totalMaxScore = new BigDecimal(0.00);
		PercentCorrect pc = new PercentCorrect();
		pc.setBelow60(0l);
		pc.setFrom60To69(0l);
		pc.setFrom70To79(0l);
		pc.setFrom80To89(0l);
		pc.setFrom90To99(0l);
		pc.setHundred100(0l);
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		List<ClassRosters> crList = new ArrayList<ClassRosters>();
		Map<Long, Long> correctResponses = new HashMap<Long, Long>();
		Map<Long, Long> inCorrectResponses = new HashMap<Long, Long>();

		for (Student student : studentList) {
			BigDecimal totalPoints = new BigDecimal(0.00);
			Long maxScore = 0l;
			Map<String, String> map = new LinkedHashMap<String, String>();
			Integer j = 1;
			for (TaskVariant tv : tvList) {
				if (tv.getMaxScore() == null) {
					tv.setMaxScore(new Integer(1));
				}
				if (tv.getTaskType().equalsIgnoreCase("itp")) {
					tv.setTaskType(tv.getTaskSubType());
				}
				
			
				StudentsResponses srs = studentsResponsesDao.getStudentResponseInterim(student.getId(), tv.getId(),
						ts.getTestId(), ts.getId());
				if (srs != null && srs.getScore() != null && tv.getScoringNeeded()) {
					maxScore = maxScore + tv.getMaxScore();
					if (srs.getScore().compareTo((new BigDecimal(0.00))) == 0) {
						if (inCorrectResponses.get(tv.getId()) != null) {
							Long inCorrectCount = inCorrectResponses.get(tv.getId());
							inCorrectCount++;
							inCorrectResponses.put(tv.getId(), inCorrectCount);
						} else {
							inCorrectResponses.put(tv.getId(), new Long(1));
						}
					} else {
						if (correctResponses.get(tv.getId()) != null) {
							Long correctCount = correctResponses.get(tv.getId());
							correctCount++;
							correctResponses.put(tv.getId(), correctCount);
						} else {
							correctResponses.put(tv.getId(), new Long(1));
						}
					}
					map.put("Q" + j.toString(), srs.getScore().setScale(2,BigDecimal.ROUND_HALF_DOWN).toString());
					totalPoints = srs.getScore().setScale(2,BigDecimal.ROUND_HALF_DOWN).add(totalPoints).setScale(2);

				} else {
					map.put("Q" + j.toString(), "-");
				}
				j++;
			}
			BigDecimal totalPercentage = new BigDecimal(0.00);
			try {
				Double a=totalPoints.doubleValue();
				Double b=new Double(maxScore);
				totalPercentage= new BigDecimal(a*100/b);
				//totalPercentage = (totalPoints.divide(new BigDecimal(maxScore))
					//	.multiply(new BigDecimal(100.00)));
			} catch (Exception e) {

			}
			if (totalPoints.compareTo(highestScore) > 0) {
				highestScore = totalPoints;
				highestPercentage = totalPercentage;
			}
			if (totalPoints.compareTo(lowestScore) < 0) {
				lowestScore = totalPoints;
				lowestPercentage = totalPercentage;
			}
			totalScore = totalScore.add(totalPoints);
			totalMaxScore = totalMaxScore.add(new BigDecimal(maxScore));

			pc.addValue(totalPercentage.intValue());
			ClassRosters cr = new ClassRosters();
			cr.setFirstName(student.getLegalFirstName());
			cr.setLastName(student.getLegalLastName());
			cr.setStudentId(student.getStateStudentIdentifier());
			cr.setTotalPercentage(df.format(totalPercentage) + "%");
			cr.setTotalPoints(totalPoints.toEngineeringString());
			Questions quest = new Questions();
			quest.setQuestionAnswer(map);
			cr.setQuestions(quest);
			crList.add(cr);
		}

		List<ItemPercentCorrect> ipc = new ArrayList<ItemPercentCorrect>();
		Integer j = 1;
		List<InterimResultsTypes> irtList = new ArrayList<InterimResultsTypes>();
		for (TaskVariant tv : tvList) {
			Long correct = correctResponses.get(tv.getId());
			Long incorrect = inCorrectResponses.get(tv.getId());
			if (correct == null) {
				correct = 0l;
			}
			if (incorrect == null) {
				incorrect = 0l;
			}
			Double correcPercentage = calculatePercentage(correct, correct + incorrect);
			Double incorrectPercentage = calculatePercentage(incorrect, correct + incorrect);

			ItemPercentCorrect ip = new ItemPercentCorrect();
			ip.setPoints(correcPercentage);
			ip.setQuestionName("Q" + j.toString());
			ipc.add(ip);

			InterimResultsTypes irt = new InterimResultsTypes();
			irt.setCorrectPercentage(df.format(correcPercentage) + "%");
			irt.setIncorrectPercentage(df.format(incorrectPercentage) + "%");
			irt.setItemType(tv.getTaskType());

			if (tv.getMaxScore() == null) {
				tv.setMaxScore(new Integer(1));
			}
			irt.setPointValue(new Long(tv.getMaxScore()).toString());
			if (tv.getScoringMethod() == null) {
				tv.setScoringMethod("correctOnly");
			}
			if (isCsv) {
				irt.setScoringType(tv.getScoringMethod().toUpperCase());
				irt.setAlignment(tv.getContentCode());
			} else {
				irt.setScoringType(tv.getScoringMethod().replaceAll("(.{4})", "$1\n").toUpperCase());
				irt.setAlignment(tv.getContentCode().replaceAll("(.{4})", "$1\n"));
			}
			irtList.add(irt);
			j++;

		}
		SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
		BigDecimal totalPercentage = new BigDecimal(0.00);
		if(totalMaxScore != null && totalMaxScore.intValue() !=0){
			totalPercentage = totalScore.divide(totalMaxScore, 4);
			totalPercentage = totalPercentage.multiply(new BigDecimal(100));
		}
		InterimClassResults icr = new InterimClassResults();
		icr.setTestName(ts.getName());
		try {
			icr.setClassHighestpointsByPercentage(
					df.format(highestScore) + " / " + df.format(highestPercentage) + "%");
			icr.setClassLowestPointsByPercentage(df.format(lowestScore) + " / " + df.format(lowestPercentage) + "%");
		} catch (Exception e) {

		}

		icr.setClassRosters(crList);
		// icr.setStartDate(df2.format(ts.getWindowEffectiveDate()));
		// icr.setEndDate(df2.format(ts.getWindowExpiryDate()));
		icr.setInterimResultsTypes(irtList);
		List questions = Arrays
				.asList(icr.getClassRosters().get(0).getQuestions().getQuestionAnswer().keySet().toArray());
		icr.setQuestions(questions);
		Integer size = studentList.size();
		icr.setTotalparticipants(size.toString());
		icr.setTotalpointsByPercentage(df.format(totalScore) + "/" + df.format(totalPercentage) + "%");
		//icr.setByItemsFilePath(generateByItemGraph(ipc));
		//icr.setByPercentCorrectFilePath(generateByPercentGraph(pc));
		Long qSize = 17l;
		int add = icr.getQuestions().size();
		if (add > 17) {
			add = add - 17;
		} else {
			add = 0;
		}
		String width = new Double(qSize + new Double(add * 0.7)).toString() + "in";
		icr.setWidth(width);
		return icr;
	}

	private String generateByPercentGraph(PercentCorrect pc) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(new Double(pc.getHundred100()), "SCORES", "100");
		dataset.addValue(new Double(pc.getFrom80To89()), "SCORES", "90-99");
		dataset.addValue(new Double(pc.getFrom70To79()), "SCORES", "80-89");
		dataset.addValue(new Double(pc.getFrom70To79()), "SCORES", "70-79");
		dataset.addValue(new Double(pc.getFrom60To69()), "SCORES", "60-69");
		dataset.addValue(new Double(pc.getBelow60()), "SCORES", "Below 60");

		JFreeChart barChart = ChartFactory.createBarChart("", "Percentages", "No. Of Students", dataset,
				PlotOrientation.HORIZONTAL, true, true, false);
		int width = 240; /* Width of the image */
		int height = 150; /* Height of the image */
		File BarChart = new File("PercentCorrectGraph.png");
		try {
			ChartUtils.saveChartAsPNG(BarChart, barChart, width, height);
		} catch (IOException e) {
			LOGGER.error("Exception while generating percent graph", e);
		}

		return BarChart.toURI().toString();
	}

	private String generateByItemGraph(List<ItemPercentCorrect> ipc) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int add = ipc.size();
		if (add > 10) {
			add = add - 10;
		}
		for (ItemPercentCorrect ip : ipc) {
			dataset.addValue(ip.getPoints(), "Items", ip.getQuestionName());
		}

		JFreeChart barChart = ChartFactory.createBarChart("Items", "Item Names", "% Students", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		int width = 350; /* Width of the image */
		int height = 155; /* Height of the image */
		File BarChart = new File("ItemCorrectGraph.png");
		try {
			ChartUtils.saveChartAsPNG(BarChart, barChart, width, height);
		} catch (IOException e) {
			LOGGER.error("Exception while generating percent graph", e);
		}

		return BarChart.toURI().toString();
	}

	private void voidSetupStudentPDFGeneration(InterimReportsByStudentList irbs, OutputStream out, String serverPath)
			throws Exception {
		// convert that data into xml
		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("InterimReportsByStudentList", InterimReportsByStudentList.class);
		xstream.alias("InterimReportsByStudent", InterimReportsByStudent.class);
		xstream.alias("InterimStudentResponse", InterimStudentResponse.class);
		TraxSource source = new TraxSource(irbs, xstream);
		Resource resource = resourceLoader.getResource("/templates/xslt/reports/ByStudentReport.xsl");
		PDFGeneratorUtil.generatePDF(source, resource.getFile(), out, serverPath);
	}

	private void voidSetupItemPDFGeneration(ItemReport ir, OutputStream out, String serverPath) throws Exception {
		// convert that data into xml
		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("ItemReport", ItemReport.class);
		xstream.alias("InterimTestItems", InterimTestItems.class);
		xstream.alias("InterimReportTasks", InterimReportTasks.class);
		TraxSource source = new TraxSource(ir, xstream);
		Resource resource = resourceLoader.getResource("/templates/xslt/reports/InterimReportByItem.xsl");
		PDFGeneratorUtil.generatePDF(source, resource.getFile(), out, serverPath);
	}

	private void voidSetupClassPDFGeneration(InterimClassResults icr, OutputStream out, String serverPath)
			throws Exception {
		// convert that data into xml
		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("interimClassResults", InterimClassResults.class);
		xstream.alias("classRosters", ClassRosters.class);
		xstream.alias("questions", Questions.class);
		xstream.alias("questionAnswer", Map.class);
		xstream.alias("interimResultsTypes", InterimResultsTypes.class);
		TraxSource source = new TraxSource(icr, xstream);
		Resource resource = resourceLoader.getResource("/templates/xslt/reports/InterimReportByClass.xsl");
		PDFGeneratorUtil.generatePDF(source, resource.getFile(), out, serverPath);
	}

	public static String toXMLString(Object object) {
		XStream xstream = null;

		xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.setMode(XStream.NO_REFERENCES);

		xstream.alias("interimClassResults", InterimClassResults.class);
		xstream.alias("classRosters", ClassRosters.class);
		xstream.alias("questions", Questions.class);
		xstream.alias("questionAnswer", Map.class);
		xstream.alias("interimResultsTypes", InterimResultsTypes.class);
		return xstream.toXML(object);
	}

	public Double calculatePercentage(Long score, Long max) {
		Double scoreD = new Double(score);
		Double maxD = new Double(max);
		if (max != 0)
			return new Double(((scoreD / maxD)) * 100);
		else
			return new Double(0);

	}

	private void writeClassReportCSV(InterimClassResults icr, String fileName, HttpServletResponse response,
			String serverPath) throws IOException {

		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(response.getWriter(), ',');
			List<String[]> data = toInterimClassStringArray(icr);
			csvWriter.writeAll(data);
			response.flushBuffer();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}

	private void writeClassStudentCSV(InterimReportsByStudentList irbsl, String fileName, HttpServletResponse response,
			String serverPath) throws IOException {
		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(response.getWriter(), ',');
			List<String[]> data = toInterimStudentArray(irbsl);
			csvWriter.writeAll(data);
			response.flushBuffer();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}

	private List<String[]> toInterimStudentArray(InterimReportsByStudentList irbsl) {
		List<String[]> records = new ArrayList<String[]>();

		List<InterimReportsByStudent> studentList = irbsl.getStudentList();
		records.add(new String[] { "First Name", "Last Name", "Student ID", "Test", "Total Points", "Total %","Completion Date",
				"Question", "Student Response", "Correct Response", "Score", "%", "Item Type",
				"Scoring Type", "Max Score" });
		for (InterimReportsByStudent student : studentList) {
		
			for (InterimStudentResponse isr : student.getInterimStudentResponse()) {
				records.add(new String[] { student.getFirstName(), student.getLastName(), student.getStateId(),
						student.getTestSessionName(), student.getTotalPoint(), student.getTotalPercentage(),student.getTestEndDate(),
						isr.getQuestionName(), isr.getStudentAnswer(), isr.getCorrectAnswer(), isr.getScore(),
						isr.getPercentage(), isr.getItemType(), isr.getScoringType(), isr.getPointValue() });
			}
		}

		return records;
	}

	private void writeItemReportCSV(ItemReport ir, String fileName, HttpServletResponse response, String serverPath)
			throws IOException {
		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(response.getWriter(), ',');
			List<String[]> data = toInterimItemArray(ir);
			csvWriter.writeAll(data);
			response.flushBuffer();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}

	private List<String[]> toInterimItemArray(ItemReport ir) {
		List<String[]> records = new ArrayList<String[]>();
		int length=8+3*ir.getRespNum();
		String[] header = new String[length];
		header[0] = "Item Number";
		header[1] = "Branch";
		header[2] = "Item Type";
		header[3] = "Scoring Type";
		header[4] = "Total Correct";
		header[5] = "Total Correct %";
		header[6] = "Total Incorrect";
		header[7] = "Total Incorrect %";
		header[8] = "Max Score";
		Integer i = 9;
		Integer j=1;
		while (i < length) {
			header[i] = "R"+j;
			i++;
			header[i] = "R"+j+"#";
			i++;
			header[i] = "R"+j+"%";
			j++;
			i++;
		}
		records.add(header);
		for (InterimTestItems it : ir.getInterimTestItems()) {
			String[] body = new String[length];
			body[0] = it.getItemName();
			body[1]=it.getTaskDefinition();
			body[2] = it.getItemType();
			body[3] = it.getScoringType();
			body[4] = it.getTotalCorrectResponses();
			body[5] = it.getTotalCorrect();
			body[6] = it.getInterimReportTasksme();
			body[7] = it.getTotalInCorrect();
			body[8] = it.getMaxScore();
			j = 9;
			for (InterimReportTasks irt : it.getResponses()) {
				if (j < length) {
					if(irt.getResponse()!=null){
					body[j] = irt.getResponse();
					j++;
					body[j] = irt.getResponseNo();
					j++;
					body[j] = irt.getResponsePercent();
					j++;
					}else if( it.getItemType().equalsIgnoreCase("MC-K") || it.getItemType().equalsIgnoreCase("MC-MS")){
						body[j] = "-";
						j++;
						body[j] ="-";
						j++;
						body[j] = "-";
						j++;
						
					}
					else{
						body[j] = "N/A";
						j++;
						body[j] ="-";
						j++;
						body[j] = "-";
						j++;
					}
				}
			}
			records.add(body);

		}
		return records;
	}

	private List<String[]> toInterimClassStringArray(InterimClassResults icr) {
		List<String[]> records = new ArrayList<String[]>();
		List<Object> questions = Arrays
				.asList(icr.getClassRosters().get(0).getQuestions().getQuestionAnswer().keySet().toArray());
		String[] str = new String[questions.size() + 5];
		Integer len = str.length;
		str[0] = "First Name";
		str[1] = "Last Name";
		str[2] = "Student Id";
		Integer i = 3;
		for (Object quest : questions) {
			str[i] = (String) quest;
			i++;
		}
		str[str.length - 2] = "Total Points";
		str[str.length - 1] = "Total %";
		records.add(str);
		for (ClassRosters cr : icr.getClassRosters()) {
			String[] strA = new String[len];
			strA[0] = cr.getFirstName();
			strA[1] = cr.getLastName();
			strA[2] = cr.getStudentId();
			List q = Arrays.asList(cr.getQuestions().getQuestionAnswer().keySet().toArray());
			Integer j = 3;
			Map<String, String> map = cr.getQuestions().getQuestionAnswer();
			for (Object quest : questions) {
				strA[j] = map.get(quest);
				j++;
			}
			strA[len - 2] = cr.getTotalPoints();
			strA[len - 1] = cr.getTotalPercentage();
			records.add(strA);

		}

		return records;
	}

	@Override
	public List<String[]> studentReportCSVByGrid(Long testSessionId, User user) {

		InterimReportsByStudentList irbsl = generateInterimReportsByStudent(testSessionId, user, true);
		return toInterimStudentArray(irbsl);
	}

	@Override
	public List<String[]> classReportCSVByGrid(Long testSessionID, User user) {
		InterimClassResults icr = generateClassResultReport(testSessionID, user, true);

		return toInterimClassStringArray(icr);
	}

	@Override
	public List<String[]> itemReportCSVByGrid(Long testSessionId, User user) {
		ItemReport ir = generateItemReport(testSessionId, user, true);
		return toInterimItemArray(ir);
	}

}
