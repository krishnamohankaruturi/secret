/**
 * 
 */
package edu.ku.cete.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.student.Student;
import edu.ku.cete.pdf.PDFGeneratorUtil;
import edu.ku.cete.report.QuestionAndResponse;
import edu.ku.cete.report.RawScoreReport;
import edu.ku.cete.report.StudentProblemReport;
import edu.ku.cete.report.StudentResponseReport;
import edu.ku.cete.score.StudentRawScore;
import edu.ku.cete.util.xml.ChartNumberConverter;
import edu.ku.cete.util.xml.SectionNameConverter;

/**
 * @author neil.howerton
 *
 */
@Component
public class ReportGenerator {

    /**
     *
     */
    @Value("${report.rawscore.xslt}")
    private String rawScoreXslt;

    /**
     * Loader to pull in the xslt files.
     */
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * This method is used to genreate a raw score report that is described by the RawScoreReport parameter.
     * @param rawScoreReport {@link RawScoreReport}
     * @param out {@link OutputStream}
     * @param imgFiles 
     * @throws TransformerException TransformerException
     * @throws IOException  IOException
     * @throws FOPException FOPException
     */
    public final void generateRawScoreReport(RawScoreReport rawScoreReport, OutputStream out, File[] imgFiles, String serverPath) throws Exception {
        XStream xstream = new XStream();
        // add in any aliasing that is needed.
        xstream.alias("rawscorereport", RawScoreReport.class);
        xstream.alias("studentrawscore", StudentRawScore.class);
        xstream.alias("studentproblemreport", StudentProblemReport.class);
        xstream.alias("student", Student.class);
        xstream.alias("studentresponsereport", StudentResponseReport.class);
        xstream.alias("qandr", QuestionAndResponse.class);
        xstream.useAttributeFor(StudentResponseReport.class, "sectionName");
        xstream.registerConverter(new SectionNameConverter());
        xstream.useAttributeFor(StudentResponseReport.class, "chartNumber");
        xstream.registerConverter(new ChartNumberConverter());
        
        // generate the source the xslt will need to create the pdf.
        TraxSource source = new TraxSource(rawScoreReport, xstream);

        // load the xslt file.
        Resource resource = resourceLoader.getResource(rawScoreXslt);

        PDFGeneratorUtil.generatePDF(source, resource.getFile(), out, serverPath);
        // delete all temporary files
        for(File file : imgFiles){
            try {
    			if (file != null) {
    				file.delete();
    			}
    			file = null;
    		} catch (Exception e) {
    			//if file path is null or filename is null it will come here.
    			//logger.error("Unknown Error in deleting file", e);
    		}
        }
        
    }
}
