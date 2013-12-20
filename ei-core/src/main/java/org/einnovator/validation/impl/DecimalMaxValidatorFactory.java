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
import org.einnovator.validation.ValidationContext;
import org.einnovator.validation.Validator;
import org.einnovator.validation.ValidatorSupport;
import org.einnovator.validation.contraints.DecimalMax;

/**
 * A {@code ValidatorFactory} for {@code Validator} based on annotation {@code DecimalMax}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see DecimalMax
 * @see Validator
 */
public class DecimalMaxValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies a maximum decimal value constraint.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DecimalMaxDescriptor {
		/**
		 * 	The String representation of the max value according to the {@code BigDecimal} string representation. 
		 */
		private BigDecimal value;
			
		/**
		 * Specifies whether the specified maximum is inclusive or 	exclusive.  By default, it is inclusive.
		 * @return <code>true</code> if the value must be lower or equal to the specified maximum, <code>false</code> if the value must be lower.
		 */
		boolean inclusive = true;

		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message;
		
		public DecimalMaxDescriptor() {	
		}
		
		public DecimalMaxDescriptor(DecimalMax decimalMax) {
			this.value = new BigDecimal(decimalMax.value());
			this.inclusive = decimalMax.inclusive();
			this.message = new MessageDescriptor().assign(decimalMax.message());
		}
	}

	static abstract class DecimalMaxValidatorBase<T> extends ValidatorSupport<T> {
		private DecimalMaxDescriptor decimalMax;

		public DecimalMaxValidatorBase(MetaDescriptor descriptor, DecimalMaxDescriptor decimalMax) {
			super(descriptor);
			this.decimalMax = decimalMax;
		}
		
		protected boolean validateInternal(BigDecimal value, ValidationContext context) {
			boolean valid = true;
			if (value!=null && ((decimalMax.inclusive && value.compareTo(decimalMax.value)>=0) ||
				(!decimalMax.inclusive && value.compareTo(decimalMax.value)>0))) {
				addErrors(value, decimalMax.message, context, decimalMax.message);
				valid = false;
			}
			return valid;
		}
	}
	
	static class BigDecimalMaxValidator extends DecimalMaxValidatorBase<BigDecimal> {
		public BigDecimalMaxValidator(MetaDescriptor descriptor, DecimalMaxDescriptor decimalMax) {
			super(descriptor, decimalMax);
		}
		
		public boolean validate(BigDecimal value, ValidationContext context) {
			return super.validateInternal(value, context);
		}
	}

	static class BigIntegerMaxValidator extends DecimalMaxValidatorBase<BigInteger> {
		public BigIntegerMaxValidator(MetaDescriptor descriptor, DecimalMaxDescriptor decimalMax) {
			super(descriptor, decimalMax);
		}
		
		public boolean validate(BigInteger value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value), context);
		}
	}
	static class NumberMaxValidator extends DecimalMaxValidatorBase<Number> {
		public NumberMaxValidator(MetaDescriptor descriptor, DecimalMaxDescriptor decimalMax) {
			super(descriptor, decimalMax);
		}
		
		public boolean validate(Number value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value.doubleValue()), context);
		}
	}
	
	static class CharSequenceMaxValidator extends DecimalMaxValidatorBase<CharSequence> {
		public CharSequenceMaxValidator(MetaDescriptor descriptor, DecimalMaxDescriptor decimalMax) {
			super(descriptor, decimalMax);
		}
		
		public boolean validate(CharSequence value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value.toString().toCharArray()), context);
		}
	}

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DecimalMaxValidatorFactory}.
	 *
	 * @param metaDataSource
	 */
	public DecimalMaxValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//
	
	/**
	 * @see org.einnovator.validation.ValidatorFactory#createValidator(org.einnovator.meta.MetaDescriptor)
	 */
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		DecimalMaxDescriptor decimalMax = getValidationDescriptor(descriptor, DecimalMaxDescriptor.class);
		if (decimalMax!=null) {
			if (BigDecimal.class.equals(descriptor.getType())) {
				return new BigDecimalMaxValidator(descriptor, decimalMax);			
			}
			if (BigInteger.class.equals(descriptor.getType())) {
				return new BigIntegerMaxValidator(descriptor, decimalMax);			
			}
			if (Number.class.isAssignableFrom(descriptor.getType())) {
				return new NumberMaxValidator(descriptor, decimalMax);			
			}
			if (CharSequence.class.isAssignableFrom(descriptor.getType())) {
				return new CharSequenceMaxValidator(descriptor, decimalMax);			
			}			
		}
		return null;
	}

	/**
	 * A {@code DecimalMaxConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DecimalMaxConstraintSource implements SimpleMetaDataSource<DecimalMaxDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public DecimalMaxDescriptor getMetaData(MetaDescriptor descriptor) {
			DecimalMax decimalMax = descriptor.getAnnotation(DecimalMax.class);
			if (decimalMax!=null) {
				return new DecimalMaxDescriptor(decimalMax);
			}
			return null;
		}
	}
	
	/**
	 * A {@code EnvironmentDecimalMaxConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentDecimalMaxConstraintSource implements SimpleMetaDataSource<DecimalMaxDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public DecimalMaxDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, DecimalMax.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, DecimalMaxDescriptor.class, environment);
		}
	}	

}
