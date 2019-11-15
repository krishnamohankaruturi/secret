/**
 * 
 */
package edu.ku.cete.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.report.NodeReport;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.report.NodeReportDao;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.tde.webservice.client.TDEWebClient;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.json.NodeReportJsonConverter;

/**
 * @author neil.howerton
 *
 */
@Controller
public class ResyncStudentController {

    private Logger logger = LoggerFactory.getLogger(ResyncStudentController.class);

    @Autowired
    private TDEWebClient tdeWebClient;

    @Autowired
    private StudentService studentService;

    @Autowired
	private NodeReportDao nodeReportDao;

    @Autowired
	private StudentReportService studentReportService;

    @Autowired
	private LmAttributeConfiguration lmAttributeConfiguration;
    
    @Autowired
    private StudentsTestsService studentsTestsService;
    

    /**
     * Displays a list of students that have not been synced with TDE.
     * @param request {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "syncStudents.htm", method = RequestMethod.GET)
    public final ModelAndView viewResyncStudents(HttpServletRequest request) {
        logger.trace("Entering the viewResyncStudents page.");
        ModelAndView mav = new ModelAndView("resyncStudents");

        //Get a list of all the students that have not been synced with TDE.
        List<Student> unSyncedStudents = getUnsyncedStudents(1000);
        logger.debug("Found unsynced students {}", unSyncedStudents);
        mav.addObject("unSyncedStudents", unSyncedStudents);

        logger.trace("Leaving the viewResyncStudents page.");
        return mav;
    }
    /**
     * TODO: move it.
     * Displays a list of students that have not been synced with TDE.
     * @param request {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "nodeResponseReport.htm", method = RequestMethod.GET)
    public final ModelAndView nodeResponseReport() {
        logger.trace("Entering the nodeResponseReport page.");
        ModelAndView mav = new ModelAndView("nodeResponseReport");
        //Get a list of all the students that have not been synced with TDE.
        logger.trace("Leaving the nodeResponseReport page.");
        return mav;
    } 
    /**
     * TODO move it.
     * Displays a list of students that have not been synced with TDE.
     * @param request {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "nodeResponseReport.htm", method = RequestMethod.POST)
    public final ModelAndView nodeResponseReport(@RequestParam("keyword") String keyWord) {
        logger.trace("Entering the nodeResponseReport page for getting results");
        ModelAndView mav = new ModelAndView("nodeResponseReport");
        //Get a list of all the students that have not been synced with TDE.
        logger.trace("Leaving the nodeResponseReport page.");
        return mav;
    }    
    /**
     * Displays a list of students that have not been synced with TDE.
     * @param request {@link HttpServletRequest}
     * @return {@link ModelAndView}
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(value = "searchNodeReport.htm",  method = RequestMethod.GET)
    public final @ResponseBody JsonResultSet searchNodeReport(
    		@RequestParam("studentKeyword") String studentKeyword,
    		@RequestParam("testKeyword") String testKeyword,
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType
    		) throws JsonParseException,
    				JsonMappingException, IOException {
        logger.trace("Entering the nodeResponseReport page for getting results");
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TimerUtil timerUtil = TimerUtil.getInstance();
        NodeReportJsonConverter nodeReportJsonConverter = NodeReportJsonConverter.getInstance(
        		lmAttributeConfiguration);
        timerUtil.resetAndLog(logger, "Initializing converter took ");
        
        studentsTestsService.getByStudentName(studentKeyword, testKeyword, userDetails.getUser().getOrganizationId());
        
        Collection<NodeReport> nodeReportItems = null;
        int currentPage = NumericUtil.parse(page, 1);
        int limitCount = NumericUtil.parse(limitCountStr,5);
        nodeReportItems = studentReportService.selectByStudentAndTest(
        		studentKeyword,
        		testKeyword,
        		userDetails.getUser().getOrganizationId(),
        		limitCount,
        		currentPage,
        		sortByColumn,
        		sortType);
        timerUtil.resetAndLog(logger, "Getting nodeReportItems converter took ");
        int totalCount = studentReportService.countByStudentAndTest(
        		studentKeyword, testKeyword,
        		userDetails.getUser().getOrganizationId());
        timerUtil.resetAndLog(logger, "Finding count of node report items took ");
        JsonResultSet
        jsonResultSet = nodeReportJsonConverter.convertToNodeReportJson(
        		nodeReportItems,totalCount,currentPage, limitCount, lmAttributeConfiguration);
        timerUtil.resetAndLog(logger, "Converting to JSON took ");
        logger.trace("Leaving the nodeResponseReport page.");
        return jsonResultSet;
    } 
       
    /**
     * TODO use request param instead of the request object.
     * The method will resync selected students to TDE.
     * @param request {@link HttpServletRequest}
     * @return Map<String, Object>
     *//*
    @RequestMapping(value = "syncStudents.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> reSyncStudentsWithTDE(HttpServletRequest request) {
        //Attempt to resync the students with TDE.
        logger.trace("Entering the reSyncStudentsWithTDE() method.");
        Map<String, Object> map = new HashMap<String, Object>();

        String[] strStudents = request.getParameterValues("students[]");
        int limit = NumericUtil.parse(request.getParameter("limit"), 1000);

        logger.debug("Attempting to resync students with id = {}", strStudents);
        if (strStudents.length > 0) {
            StudentExample example = new StudentExample();
            StudentExample.Criteria criteria = example.createCriteria();

            List<Long> studentIds = new ArrayList<Long>();

            for (String studentId : strStudents) {
                studentIds.add(Long.parseLong(studentId));
            }

            criteria.andIdIn(studentIds);
            List<Student> students = studentService.getByCriteria(example);
            logger.debug("Found students {} in system.", students);

            if (students.size() > 0) {
                logger.debug("Attempting to resync students now.");
                List<TDEStudent> retStudents = tdeWebClient.upsertsStudents(students);

                if (retStudents != null) {
                    
                     * This means that the communication between AART and TDE was at least partially successful.
                     * The TDEStudent objects returned from the web client call represent the students that have
                     * successfully been synced with TDE. Now we need to update them in AART to show that they are
                     * synced.
                     
                    Student updateRecord = new Student();
                    updateRecord.setSynced(true);
                    StudentExample updateExample = new StudentExample();
                    StudentExample.Criteria updateCiteria = updateExample.createCriteria();

                    studentIds = new ArrayList<Long>();
                    for (TDEStudent tdeStudent : retStudents) {
                        for (Student student : students) {
                            if (student.getUsername().equals(tdeStudent.getUsername())) {
                                studentIds.add(student.getId());
                            }
                        }
                    }

                    updateCiteria.andIdIn(studentIds);

                    studentService.updateByExampleSelective(updateRecord, updateExample);
                    map.put("synced", true);
                } else {
                    // There was a problem re-syncing the students. Inform the user.
                    map.put("synced", false);
                }
            }

        }

        map.put("students", getUnsyncedStudents(limit));

        return map;
    }
*/
    /**
     * Returns the first 1000 un-synced students.
     * @return List<Student> - list of students that still need to be synced.
     */
    private List<Student> getUnsyncedStudents(int limit) {
        logger.trace("Entering the getUnsyncedStudents.");
        StudentExample example = new StudentExample();

        /*
         * We return only the first 1000 records to improve the performance of the page. When originally written the sync students
         * page was never intended to be production ready. There was always the intention of developing requirements for the page
         * and then using the tool as a baseline for that feature. 
         */
        example.setLimit(limit);
        StudentExample.Criteria criteria = example.createCriteria();

        criteria.andSyncedEqualTo(false);
        example.setDistinct(true);
        
        List<Student> students = studentService.getByCriteriaForResync(example);
        logger.debug("Returing the first 1000 un-synced students");

        logger.trace("Leaving the getUnsyncedStudents method.");
        return students;
    }
    

}
