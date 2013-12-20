package org.einnovator.util.xml.dom;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.einnovator.util.StringUtil;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;


/**
 * A DomWriter.
 *
 * @author Jorge Simão
 */
public class DomWriter {

	protected int indent = DomUtil.DEFAULT_INDENT;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of DomWriter.
	 *
	 */
	public DomWriter() {
	}
	
	//
	// Write methods
	//
	
	public void write(Node node, String resource) throws IOException {
		PrintStream fw = new PrintStream(resource);
		write(node, fw);
	}

	
	public void write(Node node) {
		write(node, System.out);
	}
	
	public void write(Node node, PrintStream out) {
		 PrintWriter w = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
		 write(node, w);
		 w.flush();
	}
	
	public void write(Node node, PrintWriter out) {
		if (node instanceof Document) {
			writeXmlPI((Document)node, out);
			writeDocType((Document)node, out);
		}
		write(node, out, 0, false);
	}
	
	protected void write(Node node, PrintWriter out, int n, boolean inline) {
		switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				if (!inline)  {
					StringUtil.hspace(n*indent, out);
				}
				boolean inlineContent = inline || isInlineContent(node);
				writeStartTagChecked((Element) node, out, inlineContent);
				break;
			case Node.TEXT_NODE:
				writeText((Text)node, out);
				break;
			case Node.CDATA_SECTION_NODE:
				CDATASection cdata = (CDATASection)node;
				out.print("<![CDATA[");
				out.print(cdata.getNodeValue());
				out.print("]]>");
				break;
			case Node.COMMENT_NODE:
				Comment cmt = (Comment)node;	
				out.print("<!--");
				out.print(cmt.getNodeValue());
				out.println("-->");
				break;
			case Node.DOCUMENT_FRAGMENT_NODE:
				n--;
				break;
			case Node.DOCUMENT_NODE:
				n--;
				break;	
			case Node.ENTITY_NODE:
				break;
			case Node.ENTITY_REFERENCE_NODE:
				break;
			case Node.NOTATION_NODE:
				break;
			case Node.PROCESSING_INSTRUCTION_NODE:
				ProcessingInstruction pi = (ProcessingInstruction)node;
				out.println("<?");
				if (pi.getTarget()!=null) {
					out.print(pi.getTarget());
				}
				if (pi.getData()!=null) {
					out.print(" ");
					out.print(pi.getData());
				}
				out.println("?>");
		        break;
		}
		Node child = node.getFirstChild();
		while (child!=null) {
			write(child, out, n+1, inline);
			child = child.getNextSibling();
		}
		if (node.getNodeType()==Node.ELEMENT_NODE) {
			if (node.hasChildNodes()) {
				boolean inlineContent = inline || isInlineContent(node);
				if (!inlineContent)  {
					StringUtil.hspace(n*indent, out);
				}
				writeEndTag((Element)node, out, inline);
			}
		}
	}

	protected boolean isInlineContent(Node node) {
		if (node.hasChildNodes()) {
			Node child = node.getFirstChild();
			if (child.getNodeType()==Node.TEXT_NODE) {
				return true;
			}
		}
		return false;
	}
	
	protected void writeText(Text text, PrintWriter out) {		 		
		String value = text.getNodeValue();
		if (value!=null) {
			value = value.trim();
			if (!value.isEmpty()) {
				out.print(value);
			}
		}		
	}
	
	protected void writeAttrs(Element e, PrintWriter out) {	
		NamedNodeMap attrs = e.getAttributes();
		for (int i=0; i<attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			if (attr==null) continue;
			String name = attr.getNodeName();
			String val = attr.getNodeValue();
			//if (xt!=null) val = xt.transform(attr, val);
			out.printf(" %s=\"%s\"", name, val);
		}		
	}
	
	protected void writeStartTag(Element e, PrintWriter out) {
		writeStartTag(e, out, false);
	}
	
	protected void writeStartTag(Element e, PrintWriter out, boolean inline) {
		out.print("<");
		out.print(e.getTagName());
		writeAttrs(e, out);
		out.printf(">");
		if (!inline) {
			out.println();
		}
	}
	protected void writeStartTagChecked(Element e, PrintWriter out) {		
		writeStartTagChecked(e, out, false);
	}
	
	protected void writeStartTagChecked(Element e, PrintWriter out, boolean inline) {	
		out.print("<");
		out.print(e.getTagName());
		writeAttrs(e, out);
		if (e.hasChildNodes()) {
			out.printf(">");
		}
		else {
			out.printf("/>");		
		}
		if (!inline) {
			out.println();		
		}
	}

	protected void writeEndTag(Element e, PrintWriter out) {
		writeEndTag(e, out, false);
	}
	
	protected void writeEndTag(Element e, PrintWriter out, boolean inline) {
		out.print("</");
		out.print(e.getTagName());
		out.print(">");
		if (!inline) {
			out.println();				
		}
	}
	
	protected void writeEndTagChecked(Element e, PrintWriter out) {
		writeEndTagChecked(e, out, false);
	}
	
	protected void writeEndTagChecked(Element e, PrintWriter out, boolean inline) {		 
		if (e.hasChildNodes()) {
			writeEndTag(e, out, inline);
		}
	}

	protected void writeXmlPI(Document doc, PrintWriter out) {
		out.print("<?xml");
		out.printf(" version=\"%s\"", doc.getXmlVersion());
		out.printf(" encoding=\"%s\"", doc.getXmlEncoding()!=null ? doc.getXmlEncoding() : DomUtil.DEFAULT_ENCODING);
		out.printf(" standalone=\"%s\"", doc.getXmlStandalone() ? "yes" : "no");
		out.println("?>");
	}

	protected void writeDocType(Document doc, PrintWriter out) {
		DocumentType docType = doc.getDoctype();
		if (docType!=null) {
			out.print("<!DOCTYPE");
			out.printf(" %s", docType.getName());
			if (docType.getPublicId()!=null) {
				out.printf(" %s", docType.getPublicId());				
			}
			if (docType.getSystemId()!=null) {
				out.printf(" %s", docType.getSystemId());
			}
			out.println("!>");
		}
	}
	
	public static void dump(Node node) {
		dump(node, 0);
	}
	
	private static void dump(Node node, int n) {
		StringUtil.hspace(n*DomUtil.DEFAULT_INDENT, System.out);
		System.out.println(node);
		Node child = node.getFirstChild();
		while (child!=null) {
			dump(child, n+1);
			child = child.getNextSibling();
		}
	}

}
