/**
 * 
 */
package org.einnovator.validation.impl;

import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.einnovator.environment.Environment;
import org.einnovator.environment.EnvironmentHolder;
import org.einnovator.environment.PropertiesMapperUtil;
import org.einnovator.i18n.MessageDescriptor;
import org.einnovator.meta.MetaDataSource;
import org.einnovator.meta.MetaDescriptor;
import org.einnovator.meta.SimpleMetaDataSource;
import org.einnovator.util.types.TypeUtil;
import org.einnovator.validation.ValidationContext;
import org.einnovator.validation.Validator;
import org.einnovator.validation.ValidatorSupport;
import org.einnovator.validation.contraints.Max;

/**
 * A {@code ValidatorFactory} for {@code Validator} based on annotation {@code Max}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see Max
 * @see Validator
 */
public class MaxValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies a maximum value constraint.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class MaxDescriptor {
		/**
		 * 	The String representation of the max value according to the {@code BigDecimal} string representation. 
		 */
		private Long value;
			
		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message = null;
		
		public MaxDescriptor() {	
		}
		
		public MaxDescriptor(Max max) {
			this.value = max.value();
			this.message = new MessageDescriptor().assign(max.message());
		}
	}

	static abstract class MaxValidatorBase<T> extends ValidatorSupport<T> {
		private MaxDescriptor max;

		public MaxValidatorBase(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor);
			this.max = max;
		}
		
		protected boolean validateInternal(Long value, ValidationContext context) {
			boolean valid = true;
			if (value!=null && value>max.value) {
				addErrors(value, max.message, context, max);
				valid = false;
			}
			return valid;
		}
	}
	
	static class LongMaxValidator extends MaxValidatorBase<Long> {
		public LongMaxValidator(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor, max);
		}
		
		public boolean validate(Long value, ValidationContext context) {
			return super.validateInternal(value, context);
		}
	}
	
	static class IntegerMaxValidator extends MaxValidatorBase<Integer> {
		public IntegerMaxValidator(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor, max);
		}
		
		public boolean validate(Integer value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}
	
	static class ShortMaxValidator extends MaxValidatorBase<Short> {
		public ShortMaxValidator(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor, max);
		}
		
		public boolean validate(Short value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}

	static class ByteMaxValidator extends MaxValidatorBase<Byte> {
		public ByteMaxValidator(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor, max);
		}
		
		public boolean validate(Byte value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}

	static class BigMaxValidator extends MaxValidatorBase<BigDecimal> {
		public BigMaxValidator(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor, max);
		}
		
		public boolean validate(BigDecimal value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}

	static class BigIntegerMaxValidator extends MaxValidatorBase<BigInteger> {
		public BigIntegerMaxValidator(MetaDescriptor descriptor, MaxDescriptor max) {
			super(descriptor, max);
		}
		
		public boolean validate(BigInteger value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MaxValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public MaxValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//
	
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		MaxDescriptor max = getValidationDescriptor(descriptor, MaxDescriptor.class);
		if (max!=null) {
			if (TypeUtil.isLong(descriptor.getType())) {
				return new LongMaxValidator(descriptor, max);			
			}
			if (TypeUtil.isInt(descriptor.getType())) {
				return new IntegerMaxValidator(descriptor, max);			
			}
			if (TypeUtil.isShort(descriptor.getType())) {
				return new ShortMaxValidator(descriptor, max);			
			}
			if (TypeUtil.isByte(descriptor.getType())) {
				return new ByteMaxValidator(descriptor, max);			
			}
			if (BigDecimal.class.equals(descriptor.getType())) {
				return new BigMaxValidator(descriptor, max);			
			}
			if (BigInteger.class.equals(descriptor.getType())) {
				return new BigIntegerMaxValidator(descriptor, max);			
			}			
		}

		return null;
	}
	
	/**
	 * A {@code MaxConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class MaxConstraintSource implements SimpleMetaDataSource<MaxDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public MaxDescriptor getMetaData(MetaDescriptor descriptor) {
			Max decimalMax = descriptor.getAnnotation(Max.class);
			if (decimalMax!=null) {
				return new MaxDescriptor(decimalMax);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentMaxConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentMaxConstraintSource implements SimpleMetaDataSource<MaxDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public MaxDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Max.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, MaxDescriptor.class, environment);
		}
	}	

}
