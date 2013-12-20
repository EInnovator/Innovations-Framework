package org.einnovator.util.xml.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class DomSaxParser  {
	public static final String KEY_LOCATION = "location";

	protected DocumentBuilderFactory docBuilderFactory;

	protected DocumentBuilder docBuilder;

	protected Document doc;
   
	protected Stack<Element> elementStack = new Stack<Element>();
   
	protected StringBuilder textBuffer = new StringBuilder();

	//
	// Constructors
	//
	
    /**
     * Create instance of DomSaxParser.
     *
     */
    public DomSaxParser() {
	}

    /**
     * Create instance of DomSaxParser.
     *
     * @param uri
     * @throws SAXException 
     */
    public DomSaxParser(String uri) throws SAXException {
    	parse(uri);
    }

    /**
     * Create instance of DomSaxParser.
     *
     * @param file
     * @throws SAXException 
     */
    public DomSaxParser(File file) throws SAXException {
    	parse(file);
    }

    public DomSaxParser(InputStream in) throws SAXException {
	    	parse(in);
    }

    //
    //
    //
    
	public void parse(String uri) throws SAXException {
		try {
			if (uri.startsWith("file:")) {
				uri = uri.substring("file:".length());
			}
			parse(new FileInputStream(uri));
		} catch (FileNotFoundException e) {
		    throw new RuntimeException(e);	
		}
	}

	public void parse(File file) throws SAXException {
		try {
			parse(new FileInputStream(file));
		} catch (FileNotFoundException e) {
		    throw new RuntimeException(e);	
		}
	}
	
	public Document getDocument() {
		return doc;
	}

	public void parse(InputStream in) throws SAXException {
		try {
		    SAXParserFactory factory = SAXParserFactory.newInstance();
		    factory.setNamespaceAware(true);
		    SAXParser parser = factory.newSAXParser();
		    docBuilderFactory = DocumentBuilderFactory.newInstance();
		    docBuilderFactory.setNamespaceAware(true);
		    docBuilderFactory.setValidating(true);
		    docBuilder = docBuilderFactory.newDocumentBuilder();
		   //docBuilder.setEntityResolver(new EntityResolverImpl());
		    doc = docBuilder.newDocument();
		    parser.parse(in, new DomSaxHandler());
		} catch (final ParserConfigurationException e) {
		    throw new RuntimeException(e);
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}	
	 }
 
	public static class EntityResolverImpl implements EntityResolver {
        public InputSource resolveEntity(String publicId, String systemId) 
        		throws SAXException, IOException {
        	System.out.println(this + ".resolveEntity:" + publicId + " " + systemId);
        	return new InputSource(new StringReader("XXXX"));
        }
    }
	
	public static class NodeLocation {
		protected int lineNumber;
		protected int columnNumber;

		
		public NodeLocation(int lineNumber, int columnNumber) {
			this.lineNumber = lineNumber;
			this.columnNumber = columnNumber;
		}

		public int getLineNumber() {
			return lineNumber;
		}

		public void setLineNumber(int lineNumber) {
			this.lineNumber = lineNumber;
		}

		public int getColumnNumber() {
			return columnNumber;
		}

		public void setColumnNumber(int columnNumber) {
			this.columnNumber = columnNumber;
		}
		
		@Override
		public String toString() {
			return "line:" + lineNumber + " column:" + columnNumber;
		}
	}
	
	class DomSaxHandler extends DefaultHandler2 {
		private Locator locator;

		@Override
		public void setDocumentLocator(final Locator locator) {
			this.locator = locator;
		}

		@Override
		public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
			addTextNode();
			final Element e = doc.createElementNS(uri, qName);
			for (int i = 0; i < attributes.getLength(); i++) {
			    e.setAttributeNS(attributes.getURI(i), attributes.getQName(i), attributes.getValue(i));
			}
			e.setUserData(KEY_LOCATION, new NodeLocation(locator.getLineNumber(), locator.getColumnNumber()), null);
			elementStack.push(e);
	    }

		@Override
		public void endElement(final String uri, final String localName, final String qName) {
			addTextNode();
			Element e = elementStack.pop();
			appendNode(e);
		}

		protected void appendNode(Node node) {
			if (elementStack.isEmpty()) { //root
				doc.appendChild(node);
			} else {
				elementStack.peek().appendChild(node);
			}			
		}
		
		@Override
		public void characters(final char ch[], final int start, final int length) throws SAXException {
		    textBuffer.append(ch, start, length);
		}
		
		private void addTextNode() {
			if (textBuffer.length() > 0) {
				Element e = elementStack.peek();
				Node textNode = doc.createTextNode(textBuffer.toString());
				e.appendChild(textNode);
				textBuffer.delete(0, textBuffer.length());
			}
		}
		
		@Override
		public void processingInstruction(String target, String data) throws SAXException {
			//System.out.println(this + ".processingInstruction:" + target + " " + data);
			appendNode(doc.createProcessingInstruction(target, data));
		}
		
		@Override
		public void notationDecl(String name, String publicId, String systemId) {
			//System.out.println(this + ".notationDecl:" + name + " " + publicId + " " + systemId);
		}

		@Override
		public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) {
			//System.out.println(this + ".unparsedEntityDecl:" + name + " " + publicId + " " + systemId + " " + notationName);			
		}

		@Override
		public void startDTD(String name, String publicId, String systemId) throws SAXException {
			//System.out.println(this + ".startDTD:" + name + " " + publicId + " " + systemId);
		}

		@Override
		public void endDTD() throws SAXException {
			//System.out.println(this + ".endDTD:");			
		}
		
		@Override
		public void comment(char[] ch, int start, int length) throws SAXException {
			appendNode(doc.createComment(new String(ch, start, length)));
		}

		@Override
		public void startCDATA() throws SAXException {
		}

		@Override
		public void endCDATA() throws SAXException {		
		}
	}
	
	public static String locationToString(Element element) {
		return element!=null ? " " + element.getUserData(KEY_LOCATION).toString() : "";
	}
	
	
	public static String elementName(Element e) {
		return e.getNamespaceURI()!=null ? e.getNamespaceURI() + ":" + e.getTagName() : e.getTagName();
	}

}
