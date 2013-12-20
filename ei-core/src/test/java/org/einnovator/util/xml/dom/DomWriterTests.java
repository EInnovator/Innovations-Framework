/**
 * 
 */
package org.einnovator.util.xml.dom;

import org.einnovator.util.xml.dom.DomUtil;
import org.einnovator.util.xml.dom.DomWriter;
import org.einnovator.util.xml.dom.HtmlDomWriter;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * A DomWriterTests.
 *
 * @author Jorge Simão
 */
public class DomWriterTests {

	@Test
	public void test0() {
		String s = "<?xml version=\"1.0\" ?>" +
		"<!DOCTYPE html  >" + 
		//"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\" >" + 
		//"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\" >" + 
		"<!-- Testing XML scanner -->" +
		"<html style=\"width:100%\">" +
			"<head/>" +
			"<body><p>Hello (X)HTML</p>" + 
			"<a href=\"${url}?initial=${prevInitial}\" style=\"float:left\">&lt;prev</a>" +
			"<script type=\"text/javascript\" src=\"/js/org.einnovator/event.js\"></script>" +
			"<script type=\"text/javascript\">" +
			"function ajaxPrev(event) {" +
			"	ajaxGet(\"main\", \"xxx\");" +
			"	return cancelEvent(event);" +
			"}" +
			"</script>" +
			"</body>" +
				"</html>";
		Document doc = DomUtil.parseString(s);		
		DomUtil.write(doc);
		System.out.println("----");
		DomWriter.dump(doc);
		System.out.println("----");
		new DomWriter().write(doc);
		System.out.println("----");
		new HtmlDomWriter().write(doc);
	}
}
