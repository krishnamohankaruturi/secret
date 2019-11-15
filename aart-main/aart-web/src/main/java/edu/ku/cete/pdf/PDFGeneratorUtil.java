package edu.ku.cete.pdf;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;

public class PDFGeneratorUtil {


    public static final void generatePDF(Source xml, File xslt, OutputStream out, String serverPath) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslt.getCanonicalPath()));
        FopFactory fopFactory = FopFactory.newInstance(new File(serverPath).toURI());
        
        //fopFactory.setBaseURL(serverPath);
        Fop fop = fopFactory.newFop("application/pdf", out);
        Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(xml, res);
        return;
    }

}