package se.nrm.specify.ui.form.data.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Implementation of XmlHandler, containing helper methods for loading and parsing
 * mainly xml data
 */
public class XmlHandlerImpl implements XmlHandler {

    private final Logger logger = LoggerFactory.getLogger(XmlHandlerImpl.class);

    /**
     * Reads a java.io.File and returns it as a byte array
     * @param xmlFile, the java.io.File to be read
     * @return the file as a byte array
     * @throws IOException
     */
    @Override
    public byte[] readFile(File xmlFile) throws IOException {
        
        logger.info("readFile");
        
        FileInputStream is = new FileInputStream(xmlFile);
        FileChannel channel = is.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
        channel.read(buffer);
        
        return buffer.array();
    }

    /**
     * Writes a byte array to the java.io.File supplied
     * @param xmlFile the file to write to
     * @param bytes the data to be written
     * @throws IOException
     */
    @Override
    public void writeFile(File xmlFile, byte[] bytes) throws IOException {
        
        logger.info("writeFile");
        
        FileOutputStream os = new FileOutputStream(xmlFile);
        FileChannel channel = os.getChannel();
        channel.write(ByteBuffer.wrap(bytes));
        channel.close();
    }

    /**
     * Creates a w3c DOM Document from an input stream
     * @param inputStream the input stream to be parsed
     * @return an w3c DOM Document
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public Document parseXml(InputStream inputStream)
            throws ParserConfigurationException, SAXException, IOException {
         
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        
        docBuilder.setEntityResolver(new EntityResolver() {

            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException { 
                return new InputSource(new StringReader(""));
            }
        }); 
        return docBuilder.parse(inputStream);
    }

    /**
     * Creates a byte array from a w3c DOM Document
     * @param document the w3c DOM Document to be serialized
     * @return the document as a byte array
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    @Override
    public byte[] serializeXml(Document document)
            throws TransformerConfigurationException, TransformerException {
        
        logger.info("serializeXml");
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer idTrans = transformerFactory.newTransformer();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Source sourceOut = new DOMSource(document);
        Result targetOut = new StreamResult(byteArrayOutputStream);
        idTrans.transform(sourceOut, targetOut);

        return byteArrayOutputStream.toByteArray();
    }
}
