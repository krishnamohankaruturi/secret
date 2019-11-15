package edu.ku.cete.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;
import edu.ku.cete.domain.report.InterimReportContext;
import edu.ku.cete.domain.report.PredictiveReportCreditPercent;

@Component
public class InterimOrganizationSummaryReportGenerator extends ReportGenerator {

	@Value("/templates/xslt/reports/interimSummaryReport.xsl")
	private Resource interimSummaryXslFile;
	
	@Value("/images/reports/interimStudentReportKAPlogo.svg")
	private Resource interimStudentReportKAPlogo;
	
	@Value("/images/reports/interimStudentKAPFooterLogo.svg")
	private Resource kapFooterLogoFile;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InterimOrganizationSummaryReportGenerator.class);
	
	public void generateReportFile(PredictiveReportCreditPercent data, OutputStream out) throws Exception {

			InterimReportContext reportData = new InterimReportContext();
			reportData.setData(data);
			
			reportData.setLogoPath(interimStudentReportKAPlogo.getFile().toURI().toString());
			
			File footerLogoFile = kapFooterLogoFile.getFile();
			reportData.setFooterLogoPath(footerLogoFile.toURI().toString());
			reportData.setReportQuestionInformation(data.getReportQuestionInformation());
				
			XStream xstream = new XStream();
			xstream.alias("interimReportDetails", InterimReportContext.class);
			xstream.aliasField("data", InterimReportContext.class, "data");
			xstream.aliasField("reportQuestionInformation", InterimReportContext.class, "reportQuestionInformation");
			TraxSource source = new TraxSource(reportData, xstream);
	
			generatePdf(interimSummaryXslFile.getFile(), out, source);
	
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
}