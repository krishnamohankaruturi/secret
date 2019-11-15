package edu.ku.cete.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.ItiStudentReport;
import edu.ku.cete.domain.ItiStudentReportEE;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.MicroMap;
import edu.ku.cete.domain.report.student.DLMStudentReport;
import edu.ku.cete.domain.report.student.StudentRptCCArea;
import edu.ku.cete.domain.report.student.StudentRptEssentialElement;
import edu.ku.cete.domain.report.student.StudentRptLinkageLevel;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.MicroMapService;
import edu.ku.cete.service.report.DLMReportsService;

@Component
public class DLMStudentReportGenerator extends ReportGenerator {
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DLMStudentReportGenerator.class);
	
	@Autowired
	private ItiTestSessionService itiTestSessionService;

	@Autowired
	private MicroMapService microMapService;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private DLMReportsService dlmReportsService;
	
	@Value("/images/dlm_logo_final_registered_312x128.png")
	private Resource dlmLogoFile;
	
	@Value("/images/target.png")
	private Resource targetIconFile;
	
	@Value("${report.dlmstudent.writing.ee.subjects}")
	private String dlmStudentReportWritingEESubjects;
	
	@Value("${report.dlmstudent.teststatus.complete}")
	private String dlmStudentReportTestStatusComplete;
	
	public String getDLMStudentReportIconPath(String iconName) throws IOException {
		File iconFile = null;
		if(iconName.equalsIgnoreCase("dlmlogo")){
			iconFile = dlmLogoFile.getFile();
		} else if(iconName.equalsIgnoreCase("targeticon")){
			iconFile = targetIconFile.getFile();
		} 
		return iconFile.toURI().toString();
	}
	
	public void generatePdf(File foFile, OutputStream out, TraxSource source) throws IOException, Exception {
		
		try {
			
			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					foFile.getCanonicalPath()));
	
			FOUserAgent foUserAgent = getFopFactory().newFOUserAgent();
			
			// Construct fop with desired output format
			Fop fop = getFopFactory().newFop(MimeConstants.MIME_PDF, foUserAgent,
					out);
	
			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());
	
			// Start XSLT transformation and FOP processing
			transformer.transform(source, res);
		} 
		catch(IOException ie)
		{
			LOGGER.error("IOException in generatePdf() method.", ie);
		}
		catch(SAXException se)
		{
			LOGGER.error("SAXException in generatePdf() method.", se);
		}
		catch(TransformerException te)
		{
			LOGGER.error("TransformerException in generatePdf() method.", te);
		}
		catch(NullPointerException ne)
		{
			LOGGER.error("NullPointerException in generatePdf() method.", ne);
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public void setupStudentReportPDFGeneration(DLMStudentReport studentReport, OutputStream out)
			throws Exception {
		try{
			XStream xstream = new XStream();
			xstream.alias("studentReportData", DLMStudentReport.class);
			
			TraxSource source = new TraxSource(studentReport, xstream);
			Resource resource = resourceLoader.getResource("/templates/xslt/studentreport.xsl");
			generatePdf(resource.getFile(), out, source);
		} 
		catch(IOException ie)
		{
			LOGGER.error("IOException in setupRosterReportPDFGeneration() method.", ie);
		}
		catch(NullPointerException ne){
			LOGGER.error("NullPointerException in setupRosterReportPDFGeneration() method.", ne);
		}
		catch(Exception ne){
			LOGGER.error("Exception in setupRosterReportPDFGeneration() method.", ne);
		}
	}

	public List<StudentRptCCArea> generateStudentReportData(Long studentID, Long subjectId, Long testCycleID) throws IOException {
		//Identify if the EE is writing. 
		//This is done only for certain subjects. As of now, only ELA
		//Set in upload.properties file
		List<String> dlmWritingEESubjects = Arrays.asList(dlmStudentReportWritingEESubjects.split("\\s*,\\s*"));
		
		//To check if the EEs with the subjectId should be checked for writing
		String subjectAbbrName = contentAreaService.selectByPrimaryKey(subjectId).getAbbreviatedName();
		
		//This report provides student results so far for this school year. 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	       
		List<ItiStudentReport> itiTestHistroyData = itiTestSessionService.selectByStudentIdAndUnUsedStatusAndSubject(studentID, subjectId, currentSchoolYear, testCycleID);
		List<MicroMap> microMapData = microMapService.selectMicroMapByItiHistoryStudentId(studentID);
		Map<String, String> eeLMMacroMap = new HashMap<String, String>();
		for (MicroMap microMapRow : microMapData) {
			eeLMMacroMap.put(microMapRow.getContentFrameworkDetailId()+ microMapRow.getLinkageLabel(),microMapRow.getLinkageLevelShortDesc());
		}
		List<StudentRptCCArea> reportData = new ArrayList<StudentRptCCArea>();
		for (ItiStudentReport itiStudentReport : itiTestHistroyData) {
			
			String schoolModel = itiStudentReport.getPoolType();
			String studentState = itiStudentReport.getStateName();
			Long stateID = itiStudentReport.getStateID();
			/*** US17204: EP: Production - DLM Student Progress report not displaying information when Multi-EE ***/
			/*if(schoolModel==null || schoolModel.toUpperCase().contains("MULTIEE"))
			{
				return null;
			}*/
			LOGGER.debug("studentState:"+studentState+" schoolModel: "+schoolModel+" StateID:"+stateID);
			
			StudentRptCCArea claimConceptualArea = new StudentRptCCArea();
			claimConceptualArea.setTargetLogo(getDLMStudentReportIconPath("targeticon"));
			if(StringUtils.isNotEmpty(itiStudentReport.getClaim())) {
				claimConceptualArea.setClaim(itiStudentReport.getClaim().split(" - ")[0]);
			} else {
				claimConceptualArea.setClaim(StringUtils.EMPTY);
			}
			if(StringUtils.isNotEmpty(itiStudentReport.getConceptualArea())) {
				claimConceptualArea.setConceptualArea(itiStudentReport.getConceptualArea());
			} else {
				claimConceptualArea.setConceptualArea(StringUtils.EMPTY);
			}
			
			LOGGER.debug("Claim: "+itiStudentReport.getClaim().split(" - ")[0]+" Conceptual Area:"+itiStudentReport.getConceptualArea());
			
			List<StudentRptEssentialElement> eEssentialElementList = new ArrayList<StudentRptEssentialElement>();
			for (ItiStudentReportEE itiStudentReportEE : itiStudentReport.getItiStudentReportEE()) {
				StudentRptEssentialElement essentialElement = new StudentRptEssentialElement();
				String[] eElementString = itiStudentReportEE.getEssentialElement().split(" - ");
				essentialElement.setEeCode(eElementString[0]);
				long eElementId = itiStudentReportEE.getEssentialElementId();
				if(eElementString.length>1) {
					essentialElement.setEeDesc(eElementString[1]);
					LOGGER.debug("EE-ID: "+eElementId+" studentID:"+studentID+" EEString: "+eElementString[0]+" "+eElementString[1]);
				}
				StudentRptLinkageLevel initialPrecursor = new StudentRptLinkageLevel();
				initialPrecursor.setAdministered("");
				initialPrecursor.setDesc(eeLMMacroMap.get(eElementId + "Initial Precursor"));
				if(StringUtils.equalsIgnoreCase("SCI", subjectAbbrName)) {
					initialPrecursor.setLinkageLevel("Initial");
				} else {
					initialPrecursor.setLinkageLevel("Initial Precursor");
				}
				
				StudentRptLinkageLevel distalPrecursor = new StudentRptLinkageLevel();
				distalPrecursor.setAdministered("");
				distalPrecursor.setDesc(eeLMMacroMap.get(eElementId + "Distal Precursor"));
				distalPrecursor.setLinkageLevel("Distal Precursor");
				distalPrecursor.setSessionLevel("no");
				
				StudentRptLinkageLevel proximalPrecursor = new StudentRptLinkageLevel();
				proximalPrecursor.setAdministered("");
				proximalPrecursor.setSessionLevel("no");
				proximalPrecursor.setDesc(eeLMMacroMap.get(eElementId + "Proximal Precursor"));
				if(StringUtils.equalsIgnoreCase("SCI", subjectAbbrName)) {
					proximalPrecursor.setLinkageLevel("Precursor");
				} else {
					proximalPrecursor.setLinkageLevel("Proximal Precursor");
				}
				
				StudentRptLinkageLevel target = new StudentRptLinkageLevel();
				target.setAdministered("");
				target.setDesc(eeLMMacroMap.get(eElementId + "Target"));
				target.setLinkageLevel("Target");
				target.setSessionLevel("no");
				
				StudentRptLinkageLevel successor = new StudentRptLinkageLevel();
				successor.setAdministered("");
				successor.setDesc(eeLMMacroMap.get(eElementId + "Successor"));
				successor.setLinkageLevel("Successor");
				successor.setSessionLevel("no");
				
				for (ItiTestSessionHistory itiTestSessionHistoryRow : itiStudentReportEE.getItiTestSessionHistory()) {
					
					boolean isEEWritingType = false;
					Long testId = itiTestSessionHistoryRow.getStudTestID();
					String testStatus = itiTestSessionHistoryRow.getStudentTestStatus();
					
					//If subject is in in dlmWritingEESubjects, check if EE is writing type (Eg: If ELA, check if EE is writing type)
					if (dlmWritingEESubjects.contains(subjectAbbrName)){
						/*** US17204: EP: Production - DLM Student Progress report not displaying information when Multi-EE ***/
						//if(schoolModel.toUpperCase().contains("SINGLEEE"))
						isEEWritingType = dlmReportsService.checkIfIntegratedEEisWritingType(eElementId);
						/*else if(schoolModel.toUpperCase().contains("MULTIEE"))
							isEEWritingType = dlmReportsService.checkIfYearEndEEisWritingType(eElementString[0], testId);
							*/
					}
					LOGGER.debug("testId: "+testId+" testStatus:"+testStatus+" isEEWritingType: "+isEEWritingType);
					
					
					//Calculate percent of correct responses to determine Mastery
					double percentCorrectScore = -1;
					int numCorrect = -1;
					int total = -1;
					
					if(!isEEWritingType && testStatus.equalsIgnoreCase(dlmStudentReportTestStatusComplete))
					{
						total = dlmReportsService.countTotalNumberOfScoreableItems(studentID, testId);
						numCorrect = dlmReportsService.countNumberOfScoreableItemsWithCorrectResponses(studentID, testId);
						if(total > 0)
							percentCorrectScore = (double)numCorrect/total * 100;
						else if(total == 0)
							percentCorrectScore = 0;
					}
					
					LOGGER.debug("numCorrect: "+numCorrect+" total:"+total+" percentCorrectScore: "+percentCorrectScore);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
					String administeredDate = "";
					if (itiTestSessionHistoryRow.getAdministeredDate() != null) 
						administeredDate = dateFormat.format(itiTestSessionHistoryRow.getAdministeredDate());
					
					LOGGER.debug("LinkageLevel: "+itiTestSessionHistoryRow.getLinkageLevel());
					
					if(initialPrecursor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel()))
					{
						initialPrecursor.setSessionLevel("yes");
						initialPrecursor.setAdministered(administeredDate);
						initialPrecursor.setTestStatus(testStatus);
						initialPrecursor.setPercentCorrectScoreOfScoreableItems(-1);
						initialPrecursor.setWritingType(false);
						
						//If Student completed the test, calculate the score of the most recent test 
						if(testStatus.equalsIgnoreCase(dlmStudentReportTestStatusComplete))
						{
							//If Writing Type, Mastery level = "Assessed"
							if(isEEWritingType)
								initialPrecursor.setWritingType(isEEWritingType);
							else
								initialPrecursor.setPercentCorrectScoreOfScoreableItems(percentCorrectScore);
						}
					}
					
					if(!StringUtils.equalsIgnoreCase("SCI", subjectAbbrName) && 
							distalPrecursor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						distalPrecursor.setSessionLevel("yes");
						distalPrecursor.setAdministered(administeredDate);
						distalPrecursor.setTestStatus(testStatus);
						distalPrecursor.setPercentCorrectScoreOfScoreableItems(-1);
						distalPrecursor.setWritingType(false);
						
						if(testStatus.equalsIgnoreCase(dlmStudentReportTestStatusComplete))
						{
							if(isEEWritingType)
								distalPrecursor.setWritingType(isEEWritingType);
							else
								distalPrecursor.setPercentCorrectScoreOfScoreableItems(percentCorrectScore);
						}
						
					}
					
					if(proximalPrecursor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						proximalPrecursor.setSessionLevel("yes");
						proximalPrecursor.setAdministered(administeredDate);
						proximalPrecursor.setTestStatus(testStatus);
						proximalPrecursor.setPercentCorrectScoreOfScoreableItems(-1);
						proximalPrecursor.setWritingType(false);
						
						if(testStatus.equalsIgnoreCase(dlmStudentReportTestStatusComplete))
						{
							if(isEEWritingType)
								proximalPrecursor.setWritingType(isEEWritingType);
							else
								proximalPrecursor.setPercentCorrectScoreOfScoreableItems(percentCorrectScore);
						}
					}
					
					if(target.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						target.setSessionLevel("yes");
						target.setAdministered(administeredDate);
						target.setTestStatus(testStatus);
						target.setPercentCorrectScoreOfScoreableItems(-1);
						target.setWritingType(false);
						
						if(testStatus.equalsIgnoreCase(dlmStudentReportTestStatusComplete))
						{
							if(isEEWritingType)
								target.setWritingType(isEEWritingType);
							else
								target.setPercentCorrectScoreOfScoreableItems(percentCorrectScore);
						}
					}
					
					if(!StringUtils.equalsIgnoreCase("SCI", subjectAbbrName) && 
							successor.getLinkageLevel().equalsIgnoreCase(itiTestSessionHistoryRow.getLinkageLevel())){
						successor.setSessionLevel("yes");
						successor.setAdministered(administeredDate);
						successor.setTestStatus(testStatus);
						successor.setPercentCorrectScoreOfScoreableItems(-1);
						successor.setWritingType(false);
						
						if(testStatus.equalsIgnoreCase(dlmStudentReportTestStatusComplete))
						{
							if(isEEWritingType)
								successor.setWritingType(isEEWritingType);
							else
								successor.setPercentCorrectScoreOfScoreableItems(percentCorrectScore);
						}
					}
				}
				
				List<StudentRptLinkageLevel> linkageLevels = new ArrayList<StudentRptLinkageLevel>();
				linkageLevels.add(initialPrecursor);
				if(!StringUtils.equalsIgnoreCase("SCI", subjectAbbrName)) {
					linkageLevels.add(distalPrecursor);
				}
				linkageLevels.add(proximalPrecursor);
				linkageLevels.add(target);
				if(!StringUtils.equalsIgnoreCase("SCI", subjectAbbrName)) {
					linkageLevels.add(successor);
				}
				essentialElement.setLevelDetails(linkageLevels);
				eEssentialElementList.add(essentialElement);
			}
			claimConceptualArea.seteEList(eEssentialElementList);
			reportData.add(claimConceptualArea);
		}
		return reportData;

	}

}