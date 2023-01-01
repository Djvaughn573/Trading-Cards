package com.vaughn.tradingcards.ui.preference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.osgi.service.datalocation.Location;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XML {
	public DocumentBuilder documentBuilder;
	public Document document;
	public Transformer transformer;
	public String filePath, fileFolder, fileName;
	public File file;
	
	public XML(Location filePath, String fileFolder, String fileName) {
		defaultConstructor();
		
		this.filePath = locationToString(filePath);
		this.fileFolder = fileFolder;
		this.fileName = fileName;
		this.file = new File(this.filePath + File.separator + this.fileFolder 
				+ File.separator + this.fileName + ".xml");
	}
	
	public void defaultConstructor() {
		document = setDocument();
		transformer = setTransformer();
	}

	public String locationToString(Location location) {
		return new File(location.getURL().getPath()).toString();
	}

	public DocumentBuilder setDocumentBuilder() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db;
		} catch (ParserConfigurationException e) {
			return null;
		}
	}
	
	public Document setDocument() {
		if (documentBuilder == null) {
			documentBuilder = setDocumentBuilder();
		}
		
		Document doc = documentBuilder.newDocument();
		doc.setXmlVersion("1.0");
		doc.setXmlStandalone(true);
		
		return doc;
	}
	
	public Transformer setTransformer() {
		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute("indent-number", new Integer(4));
		
		Transformer t;
		try {
			t = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			return null;
		}

//		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.setOutputProperty(OutputKeys.VERSION, "1.0");
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty(OutputKeys.STANDALONE, "yes");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		
		return t;
	}
	
	public boolean loadFile(File file) {
		if (document == null) {
			document = setDocument();
		}
		try {
			document = documentBuilder.parse(file);
			document.normalize();
			return true;
		} catch (SAXException | IOException event) {
			return false;
		}
	}
	
	public boolean saveFile(File file) {
		try {
			File dir = new File(filePath + File.separator + fileFolder);
			dir.mkdir();
			
//			System.out.println("Saving file: " + file.getPath().toString());
			
			file.createNewFile();
			
			FileOutputStream output = new FileOutputStream(file);

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(output);
			
			transformer.transform(source, result);
			return true;
		} catch (IOException | TransformerException event) {
			return false;
		}
	}
	
}
