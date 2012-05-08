package se.nrm.specify.ui.form.data.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Interface containing helper methods for loading and parsing
 * mainly xml data
 */

public interface XmlHandler {

    public byte[] readFile(File xmlFile) throws IOException;

    public void writeFile(File xmlFile, byte[] bytes) throws IOException;

    public Document parseXml(InputStream is)
            throws ParserConfigurationException, SAXException, IOException;

    public byte[] serializeXml(Document document) throws TransformerConfigurationException, TransformerException;
}
