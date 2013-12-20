/**
 * 
 */
package org.einnovator.validation.impl;

import java.lang.annotation.ElementType;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

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
import org.einnovator.validation.contraints.Size;

/**
 * A {@code SizeValidatorFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class SizeValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies specifies a minimum and/or maximum value or length constraints.
	 * 
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class SizeDescriptor {
		
		/**
		 * Element size/length must be higher or equal to. 
		 */
		private int min;

		/**
		 * Element size/length must be lower or equal to. 
		 */
		private int max;

		/**
		 * @return the message to produce or render.
		 */
		private MessageDescriptor message;
		
		/**
		 * Create instance of {@code SizeDescriptor}.
		 *
		 */
		public SizeDescriptor() {
		}
		
		/**
		 * Create instance of {@code SizeDescriptor}.
		 *
		 * @param size
		 */
		public SizeDescriptor(Size size) {
			this.min = size.min();
			this.max = size.max();
			message = new MessageDescriptor().assign(size.message());
		}
	}


	static abstract class SizeValidatorBase<T> extends ValidatorSupport<T> {
		private SizeDescriptor size;

		public SizeValidatorBase(MetaDescriptor descriptor, SizeDescriptor size) {
			super(descriptor);
			this.size = size;
		}
		
		public boolean validate(T s, int n, ValidationContext context) {
			boolean valid = true;
			if (s!=null) {
				if (size.min!=Integer.MIN_VALUE && n<size.min) {
					addErrors(s, size.message, context, size.min, size.max);
					valid = false;
				}
				if (size.max!=Integer.MAX_VALUE && n>size.max) {
					addErrors(s, size.message, context, size.min, size.max);
					valid = false;
				}				
			}
			return valid;
		}
	}
	
	static class CharSequenceSizeValidator extends SizeValidatorBase<CharSequence> {
		public CharSequenceSizeValidator(MetaDescriptor descriptor, SizeDescriptor size) {
			super(descriptor, size);
		}
		
		@Override
		public boolean validate(CharSequence s, ValidationContext context) {
			return validate(s, s.length(), context);
		}
	}

	static class ArraySizeValidator extends SizeValidatorBase<Object> {
		public ArraySizeValidator(MetaDescriptor descriptor, SizeDescriptor size) {
			super(descriptor, size);
		}
		
		@Override
		public boolean validate(Object a, ValidationContext context) {
			return validate(a, Array.getLength(a), context);
		}
	}

	static class CollectionSizeValidator extends SizeValidatorBase<Collection<?>> {
		public CollectionSizeValidator(MetaDescriptor descriptor, SizeDescriptor size) {
			super(descriptor, size);
		}
		
		@Override
		public boolean validate(Collection<?> collection, ValidationContext context) {
			return validate(collection, collection.size(), context);
		}
	}
	
	static class MapSizeValidator extends SizeValidatorBase<Map<?,?>> {
		public MapSizeValidator(MetaDescriptor descriptor, SizeDescriptor size) {
			super(descriptor, size);
		}
		
		@Override
		public boolean validate(Map<?,?> map, ValidationContext context) {
			return validate(map, map.size(), context);
		}
	}

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code SizeValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public SizeValidatorFactory(MetaDataSource metaDataSource) {
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
		SizeDescriptor size = getValidationDescriptor(descriptor, SizeDescriptor.class);
		if (size!=null) {
			if (CharSequence.class.isAssignableFrom(descriptor.getType())) {
				return new CharSequenceSizeValidator(descriptor, size);			
			}
			if (descriptor.getType().isArray()) {
				return new ArraySizeValidator(descriptor, size);			
			}
			if (Collection.class.isAssignableFrom(descriptor.getType())) {
				return new CollectionSizeValidator(descriptor, size);			
			}			
		}
		return null;
	}
	
	/**
	 * A {@code SizeConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class SizeConstraintSource implements SimpleMetaDataSource<SizeDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public SizeDescriptor getMetaData(MetaDescriptor descriptor) {
			Size size = descriptor.getAnnotation(Size.class);
			if (size!=null) {
				return new SizeDescriptor(size);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentSizeConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentSizeConstraintSource implements SimpleMetaDataSource<SizeDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public SizeDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Size.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, SizeDescriptor.class, environment);
		}
	}	

}
