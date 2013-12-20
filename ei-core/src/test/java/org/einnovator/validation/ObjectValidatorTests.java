/**
 * 
 */
package org.einnovator.validation;

import static org.junit.Assert.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.einnovator.environment.MapEnvironment;
import org.einnovator.environment.WritableEnvironment;
import org.einnovator.format.FormatUtil;
import org.einnovator.log.Level;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.Projection;
import org.einnovator.meta.Ref;
import org.einnovator.validation.contraints.DecimalMax;
import org.einnovator.validation.contraints.DecimalMin;
import org.einnovator.validation.contraints.Digits;
import org.einnovator.validation.contraints.Future;
import org.einnovator.validation.contraints.Max;
import org.einnovator.validation.contraints.Min;
import org.einnovator.validation.contraints.NotNull;
import org.einnovator.validation.contraints.Null;
import org.einnovator.validation.contraints.Past;
import org.einnovator.validation.contraints.Pattern;
import org.einnovator.validation.contraints.Size;
import org.junit.Before;
import org.junit.Test;


/**
 * A {@code ObjectMapperTests}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ObjectValidatorTests {

	ObjectValidator validator;
	
	@Before
	public void setup() {
		validator = new ObjectValidator();
	}
	
	interface Highlighted {
		void id();
		void text();
	}

	static class A {
		int id = 1;

		@NotNull
		Object invalidNotNull;

		@Null
		Object invalidNull = new Object();
		
		@Min(0)
		int invalidNegative = -1;

		@Max(0)
		int invalidPositive = 1;

		@DecimalMin("0")
		BigDecimal invalidNegativeDecimal = new BigDecimal(-1);

		@DecimalMax("0")
		BigDecimal invalidPositiveDecimal = new BigDecimal(1);

		@Size(min=1)
		String invalidEmptyString = "";

		@Size(max=3)
		String invalidLongString = "1234";

		@Size(min=1)
		String[] invalidEmptyArray = new String[0];

		@Size(max=3)
		String[] invalidLongArray = {"1", "2", "3", "4"};

		@Size(min=1)
		Collection<?> invalidEmptyCollection = new ArrayList<Object>();
	
		@Size(max=3)
		Collection<?> invalidLongCollection = Arrays.asList(new String[]{"1", "2", "3", "4"});

		@Past
		Date invalidNotPast = new Date();

		@Future
		Date invalidNotFuture = new Date();

		@Past
		Calendar invalidCalendarNotPast;

		@Future
		Calendar invalidCalendarNotFuture;

		@Pattern(regexp=".*@.*")
		String invalidEmail = "xy";
		
		@Digits(integer=1, fraction=1)
		BigDecimal invalidDigits = new BigDecimal("11.1");

		@Digits(integer=1, fraction=1)
		BigDecimal invalidDigits2 = new BigDecimal("1.11");

		@Valid
		A a1;

		@Valid(projection=@Projection(includeOnly="invalidNotNull"))
		A a2;

		A a3;
		
		static class ColorValidator implements Validator<Color> {
			@Override
			public boolean validate(Color color, ValidationContext context) {
				if (color.getRed()>0) {
					context.addError(color, "color.toored", "Too Red", color.getRed());
					return false;
				}
				return true;
			}
			
		}
		
		@Valid(validator=@Ref(ColorValidator.class))
		Color invalidColor = new Color(255, 255, 255);
		
		@Valid(projection=@Projection(includeOnly="invalidNotNull"))
		List<A> complexList = new ArrayList<A>();
		
		public A() {
			complexList.addAll(Arrays.asList(new A[]{new A(1), new A(2), new A(3)}));
			invalidCalendarNotFuture = Calendar.getInstance();
			invalidCalendarNotFuture.set(0, 1, 1, 0, 0, 0);
			invalidNotFuture = invalidCalendarNotFuture.getTime();
			invalidCalendarNotPast = Calendar.getInstance();
			invalidCalendarNotPast.set(3000, 1, 1, 0, 0, 0);
			invalidNotPast = invalidCalendarNotPast.getTime();
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
	public void annotationsTest() {
		A a = new A();

		a.a1 = new A(1);
		a.a2 = new A(2);
		a.a3 = new A(3);
		//a.a4 = a;
		System.out.println(a);
		
		LoggerUtil.setLevel(ObjectValidator.class, Level.ALL);
		System.out.println(LoggerUtil.getProperties());
		
		ValidationContext context = new ValidationContext();
		boolean valid = validator.validate(a, context);
		Errors errors = context.getErrors();
		System.out.println(errors);
		for (Error error: errors.getErrorList()) {
			System.out.println(error);
		}
		assertFalse(valid);

		assertNotNull(errors.getErrors("invalidNotNull"));
		assertEquals(1, errors.getErrors("invalidNotNull").size());

		assertNotNull(errors.getErrors("invalidNull"));
		assertEquals(1, errors.getErrors("invalidNull").size());

		assertNotNull(errors.getErrors("invalidNegativeDecimal"));
		assertEquals(1, errors.getErrors("invalidNegativeDecimal").size());
		
		assertNotNull(errors.getErrors("invalidPositiveDecimal"));
		assertEquals(1, errors.getErrors("invalidPositiveDecimal").size());
		
		assertNotNull(errors.getErrors("invalidEmptyString"));
		assertEquals(1, errors.getErrors("invalidEmptyString").size());
		
		assertNotNull(errors.getErrors("invalidLongString"));
		assertEquals(1, errors.getErrors("invalidLongString").size());
		
		assertNotNull(errors.getErrors("invalidEmptyArray"));
		assertEquals(1, errors.getErrors("invalidEmptyArray").size());
		
		assertNotNull(errors.getErrors("invalidLongArray"));
		assertEquals(1, errors.getErrors("invalidLongArray").size());
		
		assertNotNull(errors.getErrors("invalidEmptyCollection"));
		assertEquals(1, errors.getErrors("invalidEmptyCollection").size());
		
		assertNotNull(errors.getErrors("invalidLongCollection"));
		assertEquals(1, errors.getErrors("invalidLongCollection").size());
			
		assertNotNull(errors.getErrors("invalidNotPast"));
		assertEquals(1, errors.getErrors("invalidNotPast").size());

		assertNotNull(errors.getErrors("invalidNotFuture"));
		assertEquals(1, errors.getErrors("invalidNotFuture").size());

		assertNotNull(errors.getErrors("invalidCalendarNotPast"));
		assertEquals(1, errors.getErrors("invalidCalendarNotPast").size());

		assertNotNull(errors.getErrors("invalidCalendarNotFuture"));
		assertEquals(1, errors.getErrors("invalidCalendarNotFuture").size());

		assertNotNull(errors.getErrors("invalidEmail"));
		assertEquals(1, errors.getErrors("invalidEmail").size());

		assertNotNull(errors.getErrors("invalidDigits"));
		assertEquals(1, errors.getErrors("invalidDigits").size());

		assertNotNull(errors.getErrors("invalidDigits2"));
		assertEquals(1, errors.getErrors("invalidDigits2").size());

		assertNotNull(errors.getErrors("a1.invalidNotNull"));
		assertEquals(1, errors.getErrors("a1.invalidNotNull").size());
		
		assertNotNull(errors.getErrors("a1.invalidEmptyString"));
		assertEquals(1, errors.getErrors("a1.invalidEmptyString").size());
		
		assertNotNull(errors.getErrors("a2.invalidNotNull"));
		assertEquals(1, errors.getErrors("a2.invalidNotNull").size());
		
		assertNull(errors.getErrors("a2.invalidEmptyString"));
		assertNull(errors.getErrors("a3.invalidNotNull"));

		assertNotNull(errors.getErrors("invalidColor"));
		assertEquals(1, errors.getErrors("invalidColor").size());

		assertNotNull(errors.getErrors("complexList.invalidPositive"));
		assertEquals(3, errors.getErrors("complexList.invalidPositive").size());

	}

	
	static class A2 {
		int id = 1;

		@NotNull
		Object invalidNotNull;

		@Null
		Object invalidNull = new Object();
		
		@Min(0)
		int invalidNegative = -1;

		@Max(0)
		int invalidPositive = 1;

		@DecimalMin("0")
		BigDecimal invalidNegativeDecimal = new BigDecimal(-1);

		@DecimalMax("0")
		BigDecimal invalidPositiveDecimal = new BigDecimal(1);

		@Size(min=1)
		String invalidEmptyString = "";

		@Size(max=3)
		String invalidLongString = "1234";

		@Size(min=1)
		String[] invalidEmptyArray = new String[0];

		@Size(max=3)
		String[] invalidLongArray = {"1", "2", "3", "4"};

		@Size(min=1)
		Collection<?> invalidEmptyCollection = new ArrayList<Object>();
	
		@Size(max=3)
		Collection<?> invalidLongCollection = Arrays.asList(new String[]{"1", "2", "3", "4"});

		@Past
		Date invalidNotPast = new Date();

		@Future
		Date invalidNotFuture = new Date();

		@Past
		Calendar invalidCalendarNotPast;

		@Future
		Calendar invalidCalendarNotFuture;

		@Pattern(regexp=".*@.*")
		String invalidEmail = "xy";
		
		@Digits(integer=1, fraction=1)
		BigDecimal invalidDigits = new BigDecimal("11.1");

		@Digits(integer=1, fraction=1)
		BigDecimal invalidDigits2 = new BigDecimal("1.11");

		@Valid
		A2 a1;

		@Valid(projection=@Projection(includeOnly="invalidNotNull"))
		A2 a2;

		A2 a3;
		
		static class ColorValidator implements Validator<Color> {
			@Override
			public boolean validate(Color color, ValidationContext context) {
				if (color.getRed()>0) {
					context.addError(color, "color.toored", "Too Red", color.getRed());
					return false;
				}
				return true;
			}
			
		}
		
		@Valid(validator=@Ref(ColorValidator.class))
		Color invalidColor = new Color(255, 255, 255);
		
		@Valid(projection=@Projection(includeOnly="invalidNotNull"))
		List<A2> complexList = new ArrayList<A2>();
		
		public A2() {
			complexList.addAll(Arrays.asList(new A2[]{new A2(1), new A2(2), new A2(3)}));
			invalidCalendarNotFuture = Calendar.getInstance();
			invalidCalendarNotFuture.set(0, 1, 1, 0, 0, 0);
			invalidNotFuture = invalidCalendarNotFuture.getTime();
			invalidCalendarNotPast = Calendar.getInstance();
			invalidCalendarNotPast.set(3000, 1, 1, 0, 0, 0);
			invalidNotPast = invalidCalendarNotPast.getTime();
		}
		
		public A2(int id) {
			this.id = id;
			
		}
		
		@Override
		public String toString() {
			return FormatUtil.toString(this);
		}
	}

	@Test
	public void propertiesTest() {
		A2 a = new A2();

		a.a1 = new A2(1);
		a.a2 = new A2(2);
		a.a3 = new A2(3);
		//a.a4 = a;
		System.out.println(a);
		WritableEnvironment env = new MapEnvironment();
		env.setValue(A2.class.getName() + ".invalidNotNull.NotNull", true);
		env.setValue(A2.class.getName() + ".invalidNull.Null", true);

	}

}
