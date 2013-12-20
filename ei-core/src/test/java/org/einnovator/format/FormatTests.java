/**
 * 
 */
package org.einnovator.format;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.einnovator.convert.DefaultConversionService;
import org.einnovator.format.Format;
import org.einnovator.format.FormatUtil;
import org.einnovator.i18n.MessageResolverHolder;
import org.einnovator.i18n.ResourceBundleMessageResolver;
import org.einnovator.log.Level;
import org.einnovator.meta.Projection;
import org.einnovator.meta.Ref;
import org.einnovator.text.CompositeTextTransform;
import org.einnovator.text.TextTransform;
import org.einnovator.text.transforms.CamelCaseToWordsTextTransform;
import org.einnovator.text.transforms.CapsTextTransform;
import org.einnovator.text.transforms.EllipsisTransform;
import org.junit.Before;
import org.junit.Test;


/**
 * A {@code ObjectMapperTests}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FormatTests {

	ObjectPrinter printer;
	
	@Before
	public void setup() {
		printer = new ObjectPrinter();
		printer.getOptions().setConversionService(new DefaultConversionService());
		FormatUtil.setPrinter(printer);
	}
	
	interface Highlighted {
		void id();
		void text();
	}
	static class A {
		int id = 1;

		@Format(ignoreDefault=true)
		int hiddenInt = 0;
		
		String text = "text";
		
		@Format(ignore=true)
		A hiddenObject;

		@Format(false)
		String hiddenText = "pass123";

		@Format(projection=@Projection(includeOnly="id", includeOnlyAnnotated=Optional.class))
		A a2;

		@Format(optional=true, projection=@Projection(includeOnly="id", includeOnlyAnnotated=Optional.class))
		A a3;

		@Format(recurse=true)
		A a4;

		@Format(cycleProjection=@Projection(includeOnly="id"))
		A a5;
		
		
		@Format(projection=@Projection(includeOnlyMatching=Highlighted.class))
		A a6;
		
		@Format(ignoreNull=true)
		Object nullValue;

		Object hiddenNullValue;

		@Format(ignoreEmpty=true)
		String hiddenNullText;		

		String emptyText = "";

		@Format(ignoreEmpty=true)
		String hiddenEmptyText = "";
		
		String[] array = {"a", "b", "c"};

		String[] emptyArray = {};

		@Format(ignoreEmpty=true)
		String[] hiddenEmptyArray = {};

		List<String> list = new ArrayList<String>();

		List<String> emptyList = new ArrayList<String>();

		@Format(ignoreEmpty=true)
		List<String> hiddenEmptyList = new ArrayList<String>();

		@Format(label="niceName")
		String bizarreName = "nice";

		Date date = new Date();

		Color color = new Color(255, 255, 255);
		
		@Format(optional=true)
		private String optional = "OPTIONAL";

		@Optional
		private String anotherOptional = "ANOTHER_OPTIONAL";

		@Format(ignoreEmpty=true, projection=@Projection(includeOnly={"id", "text"}))
		List<A> complexList = new ArrayList<A>();
		
		//byte[] byteArray = {1, 2, 3};
		
		static class DescriptionTransform extends EllipsisTransform {
			public DescriptionTransform() {
				super(14);
			}
		}
		
		@Format(transform=@Ref(DescriptionTransform.class))
		String description = "A very long description of something!";
		
		@Format(labelTransform=@Ref(CamelCaseToWordsTextTransform.class))
		String fullName;
		
		public A() {
			list.addAll(Arrays.asList("A", "B", "C"));
			complexList.addAll(Arrays.asList(new A[]{new A(1), new A(2), new A(3)}));
		}
		
		public A(int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return FormatUtil.toString(this);
		}
	}
	
	
	@Test
	public void test1() {
		A a = new A();
		a.fullName = "Alice Wonder";
		a.hiddenObject = new A();
		a.a2 = new A(2);
		a.a3 = new A(3);
		a.a4 = a;
		a.a5 = a;
		a.a6 = new A(6);
		//System.out.println(PropertiesMapperUtil.toString(a));
		//System.out.println(PropertiesOptions.newInstance());
		System.out.println(a);
		//printer.print(a));
	}

	@Test
	public void testPathProjection() {
		A a = new A();
		a.hiddenObject = new A();
		a.a2 = new A(2);
		a.a3 = new A(3);
		a.a4 = new A(4);
		a.a4.color = new Color(255,255,255);
		System.out.println(FormatUtil.toString(a, new String[]{"id", "text", "a2", "a3", "a4.id", "a4.text"}));
		//printer.print(a));
	}

	
	static class A0 {
		int id = 1;

		String text="text";
		
		Object value=123;
		
		@Format(projection=@Projection(includeOnly="id", includeOnlyAnnotated=Optional.class))
		A0 a2;
		
		@Format(optional=true, projection=@Projection(includeOnly="id", includeOnlyAnnotated=Optional.class))
		A0 a3;
		
		@Format(projection=@Projection(includeOnlyMatching=Highlighted.class))
		A0 a6;
		
		public A0() {
		}
		
		public A0(int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return FormatUtil.toString(this);
		}
	}
	
	
	@Test
	public void test0() {
		A0 a = new A0();
		a.a2 = new A0(2);
		a.a3 = new A0(3);
		a.a6 = new A0(6);
		a.a6.text = "123";
		//System.out.println(PropertiesMapperUtil.toString(a));
		//System.out.println(PropertiesOptions.newInstance());
		System.out.println(a);
		//printer.print(a));
	}
	

	static class TestLabelTransform extends CompositeTextTransform {
		public TestLabelTransform() {
			super(new CamelCaseToWordsTextTransform(), new CapsTextTransform(true));
		}
	}
	
	@Format(labelTransform=@Ref(TestLabelTransform.class))
	@TypeFormat(beginMarker="<", endMarker=">", fieldSeparator=" ")
	static class A2 {
		int id = 1;

		@Format(ignoreDefault=true)
		int hiddenInt = 0;
		
		String text = "text";
		
		@Format(ignore=true)
		A2 hiddenObject;

		@Format(false)
		String hiddenText = "pass123";

		@Format(projection=@Projection(includeOnly="id"))
		A2 a2;

		@Format(ignoreNull=true)
		Object nullValue;

		Object hiddenNullValue;

		@Format(ignoreEmpty=true)
		String hiddenNullText;		

		String emptyText = "";

		@Format(ignoreEmpty=true)
		String hiddenEmptyText = "";
		
		String[] array = {"a", "b", "c"};

		String[] emptyArray = {};

		@Format(ignoreEmpty=true)
		String[] hiddenEmptyArray = {};

		List<String> list = new ArrayList<String>();

		List<String> emptyList = new ArrayList<String>();

		@Format(ignoreEmpty=true)
		List<String> hiddenEmptyList = new ArrayList<String>();

		@Format(label="niceName")
		String bizarreName = "nice";

		Date date = new Date();

		Color color = new Color(255, 255, 255);

		@Format(optional=true)
		private String optional = "OPTIONAL";

		public A2() {
			list.addAll(Arrays.asList("A", "B", "C"));
		}
		
		public A2(int id) {
			this.id = id;
		}
	}
	
	@Test
	public void test2() {
		A2 a = new A2();
		a.hiddenObject = new A2();
		a.a2 = new A2(2);
		System.out.println(printer.print(a, (Locale)null));
	}

	@Test
	public void i18nTest() {
		MessageResolverHolder.setInstance(new ResourceBundleMessageResolver("org/einnovator/i18n/messages"));
		A a = new A();
		System.out.println(printer.print(a, (Locale)null));
	}
	

	
	@TypeFormat(beginMarker="{", endMarker="}", valueSeparator=":", fieldSeparator=", ")
	@Format(quoteChar='\'')
	static class A3 {
		int id = 1;

		@Format(ignoreDefault=true)
		int hiddenInt = 0;
		
		String text = "text";
		
		@Format(ignore=true)
		A3 hiddenObject;

		@Format(false)
		String hiddenText = "pass123";

		@Format(projection=@Projection(includeOnly="id"))
		A3 a2;

		@Format(ignoreNull=true)
		Object nullValue;

		Object hiddenNullValue;

		@Format(ignoreEmpty=true)
		String hiddenNullText;		

		String emptyText = "";

		@Format(ignoreEmpty=true)
		String hiddenEmptyText = "";
		
		String[] array = {"a", "b", "c"};

		String[] emptyArray = {};

		@Format(ignoreEmpty=true)
		String[] hiddenEmptyArray = {};

		List<String> list = new ArrayList<String>();

		List<String> emptyList = new ArrayList<String>();

		@Format(ignoreEmpty=true)
		List<String> hiddenEmptyList = new ArrayList<String>();

		@Format(label="niceName")
		String bizarreName = "nice";

		Date date = new Date();

		Color color = new Color(255, 255, 255);

		@Format(optional=true)
		private String optional = "OPTIONAL";

		public A3() {
			list.addAll(Arrays.asList("A", "B", "C"));
		}
		
		public A3(int id) {
			this.id = id;
		}
	}

	static class TextPrinter implements Printer<String> {

		TextTransform textTransform = new CapsTextTransform(true);
		
		@Override
		public String print(String value, Locale locale) {
			return "\"" + textTransform.transform(value) + "\"";
		}
	}

	static class A4 {
		int id = 1;

		@Format(ignoreDefault=true)
		int hiddenInt = 0;
		
		@Format(printer=@Ref(TextPrinter.class))
		String text = "text";
		
		@Format(ignore=true)
		A4 hiddenObject;

		@Format(false)
		String hiddenText = "pass123";

		@Format(projection=@Projection(includeOnly="id"))
		A4 a2;

		@Format(ignoreNull=true)
		Object nullValue;

		Object hiddenNullValue;

		@Format(ignoreEmpty=true)
		String hiddenNullText;		

		String emptyText = "";

		@Format(ignoreEmpty=true)
		String hiddenEmptyText = "";
		
		String[] array = {"a", "b", "c"};

		String[] emptyArray = {};

		@Format(ignoreEmpty=true)
		String[] hiddenEmptyArray = {};

		List<String> list = new ArrayList<String>();

		List<String> emptyList = new ArrayList<String>();

		@Format(ignoreEmpty=true)
		List<String> hiddenEmptyList = new ArrayList<String>();

		@Format(label="niceName")
		String bizarreName = "nice";

		Date date = new Date();

		Color color = new Color(255, 255, 255);

		@Format(optional=true)
		private String optional = "OPTIONAL";

		public A4() {
			list.addAll(Arrays.asList("A", "B", "C"));
		}
		
		public A4(int id) {
			this.id = id;
		}
	}


	
	@Test
	public void test3() {
		A3 a = new A3();
		a.hiddenObject = new A3();
		a.a2 = new A3(2);
		System.out.println(printer.print(a, (Locale)null));
	}
	
	@Test
	public void test4() {
		A4 a = new A4();
		a.hiddenObject = new A4();
		a.a2 = new A4(2);
		System.out.println(printer.print(a, (Locale)null));
	}
	
	@Test
	public void testOptional() {
		A a = new A();
		a.hiddenObject = new A();
		a.a2 = new A(2);
		System.out.println(printer.print(a, (Locale)null, false, "optional"));
	}
	
	@Format(level=Level.INFO)
	static class A5 {
		int id = 1;

		@Format(level=Level.DEBUG)
		String text = "text";

		public A5() {
		}
		
		public A5(int id) {
			this.id = id;
		}
	}

	@Test
	public void testLoggerLevels() {
		A5 a = new A5();
		System.out.println(printer.print(a, (Locale)null));
		System.out.println(printer.print(a, new FormatContext().level(Level.INFO)));
		System.out.println(printer.print(a, new FormatContext().level(Level.DEBUG)));
	}

}
