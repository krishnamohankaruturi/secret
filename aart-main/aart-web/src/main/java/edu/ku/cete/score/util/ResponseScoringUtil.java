/**
 * 
 */
package edu.ku.cete.score.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.StudentsResponsesReportDto;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.content.TestSectionsTaskVariants;
import edu.ku.cete.domain.content.TestSectionsTaskVariantsExample;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.model.TestSectionDao;
import edu.ku.cete.model.TestSectionsTaskVariantsDao;
import edu.ku.cete.report.ChartNumber;
import edu.ku.cete.report.GraphScoreReport;
import edu.ku.cete.report.QuestionAndResponse;
import edu.ku.cete.report.SectionName;
import edu.ku.cete.report.StudentProblemReport;
import edu.ku.cete.report.StudentResponseReport;
import edu.ku.cete.score.StudentRawScore;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author neil.howerton
 *
 */
@Service
public class ResponseScoringUtil {

    /**
     * Logger for the scoring utility.
     */
    private Logger logger = LoggerFactory.getLogger(ResponseScoringUtil.class);
    
    @Autowired
    private StudentReportService studentReportService;
    
    /**
     * {@link TestService}.
     */
    @Autowired
    private TestService testService;
    
    /**
     * {@link TestSectionDao}.
     */
    @Autowired
    private TestSectionDao testSectionDao;
    
    /**
    *
    */
   @Value("${studentProblem.rowCount}")
   private int rowCount;
   
   @Autowired
   private TestSectionsTaskVariantsDao testSectionsTaskVariantsDao;
	/**
	 * @param studentTestSessionDtos
	 * @return
	 */
	public List<StudentRawScore> computeRawScoreFromDto(
			List<StudentTestSessionDto> studentTestSessionDtos) {
        logger.trace("Entering the computRawScore() method.");

        List<StudentRawScore> rawScores = new ArrayList<StudentRawScore>();
        
        for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos) {
            logger.debug("Computing raw score for student {}", studentTestSessionDto.getStudent());
            int numQuestions = 0;
            int numCorrect = 0;
            StudentRawScore rawScore = new StudentRawScore();
            rawScore.setStudent(studentTestSessionDto.getStudent());
            //TODO Add local student identifier
            rawScore.setLocalStudentIdentifier(
            		ParsingConstants.BLANK + 
            		studentTestSessionDto.getStudent().getStateStudentIdentifier());

            if (studentTestSessionDto.getStudentsTests() != null
            		&& studentTestSessionDto.getStudentsTests().getTest() != null
            		&& studentTestSessionDto.getStudentsTests().getTest().getTestSections() != null) {
                for (TestSection testSection : studentTestSessionDto.getStudentsTests().getTest().getTestSections()) {
                        numQuestions = numQuestions + testSection.getNumberOfTestItems();
                }
            }

            if (studentTestSessionDto.getStudentsResponsesReportDtos() != null) {
                for (StudentsResponsesReportDto studentsResponsesReportDto : studentTestSessionDto.getStudentsResponsesReportDtos()) {
                    Long chosenAnswer = studentsResponsesReportDto.getStudentsResponses().getFoilId();
                    if (chosenAnswer != null && studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse() != null
                    		&& studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse()) {
                        numCorrect++;
                    } else {
                       
                    	logger.debug("Unable to grade foil " + chosenAnswer);
                    }
                   
                }
            }
           
            logger.debug("Student {} answered {} correct out of {} questions.",
                    new Object[] {studentTestSessionDto.getStudent(), numCorrect, numQuestions});
            rawScore.setNumQuestions(numQuestions);
            rawScore.setNumCorrect(numCorrect);
            rawScores.add(rawScore);
        }

