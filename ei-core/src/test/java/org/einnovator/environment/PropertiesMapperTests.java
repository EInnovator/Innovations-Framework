/**
 * 
 */
package org.einnovator.environment;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.einnovator.convert.DefaultConversionService;
import org.einnovator.environment.Prop;
import org.einnovator.environment.PropertiesMapper;
import org.einnovator.environment.PropertiesMapperUtil;
import org.einnovator.format.FormatUtil;
import org.einnovator.format.Optional;
import org.einnovator.log.Level;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.Projection;
import org.einnovator.util.CollectionUtil;
import org.junit.Before;
import org.junit.Test;


/**
 * A {@code PropertiesMapperTests}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class PropertiesMapperTests {

	PropertiesMapper mapper;
	
	@Before
	public void setup() {
		mapper = new PropertiesMapper();
		mapper.getOptions().setConversionService(new DefaultConversionService());
		PropertiesMapperUtil.setMapper(mapper);
		LoggerUtil.setLevel(PropertiesMapper.class, Level.ALL);
	}
	

	static class A {
		int id;

		String text;

		@Prop(false)
		A ignoredObject;

		@Prop(ignoreZero=true)
		int ignoredInt;
		
		@Prop(projection=@Projection(includeOnly="id", includeOnlyAnnotated=Optional.class))
		A a2;

		@Prop(optional=true, projection=@Projection(includeOnly="id", includeOnlyAnnotated=Optional.class))
		A a3;

		@Prop(key="niceName")
		String bizarreName;

		@Prop(ignoreEmpty=true)
		String ignoredEmptyText = "";

		@Prop(ignoreEmpty=true)
		String[] ignoreEmptyArray = {};

		@Prop(false)
		String[] array;

		@Prop(false)
		List<String> list = new ArrayList<String>();

		@Prop(false)
		List<String> emptyList = new ArrayList<String>();

		Date date;

		Color color;
		
		@Prop(value=false, ignoreEmpty=true, projection=@Projection(includeOnly={"id", "text"}))
		List<A> complexList = new ArrayList<A>();
		
		//byte[] byteArray = {1, 2, 3};
		
		public A() {
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
	public void test1() throws IllegalArgumentException, IllegalAccessException {
		A a = new A();
		a.id = 1;
		a.text = "text";
		a.bizarreName = "nice";
		a.ignoredObject = new A();
		a.ignoredInt = 0;
		a.date = new Date();
		a.color = new Color(255, 255, 255);
		a.a2 = new A(2);
		a.a3 = new A(3);
		a.array =  new String[]{"a", "b", "c"};
		a.list.addAll(Arrays.asList("A", "B", "C"));
		a.complexList.addAll(Arrays.asList(new A[]{new A(1), new A(2), new A(3)}));
		System.out.println(a);
		Map<String, Object> map = PropertiesMapperUtil.toMap(a);
		System.out.println(map);
		A a2  = PropertiesMapperUtil.getMapper().read(null, A.class, new MapEnvironment(map), null);
		System.out.println(a2);
		assertEquals(a.id, a2.id);
		assertEquals(a.text, a2.text);
		assertEquals(a.bizarreName, a2.bizarreName);		
		assertEquals(a.date, a2.date);
		assertEquals(a.color, a2.color);		
		assertEquals(a.bizarreName, a2.bizarreName);		
	}

	@Prop(key="x")
	static class A2 {
		int id = 1;

		String text = "text";

		A2 a2;
		
		public A2() {
		}
		
		public A2(int id) {
			this.id = id;
		}
	}

	@Test
	public void test2() {
		A2 a = new A2();
		a.a2 = new A2(2);
		//System.out.println(PropertiesMapperUtil.toString(a));
		//System.out.println(PropertiesOptions.newInstance());
		System.out.println(a);
		System.out.println(PropertiesMapperUtil.toMap(a));
		System.out.println(CollectionUtil.toString(PropertiesMapperUtil.toMap(a)));
		//mapper.toMap(a));
	}


}
