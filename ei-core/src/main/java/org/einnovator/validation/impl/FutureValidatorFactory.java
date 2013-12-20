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
import org.einnovator.validation.contraints.Future;


/**
 * A {@code ValidatorFactory} for a {@code Validator} based on annotation {@code Future}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see Future
 * @see Validator
 */
public class FutureValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies the target value must be a date in the future.
	 * 
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class FutureDescriptor {
		
		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message;
		
		/**
		 * Create instance of {@code FutureDescriptor}.
		 *
		 */
		public FutureDescriptor() {
		}
		
		/**
		 * Create instance of {@code FutureDescriptor}.
		 *
		 * @param future
		 */
		public FutureDescriptor(Future future) {
			message = new MessageDescriptor().assign(future.message());
		}
	}

	static class DateFutureValidator extends ValidatorSupport<Date> {
		private FutureDescriptor future;

		public DateFutureValidator(MetaDescriptor descriptor, FutureDescriptor future) {
			super(descriptor);
			this.future = future;
		}
		
		public boolean validate(Date date, ValidationContext context) {
			boolean valid = true;
			if (date!=null && date.before(new Date())) {
				addErrors(date, future.message, context, future);
				valid = false;
			}
			return valid;
		}
	}
	
	static class CalendarFutureValidator extends ValidatorSupport<Calendar> {
		private FutureDescriptor future;
	
		public CalendarFutureValidator(MetaDescriptor descriptor, FutureDescriptor future) {
			super(descriptor);
			this.future = future;
		}
		
		public boolean validate(Calendar calendar, ValidationContext context) {
			boolean valid = true;
			if (calendar!=null && calendar.getTime().before(new Date())) {
				addErrors(calendar, future.message, context, future);
				valid = false;
			}
			return valid;
		}
	}

	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code FutureValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public FutureValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	public Validator<?> createValidator(MetaDescriptor descriptor) {
		FutureDescriptor future = getValidationDescriptor(descriptor, FutureDescriptor.class);
		if (future!=null) {
			if (Date.class.isAssignableFrom(descriptor.getType())) {
				return new DateFutureValidator(descriptor, future);			
			}
			if (Calendar.class.isAssignableFrom(descriptor.getType())) {
				return new CalendarFutureValidator(descriptor, future);			
			}				
		}
		return null;
	}
	
	/**
	 * A {@code FutureConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class FutureConstraintSource implements SimpleMetaDataSource<FutureDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public FutureDescriptor getMetaData(MetaDescriptor descriptor) {
			Future future = descriptor.getAnnotation(Future.class);
			if (future!=null) {
				return new FutureDescriptor(future);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentFutureConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentFutureConstraintSource implements SimpleMetaDataSource<FutureDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public FutureDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Future.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, FutureDescriptor.class, environment);
		}
	}	

}