        logger.trace("Leaving the computeRawScore method.");
        return rawScores;
	}
	
	
	/**
	 * Function to display the Student Problem Table
	 * @param studentTestSessionDtos
	 * @param totalNumQuestions
	 * @param imagePath
	 * @return
	 */
	public List<StudentProblemReport> computeStudentProblemReport(List<StudentTestSessionDto> studentTestSessionDtos,
			Integer totalNumQuestions, String imagePath){
		
		
		List<StudentProblemReport> studentProblemReports = new ArrayList<StudentProblemReport>();		
		StudentProblemReport studentProblemReport = null;		
		StudentsResponsesReportDto studentsResponsesReportDto = null;
        DecimalFormat decimalFormatter = new DecimalFormat("###.#");
		List<StudentsResponsesReportDto> studentsResponsesReportDtoList = new ArrayList<StudentsResponsesReportDto>();
		List<String> testSessionResultsList = new ArrayList<String>();
		List<String> testSectionNames = new ArrayList<String>();
		List<Integer> testSectionsList = new ArrayList<Integer>();
		List<String> scores = new ArrayList<String>();
        int correctResponseCount = 0;
        int totalCount = 0;
        int[] score= new int[500];
        ArrayList<String> labels = new ArrayList<String>(500);
        int totalQuestionsCount = 0;        
        String taskVariantPositions = "";
        String testSessionResults = "";
        String scoreValue = "";
	
        
        TreeMap<String, String> testSessionResultsMap = new TreeMap<String, String>();
        TreeMap<String, ArrayList<String>> itemPercentageResultsMap = new TreeMap<String, ArrayList<String>>();
        List<String> studentResultsList = new ArrayList<String>();
        String sortedStudentNames = "";
    	Boolean found = false;
        List<String> taskVariantPositionsAndSectionsList = new ArrayList<String>();
        String taskVariantPositionAndSection = "";
        String currentMapKey = "";
        String previousMapKey = "";            
        TreeMap<String, String> samePercentCorrectSortedMap = new TreeMap<String, String>();
        
		if(studentTestSessionDtos != null &&  CollectionUtils.isNotEmpty(studentTestSessionDtos)) {
        	Long testId = studentTestSessionDtos.get(0).getStudentsTests().getTestId();
        	Test test = null;
        	//for only formative test collection with one test question information will be gathered.
        	if(testId != null && testId > 0) {
        		test = testService.findTestAndSectionById(testId);
        		if(test != null) {
                	for(TestSection testSection : test.getTestSections()) {
                		testSectionsList.add(testSection.getNumberOfTestItems());
                		testSectionNames.add(testSection.getTestSectionName());
                		totalQuestionsCount = totalQuestionsCount + testSection.getNumberOfTestItems();
                	}            			
        		}
        	}
        	
            if (totalQuestionsCount == 0) {
				// Get max questions count by iterating through all the StudentsResponsesReportDtos
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos) {
					if (studentTestSessionDto != null
							&& studentTestSessionDto.getStudent() != null
							&& studentTestSessionDto
									.getStudentsResponsesReportDtos() != null) {
						if (totalQuestionsCount < studentTestSessionDto
								.getStudentsResponsesReportDtos().size()) {
							totalQuestionsCount = studentTestSessionDto
									.getStudentsResponsesReportDtos()
									.size();
							studentsResponsesReportDtoList = studentTestSessionDto
									.getStudentsResponsesReportDtos();
						}
					}
				}
			}
			//Build the table header
        	//TODO do not rely on string parsing. Use Json objects or use standard bean resolvers.
            int count = 0;//count to record the original position
        	if (test != null && test.getTestSections() != null && CollectionUtils.isNotEmpty(test.getTestSections())) {
				for (TestSection testSec : test.getTestSections()) {
					for (TestSectionsTaskVariants testSectionsTaskVariants : testSec
							.getTestSectionsTaskVariants()) {
						taskVariantPositions = taskVariantPositions
								+ testSectionsTaskVariants
										.getTaskVariantPosition() + ";";
						//labels holds the "position" from CB and the original position
						labels.add(testSectionsTaskVariants
										.getTaskVariantPosition() + ":" + count + ":" + testSec.getTestSectionName());
						count++;
					}
					taskVariantPositions += testSec.getTestSectionName() + ",";
					
				}
			} else {
				//The test collection has more than 1 test.
	            for( StudentsResponsesReportDto studentsResponsesReportDtoObj : studentsResponsesReportDtoList) {
            		TestSection testSection = testSectionDao.selectByPrimaryKey(studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTestSectionId());
	            	if(studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() != null) {
	            		taskVariantPositions += studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() + ";";
	            		labels.add(studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() != null ?
	            				studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() + ":" 
	            						+ count + ":" +testSection.getTestSectionName()  : "-");
	            	} else {
	            		//I have concerns about this section of code
	            		taskVariantPositions += "-" + ";";
	            		labels.add("-");
	            	}
	            	count++;
		            taskVariantPositions += testSection.getTestSectionName() + ",";
	            }			
			}
            	            	            	           
            //Build table rows
            //TODO Consider how to move it to the service method itself the operations on this dto.
            for(StudentTestSessionDto studentTestSessionDto :  studentTestSessionDtos) {
            	
            	if(studentTestSessionDto != null && studentTestSessionDto.getStudent() != null &&
            			studentTestSessionDto.getStudentsResponsesReportDtos() != null) {    
            		
            		testId = studentTestSessionDto.getStudentsTests().getTestId();
                	taskVariantPositionsAndSectionsList = testSectionsTaskVariantsDao.getTaskVariantPositionAndSectionByTestId(testId);
            		
            		for(String taskVariantPosAndSec : taskVariantPositionsAndSectionsList) {
            			found = false;
            			for(int i = 0; i < studentTestSessionDto.getStudentsResponsesReportDtos().size() && !found; i++)  {
            				studentsResponsesReportDto = studentTestSessionDto.getStudentsResponsesReportDtos().get(i);	            		
	            			
            				if(studentsResponsesReportDto != null && studentsResponsesReportDto.getTaskVariantsFoils()!= null) {
	            				
            					if(studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse() != null) {
            						
            						taskVariantPositionAndSection =  ((studentsResponsesReportDto.getTestSectionsTaskVariants().getTestSectionId()).toString() + 
            								(studentsResponsesReportDto.getTestSectionsTaskVariants().getTaskVariantPosition()).toString());
									
            						if(taskVariantPosAndSec.equalsIgnoreCase(taskVariantPositionAndSection)) {
            							found = true;
	            						// if the response is not null and correct
		            					if(studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse()) {
					            			testSessionResults += "0000T;";
					            			correctResponseCount++;
					            			score[totalCount] +=1;
					            		} else {
					            			// if the response is not null and incorrect
					            			String rtlf = studentsResponsesReportDto.getReportTaskLayoutFormat();
					            			if (rtlf == null)
					            				rtlf = "no label";
					            			Integer responseOrder = studentsResponsesReportDto.getTaskVariantsFoils().getResponseOrder();
					            			String responseLabel = ResponseLabelUtil.getResponseLabel(responseOrder, rtlf);
					            			if (responseLabel.equals("")){
					            				//no label
					            				testSessionResults += "-----;";
					            			} else {
					            				testSessionResults += "0000" + responseLabel + ";";
					            			}
					            		}
            						}/* else {
        							//If the student skipped a particular question.
        							testSessionResults += "-;";
        						}*/
            				}/* else {
        						// if the response is null
        						testSessionResults += "-;";
            				}*/
            			}
            		}
        			if(!found){
            			testSessionResults += "-----" + ";";
            		}
            		totalCount++;
            	//}
        		}

            		try {
		            	//testSessionResults +=  String.valueOf(correctResponseCount) + "/" + String.valueOf(totalCount) + ";" ;
		            	if(totalCount != 0) {
		            		//testSessionResults = String.valueOf(Double.valueOf(decimalFormatter.format((100.0/totalCount) * correctResponseCount))) + ";" + testSessionResults;
		            		//testSessionResults += String.valueOf(Double.valueOf(decimalFormatter.format((100.0/totalCount) * correctResponseCount))) + ";";
		            		scoreValue = String.valueOf(Double.valueOf(decimalFormatter.format((100.0/totalCount) * correctResponseCount)));
		            		if(scoreValue.length() == 3)
		            			scoreValue = "00" + scoreValue;
		            		else if(scoreValue.length() == 4)
		            			scoreValue = "0" + scoreValue;            			
		            		testSessionResults += scoreValue + ";";
		            	} else {
		            		scoreValue = "000.0";
		            		testSessionResults = scoreValue + ";"+ testSessionResults;
		            	}
            		} catch (ArithmeticException ex) {
            			logger.error("Caught ArithmeticException {}", ex);
            		}
            		
            		testSessionResults = testSessionResults.substring(0, testSessionResults.lastIndexOf(";"));
            		testSessionResultsMap.put(scoreValue + WordUtils.capitalize(studentTestSessionDto.getStudent().getLegalLastName()) +  
            				"," + WordUtils.capitalize(studentTestSessionDto.getStudent().getLegalFirstName()), testSessionResults);
	            	testSessionResultsList.add(testSessionResults);
	            	testSessionResults = "";
	            	correctResponseCount = 0;	            	 
	            	totalCount = 0;
            	}
            }
            
            //sort the % Correct data using TreeMap and assign to a List. If the % correct is same for more than 1 student 
            // then sort the data based on ascending order by last name.
            if(testSessionResultsMap != null && testSessionResultsMap.size() > 0) {
	            currentMapKey = "";
	            previousMapKey = testSessionResultsMap.descendingMap().firstKey().substring(0, 5);            
	
	            for(Map.Entry<String,String> entry : testSessionResultsMap.descendingMap().entrySet()) {
	
	            	currentMapKey =  entry.getKey().substring(0, 5);
	            	
	            	if(!previousMapKey.equalsIgnoreCase(currentMapKey)) {
	            		for(Map.Entry<String,String> sameNameEntry : samePercentCorrectSortedMap.entrySet()) {
	            			studentResultsList.add(sameNameEntry.getValue());
	                		sortedStudentNames += sameNameEntry.getKey() + ";";
	            		}
	            		samePercentCorrectSortedMap.clear();
	            		previousMapKey = entry.getKey().substring(0, 5);
	            	}
	            	
	            	samePercentCorrectSortedMap.put(entry.getKey(), entry.getValue());
	            }
	            for(Map.Entry<String,String> samePercentCorrectEntry : samePercentCorrectSortedMap.entrySet()) {
	    			studentResultsList.add(samePercentCorrectEntry.getValue());
	        		sortedStudentNames += samePercentCorrectEntry.getKey() + ";";
	    		}
            }
 
            
            // Build the table last row to show question-wise score            
            for (int scoreIndex=0; scoreIndex < totalQuestionsCount; scoreIndex++) {
            	scoreValue = "000.0;";
            	if(studentTestSessionDtos.size() != 0) {
            		scoreValue = String.valueOf(Math.ceil(((100.0/studentTestSessionDtos.size()) * score[scoreIndex])));
            		if(scoreValue.length() == 3)
            			scoreValue = "00" + scoreValue;
            		else if(scoreValue.length() == 4)
            			scoreValue = "0" + scoreValue;
            	} 
            	testSessionResults += scoreValue + ";";
            	//populate tree map with percentage as key and list of item label/original position as value
            	ArrayList<String> label = itemPercentageResultsMap.get(scoreValue);
            	if (label == null){
            		label=new ArrayList<String>();
            	}
            	label.add(labels.get(scoreIndex));
            	if(!(scores.contains(scoreValue))){
            		scores.add(scoreValue);
               	 }
            	
            	itemPercentageResultsMap.put(scoreValue, label);
            }
            
            testSessionResults += "SCORE;";
            testSessionResults = testSessionResults.substring(0, testSessionResults.lastIndexOf(";"));
            testSessionResultsList.add(testSessionResults);	                           
            studentResultsList.add(testSessionResults);
        }
		ArrayList<String> reorderedStudentResultsList = new ArrayList<String>();
		String reorderedTaskVariantPositions = "";
		
				for(String testSectioname  : testSectionNames){
					TreeMap<String, ArrayList<String>> testSectionResultsMap = new TreeMap<String, ArrayList<String>>();
					for(String scoreValue1 : scores){
						ArrayList<String> label1 = itemPercentageResultsMap.get(scoreValue1);
						ArrayList<String> label2 = new ArrayList<String>();
						for (String labelString1 : label1){
							String cur = labelString1.split(":")[2];
					if(cur.equals(testSectioname)){
						label2.add(labelString1);
					}
				}
						if(!(label2.isEmpty()))
						testSectionResultsMap.put(scoreValue1, label2);
							
			}
		
		//studentResultsList must be deconstructed reordered and reordered based on item label order
		//use studentResultsList and labels position to keep columns info together
		
		int count = 1;
		
		for (String studentResults : studentResultsList){
			String[] results = studentResults.split(";");
			String reorderedStudentResults = "";
			String studentPercentCorrect = results[results.length-1];
			//get in descending order to order from easiest to hardest - US11166
			for(Map.Entry<String, ArrayList<String>> entry : testSectionResultsMap.descendingMap().entrySet()){
				for (String labelString : entry.getValue()){
					//get the original position
					int originalPosition = Integer.parseInt(labelString.split(":")[1]);
					//get the label
					String currentLabel = labels.get(originalPosition);
					//for the first iteration only, collect the task variant positions
					if (count == 1)
						reorderedTaskVariantPositions += currentLabel.split(":")[0] + ";";
					//get the result using the original position
					String currentResult = results[originalPosition];
					reorderedStudentResults += currentResult + ";";
				}
			}
			count++;
			reorderedStudentResults += studentPercentCorrect +";";
			reorderedStudentResultsList.add(reorderedStudentResults);
		}
		}		
				
		String responses = "";
		String[] splitResponse = null;
		Integer splitResponseLength = 0;
		String[] splitTaskVariantPositions = null;
		List <String> studentResponse = null;
		String taskVariants = "";
		Integer studentResponseLength = null;
		String allCorrectResponses = "";
		//String correctResponses = "";
		int numSectionQuestions = 0 ;
		int testSectionQuestions = 0;
		int rows = testSessionResultsList.size();
		int currentrow = 0;
		int numOfTestSection = 0;
		for(Integer testSection: testSectionsList){
			int numSection = testSection ;
			int testSectionLevel = 0;
			numSectionQuestions = numSectionQuestions +testSection;
			List<String> recordsPerTable = reorderedStudentResultsList.subList(currentrow, currentrow + rows);
			currentrow = currentrow + rows;
		for(int i = 0 ; i < testSection ; i = i+rowCount) {
			allCorrectResponses = "";
			responses = "";
			splitTaskVariantPositions = null;
			studentResponse = new ArrayList<String>();
			taskVariants = "% Correct;";
			studentResponseLength = 0;
			imagePath=imagePath.concat("images/check.jpg");	
			for(String correctResponses : recordsPerTable) {			
				splitResponse = correctResponses.split(";");
				splitResponseLength = correctResponses.split(";").length;
				responses = "";

				if(splitResponse[splitResponseLength - 1].equalsIgnoreCase("SCORE")) {
					allCorrectResponses += "     ;";
				} else {
					allCorrectResponses += splitResponse[splitResponseLength - 1] + ";";
				}
				if((testSectionLevel+rowCount) > numSection){
					for(int j = testSectionLevel; j < testSectionLevel + rowCount /*&& j < (splitResponse.length - 1)*/ ; j ++) {
						if (j >= splitResponse.length - 1){
							allCorrectResponses += "     ;";	
						}else{
							allCorrectResponses += splitResponse[j] + ";";
							responses += splitResponse[j] + ";";
						}
					}
				}else{
				for(int j = testSectionLevel; j < testSectionLevel + rowCount /*&& j < (splitResponse.length - 1)*/ ; j ++) {
					if (j >= splitResponse.length - 1){
						allCorrectResponses += "     ;";	
					}else{
						allCorrectResponses += splitResponse[j] + ";";
						responses += splitResponse[j] + ";";
					}
				}
				}
				
					studentResponseLength = rowCount+1;
				studentResponse.add(responses);
				
				
			}
			studentProblemReport = new StudentProblemReport();
			//studentProblemReport.setStudents(students);
			
			studentProblemReport.setCurrentRowCount(i+1);
            //studentProblemReport.setRowQuestionsCount(maxQuestionCount);
            
            studentProblemReport.setCorrectResponses(allCorrectResponses);
            //logger.debug(level+ "   " +allCorrectResponses);
            studentProblemReport.setImagePath(imagePath);
            studentProblemReport.setTestSectionName(testSectionNames.get(numOfTestSection));
            studentProblemReport.setStudentResponses(studentResponse);
           
            studentProblemReport.setRowResponseCount((long) studentResponseLength);
            studentProblemReport.setScoreRowIndex((long) testSessionResultsList.size());
            
            splitTaskVariantPositions = reorderedTaskVariantPositions.split(";");	
            if((testSectionQuestions+rowCount) > numSectionQuestions){
			for(int j = testSectionQuestions; j < numSectionQuestions  /*&& j < splitTaskVariantPositions.length*/ ; j ++) {
				if (j >= splitTaskVariantPositions.length )
					taskVariants += " ;";
				else
					taskVariants +=  splitTaskVariantPositions[j] + ";";		
			}
            }else{
            	for(int j = testSectionQuestions; j < testSectionQuestions + rowCount  /*&& j < splitTaskVariantPositions.length*/ ; j ++) {
    				if (j >= splitTaskVariantPositions.length )
    					taskVariants += " ;";
    				else
    					taskVariants +=  splitTaskVariantPositions[j] + ";";		
    			}
            }
            if((testSectionQuestions+rowCount) > numSectionQuestions){
				for(int c=0;c < (rowCount - (numSectionQuestions - testSectionQuestions));c++)
				taskVariants += " ;";
			}
            if((testSectionQuestions+rowCount) > numSectionQuestions)
            	testSectionQuestions = numSectionQuestions;
	            else
	            	testSectionQuestions = testSectionQuestions + rowCount;
			studentProblemReport.setRowQuestionsCount(rowCount + 1);
			
			
            studentProblemReport.setNumOfResponses(taskVariants);
            
            studentProblemReport.setSortedStudentNames(sortedStudentNames);
            
            studentProblemReports.add(studentProblemReport);
            if((testSectionLevel+rowCount) > numSection)
	            testSectionLevel = numSection;
	            else
	            	testSectionLevel = testSectionLevel + rowCount;
		}	
		numOfTestSection ++;
		}
		return studentProblemReports;
		
	}

	
	/**
	 * Creating data for the Item Performace Graphs
	 * @param studentTestSessionDtos
	 * @return
	 */
	public List<GraphScoreReport> createDataSetGraphs(
			List<StudentTestSessionDto> studentTestSessionDtos) {
        logger.trace("Entering the createDataSetGraphs() method.");

        List<GraphScoreReport> graphScoreReports = new ArrayList<GraphScoreReport>();
        List<String> testSectionNames = new ArrayList<String>();
        GraphScoreReport graphScoreReport = null;
		StudentsResponsesReportDto studentsResponsesReportDto = null;
		List<StudentsResponsesReportDto> studentsResponsesReportDtoList = new ArrayList<StudentsResponsesReportDto>();
		List<String> testSessionResultsList = new ArrayList<String>();
		List<Integer> testSectionsList = new ArrayList<Integer>();
		List<Long> taskVariantIds = new ArrayList<Long>();
		List<String> taskVariantPositionsAndSectionsList = new ArrayList<String>();
	    String taskVariantPositionAndSection = "";
	    Boolean found = false;
        int correctResponseCount = 0;
        int totalCount = 0;
        int[] score= new int[500];
        int totalQuestionsCount = 0;        
        String taskVariantPositions = "";
        String testSessionResults = "";

        
       
        if(studentTestSessionDtos != null &&  CollectionUtils.isNotEmpty(studentTestSessionDtos)) {
        	Long testId = studentTestSessionDtos.get(0).getStudentsTests().getTestId();
        	Test test = null;
        	//for only formative test collection with one test question information will be gathered.
        	if(testId != null && testId > 0) {
        		test = testService.findTestAndSectionById(testId);
        		if(test != null) {
                	for(TestSection testSection : test.getTestSections()) {
                		testSectionsList.add(testSection.getNumberOfTestItems());
                		testSectionNames.add(testSection.getTestSectionName());
                		totalQuestionsCount = totalQuestionsCount + testSection.getNumberOfTestItems();
                		
                	}            			
        		}
        	}
        	
            if (totalQuestionsCount == 0) {
				// Get max questions count by iterating through all the StudentsResponsesReportDtos
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos) {
					if (studentTestSessionDto != null
							&& studentTestSessionDto.getStudent() != null
							&& studentTestSessionDto
									.getStudentsResponsesReportDtos() != null) {
						if (totalQuestionsCount < studentTestSessionDto
								.getStudentsResponsesReportDtos().size()) {
							totalQuestionsCount = studentTestSessionDto
									.getStudentsResponsesReportDtos()
									.size();
							studentsResponsesReportDtoList = studentTestSessionDto
									.getStudentsResponsesReportDtos();
						}
					}
				}
			}
			//Build the table header
        	//TODO do not rely on string parsing. Use Json objects or use standard bean resolvers.
            //testSessionResults += "Name;" + "Status;";
        	if (test != null && test.getTestSections() != null && CollectionUtils.isNotEmpty(test.getTestSections())) {
				for (TestSection testSec : test.getTestSections()) {
					for (TestSectionsTaskVariants testSectionsTaskVariants : testSec
							.getTestSectionsTaskVariants()) {
						Long tasskid = testSectionsTaskVariants.getTaskVariantId();
						//taskVariantPositions = taskVariantPositions
						//		+ testSectionsTaskVariants.getTaskVariantId() + ",";
						taskVariantPositions = taskVariantPositions
								+ testSectionsTaskVariants
										.getTaskVariantPosition() + ";";
						taskVariantIds.add(tasskid);
					}
				}
			} else {
				//The test collection has more than 1 test.
	            for( StudentsResponsesReportDto studentsResponsesReportDtoObj : studentsResponsesReportDtoList) {
	            	Long tasskid = studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantId();
	            	if(studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() != null) {
	            		taskVariantPositions += studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() + ";";
	            		taskVariantIds.add(tasskid);
	            	} else {
	            		taskVariantPositions += "No Response" + ";";
	            		taskVariantIds.add(tasskid);
	            	}
	            }					
			}
            
            //testSessionResults += "Score;" + "Score %";
            //testSessionResultsList.add(testSessionResults);
            //testSessionResults = "";
        	     	            	           
            //Build table rows
            //TODO Consider how to move it to the service method itself the operations on this dto.        	
        	for(StudentTestSessionDto studentTestSessionDto :  studentTestSessionDtos) {            	
            	
            	if(studentTestSessionDto != null && studentTestSessionDto.getStudent() != null &&
            			studentTestSessionDto.getStudentsResponsesReportDtos() != null) {            		
            		testId = studentTestSessionDto.getStudentsTests().getTestId();
                	taskVariantPositionsAndSectionsList = testSectionsTaskVariantsDao.getTaskVariantPositionAndSectionByTestId(testId);
            		
            		for(String taskVariantPosAndSec : taskVariantPositionsAndSectionsList) {
            			found = false;
            			for(int i = 0; i < studentTestSessionDto.getStudentsResponsesReportDtos().size() && !found; i++)  {
            				studentsResponsesReportDto = studentTestSessionDto.getStudentsResponsesReportDtos().get(i);	            		
	            			
            				if(studentsResponsesReportDto != null && studentsResponsesReportDto.getTaskVariantsFoils()!= null) {
	            				
            					if(studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse() != null) {
            						
            						taskVariantPositionAndSection =  ((studentsResponsesReportDto.getTestSectionsTaskVariants().getTestSectionId()).toString() + 
            								(studentsResponsesReportDto.getTestSectionsTaskVariants().getTaskVariantPosition()).toString());
									
            						if(taskVariantPosAndSec.equalsIgnoreCase(taskVariantPositionAndSection)) {
            							found = true;
            							
	            						// if the response is not null and correct
		            					if(studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse()) {
		            						String rtlf = studentsResponsesReportDto.getReportTaskLayoutFormat();
		            						if (rtlf == null)
					            				rtlf = "no label";
		            						Integer responseOrder = studentsResponsesReportDto.getTaskVariantsFoils().getResponseOrder();
					            			String responseLabel = ResponseLabelUtil.getResponseLabel(responseOrder, rtlf);
					            			testSessionResults += responseLabel + ";";
					            			correctResponseCount++;
					            			score[totalCount] +=1;
					            		} else {
					            			// if the response is not null and incorrect
					            			String rtlf = studentsResponsesReportDto.getReportTaskLayoutFormat();
					            			if (rtlf == null)
					            				rtlf = "no label";
					            			Integer responseOrder = studentsResponsesReportDto.getTaskVariantsFoils().getResponseOrder();
					            			String responseLabel = ResponseLabelUtil.getResponseLabel(responseOrder, rtlf);
					            			if (responseLabel.equals("")){
					            				//no label
					            				testSessionResults += "No Response;";
					            			} else {
					            				testSessionResults += responseLabel + ";";
					            			}
					            		}
            						}/* else {
        							//If the student skipped a particular question.
        							testSessionResults += "-;";
        						}*/
            				}/* else {
        						// if the response is null
        						testSessionResults += "-;";
            				}*/
            			}
            		}
        			if(!found){
        				testSessionResults += "No Response;";
            		}
	            		totalCount++;            		
	            	}

            		
            		
            		/*testSessionResults = testSessionResults.substring(0, testSessionResults.lastIndexOf(";"));
            		testSessionResultsMap.put(scoreValue + WordUtils.capitalize(studentTestSessionDto.getStudent().getLegalLastName()) +  
            				"," + WordUtils.capitalize(studentTestSessionDto.getStudent().getLegalFirstName()), testSessionResults);*/
	            	testSessionResultsList.add(testSessionResults);
	            	testSessionResults = "";
	            	correctResponseCount = 0;	            	 
	            	totalCount = 0;
            	}
            }
            
        }
      //  logger.debug(testSessionResults);
        String[] splitTaskVariantPositions = null;
        String[] splitResponsePositions = null;
        String taskVariants = "";
        String allCorrectResponses = "";
        graphScoreReport = new GraphScoreReport();
        splitTaskVariantPositions = taskVariantPositions.split(";");			
		for(int i = 0;  i < splitTaskVariantPositions.length ; i ++) {
			taskVariants +=  splitTaskVariantPositions[i] + ";";		
		}
		for(String correctResponses : testSessionResultsList) {
			splitResponsePositions = correctResponses.split(";");
			//logger.debug(correctResponses);
			for(int j = 0;  j < splitResponsePositions.length ; j ++) {
				allCorrectResponses +=  splitResponsePositions[j] + ";";		
			}
		}
		//testSessionResultsList.add(taskVariants);
		//logger.debug("Task Variants Questions "+taskVariantQuestions);
		graphScoreReport.setCorrectResponses(allCorrectResponses);
		graphScoreReport.setNumOfResponses(taskVariants);
		graphScoreReport.setTestSectionsList(testSectionsList);
		graphScoreReport.setTaskVariantIds(taskVariantIds);
		graphScoreReport.setTestSectionNames(testSectionNames);
		graphScoreReports.add(graphScoreReport);
		
        logger.trace("Leaving the createDataSetGraphs method.");
        return graphScoreReports;
	}

	
	public List<StudentResponseReport> getStudentResponseReport(List<StudentTestSessionDto> studentTestSessionDtos){
		List<StudentResponseReport> studentResponseReports = new ArrayList<StudentResponseReport>();
		for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos){
			for (TestSection section :studentTestSessionDto.getStudentsTests().getTest().getTestSections()){
					List<StudentResponseReport> reports= createReports(studentTestSessionDto, section);
					studentResponseReports.addAll(reports);
			}
		}
		return studentResponseReports;
	}

	private List<StudentResponseReport> createReports(StudentTestSessionDto studentTestSessionDto, TestSection section){
		List<StudentResponseReport> reports = new ArrayList<StudentResponseReport>();
		int numberOfReports = section.getNumberOfTestItems() / 10;
		int remainder = section.getNumberOfTestItems() % 10;
		if (remainder > 0 )
			numberOfReports++;
		int remainingItemsToProcess = section.getNumberOfTestItems();
		List<StudentsResponsesReportDto> sectionResponses = getCorrectSectionResponses(studentTestSessionDto.getStudentsResponsesReportDtos(), section);
		for (int i = 1; i <= numberOfReports; i++){
			StudentResponseReport report = new StudentResponseReport();
			report.setStudentFirstName(studentTestSessionDto.getStudent().getLegalFirstName());
			report.setStudentMiddleName(studentTestSessionDto.getStudent().getLegalMiddleName());
			report.setStudentLastName(studentTestSessionDto.getStudent().getLegalLastName());
			report.setSectionName(new SectionName(section.getTestSectionName()));
			report.setChartNumber(new ChartNumber(Integer.toString(i)));
			QuestionAndResponse qAndR = null;
			List<QuestionAndResponse> list = new ArrayList<QuestionAndResponse>();
			report.setQAndRs(list);
			remainingItemsToProcess = remainingItemsToProcess - 10;
			int x = 10;
			if (remainingItemsToProcess < 0){
				x = 10 + remainingItemsToProcess;
			}
			for (int j = 0; j < x; j++){
				int accessor = ((i-1)*10) + j;
				if(accessor == sectionResponses.size()){
					//hacky fix to an issue i can't figure out
					//TODO fix in getCorrectSectionResponses
					StudentsResponsesReportDto blank = new StudentsResponsesReportDto();
					blank.setId(-1L);
					sectionResponses.add(blank);	
				}
				StudentsResponsesReportDto dto = sectionResponses.get(accessor);
				qAndR = new QuestionAndResponse();
				if (null != dto.getId() && dto.getId() != -1L){
					qAndR.setFoilId(dto.getStudentsResponses().getFoilId());
					qAndR.setTaskVariantId(dto.getStudentsResponses().getTaskVariantId());
					qAndR.setCorrectResponse(dto.getTaskVariantsFoils().getCorrectResponse());
					qAndR.setTaskIdentifier(dto.getTestSectionsTaskVariants().getTaskVariantPosition());
					String label = ResponseLabelUtil.getResponseLabel(dto.getTaskVariantsFoils().getResponseOrder(), dto.getReportTaskLayoutFormat());
					if (label.equals(""))
						label = "-";
					qAndR.setResponse(label);
				}else{
					qAndR.setResponse("-");
					qAndR.setTaskIdentifier(accessor + 1);
				}
				report.getQAndRs().add(qAndR);
			}
			reports.add(report);
		}
		return reports;
	}


	private List<StudentsResponsesReportDto> getCorrectSectionResponses(List<StudentsResponsesReportDto> studentsResponsesReportDtos, TestSection section) {
		List<StudentsResponsesReportDto> sectionResponses = new ArrayList<StudentsResponsesReportDto>();
		Integer position = 1;
		for (int i = 0; i < studentsResponsesReportDtos.size(); i++){
				StudentsResponsesReportDto dto = studentsResponsesReportDtos.get(i);
				if (section.getId().equals(dto.getTestSectionsTaskVariants().getTestSectionId())){
					if (dto.getTestSectionsTaskVariants().getTaskVariantPosition() > position){
						int numToFillIn = dto.getTestSectionsTaskVariants().getTaskVariantPosition() - position;
						for (int j = 0; j < numToFillIn; j++){
							StudentsResponsesReportDto blank = new StudentsResponsesReportDto();
							blank.setId(-1L);
							sectionResponses.add(blank);	
						}
						position = position + numToFillIn;
						sectionResponses.add(dto);
						position++;
					}else{
						sectionResponses.add(dto);
						position++;
					}
				}
		}
		return sectionResponses;
	}
}
