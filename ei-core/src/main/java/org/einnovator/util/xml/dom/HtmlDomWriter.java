/**
 * 
 */
package org.einnovator.util.xml.dom;

import java.io.PrintWriter;

import org.einnovator.util.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


/**
 * A HtmlDomWriter.
 *
 * @author Jorge Simão
 */
public class HtmlDomWriter extends DomWriter {

	protected String[] unprocessedTextTags = {"script"};
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of HtmlDomWriter.
	 *
	 */
	public HtmlDomWriter() {
	}
	
	//
	// DomWriter overrides
	//
	protected void writeText(Text text, PrintWriter out) {		 		
		Node node = text.getParentNode();
		if (node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
			String tagName = ((Element)node).getTagName();
			if (StringUtil.contains(unprocessedTextTags, tagName)) {
				out.print(text.getNodeValue());
			} else {
				super.writeText(text, out);
			}
			
		} else {
			super.writeText(text, out);
		}
	}

	
}
