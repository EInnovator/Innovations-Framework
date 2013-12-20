/**
 * 
 */
package org.einnovator.validation.impl;

import java.lang.annotation.ElementType;

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
import org.einnovator.validation.contraints.NotNull;

/**
 * A {@code NotNullValidatorFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class NotNullValidatorFactory extends ValidatorFactorySupport {

	
	/**
	 * Descriptor that specifies the target value must not be <code>null</code>.
	 * 
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class NotNullDescriptor {
		
		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message;
		
		/**
		 * Create instance of {@code NotNullDescriptor}.
		 *
		 */
		public NotNullDescriptor() {
		}
		
		/**
		 * Create instance of {@code NotNullDescriptor}.
		 *
		 * @param notNull
		 */
		public NotNullDescriptor(NotNull notNull) {
			message = new MessageDescriptor().assign(notNull.message());
		}
	}

	static class NotNullValidator extends ValidatorSupport<Object> {
		private NotNullDescriptor notNull;
		
		NotNullValidator(MetaDescriptor descriptor, NotNullDescriptor notNull) {
			super(descriptor);
			this.notNull = notNull; 
		}
		
		public boolean validate(Object obj, ValidationContext context) {
			boolean valid = true;
			if (obj==null) {
				addErrors(null, notNull.message, context);
				valid = false;
			}
			return valid;
		}
	}
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code NotNullValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public NotNullValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	/**
	 * @see org.einnovator.validation.ValidatorFactory#createValidator(org.einnovator.meta.MetaDescriptor)
	 */
	@Override
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		NotNullDescriptor notNull = getValidationDescriptor(descriptor, NotNullDescriptor.class);
		if (notNull!=null) {			
			return new NotNullValidator(descriptor, notNull);
		}
		return null;
	}
	
	
	/**
	 * A {@code NotNullConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class NotNullConstraintSource implements SimpleMetaDataSource<NotNullDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public NotNullDescriptor getMetaData(MetaDescriptor descriptor) {
			NotNull notNull = descriptor.getAnnotation(NotNull.class);
			if (notNull!=null) {
				return new NotNullDescriptor(notNull);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentNotNullConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentNotNullConstraintSource implements SimpleMetaDataSource<NotNullDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public NotNullDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, NotNull.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, NotNullDescriptor.class, environment);
		}
	}	

}
