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
import org.einnovator.validation.contraints.Null;

/**
 * A {@code NullValidatorFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class NullValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies the target value must be <code>null</code>.
	 * 
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class NullDescriptor {
		
		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message;
		
		/**
		 * Create instance of {@code NullDescriptor}.
		 *
		 */
		public NullDescriptor() {
		}
		
		/**
		 * Create instance of {@code NullDescriptor}.
		 *
		 * @param _null
		 */
		public NullDescriptor(Null _null) {
			message = new MessageDescriptor().assign(_null.message());
		}
	}

	static class NullValidator extends ValidatorSupport<Object> {
		private NullDescriptor _null;
		
		NullValidator(MetaDescriptor descriptor, NullDescriptor _null) {
			super(descriptor);
			this._null = _null;
		}
		
		public boolean validate(Object obj, ValidationContext context) {
			boolean valid = true;
			if (obj!=null) {
				addErrors(obj, _null.message, context);
				valid = false;
			}
			return valid;
		}
	}
		
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code NullValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public NullValidatorFactory(MetaDataSource metaDataSource) {
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
		NullDescriptor _null = getValidationDescriptor(descriptor, NullDescriptor.class);
		if (_null!=null) {
			return new NullValidator(descriptor, _null);			
		}
		return null;
	}
	
	/**
	 * A {@code NullConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class NullConstraintSource implements SimpleMetaDataSource<NullDescriptor> {

		@Override
		public NullDescriptor getMetaData(MetaDescriptor descriptor) {
			Null _null = descriptor.getAnnotation(Null.class);
			if (_null!=null) {
				return new NullDescriptor(_null);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentNullConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentNullConstraintSource implements SimpleMetaDataSource<NullDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public NullDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Null.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, NullDescriptor.class, environment);
		}
	}	

}
