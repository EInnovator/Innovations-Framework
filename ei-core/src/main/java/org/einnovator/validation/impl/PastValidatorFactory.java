/**
 * 
 */
package org.einnovator.validation.impl;

import java.lang.annotation.ElementType;
import java.util.Calendar;
import java.util.Date;

import org.einnovator.environment.Environment;
import org.einnovator.environment.EnvironmentHolder;
import org.einnovator.environment.PropertiesMapperUtil;
import org.einnovator.i18n.MessageDescriptor;
import org.einnovator.meta.MetaDataSource;
import org.einnovator.meta.MetaDescriptor;
import org.einnovator.meta.SimpleMetaDataSource;
import org.einnovator.validation.ValidationContext;
import org.einnovator.validation.Validator;
import org.einnovator.validation.ValidatorSupport;
import org.einnovator.validation.contraints.Past;

/**
 * A {@code ValidatorFactory} for {@code Validator} based on annotation {@code Past}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see Past
 * @see Validator
 */
public class PastValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies the target value must be a date in the past.
	 * 
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class PastDescriptor {
		
		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message;
		
		/**
		 * Create instance of {@code PastDescriptor}.
		 *
		 */
		public PastDescriptor() {
		}
		
		/**
		 * Create instance of {@code PastDescriptor}.
		 *
		 * @param past
		 */
		public PastDescriptor(Past past) {
			message = new MessageDescriptor().assign(past.message());
		}
	}

	static class DatePastValidator extends ValidatorSupport<Date> {
		private PastDescriptor past;

		public DatePastValidator(MetaDescriptor descriptor, PastDescriptor past) {
			super(descriptor);
			this.past = past;
		}
		
		public boolean validate(Date date, ValidationContext context) {
			boolean valid = true;
			if (date!=null && date.after(new Date())) {
				addErrors(date, past.message, context, past);
				valid = false;
			}
			return valid;
		}
	}
	
	static class CalendarPastValidator extends ValidatorSupport<Calendar> {
		private PastDescriptor past;

		public CalendarPastValidator(MetaDescriptor descriptor, PastDescriptor past) {
			super(descriptor);
			this.past = past;
		}
		
		public boolean validate(Calendar calendar, ValidationContext context) {
			boolean valid = true;
			if (calendar!=null && calendar.getTime().after(new Date())) {
				addErrors(calendar, past.message, context, past);
				valid = false;
			}
			return valid;
		}
	}
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PastValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public PastValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	/**
	 * @see org.einnovator.validation.ValidatorFactory#createValidator(org.einnovator.meta.MetaDescriptor)
	 */
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		PastDescriptor past = getValidationDescriptor(descriptor, PastDescriptor.class);
		if (past!=null) {
			if (Date.class.isAssignableFrom(descriptor.getType())) {
				return new DatePastValidator(descriptor, past);			
			}	
			if (Calendar.class.isAssignableFrom(descriptor.getType())) {
				return new CalendarPastValidator(descriptor, past);			
			}			
		}
		return null;
	}
	
	/**
	 * A {@code PastConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class PastConstraintSource implements SimpleMetaDataSource<PastDescriptor> {

		@Override
		public PastDescriptor getMetaData(MetaDescriptor descriptor) {
			Past past = descriptor.getAnnotation(Past.class);
			if (past!=null) {
				return new PastDescriptor(past);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentPastConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentPastConstraintSource implements SimpleMetaDataSource<PastDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public PastDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Past.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, PastDescriptor.class, environment);
		}
	}	

}
