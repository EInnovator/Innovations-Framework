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
import org.einnovator.validation.contraints.Min;

/**
 * A {@code ValidatorFactory} for {@code Validator} based on annotation {@code Min}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see Min
 * @see Validator
 */
public class MinValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies a minimum value constraint.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class MinDescriptor {
		/**
		 * 	The String representation of the min value according to the {@code BigDecimal} string representation. 
		 */
		private Long value;
			
		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message = null;
		
		public MinDescriptor() {	
		}
		
		public MinDescriptor(Min min) {
			this.value = min.value();
			this.message = new MessageDescriptor().assign(min.message());
		}
	}

	static abstract class MinValidatorBase<T> extends ValidatorSupport<T> {
		private MinDescriptor min;

		public MinValidatorBase(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor);
			this.min = min;
		}
		
		protected boolean validateInternal(Long value, ValidationContext context) {
			boolean valid = true;
			if (value!=null && value<min.value) {
				addErrors(value, min.message, context, min);
				valid = false;
			}
			return valid;
		}
	}
	
	static class LongMinValidator extends MinValidatorBase<Long> {
		public LongMinValidator(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor, min);
		}
		
		public boolean validate(Long value, ValidationContext context) {
			return super.validateInternal(value, context);
		}
	}
	
	static class IntegerMinValidator extends MinValidatorBase<Integer> {
		public IntegerMinValidator(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor, min);
		}
		
		public boolean validate(Integer value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}
	
	static class ShortMinValidator extends MinValidatorBase<Short> {
		public ShortMinValidator(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor, min);
		}
		
		public boolean validate(Short value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}


	static class ByteMinValidator extends MinValidatorBase<Byte> {
		public ByteMinValidator(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor, min);
		}
		
		public boolean validate(Byte value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}

	static class BigDecimalMinValidator extends MinValidatorBase<BigDecimal> {
		public BigDecimalMinValidator(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor, min);
		}
		
		public boolean validate(BigDecimal value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}

	static class BigIntegerMinValidator extends MinValidatorBase<BigInteger> {
		public BigIntegerMinValidator(MetaDescriptor descriptor, MinDescriptor min) {
			super(descriptor, min);
		}
		
		public boolean validate(BigInteger value, ValidationContext context) {
			return super.validateInternal(value.longValue(), context);
		}
	}
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MinValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public MinValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	public Validator<?> createValidator(MetaDescriptor descriptor) {
		MinDescriptor min = getValidationDescriptor(descriptor, MinDescriptor.class);
		if (min!=null) {
			if (TypeUtil.isLong(descriptor.getType())) {
				return new LongMinValidator(descriptor, min);			
			}
			if (TypeUtil.isInt(descriptor.getType())) {
				return new IntegerMinValidator(descriptor, min);			
			}
			if (TypeUtil.isShort(descriptor.getType())) {
				return new ShortMinValidator(descriptor, min);			
			}
			if (TypeUtil.isByte(descriptor.getType())) {
				return new ByteMinValidator(descriptor, min);			
			}
			if (BigDecimal.class.equals(descriptor.getType())) {
				return new BigDecimalMinValidator(descriptor, min);			
			}
			if (BigInteger.class.equals(descriptor.getType())) {
				return new BigIntegerMinValidator(descriptor, min);			
			}			
		}
		return null;
	}
	
	
	public static class MinConstraintSource implements SimpleMetaDataSource<MinDescriptor> {

		@Override
		public MinDescriptor getMetaData(MetaDescriptor descriptor) {
			Min decimalMin = descriptor.getAnnotation(Min.class);
			if (decimalMin!=null) {
				return new MinDescriptor(decimalMin);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentMinConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentMinConstraintSource implements SimpleMetaDataSource<MinDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public MinDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Min.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, MinDescriptor.class, environment);
		}
	}	

}
