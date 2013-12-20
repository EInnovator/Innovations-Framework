package org.einnovator.util.xml.dom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.einnovator.util.FileUtil;
import org.einnovator.util.StringUtil;
import org.einnovator.util.types.TypeUtil;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class DomUtil {

	public static final int DEFAULT_INDENT = 4;
	
	public static final String DEFAULT_ENCODING = "ISO-8859-1";
	
	static public void write(Node node, Writer w) {
		write(node, w, "xml", DEFAULT_ENCODING, DEFAULT_INDENT);
	}

	static public void writeHtml(Node node, Writer w) {
		write(node, w, "html", DEFAULT_ENCODING, DEFAULT_INDENT);
	}

	static public void writeHtml(Node node, PrintStream s) {
		 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s)));
		 writeHtml(node, out);
		 out.flush();
	}

	static public void writeHtml(Node node) {
		writeHtml(node, System.out);
	}

	static public void write(Node node, Writer w, String method, String encoding, int indent) {
		 //set up a transformer
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t;
		try {
			t = tf.newTransformer();
		} catch (TransformerConfigurationException element) {
			throw new RuntimeException(element);
		}
	    //t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.METHOD, method);
		t.setOutputProperty(OutputKeys.ENCODING, encoding);
		if (indent>0) {
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));			
		}
		if (node instanceof Document) {
			DocumentType docType = ((Document)node).getDoctype();
			if (docType != null) {
				if (docType.getPublicId()!=null) {
					t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, docType.getPublicId());					
				}
				if (docType.getSystemId()!=null) {
					t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType.getSystemId());					
				}
			}
		}
	
		StreamResult result = new StreamResult(w);
	    DOMSource source = new DOMSource(node);
	    try {
			t.transform(source, result);
		} catch (TransformerException element) {
			throw new RuntimeException(element);
		}
	}

	static public void write(Node node, String resource) throws IOException {
		FileWriter fw = new FileWriter(resource);
		write(node, fw);
	}

	static public void write(Node node, String resource, String method, String encoding, int indent) throws IOException {
		FileWriter fw = new FileWriter(resource);
		write(node, fw, method, encoding, indent);
	}
	
	static public void write(Node node, PrintStream s) {
		 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s)));
		 write(node, out);
		 out.flush();
	}
	
	static public void write(Node node, PrintStream s, String method, String encoding, int indent) {
		 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s)));
		 write(node, out, method, encoding, indent);
		 out.flush();		
	}
	
	static public void write(Node node) {
		write(node, System.out);
	}
	
	public static Document createDocument() {
		return createDocumentBuilder().newDocument();
	}

	public static DocumentBuilder createDocumentBuilder() {
		return createDocumentBuilder(true, true, false);
	}
		
	public static DocumentBuilder createDocumentBuilder(boolean namespaceWare, boolean validating, boolean expandEntityRef) {
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();	
		fact.setNamespaceAware(namespaceWare);
		fact.setExpandEntityReferences(expandEntityRef);
		fact.setValidating(validating);
		try {
			return fact.newDocumentBuilder();
		} catch (ParserConfigurationException element) {
			throw new RuntimeException(element);
		}
	}

	
	static public String toString(Document doc) {
		StringWriter sw = new StringWriter();
		write(doc, sw);
	   return sw.toString();
	}
	
	public static Document parse(InputStream in) {
		return parse(in, createDocumentBuilder());
	}

	public static Document parse(InputStream in, DocumentBuilder builder) {
		try {
			return builder.parse(in);
		} catch (SAXException element) {
			throw new RuntimeException(element);
		} catch (IOException element) {
			throw new RuntimeException(element);
		}
	}


	public static Document parse(String fn) {
		try {
			DocumentBuilder builder = createDocumentBuilder();			
			fn = FileUtil.absolute(fn);
			if (!fn.contains(":")) {
				fn = "file://" + fn;
			}
			return builder.parse(fn);
 		} catch (Exception element) {
			throw new RuntimeException(element);
 		}
	}

	public static Document parse(File file) {
		try {
			DocumentBuilder builder = createDocumentBuilder();			
			return builder.parse(file);
 		} catch (Exception element) {
			throw new RuntimeException(element);
 		}
	}

	public static Document parseString(String s) {
		return parseString(s, createDocumentBuilder());			
	}

	@SuppressWarnings("deprecation")
	public static Document parseString(String s, DocumentBuilder builder) {
		try {
			return builder.parse(new StringBufferInputStream(s));
 		} catch (Exception element) {
			throw new RuntimeException(element);
 		}
	}

	public static Element createElement(Document doc, Node parent, String tag) {	
		return createElement(doc, parent, tag, null);
	}
	
	public static Element createElement(Document doc, Node parent, String tag, String text) {
		Element element = doc.createElement(tag);	
		if (parent!=null) {
			parent.appendChild(element);
		}
		if (text!=null) {
			Text t = doc.createTextNode(text);
			element.appendChild(t);
		}
		return element;
	}
	
	public static Element createElement(Document doc, Node parent, String tag, String attrs, String text) {	
		Element element = createElement(doc, parent, tag, text);
		setAttributes(element, attrs);
		return element;
	}
	
	public static Element createElement(Document doc, Node parent, String tag, String attrs, String style, String text) {	
		Element element = createElement(doc, parent, tag, attrs, text);
		if (style!=null) element.setAttribute("style", style);
		return element;
	}
	
	public static void setAttributes(Element element, String attrs) {
		if (StringUtil.isEmpty(attrs)) return;
		String[] lattrs = attrs.split(";");
		for (String attr: lattrs) {
			if (StringUtil.isEmpty(attr)) {
				continue;
			}
			int i = attr.indexOf(':');
			if (i<0) {
				i = attr.indexOf('=');
			}
			if (i<0) {
				continue;
			}
			String name = attr.substring(0, i);
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			String value = attr.substring(i+1);
			if (value==null) {
				continue;
			}
			element.setAttribute(name, value);
		}
	}
	
	public static Comment createComment(Document doc, Node parent, String data) {
		Comment comment = doc.createComment(data);	
		if (parent!=null) {
			parent.appendChild(comment);
		}
		return comment;
	}

	static public String getText(Element element) {
		Node e_ = element.getFirstChild();
		if (e_ instanceof Text) {
			return ((Text)e_).getTextContent();
		}
		return null;
	}

	static public Element getChild(Element parent, String tag) {
		NodeList nodeList = parent.getElementsByTagName(tag);
		for (int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				return (Element)node;
			}
		}
		return null;
	}
	
	static public List<Element> getChildreen(Element parent, String tag) {
		List<Element> childreen = new ArrayList<Element>();
		NodeList nodeList = parent.getElementsByTagName(tag);
		for (int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				childreen.add((Element)node);
			}
		}
		return childreen;
	}

	static public String getChildText(Element parent, String tag) {
		return getChildText(parent, tag, (String)null);
	}

	static public String getChildText(Element parent, String tag, String defaultValue) {
		Element child = getChild(parent, tag);
		if (child==null) {
			return defaultValue;
		}
		return getText(child);
	}

	static public List<String> getChildreenText(Element parent, String tag) {
		NodeList nodeList = parent.getElementsByTagName(tag);
		List<String> textList = new ArrayList<String>();
		for (int i=0; i<nodeList.getLength(); i++) {
			Element child = (Element)nodeList.item(i);
			String text = getText(child);
			if (text!=null) {
				textList.add(text);
			}
		}
		return textList;
	}

	static public Document getDocument(Node node) {
		if (node instanceof Document) {
			return (Document)node;
		} else {
			return node.getOwnerDocument();
		}
	}

	static public Boolean getChildText(Element parent, String tag, Boolean defaultValue) {
		String value = getChildText(parent, tag);
		if (value==null) {
			return defaultValue;
		}
		return TypeUtil.isTrue(value);
	}

	static public String textContent(Element element, boolean blank) {
		StringBuffer sb = new StringBuffer();
		NodeList childs = element.getChildNodes();
		for (int i=0; i<childs.getLength(); i++) {
			Node child = childs.item(i);
			if (child.getNodeType()==Node.TEXT_NODE) {
				if (blank==true || !StringUtil.isBlank(child.getNodeValue())) {
					sb.append(child.getNodeValue());
				}
			}
		}		
		return sb.toString();
	}

}