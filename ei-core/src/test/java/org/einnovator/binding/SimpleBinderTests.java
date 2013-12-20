/**
 * 
 */
package org.einnovator.binding;

import static org.junit.Assert.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.einnovator.convert.ConversionService;
import org.einnovator.convert.ConversionServiceImpl;
import org.einnovator.convert.DefaultConversionService;
import org.einnovator.format.datetime.DateTimeFormatter;
import org.einnovator.log.Level;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaOperations;
import org.einnovator.validation.Errors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * A {@code SimpleBinderTests}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class SimpleBinderTests {

	ConversionService conversionService;
	
	Binder binder;
	
	@BeforeClass
	public static void setupS() {
		LoggerUtil.setLevel(SimpleBinder.class, Level.ALL);
	}
	
	
	@Before
	public void setup() {
		conversionService = new DefaultConversionService();
		((DateTimeFormatter)((ConversionServiceImpl)conversionService).getFormatter(Date.class)).setPattern("dd-MM-yyyy");
		binder = new SimpleBinder(conversionService);
	}

	@Test
	public void binderTests() {
		binderTests(binder);
	}


	public void binderTests(Binder binder) {
		Account account = new Account();

		BindingContext context = new BindingContext()
			.add("id", "1")
			.add("name", "A")
			.add("password", "apass")
			.add("email", "a@x.org")
			.add("otherEmails", "a@y.org,a@z.org")
			.add("birthday", "01-01-2001")
			.add("balance", "100.0")
			.add("height", "190.50")
			.add("type", "1")
			.add("newsletter", "true")
			.add("sex", "MALE")
			.add("note", "about!")
			.add("favoriteColor", "#ff0000");
		
		assertEquals("1", context.getParameter("id"));
		assertEquals("A", context.getParameter("name"));		

		MetaClass<?> metaClass = MetaOperations.getMetaClass(Account.class);
		System.err.println(Arrays.toString(metaClass.getAllProperties()));

		Errors errors = binder.bind(account, context);
	
		assertEquals(new Long(1L), account.getId());
		assertEquals("A", account.getName());
		assertEquals("apass", account.getPassword());
		assertEquals("a@x.org", account.getEmail());
		
		assertArrayEquals(new String[]{"a@y.org","a@z.org",}, account.getOtherEmails());

		@SuppressWarnings("deprecation")
		Date date = new Date(2001-1900, 0, 1, 0, 0, 0);
		assertEquals(date, account.getBirthday());
		assertEquals(new BigDecimal(100.0), account.getBalance());
		//assertEquals(190.50f, account.getHeight(), 0.0001);
		assertEquals(1, account.getType());
		assertEquals(Boolean.TRUE, account.getNewsletter());
		assertEquals(Sex.MALE, account.getSex());
		assertEquals("about!", account.getNote());
		assertEquals(new Color(255, 0, 0), account.getFavoriteColor());
		assertNull(account.getAddress());
		
		assertTrue(errors.ok());

	}

	@Test
	public void binderTests2() {
		Account account = new Account();
		BindingContext context = new BindingContext()
			.add("id", "1")
			.add("name", "A")
			.add("password", "apass")
			.add("email", "a@x.org")
			.add("otherEmails[0]", "a@y.org")
			.add("otherEmails[1]", "a@z.org")
			.add("birthday", "01-01-2001")
			.add("balance", "100.0")
			.add("height", "190.50")
			.add("type", "1")
			.add("newsletter", "true")
			.add("sex", "MALE")
			.add("note", "about!")
			.add("favoriteColor", "#ff0000")
			.add("address.country", "WW")
			.add("titles[0]", "Ph.D.")
			.add("titles[1]", "M.Sc.")
			.add("titles[2]", "Eng./B.Sc.")
			.add("otherTitles[0]", "Ph.D.")
			.add("otherTitles[1]", "M.Sc.")
			.add("otherTitles[2]", "Eng./B.Sc.")
			.add("otherAddresses[0].country", "WW0")
			.add("otherAddresses[0].city", "TTown0")
			.add("otherAddresses[1].country", "WW1")
			.add("otherAddresses[2].country", "WW2");

		assertEquals("1", context.getParameter("id"));
		assertEquals("A", context.getParameter("name"));		
		assertEquals("Ph.D.", context.getParameter("titles[0]"));			
		assertEquals("Ph.D.", context.getParameter("otherTitles[0]"));		
		assertEquals("WW0", context.getParameter("otherAddresses[0].country"));	
		
		MetaClass<?> metaClass = MetaOperations.getMetaClass(Account.class);
		System.err.println(Arrays.toString(metaClass.getDeclaredProperties()));

		Errors errors = binder.bind(account, context);
	
		assertEquals(new Long(1L), account.getId());
		assertEquals("A", account.getName());
		assertEquals("apass", account.getPassword());
		assertEquals("a@x.org", account.getEmail());
		assertArrayEquals(new String[]{"a@y.org","a@z.org",}, account.getOtherEmails());
		
		@SuppressWarnings("deprecation")
		Date date = new Date(2001-1900, 0, 1, 0, 0, 0);
		assertEquals(date, account.getBirthday());
		assertEquals(new BigDecimal(100.0), account.getBalance());
		assertEquals(190.50f, account.getHeight(), 0.0001);
		assertEquals(1, account.getType());
		assertEquals(Boolean.TRUE, account.getNewsletter());
		assertEquals(Sex.MALE, account.getSex());
		assertEquals("about!", account.getNote());
		assertEquals(new Color(255, 0, 0), account.getFavoriteColor());
		assertNotNull(account.getAddress());
		assertEquals("WW", account.getAddress().getCountry());
	
		assertNotNull(account.getOtherTitles());
		assertEquals(3, account.getOtherTitles().size());
		assertEquals(String.class, account.getOtherTitles().get(0).getClass());
		assertEquals(String.class, account.getOtherTitles().get(1).getClass());
		assertEquals(String.class, account.getOtherTitles().get(2).getClass());
		assertEquals("Ph.D.", account.getOtherTitles().get(0));
		assertEquals("M.Sc.", account.getOtherTitles().get(1));
		assertEquals("Eng./B.Sc.", account.getOtherTitles().get(2));
		
		assertNotNull(account.getOtherAddresses());
		assertEquals(3, account.getOtherAddresses().size());
		assertEquals(Address.class, account.getOtherAddresses().get(0).getClass());
		assertEquals(Address.class, account.getOtherAddresses().get(1).getClass());
		assertEquals(Address.class, account.getOtherAddresses().get(2).getClass());
		assertEquals("WW0", account.getOtherAddresses().get(0).getCountry());
		assertEquals("TTown0", account.getOtherAddresses().get(0).getCity());
		assertEquals("WW1", account.getOtherAddresses().get(1).getCountry());
		assertEquals("WW2", account.getOtherAddresses().get(2).getCountry());
		assertTrue(errors.ok());
	}

}
