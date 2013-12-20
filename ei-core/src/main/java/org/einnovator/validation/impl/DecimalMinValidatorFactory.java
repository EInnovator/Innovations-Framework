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
import org.einnovator.validation.contraints.DecimalMin;

/**
 * A {@code ValidatorFactory} for {@code Validator} based on annotation {@code DecimalMin}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see DecimalMin
 * @see Validator
 */
public class DecimalMinValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies a minimum decimal value constraint.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DecimalMinDescriptor {
		/**
		 * 	The String representation of the min value according to the {@code BigDecimal} string representation. 
		 */
		private BigDecimal value;
			
		/**
		 * Specifies whether the specified minimum is inclusive or 	exclusive.  By default, it is inclusive.
		 * @return <code>true</code> if the value must be lower or equal to the specified minimum, <code>false</code> if the value must be lower.
		 */
		boolean inclusive = true;

		/**
		 * @return the message to produce or render.
		 */
		MessageDescriptor message;
		
		public DecimalMinDescriptor() {	
		}
		
		public DecimalMinDescriptor(DecimalMin decimalMin) {
			this.value = new BigDecimal(decimalMin.value());
			this.inclusive = decimalMin.inclusive();
			this.message = new MessageDescriptor().assign(decimalMin.message());
		}
	}

	static abstract class DecimalMinValidatorBase<T> extends ValidatorSupport<T> {
		private DecimalMinDescriptor decimalMin;

		public DecimalMinValidatorBase(MetaDescriptor descriptor, DecimalMinDescriptor decimalMin) {
			super(descriptor);
			this.decimalMin = decimalMin;
		}
		
		protected boolean validateInternal(BigDecimal value, ValidationContext context) {
			boolean valid = true;
			if (value!=null && ((decimalMin.inclusive && value.compareTo(decimalMin.value)<=0) ||
				(!decimalMin.inclusive && value.compareTo(decimalMin.value)<0))) {
				addErrors(value, decimalMin.message, context, decimalMin.value);
				valid = false;
			}
			return valid;
		}
	}
	
	static class BigDecimalMinValidator extends DecimalMinValidatorBase<BigDecimal> {
		public BigDecimalMinValidator(MetaDescriptor descriptor, DecimalMinDescriptor decimalMin) {
			super(descriptor, decimalMin);
		}
		
		public boolean validate(BigDecimal value, ValidationContext context) {
			return super.validateInternal(value, context);
		}
	}

	static class BigIntegerMinValidator extends DecimalMinValidatorBase<BigInteger> {
		public BigIntegerMinValidator(MetaDescriptor descriptor, DecimalMinDescriptor decimalMin) {
			super(descriptor, decimalMin);
		}
		
		public boolean validate(BigInteger value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value), context);
		}
	}
	static class NumberMinValidator extends DecimalMinValidatorBase<Number> {
		public NumberMinValidator(MetaDescriptor descriptor, DecimalMinDescriptor decimalMin) {
			super(descriptor, decimalMin);
		}
		
		public boolean validate(Number value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value.doubleValue()), context);
		}
	}
	
	static class CharSequenceMinValidator extends DecimalMinValidatorBase<CharSequence> {
		public CharSequenceMinValidator(MetaDescriptor descriptor, DecimalMinDescriptor decimalMin) {
			super(descriptor, decimalMin);
		}
		
		public boolean validate(CharSequence value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value.toString().toCharArray()), context);
		}
	}

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DecimalMinValidatorFactory}.
	 *
	 * @param metaDataSource
	 */
	public DecimalMinValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	/**
	 * @see org.einnovator.validation.ValidatorFactory#createValidator(org.einnovator.meta.MetaDescriptor)
	 */
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		DecimalMinDescriptor decimalMin = getValidationDescriptor(descriptor, DecimalMinDescriptor.class);
		if (decimalMin!=null) {
			if (BigDecimal.class.equals(descriptor.getType())) {
				return new BigDecimalMinValidator(descriptor, decimalMin);			
			}
			if (BigInteger.class.equals(descriptor.getType())) {
				return new BigIntegerMinValidator(descriptor, decimalMin);			
			}
			if (Number.class.isAssignableFrom(descriptor.getType())) {
				return new NumberMinValidator(descriptor, decimalMin);			
			}
			if (CharSequence.class.isAssignableFrom(descriptor.getType())) {
				return new CharSequenceMinValidator(descriptor, decimalMin);			
			}
		}
		return null;
	}
		
	/**
	 * A {@code DecimalMinConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DecimalMinConstraintSource implements SimpleMetaDataSource<DecimalMinDescriptor> {

		@Override
		public DecimalMinDescriptor getMetaData(MetaDescriptor descriptor) {
			DecimalMin decimalMin = descriptor.getAnnotation(DecimalMin.class);
			if (decimalMin!=null) {
				return new DecimalMinDescriptor(decimalMin);
			}
			return null;
		}
	}

	/**
	 * A {@code DecimalMinConstraintSource2}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DecimalMinConstraintSource2 implements SimpleMetaDataSource<DecimalMinDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public DecimalMinDescriptor getMetaData(MetaDescriptor descriptor) {
			DecimalMin decimalMin = descriptor.getAnnotation(DecimalMin.class);
			if (decimalMin!=null) {
				return new DecimalMinDescriptor(decimalMin);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentDecimalMinConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentDecimalMinConstraintSource implements SimpleMetaDataSource<DecimalMinDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public DecimalMinDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, DecimalMin.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, DecimalMinDescriptor.class, environment);
		}
	}	

}
