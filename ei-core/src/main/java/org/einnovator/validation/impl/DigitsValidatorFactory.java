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
import org.einnovator.validation.contraints.Digits;

/**
 * A {@code ValidatorFactory} for {@code Validator} based on annotation {@code Digits}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see Digits
 * @see Validator
 */
public class DigitsValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies a maximum number of digits constraint.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DigitsDescriptor {

		/**
		 * maximum number of integral digits accepted for this numberThe String representation of the max value according to the {@code BigDecimal} string representation. 
		 */
		int integer;
			
		/**
		 * maximum number of fractional digits accepted for this number
		 */
		int fraction;
		    
		/**
		 * The message to produce or render.
		 */
		MessageDescriptor message;

		/**
		 * Create instance of {@code DigitsDescriptor}.
		 *
		 */
		public DigitsDescriptor() {	
		}
		
		public DigitsDescriptor(Digits digits) {
			this.integer = digits.integer();
			this.fraction = digits.fraction();
			this.message = new MessageDescriptor().assign(digits.message());
		}
	}

	static abstract class DecimalDigitsValidatorBase<T> extends ValidatorSupport<T> {
		private DigitsDescriptor digits;

		public DecimalDigitsValidatorBase(MetaDescriptor descriptor, DigitsDescriptor digits) {
			super(descriptor);
			this.digits = digits;
		}
		
		protected boolean validateInternal(BigDecimal value, ValidationContext context) {
			boolean valid = true;
			if (value!=null) {
				BigInteger i = value.toBigInteger();
				BigDecimal f = value.subtract(new BigDecimal(i));
				if ((i.toString().length()>digits.integer) || (f.toString().length()-2>digits.fraction)) {
					addErrors(value, digits.message, context, digits.integer, digits.fraction);			
				}
				valid = false;
			}
			return valid;
		}
	}
	
	static class BigDecimalDigitsValidator extends DecimalDigitsValidatorBase<BigDecimal> {
		public BigDecimalDigitsValidator(MetaDescriptor descriptor, DigitsDescriptor digits) {
			super(descriptor, digits);
		}
		
		public boolean validate(BigDecimal value, ValidationContext context) {
			return super.validateInternal(value, context);
		}
	}

	static class BigIntegerDigitsValidator extends DecimalDigitsValidatorBase<BigInteger> {
		public BigIntegerDigitsValidator(MetaDescriptor descriptor, DigitsDescriptor digits) {
			super(descriptor, digits);
		}
		
		public boolean validate(BigInteger value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value), context);
		}
	}
	static class NumberDigitsValidator extends DecimalDigitsValidatorBase<Number> {
		public NumberDigitsValidator(MetaDescriptor descriptor, DigitsDescriptor digits) {
			super(descriptor, digits);
		}
		
		public boolean validate(Number value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value.doubleValue()), context);
		}
	}
	
	static class CharSequenceDigitsValidator extends DecimalDigitsValidatorBase<CharSequence> {
		public CharSequenceDigitsValidator(MetaDescriptor descriptor, DigitsDescriptor digits) {
			super(descriptor, digits);
		}
		
		public boolean validate(CharSequence value, ValidationContext context) {
			return super.validateInternal(new BigDecimal(value.toString().toCharArray()), context);
		}
	}
	
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DigitsValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public DigitsValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	/**
	 * @see org.einnovator.validation.ValidatorFactory#createValidator(org.einnovator.meta.MetaDescriptor)
	 */
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		DigitsDescriptor digits = getValidationDescriptor(descriptor, DigitsDescriptor.class);
		if (digits!=null) {
			if (BigDecimal.class.equals(descriptor.getType())) {
				return new BigDecimalDigitsValidator(descriptor, digits);	
			}
			if (BigInteger.class.equals(descriptor.getType())) {
				return new BigIntegerDigitsValidator(descriptor, digits);		
			}
			if (Number.class.isAssignableFrom(descriptor.getType())) {
				return new NumberDigitsValidator(descriptor, digits);
			}
			if (CharSequence.class.isAssignableFrom(descriptor.getType())) {
				return new CharSequenceDigitsValidator(descriptor, digits);	
			}
		}
		return null;
	}
	

	/**
	 * A {@code DigitsConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DigitsConstraintSource implements SimpleMetaDataSource<DigitsDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public DigitsDescriptor getMetaData(MetaDescriptor descriptor) {
			Digits digits = descriptor.getAnnotation(Digits.class);
			if (digits!=null) {
				return new DigitsDescriptor(digits);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentDigitsConstraintSource}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentDigitsConstraintSource implements SimpleMetaDataSource<DigitsDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public DigitsDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Digits.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, DigitsDescriptor.class, environment);
		}
	}	

}
